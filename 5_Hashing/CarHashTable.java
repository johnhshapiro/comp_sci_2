/* John Shapiro
   Section MW
   
   CarHashTable Class
   
   Methods:
      insertCar - insert a car to its correct location in a hash table
      buildTable - insert all cars using insert car, rehashes when load
            factor is exceeded
      printTable - prints the hash table to an external file
      hashFn - returns a hashed key to be used for calulating index
      print - prints a String to the console
      printHeading - a static method that prints assignment heading in the
            output file
*/

import java.io.*;                // Access to filewriter/reader
import java.util.*;              // Access to scanner and ArrayList

public class CarHashTable{

   private int            count;       // pieces of data in the hash table
   private Car[]          table;       // hash table containing Car objects
   public  BufferedWriter writer;      // write to file

   // CarHashTable constructor
   public CarHashTable(String outputFile, int size) throws IOException {
   
      count = 0;
      table = new Car[size];
      writer = new BufferedWriter(new FileWriter(outputFile));
   
   } // end Car constructor
   
   /* insertCar - inserts a car into the Class level table using a String
         of data seperated by commas */
   //*********************************************************************
   public void insertCar(String carData) throws IOException {   
      //print("currently in method insertCar");
      
      Car car = new Car(carData);
      int index = hashFn(Integer.parseInt(car.vin))%table.length;
      
      // loop to find an unoccupied index
      while (table[index] != null) {
         if (index < table.length) {
            index++;
         }
         else {
            index = 0;
         }
      } // end while
      table[index] = car;
   
   } // end insertCar
   
   /* buildTable - reads all data from a file, and adds all pieces of data
         to the table using insertCar, and rehashes when necessary */
   //*********************************************************************
   public void buildTable(String inputName)
         throws FileNotFoundException, IOException {   
      //print("currently in buildTable");
      
      ArrayList<String> temp =
            new ArrayList<String>(100);   // list of carData Strings
      boolean rehash = false;
      int hashCount = 0;                  // count of hashes/rehashes
      int carCount  = 0;                  // count of cars in temp ArrayList
      
      // file reading variables
      FileInputStream file    
            = new FileInputStream(inputName);
      Scanner inputFile
            = new Scanner(file);
            
      // loop to import data to ArrayList
      while (inputFile.hasNextLine()) {
         temp.add(inputFile.nextLine());
         carCount++;
      }
      
      // only enter loop if there is data
      if (carCount >0) {
         // hash and rehash loop
         do {
            count = 0;            
            // resize table if rehashing
            if (rehash) {
               table = new Car[table.length + 3];
            }
            // loop to create hash table
            do {
               insertCar(temp.get(count));
               count++;
            } while (table.length/count >= 2 &&
                   count < carCount); // end create hash table
            
            hashCount++;
            rehash = false;
            
            // decide of rehash is necessary
            if (table.length/count < 2) {
               rehash = true;         
            }
                        
            printTable(hashCount);;
         } while (rehash); // end hash/rehash loop
      } // end if
      file.close();
   } // end buildTable
   
   /* searchVIN - uses hashing to quickly find a specific VIN. Prints an
         appropriate message to report if the VIN was found */
   //*********************************************************************
   public void searchVIN(int vin) throws IOException {   
      //print("currently in searchVIN");
      
      int index = hashFn(vin)%table.length;
      int originalIndex = index;
      boolean foundIndex = false;   // true when the correct index is found
      
      // no data at calculated index, no need to increment search
      if (table[index] == null) {
         print("NO MATCH FOUND FOR VIN " + vin + "\n");
         foundIndex = true;
      }
      // non matching data found, must search incrementally
      else {
         do {
            // print a match
            if (Integer.parseInt(table[index].vin) == vin) {
               print("FOUND MATCH!!\n\n" + table[index].toString() + "\n");
               foundIndex = true;
            }
            // increment if necessary
            else {
               if (index < table.length) {
                  index++;
               }
               else {
                  index = 0;
               }
            }
         } while (table[index] != null &&
                  Integer.parseInt(table[index].vin) != vin);
      } // end if/else
      
      if (!foundIndex) {
         print("NO MATCH FOUND FOR VIN " + vin + "\n");
      }
   
   } // end searchVIN
   
   /* printTable -prints the hash table to an external file */
   //*********************************************************************
   public void printTable(int hashCount) throws IOException {
      //print("currently in printTable");
      
      writer.write("table #" + hashCount + ", size = " + table.length + "\r\n");
      
      for (int i = 0; i < table.length; i++) {
         if (table[i] == null) {
            writer.write(i + ": null\r\n");
         }
         else {
            writer.write(i + ": " + table[i].vin + "\r\n");
         }
      }
      
      writer.write("\n");
      
   } // end printTable
   
   /* hashFn - returns the key to be used in calculation of the index of
         a piece of data */
   //*********************************************************************
   public int hashFn(int key) throws IOException {   
      //print("currently in hashFn");      
      return 7 * key - 13;   
   } // end hashFn
   
   /* print - this lazy person's method prints a string to console */
   //*********************************************************************
   public void print(String printThis) throws IOException {   
      System.out.println(printThis);   
   }
   
   /* print - this lazy person's method prints a string to console */
   //*********************************************************************
   public static void printHeader(BufferedWriter writer) throws IOException {   
      writer.write("John Shapiro\r\n" +
                   "CS 2, Spring 2018\r\n" + 
                   "Hash Table Report\r\n\r\n");
   }
   
   //TESTING ONLY***TESTING ONLY***TESTING ONLY***TESTING ONLY***TESTING**
   public static void main(String[] args) throws IOException {
   
      CarHashTable hash = new CarHashTable("output.txt", 7);
      hash.buildTable("cars.txt");
      hash.writer.close();
   
   } // end main

} // end CarHashTable class