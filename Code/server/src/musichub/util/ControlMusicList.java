package util;

import business.Audio;

import java.net.Socket;
import java.util.LinkedList;

public class ControlMusicList implements ControlMusic {

    private LinkedList<Audio> audioList = new LinkedList<>();
    private boolean nextMusic;
    private boolean previousMusic;
    private int port;
    private Socket socket;

    public ControlMusicList(Audio audio, int port, Socket socket) {
        this.audioList.add(audio);
        this.port = port;
        this.socket = socket;
    }

    public ControlMusicList(LinkedList<Audio> audioList, int port, Socket socket) {
        this.audioList = audioList;
        this.port = port;
        this.socket = socket;
    }

    public void playMusicList() {
        for (int i=0 ; i < this.audioList.size(); i++) {
            SingletonMusic singletonMusic = SingletonMusic.getInstance();
            singletonMusic.checkInstance();
            singletonMusic.startMusic(audioList.get(i).getContent(),this.port,this.socket);
            if (this.nextMusic) {
                this.nextMusic = false;
                continue;
            }
            if (this.previousMusic) {
                if (!i==0) {
                    i--;
                }
                this.previousMusic = false;
            }
        }
    }

    public void nextMusicAskByClient() {
        if (((String) input.readObject()).equals("next")) {
            this.nextMusic = true;
        }
    }

    public void previousMusicAskByClient() {
        if (((String) input.readObject()).equals("previous")) {
            this.previousMusic = true;
        }
    }

}
