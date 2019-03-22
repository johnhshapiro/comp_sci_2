/* John Shapiro
   Section MW
   
   Program Name - HashingDriver.java
   
   Description - HashingDriver.java is an interactive program that allows a
      user to build a hash table of cars from a file of their choosing. The
      user can then search for cars by VIN. Search results are printed to the
      console.
      The input file must contain only single lines of Strings in the form
      "make, VIN, model, year, color, license#" in order for the hash table
      to be created successfully.
         
   Methods:
      main - allows users to create and search entries in a hash table of cars
*/

import java.io.*;                         // Access to filewriter/reader
import java.util.Scanner;                 // Access to scanner
import java.util.InputMismatchException;  // Access to this exception

public class HashingDriver {
   
   /* main - the menu that allows users to create and search entries in a car
         database */
   //*********************************************************************
   public static void main(String[] args)
         throws IOException, FileNotFoundException {
   
      String  inputFile = "cars.txt";
      String outputFile = "inCaseOfFirePrintHere.txt";
      Scanner keyboard = new Scanner(System.in);
      
      // query variables
      int    vin = 1000;
      
      //begin program execution
      System.out.println(
            "This program allows you to search for a car by its\n" +
            "3-digit VIN. You will first be prompted for the name\n" +
            "of an output file (choose a name!). You will then be\n" +
            "prompted for the name of a build file. This is a .txt\n" +
            "that must already exist.\n");
         
         // file name entry
         System.out.print(
             "Enter output file name: ");
         outputFile = keyboard.nextLine();
         if (!outputFile.contains(".txt")) {
            outputFile = outputFile + ".txt";
         }
         CarHashTable hashing = new CarHashTable(outputFile, 7);
         hashing.printHeader(hashing.writer);
         
         System.out.print(
               "Build file name: ");      
         inputFile = keyboard.nextLine();
         // try catch block adds '.txt'.
         // If that doesn't fix the exception, a default file is used
         try {
            hashing.buildTable(inputFile);
         } catch (FileNotFoundException e) {
            try {
            hashing.buildTable(inputFile + ".txt");
            } catch (FileNotFoundException f) {
            System.out.println("Invalid file name.\n" +
                               "Using default build file: cars.txt\n"); 
            } // end default file used
         } // end attempt to add '.txt'
         
      // begin searching VINs
      do {
         System.out.print("Enter a 3-digit VIN (leading 0's may be excluded)\n" +
                          "or 0 to exit: ");
         // if there are any non-integers in input, user reenters input.
         try {
            vin = keyboard.nextInt();
         } catch (InputMismatchException e) {
            System.out.println("\nPlease enter integers only.\n");
         } // end try/catch
         keyboard.nextLine(); // collecting excess garbage from integer
         
         // check for valid VIN
         if (vin <= 1 || vin > 999) {
            System.out.println("VIN must be between 001 and 1000.\n");
         }
         else {
            System.out.println("VIN: " + vin + "\n");
            hashing.searchVIN(vin);
         }
      } while(vin != 0); // user enters 0 to exit
      hashing.writer.close(); // close writer  
   } // end main method
} // end HashingDriver class