package ch.fhnw.swc.mrs.data;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import ch.fhnw.swc.mrs.api.MRSServices;
import ch.fhnw.swc.mrs.api.MovieDTO;
import ch.fhnw.swc.mrs.api.RentalDTO;
import ch.fhnw.swc.mrs.api.UserDTO;
import ch.fhnw.swc.mrs.model.Movie;
import ch.fhnw.swc.mrs.model.PriceCategory;
import ch.fhnw.swc.mrs.model.Rental;
import ch.fhnw.swc.mrs.model.User;

/**
 * A simple implementation of the MRS Services.
 */
public class SimpleMRSServices implements MRSServices {

    private Map<Integer, Movie> movies = new HashMap<>();
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Rental> rentalList = new HashMap<>();

    private int movieId = 0;
    private int userId = 0;
    private int rentalId = 0;

    @Override
    public MovieDTO createMovie(String aTitle, LocalDate aReleaseDate, String aPriceCategory, int anAgeRating) {
        try {
            PriceCategory pc = PriceCategory.getPriceCategoryFromId(aPriceCategory);
            Movie m = new Movie(aTitle, aReleaseDate, pc);
            int id = ++movieId;
            m.setId(id);
            movies.put(id, m);
            return Conversions.convert(m);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<MovieDTO> getAllMovies() {
        return Conversions.convertMovieList(Collections.unmodifiableCollection(movies.values()));
    }

    @Override
    public Collection<MovieDTO> getAllMovies(boolean rented) {
        Collection<Movie> result = new ArrayList<>();
        for (Movie m : movies.values()) {
            if (rented == m.isRented()) {
                result.add(m);
            }
        }
        return Conversions.convertMovieList(result);
    }

    @Override
    public MovieDTO getMovieById(int id) {
        return Conversions.convert(movies.get(id));
    }

    @Override
    public boolean updateMovie(MovieDTO movie) {
        Movie m = Conversions.convert(movie);
        movies.put(m.getId(), m);
        return true;
    }

    @Override
    public boolean deleteMovie(int id) {
        return movies.remove(id) != null;
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return Conversions.convertUserList(Collections.unmodifiableCollection(users.values()));
    }

    @Override
    public UserDTO getUserById(int id) {
        return Conversions.convert(users.get(id));
    }

    @Override
    public UserDTO getUserByName(String name) {
        for (User u : users.values()) {
            if (u.getName().equals(name)) {
                return Conversions.convert(u);
            }
        }
        return null;
    }

    @Override
    public UserDTO createUser(String aName, String aFirstName, LocalDate aBirthdate) {
        try {
            User u = new User(aName, aFirstName);
            int id = ++userId;
            u.setId(id);
            users.put(id, u);
            return Conversions.convert(u);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateUser(UserDTO u) {
        User user = Conversions.convert(u);
        users.put(user.getId(), user);
        return true;
    }

    @Override
    public boolean deleteUser(int id) {
        return users.remove(id) != null;
    }

    @Override
    public Collection<RentalDTO> getAllRentals() {
        return Conversions.convertRentalList(Collections.unmodifiableCollection(rentalList.values()));
    }

    @Override
    public boolean createRental(int userId, int movieId, LocalDate d) {
        User u = users.get(userId);
        Movie m = movies.get(movieId);

        if (u != null && m != null && !m.isRented() && !d.isAfter(LocalDate.now())) {
            Rental r = new Rental(u, m, d);
            int id = ++rentalId;
            r.setId(id);
            rentalList.put(id, r);
            return true;
        }
        return false;
    }

    @Override
    public boolean returnRental(int id) {
        Rental r = rentalList.get(id);
        r.getMovie().setRented(false);
        boolean result = r.getUser().getRentals().remove(r);
        rentalList.remove(id);
        return result;
    }

    /**
     * Initialize the "server component".
     */
    public void init() {
        readMovies();
        readUsers();
        readRentals();
        movieId = movies.size() + 1;
        userId = users.size() + 1;
        rentalId = rentalList.size() + 1;
    }

    private void readMovies() {
        try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/data/movies.csv"))) {
            Iterable<CSVRecord> movieList = CSVFormat.EXCEL.withFirstRecordAsHeader().withHeader(MovieHeaders.class)
                    .withDelimiter(';').parse(in);
            for (CSVRecord m : movieList) {
                int id = Integer.parseInt(m.get(MovieHeaders.ID));
                String title = m.get(MovieHeaders.Title);
                LocalDate releaseDate = LocalDate.parse(m.get(MovieHeaders.ReleaseDate));
                PriceCategory pc = PriceCategory.getPriceCategoryFromId(m.get(MovieHeaders.PriceCategory));
                boolean isRented = Boolean.parseBoolean(m.get(MovieHeaders.isRented));
                // int ageRating = Integer.parseInt(m.get(MovieHeaders.AgeRating));
                Movie movie = new Movie(title, releaseDate, pc);
                movie.setId(id);
                movie.setRented(isRented);
                movies.put(id, movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readUsers() {
        try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/data/users.csv"))) {
            Iterable<CSVRecord> usersList = CSVFormat.EXCEL.withFirstRecordAsHeader().withHeader(UserHeaders.class)
                    .withDelimiter(';').parse(in);
            for (CSVRecord u : usersList) {
                int id = Integer.parseInt(u.get(UserHeaders.ID));
                String surname = u.get(UserHeaders.Surname);
                String firstname = u.get(UserHeaders.FirstName);
                // LocalDate birthdate = LocalDate.parse(u.get(UserHeaders.Birthdate));
                User user = new User(surname, firstname);
                user.setId(id);
                users.put(id, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readRentals() {
        try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/data/rentals.csv"))) {
            Iterable<CSVRecord> rentals = CSVFormat.EXCEL.withFirstRecordAsHeader().withHeader(RentalHeaders.class)
                    .withDelimiter(';').parse(in);
            for (CSVRecord r : rentals) {
                int id = Integer.parseInt(r.get(RentalHeaders.ID));
                LocalDate rentaldate = LocalDate.parse(r.get(RentalHeaders.RentalDate));
                int userId = Integer.parseInt(r.get(RentalHeaders.UserID));
                int movieId = Integer.parseInt(r.get(RentalHeaders.MovieID));
                User u = users.get(userId);
                Movie m = movies.get(movieId);
                Rental rental = new Rental(u, m, rentaldate);
                rental.setId(id);
                rentalList.put(id, rental);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    enum MovieHeaders {
        ID, Title, ReleaseDate, PriceCategory, AgeRating, isRented
    }

    enum UserHeaders {
        ID, Surname, FirstName, Birthdate
    }

    enum RentalHeaders {
        ID, RentalDate, UserID, MovieID
    }

}
