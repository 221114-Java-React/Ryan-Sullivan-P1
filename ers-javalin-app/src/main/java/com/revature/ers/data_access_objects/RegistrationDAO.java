package com.revature.ers.data_access_objects;

import com.revature.ers.models.Registration;
import com.revature.ers.utilities.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    private final static Logger logger = LoggerFactory.getLogger(RegistrationDAO.class);

    public void register(Registration reg) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dml = "INSERT INTO registrations (request_id, username, email, password_hash, given_name, surname, role_id) ";
            dml = dml + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(dml);
            ps.setString(1, reg.getRequestId());
            ps.setString(2, reg.getUsername());
            ps.setString(3, reg.getEmail());
            ps.setString(4, reg.getPasswordHash());
            ps.setString(5, reg.getGivenName());
            ps.setString(6, reg.getSurname());
            ps.setString(7, reg.getRoleId());
            ps.executeUpdate();
            logger.info("user registered");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public List<Registration> getAll() {
        List<Registration> list = new ArrayList<Registration>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM registrations");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(populateFromResult(rs));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return list;
    }

    public Registration findByUsername(String username) {
        Registration registration = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM registrations WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                registration = populateFromResult(rs);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return registration;
    }

    private Registration populateFromResult(ResultSet rs) throws SQLException {
        Registration registration = new Registration();
        registration.setRequestId(rs.getString("request_id"));
        registration.setUsername(rs.getString("username"));
        registration.setEmail(rs.getString("email"));
        registration.setGivenName(rs.getString("given_name"));
        registration.setSurname(rs.getString("surname"));
        registration.setRoleId(rs.getString("role_id"));
        registration.setPasswordHash(rs.getString("password_hash"));
        return registration;
    }


    public void delete(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM registrations WHERE username = ?");
            ps.setString(1, username);
            ps.execute();
            logger.info("registration for " + username + "deleted");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public boolean usernameTaken(String username) {
        ResultSet resultSet;
        try(Connection connection = ConnectionFactory.getInstance().getConnection()) {
            String query = "SELECT username from registrations WHERE username = ?";
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
            String query = "SELECT email FROM registrations WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return true;
    }
}
