import java.io.*;                // Access to filewriter/reader
import java.util.Scanner;        // Access to scanner

public class TestPoly {

   public static void main(String[] args) throws IOException {
      
      Scanner keyboard = new Scanner(System.in);
      
      // variables to drive Polynomial Class
      Polynomial operator = new Polynomial();
      String          one = "";
      String          two = "";
      String   userOutput = "";
      int           input = 0;
      String         fileName = "PolynomialGurka.txt";
            
      // user chooses how they would like to input data
      //do {    
      
         FileInputStream file    
               = new FileInputStream(fileName);
         BufferedWriter writer 
               = new BufferedWriter(new FileWriter("PolynomialResults.txt"));
         Scanner inputFile = new Scanner(file);
      
         System.out.println("What would you like to do? (enter a number)\n" +
                            "1) Enter your own data\n" +
                            "2) Use data from PolynomialGurka.txt\n" +
                            "3) Use data from PolynomialShapiro.txt\n" +
                            "4) Exit the program");
                            
         input = Integer.parseInt(keyboard.nextLine());
         // user input
         if (input == 1) {
            System.out.println("Which operation for entering user data?" +
                               "1) Add two polynomials?\n" +
                               "2) Evaluate a polynomial with an x value?");
            
            input = Integer.parseInt(keyboard.nextLine());
            // add polynomials with user input
            if (input == 1) {
               System.out.println("Enter first polynomial:");
               one = keyboard.nextLine();
               System.out.println("Enter second polynomial:");
               two = keyboard.nextLine();
               
               // add and print polynomial data
               operator = operator.addPoly(one, two);
               userOutput += "operation:\r\n" + 
                         "  add " + one + "\r\n" +
                         "  and " + two + "\r\n" +
                         "  result: " + operator.printPoly() +
                         "\r\n";
               System.out.println(userOutput);            
            }
            // evaluate polynomial with user input
            if (input == 2) {
               System.out.println("Enter polynomial:");
               one = keyboard.nextLine();
               System.out.println("Enter x value:");
               int x = Integer.parseInt(keyboard.nextLine());
               operator.readPoly(one);
               
               // evaluate and print results
               userOutput += "operation:\r\n" + 
                         "  evaluate " + operator.printPoly() + "\r\n" +
                         "  result: when x = " + x +
                         ", y = " + operator.evaluatePoly(x) +
                         "\r\n";
               System.out.println(userOutput);
            }                            
         }
         // file input
         if (input == 2) {
            fileName = "PolynomialGurka.txt";
         }
         if (input == 3) {
            fileName = "PolynomialShapiro.txt";
         }

            
            writer.write("John Shapiro\r\n" +
                         "Comp Sci 2 Section MW\r\n");
   
            // Read, process, and write processed data to outputDataFile
            while (inputFile.hasNextLine() &&
                  (input == 2 || input == 3)) {
               String line   = inputFile.nextLine();
               String       output = "";
               
               // add two polynomials and print results
               if (line.equals("add")) {
                  one = inputFile.nextLine();
                  two = inputFile.nextLine();
                  
                  // add and print polynomial data
                  operator = operator.addPoly(one, two);
                  output += "operation:\r\n" + 
                            "  add " + one + "\r\n" +
                            "  and " + two + "\r\n" +
                            "  result: " + operator.printPoly() +
                            "\r\n";
                  System.out.println(output);
                  writer.write(output);
               } // end add and print
               
               // evalute polynomial at x
               else if (line.equals("evaluate")) {
                  one = (inputFile.nextLine());
                  operator.readPoly(one);
                  int x = Integer.parseInt(inputFile.nextLine());
                  
                  // evaluate and print results
                  output += "operation:\r\n" + 
                            "  evaluate " + operator.printPoly() + "\r\n" +
                            "  result: when x = " + x +
                            ", y = " + operator.evaluatePoly(x) +
                            "\r\n";
                  System.out.println(output);
                  writer.write(output);
                   
               } // end evaluate and print
      
               operator.deletePoly();
            } // end while loop      
      
         // exit command used
         if (input == 4) {
            System.exit(0);         // exit program
         }
         // prompt for correct input
         else {
            System.out.println("Please enter numbers 1 through 4 only!");
         }
         writer.close();
         
      //} while (input != 4); // exit if user enters 4
      
      System.exit(0); // exit program
      
   } // End Main
} // end TestPoly class