package org.example.ers.data_access_objects;

import org.example.ers.models.User;
import org.example.ers.utilities.ConnectionFactory;
import org.example.ers.utilities.enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDAO implements DAO<User> {
    @Override
    public void create(User user) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            /* always start with the PrepareStatement */
            String dml = "INSERT INTO users (user_id, username, email, password, given_name, surname, role_id) ";
            dml = dml + "VALUES (?, ?, ?, ?, ?, ?, 3)";
            PreparedStatement ps = con.prepareStatement(dml);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, String.valueOf(user.getPassword()));
            ps.setString(5, String.valueOf(user.getGivenName()));
            ps.setString(6, String.valueOf(user.getSurname()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(String id) {
        return null;
    }


    public User findByUsername(String username) {
        User user = null;
        Connection connection = ConnectionFactory.getInstance().getConnection();
        String query = "SELECT * from users WHERE username = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String givenName = resultSet.getString("given_name");
                String surname = resultSet.getString("surname");
                boolean isActive = resultSet.getBoolean("is_active");
                UserRole role = UserRole.valueOf(resultSet.getString("user_role"));
                user = new User(userId, username, email, password, givenName, surname, isActive, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        Connection connection = ConnectionFactory.getInstance().getConnection();
        String query = "SELECT * from users";
        try {
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
            throw new RuntimeException(e);
        }
        return userList;
    }

    public List<String> findAllUsernames() {
        return null;
    }

    @Override
    public void update(User obj) {

    }


    @Override
    public void delete(String id) {

    }
}
