package org.example.ers.data_access_objects;


import org.example.ers.utilities.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleDAO {
    private final static Logger logger = LoggerFactory.getLogger(UserRoleDAO.class);

    public String getApiNameFromId(String roleId) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT role_id, api_name FROM user_roles WHERE role_id = ?";
            PreparedStatement pst = con.prepareStatement(dql);
            pst.setString(1, roleId);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getString("api_name");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("problem getting api names for roles");
            return e.getMessage();
        }
    }

    public String getIdFromApiName(String apiName) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT role_id, api_name FROM user_roles WHERE api_name = ?";
            PreparedStatement pst = con.prepareStatement(dql);
            pst.setString(1, apiName);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getString("role_id");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("problem getting api names for roles");
            return e.getMessage();
        }
    }

    public boolean validRole(String api_name) {
        ResultSet resultSet;
        try(Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String query = "SELECT api_name FROM user_roles WHERE api_name = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, api_name);
            resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return false;
    }
}
