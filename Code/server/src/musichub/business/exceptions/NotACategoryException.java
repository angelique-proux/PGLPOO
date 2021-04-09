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

public class NotACategoryException extends Exception{

	public NotACategoryException(String titleAudioBook) {
		System.out.println("False category in xml. Please change the category of " + titleAudioBook + ".");
	}
}
