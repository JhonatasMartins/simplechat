package br.upis.chat.client;

import br.upis.chat.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author jhonatas
 */
public class ClientReader implements Runnable{
    
    private ObjectInputStream in;
    
    public ClientReader(InputStream in){
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
                    System.out.printf("\n%s diz : %s", m.getUser(), m.getMessage());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            
        }
        
    }
    
}
