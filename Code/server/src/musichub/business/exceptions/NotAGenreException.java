/*
 * Exception's name : NotAGenreException
 *
 * Description   		: Class is the Exception thrown the category given does not exists
 *
 * Version       		: 1.0
 *
 * Date          		: 13/04/2021
 *
 * Copyright   			: Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
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
public class NotAGenreException extends Exception{

	/**
     * NotAGenreException constructor
     * @param	titleSong name of the song with a wrong genre
     *
     * @author	Angelique Proux
     */
	public NotAGenreException(String titleSong) {
		System.out.println("False genre in xml. Please change the genre of " + titleSong + ".");
	}
}
