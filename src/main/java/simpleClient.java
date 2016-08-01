/**
 * Created by garrethdottin on 7/10/16.
 */

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import java.util.Set;
import java.util.TreeSet;

public class simpleClient {
    private Cluster cluster;
    private Session session;

    public Session getSession() {
        return this.session;
    }

    public void connect(String node, int cassandraPort) {
        cluster = Cluster.builder().withPort(cassandraPort)
                .addContactPoint(node)
                .build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n",
                metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }
        session = cluster.connect();
    }

    public void saveToDB(Email obj) {
        String text = obj.getText();
        String date = obj.getDate();
        String FromEmail = obj.getFromEmail();
        String subject = obj.getSubject();
        String to = obj.getTo();
        Double score = obj.getSentimentScore();
        String uniqueId = obj.getMessageId();
//        String[] references = obj.getReferences();
        Set<String> reference = new TreeSet<String>();
        // Change to references
//        String email = obj.getReplyTo()[0];

        session.execute("INSERT INTO college.emails (email_text, email_date, email_from, email_subject, email_to, email_score, email_id)" +
                "VALUES ('" +
                text + "', '" +
                date + "', '" +
                FromEmail + "', '" +
                subject + "', '" +
                to + "', " +
                score + ", '" +
                uniqueId + "' " +
                ");");
    }

    public ResultSet getAllResults() {
        ResultSet result = session.execute("SELECT * FROM college.emails");
        return result;
    }

    public ResultSet getOneItem(String messageId) {
        ResultSet result = session.execute("SELECT * FROM college.emails where email_id = '" + messageId +"'");
        return result;

    }

    public void createSchema() {
        session.execute("CREATE KEYSPACE IF NOT EXISTS college WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':3};");
        session.execute(
                "CREATE TABLE IF NOT EXISTS college.emails (" +
                        "email_id text PRIMARY KEY," +
                        "email_text text," +
                        "email_date text, " +
                        "email_from text," +
                        "email_subject text," +
                        "email_to text, " +
                        "email_score double," +
                        "email_references set<text>" +
                        ");");
    }

    public void close() {
        session.close();
        cluster.close();
    }

    public static void main(String[] args) {
        simpleClient client = new simpleClient();
        client.connect("127.0.0.1", 9042);
        client.createSchema();
        client.close();
    }
}