// Samuel Fields
// 10/30/2019
// CSE143
// TA: Audrey Michelle
// Assignment #5
//
// This program allows the user to manipulate the grammar of a passed grammar
//  list according the nonterminals and terminals included within. Furthermore,
//  the phrases chosen from the nonterminals will be chosen at random with
//  equal probability.

import java.util.*;

public class GrammarSolver {
   private Map<String, String[]> grammarMap;
   
   // Constructs GrammarSolver conveniently storing the 
   //  passed grammar list so that it can generate the grammar parts.
   // Throws IllegalArgumentException if:
   //  the passed grammar list is empty
   //  two or more entries in the grammar list or the same nonterminal
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      
      grammarMap = new TreeMap<>();
      for (String word : grammar) {
         String[] parts1 = word.split("::="); // [key, phrases]
         
         //parts1[0] == key, parts1[1] == phrases
         if(grammarContains(parts1[0])) {
            throw new IllegalArgumentException();
         }
         
         String[] parts2 = parts1[1].split("\\|");
         grammarMap.put(parts1[0], parts2);
      }
   }
   
   // If the passed String symbol is a nonterminal in the grammar list
   //  returns true, else returns false
   public boolean grammarContains(String symbol) {
      return grammarMap.containsKey(symbol);
   }
   
   // Returns a string of the nonterminals in the grammar list
   //  in the following format: “[<nounp>, <sentence>, <verbp>]”
   public String getSymbols() {
      return grammarMap.keySet().toString();
   }
   
   // Uses the passed "symbol" nonterminal to "generate" "times" random occurences
   //  of the passed String "symbol"
   // Throws IllegalArgumentException if:
   //  grammar list does not contain passed symbol
   //  times is less than 0
   public String[] generate(String symbol, int times) {
      if (!grammarContains(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
   
      String[] words = new String[times];
      
      for (int i = 0; i < times; i++) {
         words[i] = generate(symbol);
      }
      
      return words;   
   }
   
   // Returns a terminal associated to the passed nonterminal "symbol"
   //  trimming off trailing or leading blank spaces
   private String generate(String symbol) {
      if (!grammarContains(symbol)) {
         return symbol;
      } //else
      
      String returnString = "";
      
      String[] values = grammarMap.get(symbol);
      Random rand = new Random();
      int randInt = rand.nextInt(values.length);
      String current = values[randInt];
      
      String[] splitCurrent = current.split("\\s+");
      
      for (int i = 0; i < splitCurrent.length; i++) {
         returnString += " " + generate(splitCurrent[i]);
      }
      
      return returnString.trim();
   }
}