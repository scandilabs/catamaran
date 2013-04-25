package com.scandilabs.catamaran.mail.async;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;


/**
 * Sends a message via the AsyncMailMessage persistent object. Note does NOT
 * support attachments yet.
 * 
 * @author mkvalsvik
 * 
 */
public class AsyncSender {

    private static Logger logger = LoggerFactory
            .getLogger(AsyncSender.class);

    private SessionFactory sessionFactory;

    public AsyncSender(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Send by persisting to local db
     * 
     * @param simpleMailMessage
     */
    public void send(final SimpleMailMessage simpleMailMessage, String messageId) {
        AsyncMessage asyncMessage = new AsyncMessage(simpleMailMessage, messageId);
        Session session = sessionFactory.getCurrentSession();
        asyncMessage.setAsyncInsertTime(new Date());
        asyncMessage.setNextAttemptTime(new Date());

        String toString = "[no TO field specified in message]";
        if (simpleMailMessage.getTo() != null) {
            toString = ArrayUtils.toString(simpleMailMessage.getTo());
        }
        
        logger.info(String.format("Scheduling async mail to %s, subject: %s",
                toString, simpleMailMessage.getSubject()));
        session.save(asyncMessage);

    }

}
