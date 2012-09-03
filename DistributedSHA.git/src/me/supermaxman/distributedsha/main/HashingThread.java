package me.supermaxman.distributedsha.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

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
                        if(mainWindow.info != null){
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


    public static String nextstring()
    {
        String string = generateRandomWord();
        while (used.contains(string)){
           string = generateRandomWord();
        }
        used.add(string);

        return string;
    }
    static ArrayList<String> used = new ArrayList<String>();
    static String generateRandomWord() {
        Random random = new Random(System.nanoTime());
        int wordLength = random.nextInt(512);
        StringBuilder sb = new StringBuilder(wordLength);
        for(int i = 0; i < wordLength; i++) { // For each letter in the word
            char tmp = (char) ('a' + random.nextInt('z' - 'a')); // Generate a letter between a and z
            sb.append(tmp); // Add it to the String
        }
        return sb.toString();
    }
}
