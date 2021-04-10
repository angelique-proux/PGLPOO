/*
 * Nom de l'exception : NotAnExistingAlbum
 *
 * Description   : Exception lorsqu'un album n'existe pas dans la liste
 *
 * Date          : 03/01/2021
 *
 * Copyright     : Manelle & Angélique
 */


package business.exceptions;

import java.lang.Exception;

/**
 * NotAnExistingAlbum Class is the Exception thrown the album given does not exists
 *
 * Version : 1.0
 *
 * @author Manelle Nouar & Angélique Proux
 */
public class NotAnExistingAlbum extends Exception{

	/**
     * NotAnExistingAlbum constructor
     *
     * @author Angelique Proux
     */
	public NotAnExistingAlbum() {
		System.out.printf("\n\nPlease, chose an existing album.\n");
	}
}
