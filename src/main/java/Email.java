/**
 * Created by dottig2-adm on 7/18/2016.
 */

public class Email {
    private String text;
    private String date;
    private String[] references = new String[]{};
    private String referenceId;
    private String subject;
    private String[] inReplyTo;
    private String messageId;
    private String from;
    private String to;
    private String priority;
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
}