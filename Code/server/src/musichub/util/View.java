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


/** JMusicHub Class is the main class of the JMusicHub program.
*
*
* Version : 1.0
*
* Date : 30/02/2001
*
* @author Gaël Lejeune and Steve Chauvreau-Manat (based on the work of Angélique Proux & Manelle Nouar)
*/
public interface View {

    /**
    * Execution of the JMusicHub program and interaction with the user using a terminal
    * @param       args Arguments of the function
    * @author Gaël Lejeune and Steve Chauvreau-Manat
    */
    public void display();
}
