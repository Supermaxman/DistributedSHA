package me.supermaxman.distributedsha.main;

public class Main {
    static SocketThread socketThread;
    static HashingThread hashingThread;
    static StatusThread statusThread;
    static int hashed = 0;


    public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

        socketThread = new SocketThread();
        hashingThread = new HashingThread();
        statusThread = new StatusThread();
        statusThread.start();
        socketThread.start();
        Thread.sleep(1000);
        hashingThread.start();

		new mainWindow();
	}

}
