import com.sun.tools.javac.code.Attribute;

import java.util.ArrayList;

/**
 * Created by garrethdottin on 7/15/16.
 */
public class Email {
    private String date;
    private String[] references;
    private String subject;
    private String[] inReplyTo;
    private String messageId;
    private String from;
    private String text;
    private String to;
    private String priority;
    private Double sentimentScore;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getReferences() {
        return references;
    }

    public void setReferences(String[] references) {
        this.references = references;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String[] inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }
}
