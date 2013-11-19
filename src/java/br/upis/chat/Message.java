package br.upis.chat;

import java.io.Serializable;

/**
 *
 * @author jhonatas
 */
public class Message implements Serializable{

    private String user;
    private Object message;
    private MessageType type;
    
    public Message(String user, Object message, MessageType type){
        this.user = user;
        this.message = message;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public Object getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }

}
