package util;

import business.Audio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;

public class ShowElements implements IShowElements {
    private int choice;

    public void showElementsAndListen(ObjectOutputStream output, ObjectInputStream input, Socket socket, String ip, SingletonMusic music, Scanner scan) {
        try {
            System.out.println((String) input.readObject());
            LinkedList<Audio> audios = (LinkedList<Audio>) input.readObject();
            for(int i=0;i<audios.size();i++) {
                System.out.println(i+"- "+audios.get(i)+"\n");
            }
            System.out.println((String) input.readObject());
            choice = Integer.parseInt(scan.nextLine());
            Audio oneAudio = audios.get(choice);
            output.writeObject(choice);
            System.out.println(input.readObject());
            output.writeObject(scan.nextLine());
            if(((boolean) input.readObject())) {
                music = SingletonMusic.getInstance();
                music.startMusic(ip,6668,socket);
                do {
                    System.out.println("Enter a command : (play/pause/stop)");
                    switch(scan.nextLine()) {
                        case "pause":
                            music.pauseMusic();
                            break;
                        case "play":
                            music.restartMusic();
                            break;
                        case "stop":
                            music.stopMusic();
                            break;
                        default:
                            System.out.println("This is not a command");
                            break;
                    }
                } while(music.isRunning());
            } else {
                System.out.println(oneAudio);
            }
        } catch  (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
        catch  (IOException ioe) {
            ioe.printStackTrace();
        }
        catch  (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
}
