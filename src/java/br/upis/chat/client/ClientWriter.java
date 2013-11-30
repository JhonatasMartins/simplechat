package br.upis.chat.client;

import br.upis.chat.Command;
import br.upis.chat.CommandUtil;
import br.upis.chat.Message;
import br.upis.chat.MessageType;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 *
 * @author jhonatas
 */
public class ClientWriter implements Runnable, IStopRunnable{
    
    private String username;
    private ObjectOutputStream oos;
    private ICloseListener listener;
    private boolean stop = false;
    
    public ClientWriter(String username, ObjectOutputStream oos, 
                        ICloseListener listener){
        this.username = username;
        this.oos = oos;
        this.listener = listener;
    }
    
    @Override
    public void run() {
       //Novo scanner usando o teclado como entrada
       Scanner scanner = new Scanner(System.in);
            
       try{
            while(true){
                
                if(stop)
                    break;
                
                //operação bloqueante, travar até ser digitado algo e apertado enter
                String line = scanner.nextLine();    
                
                //limpa o que está no buffer
                oos.flush();
                
                MessageType type = CommandUtil.getType(line);
                
                //verificar se é o /quit
                if(type == MessageType.command && 
                   CommandUtil.getCommand(line) == Command.quit){
                    
                   listener.close();
                }
                
                //manda a nova mensagem para o socket
                oos.writeObject(new Message(username, line, type));
            }
            
            oos.close();
       }catch(IOException e){
           e.printStackTrace();
       }
    }

    @Override
    public void stop() {
         stop = true;
    }

}
