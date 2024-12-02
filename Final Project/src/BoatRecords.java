
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

/**
 * Boat Records Final
 * Manage Boat Records
 * @author Ashley MacDougal
 *
 */

public class BoatRecords {

    /**
     * Global Scanner object to use keyboard
     */
    public static final Scanner keyboard = new Scanner(System.in);
    /**
     * File name to store and load data if applicable
     */
    private static final String DATA_FILE_NAME = "FleetData.db";


    /**
     * The main method
     * @param args Passed in from the command line
     */
    public static void main(String[] args) {

        // ArrayList to hold all the boats in the records
        ArrayList<Boat> boats;
        boats = new ArrayList<>();

        // Checking if there is an argument in the command line,
        // if there isn't the program loads the data from FleetData.db
        if (args.length > 0) {

            // Setting csvBoatFile equal to the name of the file given in the command line
            String csvBoatFile = args[0];

            // Calls the loadBoatDataFromCSVFile method
            loadBoatDataFromCSVFile(csvBoatFile, boats);

        } else {

            // Calls the loadDBFileSavedData method
            loadDBFileSavedData(boats);
        } // End of the if else statement


        System.out.println("Welcome to the Fleet Management System\n" +
                "--------------------------------------");

        // Calls the menu method
        menuInput(boats);


    } // End of main method


    /**
     * Load data from the CSV File
     * @param csvBoatFile CSV File given by the command line
     * @param boats ArrayList of Boat objects
     */
    public static void loadBoatDataFromCSVFile(String csvBoatFile, ArrayList<Boat> boats) {

        try (BufferedReader reader = new BufferedReader(new FileReader(csvBoatFile))) {
            String currentLine;

            currentLine = reader.readLine(); // Using the reader to read the file


            while (currentLine != null) {

                // While there is still lines to be read the reader will read
                // Then the line will be split up at the commas

                String[] userInputArray = currentLine.split(",");

                try {

                    Boat boat = new Boat(
                            // Each piece of data from the file is used to create an object
                            Boat.BoatType.valueOf(userInputArray[0].toUpperCase()),
                            userInputArray[1],
                            Integer.parseInt(userInputArray[2]),
                            userInputArray[3],
                            Integer.parseInt(userInputArray[4]),
                            Double.parseDouble(userInputArray[5]),
                            0 // Expenses set to 0 when new boats are entered


                    );
                    boats.add(boat); // Boat is added
                } catch (NumberFormatException e) {
                    // Checking for formating errors
                    System.out.println("Number error in line:  " + currentLine);
                } catch (IllegalArgumentException e) {
                    // Checking for argument errors
                    System.out.println("Boat type error in line:  " + currentLine);
                }
                currentLine = reader.readLine();
            } // End of while loop
            if (reader != null) {
                try {
                    reader.close();
                    // Closing file
                } catch (IOException e) {
                    // Printing error message if file couldn't be closed
                    System.out.println("Error closing " + csvBoatFile);
                }
            } // End of if statement

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }



    } // End of loadBoatDataFromCSVFile method


    /**
     * Load data from DB File (if no CSV File provided)
     * @param boats ArrayList of Boat objects
     */
    public static void loadDBFileSavedData(ArrayList<Boat> boats) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_NAME))) {
            // Clear the list before loading new data
            boats.clear();
            // Load the data
            List<Boat> loadedFleet = (List<Boat>) objectInputStream.readObject();
            boats.addAll(loadedFleet);

        } catch (IOException | ClassNotFoundException e) {
            // Printing error message if needed
            System.out.println("Error loading database file: " + e.getMessage());
        }
    } // End of loadDBFileSavedData method



    /**
     * Save boat data to the serialized DB file
     * @param boats ArrayList of Boat objects
     *
     */
    public static void saveFleetDataToDB(ArrayList<Boat> boats) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME))) {
            // Load the data to the file
            objectOutputStream.writeObject(boats);


        } catch (IOException e) {
            // Print error message if needed
            System.out.println("Error saving to database file: " + e.getMessage());
        }
    } // End of saveFleetDataToDB method

    /**
     * Offer user Menu options and call corresponding methods
     * @param boats ArrayList of Boat objects
     */
    private static void menuInput(ArrayList<Boat> boats) {

        // Variable for user input
        char userInput;


        // Offer options and ask for user input
        System.out.print("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");
        userInput = keyboard.next().toLowerCase().charAt(0);

        if (userInput == 'p') {
            // Call print method
            printInventory(boats);

        } else if (userInput == 'a') {
            // Call add boat method
            addBoat(boats);

        } else if (userInput == 'r') {
            // Call remove boat method
            removeBoat(boats);

        } else if (userInput == 'e') {
            // Call request permission method
            requestPermission(boats);

        } else if (userInput == 'x') {
            // Call exit program method
            exitProgram(boats);


        } else {
            // Re ask for user input if invalid option is given
            System.out.println("Invalid menu option. Try again.");
            menuInput(boats);
        }


    } // End of menuInput method

    /**
     * Prints boat record's inventory
     * @param boats ArrayList of Boat objects
     */
    private static void printInventory(ArrayList<Boat> boats) {
        System.out.println("Fleet report: ");
        int index = 0;
        double totalPrice = 0;
        double totalExpenses = 0;
        // Calculate expenses per boat
        for (index = 0; index < boats.size(); index++) {
            System.out.println(boats.get(index));
            totalPrice += boats.get(index).getPrice();
            totalExpenses += boats.get(index).getExpenses();
        } // End of for loop
        // Print out each object with formatting
        System.out.printf("Total                       : Paid $%.2f     Spent $%.2f%n", totalPrice, totalExpenses);

        // Send user back to menu
        menuInput(boats);

    } // End of printInventory method

    /**
     * Makes a boat object based on user input
     * @param userInput A string of comma separated values from the user
     * @return A boat object created from userInput
     */
    private static Boat makeBoat(String userInput) {

        // Creates a new array of strings
        String [] userInputArray = new String[6];
        // Separates the users input at each comma
        userInputArray = userInput.split(",");

        // Creates a new boat based on the users
        Boat userBoat = new Boat(Boat.BoatType.valueOf(userInputArray[0].toUpperCase()), userInputArray[1], Integer.parseInt(userInputArray[2]), userInputArray[3], Integer.parseInt(userInputArray[4]), Double.parseDouble(userInputArray[5]), 0);


        return userBoat;
    } // End of makeBoat method


    /**
     * Adds the user created boat
     * @param boats ArrayList of Boat objects
     */


    private static void addBoat(ArrayList<Boat> boats) {

        // Prompts user for input
        System.out.print("Please enter the new boat CSV data: ");
        String userInput;
        userInput = keyboard.next();
        // Adds the expenses set to 0
        userInput = userInput + 0;
        boats.add(makeBoat(userInput));

        // Sends user back to menu
        menuInput(boats);





    } // End of addBoat method

    /**
     * Removes a boat based on user input
     * @param boats ArrayList of Boat objects
     */

    private static void removeBoat(ArrayList<Boat> boats) {
        String nameInput;

        keyboard.nextLine();

        // Prompts user for which boat they want to remove

        System.out.print("Which boat do you want to remove? ");

        nameInput = keyboard.nextLine();


        int index = 0;
        boolean found = false;

        // Check if boat exists
        for (index = 0; index < boats.size(); index++) {
            Boat currentBoat = boats.get(index);

            if (nameInput.equalsIgnoreCase(currentBoat.getName())) {

                boats.remove(currentBoat);
                found = true;
                break;

            }
        }

        if (!found) {
            System.out.println("Could not find boat " + nameInput);
        }

        // Sends user back to menu
        menuInput(boats);

    } // End of removeBoat method


    /**
     * Requests permission to spend money subject to the Commodore's policy
     * @param boats ArrayList of Boat objects
     */

    private static void requestPermission(ArrayList<Boat> boats) {
        String nameInput;

        keyboard.nextLine();

        // Gets user input
        System.out.print("Which boat do you want to spend on? ");
        nameInput = keyboard.nextLine();

        int index = 0;
        boolean found = false;

        // Checks if boat exists
        for (index = 0; index < boats.size(); index++) {
            Boat currentBoat = boats.get(index);

            if (nameInput.equalsIgnoreCase(currentBoat.getName())) {

                found = true;
                // Calls spend on boat method
                spendOnBoat(index, nameInput, boats);
                break;

            }
        }

        if (found == false) {
            System.out.println("Could not find boat " + nameInput);

            // Sends user back to menu
            menuInput(boats);

        }

    } // End of requestPermission method

    /**
     * Spends on boat according to the Commodore's policy
     * @param boatIndex Index of boat the user wants to spend on
     * @param name Name of boat the user wants to spend on
     * @param boats ArrayList of Boat objects
     */

    private static void spendOnBoat(int boatIndex, String name, ArrayList<Boat> boats) {


        double amountRequested;
        double boatCost = boats.get(boatIndex).getPrice();
        double currentExpenses = boats.get(boatIndex).getExpenses();

        // Gets user input

        System.out.print("How much do you want to spend? ");
        amountRequested = keyboard.nextDouble();


        double totalAmountSpent = currentExpenses + amountRequested;

        // Checks if the amount abides by the Commodore's policy
        if (totalAmountSpent > boatCost) {
            double amountRemaining = boatCost - currentExpenses;
            System.out.printf("Expense not permitted, only $%.2f left to spend.%n", amountRemaining);
        } else {

            boats.get(boatIndex).updateExpenses(amountRequested);
            System.out.printf("Expense authorized, $%.2f spent.%n", totalAmountSpent);
        }

        // Sends user back to menu input
        menuInput(boats);

    } // End of spendOnBoat method


    /**
     * Sends an exit message to the user
     * @param boats ArrayList of Boat objects
     */

    private static void exitProgram(ArrayList<Boat> boats) {
        // Saves data to DB file
        saveFleetDataToDB(boats);

        System.out.println("Exiting Program");

    }

}
