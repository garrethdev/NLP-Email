/**
 * Created by garrethdottin on 7/10/16.
 */

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

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
        for ( Host host : metadata.getAllHosts() ) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }
        session = cluster.connect();
    }

    public void saveToDB(Email obj) {
        String text = obj.getText();
        Double score = obj.getSentimentScore();
        String from = obj.getFrom();
        String to = obj.getTo();
        String date = obj.getDate();
        String uniqueId = obj.getMessageId();
        String[] references  = obj.getReferences();
        String email = obj.getInReplyTo()[0];

        session.execute("INSERT INTO college.emails (email_text, email_header, sentiment_score)" +
                "VALUES ('" +
                text + "',"  +
                score + "'," +
                score + ""   +
                ");");
    }

    public ResultSet getAllResults(String[] args) {
        ResultSet result = session.execute("SELECT * FROM emails");
        return result;
    }

    public void createSchema() {
        session.execute("CREATE KEYSPACE IF NOT EXISTS college WITH replication " +
                "= {'class':'SimpleStrategy', 'replication_factor':3};");
        session.execute(
                "CREATE TABLE IF NOT EXISTS college.emails (" +
                        "id uuid PRIMARY KEY," +
                        "text text," +
                        "from text, " +
                        "re string," +
                        "to string," +
                        "date string, " +
                        "sentiment_score double," +
                        "email string," +
                        "references set" +
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