/*
* Name of exception : NotACategoryException
*
* Description   : Exception that manages audiobook categories
*
* Date          : 03/01/2021
*
* Copyright     : Angélique & Gaël & Steve
*/


package business;

// Packages from java
import java.lang.Exception;


public class NotACategoryException extends Exception {

	/**
	* Exception showing category error handling mechanism for audiobooks
	*/
	public NotACategoryException(String titleAudiobook) {
		System.out.println("False category in xml. Please change the category of " + titleAudiobook + ".");
	}
}
