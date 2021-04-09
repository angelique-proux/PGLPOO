package util;

import java.util.*;
import java.io.*;
import java.sql.Timestamp;

public class SingletonConsoleLogger implements ILogger
{
    private static SingletonConsoleLogger logger = null;

    private SingletonConsoleLogger(){

    }

    public static synchronized SingletonConsoleLogger getInstance() {
        if (logger == null) //création d’instance unique
        logger = new SingletonConsoleLogger();
        return logger;
    }
    public void write(Level l, String message) {
        writeAtTerminal(l, message);
    }
    public static void writeAtTerminal (Level l, String message) {
        System.out.println ("[" + new Timestamp(new Date().getTime()).toString() + "] - " + l + " - " + message);
    }
}
