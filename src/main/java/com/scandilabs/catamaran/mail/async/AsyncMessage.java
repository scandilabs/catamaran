package com.scandilabs.catamaran.mail.async;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Index;
import org.springframework.mail.SimpleMailMessage;

import com.scandilabs.catamaran.entity.support.IdentifiableBase;
import com.scandilabs.catamaran.util.ArrayUtils;

@Entity
public class AsyncMessage extends IdentifiableBase {

    private long id;

    /**
     * An identifier that should be injected into the email body and can be used
     * to retrieve an online version of the message
     */
    private String externalMessageId;
    
    private String from;
    private String replyTo;
    private String toTabSeparated;
    private String ccTabSeparated;
    private String bccTabSeparated;
    private Date sentDate;
    private Date asyncSentSuccessTime;
    private Date asyncInsertTime;
    private Date lastAttemptTime;
    private Date nextAttemptTime;
    private String lastAttemptMessage;
    private int attempts;
    private String subject;
    private String text;
    private Status status = Status.PENDING;

    public AsyncMessage() {
    }

    public AsyncMessage(SimpleMailMessage message, String externalMessageId) {
        this.externalMessageId = externalMessageId;
        this.from = message.getFrom();
        this.replyTo = message.getReplyTo();
        this.toTabSeparated = ArrayUtils.toString(message.getTo(), "\t");
        this.ccTabSeparated = ArrayUtils.toString(message.getCc(), "\t");
        this.bccTabSeparated = ArrayUtils.toString(message.getBcc(), "\t");
        this.sentDate = message.getSentDate();
        this.subject = message.getSubject();
        this.text = message.getText();
    }

    public SimpleMailMessage toSimpleMailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setReplyTo(this.replyTo);
        if (this.toTabSeparated != null) {
            message.setTo(this.toTabSeparated.split("\t"));
        }
        if (this.ccTabSeparated != null) {
            message.setCc(this.ccTabSeparated.split("\t"));
        }
        if (this.bccTabSeparated != null) {
            message.setBcc(this.bccTabSeparated.split("\t"));
        }
        message.setSentDate(this.sentDate);
        message.setSubject(this.subject);
        message.setText(this.text);
        return message;
    }

    @Column(name = "messageFrom")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getToTabSeparated() {
        return toTabSeparated;
    }

    public void setToTabSeparated(String toTabSeparated) {
        this.toTabSeparated = toTabSeparated;
    }

    public String getCcTabSeparated() {
        return ccTabSeparated;
    }

    public void setCcTabSeparated(String ccTabSeparated) {
        this.ccTabSeparated = ccTabSeparated;
    }

    public String getBccTabSeparated() {
        return bccTabSeparated;
    }

    public void setBccTabSeparated(String bccTabSeparated) {
        this.bccTabSeparated = bccTabSeparated;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Lob
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public enum Status {
        PENDING, SENT, FAILED;
    }

    @Index(name = "statusIndex")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getAsyncInsertTime() {
        return asyncInsertTime;
    }

    public void setAsyncInsertTime(Date asyncInsertTime) {
        this.asyncInsertTime = asyncInsertTime;
    }

    public Date getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void setLastAttemptTime(Date lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getLastAttemptMessage() {
        return lastAttemptMessage;
    }

    public void setLastAttemptMessage(String lastAttemptErrorMessage) {
        this.lastAttemptMessage = lastAttemptErrorMessage;
    }

    public Date getNextAttemptTime() {
        return nextAttemptTime;
    }

    public void setNextAttemptTime(Date nextAttemptTime) {
        this.nextAttemptTime = nextAttemptTime;
    }

    public Date getAsyncSentSuccessTime() {
        return asyncSentSuccessTime;
    }

    public void setAsyncSentSuccessTime(Date asyncSentSuccessTime) {
        this.asyncSentSuccessTime = asyncSentSuccessTime;
    }

    @Index(name = "externalMessageIdIndex")
    public String getExternalMessageId() {
        return externalMessageId;
    }

    public void setExternalMessageId(String externalMessageId) {
        this.externalMessageId = externalMessageId;
    }

}
