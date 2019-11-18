public class LetterInventoryTestClient {
   public static void main(String[] args) {
      LetterInventory first = new LetterInventory("aabcde");
      LetterInventory second = new LetterInventory("jjjhhhhkkk");
      LetterInventory third = new LetterInventory("aabcdejjjhhhhkkk");//first.add(second);
      
      System.out.println("length is " + first.size());
   
      System.out.println("number of 'a's is " + first.get('a'));
   
      System.out.println("first to String is " + first.toString());
   }
}