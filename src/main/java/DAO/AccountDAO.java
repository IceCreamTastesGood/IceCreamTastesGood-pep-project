package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    //insert account
    //null account_id, unique username, and password with at least 4 characters
    public Account insertAccount (Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";

            //auto generate a account_id key
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // NEED TO SET IF STATEMENTS
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // returns the account object with generated primary key
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_account_key = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_key, account.getUsername(), account.getPassword());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    // user login
    public Account getAccount(int account_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            //finds the account within the database/query then returns that account
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Account account = new Account(resultSet.getInt("account_id"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password"));
                return account;
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }


}
