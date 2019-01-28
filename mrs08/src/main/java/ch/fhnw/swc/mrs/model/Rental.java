package ch.fhnw.swc.mrs.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import ch.fhnw.swc.mrs.api.MovieRentalException;

/**
 * Represents a rental.
 */
public class Rental {
    /** ID is not yet set. */
    public static final String EXC_ID_NOT_SET = "ID of this Rental object has not been set.";
    /** ID is already set. */
    public static final String EXC_ID_ALREADY_SET = "Illegal change of rental's id";
    /**
     * Movie argument is either
     * 
     * <pre>
     * null
     * </pre>
     * 
     * or the designated movie is already rented.
     */
    public static final String EXC_MOVIE_NOT_RENTALBE = "Movie must not be null or is already rented.";
    /** User argument must not be null. */
    public static final String EXC_USER_NULL = "User must not be null.";
    /** Rental date must not be in the future. */
    public static final String EXC_RENTAL_DATE_IN_FUTURE = "Rental date must not be null or in the future. ";
    /** Attempt to rent a movie when not being old enough. */
    public static final String EXC_UNDER_AGE = "User under age: may not rent this movie.";

    /** Flag indicating whether the object has been initialized. */
    private boolean initialized = false;

    private int id = 0;
    private Movie movie;
    private User user;
    private LocalDate rentalDate;

    /**
     * Constructs a rental of a movie to a user at a given date for a certain number of days.
     * <b>Note:</b> objects created with this constructor are not ready for use, they need to be
     * assigned an id!
     * 
     * @param aUser User who is renting aMovie.
     * @param aMovie Movie that is rented.
     * @param aRentalDate date of start of this rental.
     * @throws MovieRentalException if the maximum number of movies that one user can rent is
     *             exceeded.
     * @throws IllegalStateException if the movie is already rented.
     * @throws NullPointerException if not all input parameters where set.
     */
    public Rental(User aUser, Movie aMovie, LocalDate aRentalDate) {
        setUser(aUser);
        List<Rental> rentals = aUser.getRentals();
        setMovie(aMovie);

        rentals.add(this);
        aMovie.setRented(true);
        setRentalDate(aRentalDate);
    }

    /**
     * @return the unique rental identifier.
     */
    public int getId() {
        if (initialized) {
            return id;
        } else {
            throw new IllegalStateException(EXC_ID_NOT_SET);
        }
    }

    /**
     * The unique identifier can only be set once.
     * 
     * @param anId a unique identifier for rentals.
     * @throws IllegalStateException if the id has already been set.
     */
    public void setId(int anId) {
        if (initialized) {
            throw new IllegalStateException(EXC_ID_ALREADY_SET);
        } else {
            initialized = true;
            id = anId;
        }
    }

    /**
     * Calculate the duration of this rental.
     * 
     * @return the number of days this movie is rented to the user.
     */
    public int getRentalDays() {
        return (int) ChronoUnit.DAYS.between(rentalDate, LocalDate.now());
    }

    /**
     * @return The rental fee to pay for this rental.
     */
    public double getRentalFee() {
        return getMovie().getPriceCategory().getCharge(getRentalDays());
    }

    /**
     * @return the rented movie.
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @return the user who is renting.
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the rental date.
     */
    public LocalDate getRentalDate() {
        return rentalDate;
    }

    /**
     * @param aMovie the movie that is rented.
     * @throws NullPointerException if aMovie is <code>null</code>.
     */
    protected void setMovie(Movie aMovie) {
        if (aMovie == null || (initialized && aMovie.isRented())) {
            throw new MovieRentalException(EXC_MOVIE_NOT_RENTALBE);
        }
        movie = aMovie;
    }

    /**
     * @param anUser the user that is renting a movie.
     * @throws NullPointerException if anUser is <code>null</code>.
     */
    protected void setUser(User anUser) {
        if (anUser == null) {
            throw new NullPointerException(EXC_USER_NULL);
        }
        user = anUser;
    }

    /**
     * @param aRentalDate the date of the rental.
     */
    protected void setRentalDate(LocalDate aRentalDate) {
        if (aRentalDate == null || aRentalDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(EXC_RENTAL_DATE_IN_FUTURE);
        }
        rentalDate = aRentalDate;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = this == o;
        if (!result) {
            if (o instanceof Rental) {
                Rental other = (Rental) o;
                result = initialized ? getId() == other.getId() : initialized == other.initialized;
                result &= getMovie().equals(other.getMovie());
                result &= getUser().equals(other.getUser());
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = initialized ? getId() : 0;
        result = result * 19 + getMovie().hashCode();
        result = result * 19 + getUser().hashCode();
        return result;
    }

}
