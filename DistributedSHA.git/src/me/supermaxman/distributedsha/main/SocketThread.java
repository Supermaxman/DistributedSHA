package me.supermaxman.distributedsha.main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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


    public void run() {
        setup();
        while (true) {

            HashMap<String, String> derp = null;
            synchronized (toSend) {

                if (toSend.size() >= 1000) {
                    derp = (HashMap<String, String>) toSend.clone();
                    toSend.clear();
                }

            }
            if (derp != null) {
                for (Map.Entry<String, String> entry : derp.entrySet()) {
                    try {

                        dos.write(0x01);
                        dos.writeUTF(entry.getKey());
                        dos.writeUTF(entry.getValue());
                        dos.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                derp.clear();
            }

            try {
                sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    final HashMap<String, String> toSend = new HashMap<String, String>();

    public void sendWorkResult(String seed, String hash) throws IOException {
        synchronized (toSend) {
            toSend.put(seed, hash);
        }

    }


}