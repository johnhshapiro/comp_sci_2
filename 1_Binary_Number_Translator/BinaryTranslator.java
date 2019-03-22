/*  Program name: BinaryTranslator.java
    Author: John Shapiro
    Date: January 2018
    
    Description: Binary translator reads single lines from an input file and
         and decides if the number is a valid 8 bit binary number. If so, the
         program prints out the decimal form of the number.
    
    Input: input from ".txt" file named by the user.
    Output: screen (console) and output file
*/

import java.io.*;
import java.util.Scanner;

public class BinaryTranslator {
   
   // Class variables
   private Scanner keyboard;  // User input via keyboard
   private boolean valid;     // Flag for marker validity
   private String  userVal;   // User and file inputs stored here
   private String  userFile;  // In put file name entered by user
   private int     numInputs; // Number of inputs to be processed
   private int     decValue;  // Decimal value of processed binary #
   
   // File writer and reader
   private File        inputDataFile;  // Input file
   private Scanner     inputFile;      // Read from file
   private FileWriter  outputDataFile; // Write to file
   private PrintWriter outputFile;     // Print to file

	// BinaryTranslator Method
   public BinaryTranslator () throws IOException {

	   // instantiate class variables
      valid     = true;
      userVal   = "";
      numInputs = 0;
      decValue  = 0;
      
      keyboard = new Scanner (System.in);
      
      // instantiate input and output files
      System.out.print("Enter input file name: ");
      userFile       = keyboard.nextLine();
      inputDataFile  = new File(userFile);
      // inputDataFile  = new File("binary_input.txt");
      inputFile      = new Scanner(inputDataFile);
      outputDataFile = new FileWriter("binary_output.txt");
      outputFile     = new PrintWriter(outputDataFile);
      
   }// end BinaryTranslator
   
//*********************************************************
   // Explain program to user
   public void printInfo(){
   
      System.out.println("Binary Number Translator\n" +
                         "Reads 8 bit binary strings from input file.\n" +
                         "Valid strings will be output as a decimal number.\n");
   } // end printInfo

//*********************************************************
   // Prompt for user inputs
   public void processUserNumbers(){
    
      System.out.print("Enter # of values to be processed:");  
      numInputs = Integer.parseInt(keyboard.nextLine());
      if (numInputs == 0) {
         System.out.println("No user inputs? GOODBYE!");
         System.exit(0);
      }
      for (int i = 0; i < numInputs; i++) {
         /**Issue - Loop was ran once before user input.
            - Solved: Part of the value from the
              previously entered integer was being
              read as the first entered value.
         */
         System.out.print("string: ");
         userVal = keyboard.nextLine();
         validateNumber();
         
         if (valid == true) {
            System.out.println("status: valid");
            evaluateNumber();
            System.out.println("decimal value: " +
                               decValue);
         }
         else {
            System.out.println("status: invalid");
         }
      }
   }

//*********************************************************
   // Checks number validity (8 bits, 1s and 0s only)
   public void validateNumber(){
      valid = true;
      
      // check input length
      if (userVal.length() == 8) {
         valid = true;
         // check each digit for 1 or 0
         for (int i = 0; i < 8; i++) {
            if (userVal.charAt(i) != '1' && userVal.charAt(i) != '0') {
               valid = false;
            }
         } // end for loop
      }
      else {
         valid = false;
      }
   } // end validateNumber

//*********************************************************
   // Convert binary value to decimal value
   public void evaluateNumber(){
      //***CHANGE OF BASE FOR EC***
      int exp = 0;         // Value of exponent
      decValue = 0;        // Reset decimal value
      
      // loop calculates each digit
      for (int i = userVal.length() - 1; i >= 0; i--) {
         if (userVal.charAt(i) == '1') {
            decValue += Math.pow(2,exp);
         }
         exp++;
      } // end loop
   } // end evaluateNumber

//*********************************************************
   // Process binary string(s) from file
   public void processFileNumbers() throws IOException {
   
      outputFile.println("John Shapiro\r\n" +
                         "Binary Translator\r\n");
      
      while (inputFile.hasNext()) {
         userVal = inputFile.nextLine();
         outputFile.println("string: " + userVal);
         validateNumber();
         
         if (valid == true) {
            outputFile.println("status: valid");
            evaluateNumber();
            outputFile.println("decimal value: " +
                               decValue);
         }
         else {
            outputFile.println("status: invalid");
         }
      }
      
   outputFile.close();
   } // end processFileNumbers

// BEGIN MAIN METHOD***************************************
   public static void main(String[] args) throws IOException {
   
      BinaryTranslator translate = new BinaryTranslator();
      translate.printInfo();
      translate.processFileNumbers();
      System.exit(0);
   } // end main
} // end class