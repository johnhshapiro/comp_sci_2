/* John Shapiro
   Section MW
   
   Program Name - CarDriver.java
   
   Description - CarDriver.java is an interactive program that allows a user
      to build a database of cars from a file of their choosing. The user can
      then choose query types from a menu, and then perform searches based on
      manually input data. All search data will printed to an external .txt
      file whose name will be chose by the user.
      The input file must contain only single lines of Strings in the form
      "make, model, year, color, license#" in order to produce a database.
      Messages produced by this file will only print to the system console.
      All file data is printed using methods from Car and CarDB.
         
   Methods:
      main - allows users to create and search entries in a car
         database
*/

import java.io.*;                         // Access to filewriter/reader
import java.util.Scanner;                 // Access to scanner
import java.util.InputMismatchException;  // Access to this exception

public class CarDriver {
   
   /* main - the menu that allows users to create and search entries in a car
         database */
   //*********************************************************************
   public static void main(String[] args)
         throws IOException, FileNotFoundException {
   
      String  inputFile = "cars.txt";
      String outputFile = "inCaseOfFirePrintHere.txt";
      int         query = -1;
      Scanner keyboard = new Scanner(System.in);
      
      // query variables
      String make,
             color,
             partial;
      int    year1,
             year2;
      
      //begin program execution
      System.out.println(
            "Welcome! This program searches for specific cars\n" +
            "based on user specified queries. Please enter queries\n" +
            "as specified otherwise the program will become quite\n" +
            "upset with you.\r" +
            "Data from all queries will be printed to a text file with\n" +
            "a name specified by the user (that's you!). The text file\n" +
            "will be located in the same file as this program.\n\n" +
            "Enter the names of the database build file and output file.\n" +
            "   (Must include file extension--> .txt)\n");
         
         System.out.print(
             "   Enter output file name: ");
         outputFile = keyboard.nextLine();
         if (!outputFile.contains(".txt")) {
            outputFile = outputFile + ".txt";
         }
         CarDB database = new CarDB(outputFile);         
         
         System.out.print(
               "   Build file name: ");      
         inputFile = keyboard.nextLine();
         // try catch block adds '.txt'.
         // If that doesn't fix the exception, a default file is used
         try {
            database.buildCarDB(inputFile);
         } catch (FileNotFoundException e) {
            try {
            database.buildCarDB(inputFile + ".txt");
            } catch (FileNotFoundException f) {
            System.out.println("Invalid file name." +
                               "Using default database: cars.txt"); 
            } // end default file used
         } // end attempt to add '.txt'
            
      boolean noMoreQueries = false;
      
      // begin making queries
      do {
         System.out.println("\nChoose a query type (or exit program):\n" +
                            "  1) Make\n" +
                            "  2) Model\n" +
                            "  3) Color\n" +
                            "  4) Year\n" +
                            "  5) License plate number\n" +
                            "  6) Partial license plate number\n" +
                            "  7) Make and color\n" +
                            "  8) Color and partial license plate number\n" +
                            "  9) Range of years\n" +
                            " 10) Print all cars to output file\n" +
                            "  0) Exit program\n");

         // if there are any non-integers in input, user redoes input.
         try {
            query = keyboard.nextInt();
         } catch (InputMismatchException e) {
            System.out.println("Please enter integers from 0-10 only.");
         }
         keyboard.nextLine();
         System.out.println();
          
         // list actions for list type one
         switch(query) {
            case 1: // make
               System.out.print("Enter make: ");
               database.searchMake(keyboard.nextLine());
               break;
               
            case 2: // model
               System.out.print("Enter model: ");
               database.searchModel(keyboard.nextLine());
               break;

            case 3: // color
               System.out.print("Enter color: ");
               database.searchColor(keyboard.nextLine());              
               break;
               
            case 4: // year
               System.out.print("Enter year: ");
               database.searchYear(keyboard.nextInt());                 
               break;
               
            case 5: // plate
               System.out.print("Enter license plate #: ");
               database.searchPlate(keyboard.nextLine());              
               break;
               
            case 6: // partial plate
               System.out.print("Enter partial plate #: ");
               database.searchPartialPlate(keyboard.nextLine());              
               break;
               
            case 7: // make and color
               System.out.print("Enter make: ");
               make = keyboard.nextLine();
               System.out.print("Enter color: ");
               color = keyboard.nextLine();
               database.makeAndColor(make, color);
               break;
               
            case 8: // color and partial
               System.out.print("Enter color: ");
               color = keyboard.nextLine();
               System.out.print("Enter partial plate #: ");
               partial = keyboard.nextLine();
               database.colorAndPartial(partial, color);
               break;
               
            case 9: // range of years
               System.out.print("Enter first year: ");
               year1 = keyboard.nextInt();
               System.out.print("Enter second year: ");
               year2 = keyboard.nextInt();
               database.searchYearRange(year1, year2);
               break;
               
            case 10: // print all
               System.out.print("All cars in database printed to file!");
               database.printCarDB();
               break;
               
            case 0:
               System.out.print("Your queries have been printed to "
               + outputFile);
               noMoreQueries = true;
               break;
               
            default:
               System.out.println("Invalid option, please choose again!");
         }
      } while(!noMoreQueries);
      
      database.writer.close();
      
   
   } // end main method

} // end CarDriver class