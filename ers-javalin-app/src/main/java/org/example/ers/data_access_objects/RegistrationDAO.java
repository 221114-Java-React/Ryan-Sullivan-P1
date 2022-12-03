package org.example.ers.data_access_objects;

import org.example.ers.models.Registration;
import org.example.ers.utilities.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            logger.info("registration failed to insert");
            logger.info(e.getMessage());
        }
    }

    public Registration findById(String requestId) {
        Registration registration = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM registrations WHERE request_id = ?");
            ps.setString(1, requestId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                registration = new Registration();
                registration.setUsername(rs.getString("username"));
                registration.setRequestId(rs.getString("request_id"));
                registration.setEmail(rs.getString("email"));
                registration.setGivenName(rs.getString("given_name"));
                registration.setSurname(rs.getString("surname"));
                registration.setRoleId(rs.getString("role_id"));
                registration.setPasswordHash(rs.getString("password_hash"));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return registration;
    }

    public void delete(String requestId) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM registrations WHERE request_id = ?");
            ps.setString(1, requestId);
            ps.execute();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}
