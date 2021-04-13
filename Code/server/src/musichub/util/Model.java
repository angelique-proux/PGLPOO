/*
 * Interface's name : Model
 *
 * Description   		: Interface is the interface representing a base model
 *
 * Version       		: 1.0
 *
 * Date		          : 13/04/2021
 *
 * Copyright		    : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux
 */

package util;

import business.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.text.Normalizer;

/**
 * Model Interface is the interface representing a base model
 *
 * Version : 1.0
 *
 * @author Gaël Lejeune
 */
public interface Model {

	/**
	 * Transforms the given element into a file
	 *
	 * @param document document to transform
	 * @param filePath path where the file will be created
	 *
	 * @author Felicia Ionascu
	 */
	public void createXMLFile(Document document, String filePath);

	/**
	 * Create the xml document
	 *
	 * @return the created document
	 *
  	 * @author Felicia Ionascu
 	 */
	public Document createXMLDocument();

	/**
	* Return a NodeList based on the XML elements of the file
	*
	* @param filePath Path of the file to read and parse
	*
	* @return List of the read nodes
	*/
	public NodeList parseXMLFile (String filePath);
}
