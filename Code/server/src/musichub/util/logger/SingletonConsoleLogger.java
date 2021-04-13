/*
 * Class' name : SingletonConsoleLogger
 *
 * Description : Class is a console logger and display messages and errors on the console
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package util.logger;

import java.util.*;
import java.io.*;
import java.sql.Timestamp;

/**
 * SingletonConsoleLogger Class is a console logger and display messages and errors on the console
 *
 * Version : 1.0
 *
 * @see ILogger
 * @author Gaël Lejeune (Based on the work of Steve Chauvreau-Manat)
 */
public class SingletonConsoleLogger implements ILogger
{
    /**
     * unique SingletonConsoleLogger
     */
    private static SingletonConsoleLogger logger = null;

    /**
     * SingletonConsoleLogger constructor
     *
     * @author      Gaël Lejeune
     */
    private SingletonConsoleLogger() {}

    /**
     * Acts as the singleton constructor and return an instance of SingletonConsoleLogger
     *
     * @return      SingletonConsoleLogger
     *
     * @author      Gaël Lejeune
     */
    public static synchronized SingletonConsoleLogger getInstance() {
        if (logger == null) //création d’instance unique
        logger = new SingletonConsoleLogger();
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
        writeAtTerminal(l, message);
    }

    /**
     * Write the log with it's level and message on the console
     *
     * @param       l importance level of the log to write
     * @param       message message of the log to write
     *
     * @author      Gaël Lejeune
     */
    public static void writeAtTerminal (Level l, String message) {
        System.out.println ("[" + new Timestamp(new Date().getTime()).toString() + "] - " + l + " - " + message);
    }
}
