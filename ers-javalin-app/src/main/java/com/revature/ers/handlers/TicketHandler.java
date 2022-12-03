package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import com.revature.ers.data_transfer_objects.requests.TicketNew;
import com.revature.ers.models.Principal;
import com.revature.ers.models.Ticket;
import com.revature.ers.models.TicketStatus;
import com.revature.ers.services.TicketService;
import com.revature.ers.utilities.custom_exceptions.InvalidTicketRequestException;

import java.io.IOException;
import java.util.List;

public class TicketHandler {
    TicketService ticketService;
    ObjectMapper mapper;


    public TicketHandler(TicketService ticketService, ObjectMapper objectMapper) {
        this.ticketService = ticketService;
        this.mapper = objectMapper;
    }

    public void submit(Context ctx) throws IOException {
        Principal principal = ctx.attribute("principal");
        TicketNew newTicketRequest = mapper.readValue(ctx.req.getInputStream(), TicketNew.class);
        try {
            ticketService.createTicket(newTicketRequest, principal);
            ctx.status(201); // created
        } catch (InvalidTicketRequestException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public void getByStatus(Context ctx) {
        TicketStatus status = TicketStatus.valueOf(ctx.pathParam("status").toUpperCase());
        List<Ticket> ticketList = ticketService.getByStatus(status);
        ctx.json(ticketList);
    }

    public void resolve(Context ctx, TicketStatus status) {
        Principal principal = ctx.attribute("principal");
        String ticketId = ctx.pathParam("id");
        try {
            ticketService.resolve(ticketId, principal, status);
            ctx.status(202);
        } catch (InvalidTicketRequestException e) {
            ctx.status(409).result(e.getMessage());
        }
    }

    public void getAll(Context ctx) {

    }

    public void getUsersTickets(Context ctx) {

    }

    public void getByType(Context ctx) {

    }

    public void getDetails(Context ctx) {

    }
}
