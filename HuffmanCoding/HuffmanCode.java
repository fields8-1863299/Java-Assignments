// This class helps create the Huffman code for a specific message by using the 
//  Huffman algorithm.

import java.util.*;
import java.io.*;
public class HuffmanCode {
   private HuffmanNode overallRoot;
   
   // Constructs a new HuffmanCode by taking in an array of ASCII frequencies. 
   // frequencies - an array where frequences[i]is the frequency of ASCII value i
   public HuffmanCode(int[] frequencies) {
      PriorityQueue<HuffmanNode> huffQueue = new PriorityQueue<>();
      
      for (int i = 0; i < frequencies.length; i++) {
         if (frequencies[i] != 0) {
            huffQueue.add(new HuffmanNode(i, frequencies[i]));
         }
      }
      
      while (huffQueue.size() > 1) {
         HuffmanNode first = huffQueue.remove();
         HuffmanNode second = huffQueue.remove();
         HuffmanNode temp = new HuffmanNode(first, second); 
         huffQueue.add(temp);
      }
      
      overallRoot = huffQueue.peek();
   }
   
   // constructs a new HuffmanCode by reading in a .code file, the format of this file
   //  should be ascii val on the first line followed by the HuffmanCode val on the
   //  following line:
   //     (ASCII val)
   //     (Huffman Code val)
   //     .
   //     .
   //     .
   // Assumes Scanner is not null and that the data passed is in the described format
   public HuffmanCode(Scanner input) {
      overallRoot = new HuffmanNode(0, 0);
      
      while (input.hasNextLine()) {
         int ascii = Integer.parseInt(input.nextLine());
         String huffCode = input.nextLine();
         overallRoot = huffCodeHelper(overallRoot, ascii, huffCode, 0);
      }
   }
   
   // Helper method for the HuffmanCode(Scanner input) constructor.
   //  Takes a HuffmanNode, ascii value being considered, String version
   //   of the considered huffman code, and an int count representing an
   //   index on the huffman code
   // Returns a HuffmanNode
   private HuffmanNode huffCodeHelper(HuffmanNode root, int ascii, String huffCode, 
                                       int count) {
      if (count == huffCode.length()) {
         root = new HuffmanNode(ascii, 1);
      } else {
         if (root == null) {
            root = new HuffmanNode(0, 0);
         }
         if (huffCode.charAt(count) == '0') {
            root.left = huffCodeHelper(root.left, ascii, huffCode, count + 1);
         } else {
            root.right = huffCodeHelper(root.right, ascii, huffCode, count + 1);
         }
      }
      
      return root;
   }
   
   // Saves the current Huffman code to the output PrintStream in the format ascii val 
   //  on the first line followed by the HuffmanCode val on the following line:
   //     (ASCII val)
   //     (Huffman Code val)
   //     .
   //     .
   //     .
   public void save(PrintStream output) {
      saveHelper(overallRoot, output, "");
   }
   
   // Helper method for the save method. Takes in a HuffmanNode, output PrintStream,
   //  and the String huffCode and prints out the Huffman code to the PrintStream
   private void saveHelper(HuffmanNode root, PrintStream output, String huffCode) {
      if (root != null) {
         if (root.left == null && root.right == null) { //leaf
            output.println(root.ascii);
            output.println(huffCode);
         } else {
            saveHelper(root.left, output, huffCode + "0");
            saveHelper(root.right, output, huffCode + "1");
         }
      }
   }
   
   // Reads in bits from the input BitInputStream and translates the bits using
   //  the Huffman code and writes it out into the output PrintStream.
   // Assumes that the input BitInputStream is encoded properly for the Huffman code.
   public void translate(BitInputStream input, PrintStream output) {
      HuffmanNode temp = overallRoot;

      while (input.hasNextBit()) {
         int bit = input.nextBit();
            
         if (bit == 0) {
            temp = temp.left;
         } else {
            temp = temp.right;
         }
         
         if (temp != null && temp.left == null && temp.right == null) { //leaf
            char test = (char)temp.ascii;
            output.write(test);
            temp = overallRoot;
         }
         
      }
   }
    
   // Constructs a HuffmanNode to be used by the HuffmanCode class  
   private static class HuffmanNode implements Comparable<HuffmanNode> {
      public int ascii;
      public int freq;
      public HuffmanNode left;
      public HuffmanNode right;
      
      // Constructs a HuffmanNode, takes in an int ascii value and an int freq of that 
      //  value and sets the left and right nodes of this node as null.
      public HuffmanNode(int ascii, int freq) {
         this(ascii, freq, null, null);
      }
      
      // Constructs a HuffmanNode, takes in the left and right of this node and sets the 
      //  acii value as 0 and sets the freq to the sum of the passed left and right 
      //  nodes freq.
      public HuffmanNode(HuffmanNode left, HuffmanNode right) {
         this(0, left.freq + right.freq, left, right);
      }
      
      // Constructs a HuffmanNode with the passed int ascii value, int freq value,
      //  left and right nodes.
      public HuffmanNode(int ascii, int freq, HuffmanNode left, HuffmanNode right) {
         this.ascii = ascii;
         this.freq = freq;
         this.left = left;
         this.right = right;
      }
      
      // Compares this node to a passed "other" HuffmanNode. Returns pos if this freq
      //  is greater than the other nodes freq, 0 if they're equal, and neg if 
      //  this nodes freq is less than  the other nodes freq.
      public int compareTo(HuffmanNode other) {
         return this.freq - other.freq;
      }
   }
}