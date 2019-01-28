package ch.fhnw.swc.mrs.api;

import java.time.LocalDate;
import java.util.Collection;

/**
 * The service interface for all services that the MRS offers.
 */
public interface MRSServices {
    /**
     * Create a new movie.
     * 
     * @param aTitle Title of the movie. Must not be null nor empty.
     * @param aReleaseDate Date when this movie was released. Must not be null.
     * @param aPriceCategory Price category for this movie. Must not be null.
     * @param anAgeRating How old a user must be at least to be allowed to rent this Movie. A value
     *            between [0, 18].
     * @return a MovieDTO containing the data of the newly created Movie object.
     * @throws IllegalArgumentException in case, any of the parameters are null or title is empty.
     */
    MovieDTO createMovie(String aTitle, LocalDate aReleaseDate, String aPriceCategory, int anAgeRating);

    /**
     * Retrieve all Movies.
     * 
     * @return all Movies.
     */
    Collection<MovieDTO> getAllMovies();

    /**
     * get all rented or available Movies.
     * 
     * @param rented whether the available or the rented Movies shall be retrieved.
     * @return all Movies that are either rented or not (depending on parameter rented)
     */
    Collection<MovieDTO> getAllMovies(boolean rented);

    /**
     * @param id the identification of the Movie to retrieve.
     * @return get Movie by its ID.
     */
    MovieDTO getMovieById(int id);

    /**
     * Update Movie with new data.
     * 
     * @param movie contains the new data.
     * @return whether the update operation was successful.
     */
    boolean updateMovie(MovieDTO movie);

    /**
     * Delete Movie.
     * 
     * @param id id of Movie to delete.
     * @return whether the delete operation was successful.
     */
    boolean deleteMovie(int id);

    /**
     * Retrieve all Users.
     * 
     * @return all Users.
     */
    Collection<UserDTO> getAllUsers();

    /**
     * @param id the identification of the User to retrieve.
     * @return get User by its ID.
     */
    UserDTO getUserById(int id);

    /**
     * @param name retrieve first user found with given name.
     * @return User or null if not found.
     */
    UserDTO getUserByName(String name);

    /**
     * Create a new user with the given name information.
     * 
     * @param aName the user's family name.
     * @param aFirstName the user's first name.
     * @param aBirthdate the user's date of birth.
     * @return a UserDTO containing the data of the newly create User object.
     * @throws IllegalArgumentException The name must neither be <code>null</code>.
     * @throws MovieRentalException If the name is empty ("") or longer than 40 characters.
     */
    UserDTO createUser(String aName, String aFirstName, LocalDate aBirthdate);

    /**
     * Update User with new data.
     * 
     * @param u contains the new data.
     * @return whether the update operation was successful.
     */
    boolean updateUser(UserDTO u);

    /**
     * Delete User.
     * 
     * @param id id of user to delete.
     * @return whether the delete operation was successful.
     */
    boolean deleteUser(int id);

    /**
     * Retrieve all Rentals.
     * 
     * @return all Rentals.
     */
    Collection<RentalDTO> getAllRentals();

    /**
     * Create a new Rental.
     * 
     * @param userId the id of the user who is renting a movie.
     * @param movieId the id of the rented movie.
     * @param rentalDate date the rental starts.
     * @return whether a new rental could be created.
     */
    boolean createRental(int userId, int movieId, LocalDate rentalDate);

    /**
     * Return a rented Movie.
     * 
     * @param id id of rental to terminate.
     * @return whether the return was successful.
     */
    boolean returnRental(int id);

    /**
     * Initialize the backend component.
     */
    void init();
}
