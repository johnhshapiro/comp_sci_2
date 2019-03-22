import java.io.*;
import java.util.Scanner;

public class StaticBinaryTranslator {
   
   static Scanner keyboard =
              new Scanner(System.in);  // User input via keyboard
   static boolean valid = true;        // Flag for marker validity
   static int     numInputs = 0;       // Number of inputs to be processed
   static int     decValue = 0;        // Decimal value of processed binary #
   static String  userVal = "";        // User and file inputs stored here
   
//*********************************************************
   // Explain program to user
   public static void printInfo(){
   
      System.out.println("Binary Number Translator\n" +
                         "Enter an 8 bit string (BINARY ONLY).\n" +
                         "Valid strings will be output as a decimal number.\n");
   } // end printInfo

//*********************************************************
   // Prompt for user inputs
   public static void processUserNumbers(){
    
      System.out.print("Enter # of values to be processed:");  
      numInputs = Integer.parseInt(keyboard.nextLine());
      
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
   public static void validateNumber(){
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
   public static void evaluateNumber(){
      //***CHANGE OF BASE FOR EC***
      int exp = 0;
      decValue = 0;
      for (int i = userVal.length() - 1; i >= 0; i--) {
         if (userVal.charAt(i) == '1') {
            decValue += Math.pow(2,exp);
         }
         exp++;
      }
   } // end evaluateNumber

//*********************************************************
   // Process binary string(s) from file
   public static void processFileNumbers(){

   } // end processFileNumbers

// BEGIN PROGRAM EXECUTION*********************************
   public static void main(String[] args) {

      printInfo();
      processUserNumbers();
   } // end main
} // end class