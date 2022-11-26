package org.example.ers.data_access_objects;

import org.example.ers.models.User;
import org.example.ers.utilities.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDAO implements DAO<User> {
    @Override
    public void create(User user) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            /* always start with the PrepareStatement */
            String dml = "INSERT INTO users (user_id, username, email, password, given_name, surname) VALUES (?, ?, ?, ?, ?, ?)";
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
    public List<User> findById(String id) {
        List<User> userList = new ArrayList<>();
        Connection connection = ConnectionFactory.getInstance().getConnection();
        String query = "SELECT * from users WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String givenName = resultSet.getString("giveName");
                String surname = resultSet.getString("surname");
                boolean isActive = resultSet.getBoolean("isActive");
                String roleId = resultSet.getString("roleId");
                User user = new User(id, username, email, password, givenName, surname, isActive, roleId);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return userList;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void update(User obj) {

    }


    @Override
    public void delete(String id) {

    }
}
