// This class allows the user to manage a game of Assassin. It includes methods
//  essential to the mechanisisms of the game.

import java.util.*;

public class AssassinManager {
   private AssassinNode front;
   private AssassinNode graveFront;
   
   // Constructs assassin manager object from names from the past list. 
   //    Assumes non-empty names and no duplicate names 
   // If list "names" is empty throws IllegalArgumentException 
   public AssassinManager (List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      }
   
      graveFront =  null;
      
      for (int i = names.size() - 1; i >= 0; i--) {
         front = new AssassinNode(names.get(i), front);
      }
   }
   
   // Prints out the kill ring. If one person is left, reports they are
   //  stalking themselves
   public void printKillRing() {
      AssassinNode curr = front;
      
      while (curr.next != null) {
         System.out.println("    " + curr.name+ " is stalking " + curr.next.name); 
         curr = curr.next;
      }
      
      System.out.println("    " + curr.name + " is stalking " + front.name);
   }
   
   // Prints the names of people in the grave yard in the reverse order 
   //  they were killed. If no has been killed prints empty
   public void printGraveyard() {
      AssassinNode curr = graveFront;
      
      while (curr != null) {
         System.out.println("    " + curr.name + " was killed by " + curr.killer); 
         curr = curr.next;
      }     
   }
   
   // Returns true if kill ring contains the passed string "name", false otherwise
   //  Ignores case
   public boolean killRingContains(String name) {
      return contains(front, name);   
   }
   
   // Returns true if graveyard contains the passed string "name", false otherwise
   //  Ignores case
   public boolean graveyardContains(String name) {
      return contains(graveFront, name);
   }
   
   // Returns true if game is over (one person alive) and false otherwise
   public boolean gameOver() {
      return (front.next == null);
   }
   
   // Returns name of the winner and null if game isn't over
   public String winner() {
      if (gameOver()) {
         return front.name;
      } else {
         return null;
      }
   }
   
   // Records killing of the passed "name"
   // If game isn't over throws IllegalStateException
   // If kill ring doesn't contain passed name throws IllegalArgumentException
   public void kill(String name)  {
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      } else if (gameOver()) {
         throw new IllegalStateException();
      }
      
      AssassinNode curr = front;
      AssassinNode temp = null;
      
      //seperate test if its first name in list
      if (name.equalsIgnoreCase(curr.name)) {
         temp = curr;
         
         //get to last person in list
         while (curr.next != null) {
            curr = curr.next;
         }
         //removes person from killring
         front = front.next;
         
      } else {
         while (!name.equalsIgnoreCase(curr.next.name)) {
            curr = curr.next;
         }
         
         temp = curr.next;
         //removes from ring
         curr.next = curr.next.next;
      }
      
      if (graveFront == null) {
         graveFront = temp;
         temp.next = null;
      } else {
         temp.next = graveFront;
      }
      
      graveFront = temp;
      graveFront.killer = curr.name;
   }
   
   // Returns true if the passed "name" exists within the passed 
   //  assassin node "curr" and false otherwise
   private boolean contains(AssassinNode curr, String name) {
      while (curr != null) {
         if (name.equalsIgnoreCase(curr.name)) {
            return true;
         }
         curr = curr.next;
      }
      
      return false;
   }
}