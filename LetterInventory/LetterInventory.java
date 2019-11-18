// Samuel Fields
// 10/02/2019
// CSE143
// TA: Audrey Michelle
// Assignment #1
//
//This class allows the user to construct a LetterInventory object. The letter inventory 
// object stores an inventory of letters present in the passed string. It ignores the 
// case of the letter and anything non-alphabetical.

import java.util.*;

public class LetterInventory {
   public static final int NUMBER_CHARACTERS = 26;
   private int size;
   private int[] inventory;
   
   //Constructs an inventory (a count) of the alphabetic letters in the given string, 
   // ignoring the case of letters and ignoring any non-alphabetic characters.
   public LetterInventory(String input)  {
      inventory = new int[NUMBER_CHARACTERS];
      size = 0;
      
      input = input.toLowerCase();
      for(int i = 0; i < input.length(); i++) {
         char temp = input.charAt(i);
         int count = temp - 'a';
         
         //determines if temp is a letter and adds one to inventory count of that letter
         if(count < NUMBER_CHARACTERS && count >= 0) {
            inventory[count]++;
            size++;
         }   
      }
   }
   
   //Returns the total size of the inventory (number of characters stored)
   public int size() {
      return size;
   }
   
   //Returns whether the inventory is empty (true if yes, false if not)
   public boolean isEmpty() {
      return (size == 0);
   }
   
   //Returns a count of how many of the passed character are in the inventory.  
   //Letter may be lowercase or uppercase.  
   //If a nonalphabetic character is passed, throws an IllegalArgumentException.
   //char letter: letter character to be identified
   public int get(char letter) {
      int count = findCount(letter);
      
      return inventory[count];
   }
   
   //Returns a String representation of the inventory with the letters all in lowercase 
   // and in sorted order and surrounded by square brackets.
   public String toString() {
      String toString = "[";
      for(int i = 0; i < NUMBER_CHARACTERS; i++) {
         int numChar = inventory[i];
         
         for(int j = 0; j < numChar; j++) {
            toString = toString + (char)('a' + i);
         }
      }
      return (toString + "]");
   }
   
   //Sets the count of the given letter to the passed value.  
   //Letter might be lowercase or uppercase. 
   //If the character passed is negative or nonalphabeitic,  
   // throws an IllegalArgumentException
   //char letter: letter to be changed
   //int value: value for letter to be set to
   public void set(char letter, int value) {
      int count = findCount(letter);
      int oldValue = inventory[count];
      size = (size - oldValue) + value;
      inventory[count] = value;
   }
   
   //Creates and returns a new LetterInventory object that is the sum of this 
   // letter inventory and the "other" given LetterInventory.
   //LetterInventory other: the letterinventory to be added
   public LetterInventory add(LetterInventory other) {
      LetterInventory returnInventory = new LetterInventory("");
      returnInventory.size = size + other.size;
      for(int i = 0; i < NUMBER_CHARACTERS; i++) {
         returnInventory.inventory[i] = (inventory[i] + other.inventory[i]);
      }
      return returnInventory;
   }
   
   //Creates and returns a new LetterInventory object that is the result of 
   // subtracting the "other" inventory from this inventory
   //If any resulting count would be negative, returns null.
   //LetterInventory other: the letterinventory to be subtracted
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory returnInventory = new LetterInventory("");
      returnInventory.size = size - other.size;
      for(int i = 0; i < NUMBER_CHARACTERS; i++) {
         int difference = inventory[i] - other.inventory[i];
         if(difference >= 0) {
            returnInventory.inventory[i] = difference;
         } else {
            return null;
         }
         
      }
      return returnInventory;
   }
   
   //Returns the character "count" (value) for the character being passed through
   //char letter: letter to be identified
   private int findCount(char letter) {
      letter = Character.toLowerCase(letter);
      int count = letter - 'a';
      
      if(count > NUMBER_CHARACTERS || count < 0) {
         throw new IllegalArgumentException();
      }
      
      return count;
   }
}