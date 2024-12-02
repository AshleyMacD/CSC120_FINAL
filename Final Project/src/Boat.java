/**
 * Boat Class
 * @author Ashley MacDougal
 */


import java.io.Serializable;


public class Boat implements Serializable {


    /**
     * Enum for boat type
     */
    public enum BoatType {
        POWER, SAILING
    }

    /**
     * Type of boat
     */
    BoatType boatType;
    /**
     * Name of boat
     */
    String boatName;
    /**
     * Year of manufacture
     */
    int yearOfManufacture;
    /**
     * Make of boat
     */
    String makeModel;
    /**
     * Length of boat
     */
    int length;
    /**
     * Purchase price of boat
     */
    double purchasePrice;
    /**
     * Expenses of boat
     */
    double expenses;

    /**
     * Default constructor
     */
    public Boat() {
        boatType = null;
        boatName = "";
        yearOfManufacture = 0;
        makeModel = "";
        length = 0;
        purchasePrice = 0.0;
        expenses = 0.0;


    }

    /**
     * Initial value constructor.
     * @param type Boat type
     * @param name Boat name
     * @param year Boat year
     * @param make Boat make
     * @param feet Boat length
     * @param price Boat price
     * @param expense Boat expenses
     */
    public Boat(BoatType type, String name, int year, String make, int feet, double price, double expense) {
        boatType = type;
        boatName = name;
        yearOfManufacture = year;
        makeModel = make;
        length = feet;
        purchasePrice = price;
        expenses = expense;

    }

    /**
     * Gets the boat price
     * @return Boat price
     */
    public double getPrice() {
        return purchasePrice;
    }

    /**
     * Gets the updated expenses of the boat
     * @return Boats expense
     */
    public double getExpenses() {
        return expenses;
    }

    /**
     * Updates boats expenses
     * @param newExpenses Updates expenses
     */
    public void updateExpenses(double newExpenses) {
        expenses = expenses + newExpenses;
    }

    /**
     * Gets the boats name
     * @return Boat name
     */
    public String getName() {
        return boatName;
    }

    /**
     * Produces printable information about the boat
     * @return String with boat's type, name, year, make, length, price and expenses
     */
    public String toString() {

        return String.format("%s   %s          %d %s %d'  :  Paid $%.2f : Spent $%.2f",
                boatType, boatName, yearOfManufacture, makeModel, length, purchasePrice, expenses);
    }

}
