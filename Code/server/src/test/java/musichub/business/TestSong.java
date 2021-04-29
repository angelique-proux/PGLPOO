package musichub.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class TestSong {
    @Test
    void testConstructor() {
        Song music = new Song("title", "artist", 180, UUID.randomUUID(), "content", Genre.RAP);

        assertEquals(music.getArtist(), "artist");
    }
}
