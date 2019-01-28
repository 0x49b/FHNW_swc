package ch.fhnw.swc.mrs.api;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDTO {

    /** An identification number unique to each user. */
    private final ReadOnlyIntegerProperty id;

    /** The user's family name. */
    private final StringProperty name;

    /** The user's first name. */
    private final StringProperty firstName;

    /** The user's date of birth is used to check age ratings. */
    private final ObjectProperty<LocalDate> birthdate;

    /**
     * @return the id
     */
    public ReadOnlyIntegerProperty getId() {
        return id;
    }

    /**
     * @return the name
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * @param newName the new name
     */
    public void setName(String newName) {
        name.set(newName);
    }

    /**
     * @return the firstName
     */
    public StringProperty getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param newFirstName new first name
     */
    public void setFirstName(String newFirstName) {
        firstName.set(newFirstName);
    }

    /**
     * @return the birthdate
     */
    public ObjectProperty<LocalDate> getBirthdate() {
        return birthdate;
    }

    /**
     * 
     * @param newBirthdate new birthdate
     */
    public void setBirthdate(LocalDate newBirthdate) {
        birthdate.set(newBirthdate);
    }

    /**
     * Create a new user with the given name information.
     * 
     * @param anId unique user identification.
     * @param aName the user's family name.
     * @param aFirstName the user's first name.
     * @param aBirthdate the user's date of birth.
     */
    public UserDTO(int anId, String aName, String aFirstName, LocalDate aBirthdate) {
        id = new SimpleIntegerProperty(anId);
        name = new SimpleStringProperty(aName);
        firstName = new SimpleStringProperty(aFirstName);
        birthdate = new SimpleObjectProperty<LocalDate>(aBirthdate);
    }

}
