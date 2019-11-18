import java.util.*;
import java.io.*;
public class HuffTest {
   public static void main(String[] args) throws IOException {
      //HuffmanCode test = new HuffmanCode(new Scanner(new File("test.code")));
      //test.save(new PrintStream(new File("testtest.code")));
      //BitInputStream stream = new BitInputStream("test.short");
      //PrintStream testPrintStream = new PrintStream(new File("testOutputStream.txt"));
      //BitOutputStream outputStream = new BitOutputStream(testPrintStream, false);
      //test.translate(stream, new PrintStream(new File("test.txt")));

      HuffmanCompressor compress = new HuffmanCompressor("test.txt");
      compress.makeCode();
   }
}