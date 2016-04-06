/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleApp;
import java.io.IOException;
import javaChat.ClientConnection;
/**
 *
 * @author Andhika
 */
public class ConsoleApplication {
    private ClientConnection client;
    
    class ReadInput extends Thread{
        public void run() {
            try {
                String inputKeyboard;
                do {
                    System.out.print(">> ");
                    inputKeyboard = client.inputString();
                    client.writeStream(inputKeyboard);
                } while (!inputKeyboard.equals("quit"));
                client.disconnect();
            } catch (IOException ex) {
                System.out.println("Theres an Error : " + ex.getMessage());
            }
        }
    }
    
    class WriteOutput extends Thread {
        public void run() {
            try {
                String inputan;
                while ((inputan = client.readStream()) != null) {
                    System.out.println(inputan);
                    System.out.println(">> ");
                } 
            } catch (Exception e) {
                System.out.println("Error Message: " + e.getMessage());
            }
        }
    }
    
    public void startChat() {
        try {
            client = new ClientConnection();
            
            System.out.print("Input sever IP : ");
            String ip = client.inputString();
            client.connect(ip);
            
            ReadInput in = new ReadInput();
            WriteOutput out = new WriteOutput();
            in.start();
            out.start();
            
        } catch (IOException ex) {
            System.out.println("Error Message: " + ex.getMessage());
        }
    }
}
