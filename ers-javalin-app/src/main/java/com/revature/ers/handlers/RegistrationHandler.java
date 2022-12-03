package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.data_access_objects.RegistrationDAO;
import com.revature.ers.data_transfer_objects.responses.RegistrationResponseDTO;
import com.revature.ers.models.Registration;
import io.javalin.http.Context;
import com.revature.ers.data_transfer_objects.requests.RegistrationRequest;
import com.revature.ers.services.RegistrationService;
import com.revature.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import java.io.IOException;
import java.util.List;

public class RegistrationHandler {
    private final RegistrationService registrationService;
    private final ObjectMapper mapper;

    public RegistrationHandler(RegistrationService registrationService, ObjectMapper mapper) {
        this.registrationService = registrationService;
        this.mapper = mapper;
    }

    public void register(Context ctx) {
        RegistrationRequest request = null;
        try {
            request = mapper.readValue(ctx.req.getInputStream(), RegistrationRequest.class);
        } catch (IOException e) {
            ctx.status(400).result("invalid parameters in message body");
        }
        try {
            registrationService.register(request);
            ctx.status(201);
        } catch (InvalidUserFieldsException e) {
            ctx.status(400).result(e.getMessage());
        }

    }

    public void getAll(Context ctx) {
        ctx.status(200).json(this.registrationService.getAll());
    }

    public void getByUsername(Context ctx) {
        String username = ctx.pathParam("username");
        RegistrationResponseDTO reg = this.registrationService.getByUsername(username);
        ctx.status(200).json(reg);
    }

    public void delete(Context ctx) {
        String username = ctx.pathParam("username");
        registrationService.delete(username);
    }

    public void approve(Context ctx) {
        String registrationId = ctx.pathParam("id");
        registrationService.approve(registrationId);
        ctx.status(201);
    }
}
