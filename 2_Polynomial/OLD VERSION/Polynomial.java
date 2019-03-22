/* John Shapiro
   
   Polynomial.class
   
   Description: This class allows a user to enter a polynomial into a linked
      list one term at a time, either by way of an input file or by entering
      data manually. It also allows a user to evaluate the polynomial by
      providing a value for "x" as well as add two polynomials together.
      
      It also contains a node class called Term. Each term in the polynomial
      will be stored as one of these terms.
*/


import java.util.*; // Scanner, StringTokenizer
import java.io.*;   // File reading and writing

// Singly linked non circular list w/ no tail
public class Polynomial {

	private Term          head,
                         tail,
                         current;
   private Scanner       keyboard;
      
//*********************************************************
   // default constructor
   public Polynomial() {
      
      head     = null;
      tail     = null;
      current  = head;
      keyboard = new Scanner(System.in);
   
   } // end constructor
   
//*********************************************************
   // returns a polynomial from a string
   public Polynomial(String poly) {
   
   StringTokenizer input = new StringTokenizer(poly);
      
      // break up the string using StringTokenizer
      do {      
         Term newTerm = new Term();         
         poly = input.nextToken();
         
         // skip addition signs
         if (poly.contains("+")) {
            continue;
         }
         
            // check if the term not a constant
            if (poly.contains("x")) {
               
               // change coefficient to integer before 'x'
               if (poly.charAt(0) != 'x') {
                  newTerm.coefficient =
                     Integer.parseInt(
                        poly.substring(0, poly.indexOf('x')));
               }
               
               // check for exponent and set exponent value
               if (poly.contains("^")) {
                  newTerm.exponent =
                     Integer.parseInt(
                        poly.substring((poly.indexOf('^') + 1),
                                           (poly.length())));
               }
            }
            
            // sets value of constant and exponent to '0'
            if (poly.contains("x") != true) {
               newTerm.coefficient =
                  Integer.parseInt(poly);
               newTerm.exponent = 0;
            }
                     
         // enter term into list if first term
         if (isEmpty()) {
            head = newTerm;
            tail = head;
         }
         
         // enter term into list if not first term
         else {
            tail.next = newTerm;
            tail = newTerm;
         }         
      } while (input.hasMoreTokens());
                 
   } // end userPolynomial
   
//*********************************************************
   // allow user to enter a polynomial
   public void userPolynomial(){
      
      // Explain polynomial input to user
      System.out.println("Rules for entering a polynomial:\n" +
                         " - One space in between terms and plus signs.\n" +
                         " - No coefficients of 'x' equal to 0 or 1.\n" +
                         " - Exponents represented by 'x^exp' where 'exp'\n" +
                         "    is the power 'x' will be raised to.\n" +
                         " - EXAMPLE: 10x^4 + 2x + 10\n\n" +
                         
                         "Enter your polynomial:");

      // create a polynomial object with user input  
      Polynomial userPoly = new Polynomial(keyboard.nextLine());
      
   } // end userPolynomial

//*********************************************************
   // find the value of the polynomial after substituting an int for 'x'
   public int evaluatePoly(int x) {
   
      int polyValue = 0;
      
      current = head;
      // polynomial with a single term
      if (current == tail) {
         polyValue += current.coefficient * (int)Math.pow(x, current.exponent);
         return polyValue;
      }
      
      // polynomial with more than one term
      current = head;
      do {
         polyValue += current.coefficient * (int)Math.pow(x, current.exponent);
         current = current.next;
      } while (current != null);
      
      return polyValue;
         

   } // end evaluatePoly() {
   
//*********************************************************
   // adds two polynomials
   public Polynomial (String polyOne, String polyTwo) {
      
      Polynomial one = new Polynomial(polyOne);
      Polynomial two = new Polynomial(polyTwo);
      
      one.current = one.head;
      two.current = two.head;
      
      // single term each
     if (one.head.next == null && two.head.next == null) {
         if (one.current.exponent > two.current.exponent) {
            one.current.next = two.current;
            one.tail = two.current;         
         }
         if (one.current.exponent == two.current.exponent) {
            one.current.coefficient += two.current.coefficient;
            
         }
         two.head.next = one.head;
         two.tail = one.head;
         return;
      }
      
      // single term in one, more in the other
      if (one.head.next == null && two.head.next != null) {
         if (one.current.exponent > two.current.exponent) {
            one.head.next = two.head.next;
            one.tail = two.tail;
         }
         if (one.current.exponent == two.current.exponent) {
            one.current.coefficient += two.current.coefficient;
            one.current.next = two.current.next;
            one.tail = two.tail;
         }
         do {
            if (one.current.exponent > two.current.exponent
                && one.current.exponent < two.current.next.exponent) {
               one.current.next = two.current.next;
               two.current.next = one.current;
            }
            if (one.current.exponent == two.current.exponent) {
               two.current.coefficient += one.current.coefficient;
            }
            two.current = two.current. next;
         } while (two.current != two.tail);
         two.tail.next = one.current;
         two.tail = one.current;
      }
      
      // single term in two, more in the other
      if (two.head.next == null && one.head.next != null) {
         if (two.current.exponent > one.current.exponent) {
            two.head.next = one.head.next;
            two.tail = one.tail;
         }
         if (two.current.exponent == one.current.exponent) {
            two.current.coefficient += one.current.coefficient;
            two.current.next = one.current.next;
            two.tail = one.tail;
         }
         do {
            if (two.current.exponent > one.current.exponent
                && two.current.exponent < one.current.next.exponent) {
               two.current.next = one.current.next;
               one.current.next = two.current;
            }
            if (two.current.exponent == one.current.exponent) {
               one.current.coefficient += two.current.coefficient;
            }
            one.current = one.current. next;
         } while (one.current != one.tail);
         one.tail.next = two.current;
         one.tail = two.current;
      }
      
      // multiple terms in either one
      do {
         // exponent from two is greater than first term exponent of one
         if (one.current.exponent < two.current.exponent &&
             one.current == one.head) {
            Term temp = new Term(two.current.coefficient,
                                 two.current.exponent);
            one.head = temp;
            temp.next = one.current;
            one.current = one.current.next;
            two.current = two.current.next;
         }
         // exponent of two is between two terms on one
         if (one.current.exponent > two.current.exponent
             && one.current.next.exponent < two.current.exponent) {
            two.current.next = one.current.next;
            one.current.next = two.current;
            one.current = one.current.next;
         }
         // exponent of two is uqual to exponent of one
         if (one.current.exponent == two.current.exponent) {
            one.current.coefficient += two.current.coefficient;
            two.current = two.current.next;
            one.current = one.current.next;
         }
         // exponent of two is less than last term exponent of one
         if (one.current == one.tail &&
             one.current.exponent > two.current.exponent) {
            // two is at the tail
            if (two.current == two.tail) {
               one.current.next = two.tail;
               one.tail = two.tail;
            }
            // two has more terms
            else {
               one.current.next = two.current;
               one.tail = two.tail;
            }
         }
      } while (one.current != one.tail && two.current != two.tail);
   } // end addPoly
   
//*********************************************************
   // prints the currently stored polynomial
   public void printPoly() {
      
      current = head;
      System.out.println("Here is your polynomial!");
      // empty polynomial
      if (isEmpty()) {
         return;
      }
      // print single term polynomial
      else if (head.next == null) {
         if (current.exponent == 0) {
            System.out.println(current.coefficient);
         }
         if (current.coefficient > 1) {
            System.out.print(current.coefficient + "x");
         }
         if (current.exponent > 1) {
            System.out.println("^" + current.exponent);
         }
      }
      // print multi-term polynomial
      else {
         do {
            if (current.exponent == 0) {
               System.out.println(current.coefficient);
            }
            if (current.coefficient > 1) {
               System.out.print(current.coefficient + "x");
            }
            if (current.exponent > 1) {
               System.out.print("^" + current.exponent);
            }
            if (current != tail) {
               System.out.print(" + ");
            }
            current = current.next;
         } while (current != null); // end do/while
      } // end if else if
   } // end printPoly
      
//*********************************************************
   // checks if the list is empty
   public boolean isEmpty(){
   
      return head == null;
      
   } // end isEmpty

//*********************************************************

   // Term class
   class Term {
	   
      private Integer coefficient; // multiply 'x' by this value
	   private Integer exponent;    // 'x' will be raised to this power
      
      private Term next;       // pointer to the next node
      
      // default constructor      
      public Term(){
         
         coefficient = 1;
         exponent    = 1;
         
         next = null;
         
      } // end constructor
      
      public Term(int coeff, int exp) {
         
         coefficient = coeff;
         exponent    = exp;
         
         next = null;
         
      }
      
   }  // Term
   
   
//########MAIN METHOD FOR TESTING########
//#######################################

   public static void main (String[] args) {
   
   
   Polynomial combined = new Polynomial("5x^2", "3x");
   combined.printPoly();
   
   
   
   } //###END TEST AREA   
    
}  // Polynomial