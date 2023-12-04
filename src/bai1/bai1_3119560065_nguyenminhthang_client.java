/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bai1;

/**
 *
 * @author ACER
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class bai1_3119560065_nguyenminhthang_client {
    private Socket sk =null;
    public static BufferedReader inStream = null;
    public static BufferedWriter outStream = null;

    public bai1_3119560065_nguyenminhthang_client(String address , int port) {
        try{
            sk = new Socket(address,port);
            System.out.println("connected");
            inStream = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            outStream = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
            Scanner sc = new Scanner(System.in);
            String line  = "";
            Listening listening = new Listening();
            listening.start();
            while (!line.equals("Over")){
                System.out.println("Nhập dữ liệu: ");
                line = sc.nextLine();
                System.out.println("Client send " + line);
                outStream.write(line);
                outStream.newLine();
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            sk.close();
        }
        catch (Exception e){

        }
    }

    public static void main(String[] args) {
        bai1_3119560065_nguyenminhthang_client client = new bai1_3119560065_nguyenminhthang_client("localhost", 5000);
    }


    static class Listening extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    String line = inStream.readLine();
                    System.out.println(line);
                } catch (Exception e) {
                    System.out.println(" Error : " + e.toString());
                    break;
                }
            }
        }
    }
}