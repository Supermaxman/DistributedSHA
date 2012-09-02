package me.tehrainbowguy.distributedsha.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
                //String seedword
                //String hash
                if (b == 0x01) {
                   String seedword = in.readUTF();
                   String hash = in.readUTF();
                   if(jedis.get(seedword) != null){
                      //reject
                   }else {
                       //add to db
                       jedis.set(seedword,hash);
                   }

                }else {
                    System.out.println("Unknown packet, IGNORING.");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage() + "! Purging user!");
                user.purge();
                return;
            }

            // Sleep
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(toString() + " has input interrupted.");
            }
        }
    }

}