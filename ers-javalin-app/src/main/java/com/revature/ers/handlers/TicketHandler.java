package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.ers.data_access_objects.TicketDAO;
import io.javalin.http.Context;
import com.revature.ers.data_transfer_objects.requests.SubmitTicketRequest;
import com.revature.ers.models.Principal;
import com.revature.ers.models.Ticket;
import com.revature.ers.models.TicketStatus;
import com.revature.ers.services.TicketService;
import com.revature.ers.utilities.custom_exceptions.InvalidTicketRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TicketHandler {
    private final static Logger logger = LoggerFactory.getLogger(TicketHandler.class);
    TicketService ticketService;
    ObjectMapper mapper;


    public TicketHandler(TicketService ticketService, ObjectMapper objectMapper) {
        this.ticketService = ticketService;
        this.mapper = objectMapper;
    }

    public void submit(Context ctx) throws IOException {
        Principal principal = ctx.attribute("principal");
        try {
            SubmitTicketRequest newTicketRequest = mapper.readValue(ctx.req.getInputStream(), SubmitTicketRequest.class);
            ticketService.createTicket(newTicketRequest, principal);
            ctx.status(201); // created
        } catch (UnrecognizedPropertyException e) {
            logger.info(e.getMessage());
            ctx.status(400).result(e.getMessage());
        } catch (InvalidTicketRequestException e) {
            logger.info(e.getMessage());
            ctx.status(400).result(e.getMessage());
        }
    }

    public void getAllMyTickets(Context ctx) {
        Principal principal = ctx.attribute("principal");
        List<Ticket> tickets = ticketService.getAllForUser(principal.getId());
        ctx.json(tickets);
    }

    public void getMyFilteredTickets(Context ctx) {
        Principal principal = ctx.attribute("principal");
        Map<String, List<String>> paramMap = ctx.queryParamMap();
        List<Ticket> tickets = ticketService.getFilteredTickets(principal.getId(), paramMap);
        ctx.status(200).json(tickets);
    }

    public void deleteMyTicket(Context ctx) {
        Principal principal = ctx.attribute("principal");
        String ticketId = ctx.pathParam("id");
        try {
            ticketService.deleteFor(principal.getId(), ticketId);
            ctx.status(200);
        } catch (InvalidTicketRequestException e) {
            ctx.status(400).result(e.getMessage());
        }
    }

    public void getAllPending(Context ctx) {
        List<Ticket> ticketList = ticketService.getByStatus(TicketStatus.PENDING);
        ctx.json(ticketList);
    }

    public void getResolvedBy(Context ctx) {
        Principal principal = ctx.attribute("principal");
        List<Ticket> ticketList = ticketService.getResolvedBy(principal.getId());
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
}
