package br.upis.chat.server;

import br.upis.chat.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author jhonatas
 */
public class ServerClient implements Runnable{
    
    private ObjectInputStream in;
    private IServerWriter writer;
    
    public ServerClient(InputStream in, IServerWriter writer){
        this.writer = writer;
        
        try{
            this.in = new ObjectInputStream(in);
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void run() {
        
        while(true){
            
            Object ob = null;
            
            try{
                if((ob = in.readObject()) != null){
                    Message m = (Message)ob;
                    writer.writerAllsUsers(m);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
    }
    
}
