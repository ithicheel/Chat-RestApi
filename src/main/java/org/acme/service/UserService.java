package org.acme.service;


import io.agroal.api.AgroalDataSource;
import org.acme.module.ErrorMessage;
import org.acme.module.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserService {
    /**
     * LOGIN
     *
     * @return
     */
    public static ArrayList<Object> login(AgroalDataSource agroalDataSource, String email, String pass) throws SQLException {
        ArrayList<Object> users = new ArrayList<>();
        ErrorMessage errorMessage = new ErrorMessage(true, "Successful");
        Connection conn = agroalDataSource.getConnection();
        Statement statement = conn.createStatement();
        String sql = "call javalibrary.loginUserByEmail('" + email +"')";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            if(resultSet.getString("password").equals(pass)){
                Users user = new Users(
                        resultSet.getString("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("description"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("status")
                );
                users.add(user);
            }else {
                errorMessage.setStatus(false);
                errorMessage.setMessage("username esvel password buruu bna.");
            }
        }
        if(users.size() == 0){
            errorMessage.setStatus(false);
            errorMessage.setMessage("username esvel password buruu bna..");
        }
        users.add(errorMessage);
        return users;
    }

    /**
     * GET INFO
     */


    public static ArrayList<Object> findById(AgroalDataSource agroalDataSource, String id) throws SQLException {
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");
        Connection conn = agroalDataSource.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("call javalibrary.getUserById('" + id + "');");
        System.out.println("call javalibrary.getUserById('" + id + "');");
        ArrayList<Object> users = new ArrayList<>();
        while (resultSet.next()){
            Users user = new Users(
                    resultSet.getString("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("description"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("status")
            );
            users.add(user);
        }
        if(users.size() == 0){
            errorMessage.setStatus(false);
            errorMessage.setMessage("buruu id bna");
        }
        users.add(errorMessage);
        return users;
    }
    public static ArrayList<Object> getFriendList(AgroalDataSource agroalDataSource, String id) throws SQLException {
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");
        Connection conn = agroalDataSource.getConnection();
        Statement statement = conn.createStatement();
        String sql = "call javalibrary.getFriendList('" + id + "')";
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Object> results = new ArrayList<>();
        while(resultSet.next()){
            results.add(resultSet.getString("friend_id"));
        }
        if(results.size() == 0){
            errorMessage.setStatus(false);
            errorMessage.setMessage("buruu id bna");
        }
        results.add(errorMessage);
        return results;
    }
}
