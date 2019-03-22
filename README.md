# Computer Science 2 Projects
This repository contains all main projects completed for the Computer Science 2 course
at Metropolitan State University of Denver.

## 1. Binary Number Translator

A very basic binary number translator to kick off the course. From the documentation: "Binary translator reads single lines from an input file and decides if the number is a valid 8 bit binary number. If so, the program prints out the decimal form of the number in an output file."

## 2. Polynomial Evaluator

An early foray into the world of linked lists. From the documentation: "This program takes strings containing polynomials in normal form and seperates the individual terms into Nodes (called Terms in this program) in a singly linked non circular linked list." This program also evaluates polynomials when given a value for 'x'. It can also combine two polynomials in normal form into a single polynomial.

## 3. Sorting Algorithm Timer

A program designed to time and compare the efficiency of different sorting algorithms. As a part of this project, we had to write each sorting algorithm on our own to better understand the method behind each sort type. From the documentation: "Program sorts a lists of random dataCopy using bubble, insertion, selection, merge, and quick sorts. Each sort will be timed using lists of different lengths in order to create data about how many compares, swaps, shifts, and passes were performed for each sort. It will also time each sort in order to demonstrate which sorts take less processing power."

## 4. Car Binary Search Tree "Database"

A culmination of the previous projects. It turns an unordered list of cars in a particular format into a binary search tree by make. Each node in the binary search tree then has a linked list of cars of said make. Here is a detailed description of each class.

### Car

    - Description - Car.java is a class that creates Car objects. It takes a
      String of car data (in the form of 'make, model, year, color, license#')
      and splits the data into seperate fields.
    - Methods:
      - parseLicense - returns a more search friendly license plate number
      - parseCarData - takes a String of car data, splits each piece of data
        into an array, and returns the array
      - toString     - returns a print friendly string of a Car's data

### CarDB

    - Description - CarDB.java is a class that creates CarDB objects. A CarDB
      object is a primitive database of Car Class objects implemented as a
      BST. The BST is sorted using the make field of a Car. Each node in a
      CarDB BST has a make field and points to a singly linked list containing
      car objects whose makes matche that of the Make node.
      This class contains methods to do search for Cars based on a variety
      of Car traits (i.e. make, model, year, color, plate #). All search
      data prints to an external .txt file with a message that identifies
      which data was searched for.
    - Methods:
      - addCar - adds a car to the database
      - buildCarDB - reads input file and uses addCar to build the database
      - print - prints a string to the output file
      - printCarDB - print all cars to output file
      - searchMake - print all cars with specified make
      - searchModel - print all cars with specified model
      - searchColor - print all cars with specified color
      - searchYear - print all cars with specified year
      - searchLicense - print a car with specified license #
      - searchPartial - print all cars containing specified partial license #
      - makeAndColor - print all cars with specified make AND color
      - colorAndPartial - print all cars with specified color AND partial
      - searchYearRange - print all cars within specified year range (inclusive)
      - parseLicense - returns a more search friendly license plate number

### CarDriver

    - Description - CarDriver.java is an interactive program that allows a user
      to build a database of cars from a file of their choosing. The user can
      then choose query types from a menu, and then perform searches based on
      manually input data. All search data will printed to an external .txt
      file whose name will be chose by the user.
      The input file must contain only single lines of Strings in the form
      "make, model, year, color, license#" in order to produce a database.
      Messages produced by this file will only print to the system console.
      All file data is printed using methods from Car and CarDB.
    - Methods:
      - main - allows users to create and search entries in a car database

## 5. Hashing

A much simpler version of the previous program that builds a "database" using a hash table. The breakdown is as follows.

### Car

The same car as in the previous program. See above.

### CarHashTable

    - Description - A set of methods that allow that build a hash table of car
      objects from an outside program or from user input.
    - Methods:
      - insertCar - insert a car to its correct location in a hash table
      - buildTable - insert all cars using insert car, rehashes when load
        factor is exceeded
      - printTable - prints the hash table to an external file
      - hashFn - returns a hashed key to be used for calulating index
      - print - prints a String to the console
      - printHeading - a static method that prints assignment heading in the
            output file

### Hashing Driver

    - Description - HashingDriver.java is an interactive program that allows a
      user to build a hash table of cars from a file of their choosing. The
      user can then search for cars by VIN. Search results are printed to the
      console.
      The input file must contain only single lines of Strings in the form
      "make, VIN, model, year, color, license#" in order for the hash table
      to be created successfully.
         
    - Methods:
      - main - allows users to create and search entries in a hash table of cars
