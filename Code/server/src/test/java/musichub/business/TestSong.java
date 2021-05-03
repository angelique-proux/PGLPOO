package musichub.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class TestSong {
    @Test
    void testGetters() {
        UUID musicUUID = UUID.randomUUID();
        Song music = new Song("title", "artist", 180, musicUUID, "content", Genre.RAP);

        assertEquals(music.getArtist(), "artist");
        assertEquals(music.getTitle(), "title");
        assertEquals(music.getDuration(), 180);
        assertEquals(music.getID(), musicUUID);
        assertEquals(music.getContent(), "content");
        assertEquals(music.getGenre(), Genre.RAP);
    }

    @Test
    void testToString() {
        Song music = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);
        assertEquals(music.toString(), "title by artist\nDuration : 180\nGenre : Rap");
    }
}
