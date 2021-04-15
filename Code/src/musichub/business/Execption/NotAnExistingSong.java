/*
* Name of exception : NotAnExistingSong
*
* Description   : Exception when a music does not exist in the list
*
* Date          : 03/01/2021
*
* Copyright     : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
*/


package business;

// Packages from java
import java.lang.Exception;


public class NotAnExistingSong extends Exception {

	/**
	* Exception showing song error handling mechanism for music
	*/
	public NotAnExistingSong() {
		System.out.printf("\n\nPlease, chose an excisting song.\n");
	}
}
