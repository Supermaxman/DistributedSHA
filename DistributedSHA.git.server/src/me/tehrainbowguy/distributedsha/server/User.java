package me.tehrainbowguy.distributedsha.server;

import java.io.IOException;
import java.net.Socket;

/**
 * This object handles the execution for a single user.
 */
public class User {
    private static final int USER_THROTTLE = 200;
    private Socket socket;
    private boolean connected;
    private Inport inport;
    private String userName;

    /**
     * Handles all incoming data from this user.
     */

    /**
     * Creates a new Client User with the socket from the newly connected client.
     *
     * @param newSocket The socket from the connected client.
     */
    public User(Socket newSocket) {
        // Set properties
        socket = newSocket;
        connected = true;
        // Get input
        inport = new Inport(this, USER_THROTTLE);
        inport.start();
        while (inport.out == null || inport.in == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void spawnUser(long ID, int posX, int posY) {
        inport.spawnUser(ID, posX, posY);
    }


    public Inport getInport() {
        return inport;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Gets the connection status of this user.
     *
     * @return If this user is still connected.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Purges this user from connection.
     */
    public void purge() {
        // Close everything
        try {
            connected = false;
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not purge " + socket + ".");
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the String representation of this user.
     *
     * @return A string representation.
     */
    public String toString() {
        return socket.toString();
    }
}
