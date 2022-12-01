package org.example.ers.services;

import org.example.ers.data_access_objects.TicketDAO;
import org.example.ers.data_transfer_objects.requests.TicketNew;
import org.example.ers.models.Principal;
import org.example.ers.models.Ticket;
import org.example.ers.utilities.UtilityMethods;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService() {
        this.ticketDAO = new TicketDAO();
    }

    public void createTicket(TicketNew req, Principal principal) {
        Ticket newTicket = new Ticket(UtilityMethods.generateId(),
                                      req.getAmount(),
                                      new Timestamp(System.currentTimeMillis()),
                                      req.getDescription(),
                                      principal.getId());
        ticketDAO.create(newTicket);
    }
}
