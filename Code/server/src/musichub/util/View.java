package util;

import business.*;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * View Interface is the interface representing a base view
 *
 * Version : 1.0
 *
 * @author Gaël Lejeune
 */
public interface View {

    /**
    * Execution of the JMusicHub program and interaction with the user using a terminal
    * @param       args Arguments of the function
    * @author Gaël Lejeune and Steve Chauvreau-Manat
    */
    public void display();
}
