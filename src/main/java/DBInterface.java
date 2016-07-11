/**
 * Created by garrethdottin on 7/10/16.
 */

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.HashMap;

public class DBInterface {
    private Cluster cluster;
    private Session session;

    public Session getSession() {
        return this.session;
    }

    public void connect(String node) {
        cluster = Cluster.builder()
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

    public void saveToDB(HashMap obj) {
        Object text = obj.get("text");
        Object score = obj.get("score");
        session.execute("INSERT INTO college.emails (email_text, sentiment_score)" +
                    "VALUES ('" +
                    text + "',"  +
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
                        "email_text text," +
                        "sentiment_score integer" +
                        ");");
    }

    public void close() {
        session.close();
        cluster.close();
    }

    public static void main(String[] args) {
        DBInterface client = new DBInterface();
        client.connect("127.0.0.1");
        client.createSchema();
    }
}