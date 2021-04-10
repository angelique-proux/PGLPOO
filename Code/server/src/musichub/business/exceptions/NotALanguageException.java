/*
 * Nom de l'exception : NotALanguageException
 *
 * Description   : Exception qui gère les languages des livres audio
 *
 * Date          : 03/01/2021
 *
 * Copyright     : Manelle & Angélique
 */


package business.exceptions;

import java.lang.Exception;

/**
 * NotALanguageException Class is the Exception thrown the language given does not exists
 *
 * Version : 1.0
 *
 * @author Manelle Nouar & Angélique Proux
 */
public class NotALanguageException extends Exception{

	/**
     * NotALanguageException constructor
     * @param titleAudioBook name of the audio book with a wrong language
     *
     * @author Angelique Proux
     */
	public NotALanguageException(String titleAudioBook) {
		System.out.println("False language in xml. Please change the language of " + titleAudioBook + ".");
	}
}
