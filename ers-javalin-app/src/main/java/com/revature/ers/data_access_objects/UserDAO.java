package com.revature.ers.data_access_objects;

import com.revature.ers.models.Registration;
import com.revature.ers.utilities.ConnectionFactory;
import com.revature.ers.models.User;
import com.revature.ers.utilities.UtilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDAO {

    private final static Logger logger = LoggerFactory.getLogger(UserDAO.class);
    public void createFromRegistration(Registration registration) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dml = "INSERT INTO users (user_id, username, email, password_hash, given_name, surname, role_id, is_active)";
            dml = dml + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(dml);
            ps.setString(1, UtilityMethods.generateId());
            ps.setString(2, registration.getUsername());
            ps.setString(3, registration.getEmail());
            ps.setString(4, registration.getPasswordHash());
            ps.setString(5, registration.getGivenName());
            ps.setString(6, registration.getSurname());
            ps.setString(7, registration.getRoleId());
            ps.setBoolean(8, true);
            ps.executeUpdate();
            logger.info("new user created");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("create user dml failed");
        }
    }

    public User findByUsernameAndPasswordHash(String username, String passwordHash) {
        User user = null;

        try (Connection connection = ConnectionFactory.getInstance().getConnection()){
            String query = "SELECT * from users WHERE username = ? AND password_hash = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String email = resultSet.getString("email");
                String givenName = resultSet.getString("given_name");
                String surname = resultSet.getString("surname");
                boolean isActive = resultSet.getBoolean("is_active");
                String roleId = resultSet.getString("role_id");

                user = new User(userId, username, email, passwordHash, givenName, surname, isActive, roleId);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return user;
    }

    public boolean usernameTaken(String username) {
        ResultSet resultSet;
        try(Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String query = "SELECT username from users WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean emailTaken(String email) {
        ResultSet resultSet;
        try(Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String query = "SELECT email FROM users WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return true;
    }

    public void updatePassword(String username, String pwHash) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()){
            String dml = "UPDATE users SET password_hash = ? WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(dml);
            ps.setString(1, pwHash);
            ps.setString(2, username);
            System.out.println(ps.executeUpdate());
            logger.info("password reset");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void updateUserIsActive(String username, boolean isActive) {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()){
            String dml = "UPDATE users SET is_active = ? WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(dml);
            ps.setBoolean(1, isActive);
            ps.setString(2, username);
            System.out.println(ps.executeUpdate());
            logger.info("user is_active updated");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}
