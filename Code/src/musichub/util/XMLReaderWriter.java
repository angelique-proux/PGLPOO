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

/** XMLReaderWriter class allowing to read and write xml files
 * Read the different XML of the jMusicHub program
 *
 * Version : 1.2
 *
 * Date : 30/02/2001
 *
 * @author Gaël Lejeune (Based on the work of Felicia Ionascu)
 */

public class XMLReaderWriter{
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder documentBuilder;

	/**
	 * XMLReaderWriter constructor
	 *
	 * @author Felicia Ionascu
	 */
	public XMLReaderWriter() {
		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}

	/**
	 * Transforms the given element into a file
	 * @param document document to transform
	 * @param filePath path where the file will be created
	 *
	 * @author Felicia Ionascu
	 */
	public void createXMLFile(Document document, String filePath) {
		try {
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(filePath));

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging

			//transform the DOM Object to an XML File
			transformer.transform(domSource, streamResult);

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		System.out.println("Done creating XML File");
	}

	/**
	 * Create the xml document
	 * @return the created document
	 *
  	 * @author Felicia Ionascu
 	 */
	public Document createXMLDocument() {
		return documentBuilder.newDocument();
	}

	/**
	* Return a NodeList based on the XML elements of the file
	* @param filePath Path of the file to read and parse
	* @return List of the read nodes
	*/
	public NodeList parseXMLFile (String filePath) {
		NodeList elementNodes = null;
		try {
			Document document= documentBuilder.parse(new File(filePath));
			Element root = document.getDocumentElement();

			elementNodes = root.getChildNodes();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return elementNodes;
	}

	/**
	 * Read an element and convert it into a Song
	 * @param       element Element to turn into song
	 * @return		Read song
	 * @see         Song
	 * @author      Gaël Lejeune
	 */
	private Song getSong(Element element) {
		// NodeList song = node.getChildNodes();
		String title = element.getElementsByTagName("title").item(0).getTextContent();
		String artist = element.getElementsByTagName("artist").item(0).getTextContent();
		int duration = Integer.parseInt(element.getElementsByTagName("duration").item(0).getTextContent());
		UUID id;
		String uuid = "";
		try {
			uuid = element.getElementsByTagName("UUID").item(0).getTextContent();
		} catch (Exception ex) {
			System.out.println("Empty UUID, will create a new one");
		}
		if ((uuid == null) || (uuid.isEmpty())) {
			id = UUID.randomUUID();
		} else {
			id = UUID.fromString(uuid);
		}
		String content = element.getElementsByTagName("content").item(0).getTextContent();
		String genre = element.getElementsByTagName("genre").item(0).getTextContent();


		return new Song(title, artist, duration, id, content, Genre.valueOf(genre));
	}


	/**
	 * Read an element and convert it into an audio book
	 * @param       element Element to turn into audio book
	 * @return		Read audio book
	 * @see         AudioBook
	 * @author      Gaël Lejeune
	 */
	private AudioBook getAudioBook(Element element) {
		String title = element.getElementsByTagName("title").item(0).getTextContent();
		String author = element.getElementsByTagName("author").item(0).getTextContent();
		int duration = Integer.parseInt(element.getElementsByTagName("duration").item(0).getTextContent());
		UUID id;
		String uuid = "";
		try {
			uuid = element.getElementsByTagName("UUID").item(0).getTextContent();
		} catch (Exception ex) {
			System.out.println("Empty UUID, will create a new one");
		}
		if ((uuid == null) || (uuid.isEmpty())) {
			id = UUID.randomUUID();
		} else {
			id = UUID.fromString(uuid);
		}
		String content = element.getElementsByTagName("content").item(0).getTextContent();
		String category = element.getElementsByTagName("category").item(0).getTextContent();
		String language = element.getElementsByTagName("language").item(0).getTextContent();


		return new AudioBook(title, author, duration, id, content,Language.valueOf(language), Category.valueOf(category));
	}

	/**
	 * Read an element and convert it into a playlist
	 * @param       element Element to turn into playlist
	 * @return		Read playlist
	 * @see         Playlist
	 * @author      Gaël Lejeune
	 */
	private Playlist getPlaylist(Element element) {
		LinkedList<Audio> audioList = new LinkedList<Audio>();
		String name = "";
		UUID uniqueID = null;

		try {
			name = element.getElementsByTagName("name").item(0).getTextContent();
			String uuid = null;
			try {
				uuid = element.getElementsByTagName("UUID").item(0).getTextContent();
			} catch (Exception ex) {
				System.out.println("Empty UUID, will create a new one");
			}
			if ((uuid == null) || (uuid.isEmpty())) {
				uniqueID = UUID.randomUUID();
			} else {
				uniqueID = UUID.fromString(uuid);
			}
			NodeList audios = element.getElementsByTagName("audios").item(0).getChildNodes();
			for (int i = 0; i < audios.getLength(); i++) {
				if (audios.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element currentElement = (Element) audios.item(i);
					if (currentElement.getNodeName().equals("song")) {
						audioList.add(getSong(currentElement));
					} else if (currentElement.getNodeName().equals("audioBook")) {
						audioList.add(getAudioBook(currentElement));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Something is wrong with the XML client element");
		}
		return new Playlist(name, uniqueID, audioList);
	}

	/**
	 * Read an element and convert it into an album
	 * @param       element Element to turn into album
	 * @return		Read album
	 * @see         Album
	 * @author      Gaël Lejeune
	 */
	private Album getAlbum(Element element) {
		LinkedList<Song> songList = new LinkedList<Song>();
		String title = "";
		String artist = "";
		int duration = 0;
		String releaseDate = "";
		UUID uniqueID = null;

		try {
			title = element.getElementsByTagName("title").item(0).getTextContent();
			artist = element.getElementsByTagName("artist").item(0).getTextContent();
			duration = Integer.parseInt(element.getElementsByTagName("duration").item(0).getTextContent());
			releaseDate = element.getElementsByTagName("releaseDate").item(0).getTextContent();
			String uuid = null;
			try {
				uuid = element.getElementsByTagName("UUID").item(0).getTextContent();
			} catch (Exception ex) {
				System.out.println("Empty UUID, will create a new one");
			}
			if ((uuid == null) || (uuid.isEmpty())) {
				uniqueID = UUID.randomUUID();
			} else {
				uniqueID = UUID.fromString(uuid);
			}
			NodeList songs = element.getElementsByTagName("songs").item(0).getChildNodes();
			for (int i = 0; i < songs.getLength(); i++) {
				if (songs.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element currentElement = (Element) songs.item(i);
					songList.add(getSong(currentElement));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Something is wrong with the XML client element");
		}
		return new Album(title, artist, duration, releaseDate, uniqueID, songList);
	}

	/**
	 * Read the playlist XML file and convert it into a list of playlist
	 * @param       file Path of the file to read
	 * @return		List of the read playlist
	 * @exception   MissingFileException Thrown if one of the xml files is missing.
	 * @see MissingFileException
	 * @author      Gaël Lejeune
	 */
	public LinkedList<Playlist> readPlaylistXML(String file) throws MissingFileException {
		File playlistsFile = new File("files/playlists.xml");
		if(!playlistsFile.exists()) {
			throw new MissingFileException("Missing XML files,\nPlease check in the \"files\" folder and search for :\n    -playlists.xml\nThe program won't work if the file is missing.");
		}
		NodeList list = this.parseXMLFile(file);
		LinkedList<Playlist> playlistList = new LinkedList<Playlist>();

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element currentElement = (Element) list.item(i);
				if (currentElement.getNodeName().equals("playlist")) {
					playlistList.add(getPlaylist(currentElement));
				}
			}
		}
		return playlistList;
	}

	/**
	 * Read the album XML file and convert it into a list of album
	 * @param       file Path of the file to read
	 * @return		List of the read album
	 * @exception   MissingFileException Thrown if one of the xml files is missing.
	 * @see MissingFileException
	 * @author      Gaël Lejeune
	 */
	public LinkedList<Album> readAlbumXML(String file) throws MissingFileException {
		File albumsFile = new File("files/albums.xml");
		if(!albumsFile.exists()) {
			throw new MissingFileException("Missing XML files,\nPlease check in the \"files\" folder and search for :\n    -albums.xml\nThe program won't work if the file is missing.");
		}
		NodeList list = this.parseXMLFile(file);
		LinkedList<Album> albumList = new LinkedList<Album>();

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element currentElement = (Element) list.item(i);
				if (currentElement.getNodeName().equals("album")) {
					albumList.add(getAlbum(currentElement));
				}
			}
		}
		return albumList;
	}

	/**
	 * Read the element XML file and convert it into a list of element
	 * @param       file Path of the file to read
	 * @return		List of the read element
	 * @exception   MissingFileException Thrown if one of the xml files is missing.
	 * @see MissingFileException
	 * @author      Gaël Lejeune
	 */
	public LinkedList<Audio> readElementXML(String file) throws MissingFileException {
		File elementsFile = new File("files/elements.xml");
		if(!elementsFile.exists()) {
			throw new MissingFileException("Missing XML files,\nPlease check in the \"files\" folder and search for :\n    -elements.xml\nThe program won't work if the file is missing.");
		}
		NodeList list = this.parseXMLFile(file);
		LinkedList<Audio> elementList = new LinkedList<Audio>();

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element currentElement = (Element) list.item(i);
				if (currentElement.getNodeName().equals("song")) {
					elementList.add(getSong(currentElement));
				} else if (currentElement.getNodeName().equals("audioBook")) {
					elementList.add(getAudioBook(currentElement));
				}
			}
		}
		return elementList;
	}

	/**
	 * Convert a given song into an XML element
	 * @param       document XML document to write
	 * @param       song Song to convert
	 * @return		XML element created based on the song
	 * @author      Gaël Lejeune
	 */
	public Element writeSongXML(Document document, Song song) {
		Element songElement = document.createElement("song");

		String title = song.getTitle();
		Element titleElement = document.createElement("title");
		titleElement.appendChild(document.createTextNode(title));
		songElement.appendChild(titleElement);

		String artist = song.getArtist();
		Element artistElement = document.createElement("artist");
		artistElement.appendChild(document.createTextNode(artist));
		songElement.appendChild(artistElement);

		String duration = String.valueOf(song.getDuration());
		Element durationElement = document.createElement("duration");
		durationElement.appendChild(document.createTextNode(duration));
		songElement.appendChild(durationElement);

		UUID uniqueID = song.getID();
		Element songUUID = document.createElement("UUID");
		songUUID.appendChild(document.createTextNode(uniqueID.toString()));
		songElement.appendChild(songUUID);

		String content = song.getContent();
		Element contentElement = document.createElement("content");
		contentElement.appendChild(document.createTextNode(content));
		songElement.appendChild(contentElement);

		String genre = Normalizer.normalize(song.getGenre().toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
		Element genreElement = document.createElement("genre");
		genreElement.appendChild(document.createTextNode(genre));
		songElement.appendChild(genreElement);

		return songElement;
	}

	/**
	 * Convert a given audio book into an XML element
	 * @param       document XML document to write
	 * @param       audioBook Audio book to convert
	 * @return		XML element created based on the audio book
	 * @author      Gaël Lejeune
	 */
	public Element writeAudioBookXML(Document document, AudioBook audioBook) {
		Element audioBookElement = document.createElement("audioBook");

		String title = audioBook.getTitle();
		Element titleElement = document.createElement("title");
		titleElement.appendChild(document.createTextNode(title));
		audioBookElement.appendChild(titleElement);

		String author = audioBook.getAuthor();
		Element authorElement = document.createElement("author");
		authorElement.appendChild(document.createTextNode(author));
		audioBookElement.appendChild(authorElement);

		String duration = String.valueOf(audioBook.getDuration());
		Element durationElement = document.createElement("duration");
		durationElement.appendChild(document.createTextNode(duration));
		audioBookElement.appendChild(durationElement);

		UUID uniqueID = audioBook.getID();
		Element songUUID = document.createElement("UUID");
		songUUID.appendChild(document.createTextNode(uniqueID.toString()));
		audioBookElement.appendChild(songUUID);

		String content = audioBook.getContent();
		Element contentElement = document.createElement("content");
		contentElement.appendChild(document.createTextNode(content));
		audioBookElement.appendChild(contentElement);

		String language = Normalizer.normalize(audioBook.getLanguage().toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
		Element languageElement = document.createElement("language");
		languageElement.appendChild(document.createTextNode(language));
		audioBookElement.appendChild(languageElement);

		String category = Normalizer.normalize(audioBook.getCategory().toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
		Element categoryElement = document.createElement("category");
		categoryElement.appendChild(document.createTextNode(category));
		audioBookElement.appendChild(categoryElement);

		return audioBookElement;
	}

	/**
	 * Convert a given album list into an XML file
	 * @param       outputFile Path of the XML file to create
	 * @param       albumList List of the albums to write
	 * @author      Gaël Lejeune
	 */
	public void writeAlbumXML(String outputFile, LinkedList<Album> albumList) {
		Document document = this.createXMLDocument();
		if (document == null) return;


		Element root = document.createElement("albums");
		document.appendChild(root);

		for (int i = 0; i < albumList.size(); i++) {
			Album album = albumList.get(i);
			// System.out.println(album);
			Element albumElement = document.createElement("album");

			String title = album.getTitle();
			Element titleElement = document.createElement("title");
			titleElement.appendChild(document.createTextNode(title));
			albumElement.appendChild(titleElement);

			String artist = album.getArtist();
			Element artistElement = document.createElement("artist");
			artistElement.appendChild(document.createTextNode(artist));
			albumElement.appendChild(artistElement);

			String duration = String.valueOf(album.getDuration());
			Element durationElement = document.createElement("duration");
			durationElement.appendChild(document.createTextNode(duration));
			albumElement.appendChild(durationElement);

			String releaseDate = album.getReleaseDate();
			Element releaseDateElement = document.createElement("releaseDate");
			releaseDateElement.appendChild(document.createTextNode(releaseDate));
			albumElement.appendChild(releaseDateElement);

			UUID uniqueID = album.getID();
			Element songUUID = document.createElement("UUID");
			songUUID.appendChild(document.createTextNode(uniqueID.toString()));
			albumElement.appendChild(songUUID);

			Element songs = document.createElement("songs");
			LinkedList<Song> songList = album.getSongs();
			for (int j = 0; j < songList.size(); j++) {
				if (songList.get(j) instanceof Song) {
					songs.appendChild(writeSongXML(document, (Song)songList.get(j)));
				}
			}
			albumElement.appendChild(songs);
			root.appendChild(albumElement);
		}
		this.createXMLFile(document, outputFile);
	}

	/**
	 * Convert a given element list into an XML file
	 * @param       outputFile Path of the XML file to create
	 * @param       audioList List of the elements to write
	 * @author      Gaël Lejeune
	 */
	public void writeElementXML(String outputFile, LinkedList<Audio> audioList) {
		Document document = this.createXMLDocument();
		if (document == null) return;

		// create root element
		Element root = document.createElement("elements");
		document.appendChild(root);

		for (int i = 0; i < audioList.size(); i++) {
			if (audioList.get(i) instanceof Song) {
				root.appendChild(writeSongXML(document, (Song)audioList.get(i)));
			} else if (audioList.get(i) instanceof AudioBook) {
				root.appendChild(writeAudioBookXML(document, (AudioBook)audioList.get(i)));
			}
		}
		this.createXMLFile(document, outputFile);
	}

	/**
	 * Convert a given playlist list into an XML file
	 * @param       outputFile Path of the XML file to create
	 * @param       playlistList List of the playlists to write
	 * @author      Gaël Lejeune
	 */
	public void writePlaylistXML(String outputFile, LinkedList<Playlist> playlistList) {
		Document document = this.createXMLDocument();
		if (document == null) return;


		Element root = document.createElement("playlists");
		document.appendChild(root);

		for (int i = 0; i < playlistList.size(); i++) {
			Playlist playlist = playlistList.get(i);
			// System.out.println(album);
			Element playlistElement = document.createElement("playlist");

			String name = playlist.getName();
			Element nameElement = document.createElement("name");
			nameElement.appendChild(document.createTextNode(name));
			playlistElement.appendChild(nameElement);

			UUID uniqueID = playlist.getID();
			Element songUUID = document.createElement("UUID");
			songUUID.appendChild(document.createTextNode(uniqueID.toString()));
			playlistElement.appendChild(songUUID);

			Element audios = document.createElement("audios");
			LinkedList<Audio> audiosList = playlist.getAudios();
			for (int j = 0; j < audiosList.size(); j++) {
				if (audiosList.get(j) instanceof Song) {
					audios.appendChild(writeSongXML(document, (Song)audiosList.get(j)));
				} else if (audiosList.get(j) instanceof AudioBook) {
					audios.appendChild(writeAudioBookXML(document, (AudioBook)audiosList.get(j)));
				}
			}
			playlistElement.appendChild(audios);
			root.appendChild(playlistElement);
		}
		this.createXMLFile(document, outputFile);
	}
}
