package me.supermaxman.distributedsha.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;

public class HashingThread extends Thread {
    boolean shouldrun = true;
    MessageDigest md = null;

    public HashingThread() {
        this.setName("Queue");
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (shouldrun) {

            String word = nextstring();
            try {
                if (md != null) {
                    md.update(word.getBytes("UTF-8")); // Change this to "UTF-16" if needed
                }
                byte[] digest = new byte[0];
                if (md != null) {
                    digest = md.digest();
                }

                BigInteger bigInt = new BigInteger(1, digest);
                Main.socketThread.sendWorkResult(word, bigInt.toString(16));
                Main.hashed++;
                if (mainWindow.info != null) {
                    mainWindow.info.setText("Hashed: " + String.valueOf(Main.hashed) + " Last hash: " + bigInt.toString(16));
                }
                sleep(0);
            } catch (NoSuchElementException e) {
                return;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    public static String nextstring() {


        return nextSessionId();
    }

    public static String nextSessionId() {
        SecureRandom random = new SecureRandom();
        random.setSeed(System.nanoTime());
        return new BigInteger(128, random).toString(16);
    }
}
