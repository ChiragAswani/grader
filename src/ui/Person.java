package ui;

import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty BUID;
    private final SimpleStringProperty test;

    public Person(String fName, String lName, String BUID, String test) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.BUID = new SimpleStringProperty(BUID);
        this.test = new SimpleStringProperty(test);
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

    public void setTest(String t) {
        test.set(t);
    }


}

