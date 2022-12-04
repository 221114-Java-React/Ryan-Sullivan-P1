package com.revature.ers.data_access_objects;


import com.revature.ers.utilities.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleDAO {
    private final static Logger logger = LoggerFactory.getLogger(UserRoleDAO.class);

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
