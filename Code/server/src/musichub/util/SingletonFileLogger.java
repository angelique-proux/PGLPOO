package util;

import java.util.*;
import java.io.*;
import java.sql.Timestamp;

public class SingletonFileLogger implements ILogger
{
    private final String DIR = System.getProperty("user.dir");
    private final String LOGS_FILE_PATH = DIR + "\\log.txt";
    private static SingletonFileLogger logger = null;

    private SingletonFileLogger() {

    }

    public static synchronized SingletonFileLogger getInstance() {
        if (logger == null) //création d’instance unique
        logger = new SingletonFileLogger();
        return logger;
    }
    public void write(Level l, String message) {
        writeToFile(l, message, LOGS_FILE_PATH);
    }
    private void writeToFile(Level l, String message, String fileName) {
        try{
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("[" + new Timestamp(new Date().getTime()).toString() + "] - " + l + " - " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
