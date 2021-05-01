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
     * TODO
     */
    private LinkedList<Audio> audioList = new LinkedList<Audio>();

    /**
     * TODO
     */
    private int port;

    /**
     * TODO
     */
    private static int numberAudio = 0;

    /**
     * TODO
     */
    private SingletonMusic singletonMusic;

    /**
     * TODO
     */
    private boolean finished;

    /**
     * TODO
     *
     * @param	  port Server's open port
     *
     * @author	Angélique Proux
     */
    public ControlMusicList(int port) {
        this.port = port;
        this.finished = false;
    }


    /**
     * TODO
     *
     * @param   audio one audio
     *
     * @see     Audio
     * @author	Angélique Proux
     */
    public void addAudio(Audio audio) {
        this.audioList.add(audio);
    }


    /**
     * TODO
     *
     * @param   audios audio list
     *
     * @see     Audio
     * @author	Angélique Proux
     */
    public void addAudios(LinkedList<Audio> audios) {
        this.audioList.addAll(audios);
    }


    /**
     * TODO
     *
     * @param   songs song list
     *
     * @see     Song
     * @author	Angélique Proux
     */
    public void addSongs(LinkedList<Song> songs) {
        for(int i=0;i<songs.size();i++) {
            this.audioList.add(songs.get(i));
        }
    }

    /**
     * TODO
     *
     * @param   audioBooks TODO
     *
     * @see     AudioBook
     * @author	Angélique Proux
     */
    public void addAudioBooks(LinkedList<AudioBook> audioBooks) {
        for(int i=0;i<audioBooks.size();i++) {
            this.audioList.add(audioBooks.get(i));
        }
    }

    /**
     * TODO
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
     * TODO
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
     * TODO
     *
     * @see     SingletonMusic
     * @author	Angélique Proux
     */
    public void stopMusic() {
      this.singletonMusic.stopMusic();
    }
}
