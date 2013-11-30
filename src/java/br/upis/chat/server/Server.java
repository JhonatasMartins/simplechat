package br.upis.chat.server;

import br.upis.chat.Config;
import br.upis.chat.Message;
import br.upis.chat.MessageType;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    private List<ClientData> clients;
    private ExecutorService exec;
    
    public Server(){
        clients = new ArrayList<ClientData>();
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
            
            ClientData client = new ClientData(socket, 
                                     new ObjectInputStream(socket.getInputStream()), 
                                     new ObjectOutputStream(os));
            
            clients.add(client);
            
            ServerClient run = new ServerClient(client, this);
            exec.execute(run);
        }
        
    }
    
    private void send(Message message) throws IOException, ClassNotFoundException{
        
        for(ClientData client : clients){
            
            ObjectOutputStream out = client.getOs();
            out.flush();

            out.writeObject(message);
        }
        
    }
    
        @Override
    public void list(ClientData client) {
        StringBuilder names = new StringBuilder();
            
        for(ClientData c : clients){
            names.append("Nome:");
            names.append(c.getUsername());
            names.append("\n");
        }
        
        ObjectOutputStream out = client.getOs();
        try{
            out.flush();
            out.writeObject(new Message(names.toString(), MessageType.command));
        }catch(IOException e){
            e.printStackTrace();
        }
            
    }
    
    
    @Override
    public void sendAllsUsers(Message message) {
        try{
            send(message);
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void close(ClientData client) {
        
        clients.remove(client);
        
        try{
           client.getSocket().close();
        }catch(IOException e){
            e.printStackTrace();
        }
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
