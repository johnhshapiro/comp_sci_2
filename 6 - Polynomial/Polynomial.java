/* John Shapiro
   Section MW
   
   Program Name - Polynomial.java
   
   Description - This program takes strings containing polynomials in normal
      form and seperates the individual terms into Nodes (called Terms in this
      program) in a singly linked non circular linked list.
*/

import java.util.*; // Scanner, StringTokenizer

/* Singly linked non circular list w/ tail:
    - uses Term objects that contain a coefficient and exponent
*/

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
      current  = null;
   
   } // end constructor
   
//*********************************************************
   // returns a polynomial from a string
   public void readPoly(String poly) {
   
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
   // method to add two polynomials
   public Polynomial addPoly(String polyOne, String polyTwo) {
         
      Polynomial three = new Polynomial();
      
      Polynomial one = new Polynomial();
      one.readPoly(polyOne);
      one.current = one.head;
      
      Polynomial two = new Polynomial();
      two.readPoly(polyTwo);
      two.current = two.head;
      
      // if each poly only has one term this sorts and/or combines them
      if (one.current.next == null && two.current.next == null) {
         if (one.current.exponent > two.current.exponent) {
            three.head = one.current;
            three.head.next = two.current;
            three.tail = two.current;
         }
         else if (one.current.exponent < two.current.exponent) {
            three.head = two.current;
            three.head.next = one.current;
            three.tail = one.current;
         }
         else {
            one.current.coefficient += two.current.coefficient;
            three.head = one.current;
            three.head.next = null;
            three.tail = three.head;
         }
      }
      
      // if one or both lists has more than one term
      else {
         if (one.current.exponent > two.current.exponent) {
            three.head = one.current;
            if (one.current.next != null) {
               one.current = one.current.next;
            }
         }
         else if (one.current.exponent < two.current.exponent) {
            three.head = two.current;
            if (two.current.next != null) {
               two.current = two.current.next;
            }
         }
         else {
            one.current.coefficient += two.current.coefficient;
            three.head = one.current;
            if (one.current.next != null) {
               one.current = one.current.next;
            }
            if (two.current.next != null) {
               two.current = two.current.next;
            }         
         }
         
         three.current = three.head;
         // enter first loop if both one and two have more terms
         if (one.current.next != null && two.current.next != null) {
            do {
               if (one.current.exponent > two.current.exponent) {
                  three.current.next = one.current;
                  three.current = three.current.next;
                  one.current = one.current.next;
               }
               else if (two.current.exponent > one.current.exponent) {
                  three.current.next = two.current;
                  three.current = three.current.next;
                  two.current = two.current.next;
               }
               else {
                  one.current.coefficient += two.current.coefficient;
                  three.current.next = one.current;
                  three.current = three.current.next;
                  one.current = one.current.next;
                  two.current = two.current.next;
               }
            } while (one.current.next != null && two.current.next!= null);
         }
         // enter this loop if two only has one term left
         if (one.current.next != null && two.current.next == null) {
            do {
               if (one.current.exponent > two.current.exponent) {
                  one.current = one.current.next;
               }
               else if (two.current.exponent > one.current.exponent) {
                  three.current.next = two.current;
                  two.current.next = one.current;
                  three.tail = one.tail;
                  return three;
               }
               else {
                  one.current.coefficient += two.current.coefficient;
                  three.current.next = one.current;
                  three.tail = one.tail;
                  return three;
               }
            } while (one.current.next != null);
         }
         // enter this loop if two only has one term left
         if (one.current.next == null && two.current.next != null) {
            do {
               if (two.current.exponent > one.current.exponent) {
                  two.current = two.current.next;
               }
               else if (one.current.exponent > two.current.exponent) {
                  three.current.next = one.current;
                  one.current.next = two.current;
                  three.tail = two.tail;
                  return three;
               }
               else {
                  two.current.coefficient += one.current.coefficient;
                  three.current.next = two.current;
                  three.tail = two.tail;
                  return three;
               }
            } while (two.current.next != null);
         }
         
         // finish the poly if one and two hav the same amount of terms
         if (one.current.next == null && two.current.next == null) {
            if (one.current.exponent > two.current.exponent) {
               three.current.next = one.current;
               three.current.next.next = two.current;
               three.tail = two.current;
            }
            else if (one.current.exponent < two.current.exponent) {
               three.current.next = two.current;
               three.current.next.next = one.current;
               three.tail = one.current;
            }
            else {
               one.current.coefficient += two.current.coefficient;
               three.current.next = one.current;
               three.tail = one.current;
            }
         }
      }
      return three;
   } // end addPoly
   
//*********************************************************
   // prints the currently stored polynomial
   public String printPoly() {
      
      String output = "";
      current = head;
      // empty polynomial
      if (isEmpty()) {
         return output;
      }
      // print single term polynomial
      else if (head.next == null) {
         if (current.coefficient == 1 && current.exponent == 1) {
            output += "x";
         }
         else if (current.exponent == 0) {
            output += "" + current.coefficient;
         }
         else if (current.coefficient > 1) {
            output += current.coefficient + "x";
         }
         else if (current.coefficient == 1) {
            output += "x";
         }
         if (current.exponent > 1) {
            output += "^" + current.exponent;
         }
      }
      // print multi-term polynomial
      else {
         do {
            if (current.exponent == 0) {
               output += "" + current.coefficient;
            }
            else if (current.coefficient > 1) {
               output += current.coefficient + "x";
            }
            else if (current.coefficient == 1) {
               output += "x";
            }
            if (current.exponent > 1) {
               output += "^" + current.exponent;
            }
            if (current.next != null) {
               output += " + ";
            }
            current = current.next;
         } while (current != null); // end do/while

      } // end if else if
            
      return output;
   } // end printPoly
      
//*********************************************************
   // checks if the list is empty
   public boolean isEmpty(){
   
      return head == null;
      
   } // end isEmpty
   
//*********************************************************
   // delete currently stored polynomial
   public void deletePoly(){
   
      head = null;
      tail = null;
      current = head;
      
   } // end deletePoly

//*********************************************************

   // Term class
   class Term {
	   
      private Integer coefficient; // multiply 'x' by this value
	   private Integer exponent;    // 'x' will be raised to this power
      
      private Term next;           // pointer to the next node
      
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
      
   }  // end Term class
}  // end Polynomial class