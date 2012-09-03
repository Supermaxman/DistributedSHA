package me.tehrainbowguy.distributedsha.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import static me.tehrainbowguy.distributedsha.server.Serv.jedis;

public class Inport extends Thread {
    User user;
    int sleepTime;

    public Inport(User user, int sleepTime) {
        this.user = user;
        this.sleepTime = sleepTime;
    }

    ObjectInputStream in;
    ObjectOutputStream out;

    public void run() {
        // Open the InputStream
        try {
            in = new ObjectInputStream(user.getSocket().getInputStream());
            out = new ObjectOutputStream(user.getSocket().getOutputStream());
        } catch (IOException e) {
            System.out.println("Could not get stream from " + toString());
            e.printStackTrace();
            return;
        }
        // Announce
        System.out.println(user.getSocket() + " has connected input.");
        // Enter process loop
        while (true) {
            try {
                byte b = in.readByte();
                //Work finished

                if (b == 0x01) {
                    HashMap<String,String> hashMap = (HashMap<String, String>) in.readObject();
                    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                        if(jedis.get(entry.getKey()) != null){
                            //reject
                        }else {
                            //add to db
                            jedis.set(entry.getKey(),entry.getValue());
                        }
                    }



                }else {
                    System.out.println("Unknown packet, IGNORING.");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage() + "! Purging user!");
                user.purge();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
//
//            // Sleep
//            try {
//                Thread.sleep(20);
//            } catch (Exception e) {
//                System.out.println(toString() + " has input interrupted.");
//            }
        }
    }

}