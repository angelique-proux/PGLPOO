package business.exceptions;

import java.lang.Exception;

/**
 * MissingFileException Class is the Exception thrown when the XML files aren't found in the "files" folder
 *
 * Version : 1.0
 *
 * @author Gaël Lejeune
 */
public class MissingFileException extends Exception {

    /**
     * MissingFileException constructor
     * @param message Message returned by the exception catch
     *
     * @author Gaël Lejeune
     */
    public MissingFileException(String message) {
        System.out.println(message);
    }
}
