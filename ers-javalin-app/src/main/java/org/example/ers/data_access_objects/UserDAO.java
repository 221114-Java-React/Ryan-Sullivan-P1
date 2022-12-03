package org.example.ers.data_access_objects;

import org.example.ers.models.Registration;
import org.example.ers.models.User;
import org.example.ers.utilities.ConnectionFactory;
import org.example.ers.utilities.UtilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDAO {

    private final static Logger logger = LoggerFactory.getLogger(UserDAO.class);
    public void createFromRegistration(Registration registration) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dml = "INSERT INTO users (user_id, username, email, password_hash, given_name, surname, role_id) ";
            dml = dml + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(dml);
            ps.setString(1, UtilityMethods.generateId());
            ps.setString(2, registration.getUsername());
            ps.setString(3, registration.getEmail());
            ps.setString(4, registration.getPasswordHash());
            ps.setString(5, registration.getGivenName());
            ps.setString(6, registration.getSurname());
            ps.setString(7, registration.getRoleId());
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
            e.printStackTrace();
            logger.info("sql exception when querying for username and password", e);
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
            System.exit(1);
        }
        return true;
    }
    public boolean emailTaken(String email) {
        ResultSet resultSet;
        try(Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String query = "SELECT email from users WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return true;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String query = "SELECT * from users";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String givenName = resultSet.getString("given_name");
                String surname = resultSet.getString("surname");
                boolean isActive = resultSet.getBoolean("is_active");
                String roleId = resultSet.getString("role_id");
                User user = new User(userId, username, email, password, givenName, surname, isActive, roleId);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("query failure", e);
        }

        return userList;
    }
}
