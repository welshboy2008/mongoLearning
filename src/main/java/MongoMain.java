import com.mongodb.MongoClient;

public class MongoMain {
    public static void main(String args[]) {
        // MongoClient is essentially our connection pool to the
        // document database.
        MongoClient mc = new MongoClient();
        // we should ALWAYS close our mongo client when we shut down.
        mc.close();
    }
}
