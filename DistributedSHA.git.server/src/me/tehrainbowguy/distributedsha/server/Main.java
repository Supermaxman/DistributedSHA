package me.tehrainbowguy.distributedsha.server;

import java.io.*;

/**
 * User: Benjamin
 * Date: 13/08/12
 * Time: 10:13
 */
public class Main {
    static Serv serv;
    static String url;
    static String user;
    static String password;

    public static void main(String[] args) throws IOException {
        try {
            FileInputStream fstream = new FileInputStream("db.prop");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                 String key = strLine.split(":" , 2)[0];
                 String value = strLine.split(":" , 2)[1];
                if(key.equalsIgnoreCase("url")){
                  url = value;
                }
                if(key.equalsIgnoreCase("user")){
                  user = value;
                }
                if(key.equalsIgnoreCase("password")){
                   password = value;
                }

            }
            in.close();

        } catch (Exception e) {
          e.printStackTrace();
        }


        serv = new Serv();
        serv.start();
    }
}
