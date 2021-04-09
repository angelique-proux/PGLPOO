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

public class NotALanguageException extends Exception{

	public NotALanguageException(String titleAudioBook) {
		System.out.println("False language in xml. Please change the language of " + titleAudioBook + ".");
	}
}
