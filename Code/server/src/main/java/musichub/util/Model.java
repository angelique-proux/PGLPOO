/*
 * Interface's name : Model
 *
 * Description   	: Interface is the interface representing a base model
 *
 * Version       	: 1.0
 *
 * Date		        : 13/04/2021
 *
 * Copyright   		: Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util;

import musichub.business.*;
import musichub.business.exceptions.*;
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

	/**
	 * Read an element and convert it into a Song
	 *
	 * @param       element Element to turn into song
	 * @return			Song Read song
	 * @throws			NotAGenreException //TODO
	 *
	 * @see         Song
	 * @author      Gaël Lejeune
	 */
	public Song getSong(Element element) throws NotAGenreException;

	/**
	 * Read an element and convert it into an audio book
	 *
	 * @param       element Element to turn into audio book
	 * @return			Read audio book
	 * @throws			NotALanguageException //TODO
	 * @throws			NotACategoryException //TODO
	 *
	 * @see         AudioBook
	 * @author      Gaël Lejeune
	 */
	public AudioBook getAudioBook(Element element) throws NotACategoryException, NotALanguageException;

	/**
	 * Read an element and convert it into a playlist
	 *
	 * @param       element Element to turn into playlist
	 * @return		Read playlist
	 *
	 * @see         Playlist
	 * @author      Gaël Lejeune
	 */
	public Playlist getPlaylist(Element element);

	/**
	 * Read an element and convert it into an album
	 *
	 * @param       element Element to turn into album
	 * @return		Read album
	 *
	 * @see         Album
	 * @author      Gaël Lejeune
	 */
	public Album getAlbum(Element element);

	/**
	 * Read the playlist XML file and convert it into a list of playlist
	 *
	 * @param       file Path of the file to read
	 * @return			List of the read playlist
	 * @exception   MissingFileException Thrown if one of the xml files is missing.
	 *
	 * @see 				MissingFileException
	 * @author      Gaël Lejeune
	 */
	public LinkedList<Playlist> readPlaylistXML(String file) throws MissingFileException;

	/**
	 * Read the album XML file and convert it into a list of album
	 *
	 * @param       file Path of the file to read
	 * @return			List of the read album
	 * @exception   MissingFileException Thrown if one of the xml files is missing.
	 *
	 * @see					MissingFileException
	 * @author      Gaël Lejeune
	 */
	public LinkedList<Album> readAlbumXML(String file) throws MissingFileException;

	/**
	 * Read the element XML file and convert it into a list of element
	 *
	 * @param       file Path of the file to read
	 * @return			List of the read element
	 * @exception   MissingFileException Thrown if one of the xml files is missing.
	 *
	 * @see					MissingFileException
	 * @author      Gaël Lejeune
	 */
	public LinkedList<Audio> readElementXML(String file) throws MissingFileException;

	/**
	 * Convert a given song into an XML element
	 *
	 * @param       document XML document to write
	 * @param       song Song to convert
	 * @return			XML element created based on the song
	 *
	 * @author      Gaël Lejeune
	 */
	public Element writeSongXML(Document document, Song song);

	/**
	 * Convert a given audio book into an XML element
	 *
	 * @param       document XML document to write
	 * @param       audioBook Audio book to convert
	 * @return			XML element created based on the audio book
	 *
	 * @author      Gaël Lejeune
	 */
	public Element writeAudioBookXML(Document document, AudioBook audioBook);

	/**
	 * Convert a given album list into an XML file
	 *
	 * @param       outputFile Path of the XML file to create
	 * @param       albumList List of the albums to write
	 *
	 * @author      Gaël Lejeune
	 */
	public void writeAlbumXML(String outputFile, LinkedList<Album> albumList);

	/**
	 * Convert a given element list into an XML file
	 *
	 * @param       outputFile Path of the XML file to create
	 * @param       audioList List of the elements to write
	 *
	 * @author      Gaël Lejeune
	 */
	public void writeElementXML(String outputFile, LinkedList<Audio> audioList);

	/**
	 * Convert a given playlist list into an XML file
	 *
	 * @param       outputFile Path of the XML file to create
	 * @param       playlistList List of the playlists to write
	 *
	 * @author      Gaël Lejeune
	 */
	public void writePlaylistXML(String outputFile, LinkedList<Playlist> playlistList);
}
