package me.tehrainbowguy.distributedsha.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static me.tehrainbowguy.distributedsha.server.Main.serv;

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
                    String seed = in.readUTF();
                    String hash = in.readUTF();
//                        if (!jedis.exists(seed)) {
                    synchronized (serv.con){
                    PreparedStatement preparedStatement =
                                serv.con.prepareStatement(
                                        "INSERT IGNORE INTO `test`.`sha` (`ID`, `Orig`, `Hash`) VALUES (NULL, ?, ?);"
                                );
//                        }
                        preparedStatement.setString(1, seed);
                        preparedStatement.setString(2, hash);
                        preparedStatement.execute();
                        preparedStatement.close();
                    }


                } else {
                    System.out.println("Unknown packet, IGNORING.");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage() + "! Purging user!");
                user.purge();
                return;
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

}