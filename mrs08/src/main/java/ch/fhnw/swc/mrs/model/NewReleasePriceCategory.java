package ch.fhnw.swc.mrs.model;

/**
 * @author wolfgang.schwaiger
 * 
 */
public final class NewReleasePriceCategory extends PriceCategory {

    /**
     * A new release costs 3 per day.
     * 
     * @see ch.fhnw.swc.mrs.model.PriceCategory#getCharge(int)
     * @param daysRented no of days that a movie is rented.
     * @return rental price for movie.
     */
    @Override
    public double getCharge(int daysRented) {
        if (daysRented > 0) {
            return daysRented * 3;
        }
        return 0.0d;
    }

    /**
     * There is one frequent renter point for a one day rentals and two frequent renter points for
     * any rental of two and more days.
     * 
     * @see ch.fhnw.swc.mrs.model.PriceCategory getFrequentRenterPoints(int)
     * @param daysRented no of days that a movie is rented.
     * @return frequent renter points earned.
     */
    @Override
    public int getFrequentRenterPoints(int daysRented) {
        int result = 0;
        if (daysRented > 0) {
            if (daysRented == 1) {
                result = 1;
            } else { // add bonus for two day new release rental
                result = 2;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "New Release";
    }

    /** singleton instance. */
    private static NewReleasePriceCategory singleton = new NewReleasePriceCategory();

    /**
     * prevent instantiation from outside.
     */
    private NewReleasePriceCategory() {
    };

    static {
        // register itself to the PriceCategory
        registerPriceCategory(NewReleasePriceCategory.getInstance());
    }

    /**
     * Access singleton instance.
     * 
     * @return singleton
     */
    public static NewReleasePriceCategory getInstance() {
        return singleton;
    }

}
