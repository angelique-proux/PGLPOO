/*
 * Exception's name : NotAnExistingAlbum
 *
 * Description  		: Class is the Exception thrown the album given does not exists
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
 * NotAnExistingAlbum Class is the Exception thrown the album given does not exists
 *
 * Version : 1.0
 *
 * @author	SManelle Nouar and Angélique Proux
 */
public class NotAnExistingAlbum extends Exception{

	/**
     * NotAnExistingAlbum constructor
     *
     * @author	Angelique Proux
     */
	public NotAnExistingAlbum() {
		System.out.printf("\n\nPlease, chose an existing album.\n");
	}
}
