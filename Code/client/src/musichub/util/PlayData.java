package util;

import javax.sound.sampled.*;
import java.io.IOException;

public class PlayData implements PlayDataInterface {
    AudioInputStream musicToListenTo;
    SourceDataLine line;
    boolean stopMusic = false;
    byte[] samples = new byte[4096];

    public PlayData(AudioInputStream stream, SourceDataLine line) {
        this.musicToListenTo = stream;
        this.line = line;
    }

    public void setStopMusic(boolean stop) {
        if (stop == true) {
            this.stopMusic = true;
        } else {
            this.stopMusic = false;
        }
    }

    public void playTheMusic(){
        try {
            stopMusic = false;
            line.open(musicToListenTo.getFormat());
            line.start();
            int count;
            musicToListenTo.reset();
            while ((count = musicToListenTo.read(samples, 0, samples.length)) != -1) {
                line.write(samples, 0, count);
                if(stopMusic == true) {
                    musicToListenTo.mark(10);
                    break;
                }
            }
            line.drain();
            line.stop();
        }
        catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
