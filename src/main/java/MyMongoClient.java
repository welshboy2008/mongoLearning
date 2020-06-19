import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class MyMongoClient {
    private static MongoClient mc;
    private static MongoDatabase database;
    private static MongoCollection<Document> myCollection;

    /**
     * Set up the various variables for us to access the
     * Mongo collections.
     */
    public MyMongoClient() {
        mc = new MongoClient();
        database = mc.getDatabase("myDatabase");
        myCollection = database.getCollection("myCollection");
    }

    /**
     * @return Document generated from Person object.
     */
    public static Document personToDocument(Person person) {
        return new Document("_id", person.getID())
                .append("name", person.getName())
                .append("Address", person.getContactDetails());
    }

    /**
     * Look for a record within the database with a specific _id
     * @param searchTerm - the ID of the record in the DB
     * @return Document if found, null if not.
     */
    public Document getPersonByID(String searchTerm) {
        return myCollection.find(eq("_id", searchTerm)).first();
    }

    public void addRecord(Document record) {
        myCollection.insertOne(record);
    }

    public void addObject(Person person) {
        myCollection.insertOne(personToDocument(person));
    }

    public void closeDatabase() {
        mc.close();
    }

    public void wipeDatabase() {
        mc.dropDatabase("myCollection");
    }

    public void removeRecord(String searchTerm) {
        DeleteResult dr = myCollection.deleteOne(eq("_id", searchTerm));
        System.out.println("Number of records deleted: " + dr.getDeletedCount());
    }

    public static void main(String args[]) {
        MyMongoClient myc = new MyMongoClient();
        List<Integer> bookISBNS = Arrays.asList(1234, 4567, 6789);

        // One way of putting data in to the collection.
        Document person = new Document("_id", "jo")
                .append("name", "Jonathon Finbar Saunders")
                .append("address", new BasicDBObject("street", "123 Somerset Street")
                        .append("city", "anyCity")
                        .append("county", "someCounty")
                        .append("post-code", "SS12 3GH"))
                .append("books", bookISBNS);
        myc.addRecord(person);

        // However, we'll normally build an object, serialize it in to a Document object
        // and then pump that to the Database. But I'm aware that I'm barely scratching the
        // surface here.
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("AddressLine1", "Jedi Temple");
        addressMap.put("AddressLine2", "Senatorial District");
        addressMap.put("AddressLine3", "Coruscant");

        Person p1 = new Person("Obi-Wan", "Obi-Wan Kenobi", addressMap);
        myc.addObject(p1);
        myc.removeRecord("jo");

        // we should ALWAYS close our mongo client when we shut down.
        //myc.wipeDatabase();
        myc.closeDatabase();
    }
}
