package org.acme.service;

import io.agroal.api.AgroalDataSource;
import org.acme.module.ErrorMessage;
import org.acme.module.Group;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GroupService {
    /**
     * getGroups
     */
    public static ArrayList<Object> getGroups(AgroalDataSource agroalDataSource, String id) throws SQLException {
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");
        Connection conn = agroalDataSource.getConnection();
        Statement statement = conn.createStatement();
        String sql = "select group_id from javalibrary.groupinfo\n" +
                "where group_id in (select group_id from javalibrary.groupmember where group_member_id ='" + id + "');";
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Object> results = new ArrayList<>();
        while (resultSet.next()){
//            Group group = new Group(
//                    resultSet.getString("group_id"),
//                    resultSet.getString("group_name"),
//                    resultSet.getString("group_id"),
//                    resultSet.getString("group_admin_id"),
//                    resultSet.getString("group_desc")
//            );
            results.add(resultSet.getString("group_id"));
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

    public static ArrayList<Object> getGroupInfo(AgroalDataSource agroalDataSource, String id) throws SQLException {
        ErrorMessage errorMessage = new ErrorMessage(true, "successful");
        Connection conn = agroalDataSource.getConnection();
        Statement statement = conn.createStatement();
        String sql = "select * from javalibrary.groupinfo where group_id = '" + id +  "'";
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Object> results = new ArrayList<>();
        while (resultSet.next()){
            Group group = new Group(
                    resultSet.getString("group_id"),
                    resultSet.getString("group_name"),
                    resultSet.getString("group_id"),
                    resultSet.getString("group_admin_id"),
                    resultSet.getString("group_desc")
            );
            results.add(group);
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
}
