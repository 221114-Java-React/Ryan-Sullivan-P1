package com.revature.ers.data_access_objects;

import com.revature.ers.utilities.ConnectionFactory;
import com.revature.ers.models.Ticket;
import com.revature.ers.models.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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

    public List<Ticket> getPendingTickets() {
        List<Ticket> tickets = new LinkedList<Ticket>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT * FROM tickets WHERE status = ?::ticket_status";
            PreparedStatement ps = con.prepareStatement(dql);
            ps.setString(1, String.valueOf(TicketStatus.PENDING));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getString("ticket_id"),
                                        rs.getDouble("amount"),
                                rs.getTimestamp("submitted"),
                                rs.getString("description"),
                                rs.getString("author_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("sql exception with TicketDAO.findById()");
        }
        return tickets;
    }

    public List<Ticket> getByStatus(TicketStatus status) {
        List<Ticket> tickets = new LinkedList<Ticket>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT * FROM tickets WHERE status = ?::ticket_status";
            PreparedStatement ps = con.prepareStatement(dql);
            ps.setString(1, String.valueOf(status));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getString("ticket_id"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("submitted"),
                        rs.getString("description"),
                        rs.getString("author_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("sql exception with TicketDAO.findById()");
        }
        return tickets;
    }
}
