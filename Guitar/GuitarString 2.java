// Samuel Fields
// 10/09/2019
// CSE143
// TA: Audrey Michelle
// Assignment #2
//
// This class allows the user to construct a GuitarString object. The GuitarString object 
//  models a vibrating guitar string of a given frequency.

import java.util.*;

public class GuitarString {
   private Queue<Double> ringBuffer;
   
   public final double DECAY_FACTOR = 0.996;

   // Constructs a GuitarString object of given frequency with a ring buffer of capacity 
   //    calculated by sampling rate divided by frequency, rounded to the nearest integer
   //    and initializes the ring buffer with all zeros
   // Throws IllegalArgumentException if:
   //    the given frequency <= 0
   //    the capacity of the ring buffer < 2
   public GuitarString(double frequency) {
      int capacity = (int) Math.round(StdAudio.SAMPLE_RATE / frequency);
      
      if (frequency <= 0 || capacity < 2) {
         throw new IllegalArgumentException();
      }
      
      ringBuffer = new LinkedList<Double>();
      for (int i = 0; i < capacity; i++) {
         ringBuffer.add(0.0);
      }
   }
   
   // Constructs a guitar string and initializes the contents of the ring buffer 
   //    to the values in the double array init
   // Throws IllegalArgumentException if:
   //    array init has fewer than two elements
   public GuitarString(double[] init) {
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      
      ringBuffer = new LinkedList<Double>();
      
      for (double num : init) {
         ringBuffer.add(num);
      }
   }
   
   // Replaces the elements in the ring buffer with random values 
   //    between -0.5 <= value < 0.5
   public void pluck() {
      Random rand = new Random();
      
      for (int i = 0; i < ringBuffer.size(); i++) {
         double num = (.5 * rand.nextDouble() - .5);
         ringBuffer.remove();
         ringBuffer.add(num);
      }
   }
   
   // Applies the Karplus-Strong update once:
   //    deletes the sample at the front of the ring buffer and adds to the end of 
   //    the ring buffer the average of the first two samples, multiplied by the 
   //    energy decay factor (0.996)
   public void tic() {
      double num1 = ringBuffer.remove();
      double num2 = ringBuffer.peek();
      
      ringBuffer.add(DECAY_FACTOR * 0.5 * (num1 + num2));
   }
   
   // Returns the current sample (the value at the front of the ring buffer)
   public double sample() {
      return ringBuffer.peek();
   }
}