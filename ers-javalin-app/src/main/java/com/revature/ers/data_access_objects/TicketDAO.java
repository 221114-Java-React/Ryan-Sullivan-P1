package com.revature.ers.data_access_objects;

import com.revature.ers.utilities.ConnectionFactory;
import com.revature.ers.models.Ticket;
import com.revature.ers.models.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TicketDAO {
    private final static Logger logger = LoggerFactory.getLogger(TicketDAO.class);
    public void create(Ticket ticket) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            /* always start with the PrepareStatement */
            String dml = "INSERT INTO tickets (ticket_id, amount, submitted, description, author_id, ticket_type)";
            dml = dml + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(dml);
            ps.setString(1, ticket.getTicketId());
            ps.setDouble(2, ticket.getAmount());
            ps.setTimestamp(3, ticket.getSubmitted());
            ps.setString(4, ticket.getDescription());
            ps.setString(5, ticket.getAuthorId());
            ps.setString(6, ticket.getType());
            ps.executeUpdate();
            logger.info("new ticket created");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("create ticket dml failed");
        }
    }


    public Ticket findById(String ticketId) {
        Ticket ticket = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT * FROM tickets WHERE ticket_id = ?";
            PreparedStatement ps = con.prepareStatement(dql);
            ps.setString(1, ticketId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ticket = populateFromResult(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info("sql exception with TicketDAO.findById()");
        }
        return ticket;
    }


    public void delete(String ticketId) {
        System.out.println(ticketId);
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM tickets WHERE ticket_id = ?");
            ps.setString(1, ticketId);
            ps.execute();
        } catch (SQLException e) {
            logger.info("sql exception deleting ticket");
        }
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


    public List<Ticket> getByStatus(TicketStatus status) {
        List<Ticket> tickets = new LinkedList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT * FROM tickets WHERE status = ?::ticket_status";
            PreparedStatement ps = con.prepareStatement(dql);
            ps.setString(1, String.valueOf(status));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(populateFromResult(rs));
            }
            logger.info("fetched all pending statuses");
        } catch (SQLException e) {
            logger.info("sql exception with TicketDAO.findById()");
        }
        return tickets;
    }

    public List<Ticket> getAllForUser(String userId) {
        List<Ticket> tickets = new ArrayList<Ticket>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT * FROM tickets WHERE author_id = ?";
            PreparedStatement ps = con.prepareStatement(dql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(populateFromResult(rs));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return tickets;
    }

    public List<Ticket> getResolvedBy(String userId) {
        List<Ticket> tickets = new ArrayList<Ticket>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String dql = "SELECT * FROM tickets WHERE resolver_id = ?";
            PreparedStatement ps = con.prepareStatement(dql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(populateFromResult(rs));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }

        return tickets;
    }

    private Ticket populateFromResult(ResultSet result) throws SQLException {
        Ticket ticket = new Ticket();

        ticket.setTicketId(result.getString("ticket_id"));
        ticket.setAmount(result.getDouble("amount"));
        ticket.setResolved(result.getTimestamp("resolved"));
        ticket.setSubmitted(result.getTimestamp("submitted"));
        ticket.setDescription(result.getString("description"));
        ticket.setAuthorId(result.getString("author_id"));
        ticket.setResolver(result.getString("resolver_id"));
        ticket.setStatus(TicketStatus.valueOf(result.getString("status")));
        ticket.setType(result.getString("ticket_type"));

        return ticket;
    }
}
