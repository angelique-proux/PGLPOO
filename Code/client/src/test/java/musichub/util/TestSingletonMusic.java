package musichub.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe qui teste SingletonMusic
 */
public class TestSingletonMusic {

    private SingletonMusic sinMus;

    @Test
    void testListen() {
        this.sinMus = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        assertTrue(this.sinMus.isRunning());
    }

    @Test
    void testPause(){
        this.sinMus = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        this.sinMus.pauseMusic();
        assertTrue(this.sinMus.isRunning());
    }

    @Test
    void testRestart(){
        this.sinMus = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        this.sinMus.restartMusic();
        assertTrue(this.sinMus.isRunning());
    }

    @Test
    void testStop(){
        //stop a playing music
        this.sinMus = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        this.sinMus.stopMusic();
        assertFalse(this.sinMus.isRunning());
    }

    @AfterAll
    static void testAfterAllStop() {
        //stop a null MusicThread
        SingletonMusic sinMus2 = SingletonMusic.getInstance("ip",22, null);
        sinMus2.stopMusic();
        assertFalse(sinMus2.isRunning());
    }
}