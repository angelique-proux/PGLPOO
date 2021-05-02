package musichub.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.LinkedList;

/**
 * Class which checks Album
 *
 * @author      Angélique Proux
 */
public class TestAlbum {

    private Album album;

    @Test
    void testConstructor() {
        LinkedList<Song> songsForAlbum = new LinkedList<>();
        UUID randomForAlbum = UUID.randomUUID();
        album = new Album("album1", "artist1", 200, "02/05/2021", randomForAlbum, songsForAlbum);

        //test Album's constructor
        assertEquals(album.getTitle(), "album1");
        assertEquals(album.getArtist(), "artist1");
        assertEquals(album.getDuration(), 200);
        assertEquals(album.getReleaseDate(), "02/05/2021");
        assertEquals(album.getID(), randomForAlbum);
        assertEquals(album.getSongs(), songsForAlbum);
    }

    @Test
    void testAdd() {
        Song song1 = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        Song song2 = new Song("title2", "artist", 20, UUID.randomUUID(), "content", Genre.JAZZ);
        LinkedList<Song> songsForAlbum = new LinkedList<>();

        album = new Album("album1", "artist", 200, "02/05/2021", UUID.randomUUID(), songsForAlbum);

        //add songs to album
        album.addAudio(song1);
        album.addAudio(song2);

        //list of songs to check
        songsForAlbum.add(song1);
        songsForAlbum.add(song2);

        assertEquals(album.getSongs(), songsForAlbum);
    }

    @Test
    void testAddDuplicateSong() {
        Song song1 = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        LinkedList<Song> songsForAlbum = new LinkedList<>();

        album = new Album("album1", "artist", 180, "02/05/2021", UUID.randomUUID(), songsForAlbum);

        //add the song twice in album
        album.addAudio(song1);
        album.addAudio(song1);

        //add the song once in the list to check for correct code with duplicate songs
        songsForAlbum.add(song1);

        assertEquals(album.getSongs(), songsForAlbum);
    }

    @Test
    void testToString() {
        Song song1 = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        album = new Album("album1", "artist1", 200, "02/05/2021", UUID.randomUUID(), new LinkedList<>());
        album.addAudio(song1);

        //check to result of toString() method
        assertEquals(album.toString(), "Album album1\nBy artist1\nDuration 200\nReleased 02/05/2021\nSongs : \ntitle by artist\nDuration : 180\nGenre : Rap");
    }
}
