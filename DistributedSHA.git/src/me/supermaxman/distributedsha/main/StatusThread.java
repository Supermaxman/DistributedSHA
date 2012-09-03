package me.supermaxman.distributedsha.main;

/**
 * User: Benjamin
 * Date: 02/09/12
 * Time: 23:58
 */
public class StatusThread extends Thread {
    int lastcount = 0;
    @Override
    public void run() {
        while (true){
            int i = Main.hashed;
            System.out.println("Hashes/s : " + (i - lastcount));
            lastcount = i;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}