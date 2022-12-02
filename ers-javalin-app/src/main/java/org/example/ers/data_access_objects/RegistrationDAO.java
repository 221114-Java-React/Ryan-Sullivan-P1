package org.example.ers.data_access_objects;

import org.example.ers.models.Registration;
import org.example.ers.utilities.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
