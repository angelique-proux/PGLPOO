package util;

import javax.sound.sampled.*;
import java.io.IOException;

public class PlayData implements PlayDataInterface {
    AudioInputStream musicToListenTo;
    SourceDataLine line = null;
    boolean stopMusic = false;

    public PlayData(AudioInputStream musicToListenTo) {
        this.musicToListenTo =musicToListenTo;
        this.initialiseLine();
    }

    @Override
    public void playTheMusic(){
        int BUFFER_SIZE = 4096; // 4K buffer
        try {
            line.open(musicToListenTo.getFormat());
            line.start();
            byte[] samples = new byte[BUFFER_SIZE];
            int count;
            while ((count = musicToListenTo.read(samples, 0, BUFFER_SIZE)) != -1) {
                line.write(samples, 0, count);
            }
        }
        catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
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

    public void closeMusic() {
        stopMusic = true;
    }

}
