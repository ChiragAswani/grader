package ui;

import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty BUID;

    public Person(String fName, String lName, String BUID) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.BUID = new SimpleStringProperty(BUID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String fName) {
        lastName.set(fName);
    }

    public String getBUID() {
        return BUID.get();
    }

    public void setBUID(String fName) {
        BUID.set(fName);
    }


}

