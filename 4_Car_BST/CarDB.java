/* John Shapiro
   Section MW
   
   Program Name - CarDB.java
   
   Description - CarDB.java is a class that creates CarDB objects. A CarDB
      object is a primitive database of Car Class objects implemented as a
      BST. The BST is sorted using the make field of a Car. Each node in a
      CarDB BST has a make field and points to a singly linked list containing
      car objects whose makes matche that of the Make node.
      This class contains methods to do search for Cars based on a variety
      of Car traits (i.e. make, model, year, color, plate #). All search
      data prints to an external .txt file with a message that identifies
      which data was searched for.
         
   Methods:
      addCar - adds a car to the database
      buildCarDB - reads input file and uses addCar to build the database
      print - prints a string to the output file
      printCarDB - print all cars to output file
      searchMake - print all cars with specified make
      searchModel - print all cars with specified model
      searchColor - print all cars with specified color
      searchYear - print all cars with specified year
      searchLicense - print a car with specified license #
      searchPartial - print all cars containing specified partial license #
      makeAndColor - print all cars with specified make AND color
      colorAndPartial - print all cars with specified color AND partial
      searchYearRange - print all cars within specified year range (incluseive)
      parseLicense - returns a more search friendly license plate number
*/

import java.io.*;                // Access to filewriter/reader
import java.util.Scanner;        // Access to scanner

public class CarDB {

   private Make root;
   private int count;
   BufferedWriter writer; 
   
   public CarDB(String outputFileName) throws IOException {
      root = new Make();
      count = 0;
      writer = new BufferedWriter(
            new FileWriter(outputFileName));
            
      print("John Shapiro\r\nComp Sci 2 Section MW\r\nProject: CarDB\r\n");
   }
   
   // addCar - to add a single car to the database
   //*********************************************************************
   // helper method
   public void addCar(String data) {
      
      CarNode tempCar = new CarNode(new Car(data));
      addCar(root, tempCar);
      
   } // end addCar helper method
      // addCar recursive method
      private void addCar(Make ref, CarNode car) {

      // adds CarNode to the list of makes if the makes match
      if (ref.make.compareTo(car.data.make) == 0) {
         car.next = ref.head;
         ref.head = car;
         return;
      }
      // go left if make is less than
      if (car.data.make.compareTo(ref.make) < 0) {
         if (ref.left == null) {
            Make temp = new Make();
            ref.left = temp;
            ref.left.make = car.data.make;
            ref.left.head = car;
            return;
         }
         addCar (ref.left, car);
         return;
      }
      // go right if make is greater than
      if (car.data.make.compareTo(ref.make) > 0) {
         if (ref.right == null) {
            Make temp = new Make();
            ref.right = temp;
            ref.right.make = car.data.make;
            ref.right.head = car;
            return;
         }
         addCar (ref.right, car);
         return;
      }
      
      } // end addCar recursive method
   
   /* buildCarDB - uses addCar to build the database from input file */
   //*********************************************************************
   public void buildCarDB(String inputName) throws FileNotFoundException {
      
      // input file and scanner
      FileInputStream file    
                  = new FileInputStream(inputName);
      Scanner inputFile
            = new Scanner(file);
   
      CarNode tempCar = new CarNode(new Car(inputFile.nextLine()));
      root.make = tempCar.data.make;
      root.head = tempCar;
      
      while(inputFile.hasNextLine()) {
         addCar(inputFile.nextLine());
      }
   } // end buildCarDB
   
   /* printCarDB - prints a list of all cars in database
         (sorted by make ) */
   //*********************************************************************
   public void printCarDB() throws IOException {   
   
      print("List of all vehicles in database:\r\n");
      count = 0;
      printCarDB(root);
      print("Found " + count + " match[es].\r\n");
      System.out.println(count + " record[s] printed to output file.\r\n");
      
   } // end printCarDB helper method
      // begin printCarDB recursive method
      private void printCarDB(Make ref) throws IOException {
      
         if (ref.left != null) {
            printCarDB(ref.left);
         }
         CarNode current = ref.head;
         while (current != null) {
            print(current.data.toString());
            current = current.next;
            count++;
         } // end while
         if (ref.right != null) {
            printCarDB(ref.right);
         }      
      } // end printCarDB recursive method
   
   /* searchMake - prints all cars of make to command
         window and output file */
   //*********************************************************************
   public void searchMake(String make) throws IOException {
   
      print("List of " + make + " vehicles in database:\r\n");            
      count = 0; // # of matches
      searchMake(root, make);      
      print("Found " + count + " match[es].\r\n");
      System.out.println(count + " record[s] printed to output file.\r\n");
      
   } // end searchMake helper method
      // begin searchMake recursion
      private void searchMake(Make ref, String make) throws IOException {
         // print all of make once found
         if (make.compareToIgnoreCase(ref.make) == 0) {
            CarNode current = ref.head;
            while (current != null) {
               print(current.data.toString());
               count ++;
               current = current.next;
            }
            return;
         }
         // go left in BST if make is earlier alphabetically
         if (ref.left != null) {
            searchMake(ref.left, make);
         }
         // go right in BST if make is later alphabetically
         if (ref.right != null) {
            searchMake(ref.right, make);
         }
      }
   
   /* searchModel - prints all cars of model to command
         window and output file */
   //*********************************************************************
   public void searchModel(String model) throws IOException {
   
      print("List of " + model + "s in database:\r\n");
      count = 0;
      searchModel(root, model);
      print("Found " + count + " match[es].\r\n");
      System.out.println(count + " record[s] printed to output file.\r\n");
   
   } // end searchModel helper method   
      // begin searchModel recursive method
      private void searchModel(Make ref, String model) throws IOException {
      
         if (ref.left != null) {
            searchModel(ref.left, model);
         }
         CarNode current = ref.head;
         while (current != null) {
            if (model.compareToIgnoreCase(current.data.model) == 0) {
               print(current.data.toString());
               count++;
            }
            current = current.next;
         } // end while
         if (ref.right != null) {
            searchModel(ref.right, model);
         }      
      } // end searchModel recursive method
   
   /* searchYear - prints all cars of year to command window
         and output file */
   //*********************************************************************
   public void searchYear(int year) throws IOException {
   
      print("List of " + year + "s in database:\r\n");
      count = 0;
      searchYear(root, year);
      print("Found " + count + " match[es].\r\n");
      System.out.println(count + " record[s] printed to output file.\r\n");
   
   } // end searchYear helper method   
      // begin searchYear recursive method
      private void searchYear(Make ref, int year) throws IOException {
      
         if (ref.left != null) {
            searchYear(ref.left, year);
         }
         CarNode current = ref.head;
         while (current != null) {
            if (year == current.data.year) {
               print(current.data.toString());
               count++;
            }
            current = current.next;
         } // end while
         if (ref.right != null) {
            searchYear(ref.right, year);
         }
      } // end searchYear
   
   /* searchColor prints all cars of color to command window
         and output file */
   //*********************************************************************
   public void searchColor(String color) throws IOException {
   
      print("List of " + color + " vehicles in database:\r\n");
      count = 0;
      searchColor(root, color);
      print("Found " + count + " match[es].\r\n");
      System.out.println(count + " record[s] printed to output file.\r\n");
   
   } // end searchColor helper method   
      // begin searchColor recursive method
      private void searchColor(Make ref, String color) throws IOException {
      
         if (ref.left != null) {
            searchColor(ref.left, color);
         }
         CarNode current = ref.head;
         while (current != null) {
            if (color.compareToIgnoreCase(current.data.color) == 0) {
               print(current.data.toString());
               count++;
            }
            current = current.next;
         } // end while
         if (ref.right != null) {
            searchColor(ref.right, color);
         }      
   } // end searchColor
   
   /* searchPlate prints car with plate# to command window
         and output file */
   //*********************************************************************
   public void searchPlate(String plate) throws IOException {
   
      String parsedPlate = parseLicense(plate);
      count = 0;
      searchPlate(root, parsedPlate);
      if (count == 0) {
         print("No matches for full plate #: " + plate + "\r\n");
         System.out.println("No matches for full plate #: " + plate + "\r\n");
      }
   
   } // end searchPlate helper method   
      // begin searchPlate recursive method
      private void searchPlate(Make ref, String parsedPlate) throws IOException {
      
         if (ref.left != null) {
            searchPlate(ref.left, parsedPlate);
         }
         CarNode current = ref.head;
         while (current != null) {
            if (parsedPlate.compareToIgnoreCase(current.data.license) == 0) {
               print("Found full license plate # match:\r\n");
               System.out.println("Match found and printed to file!\r\n");
               print(current.data.toString());
               count++;
               return;
            }
            current = current.next;
         } // end while
         if (ref.right != null) {
            searchPlate(ref.right, parsedPlate);
         }
      } // end searchPlate
   
   /* searchPartialPlate - prints all cars with a partial matching plate
         to command window and output file */
   //*********************************************************************
   public void searchPartialPlate(String partial) throws IOException {
   
      count = 0;
      print("Vehicles w/ plate # containing " + partial + ":\r\n");
      partial = parseLicense(partial);
      searchPartialPlate(root, partial);
      print("Found " + count + " match[es].\r\n"); 
      System.out.println(count + " record[s] printed to output file.\r\n");     
   
   } // end searchPartialPlate helper method   
      // begin searchPartialPlate recursive method
      private void searchPartialPlate(Make ref, String partial) throws IOException {
               
         if (ref.left != null) {
            searchPartialPlate(ref.left, partial);
         }
         CarNode current = ref.head;
         while (current != null) {
            if (current.data.license.contains(partial)) {
               print(current.data.toString());
               count++;
            }
            current = current.next;
         } // end while
         if (ref.right != null) {
            searchPartialPlate(ref.right, partial);
         }
      } // end searchPartialPlate
   
   /* makeAndColor - prints all cars of specified make and color to
         command window and output file */
   //*********************************************************************
   public void makeAndColor(String make, String color) throws IOException {
   
      print("List of " + color + " " + make +
                         "s in database:\r\n");
      count = 0;
      makeAndColor(root, make, color);
      print("Found " + count + " match[es].\r\n");
      System.out.println(count + " record[s] printed to output file.\r\n");
   
   } // end makeAndColor helper method   
      // begin makeAndColor recursive method
      private void makeAndColor(Make ref, String make, String color) throws IOException {
      
         if (make.compareToIgnoreCase(ref.make) == 0) {
            CarNode current = ref.head;
            while (current != null) {
            if (color.compareToIgnoreCase(current.data.color) == 0) {
               print(current.data.toString());
               count++;
            }
            current = current.next;
            } // end while
            return;
         }
         if (ref.left != null) {
            makeAndColor(ref.left, make, color);
         }
         if (ref.right != null) {
            makeAndColor(ref.right, make, color);
         }      
   } // end makeAndColor
   
   /* colorAndPartial - prints all cars of specified color and partial
         plate# to command window and output file */
   //*********************************************************************
   public void colorAndPartial(String partial, String color) throws IOException {
   
      count = 0;
      print("List of " + color + " vehicles containing " +
                         "partial plate # " + partial + ":\r\n");
      partial = parseLicense(partial);
      colorAndPartial(root, partial, color);
      print("Found " + count + " match[es].\r\n");    
      System.out.println(count + " record[s] printed to output file.\r\n");  
   
   } // end colorAndPartial helper method   
      // begin colorAndPartial recursive method
      private void colorAndPartial(Make ref, String partial, String color) throws IOException {
               
         if (ref.left != null) {
            colorAndPartial(ref.left, partial, color);
         }
         CarNode current = ref.head;
         while (current != null) {
            if (current.data.license.contains(partial) &&
                color.compareToIgnoreCase(current.data.color) == 0) {
               print(current.data.toString());
               count++;
            }
            current = current.next;
         } // end while
         if (ref.right != null) {
            colorAndPartial(ref.right, partial, color);
         }
      } // end colorAndPartial
   
   /* searchYearRange - prints all cars of between the specified yearts to
         command window and output file (includes specified years) */
   //*********************************************************************
   public void searchYearRange(int low, int high) throws IOException {
   
      print("Vehicles made from " + low + " to " + high +
                         " in database:\r\n");
      count = 0;
      searchyearRange(root, low, high);
      print("Found " + count + " match[es].\r\n");
      System.out.println(count + " record[s] printed to output file.\r\n");
   
   } // end searchyearRange helper method   
      // begin searchyearRange recursive method
      private void searchyearRange(Make ref, int low, int high) throws IOException {
      
         if (ref.left != null) {
            searchyearRange(ref.left, low, high);
         }
         CarNode current = ref.head;
         while (current != null) {
            if (low <= current.data.year &&
                high >= current.data.year) {
               print(current.data.toString());
               count++;
            }
            current = current.next;
         } // end while
         if (ref.right != null) {
            searchyearRange(ref.right, low, high);
         }
      } // end searchyearRangeRange
      
      /* parseLicense - return a version of a car's license that is easier to
            search for */
      //*********************************************************************
      public String parseLicense(String license) {
      
         String parsed = "";
         for (int i = 0; i < license.length(); i++) {
            if (license.charAt(i) != '-'
                && license.charAt(i) != ' ') {
               parsed = parsed + license.charAt(i);
            }
         }
         return parsed.toUpperCase();
         
      } // end parseLicense
      
   /* print - prints in both the command window and the output file */
   //*********************************************************************
   public void print(String printThis) throws IOException {
   
      // the following line can be used for monitoring output while testing
      //System.out.println(printThis);
      writer.write(printThis + "\r\n");
   
   }
      
   //*********************TESTING PURPOSES MAIN METHOD!!!!!!**************
   /*public static void main(String[] args) throws IOException {
   
   CarDB carDB = new CarDB("heresYourName.txt");
   carDB.buildCarDB("cars.txt");
   //carDB.printCarDB();
   carDB.searchPlate(" XPLOR");
   carDB.writer.close();
   
   } // end main*/
   
      /* Make Class - a node object that will be used to build a BST */
      //*********************************************************************
      public class Make {
      
         private Make right,
                      left;
                      
         private CarNode head;
         private String make;
         
         public Make(){
            right = null;
            left = null;
            head = null;
            make = "";
         }
         
      } // end Make class
      
      /* CarNode Class - a node object that will be used to build lists
            of cars that that branch from the Make BST */
      //*********************************************************************
      public class CarNode {
      
         private Car data;
         private CarNode next;
         
         // default constructor
         public CarNode(Car data){
            this.data = data;
            next = null;
         } // end constructor
         
      } // end CarNode class

} // end CarDB class