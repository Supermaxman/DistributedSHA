package me.supermaxman.distributedsha.main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

/**
 * User: Benjamin
 * Date: 13/08/12
 * Time: 10:19
 */
public class SocketThread extends Thread {
    private Socket socket;
    private static ObjectOutputStream dos;

    void setup() {
        try {
            socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 30480);
            dos = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Networking error");
            System.out.println(e.getMessage());
        }
    }

    final HashMap<String, String> stringHashMap = new HashMap<String, String>();

    public void run() {
        setup();
        while (true) {
            try {
                synchronized (stringHashMap) {
                    if (stringHashMap.size() >= 1000) {
                    dos.write(0x01);

                        dos.writeObject(stringHashMap);
                        stringHashMap.clear();
                    }
                }
            }
             catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void sendWorkResult(String seed, String hash) throws IOException {
        synchronized (stringHashMap) {
            stringHashMap.put(seed, hash);
        }

    }


}