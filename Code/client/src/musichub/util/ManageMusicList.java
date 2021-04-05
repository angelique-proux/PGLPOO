package util;

import java.util.Collections;
import java.util.LinkedList;

public class ManageMusicList {

    public LinkedList<String> changeListe(LinkedList<String> listAudio) {
        LinkedList<String> liste = eraseListe();
        liste.addAll(listAudio);
        liste = randomListe(liste);
        return liste;
    }

    public LinkedList<String> eraseListe() {
        return new LinkedList<>();
    }

    public LinkedList<String> addAudioToListe(LinkedList<String> listeAttente, String audioToAdd){
        listeAttente.addFirst(audioToAdd);
        return listeAttente;
    }

    public LinkedList<String> randomListe(LinkedList<String> listeAttente){
        Collections.shuffle(listeAttente);
        return listeAttente;
    }

}
