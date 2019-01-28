package ch.fhnw.swc.mrs.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Unit tests for class Movie.
 */
public class MovieTest {
    private LocalDate today;

    // Expected exception messages.
    private static final String PC_MSG = "price category must not be null";
    private static final String TITLE_MSG = "Title must not be null nor emtpy";
    private static final String RD_MSG = "Release date must not be null";

    // PriceCategories used in tests.
    private static final PriceCategory REGULAR = RegularPriceCategory.getInstance();

    @BeforeEach
    public void setup() {
        today = LocalDate.now();
    }

    @DisplayName("Test hashCode")
    @Test
    public void testHashCode() throws InterruptedException {
        Movie x = new Movie();
        x.setId(1);
        Movie y = new Movie("A", today, REGULAR);
        y.setId(2);
        Movie z = new Movie("A", today, REGULAR);
        z.setId(2);

        // do we get consistently the same result?
        int h = x.hashCode();
        assertEquals(h, x.hashCode());
        h = y.hashCode();
        assertEquals(h, y.hashCode());

        // do we get the same result from two equal objects?
        h = y.hashCode();
        assertEquals(h, z.hashCode());

        // still the same hashcode after changing rented state?
        z.setRented(true);
        assertEquals(h, z.hashCode());

        final Movie m = new Movie("A", today, REGULAR); // get a new Movie
        m.setId(2);
        m.setPriceCategory(ChildrenPriceCategory.getInstance());
        assertEquals(h, m.hashCode());

        assertThrows(IllegalStateException.class, () -> m.setId(42));
    }

    @DisplayName("Create a Movie with protected ctor")
    @Test
    public void testMovie() {
        Movie m = new Movie();
        assertNotNull(m.getPriceCategory());
        assertNotNull(m.getReleaseDate());
        assertNotNull(m.getTitle());
        assertFalse(m.isRented());
        assertEquals(0, m.getAgeRating());
    }

    @DisplayName("Create a Movie with public ctor")
    @Test
    public void testMovieStringDatePriceCategory() throws InterruptedException {
        LocalDate anotherDay = LocalDate.of(1969, 7, 19);
        Movie m = new Movie("A", anotherDay, REGULAR);
        assertEquals("A", m.getTitle());
        assertEquals(RegularPriceCategory.class, m.getPriceCategory().getClass());
        assertEquals(anotherDay, m.getReleaseDate());
        assertFalse(m.isRented());
    }

    @DisplayName("Try to instantiate Movie with no or empty title")
    @Test
    public void testExceptionOnMissingTitle() {
        Throwable e = assertThrows(IllegalArgumentException.class, () -> new Movie(null, today, REGULAR));
        assertEquals(TITLE_MSG, e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> new Movie("", today, REGULAR));
        assertEquals(TITLE_MSG, e.getMessage());
    }

    @DisplayName("Try to instantiate Movie with no price category")
    @Test
    public void testExceptionOnMissingPriceCategory() {
        Throwable e = assertThrows(IllegalArgumentException.class, () -> new Movie("A", today, null));
        assertEquals(PC_MSG, e.getMessage());
    }

    @DisplayName("Try to instantiate Movie with no release date")
    @Test
    public void testExceptionOnMissingReleaseDate() {
        Throwable e = assertThrows(IllegalArgumentException.class, () -> new Movie("A", null, REGULAR));
        assertEquals(RD_MSG, e.getMessage());
    }

    @DisplayName("Try to re-set id")
    @Test
    public void testIdReset() {
        Movie m = new Movie();

        Throwable e = assertThrows(IllegalStateException.class, () -> m.getId());
        assertEquals(Movie.EXC_ID_NOT_SET, e.getMessage());

        m.setId(42);
        assertEquals(42, m.getId().intValue());

        e = assertThrows(IllegalStateException.class, () -> m.setId(43));
        assertEquals(Movie.EXC_ID_FIXED, e.getMessage());
    }

    @DisplayName("Try ctor with null args")
    @Test
    public void testExceptionMovieStringDatePriceCategory() {
        Throwable e = assertThrows(IllegalArgumentException.class, () -> new Movie(null, today, REGULAR));
        assertEquals(TITLE_MSG, e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new Movie("A", null, REGULAR));
        assertEquals(RD_MSG, e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> new Movie("A", today, null));
        assertEquals(PC_MSG, e.getMessage());
    }

    @DisplayName("Compare with same object")
    @Test
    public void testEqualsIdentity() {
        Movie m = new Movie();
        // 1. test on identity
        assertTrue(m.equals(m));
    }

    @Test
    public void testSetTitle() {
        Movie m = new Movie();
        m.setTitle("Hallo");
        assertEquals("Hallo", m.getTitle());

        assertThrows(IllegalArgumentException.class, () -> m.setTitle(null));

    }

    @Test
    public void testSetReleaseDate() {
        Movie m = new Movie();
        m.setReleaseDate(today);
        assertEquals(today, m.getReleaseDate());
        assertThrows(IllegalArgumentException.class, () -> m.setReleaseDate(null));
    }
}
