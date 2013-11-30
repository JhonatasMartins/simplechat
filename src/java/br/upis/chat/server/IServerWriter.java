package br.upis.chat.server;

import br.upis.chat.Message;

/**
 *
 * @author jhonatas
 */
public interface IServerWriter {
    
    public void sendAllsUsers(Message message);
    
    public void list(ClientData client);
    
    public void close(ClientData client);
}
