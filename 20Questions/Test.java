import java.util.*;
import java.io.*;
public class Test {
   public static void main(String[] args) throws FileNotFoundException {
      QuestionsGame q = new QuestionsGame();
      Scanner tester = new Scanner(new File("spec-questions.txt"));
      q.read(tester);
   }
}