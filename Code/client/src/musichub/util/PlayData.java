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

    public void setMusicToListenTo(AudioInputStream musicToListenTo) {
      this.musicToListenTo = musicToListenTo;
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
            int count;
            this.stopMusic = false;
            this.line.open(musicToListenTo.getFormat());
            this.line.start();
            this.musicToListenTo.reset();
            while ((count = this.musicToListenTo.read(samples, 0, samples.length)) != -1) {
                this.line.write(samples, 0, count);
                if(this.stopMusic == true) {
                    this.musicToListenTo.mark(10);
                    break;
                }
            }
            this.line.drain();
            this.line.stop();
        }
        catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
