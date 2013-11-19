/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upis.chat.client;

import br.upis.chat.Config;
import br.upis.chat.Message;
import br.upis.chat.MessageType;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jhonatas
 */
public class Client {
 
    private String username;
    private ExecutorService exec;
    
    public Client(String username){
        this.username = username;
        exec = Executors.newCachedThreadPool();
    }
    
    public void connect(){
        try{
            Socket socket = new Socket(Config.HOST, Config.PORT);
            
            OutputStream os = socket.getOutputStream();
            os.flush();
            
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(new Message(username, "", MessageType.connect));

            ClientReader reader = new ClientReader(socket.getInputStream());
            exec.submit(reader);
            
            Scanner scanner = new Scanner(System.in);
            
            while(true){
                String str = scanner.nextLine();    
                oos.flush();
                oos.writeObject(new Message(username, str, MessageType.sending));
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public static void main(String... args){
        new Client("Carol").connect();
    }
}
