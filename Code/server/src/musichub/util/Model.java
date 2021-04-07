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

/** JMusicHubModel class allowing to read and write xml files
 * Read the different XML of the jMusicHub program
 *
 * Version : 1.2
 *
 * Date : 30/02/2001
 *
 * @author Gaël Lejeune (Based on the work of Felicia Ionascu)
 */
public interface Model {

	/**
	 * Transforms the given element into a file
	 * @param document document to transform
	 * @param filePath path where the file will be created
	 *
	 * @author Felicia Ionascu
	 */
	public void createXMLFile(Document document, String filePath);

	/**
	 * Create the xml document
	 * @return the created document
	 *
  	 * @author Felicia Ionascu
 	 */
	public Document createXMLDocument();

	/**
	* Return a NodeList based on the XML elements of the file
	* @param filePath Path of the file to read and parse
	* @return List of the read nodes
	*/
	public NodeList parseXMLFile (String filePath);
}
