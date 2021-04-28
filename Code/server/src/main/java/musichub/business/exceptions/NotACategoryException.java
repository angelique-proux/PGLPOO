/*
 * Exception's name	: NotACategoryException
 *
 * Description   		: Class is the Exception thrown the category given does not exists
 *
 * Version 					: 1.0
 *
 * Date         	 	: 13/04/2021
 *
 * Copyright   			: Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.business.exceptions;

import java.lang.Exception;

/**
 * NotACategoryException Class is the Exception thrown the category given does not exists
 *
 * Version : 1.0
 *
 * @author	SManelle Nouar and Angélique Proux
 */
public class NotACategoryException extends Exception{

	/**
     * NotACategoryException constructor
     * @param	titleAudioBook name of the audio book with a wrong category
     *
     * @author	Angelique Proux
     */
	public NotACategoryException(String titleAudioBook) {
		System.out.println("False category in xml. Please change the category of " + titleAudioBook + ".");
	}
}
