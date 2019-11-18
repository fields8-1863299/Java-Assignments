// Samuel Fields
// 10/09/2019
// CSE143
// TA: Audrey Michelle
// Assignment #2
//
// This class allows the user to construct a Guitar37 object. The Guitar37 object 
//  models a guitar with 37 different strings.

public class Guitar37 implements Guitar {
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
                    
   private GuitarString[] strings;
   private int timeCount;
   
   // Constructs the Guitar37 object
   public Guitar37() {
      strings = new GuitarString[KEYBOARD.length()];
      timeCount = 0;
       
      for (int i = 0; i < KEYBOARD.length(); i++) {        
         strings[i] = new GuitarString(440.0 * Math.pow(2, (i - 24) / 12.0));
      } 
   }

   // Pluck's GuitarString at the index given by pitch, with pitch starting at
   //    -24 and going until 12
   public void playNote(int pitch) {
      if (pitch >= -24 && pitch <= 12) {
         strings[pitch + 24].pluck();  
      }
   }

   // Returns true if the passed char string exists within the modeled keyboard
   //    and false otherwise
   public boolean hasString(char string) {
      return KEYBOARD.indexOf(string) != -1;
   }
   
   // Plucks the GuitarString object at the index determined by the position of 
   //    char string in the modeled keyboard.
   // Throws IllegalArgumentException if the char string does not exist within
   //    the modeled keyboard
   public void pluck(char string) {
      if (KEYBOARD.indexOf(string) == -1) {
         throw new IllegalArgumentException();
      }
      
      strings[KEYBOARD.indexOf(string)].pluck();
   }

   // Returns the sum of all the current samples.
   public double sample() {
      double sum = 0;
      
      for (int i = 0; i < KEYBOARD.length(); i++) {
         sum += strings[i].sample();
      }
      
      return sum;
   }

   // Applies the Karplus-Strong update to all the GuitarString elements and increments
   //    timeCount
   public void tic() {
      for (int i = 0; i < KEYBOARD.length(); i++) {
         strings[i].tic();
      }
      
      timeCount++;
   }
   
   // Returns the timeCount
   public int time() {
      return timeCount;
   }   
}