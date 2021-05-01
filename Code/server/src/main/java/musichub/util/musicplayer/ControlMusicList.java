/*
 * Class' name : ControlMusicList
 *
 * Description : TODO
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.musicplayer;

import musichub.business.*;
import java.util.LinkedList;
import java.io.*;

/**
 * TODO
 *
 * Version : 1.0
 *
 * @author  Angélique Proux
 */
public class ControlMusicList implements ControlMusic {

    /**
     * list of audio to listen to
     */
    private LinkedList<Audio> audioList = new LinkedList<Audio>();

    /**
     * port needed in AudioServerThread to open the socket
     */
    private int port;

    /**
     * number of the current audio listened
     */
    private static int numberAudio = 0;

    /**
     * Singleton used to open an unique MusicThread
     */
    private SingletonMusic singletonMusic;


    /**
     * initialization of ControlMusicList
     *
     * @param	  port Server's open port
     *
     * @author	Angélique Proux
     */
    public ControlMusicList(int port) {
        this.port = port;
    }

    /**
     * Add an audio to the list
     *
     * @param     audio to add
     *
     * @author  Angélique Proux
     */
    public void addAudio(Audio audio) {
        this.audioList.add(audio);
    }

    /**
     * Add audios to the list
     *
     * @param     audios to add
     *
     * @author  Angélique Proux
     */
    public void addAudios(LinkedList<Audio> audios) {
        this.audioList.addAll(audios);
    }

    /**
     * Add songs to the list
     *
     * @param     songs to add
     *
     * @author  Angélique Proux
     */
    public void addSongs(LinkedList<Song> songs) {
        for(int i=0;i<songs.size();i++) {
            this.audioList.add(songs.get(i));
        }
    }

    /**
     * Add audiobooks to the list
     *
     * @param     audioBooks to add
     *
     * @author  Angélique Proux
     */
    public void addAudioBooks(LinkedList<AudioBook> audioBooks) {
        for(int i=0;i<audioBooks.size();i++) {
            this.audioList.add(audioBooks.get(i));
        }
    }

    /**
     * play the current list of audios
     *
     * @param   numberAudio TODO
     *
     * @see     Audio
     * @see     SingletonMusic
     * @author	Angélique Proux
     */
    public void playMusicList(int numberAudio) {
        if((numberAudio<this.audioList.size())&&(numberAudio>=0)) {
            this.singletonMusic = SingletonMusic.getInstance(this.audioList.get(numberAudio).getContent(), this.port);
        }
    }

    /**
     * clear the list
     *
     * @see     Audio
     * @see     SingletonMusic
     * @author	Angélique Proux
     */
    public void reset() {
        this.singletonMusic.stopMusic();
        this.audioList.clear();
        this.numberAudio = 0;
    }

    /**
     * stop the listened music
     *
     * @author  Angélique Proux
     */
    public void stopMusic() {
      this.singletonMusic.stopMusic();
    }
}
