package me.supermaxman.distributedsha.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * User: Benjamin
 * Date: 13/08/12
 * Time: 10:19
 */
public class SocketThread extends Thread {
    private Socket socket;
    private static ObjectOutputStream dos;
    private static ObjectInputStream ios;

    void setup() {
        try {
            socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 30480);
            dos = new ObjectOutputStream(socket.getOutputStream());
            ios = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Networking error - You will not be able to see other players.");
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        setup();
        while (true) {
            try {
                byte b = ios.readByte();
                System.out.println("Received packet : " + b);
                if (b == 0x02) {
                    ArrayList<String> arrayList = (ArrayList<String>) ios.readObject();

                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
                //TODO: Setup reconnecting
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Sleep
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(toString() + " has input interrupted.");
            }
        }
    }

    public void sendWorkResult(String seed, String hash) throws IOException {
        dos.write(0x01);
        dos.writeUTF(seed);
        dos.writeUTF(hash);
        dos.flush();

    }


}