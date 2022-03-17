/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copypasteproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author IT-0
 */
public class Person {

    private final StringProperty firstName;

    public Person(String fName) {
        this.firstName = new SimpleStringProperty(fName);
    }

    public final StringProperty firstNameProperty() {
        return this.firstName;
    }

    public final java.lang.String getFirstName() {
        return this.firstNameProperty().get();
    }

    public final void setFirstName(final java.lang.String firstName) {
        this.firstNameProperty().set(firstName);
    }

    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + '}';
    }
    
}
