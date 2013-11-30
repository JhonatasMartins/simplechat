package br.upis.chat.client;

import br.upis.chat.Config;
import br.upis.chat.Message;
import br.upis.chat.MessageType;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jhonatas
 */
public class Client implements ICloseListener{
 
    private String username;
    private ExecutorService exec;
    private ClientReader reader;
    private ClientWriter writer;
    private Socket socket;
    
    public Client(String username){
        this.username = username;
        exec = Executors.newCachedThreadPool();
    }
    
    public void connect(){
        try{
            socket = new Socket(Config.HOST, Config.PORT);
            
            OutputStream os = socket.getOutputStream();
            os.flush();
            
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(new Message(username, MessageType.connect));

            writer = new ClientWriter(username, oos, this);
            exec.execute(writer);
            
            reader = new ClientReader(socket.getInputStream());
            exec.execute(reader);
        }catch(IOException e){
            e.printStackTrace();
        }
   
    }
    
    @Override
    public void close(){        
        try{            
            reader.stop();
            writer.stop();
            
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        exec.shutdownNow();
    }
    
    public static void main(String... args){
        new Client("Leonardo").connect();
    }
}
