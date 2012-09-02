package me.tehrainbowguy.distributedsha.server;

/**
 * User: Benjamin
 * Date: 13/08/12
 * Time: 10:13
 */
public class Main {
    static Serv serv;

    public static void main(String[] args) {
        serv = new Serv();
        serv.start();
    }
}
