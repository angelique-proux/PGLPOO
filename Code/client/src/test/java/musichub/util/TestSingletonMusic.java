package musichub.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class which checks SingletonMusic
 *
 * @author      Angélique Proux
 */
public class TestSingletonMusic {

    private SingletonMusic sinMus;

    @BeforeAll
    static void TestBeforeAllUniqueInstance() {
        //check unique instance of Singleton class
        SingletonMusic sinMus1 = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        SingletonMusic sinMus2 = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        assertEquals(sinMus1, sinMus2);
    }

    @Test
    void testListen() {
        //listen a music
        this.sinMus = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        assertTrue(this.sinMus.isRunning());
    }

    @Test
    void testPause(){
        //pause a playing music
        this.sinMus = SingletonMusic.getInstance("ip",22, new MockMusicThread());
        this.sinMus.pauseMusic();
        assertTrue(this.sinMus.isRunning());
    }

    @Test
    void testRestart(){
        //restart a music
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
    static void testAfterAllStopNull() {
        //stop a null MusicThread
        SingletonMusic sinMus3 = SingletonMusic.getInstance("ip",22, null);
        sinMus3.stopMusic();
        assertFalse(sinMus3.isRunning());
    }
}