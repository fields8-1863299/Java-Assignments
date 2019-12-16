// This program constructs a QuestionsGame to assist with the functions
//  of a game of 20 questions; however, the game is not limited to 20 questions.
//  This object keeps track of the questions and answers of the game which can
//  be modified if the computer guesses wrong.

import java.io.*;
import java.util.*;
public class QuestionsGame {
   private QuestionNode overallRoot;
   private Scanner console;
   
   // Constructs a new QuestionsGame
   public QuestionsGame() {
      overallRoot = new QuestionNode("A:", "computer");
      console = new Scanner(System.in);
   }

   // Reads in and replaces the current Q&A's with a new file passed throught "input"
   // Assumes input file is in correct format
   public void read(Scanner input) {
      overallRoot = readHelper(input);
   }
   
   // Helper method to read. Returns a QuestionNode dependent on the data in "input"
   // Assumes input file is in correct format.
   private QuestionNode readHelper(Scanner input) {
      String type = input.nextLine();
      String phrase = input.nextLine();
      QuestionNode curr = new QuestionNode(type, phrase);
         
      if (type.equals("Q:")) { 
         curr.left = readHelper(input);
         curr.right = readHelper(input);
      }
         
      return curr;
   }
   
   // Stores the current Q&A's into a PrintStream output file.
   public void write(PrintStream output) {
      writeHelper(overallRoot, output);
   }
   
   // Helper method to write. Takes in a QuestionNode "root" and prints
   //  its data to the passed output file.
   private void writeHelper(QuestionNode root, PrintStream output) {
      output.println(root.type);
      output.println(root.data);
      if (root.type.equals("Q:")) {
         writeHelper(root.left, output);
         writeHelper(root.right, output);
      }
   }
   
   // Plays one game of 20 questions with the user. If computer wins, prints 
   //  out: "Great, I got it right!". Else, adds the users object to overall data
   //  with a question to distinguish it.
   public void askQuestions() {
      overallRoot = askQuestionsHelper(overallRoot);
   }
   
   // Plays a single round of 20 questions. If the root QuestionNode passed is currently
   //  on an answer, will print out "Great, I got it right!" if computer guessed user's 
   //  object correctly, if not will add the object and a distinguishing question
   //  to the programs overall database. If root is a question, will prompt a y/n
   //  question and continue on that decision. 
   private QuestionNode askQuestionsHelper(QuestionNode root) {
      if (root.type.equals("A:")) {//answer node
         String prompt = "Would your object happen to be " + root.data + "?";
         if (yesTo(prompt)) { //correct guess
            System.out.println("Great, I got it right!");
         } else { //incorrect
            System.out.print("What is the name of your object? ");
            String answerData = console.nextLine();
            
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String questionData = console.nextLine();
            
            QuestionNode curr = new QuestionNode("A:", answerData);
            
            boolean yes = yesTo("And what is the answer for your object?");
            
            if (yes) {
               root = new QuestionNode("Q:", questionData, curr, root);
            } else {
               root = new QuestionNode("Q:", questionData, root, curr);
            }
         }
      } else { //not answer node
         String prompt = root.data;
         boolean yes = yesTo(prompt);
         
         if (yes) {
            root.left = askQuestionsHelper(root.left);
         } else {
            root.right = askQuestionsHelper(root.right);
         }
      }
      
      return root;
   }

    // asks the user a question, forcing an answer of "y" or "n";
    //   returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
          System.out.println("Please answer y or n.");
          System.out.print(prompt + " (y/n)? ");
          response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }

   // Constructs a QuestionNode object to be used by the QuestionGame class.
   private static class QuestionNode {
      public String type;
      public String data;
      public QuestionNode left;
      public QuestionNode right;
      
      // Constructs a QuestionNode object with given Strings type and data 
      public QuestionNode(String type, String data) {
         this(type, data, null, null);
      }
      
      // Constructs a QuestionNode object with given Strings type and data and given
      //  QuestionNodes left and right
      public QuestionNode(String type, String data, QuestionNode left, QuestionNode right) {
         this.type = type;
         this.data = data;
         this.left = left;
         this.right = right;  
      }
   }
}
