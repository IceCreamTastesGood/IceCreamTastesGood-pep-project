package DAO;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    //create new message
    public Message newMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            //auto-generate a message_id
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            // returns message object with its message_id key
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    // returns all messages
    public List<Message> getAllMessages(){
        
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"),
                                resultSet.getInt("posted_by"),
                                resultSet.getString("message_text"),
                                resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            } 

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
               
        return messages;
    }

    //returns a message by its message_id, called from MessageService.java
    public Message getMessagebyID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {

            String sql = "SELECT * FROM Message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"),
                                resultSet.getInt("posted_by"),
                                resultSet.getString("message_text"),
                                resultSet.getLong("time_posted_epoch"));

                return message;
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
