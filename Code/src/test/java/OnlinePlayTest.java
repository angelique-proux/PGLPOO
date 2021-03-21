/*Test de la lecture audio*/


import org.junit.Test;

import javax.sound.sampled.AudioInputStream;

import util.PlayData;
import util.PlayMusicFacade;

public class OnlinePlayTest {

    AudioInputStream ais = PlayMusicFacade.getAudioInputStreamFromFile("files/Precious.wav");
    PlayData playD = new PlayData(ais);

    @Test
    public void getAudioData(){
        // TODO : Tester si on peut récupérer des données audio sur le client depuis le serveur
    }

    @Test
    public void firstPlayTest() throws InterruptedException {
        // TODO : Tester si on peut écouter n'importe quel son depuis le menu principal
        Thread t = new Thread() {
            public void run (){
                playD.playTheMusic();
            }
        };
        t.start();
        t.sleep(10000);
    }

    @Test
    public void stopTest(){
        // TODO : Tester si on peut arrêter l'écoute
        playD.closeMusic();
    }

    @Test
    public void continueTest(){
        // TODO : Tester si on peut reprendre l'écoute au même endroit
    }

    @Test
    public void ramdomPlayTest(){
        // TODO : Tester si on peut écouter une playlist ou un album de façon aléatoire
    }
}