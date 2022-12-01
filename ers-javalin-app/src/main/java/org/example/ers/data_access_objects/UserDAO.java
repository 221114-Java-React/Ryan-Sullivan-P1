package org.example.ers.data_access_objects;

import org.example.ers.models.User;
import org.example.ers.utilities.ConnectionFactory;
import org.example.ers.models.UserRole;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDAO implements DAO<User> {

    private final static Logger logger = LoggerFactory.getLogger(UserDAO.class);
    @Override
    public void create(User user) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            /* always start with the PrepareStatement */
            String dml = "INSERT INTO users (user_id, username, email, password, given_name, surname, user_role) ";
            dml = dml + "VALUES (?, ?, ?, ?, ?, ?, ?::user_role)";
            PreparedStatement ps = con.prepareStatement(dml);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, String.valueOf(user.getPassword()));
            ps.setString(5, String.valueOf(user.getGivenName()));
            ps.setString(6, String.valueOf(user.getSurname()));
            ps.setString(7, String.valueOf(UserRole.EMPLOYEE));
            ps.executeUpdate();
            logger.info("new user created");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("create user dml failed");
        }
    }

    @Override
    public User findById(String id) {
        return null;
    }


    public User findByUsernameAndPassword(String username, String password) {
        User user = null;

        try (Connection connection = ConnectionFactory.getInstance().getConnection()){
            String query = "SELECT * from users WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String email = resultSet.getString("email");
                String givenName = resultSet.getString("given_name");
                String surname = resultSet.getString("surname");
                boolean isActive = resultSet.getBoolean("is_active");
                UserRole role = UserRole.valueOf(resultSet.getString("user_role"));

                user = new User(userId, username, email, password, givenName, surname, isActive, role);
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

    @Override
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
                UserRole role = UserRole.valueOf(resultSet.getString("user_role"));
                User user = new User(userId, username, email, password, givenName, surname, isActive, role);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("query failure", e);
        }

        return userList;
    }


    @Override
    public void update(User obj) {

    }


    @Override
    public void delete(String id) {

    }
}
