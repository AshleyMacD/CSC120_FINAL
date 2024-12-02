import java.util.Scanner;

public class DentalRecordsAshleyMacDougal {
    public static final Scanner keyboard = new Scanner(System.in);
    public static final int MAX_FAMILY = 6;
    public static final int MAX_TEETH = 8;



    public static void main(String[] args) {


        // Printing intro message
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        // Defining blank variable that user will input the family size into
        int sizeOfFamily;

        // User inputs family size
        System.out.print("Please enter number of people in the family: ");
        sizeOfFamily = keyboard.nextInt();


        // Checking the input was valid. If not valid it will keep asking until it is valid.
        while (sizeOfFamily > MAX_FAMILY || sizeOfFamily < 0) {
            System.out.print("Invalid number of people, try again: ");
            sizeOfFamily = keyboard.nextInt();
        }


        // Defining a string array for family names with the size based on the input number size of the family
        String[] familyNames = new String[sizeOfFamily];

        // Calling inputFamilyData method passing in the familyNames array as a parameter
        // Setting a new 3D array equal to the return value of the method
        char [][][] familyTeeth = inputFamilyData(familyNames);

        // Now we call the menu input
        System.out.println();
        menuInput(familyNames, familyTeeth);



    } // End of the main method

    private static char[][][] inputFamilyData(String[] names) {

        // Creating the three-dimensional array that will be used within this method.
        char[][][] teethInfo = new char[names.length][2][];

        // For loop cycling through the number of family members input. Will ask for their Name, Uppers and Lowers
        int indexFamilyName;
        for (indexFamilyName = 0; indexFamilyName < names.length; indexFamilyName++) {

            // Asking for user input for the name of family member
            System.out.print("Please enter the name for family member " + (indexFamilyName + 1) + ": " );
            names[indexFamilyName] = keyboard.next();

            // Nested for loop to ask for their Uppers and Lowers (specific to each person)
            for (int numRows = 0; numRows < teethInfo[indexFamilyName].length; numRows++) {



                // creating a variable for jaw
                String jaw = "";
                if (numRows == 0) {
                    jaw = "uppers";
                } else {
                    jaw = "lowers";
                }

                // Requesting user input (teeth in uppers/lowers)
                System.out.print("Please enter the " + jaw  + " for " + names[indexFamilyName] + ": ");
                String teethInput = keyboard.next().toUpperCase();

                // Creating a boolean value to make sure the user input is proper
                boolean validTeeth = false;

                // Checking to see if the teeth meet the requirements
                while (!validTeeth) {
                    // Checking if teeth are > 8 (the max)
                    if (teethInput.length() > MAX_TEETH) {
                        System.out.print("Too many teeth, try again: ");
                        teethInput = keyboard.next().toUpperCase();
                    } else {
                        boolean validCharacters = true;

                        // A for loop to check if the teeth letters are valid (only I B or M)
                        int teethIndex;
                        for (teethIndex = 0; teethIndex < teethInput.length(); teethIndex++) {
                            if (teethInput.charAt(teethIndex) != 'I' && teethInput.charAt(teethIndex) != 'B' && teethInput.charAt(teethIndex) != 'M') {
                                System.out.print("Invalid teeth type, try again: ");
                                teethInput = keyboard.next().toUpperCase();
                                validCharacters = false;
                            } // End of the if statement

                        } // End of the for loop

                        // If both conditions are true, the loop can exit
                        if (validCharacters) {
                            validTeeth = true;
                        } // End of the if statement

                    } // End of the else statement



                } // End of the while loop checking if the teeth input are valid


                // Setting the third dimension of the array the same as the length of inputted teeth
                teethInfo[indexFamilyName][numRows] =  new char[teethInput.length()];

                // For loop to put the teeth into the array
                int numColumn;
                for (numColumn = 0; numColumn < teethInput.length(); numColumn++) {
                    teethInfo[indexFamilyName][numRows][numColumn] = teethInput.charAt(numColumn);
                } // End of the for loop inputting teeth

            } // End of the nested for loop

        } // End of the for loop

        // Returning the three-dimensional array with all the information
        return teethInfo;

    } // End of the inputFamilyData method

    private static void menuInput(String[] names, char[][][] teethInfo) {

        // Creating a variable for the user's menu input
        char userInput;

        // Printing out the menu options and asking for user input
        System.out.print("(P)rint, ");
        System.out.print("(E)xtract, ");
        System.out.print("(R)oot, ");
        System.out.print("e(X)it:  ");
        userInput = keyboard.next().toLowerCase().charAt(0);

        // Checking if the user input a proper letter, and sending them to a different method depending on input.
        if (userInput == 'p') {
            printRecord(names, teethInfo);

        } else if (userInput == 'e') {
            extractTooth(names, teethInfo);

        } else if (userInput == 'r') {
            printRootReport(names, teethInfo);

        } else if (userInput == 'x') {
            exitProgram();

        } else {
            // If an invalid input is found, the user will be re-prompted to input again
            System.out.println("Invalid menu option, try again.");
            menuInput(names, teethInfo);
        }

    } // End of the menuInput method

    private static void printRecord(String[] names, char[][][] teethInfo) {
        // Extra new line since we are going from character input to integers
        System.out.println();

        // Creating a for loop to print out each family member's teeth
        int namesIndex;
        for (namesIndex = 0; namesIndex < names.length; namesIndex++) {
            System.out.println(names[namesIndex]);

            int rowCounter;
            int columnCounter = 0;

            // Looping through the uppers and the lowers
            for (rowCounter = 0; rowCounter < 2; rowCounter++) {

                String jaw = "";
                if (rowCounter == 0) {
                    jaw = "   Uppers: ";
                } else {
                    jaw = "   Lowers: ";
                }

                System.out.print(jaw);


                // Printing out each tooth with a number indicating which tooth it is
                for (columnCounter = 0; columnCounter < (teethInfo[namesIndex][rowCounter].length); columnCounter++) {
                    System.out.print(columnCounter + 1 + ":");
                    System.out.print(teethInfo[namesIndex][rowCounter][columnCounter] + " ");

                } // End of the tooth printing for loop
                System.out.println();
            } // End of the jaw for loop

        } // End of the family member for loop
        System.out.println();

        // Sending the user back to the menu
        menuInput(names, teethInfo);

    } // End of the printRecord method

    private static void extractTooth(String[] names, char[][][] teethInfo) {

        // Asking the user which family member they want to extract a tooth from
        System.out.print("Which family member: ");

        // Setting chosenIndex equal to the return value of findFamilyMember method while passing the names array through
        int chosenIndex = findFamilyMember(names);

        // Checking if the chosen index lines up with a valid family member
        // If a valid family member is not found then the user is sent back to the findFamilyMember method
        while (chosenIndex == -1) {
            chosenIndex = findFamilyMember(names);
        }

        // Creating a variable for the user to input their menu choice
        char userLayerInput;
        System.out.print("Which tooth layer (U)pper or (L)ower: ");
        userLayerInput = keyboard.next().toUpperCase().charAt(0);


        // Checking to make sure the user input only U or L
        // If a different character is found, the user will be asked again
        while (userLayerInput != 'U' && userLayerInput != 'L') {
            System.out.print("Invalid layer, try again: ");
            userLayerInput = keyboard.next().toUpperCase().charAt(0);
        } // End of the while loop


        // Creating a variable for user input
        int userToothNumber;
        // if else statement to check if the user wants to extract a tooth from the uppers or lowers
        if (userLayerInput == 'U') {
            // Asking the user for tooth input
            int numberOfTeeth = teethInfo[chosenIndex][0].length;
            System.out.print("Which tooth number: ");
            userToothNumber = keyboard.nextInt();
            keyboard.nextLine();

            // Checking if the tooth is in the range, if it isn't the user is re-prompted for input
            while (userToothNumber > numberOfTeeth || userToothNumber < 0) {
                System.out.print("Invalid tooth number, try again: ");
                userToothNumber = keyboard.nextInt();
                keyboard.nextLine();
            } // End of the while loop

            // Checking if the tooth is 'M' missing
            while (teethInfo[chosenIndex][0][userToothNumber - 1] == 'M') {
                System.out.print("Missing tooth, try again: ");
                userToothNumber = keyboard.nextInt();
            } // End of the while loop

            // Setting the tooth to 'M' to indicate it has been extracted
            teethInfo[chosenIndex][0][userToothNumber - 1] = 'M';

            // if else statement to check if the user wants to extract a tooth from the uppers or lowers
        } else {

            // Asking the user for tooth input
            int numberOfTeeth = teethInfo[chosenIndex][1].length;
            System.out.print("Which tooth number: ");
            userToothNumber = keyboard.nextInt();

            // Checking if the tooth is in the range, if it isn't the user is re-prompted for input
            while (userToothNumber > numberOfTeeth || userToothNumber < 0) {
                System.out.print("Invalid tooth number, try again: ");
                userToothNumber = keyboard.nextInt();
                keyboard.nextLine();
            } // End of the while loop

            // Checking if the tooth is 'M' missing
            while (teethInfo[chosenIndex][1][userToothNumber - 1] == 'M') {
                System.out.print("Missing tooth, try again: ");
                userToothNumber = keyboard.nextInt();

            } // End of the while loop

            // Setting the tooth to 'M' to indicate it has been extracted
            teethInfo[chosenIndex][1][userToothNumber - 1] = 'M';
            System.out.println("Tooth " + userToothNumber + " has been removed.");
        } // End of the if else statement asking for upper or lower jaw


        System.out.println();
        // Sending the user back to the menu
        menuInput(names, teethInfo);

    } // End of the extractTooth method

    private static int findFamilyMember(String[] names) {

        // Creating a variable to get user input for the name
        String userNameInput;
        int nameIndex;
        int chosenIndex = -1;

        // Getting user input
        userNameInput = keyboard.next();

        // Checking if the name exists in the array
        for (nameIndex = 0; nameIndex < names.length; nameIndex++) {
            if (names[nameIndex].equalsIgnoreCase(userNameInput)) {
                // Setting chosenIndex equal to the nameIndex of the name
                chosenIndex = nameIndex;

            } // End of the if statement
        } // End of the for loop

        // If the person doesn't exist in the family, the user is re prompted
        if (chosenIndex == -1) {
            System.out.print("Invalid family member, try again: ");
        } // End of the if statement

        // Returning chosen index to be used in the extractTooth method
        return chosenIndex;

    } // End of the findFamilyMember method

    private static void printRootReport(String[] names, char[][][] teethInfo) {

        // Creating variables for the different teeth types
        int incisors = 0;
        int bicuspids = 0;
        int missingTeeth = 0;

        // Creating different index variables to loop through the three-dimensional array
        int namesIndex;
        int rowIndex;
        int toothIndex;
        char tooth;

        // Creating a for loop to loop through the family members in the 3D array
        for (namesIndex = 0; namesIndex < names.length; namesIndex++) {
            // Creating a for loop to loop through the rows in the 3D array
            for (rowIndex = 0; rowIndex < 2; rowIndex++) {
                // Creating a for loop to loop through the teeth in the 3D array
                for (toothIndex = 0; toothIndex < teethInfo[namesIndex][rowIndex].length; toothIndex++) {
                    tooth = Character.toUpperCase(teethInfo[namesIndex][rowIndex][toothIndex]);

                    // Checking what letter the tooth is then adding +1 to the correct variable
                    if (tooth == 'I') {
                        incisors++;
                    } else if (tooth == 'B') {
                        bicuspids++;
                    } else if (tooth == 'M') {
                        missingTeeth++;
                    } // End of the if else if branch

                } // End of the toothIndex for loop

            } // End of the rowIndex for loop

        } // End of the namesIndex for loop


        // Creating a variable for the discriminant in the quadratic formula used to find the two roots
        double discriminant;
        // Doing the math for the discriminant
        discriminant = Math.pow(bicuspids, 2) + 4 * incisors * missingTeeth;

        // Creating variables for the roots
        double firstRoot;
        double secondRoot;

        // Doing the math for the roots using the rest of the quadratic formula
        firstRoot = (-bicuspids + Math.sqrt(discriminant)) / (2 * incisors);
        secondRoot = (-bicuspids - Math.sqrt(discriminant)) / (2 * incisors);

        // Printing the root canals
        System.out.printf("One root canal at    %.2f%n", firstRoot);
        System.out.printf("Another root canal at   %.2f%n", secondRoot);


        System.out.println();
        // Sending the user back to menuInput
        menuInput(names, teethInfo);





    } // End of the printRootReport method

    private static void exitProgram() {
        System.out.println();
        // Printing the exit message
        System.out.println("Exiting the Floridian Tooth Records :-)");
    } // End of the exitProgram method


} // End of the DentalRecordsMidtermAshleyMacDougal Class


