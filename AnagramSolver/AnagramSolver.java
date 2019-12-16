// This program constructs an AnagramSolver object which can be used to find
//  every combination of words in a given dictionary which contain the same letters
//  as a passed phrase.

import java.util.*;
public class AnagramSolver {

   private Map<String, LetterInventory> dictionaryMap;
   private List<String> dictionary;
   
   // Constructs the AnagramSolver object with the given passed dictionary list
   //  Assumes the dictionary is non-empty and does not contain any duplicates
   public AnagramSolver(List<String> dictionary) {
      this.dictionary = dictionary;
      dictionaryMap = new HashMap<>();
      for (String word : dictionary) {
         dictionaryMap.put(word, new LetterInventory(word));
      }
   }
   
   // Prints every combination of words included in the dictionary that are
   //  anagrams of the passed String "text" up to a count defined by int "max"
   //  If max is 0, this count is defined to be infinite
   // Throws an IllegalArgumentException if max is less than 0
   public void print(String text, int max) {
      Stack<String> s = new Stack<>();
      LetterInventory textInventory = new LetterInventory(text);
      Queue<String> consideredWords = new LinkedList<>();
      
      for (String word : dictionary) {
         if (textInventory.subtract(dictionaryMap.get(word)) != null) {
            consideredWords.add(word);
         }
      }
      
      printHelper(textInventory, max, s, consideredWords);
   }
   
   // Takes in the letterinventory to the String "text", the int max,
   //  a String stack s, and a TreeSet of all the words to be considered from
   //  the original dictionary and prints out the collection of anagrams to
   //  the original String "text"
   // Throws IllegalArgumentException if max is less than 0
   private void printHelper(LetterInventory textInventory, int max, 
                                    Stack<String> s, Queue<String> consideredWords) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
                              
      if (s.size() <= max || max == 0) { 
         //print out stack if empty inventory
         if (textInventory.isEmpty()) {
            System.out.println(s);
         } else {
            String currentText = textInventory.toString();
            
            //parse throught dictionary
            for (String word : consideredWords) {
               //if the string contains the word
               if (textInventory.subtract(dictionaryMap.get(word)) != null) {
                  s.push(word);
                  printHelper(textInventory.subtract(dictionaryMap.get(word)), max, s, 
                                 consideredWords);
                  s.pop();
               }
            }
         }
      }
   }
}