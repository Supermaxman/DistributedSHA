package me.supermaxman.distributedsha.main;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashingThread extends Thread {
    protected String userdata;

    final LinkedList<WorkItem> list = new LinkedList<WorkItem>();
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
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (list) {
                int newsize = list.size() / 5;
                if (newsize == 0) {
                    newsize = 1;
                }
                if (list.size() > 0) {
                    //do something
                }
                while (list.size() >= newsize) {
                    try {
                        final WorkItem workItem = list.pop();
                        if (md != null) {
                            md.update(workItem.getSeedWord().getBytes("UTF-8")); // Change this to "UTF-16" if needed
                        }
                        byte[] digest = new byte[0];
                        if (md != null) {
                            digest = md.digest();
                        }

                        BigInteger bigInt = new BigInteger(1, digest);

                        System.out.println("\"Word\": " + workItem.getSeedWord());
                        System.out.println("Hash: " + bigInt.toString(16));
                    } catch (NoSuchElementException e) {
                        return;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    public void addToQueue(ArrayList<WorkItem> workItemArrayList) {
        synchronized (list) {
            list.addAll(workItemArrayList);
        }
    }

//    public void run() {
//
//        //TODO rainbow's backend stuff to generate stuff
////        while (!isInterrupted()) {
////            System.out.println();
////            MessageDigest md = null;
////            try {
////                md = MessageDigest.getInstance("SHA-256");
////            } catch (NoSuchAlgorithmException e) {
////                e.printStackTrace();
////            }
////            String derp = "";
////            try {
////                if (md != null) {
////                    md.update(derp.getBytes("UTF-8")); // Change this to "UTF-16" if needed
////                }
////            } catch (UnsupportedEncodingException e) {
////                e.printStackTrace();
////            }
////            byte[] digest = new byte[0];
////            if (md != null) {
////                digest = md.digest();
////            }
////
////            BigInteger bigInt = new BigInteger(1, digest);
////
////            System.out.println("\"Word\": " + derp);
////            System.out.println("Hash: " + bigInt.toString(16));
////        }
//    }
}
