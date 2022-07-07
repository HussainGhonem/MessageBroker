/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentone;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hussain
 */
public class Broker implements Runnable {
    Socket w;
    static BrokerGUI BG;

    public Broker(Socket w) {
        this.w = w;
    }
    public void run(){
        try {
            DataInputStream i= new DataInputStream(w.getInputStream());
            DataOutputStream o = new DataOutputStream(w.getOutputStream());
            while(true){
                System.out.println("waiting....");
                String sender=i.readUTF();
                String message=i.readUTF();
                String receiver=i.readUTF();
                String chat=sender+" to "+receiver+": "+message;
                BG.appendTextArea(chat);
                System.out.println(sender+" "+message+" "+receiver);
                Socket d = null;
                switch(receiver){
                    case "w1":
                        System.out.println("to w1");
                        d = new Socket("localhost",6001);
                        DataOutputStream o1 = new DataOutputStream(d.getOutputStream());
                        o1.writeUTF(chat);
                        break;
                    case "w2":
                        System.out.println("to w2");
                        d = new Socket("localhost",6002);
                        DataOutputStream o2 = new DataOutputStream(d.getOutputStream());
                        o2.writeUTF(chat);
                        break;
                    case "w3":
                        System.out.println("to w3");
                        d = new Socket("localhost",6003);
                        DataOutputStream o3 = new DataOutputStream(d.getOutputStream());
                        o3.writeUTF(chat);
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {           
        // TODO code application logic here
        try {
            ServerSocket broker= new ServerSocket(6000);
            BG = new BrokerGUI();
            BG.setVisible(true);
            while(true){
                Socket w=broker.accept();
                System.out.println("connected...");
                Thread c = new Thread(new Broker(w));
                c.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
