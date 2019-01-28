package ch.fhnw.swc.mrs.api;

import java.time.LocalDate;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class RentalDTO {
    /** Unique identifier of this rental. */
    private ReadOnlyIntegerProperty id;
    /** The title of the movie that was rented. */
    private ReadOnlyStringProperty title;
    /** The renter's name. */
    private ReadOnlyStringProperty userName;
    /** The renter's first name. */
    private ReadOnlyStringProperty userFirstName;
    /** The date this rental started. */
    private ReadOnlyObjectProperty<LocalDate> rentalDate;
    /** The duration of this rental in days. */
    private ReadOnlyIntegerProperty rentalDays;
    /** The fee due for this rental. */
    private ReadOnlyDoubleProperty rentalFee;

    /**
     * @return the id
     */
    public ReadOnlyIntegerProperty getId() {
        return id;
    }

    /**
     * @return the title
     */
    public ReadOnlyStringProperty getTitle() {
        return title;
    }

    /**
     * @return the userName
     */
    public ReadOnlyStringProperty getUserName() {
        return userName;
    }

    /**
     * @return the userFirstName
     */
    public ReadOnlyStringProperty getUserFirstName() {
        return userFirstName;
    }

    /**
     * @return the rentalDate
     */
    public ReadOnlyObjectProperty<LocalDate> getRentalDate() {
        return rentalDate;
    }

    /**
     * @return the rentalDays
     */
    public ReadOnlyIntegerProperty getRentalDays() {
        return rentalDays;
    }

    /**
     * @return the rentalFee
     */
    public ReadOnlyDoubleProperty getRentalFee() {
        return rentalFee;
    }

    /**
     * Constructs a rental of a movie to a user at a given date for a certain number of days.
     * 
     * @param anId unique identifier of this rental.
     * @param aName name of user who is renting aMovie.
     * @param aFirstName first name of user who is renting aMovie.
     * @param aTitle title of movie that is rented.
     * @param aRentalDate date of start of this rental.
     * @param rentaldays duration of this rental.
     * @param aFee rental fee.
     */
    public RentalDTO(int anId, String aName, String aFirstName, String aTitle, LocalDate aRentalDate, int rentaldays,
            double aFee) {
        id = new SimpleIntegerProperty(anId);
        title = new SimpleStringProperty(aTitle);
        userName = new SimpleStringProperty(aName);
        userFirstName = new SimpleStringProperty(aFirstName);
        rentalDate = new SimpleObjectProperty<>(aRentalDate);
        rentalDays = new SimpleIntegerProperty(rentaldays);
        rentalFee = new SimpleDoubleProperty(aFee);
    }

}
