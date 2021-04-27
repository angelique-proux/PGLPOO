/*
 * Exception's name : NotALanguageException
 *
 * Description   		: Class is the Exception thrown the language given does not exists
 *
 * Version       		: 1.0
 *
 * Date          		: 13/04/2021
 *
 * Copyright     		: Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.business.exceptions;

import java.lang.Exception;

/**
 * NotALanguageException Class is the Exception thrown the language given does not exists
 *
 * Version : 1.0
 *
 * @author SManelle Nouar and Angélique Proux
 */
public class NotALanguageException extends Exception{

	/**
     * NotALanguageException constructor
     * @param	titleAudioBook name of the audio book with a wrong language
     *
     * @author	Angelique Proux
     */
	public NotALanguageException(String titleAudioBook) {
		System.out.println("False language in xml. Please change the language of " + titleAudioBook + ".");
	}
}
