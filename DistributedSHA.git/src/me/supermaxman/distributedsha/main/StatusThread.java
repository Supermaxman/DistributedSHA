package me.supermaxman.distributedsha.main;

/**
 * User: Benjamin
 * Date: 02/09/12
 * Time: 23:58
 */
public class StatusThread extends Thread {
    @Override
    public void run() {
        while (true){
            int i = Main.hashed;
            Main.hashed = 0;
            System.out.println("Hashes/s : " + i);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
