package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

import DAO.AccountDAO;
import Model.Account;

//arguments/conditons here to keep code clean

public class MessageService {
    
    //creates object
    MessageDAO messageDao;
    AccountDAO accountDao;

    //intantiates objects
    public MessageService(){
        this.messageDao = new MessageDAO();
        this.accountDao = new AccountDAO();
    }


    //creates new message
    public Message newMessage(Message message){

        // not null, less than 255 characters, and not empty posted_by
        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255){ //&& accountDao.getAccount(message.getPosted_by()) != null){
            return messageDao.newMessage(message);
        } else {
            return null;
        }
    }

    
    //returns all messages
    public List<Message> getAllMessages(){
        return messageDao.getAllMessages();
    }
    
    //gets a message by its ID; message_id
    public Message getMessage(int id){
        return messageDao.getMessagebyID(id);
    }

    //retrieves message by its ID then calls on MessageDAO to delete it
    public Message deleteMessage (int id){

        Message delete = messageDao.getMessagebyID(id);

        //messageDao.deleteMessagebyID(id);

        if (delete != null){
            return messageDao.deleteMessagebyID(id);
        } else {
            return null;
        }

        
    }

    //updates message by its ID then calls on MessageDAO to update it
    public Message updateMessage (int id, Message message){
        
        Message update = messageDao.getMessagebyID(id);
        
        if (update != null && !message.getMessage_text().isBlank() && message.getMessage_text().length() < 255) {
            messageDao.updateMessagebyID(id, message);

            return messageDao.getMessagebyID(id);

        } else {
            return null;
        }
    }

    //get all messages by ID
    public List<Message> getAllMessagesbyID(int id){
        return messageDao.getAllMessagesbyID(id);
    }

}
