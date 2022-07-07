/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Worker2;

import Worker1.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hussain
 */
public class w2 implements Runnable {
    Socket w;
    static w2GUI w1gui;
    static Socket br;
    public w2(Socket w) throws IOException {
        this.br=new Socket("localhost",6000);       
        this.w = w;     
    }
    
    @Override
    public void run() {
        try {       
            DataInputStream i = new DataInputStream(w.getInputStream());
            DataOutputStream o = new DataOutputStream(w.getOutputStream());
            while(true){
                String message;
                message=i.readUTF();
                System.out.println(message);
                w1gui.appendTextArea(message);              
            }
        } catch (IOException ex) {
            Logger.getLogger(w2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket w1 = new ServerSocket(6002);
        w1gui= new w2GUI();
        w1gui.setVisible(true);
        while(true){
            Socket b=w1.accept();
            Thread broker = new Thread (new w2(b));
            broker.start();           
        }
    }
    
}
