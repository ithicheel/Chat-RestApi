package org.acme.service;


import io.agroal.api.AgroalDataSource;
import io.vertx.core.json.JsonObject;
import org.acme.module.ErrorMessage;
import org.acme.module.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;

public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);

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
        statement.close();
        conn.close();

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
        statement.close();
        conn.close();
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

        statement.close();
        conn.close();
        return results;
    }


    public static void addGroupChatPeople(AgroalDataSource agr, String id) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");
        try {
            conn = agr.getConnection();
            statement = conn.createStatement();
            String sql = "call javalibrary.addGroupChat('"+id+"')";
            statement.executeQuery(sql);

        } catch (Exception e){
            logger.info("failed");
        } finally {
            conn.close();
            statement.close();
        }
    }

    public static void removeGroupChatMember(AgroalDataSource agroalDataSource, String id) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");
        try {
            conn = agroalDataSource.getConnection();
            statement = conn.createStatement();
            String sql = "call javalibrary.removeGroupChatMember('"+id+"')";
            statement.executeQuery(sql);

        } catch (Exception e){
            logger.infov("aldaa");
        } finally {
            if (conn != null){
                conn.close();
                statement.close();
            }
        }

    }


    public RestResponse<JsonObject> getGroupChatPeoples(AgroalDataSource agr, String group_Id) throws SQLException {
        JsonObject jret = new JsonObject();
        Connection conn = null;
        Statement statement = null;
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");

        try {
            conn = agr.getConnection();
            statement = conn.createStatement();
            String sql = "";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                jret.put("members", resultSet.getString("user_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null){
                conn.close();
                statement.close();
            }
        }

        return RestResponse.ResponseBuilder.ok(jret).build();
    }

    public RestResponse<JsonObject> getGroupChatInfo(AgroalDataSource agroalDataSource, String id) throws SQLException {

        JsonObject listData = new JsonObject();
        Connection conn = null;
        Statement statement = null;
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");
        JsonObject jret = new JsonObject();
        try {
            conn = agroalDataSource.getConnection();
            statement = conn.createStatement();
            String sql = "";
            ResultSet resultSet = statement.executeQuery(sql);


            while (resultSet.next()){
                jret.put("name", resultSet.getString("group_name"));
                jret.put("id", resultSet.getString("group_id"));
                jret.put("group_admin_id", resultSet.getString("group_admin_id"));
                jret.put("group_date", resultSet.getString("group_date"));
            }

        } catch (Exception e){
            logger.infov("aldaa!!");

        } finally {
            try {
                if(conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }



        return RestResponse.ResponseBuilder.ok(jret).build();
    }



}
