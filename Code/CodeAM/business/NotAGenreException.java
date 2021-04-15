/*
* Name of exception : NotAGenreException
*
* Description   : Exception that manages the genres of songs
*
* Date          : 03/01/2021
*
* Copyright     : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
*/


package business;

// Packages from java
import java.lang.Exception;


public class NotAGenreException extends Exception {

	/**
	* Exception showing genre error handling mechanism for songs
	*/
	public NotAGenreException(String titleSong) {
		System.out.println("False genre in xml. Please change the genre of " + titleSong + ".");
	}
}
