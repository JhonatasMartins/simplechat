package br.upis.chat.server;

import br.upis.chat.Command;
import br.upis.chat.CommandUtil;
import br.upis.chat.Message;
import br.upis.chat.MessageType;
import java.io.ObjectInputStream;

/**
 *
 * @author jhonatas
 */
public class ServerClient implements Runnable{
    
    private ClientData client;
    private IServerWriter writer;
    private boolean stop = false;
    
    public ServerClient(ClientData client, IServerWriter writer){
        this.writer = writer;
        this.client = client;
    }
    
    @Override
    public void run() {
        ObjectInputStream in = client.getIn();
        
        while(true){
            if(stop)
                break;
            
            Object ob = null;
            
            try{
                if((ob = in.readObject()) != null){
                    Message m = (Message)ob;
                    processMessage(m);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
    }
    
    private void processMessage(Message message){
        
        MessageType type = message.getType();
        String strMsg = String.valueOf(message.getMessage());
            
        if(type == MessageType.sending){
           writer.sendAllsUsers(message);
        }
        
        else if(type == MessageType.connect){
           client.setUsername(message.getUser());
           System.out.printf("\nUsuário: %s, conectado com sucesso", message.getUser());
        }
        
        else if(type == MessageType.command){
            System.out.printf("\nOi %s eu sou um comando ;)", message.getUser());
            
            Command cm = CommandUtil.getCommand(strMsg);
            
            if(cm == Command.quit){
                close();
            }else if(cm == Command.list){
                list();
            }
        }
                        
    }
    
    private void list(){
        writer.list(client);
    }
    
    private void close(){  
        stop = true;
        
        writer.close(client);
    }
    
}
