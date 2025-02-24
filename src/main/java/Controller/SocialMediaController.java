package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import org.eclipse.jetty.util.component.ContainerLifeCycle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    // declares objects from the service.java
    AccountService accountService;
    MessageService messageService;

    // intiantiates object
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    // Javalin handles
    public Javalin startAPI(){
        Javalin app = Javalin.create();

        //get all messages
        app.get("/messages", this::getAllMessagesHandler);

        //create new message
        app.post("/messages", this::postNewMessageHandler);

        //create new user
        app.post("/register", this::postNewAccountHandler);

        //user login
        app.post("/login", this::postLoginHandler);

        //get message from message_id
        app.get("/messages/{message_id}", this::getMessagebyIDHandler);

        //delete message from message_id
        app.delete("/messages/{message_id}", this::deleteMessagebyIDHandler);

        //update message from message_id
        app.patch("/messages/{message_id}", this::updateMessagebyIDHandler);

        //get app messages from message_id
        app.get("/messages/{message_id}", this::getAllMessagesbyIDHandler);

        return app;
    }

    //get all messages handler, transfers it to http
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }


    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.newMessage(message);

        // if message creation successful then return json, if not then return 400 error
        if (newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        } else {
            ctx.status(400);
        }
        
    }

    private void postNewAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.addAccount(account);

        if (newAccount != null){
            ctx.json(mapper.writeValueAsString(newAccount));
        } else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account getAccount = accountService.getAccount(account);

        if (getAccount != null){
            ctx.json(mapper.writeValueAsString(getAccount));
        } else {
            ctx.status(401);
        }
        
    }

    private void getMessagebyIDHandler(Context ctx) throws JsonProcessingException {

        //parse* this into Integer; holds true for read and update handlers
        //Explanation: because the message_id is a string with integers, then the parseInt() works
        Integer messageID = Integer.parseInt(ctx.pathParam("message_id"));

        Message getMessage = messageService.getMessage(messageID);

        if (getMessage != null){
            ctx.json(getMessage);
        } else {
            ctx.status(200);
        }
        
    }

    private void deleteMessagebyIDHandler(Context ctx) throws JsonProcessingException {

        Integer messageID = Integer.parseInt(ctx.pathParam("message_id"));

        Message deleteMessage = messageService.deleteMessage(messageID);

        if (deleteMessage != null){
            ctx.json(deleteMessage);
        } else {
            ctx.status(200);
        }
    }

    private void updateMessagebyIDHandler(Context ctx) throws JsonProcessingException {
        //Integer messageID = Integer.parseInt(ctx.pathParam("message_id"));
        //Message updateMessage = messageService.updateMessage(messageID);

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updateMessage = messageService.updateMessage(message);

        if (updateMessage != null){
            ctx.json(mapper.writeValueAsString(updateMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesbyIDHandler(Context ctx) throws JsonProcessingException {
        //List<Message> messages = messageService.getAllMessagesbyID();
        //ctx.json(messages);

        Integer messageID = Integer.parseInt(ctx.pathParam("message_id"));

        List<Message> allMessage = messageService.getAllMessagesbyID(messageID);

        if (allMessage != null){
            ctx.json(allMessage);
        } else {
            ctx.status(200);
        }

    }

}