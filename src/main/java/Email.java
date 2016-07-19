/**
 * Created by dottig2-adm on 7/18/2016.
 */
public class Email {
    private String[] text;
    private String referenceId;
    private String[] references;
    private String from;
    private String to;
    private String email;
    private boolean endOfChain = true;

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String[] getReferences() {
        return references;
    }

    public void setReferences(String[] references) {
        this.references = references;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEndOfChain() {
        return endOfChain;
    }

    public void setEndOfChain(boolean endOfChain) {
        this.endOfChain = endOfChain;
    }
}
