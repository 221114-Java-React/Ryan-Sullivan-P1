package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.ers.data_transfer_objects.requests.RegistrationRequest;
import org.example.ers.services.RegistrationService;
import org.example.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import java.io.IOException;

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
