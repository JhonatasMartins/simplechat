package br.upis.chat.client;

import br.upis.chat.Message;
import br.upis.chat.MessageType;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author jhonatas
 */
public class ClientReader implements Runnable, IStopRunnable{
    
    private ObjectInputStream in;
    private boolean stop = false;
    
    public ClientReader(InputStream in){
        try{
            this.in = new ObjectInputStream(in);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
      
        try{
            while(true){

                Object ob = null;

                if(stop)
                     break; 

                if((ob = in.readObject()) != null){
                    Message m = (Message)ob;
                    
                    if(m.getType() == MessageType.sending)
                       System.out.printf("\n%s diz : %s \n", m.getUser(), m.getMessage());
                }
            }
            
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void stop(){  
       stop = true;
    }
    
}
