/*
 * Exception's name : NotAnExistingAlbum
 *
 * Description  		: Class is the Exception thrown the album given does not exists
 *
 * Version       		: 1.0
 *
 * Date          		: 13/04/2021
 *
 * Copyright     		: Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package business.exceptions;

import java.lang.Exception;

/**
 * NotAnExistingAlbum Class is the Exception thrown the album given does not exists
 *
 * Version : 1.0
 *
 * @author	Manelle Nouar & Angélique Proux
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
