// Samuel Fields
// 10/23/2019
// CSE143
// TA: Audrey Michelle
// Assignment #4
//
// This class allows the user to manage a game of Hangman. It includes methods
//  essential to the mechanisisms of the game.

import java.util.*;

public class HangmanManager {
   private int max;
   private Set<Character> guesses;
   private Map<String, TreeSet<String>> current;
   private String bestKey;
   
   // Initializes the state of the game with reference to the dictionary passed, 
   //  the length of word desired, and the max number of guesses allowed. 
   //  Initializes the set of words considered and ignores any duplicates.
   // dictionary: Collection of words, assumed non-empty Strings, all lowercase
   // length: a target word length
   // max: max number of wrong guesses a player can make
   // Throws IllegalArgumentException if:
   //  length < 1 or max < 0
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      this.max = max;
      
      if (length  < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      
      guesses = new TreeSet<Character>();
      current = new TreeMap<String, TreeSet<String>>();
      
      //Initialize the "best" key (key with most values)
      bestKey = "-"; //at least 1
      for (int i = 1; i < length; i++) {
         bestKey = bestKey + " -";
      }
      
      //Initialize current (the main) map with all words within dictionary
      current.put(bestKey, new TreeSet<String>());
      
      for (String word : dictionary) {
         if (word.length() == length && !current.get(bestKey).contains(word)) {
            current.get(bestKey).add(word);
         }
      } 
   }
   
   // Returns the current set of words being considered
   public Set<String> words() {
      return current.get(bestKey);
   }
   
   // Returns the current number of guesses left
   public int guessesLeft() {
      //so doesn't add correct guesses
      int contains = 0;
      for (char letter : guesses) {
         if (bestKey.contains("" + letter)) {
            contains++;
         }
      }
      
      return (max - guesses.size() + contains);
   }
   
   // Returns the guessed characters
   public Set<Character> guesses() {
      return guesses;
   }
   
   // Returns the current string pattern of the hangman game considering
   //  all guesses made. Letters not guessed are displayed as a dash "-"
   //  and there are no leading or trailing spaces to the pattern.
   // Throws IllegalStateException if the set of words being considered is empty.
   public String pattern() {
      String returnKey = bestKey;
      for (String key : current.keySet()) {//go through keyset
         //compare set sizes of each key
         if (current.get(key).size() > current.get(returnKey).size()) {
            returnKey = key;//save key to largest set
         }
      }
      
      if (current.get(returnKey).isEmpty())  {
         throw new IllegalStateException();
      }
      
      return returnKey;
   }
   
   // Records the next guess made by the user and determines which set to use moving 
   //  forward. Updates the number of (wrong) guesses allowed to the user and returns
   //  the number of occurences of the currently guessed character in the current 
   //  pattern.
   // guess: char guessed by the user
   // Throws IllegalStateException if:
   //  guesses left  < 1 or the set of considered words is empty
   // Throws IllegalArgumentException if:
   //  the current guess has already been guessed previously
   public int record(char guess) {
      //throw exceptions
      if (guessesLeft() < 1 || words().isEmpty()) {
         throw new IllegalStateException();
      } else if (guesses.contains(guess)) {
         throw new IllegalArgumentException();
      }
      //add char to guess set
      guesses.add(guess);
      //Iterates threough map creating, adding, and removing words and keys as needed
      iterate(guess);
      //update bestKey to key of the largest existing set in current map
      bestKey = pattern();
      //update current map, only keeping the largest set and removing other sets
      Map<String, TreeSet<String>> temp = new TreeMap<>();
      temp.put(bestKey, current.get(bestKey));
      current = temp;
      
      //Actual recording part of method
      int record = 0;
      if (bestKey.contains("" + guess)) {
         for (int i = 0; i < bestKey.length(); i++) {
            if (bestKey.charAt(i) == guess) {
               record++;
            }
         }
      }
      return record;
   }
   
   // Iterates through (current) map creating, adding, and removing words and keys 
   //  as needed and updates the current key ("best" key) all considering the new
   //  character being guessed (char guess)
   private void iterate(char guess) {
      Iterator<String> words = current.get(bestKey).iterator();
      while (words.hasNext()) {
         String word = words.next();
         //current contains guess -> update key and add word to diff set in current 
         if (word.contains("" + guess)) {
            String tempKey = tempKey(word);
            //check if set exists for that key, makes new set if doesnt
            if (!current.containsKey(tempKey)) {
               current.put(tempKey, new TreeSet<String>());
            }
            //Adds word to key
            current.get(tempKey).add(word);
            //Removes word from old key (why we need an iterator)
            words.remove();
         } //else ignore and keep word in current key
      }
   }
   
   // Returns the pattern/key with reference to the word being passed and 
   //  considering all the guessed made by the user
   private String tempKey(String word) {
      String tempKey = "";
      //constructs new key, go through set guesses, "-" if not in set
      for (int i = 0; i < word.length(); i++) {
         char currentChar = word.charAt(i);
         if (guesses.contains(currentChar)) {
            for (char letter : guesses) {
               if (currentChar == letter) {
                  tempKey = tempKey + letter + " ";
               }
            }
         } else {
            tempKey = tempKey + "- ";
         }
      }
      //removes trailing space, wont give error bc length cant be < 1
      tempKey = tempKey.substring(0, tempKey.length() - 1);
      
      return tempKey;
   }
}