package br.upis.chat;

/**
 *
 * @author jhonatas
 */
public final class CommandUtil {
    
    private static final String COMMAND = "/";
    
   /**
     * Verifica se a entrada do teclado é um comando ou uma
     * String, por default retorna MessageType.sending, 
     * se for a String for um comando deve retornar MessageType.command
     * 
     * @param line
     * @return 
     */
    public static MessageType getType(String line){
         MessageType type =  MessageType.sending;
        
        if(line != null && !line.isEmpty() && line.startsWith(COMMAND))
            type = MessageType.command;
        
        return type;
    }
    
    /**
     * Tenta obter através da String um comando,
     * se o mesmo existir no enum Command
     * 
     * @param line
     * @return 
     */
    public static Command getCommand(String line){
        Command command = null;
        
        if(line != null && !line.isEmpty()){
            String comm = line.trim().replaceFirst(COMMAND, "");
            
            command = Command.valueOf(comm);
        }
        
        return command;
    }
    
}
