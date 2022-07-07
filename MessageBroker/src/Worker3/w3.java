/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Worker3;

import Worker1.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hussain
 */
public class w3 implements Runnable {
    Socket w;
    static w3GUI w1gui;
    static Socket br;
    public w3(Socket w) throws IOException {
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
                w1gui.appendTextArea(message);              
            }
        } catch (IOException ex) {
            Logger.getLogger(w3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket w1 = new ServerSocket(6003);
        w1gui= new w3GUI();
        w1gui.setVisible(true);
        while(true){
            Socket b=w1.accept();
            Thread broker = new Thread (new w3(b));
            broker.start();           
        }
    }
    
}
