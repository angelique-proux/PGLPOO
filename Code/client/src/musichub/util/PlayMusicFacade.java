package util;


import business.Audio;
import business.Genre;
import business.Song;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayMusicFacade {
    int numAudio; // numéro de l'objet à écouter
    AudioInputStream musicToListenTo; // audio à écouter
    SourceDataLine line; // ligne pour écrire l'audio
    PlayData playDataObject;

    public PlayMusicFacade() {
        Audio audioToListenTo = audioGivenByServer(numAudio);
        this.musicToListenTo = getAudioInputStreamFromFile(audioToListenTo.getContent());
        this.initialiseLine();
        this.playDataObject = new PlayData(musicToListenTo, line);
    }

    private Audio audioGivenByServer(int numAudio) {
        Song song = new Song("Precious", "Ateez", 122, UUID.randomUUID(), "Code/client/files/Precious.wav", Genre.valueOf("HIPHOP"));
        return song;
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
        this.playDataObject.playTheMusic();
    }

    public void pause() {
        playDataObject.setStopMusic(true);
    }
}
