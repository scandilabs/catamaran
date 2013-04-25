package com.scandilabs.catamaran.mail.async;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.scandilabs.catamaran.mail.send.SimpleHtmlMailSender;

public class AsyncDaemon extends TimerTask {
    
    private SessionFactory sessionFactory;
    
    private SimpleHtmlMailSender simpleHtmlMailSender;
    
    private static Logger logger = LoggerFactory.getLogger(AsyncDaemon.class);
    
    private static Timer timer = new Timer("Async-Mail", true);
    
    private int initialDelay; 
    private int period;
    private static Calendar calendar = new GregorianCalendar();
    
    private static AsyncDaemon instance;
    
    /**
     * Public constructor used by surrounding framework (like Spring container)
     * @param initialDelay initial delay in ms
     * @param period time between runs in ms
     */
    public AsyncDaemon(SessionFactory sessionFactory, SimpleHtmlMailSender simpleHtmlMailSender, int initialDelay, int period) {
        this.sessionFactory = sessionFactory;
        this.initialDelay = initialDelay;        
        this.period = period;
        this.simpleHtmlMailSender = simpleHtmlMailSender;
    }
    
    /**
     * Starts the daemon service
     */
    public void init() {
        logger.debug("Starting async mail daemon..");        
        timer.schedule(this, initialDelay, period);
        logger.debug(String.format("Scheduled for run in %dms, and then every %dms", initialDelay, period));           
    }
    
    public void run() {
        if (logger.isTraceEnabled()) {
            logger.trace("Running..");    
        }        
        long start = System.currentTimeMillis();        
        Session session = sessionFactory.getCurrentSession();
        Transaction txn = session.beginTransaction();
        
        // NOTE: We're not paying much attention to locking here, because it's assumed that only one daemon thread is running.  If we go multi-threaded then this probably needs to be synchronized (either in java or DB)
        Criteria crit = session.createCriteria(AsyncMessage.class);
        crit.add(Restrictions.eq("status", AsyncMessage.Status.PENDING));
        crit.add(Restrictions.le("nextAttemptTime", new Date()));
        List<AsyncMessage> pendingMessages = crit.list();
        for (AsyncMessage asyncMessage : pendingMessages) {
            SimpleMailMessage message = asyncMessage.toSimpleMailMessage();
            try {
                simpleHtmlMailSender.send(message);    
                
                // Success
                logger.debug(String.format("Success: Sent %s to %s", asyncMessage.getSubject(), asyncMessage.getToTabSeparated()));                
                asyncMessage.setAttempts(asyncMessage.getAttempts() + 1);
                asyncMessage.setAsyncSentSuccessTime(new Date());
                asyncMessage.setLastAttemptTime(new Date());
                asyncMessage.setStatus(AsyncMessage.Status.SENT);
            } catch (MailException e) {                
                asyncMessage.setLastAttemptMessage(e.getMessage());
                asyncMessage.setAttempts(asyncMessage.getAttempts() + 1);
                asyncMessage.setLastAttemptTime(new Date());
                
                // Use exponential seconds calculation to compute the next attempt time (wait longer and longer)
                int secondsToWait = (int) Math.pow(2, asyncMessage.getAttempts());
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, secondsToWait);
                asyncMessage.setNextAttemptTime(calendar.getTime());
                logger.error(String.format("Async sending of %s to %s failed, will try again in %ds: %sec", asyncMessage.getSubject(), asyncMessage.getToTabSeparated(), secondsToWait, e.getMessage()), e);
            }
            
            // Update async message in db
            session.update(asyncMessage);            
        }
        
        // Commit, close session
        txn.commit();
        session.close();
                
        if (logger.isTraceEnabled()) {
            logger.trace("Done running.");
        }
    }  
    
}
