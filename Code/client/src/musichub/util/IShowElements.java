package util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public interface IShowElements {
    void showElementsAndListen(ObjectOutputStream output, ObjectInputStream input, Socket socket, String ip, SingletonMusic music, Scanner scan);
}
