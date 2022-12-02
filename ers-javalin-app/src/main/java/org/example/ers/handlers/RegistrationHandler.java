package org.example.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.ers.data_transfer_objects.requests.RegistrationRequest;
import org.example.ers.services.RegistrationService;

import java.io.IOException;

public class RegistrationHandler {
    private RegistrationService registrationService;
    private ObjectMapper mapper;

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
        registrationService.register(request);
        ctx.status(201);
    }

    public void approve(Context ctx) {

    }
}
