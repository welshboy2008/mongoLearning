import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMongoClient {

    /**
     * @return Document generated from Person object.
     */
    public static Document personToDocument(Person person) {
        return new Document("id", person.getID())
                .append("name", person.getName())
                .append("Address", person.getContactDetails());
    }

    public Document getPerson(String searchTerm) {

    }

    public static void main(String args[]) {
        // MongoClient is essentially our connection pool to the
        // document database.
        MongoClient mc = new MongoClient();

        // This data base if it doesn't exist, will be created when we put something in it.
        MongoDatabase database = mc.getDatabase("myDatabase");

        // This isn't the code in the tutorial.
        MongoCollection<Document> myCollection = database.getCollection("myCollection");

        List<Integer> bookISBNS = Arrays.asList(1234, 4567, 6789);

        // One way of putting data in to the collection.
        Document person = new Document("_id", "jo")
                .append("name", "Jonathon Finbar Saunders")
                .append("address", new BasicDBObject("street", "123 Somerset Street")
                        .append("city", "anyCity")
                        .append("county", "someCounty")
                        .append("post-code", "SS12 3GH"))
                .append("books", bookISBNS);

        myCollection.insertOne(person);


        // However, we'll normally build an object, serialize it in to a Document object
        // and then pump that to the Database. But I'm aware that I'm barely scratching the
        // surface here.
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("AddressLine1", "Jedi Temple");
        addressMap.put("AddressLine2", "Senatorial District");
        addressMap.put("AddressLine3", "Coruscant");

        Person p1 = new Person("Obi-Wan", "Obi-Wan Kenobi", addressMap);
        myCollection.insertOne(personToDocument(p1));

        // we should ALWAYS close our mongo client when we shut down.
        mc.close();
    }
}
