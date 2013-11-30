package br.upis.chat.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author jhonatas
 */
public class ClientData{
    
    private String username;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream os;
    
    public ClientData(Socket socket, ObjectInputStream in,
                      ObjectOutputStream os){
        
        this.socket = socket;
        this.in = in;
        this.os = os;
    }

    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOs() {
        return os;
    }
    
}
