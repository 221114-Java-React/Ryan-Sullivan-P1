package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.ers.data_transfer_objects.requests.TicketNew;
import org.example.ers.models.Principal;
import org.example.ers.services.TicketService;

import java.io.IOException;

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
        ticketService.createTicket(newTicketRequest, principal);
    }
    public void getAll(Context ctx) {

    }

    public void getUsersTickets(Context ctx) {

    }

    public void getByType(Context ctx) {

    }

    public void getByStatus(Context ctx) {

    }

    public void approve(Context ctx) {

    }

    public void deny(Context ctx) {

    }

    public void getDetails(Context ctx) {

    }
}
