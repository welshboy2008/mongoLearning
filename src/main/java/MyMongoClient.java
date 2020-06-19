import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

public class MyMongoClient {

    public static void main(String args[]) {
        // MongoClient is essentially our connection pool to the
        // document database.
        MongoClient mc = new MongoClient();

        // This data base if it doesn't exist, will be created when we put something in it.
        MongoDatabase database = mc.getDatabase("myDatabase");

        // This isn't the code in the tutorial.
        MongoCollection<Document> myCollection = database.getCollection("myCollection");

        // Build an object to put in our DB.
        List<Integer> bookISBNS = Arrays.asList(12312,1235,32423,1213123);

        Document person = new Document("_id", "jo")
                .append("name", "Jonathon Finbar Saunders")
                .append("address", new BasicDBObject("street", "123 Somerset Street")
                        .append("city", "anyCity")
                        .append("county", "someCounty")
                        .append("post-code", "SS12 3GH"))
                .append("books", bookISBNS);

        myCollection.insertOne(person);

        // we should ALWAYS close our mongo client when we shut down.
        mc.close();
    }
}
