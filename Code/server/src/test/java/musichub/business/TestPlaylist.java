package musichub.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.LinkedList;

/**
 * Class which checks Playlist
 *
 * @author      Angélique Proux
 */
public class TestPlaylist {

    private Playlist list;

    @Test
    void testConstructor() {
        LinkedList<Audio> audiosForPlayList = new LinkedList<>();
        UUID randomForPlayList = UUID.randomUUID();
        list = new Playlist("list1", randomForPlayList, audiosForPlayList);

        //test Playlist's constructor
        assertEquals(list.getAudios(), audiosForPlayList);
        assertEquals(list.getName(), "list1");
        assertEquals(list.getID(), randomForPlayList);
    }

    @Test
    void testAdd() {
        Song song1 = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        AudioBook book1 = new AudioBook("title", "author", 120, UUID.randomUUID(), "path", Language.FRANCAIS, Category.JEUNESSE);
        LinkedList<Audio> audiosForPlayList = new LinkedList<>();

        list = new Playlist("list1", UUID.randomUUID(), audiosForPlayList);

        //add audios to playlist
        list.addAudio(song1);
        list.addAudio(book1);

        //list of audio to check
        audiosForPlayList.add(song1);
        audiosForPlayList.add(book1);

        assertEquals(list.getAudios(), audiosForPlayList);
    }

    @Test
    void testAddDuplicateSong() {
        Song song1 = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        LinkedList<Audio> audiosForPlayList = new LinkedList<>();

        list = new Playlist("list1", UUID.randomUUID(), audiosForPlayList);

        //add the song twice in playlist
        list.addAudio(song1);
        list.addAudio(song1);

        //add the song once in the list to check for correct code with duplicate audios
        audiosForPlayList.add(song1);

        assertEquals(list.getAudios(), audiosForPlayList);
    }

    @Test
    void testRemove() {
        Song song1 = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        LinkedList<Audio> audiosForPlayList = new LinkedList<>();

        list = new Playlist("list1", UUID.randomUUID(), audiosForPlayList);

        //add song to playlist then remove it
        list.addAudio(song1);
        list.removeAudio(song1);

        assertEquals(list.getAudios(), audiosForPlayList);
    }

    @Test
    void testRemoveNotExistingAudio() {
        Song song1 = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        AudioBook book1 = new AudioBook("title", "author", 120, UUID.randomUUID(), "path", Language.FRANCAIS, Category.JEUNESSE);
        LinkedList<Audio> audiosForPlayList = new LinkedList<>();
        audiosForPlayList.add(song1);

        list = new Playlist("list1", UUID.randomUUID(), audiosForPlayList);

        //remove a not existing book from playlist
        list.removeAudio(book1);

        assertEquals(list.getAudios(), audiosForPlayList);
    }

    @Test
    void testToString() {
        AudioBook book1 = new AudioBook("title", "author", 120, UUID.randomUUID(), "path", Language.FRANCAIS, Category.JEUNESSE);
        list = new Playlist("list1", UUID.randomUUID(), new LinkedList<Audio>());
        list.addAudio(book1);

        //check to result of toString() method
        assertEquals(list.toString(), "Playlist list1\ntitle written by author : 120");
    }
}
