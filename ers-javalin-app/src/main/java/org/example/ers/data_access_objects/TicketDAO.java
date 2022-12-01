package org.example.ers.data_access_objects;

import org.example.ers.models.Ticket;
import org.example.ers.models.TicketStatus;
import org.example.ers.utilities.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDAO {
    private final static Logger logger = LoggerFactory.getLogger(TicketDAO.class);
    public String create(Ticket ticket) {
        String ticketId = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            /* always start with the PrepareStatement */
            String dml = "INSERT INTO tickets (ticket_id, amount, submitted, description, author_id)";
            dml = dml + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(dml);
            ps.setString(1, ticket.getTicketId());
            ps.setDouble(2, ticket.getAmount());
            ps.setTimestamp(3, ticket.getSubmitted());
            ps.setString(4, ticket.getDescription());
            ps.setString(5, ticket.getAuthorId());
            ps.executeUpdate();
            ticketId = ticket.getTicketId();
            logger.info("new ticket created");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("create ticket dml failed");
        }
        return ticketId;
    }

    public Ticket findById(String ticketId) {
        Ticket ticket = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT ticket_id, status FROM tickets WHERE ticket_id = ?";
            PreparedStatement ps = con.prepareStatement(dql);
            ps.setString(1, ticketId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TicketStatus status = TicketStatus.valueOf(rs.getString("status"));
                ticket = new Ticket(ticketId, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("sql exception with TicketDAO.findById()");
        }
        return ticket;
    }

    public void resolve(Ticket ticket) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dml = "UPDATE tickets SET status = ?::ticket_status, resolver_id = ?, resolved = ? WHERE ticket_id = ?";
            PreparedStatement ps = con.prepareStatement(dml);

            ps.setString(1, String.valueOf(ticket.getStatus()));
            ps.setString(2, ticket.getResolver());
            ps.setTimestamp(3, ticket.getResolved());
            ps.setString(4, ticket.getTicketId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("sql exception resolving ticket");
        }
    }
}
