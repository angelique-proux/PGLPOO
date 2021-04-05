package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class PlayMusicFacade {
    private LinkedList<String> listeAttente;
    private AudioInputStream musicToListenTo; // audio à écouter
    private SourceDataLine line; // ligne pour écrire l'audio
    private final PlayData playDataObject;
    private final ManageMusicList manageMusicList;

    public PlayMusicFacade() {
        // initialisation des objets nécessaire pour les utiliser après (setContent)
        this.listeAttente = new LinkedList<>();
        this.listeAttente.add("Code/client/files/Precious.wav");
        this.musicToListenTo = getAudioInputStreamFromFile(listeAttente.getFirst());
        this.initialiseLine();
        playDataObject = new PlayData(musicToListenTo, line);
        manageMusicList = new ManageMusicList();
    }

    public void setContent(String content) {
        this.musicToListenTo = getAudioInputStreamFromFile(content);
        this.playDataObject.setMusicToListenTo(this.musicToListenTo);
    }

    public void addAudioToListAttente(String audioToAdd){
        this.listeAttente = manageMusicList.addAudioToListe(listeAttente, audioToAdd);
        setContent(audioToAdd);
    }

    public void changeListeAttente(LinkedList<String> listAudio) {
        this.listeAttente = manageMusicList.changeListe(listAudio);
        setContent(listeAttente.getFirst());
    }

    public static AudioInputStream getAudioInputStreamFromFile(String filepath) {
        if (filepath == null) {
            throw new IllegalArgumentException("filename is null");
        }

        try {
            // first try to read file from local file system
            File file = new File(filepath);
            if (file.exists()) {
                return AudioSystem.getAudioInputStream(file);
            }

            // give up
            else {
                throw new IllegalArgumentException("could not read '" + filepath + "'");
            }
        }
        catch (IOException e) {
            throw new IllegalArgumentException("could not read '" + filepath + "'", e);
        }
        catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("file of unsupported audio format: '" + filepath + "'", e);
        }
    }

    public void initialiseLine() {
        try {
            AudioFormat audioFormat = musicToListenTo.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        Thread t = new Thread(() -> playDataObject.playTheMusic());
        t.start();
    }

    public void pause() {
        playDataObject.setStopMusic(true);
    }

    public void playNext() {
        if (!listeAttente.isEmpty()) {
            listeAttente.remove();
            if (!listeAttente.isEmpty()) {
                setContent(listeAttente.getFirst());
                Thread t = new Thread(() -> play());
                t.start();
            } else {
                System.out.println("La liste d'attente est vide, ajoutez un audio si vous voulez écouter une autre musique.");
            }
        } else {
            System.out.println("La liste d'attente est vide, ajoutez un audio si vous voulez écouter une autre musique.");
        }
    }

}