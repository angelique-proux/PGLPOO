package musichub.util.logger;

/* Exemple d'utilisation du Singleton */

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;


public class TestSingleton {

  @Test
  void TestSingleton() {
    ILogger sfl = SingletonFileLogger.getInstance();



    try {
      Scanner scanner1 = new Scanner(new File("log.txt"));
      String line = null;

      sfl.write(Level.WARNING, "Ceci est un warning");
      while (scanner1.hasNextLine()) {
        line = scanner1.nextLine();
      }
      System.out.println(line);
      Pattern pattern = Pattern.compile("\\[[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]*\\] - WARNING - Ceci est un warning");
      assertTrue(pattern.matcher(line).matches());
      scanner1.close();

      Scanner scanner2 = new Scanner(new File("log.txt"));
      line = null;
      try {
        String str = "Ceci est une chaine de char";
        int i = Integer.parseInt(str);
        System.out.println(i);
      } catch (NumberFormatException e) {
        sfl.write(Level.ERROR, "Ceci est une erreur : "+e.getMessage());
      }
      while (scanner2.hasNextLine()) {
        line = scanner2.nextLine();
      }
      System.out.println(line);
      pattern = Pattern.compile("\\[[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]*\\] - ERROR - Ceci est une erreur :.*");
      assertTrue(pattern.matcher(line).matches());
      scanner2.close();


      Scanner scanner3 = new Scanner(new File("log.txt"));
      line = null;
      sfl.write(Level.INFO, "Ceci est une info");
      while (scanner3.hasNextLine()) {
        line = scanner3.nextLine();
      }
      System.out.println(line);
      pattern = Pattern.compile("\\[[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]*\\] - INFO - Ceci est une info");
      assertTrue(pattern.matcher(line).matches());
      scanner3.close();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }
}
