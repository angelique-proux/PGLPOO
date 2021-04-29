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

    public void addAudio(Audio audio) {
        this.audioList.add(audio);
    }

    public void addAudios(LinkedList<Audio> audios) {
        this.audioList.addAll(audios);
    }

    public void addSongs(LinkedList<Song> songs) {
        for(int i=0;i<songs.size();i++) {
            this.audioList.add(songs.get(i));
        }
    }

    public void addAudioBooks(LinkedList<AudioBook> audioBooks) {
        for(int i=0;i<audioBooks.size();i++) {
            this.audioList.add(audioBooks.get(i));
        }
    }

    /**
     * TODO
     *
     * @author	Angélique Proux
     */
    public void playMusicList() {
        if((numberAudio<this.audioList.size())&&(numberAudio>=0)) {
            this.singletonMusic = SingletonMusic.getInstance(this.audioList.get(numberAudio).getContent(), this.port);
        } else if(numberAudio==this.audioList.size()){
            this.finished = true;
        }
        if(this.singletonMusic.finished()) {
            this.nextMusic();
        }
    }


    /**
     * ESSAI
     */
    //private ObjectInputStream input;
    //private ObjectOutputStream output;

    public void playMusicList2(ObjectInputStream input, ObjectOutputStream output) {
        try {
            System.out.println("-1 réussi !");
            output.writeObject("Envoi de la musique à écouter");
            output.writeObject(this.audioList.size());
            for (int i = 0; i < this.audioList.size(); i++) {
                output.writeObject(true);
                this.singletonMusic = SingletonMusic.getInstance(this.audioList.get(i).getContent(), this.port + 1);
                System.out.println("Musique " + i + " envoyée");
                String choix = (String) input.readObject();
                switch (choix) {
                    case "next":
                        if (i != (this.audioList.size() - 1)) {
                            output.writeObject("next");
                            this.singletonMusic.stopMusic();
                            System.out.println("suivante");
                        } else {
                            output.writeObject("end");
                        }
                        break;
                    case "previous":
                        output.writeObject("previous");
                        this.singletonMusic.stopMusic();
                        if (i >= 1) {
                            //si il y a une musique avant, l'écouter
                            i -= 2;
                        } // sinon, écouter celle d'après
                        System.out.println("précédente");
                        break;
                    case "listen":
                        output.writeObject("listen");
                        System.out.println(input.readObject());
                        break;
                    case "end":
                        output.writeObject("end");
                        i = this.audioList.size() - 1;
                        break;
                    default:
                        output.writeObject("listen");
                        i = this.audioList.size() - 1;
                        System.out.println(input.readObject());
                        break;
                }
            }
            System.out.println("Fin");
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /**
     * TODO
     *
     * @author	Angélique Proux
     */
    public void nextMusic() {
        if(this.numberAudio<this.audioList.size()) {
            this.numberAudio++;
            this.singletonMusic.stopMusic();
        }
    }

    /**
     * TODO
     *
     * @author	Angélique Proux
     */
    public void previousMusic() {
        if(this.numberAudio>0) {
            this.numberAudio--;
            this.singletonMusic.stopMusic();
        }
    }

    /**
     * TODO
     *
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
     * @return  boolean //TODO
     *
     * @author	Angélique Proux
     */
    public boolean isFinished() {
        return this.finished;
    }
}
