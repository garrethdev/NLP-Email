/**
 * Created by dottig2-adm on 7/18/2016.
 */
import java.util.Date;

public class Email {
    private String text;
    private String date;
    private String[] references = new String[]{};
    private String referenceId;
    private String fromEmail;
    private String subject;
    private String[] replyTo;
    private String messageId;
    private String to;
    private String priority;
    private Date timestamp;
    private Double sentimentScore;
    private boolean endOfChain = true;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

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

    public String[] getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String[] replyTo) {
        this.replyTo = replyTo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean isEndOfChain() {
        return endOfChain;
    }

    public void setEndOfChain(boolean endOfChain) {
        this.endOfChain = endOfChain;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}