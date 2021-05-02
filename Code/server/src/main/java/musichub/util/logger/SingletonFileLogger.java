/*
 * Class' name : SingletonFileLogger
 *
 * Description : Class is a file logger and record messages and errors in a log file
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.logger;

import java.util.*;
import java.io.*;
import java.sql.Timestamp;

/**
 * SingletonFileLogger Class is a file logger and record messages and errors in a log file
 *
 * Version : 1.0
 *
 * @see ILogger
 * @author Gaël Lejeune (Based on the work of Steve Chauvreau-Manat)
 */
public class SingletonFileLogger implements ILogger
{
    /**
     * Execution path of the program
     */
    private final String DIR = System.getProperty("user.dir");

    /**
     * log file path
     */
    private final String LOGS_FILE_PATH = "log.txt";

    /**
     * unique SingletonFileLogger
     */
    private static SingletonFileLogger logger = null;

    /**
     * SingletonConsoleLogger constructor
     *
     * @author      Gaël Lejeune
     */
    private SingletonFileLogger() {}

    /**
     * Acts as the singleton constructor and return an instance of SingletonFileLogger
     *
     * @return      SingletonFileLogger
     *
     * @author      Gaël Lejeune
     */
    public static synchronized SingletonFileLogger getInstance() {
        if (logger == null) //création d’instance unique
        logger = new SingletonFileLogger();
        return logger;
    }

    /**
     * Write the log with it's level and message
     *
     * @param       l importance level of the log to write
     * @param       message message of the log to write
     *
     * @author      Gaël Lejeune
     */
    public void write(Level l, String message) {
        writeToFile(l, message, LOGS_FILE_PATH);
    }

    /**
     * Write the log with it's level and message in the log file
     *
     * @param       l importance level of the log to write
     * @param       message message of the log to write
     * @param       fileName name of the log file
     *
     * @author      Gaël Lejeune
     */
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
