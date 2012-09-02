package me.supermaxman.distributedsha.main;

public class Main {
    static SocketThread socketThread;
    static HashingThread hashingThread;
    static int hashed = 0;


    public static void main(String[] args) {
		// TODO Auto-generated method stub

        socketThread = new SocketThread();
        hashingThread = new HashingThread();
        socketThread.start();
        hashingThread.start();

		new mainWindow();
	}

}
