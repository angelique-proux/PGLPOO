package musichub.util.logger;

/* Exemple d'utilisation du Singleton */

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;


public class TestSingleton {

  @Test
  void TestSingleton() {
    ILogger sfl = SingletonFileLogger.getInstance();

    sfl.write(Level.WARNING, "Ceci est un warning");

    try {
      String str = "Ceci est une chaine de char";
      int i = Integer.parseInt(str);
      System.out.println(i);
    } catch (NumberFormatException e) {
      sfl.write(Level.ERROR, "Ceci est une erreur"+e.getMessage());
    }

    sfl.write(Level.INFO, "Ceci est une info");
    // try {
    //   Scanner scanner = new Scanner(new File("log.txt"));
    //   String line = null;
    //   while (scanner.hasNextLine()) {
    //     line = scanner.nextLine();
    //   }
    //   // dans line, tu as ta dernière ligne
    //   System.out.println(line);
    //   scanner.close();
    // } catch (FileNotFoundException e) {
    //   System.out.println(e.getMessage());
    // }
  }
}
