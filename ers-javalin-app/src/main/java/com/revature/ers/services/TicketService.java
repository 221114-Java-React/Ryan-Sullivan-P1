package com.revature.ers.services;

import com.revature.ers.data_access_objects.TicketDAO;
import com.revature.ers.data_transfer_objects.requests.SubmitTicketRequest;
import com.revature.ers.models.Principal;
import com.revature.ers.models.TicketStatus;
import com.revature.ers.utilities.UtilityMethods;
import com.revature.ers.models.Ticket;
import com.revature.ers.utilities.custom_exceptions.InvalidTicketRequestException;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService() {
        this.ticketDAO = new TicketDAO();
    }

    public void createTicket(SubmitTicketRequest req, Principal principal) throws InvalidTicketRequestException {
        if (req.getAmount() <= 0) throw new InvalidTicketRequestException("amount must be positive");
        if (req.getDescription() == null || req.getDescription().isEmpty()) {
            throw new InvalidTicketRequestException("must provide description");
        }
        if (req.getType() == null || req.getType().isEmpty()) {
            throw new InvalidTicketRequestException("must provide type");
        }

        Ticket newTicket = new Ticket(UtilityMethods.generateId(),
                                      req.getAmount(),
                                      new Timestamp(System.currentTimeMillis()),
                                      req.getDescription(),
                                      principal.getId());
        newTicket.setType(req.getType());
        ticketDAO.create(newTicket);
    }

    public void resolve(String ticketId, Principal principal, TicketStatus status) throws InvalidTicketRequestException {
        Ticket ticket = ticketDAO.findById(ticketId);
        if (ticket.getStatus() != TicketStatus.PENDING) {
            throw new InvalidTicketRequestException("ticket is already resolved");
        }
        ticket.setStatus(status);
        ticket.setResolver(principal.getId());
        ticket.setResolved(new Timestamp(System.currentTimeMillis()));
        ticketDAO.resolve(ticket);
    }

    public List<Ticket> getByStatus(TicketStatus status) {
        return ticketDAO.getByStatus(status);
    }

    public List<Ticket> getAllForUser(String userId) {
        return ticketDAO.getAllForUser(userId);
    }
}
