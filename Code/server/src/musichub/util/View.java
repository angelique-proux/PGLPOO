/*
 * Interface's name : View
 *
 * Description      : Interface representing a base view
 *
 * Version          : 1.0
 *
 * Date             : 13/04/2021
 *
 * Copyright        : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

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
    *
    * @author       Gaël Lejeune and Steve Chauvreau-Manat
    */
    public void display();
}
