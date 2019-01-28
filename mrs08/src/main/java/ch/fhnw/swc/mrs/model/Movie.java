package ch.fhnw.swc.mrs.model;

import java.time.LocalDate;

/**
 * Represents a movie.
 */
public class Movie {
    /** Default title for movies. */
    public static final String DEFAULT_TITLE = "Untitled";
    /** Exception text: Illegal value for age rating was used. */
    public static final String EXC_AGE_RATING = "Age rating must be in range [0, 18]";
    /** Exception text: Id of this movie has not been set. */
    public static final String EXC_ID_NOT_SET = "Id of this movie has not been set.";
    /** Exception text: Id cannot be changed for movies. */
    public static final String EXC_ID_FIXED = "Id cannot be changed for movies";
    /** Exception text: Title must not be null nor emtpy. */
    public static final String EXC_MISSING_TITLE = "Title must not be null nor emtpy";
    /** Exception text: Release date must not be null. */
    public static final String EXC_MISSING_RELEASE_DATE = "Release date must not be null";
    /** Exception text: price category must not be null. */
    public static final String EXC_MISSING_PRICE_CATEGORY = "price category must not be null";

    private boolean initialized = false;
    private int id = 0;
    private boolean rented = false;
    private String title = "Untitled";
    private LocalDate releaseDate;
    private PriceCategory priceCategory;
    private int ageRating;

    /** Ctor only for testing needed. */
    protected Movie() {
        this(DEFAULT_TITLE, LocalDate.now(), RegularPriceCategory.getInstance());
    }

    /**
     * Objects initialized with this constructor are not ready for use. They must be assigned an id!
     * 
     * @param aTitle Title of the movie. Must not be null nor empty.
     * @param aReleaseDate Date when this movie was released. Must not be null.
     * @param aPriceCategory Price category for this movie. Must not be null.
     * @throws IllegalArgumentException in case, any of the parameters are null or title is empty.
     */
    public Movie(String aTitle, LocalDate aReleaseDate, PriceCategory aPriceCategory) {
        setTitle(aTitle);
        setReleaseDate(aReleaseDate);
        setPriceCategory(aPriceCategory);
    }

    /**
     * @return unique identification number of this Movie.
     * @throws IllegalStateException when trying to retrieve id before it was set.
     */
    public Integer getId() {
        if (initialized) {
            return id;
        } else {
            throw new IllegalStateException(EXC_ID_NOT_SET);
        }
    }

    /**
     * @param anId set an unique identification number for this Movie.
     * @throws IllegalStateException when trying to re-set id.
     */
    public void setId(Integer anId) {
        if (initialized) {
            throw new IllegalStateException(EXC_ID_FIXED);
        }
        initialized = true;
        id = anId;
    }

    /**
     * @return The title of this Movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param aTitle set the title of this Movie.
     */
    protected void setTitle(String aTitle) {
        if (aTitle == null || aTitle.trim().isEmpty()) {
            throw new IllegalArgumentException(EXC_MISSING_TITLE);
        }
        title = aTitle;
    }

    /**
     * @return whether this Movie is rented to a User.
     */
    public boolean isRented() {
        return rented;
    }

    /**
     * @param isRented set the rented status.
     */
    public void setRented(boolean isRented) {
        rented = isRented;
    }

    /**
     * @return the date this Movie was released.
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param aReleaseDate set the date this Movie was released.
     */
    protected void setReleaseDate(LocalDate aReleaseDate) {
        if (aReleaseDate == null) {
            throw new IllegalArgumentException(EXC_MISSING_RELEASE_DATE);
        }
        releaseDate = aReleaseDate;
    }

    /**
     * @return PriceCategory of this Movie.
     */
    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    /**
     * @param aPriceCategory set PriceCategory for this Movie.
     */
    public void setPriceCategory(PriceCategory aPriceCategory) {
        if (aPriceCategory == null) {
            throw new IllegalArgumentException(EXC_MISSING_PRICE_CATEGORY);
        }
        priceCategory = aPriceCategory;
    }

    /**
     * @return The minimum age for being allowed to rent this movie.
     */
    public int getAgeRating() {
        return ageRating;
    }

    /**
     * @param ageRating The minimum age for being allowed to rent this movie.
     */
    public void setAgeRating(int ageRating) {
        if (ageRating < 0 || ageRating > 18) {
            throw new IllegalArgumentException(EXC_AGE_RATING);
        }
        this.ageRating = ageRating;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + getId();
        result = prime * result + ((getReleaseDate() == null) ? 0 : getReleaseDate().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || !(obj instanceof Movie)) {
            return false;
        }

        // declare other object to avoid casts in the following
        final Movie other = (Movie) obj;
        if (getId() != other.getId()) {
            return false;
        }

        if (getReleaseDate() == null) {
            if (other.getReleaseDate() != null) {
                return false;
            }
        } else if (!getReleaseDate().equals(other.getReleaseDate())) {
            return false;
        }

        if (getTitle() == null) {
            if (other.getTitle() != null) {
                return false;
            }
        } else if (!getTitle().equals(other.getTitle())) {
            return false;
        }

        return true;
    }

}
