/*

Program: Sorting
Authors: J. Gurka + John Shapiro
Date: March 2018

Description: Program sorts a lists of random dataCopy using bubble, insertion,
   selection, merge, and quick sorts. Each sort will be timed using lists of
   different lengths in order to create data about how many compares, swaps,
   shifts, and passes were performed for each sort. It will also time each
   sort in order to demonstrate which sorts took less processing power.

Assumptions and limitations:
   1. ??

*/

import java.text.DecimalFormat;   // counter output
import java.io.*;                 // write to file
import java.util.Date;            // get the date
import java.text.DateFormat;      // format date
import java.text.SimpleDateFormat;// format date

public class Sorts {

   private int     n,            // number of values to be sorted
                   run;          // current execution of sorts out of RUN_MAX
                   
   // sorting counters
   private double  comparisons,  // all sorts 
                   swaps,        // bubble sort, selection sort, quicksort
                   shifts,       // insertion sort
                   passes,       // bubble sort, insertion sort, selection sort
                   copies,       // merge sort
                   calls;        // merge sort, quicksort (n^2 sorts have 1 call each)
   
   private int[]   data,         // initial random data, not changed during one run at one n value 
                   dataCopy;     // same data, to be sorted
   
   private final int MAX_VALUE  = 1000000,  // largest data value to be sorted
                     START_SIZE = 10000,    // smallest array size
                     END_SIZE   = 100000,   // largest array size
                     PRINT_MAX  = 10,       // largest array that will print out
                     RUN_MAX    = 3;        // number of times to run each sort at each n  
   
   private DecimalFormat countFormatter;
   
   private String line; // data output for file

//*******************************************************************
   public Sorts() {
      // System.out.println("constructor ...");
      comparisons = swaps = shifts = passes = calls = 0;
      run = 1;
      n = START_SIZE;
      data = new int[n];
      dataCopy = new int[n];
      countFormatter = new DecimalFormat("#,###");
   }

//*******************************************************************
   public void bubbleSort() {
      // System.out.println("bubble sort ...");
      int passSwapCount,   // counter for early sort termination
          tempSwap;
      long startTime = System.currentTimeMillis();
      for (int pass = 0; pass < n; pass++) {
         passSwapCount = 0;    // swaps on this pass
         passes++;             // total passes
         // compare pairs in unsorted part of array
         for (int i = 0; i < n-pass-1; i++) {
            // pair out of order?
            comparisons++;
            if (dataCopy[i] > dataCopy[i+1]) {
               // yes, swap
               swaps++;
               passSwapCount++;
               tempSwap = dataCopy[i];
               dataCopy[i] = dataCopy[i+1];
               dataCopy[i+1] = tempSwap;
            }  // swap
         }  // one pass
         // end of pass - sorted?
         if (passSwapCount == 0) {
            break;
         }
      }  // all passes
      long endTime = System.currentTimeMillis();
      // results
      line = "      bubble sort\r\n" + 
             "         elapsed time = " + createTimeString(endTime-startTime) + "\r\n" + 
             "         comparisons = " + countFormatter.format(comparisons) + "\r\n" + 
             "         swaps = " + countFormatter.format(swaps) + "\r\n" +
             "         passes = " + countFormatter.format(passes) + "\r\n" +
             "         calls = 1" + "\r\n" +
             "      bubble sort OK? " +
                               (this.isSorted() ? "yes" : "no") + "\r\n";
      System.out.println(line);
   }  // bubbleSort
   
//*******************************************************************
   public void insertionSort() {
   
      int tempInsert;         // hold value to be inserted          
      long startTime = System.currentTimeMillis(); // start timer
      int pass = 1;      
      // begin all passes
      while (pass < n) {    
         tempInsert = dataCopy[pass];
         int j = pass - 1;
         // one pass compares and shifts
         while (j >= 0 && dataCopy[j] > tempInsert) {            
            comparisons++;
            shifts ++;
            dataCopy[j+1] = dataCopy[j];
            j--;         
         }         
         // insert single pass value in order
         dataCopy[j+1] = tempInsert;   
         pass++;      
      } // end all passes
   
      long endTime = System.currentTimeMillis(); // end timer
      // results
      line = "      insertion sort\r\n" + 
             "         elapsed time = " + createTimeString(endTime-startTime) + "\r\n" + 
             "         comparisons = " + countFormatter.format(comparisons) + "\r\n" + 
             "         shifts = " + countFormatter.format(shifts) + "\r\n" +
             "         passes = " + countFormatter.format(pass) + "\r\n" +
             "         calls = 1" + "\r\n" +
             "      insertion sort OK? " +
                               (this.isSorted() ? "yes" : "no") + "\r\n";
      System.out.println(line);
   } // end instertionSort

//*******************************************************************
   public void selectionSort() {
     
      int min,
          index;
      
      long startTime = System.currentTimeMillis(); // start timer
      // all passes
      for (int pass = 0; pass < n - 1; pass++) {
      
         min = dataCopy[pass];
         index = pass;
         // single pass traverses the array and finds minimum unsorted integer
         for (int current = pass; current < n - 1; current++) {
            comparisons ++;
            if (dataCopy[current+1] < min) {
               min = dataCopy[current+1];
               index = current + 1;
               swaps++;
            }
         } // end single pass
         // do the swap
         dataCopy[index] = dataCopy[pass];
         dataCopy[pass] = min;
         passes++;
      
      } // end all passes
   
      long endTime = System.currentTimeMillis(); // end timer
      // results
      line = "      selection sort\r\n" +
             "         elapsed time = " + createTimeString(endTime-startTime) + "\r\n" + 
             "         comparisons = " + countFormatter.format(comparisons) + "\r\n" + 
             "         swaps = " + countFormatter.format(swaps) + "\r\n" +
             "         passes = " + countFormatter.format(passes) + "\r\n" +
             "         calls = 1" + "\r\n" +
             "      selection sort OK? " +
                               (this.isSorted() ? "yes" : "no") + "\r\n";  
      System.out.println(line);
   
   } // end selectionSort   

//*******************************************************************
   // mergesort
   public void mergesort() {
   
      int   low  = 0,
            high = n - 1;
      int[] temp = new int[n];
      // call temp
      long startTime = System.currentTimeMillis(); // start timer
      
      calls++;
      mergesort(low, high, temp);
      
      long endTime = System.currentTimeMillis(); // end timer
      // results
      line = "      merge sort\r\n" +
             "         elapsed time = " + createTimeString(endTime-startTime) + "\r\n" + 
             "         comparisons = " + countFormatter.format(comparisons) + "\r\n" + 
             "         copies = " + countFormatter.format(copies) + "\r\n" +
             "         calls = " + countFormatter.format(calls) + "\r\n" +
             "      merge sort OK? " +
                               (this.isSorted() ? "yes" : "no") + "\r\n";
      System.out.println(line);
   } // end mergesort
   // mergesort helper
   private void mergesort(int low, int high, int[] temp){
		// check if current section has more than one piece of data
		if(low < high){
			int mid = low + (high - low) / 2;
         calls += 3;
			mergesort(low, mid, temp);
			mergesort(mid + 1, high, temp);
			merge(low, mid, high, temp);
         comparisons++;
		}
	} // end mergesort helper
   // merge two two sets of numbers into a single array
	private void merge(int low, int mid, int high, int[] temp){
		// copy section of data into a temporary array
		for(int i = low; i <= high; i++){
			temp[i] = dataCopy[i];
         copies++;
		}
      // incrementation variables
		int loCurrent = low;
		int hiCurrent = mid + 1;
		int current = low;
      // begin merge
		while(loCurrent <= mid && hiCurrent <= high){
			// current value at the lower index is less than or equal
         if(temp[loCurrent] <= temp[hiCurrent]){
				dataCopy[current] = temp[loCurrent];
				loCurrent++;
			// current value at the lower index is greater than
         } else {
				dataCopy[current] = temp[hiCurrent];
				hiCurrent++;
			}
			current++;
         comparisons++;
         copies++;
		}
      // wstore remaining values from the temp array to the dataCopy array
		while( loCurrent <= mid){
			dataCopy[current] = temp[loCurrent];
			current++;
			loCurrent++;
         copies++;
		}
	}      

//*******************************************************************
   // quicksort
   public void quicksort() {
   
      long startTime = System.currentTimeMillis(); // start timer
   
      int pivot = n-1,  // integer to compare to
          start = 0,    // start of the current section of array
          sorted = 0,   // index of most recent swap
          current = 0;  // index of element being compared to pivot
      
      calls++;
      quicksort(pivot, start, sorted, current);
      
      long endTime = System.currentTimeMillis(); // end timer
      // results
      line = "      quick sort\r\n" + 
             "         elapsed time = " + createTimeString(endTime-startTime) + "\r\n" + 
             "         comparisons = " + countFormatter.format(comparisons) + "\r\n" + 
             "         swaps = " + countFormatter.format(swaps) + "\r\n" +
             "         calls =" + countFormatter.format(calls) + "\r\n" +
             "      quick sort OK? " +
                               (this.isSorted() ? "yes" : "no") + "\r\n";
      System.out.println(line);
   } // end quicksort
      // quicksort helper
      private void quicksort(int pivot, int start, int sorted, int current) {
         
         int swap = 0;
         // quicksort current part of array
         while (sorted < n && sorted >= 0 && current < n && current >= 0) {
         
            comparisons++;
            // swap
            if (dataCopy[current] < dataCopy[pivot]) {            
               swap = dataCopy[current];
               dataCopy[current] = dataCopy[sorted];
               dataCopy[sorted] = swap;
               sorted++;
               swaps++;
            }
            current++;         
         }
         // final swap of single pass
         swap = dataCopy[pivot];
         dataCopy[pivot] = dataCopy[sorted];
         dataCopy[sorted] = swap;
         swaps++;
         // quicksort low section then high section
         if (sorted > start) {
            calls++;
            quicksort(sorted - 1, start, start, start);
         }
         if (sorted < pivot) {
            calls++;
            quicksort(pivot, sorted + 1, sorted + 1, sorted + 1);
         }
      } // end quicksort helper

//*******************************************************************
   public StringBuffer createTimeString(long milliseconds) {
   // create a string with approximate elapsed time, converted from milliseconds
      // System.out.println("createTimeString ...");
      StringBuffer timeString = new StringBuffer("");
      long seconds = (milliseconds / 1000) % 60;
      long minutes = (milliseconds / (1000 * 60)) % 60;
      long hours = (milliseconds / (1000 * 60 * 60)) % 24;
      if (seconds == 0 && minutes == 0 && hours == 0) {
         timeString = timeString.append("less than one second");
      }
        else if (minutes == 0 && hours == 0) {
         timeString = timeString.append(seconds + " second");
         if (seconds != 1)
            timeString = timeString.append("s"); 
      } 
        else if (hours == 0) {
         timeString = timeString.append(minutes + " minute"); 
         if (minutes != 1)
            timeString = timeString.append("s");
         timeString.append(", ");
         if (seconds != 0) {
            timeString = timeString.append(seconds + " second");  
            if (seconds != 1)
               timeString = timeString.append("s");
         }
      } else {  // >= 1 hour
         timeString = timeString.append(hours + " hour");
         if (hours != 1)
            timeString = timeString.append("s"); 
         if (minutes != 0) {        
            timeString = timeString.append(minutes + " + minute");
            if (minutes != 1)
               timeString = timeString.append("s");           
         }
      }
      return timeString;
   }

//*******************************************************************
   public void generateData() {
      // System.out.println("generateData ...);
      data = new int[n];
      dataCopy = new int[n];  
      for (int i = 0; i < n; i++) {
         data[i] = (int)(Math.random() * MAX_VALUE + 1);
         dataCopy[i] = data[i];
      }
   }

//*******************************************************************
   public void restoreData() {
   // fill copy array with original unsorted data,
   // to be sorted by next sorting algorithm
      // System.out.println("restoreData ...");
      for (int i = 0; i < n; i++) {
         dataCopy[i] = data[i];
      }
   }

//*******************************************************************
   public boolean isSorted() {
   // check data order - is it sorted non-descending?
      // System.out.println("isSorted ...");
      boolean sorted = true;
      int i = 0;
      while (sorted && i < n-1) {
         if (dataCopy[i] > dataCopy[i+1])
            sorted = false;
           else
            i++;
      }
      return sorted;
   }  // isSorted

//*******************************************************************
   public void reset() {
   // set counters back to zero
      // System.out.println("reset ...");   
      comparisons = swaps = shifts = passes = calls = 0;  
   }

//*******************************************************************
   public void printList(String label, int[] list) {
      // System.out.println("printList ...");
      if (n <= PRINT_MAX) {
         System.out.print(label + " ");
         for (int i = 0; i < n; i++) {
            System.out.print(list[i] + "  ");
            if (i % 10 == 9)
               System.out.println();
         }         
      }
   }

//*******************************************************************
   //MAIN METHOD*****************************************************
   public static void main (String args[]) throws IOException {
   
   BufferedWriter writer 
                  = new BufferedWriter(new FileWriter("SortReport.txt"));
                  
   Sorts sorter = new Sorts();
   Date date = new Date();
   DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
   // heading 
   writer.write("John Shapiro\r\n" +
                "Computer Science 2\r\n" +
                "SectionMW\r\n" +
                sdf.format(date) + "\r\n");
   
      for (int runNumber = 1; runNumber <= 3; runNumber++) {
         // set correct amount of data for each run
         if (runNumber == 1 ) {
            sorter.n = sorter.START_SIZE;
         }
         if (runNumber == 2 ) {
            sorter.n = sorter.START_SIZE * 5;
         }
         if (runNumber == 3 ) {
            sorter.n = sorter.START_SIZE * 10;
         }
         
         System.out.println("-----");
         System.out.println ("\r\nSorts beginning, N = " + sorter.countFormatter.format((long)sorter.n));
         writer.write("-----" + 
                      "\r\nResults when N = " + sorter.countFormatter.format((long)sorter.n) + "\r\n");
         sorter.generateData();  // new data, used by all sorts
         
         for (sorter.run = 1; sorter.run <= sorter.RUN_MAX; sorter.run++) {
         
            sorter.reset();         // counters = 0  
            sorter.generateData();  // new data
            
            /* test code prints short lists for debugging
            sorter.printList("Original list:", sorter.data);
            sorter.printList("Unsorted list:", sorter.dataCopy);*/
            
            System.out.println("   run #" + sorter.run);
            writer.write("   run #" + sorter.run + "\r\n");
            // bubble
            sorter.bubbleSort();
            writer.write(sorter.line); // print data to file
            sorter.reset();            // counters = 0  
            sorter.restoreData();      // copy original data for next sorting algorithm
            // insertion
            sorter.insertionSort();
            writer.write(sorter.line); // print data to file
            sorter.reset();            // counters = 0  
            sorter.restoreData();      // copy original data for next sorting algorithm
            // selection
            sorter.selectionSort();
            writer.write(sorter.line); // print data to file
            sorter.reset();            // counters = 0  
            sorter.restoreData();      // copy original data for next sorting algorithm
            // merge
            sorter.mergesort();
            writer.write(sorter.line); // print data to file
            sorter.reset();            // counters = 0  
            sorter.restoreData();      // copy original data for next sorting algorithm
            // quick
            sorter.quicksort();
            writer.write(sorter.line); // print data to file
            sorter.reset();            // counters = 0  
            sorter.restoreData();      // copy original data for next sorting algorithm
            
            /* test code prints short lists for debugging
            sorter.printList("Sorted list, quicksort:", sorter.dataCopy);*/
            
         }  // runs

      }  // increasing n values
      
      writer.close(); // close output file
      
   }  // main

}  // Sorts