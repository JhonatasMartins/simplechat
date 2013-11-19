package br.upis.chat.server;

import br.upis.chat.Config;
import br.upis.chat.Message;
import br.upis.chat.MessageType;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jhonatas
 */
public class Server implements IServerWriter{
    
    private ServerSocket server;
    private List<ObjectOutputStream> outs;
    private ExecutorService exec;
    
    public Server(){
        outs = new ArrayList<ObjectOutputStream>();
        exec = Executors.newCachedThreadPool();
    }
    
    public void start(){
        
        try{
            server = new ServerSocket(Config.PORT);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void process() throws IOException{
        
        while(true){
            Socket socket = server.accept();
            
            OutputStream os = socket.getOutputStream();
            os.flush();
            
            outs.add(new ObjectOutputStream(os));
            
            ServerClient run = new ServerClient(socket.getInputStream(), this);
            exec.submit(run);
        }
        
    }
    
    public void processMessage(Message message){
        
        MessageType type = message.getType();
            
        if(type == MessageType.sending){
           try{
               send(message);
           }catch(Exception e){
               e.printStackTrace();
           }
        }
        
        else if(type == MessageType.connect)
           System.out.printf("\nUsuário: %s, conectado com sucesso", message.getUser());
        
        
        else if(type == MessageType.disconnect)
           System.out.printf("\nUsuário: %s, desconectado !", message.getUser());
                        
    }
    
    private void send(Message message) throws IOException, ClassNotFoundException{
        
        for(ObjectOutputStream out : outs){
            
            out.flush();
            
            out.writeObject(message);
        }
        
    }
    
    
    @Override
    public void writerAllsUsers(Message message) {
        processMessage(message);
    }
    
    public static void main(String... args){
        Server server = new Server();
        server.start();
        
        try{
            server.process();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
