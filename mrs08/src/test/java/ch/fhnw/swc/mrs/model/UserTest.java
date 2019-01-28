package ch.fhnw.swc.mrs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.fhnw.swc.mrs.api.MovieRentalException;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class UserTest {
    private static final String NAME = "name";
    private static final String FIRSTNAME = "first name";
    private static final String EMPTYSTRING = "";

    private static final String LONG_TEXT = "This is a very long name which is over fourty characters long and thus should result in an exception";

    @Test
    public void testUser() {
        User u = new User(NAME, FIRSTNAME);
        assertNotNull(u, "u should not be null");

        // check if name and first name were stored correctly
        String n = u.getName();
        String f = u.getFirstName();
        assertEquals(NAME, n);
        assertEquals(FIRSTNAME, f);

        // check if there exists a rental list
        List<Rental> rentals = u.getRentals();
        assertNotNull(rentals, "rentals list should be empty, not null");
        assertEquals(0, rentals.size());
    }

    /**
     * Test if correct exceptions are thrown in constructor.
     */
    @Test
    public void testUserExceptions() {
        Throwable e = assertThrows(IllegalArgumentException.class, () -> new User(null, FIRSTNAME));
        assertEquals(User.MISSING_NAME, e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> new User(NAME, null));
        assertEquals(User.MISSING_NAME, e.getMessage());

        e = assertThrows(MovieRentalException.class, () -> new User(EMPTYSTRING, FIRSTNAME));
        assertEquals(User.ILLEGAL_NAME, e.getMessage());

        e = assertThrows(MovieRentalException.class, () -> new User(NAME, EMPTYSTRING));
        assertEquals(User.ILLEGAL_NAME, e.getMessage());
    }

    @Test
    public void testSetterGetterId() {
        User u = new User(NAME, FIRSTNAME);
        u.setId(42);
        assertEquals(42, u.getId());
        assertThrows(IllegalStateException.class, () -> u.setId(0));
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#getRentals()} . Test method for
     * {@link ch.fhnw.edu.rental.model.User#setRentals()}.
     */
    @Test
    public void testSetterGetterRentals() {
        List<Rental> list = new LinkedList<Rental>();
        User u = new User(NAME, FIRSTNAME);
        u.setRentals(list);
        assertEquals(list, u.getRentals());
        u.setRentals(null);
        assertNull(u.getRentals(), "rental list must be null");
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#getName()}. Test method for
     * {@link ch.fhnw.edu.rental.model.User#setName()}.
     */
    @Test
    public void testSetterGetterName() {
        User u = new User(NAME, FIRSTNAME);

        Throwable e = assertThrows(IllegalArgumentException.class, () -> u.setName(null));
        assertEquals(User.MISSING_NAME, e.getMessage());

        e = assertThrows(MovieRentalException.class, () -> u.setName(""));
        assertEquals(User.ILLEGAL_NAME, e.getMessage());

        e = assertThrows(MovieRentalException.class, () -> u.setName(LONG_TEXT));
        assertEquals(User.ILLEGAL_NAME, e.getMessage());

        u.setName("Bla");
        assertEquals("Bla", u.getName());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#setName(java.lang.String)}. Test method
     * for {@link ch.fhnw.edu.rental.model.User#getFirstName()}.
     */
    @Test
    public void testSetterGetterFirstName() {
        User u = new User(NAME, FIRSTNAME);

        Throwable e = assertThrows(IllegalArgumentException.class, () -> u.setFirstName(null));
        assertEquals(User.MISSING_NAME, e.getMessage());

        e = assertThrows(MovieRentalException.class, () -> u.setFirstName(""));
        assertEquals(User.ILLEGAL_NAME, e.getMessage());

        e = assertThrows(MovieRentalException.class, () -> u.setFirstName(LONG_TEXT));
        assertEquals(User.ILLEGAL_NAME, e.getMessage());

        u.setFirstName("Bla");
        assertEquals("Bla", u.getFirstName());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.User#getCharge()}.
     */
    @Test
    public void testGetCharge() {
        LocalDate today = LocalDate.now();
        double delta = 1e-6;
        User u = new User(NAME, FIRSTNAME);
        // a newly created user has no rentals and no charge.
        double charge = u.getCharge();
        assertEquals(0.0d, charge, delta);

        PriceCategory regular = RegularPriceCategory.getInstance();

        // first check regular movie
        Movie mov = new Movie("A", today, regular);
        Rental r = new Rental(u, mov, today);
        charge = r.getRentalFee();
        assertEquals(charge, u.getCharge(), delta);

        // now add another two regular movies
        mov = new Movie("B", today, regular);
        r = new Rental(u, mov, today);
        charge += r.getRentalFee();
        mov = new Movie("C", today, regular);
        r = new Rental(u, mov, today);
        charge += r.getRentalFee();
        assertEquals(charge, u.getCharge(), delta);
    }

    @Test
    public void testEquals() throws Exception {
        User u1 = new User(NAME, FIRSTNAME);
        User u2 = new User(NAME, FIRSTNAME);
        u1.setId(4);
        u2.setId(4);
        assertTrue(u1.equals(u1));
        assertFalse(u1.equals(NAME));
        assertTrue(u1.equals(u2));
        assertTrue(u2.equals(u1));
        u2 = new User(NAME, FIRSTNAME);
        u2.setId(5);
        assertFalse(u1.equals(u2));
        assertFalse(u2.equals(u1));
        u1 = new User(NAME, FIRSTNAME);
        u1.setId(5);
        assertTrue(u1.equals(u2));
        u1.setName("Meier");
        assertFalse(u1.equals(u2));
        assertFalse(u2.equals(u1));
        u2.setName("Meier");
        assertTrue(u1.equals(u2));
        u1.setFirstName("Hans");
        assertFalse(u1.equals(u2));
        assertFalse(u2.equals(u1));
        u2.setFirstName("Hans");
        assertTrue(u1.equals(u2));
    }

    @Test
    public void testHashCode() throws Exception {
        // dummy user objects
        User x = new User(NAME, FIRSTNAME);
        User y = new User(NAME, FIRSTNAME);

        assertEquals(x.hashCode(), y.hashCode());
        x.setId(42);
        assertTrue(x.hashCode() != y.hashCode());
        y.setId(42);
        assertEquals(x.hashCode(), y.hashCode());
    }

}
