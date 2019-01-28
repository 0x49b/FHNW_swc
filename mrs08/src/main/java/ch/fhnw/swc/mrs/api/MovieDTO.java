package ch.fhnw.swc.mrs.api;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MovieDTO {
	/** Unique identifier for this movie. */
	private final ReadOnlyIntegerProperty id;
	

	/** Indicates whether this movie is rented. */
	private final BooleanProperty rented;
	
	/** This movies title. */
	private final StringProperty title;
	
	/** The release date of this movie. */
	private final ObjectProperty<LocalDate> releaseDate;
	
	/** The minimum age to be allowed to rent this movie. */
	private final IntegerProperty ageRating;
	
	/** The price category of this movie. */
	private final StringProperty priceCategory;

	/**
	 * 
	 * @return id
	 */
	public ReadOnlyIntegerProperty getId() {
		return id;
	}

	/**
	 * 
	 * @return rented state
	 */
	public BooleanProperty getRented() {
		return rented;
	}

	/**
	 * 
	 * @return title
	 */
	public StringProperty getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title new title
	 */
	public void setTitle(String title) {
		this.title.set(title);
	}
	

	/**
	 * 
	 * @return releaseDate
	 */
	public ObjectProperty<LocalDate> getReleaseDate() {
		return releaseDate;
	}

	/**
	 * 
	 * @param newReleaseDate new release date
	 */
	public void setReleaseDate(LocalDate newReleaseDate) {
		releaseDate.set(newReleaseDate);
	}
	
	/**
	 * 
	 * @return age Rating of movie
	 */
	public IntegerProperty getAgeRating() {
		return ageRating;
	}

	/**
	 * 
	 * @param newAgeRating new age rating
	 */
	public void setAgeRating(int newAgeRating) {
		ageRating.set(newAgeRating);
	}
	
	/**
	 * 
	 * @return price category of movie
	 */
	public StringProperty getPriceCategory() {
		return priceCategory;
	}

	/**
	 * 
	 * @param newPriceCategory new category value
	 */
	public void setPriceCategory(String newPriceCategory) {
		priceCategory.set(newPriceCategory);
	}

	
	/**
	 * @param anId
	 *            unique identification of movie.
	 * @param isRented
	 *            whether this movie is rented.
	 * @param anAgeRating
	 *            the minimum age to rent this movie.
	 * @param aTitle
	 *            the movie's title
	 * @param aReleaseDate
	 *            the movie's release date.
	 * @param aPriceCategory
	 *            the price category for renting this movie.
	 */
	public MovieDTO(int anId, boolean isRented, int anAgeRating, String aTitle, LocalDate aReleaseDate,
			String aPriceCategory) {
		id = new SimpleIntegerProperty(anId);
		rented = new SimpleBooleanProperty(isRented);
		ageRating = new SimpleIntegerProperty(anAgeRating);
		title = new SimpleStringProperty(aTitle);
		releaseDate = new SimpleObjectProperty<LocalDate>(aReleaseDate);
		priceCategory = new SimpleStringProperty(aPriceCategory);
	}

}
