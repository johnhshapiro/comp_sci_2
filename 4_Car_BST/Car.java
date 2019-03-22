/* John Shapiro
   Section MW
   
   Program Name - Car.java
   
   Description - Car.java is a class that creates Car objects. It takes a String
         of car data (in the form of 'make, model, year, color, license#') and
         splits the data into seperate fields 
         
   Methods:
      parseLicense - returns a more search friendly license plate number
      parseCarData - takes a String of car data, splits each piece of data
            into an array, and returns the array
      toString     - returns a print friendly string of a Car's data
*/

public class Car {
      
   // Car variables
   public String  make,         // make of car
                  model,        // model of car
                  color,        // color of car
                  license,      // car's license plate number sans " -"
                  rawLicense;   // unmodified license plate number
   public Integer year;         // year of car
   
      // default Car constructor
      public Car() {      
         make = "";
         model = "";
         color = "";
         license = "";
         year = 0;      
      } // end default Car constructor
   
   // Car - constructor creates a car using a single string of car data
   //*********************************************************************
   public Car(String data) {      
   
      // split car data into an array
      String[] carData = parseCarData(data);
      
      // set each field of Car to the matching array data
      make = carData[0].trim();
      model = carData[1].trim();
      year = Integer.parseInt(carData[2].trim());
      color = carData[3].trim();
      rawLicense = carData[4].trim();
      license = parseLicense(rawLicense);
         
   } // end Car constructor
   
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
   
   /* parseCarData - return an array of a car's data that can be used to
         set each data field */
   //*********************************************************************
   public String[] parseCarData(String carLine) {
   
      String[] carData = new String[5];
      String temp = "";      
      int dataCount = 0;      // increments to the next piece of data
      
      // traverse and string and split data around commas into an array
      for (int i = 0; i < carLine.length(); i++) {
         // add non commas to a temp string
         if (carLine.charAt(i) != ',') {
            temp = temp + carLine.charAt(i);
         }
         // add temp to the array when a comma or end of string is reached
         if (carLine.charAt(i) == ',' ||
             i == carLine.length() - 1)  {
            carData[dataCount] = temp;
            temp = "";
            dataCount++;
         }
      }
      return carData;
      
   } // end parseCarData
   
   // toString - return a print friendly String of a car's data
   //*********************************************************************
   public String toString(){

      return "   Make: " + make + "\r\n" +
             "  Model: " + model + "\r\n" +
             "   Year: " + year + "\r\n" +
             "  Color: " + color + "\r\n" +
             "License: " + rawLicense + "\r\n";      

   } // toString
} // end car class