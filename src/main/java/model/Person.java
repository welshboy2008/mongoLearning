package model;

import java.util.Map;

public class Person {
    private String id;
    private String name;
    private Map<String, String> contactDetails;

    public Person(String id, String name, Map<String, String> ct) {
        this.id = id;
        this.name = name;
        this.contactDetails = ct;
    }

    /**
     * Get the map containing the contact details.
     * @return
     */
    public Map<String, String> getContactDetails() {
        return contactDetails;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Parses the contactDetails map, to print a formatted address
     * to the screen.
     * @return String - Formatted address.
     */
    public String printFormattedAddress() {
        StringBuilder addressString = new StringBuilder();
        for(Map.Entry<String, String> entries : contactDetails.entrySet()) {
            addressString.append(entries.getKey());
            addressString.append(": \t\t");
            addressString.append(entries.getValue());
            addressString.append("\n" );
        }
        return addressString.toString();
    }
}
