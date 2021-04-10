/*
 * Nom de l'exception : NotACategoryException
 *
 * Description   : Exception qui gère les catégories des livres audio
 *
 * Date          : 03/01/2021
 *
 * Copyright     : Manelle & Angélique
 */


package business.exceptions;

import java.lang.Exception;

/**
 * NotACategoryException Class is the Exception thrown the category given does not exists
 *
 * Version : 1.0
 *
 * @author Manelle Nouar & Angélique Proux
 */
public class NotACategoryException extends Exception{

	/**
     * NotACategoryException constructor
     * @param titleAudioBook name of the audio book with a wrong category
     *
     * @author Angelique Proux
     */
	public NotACategoryException(String titleAudioBook) {
		System.out.println("False category in xml. Please change the category of " + titleAudioBook + ".");
	}
}
