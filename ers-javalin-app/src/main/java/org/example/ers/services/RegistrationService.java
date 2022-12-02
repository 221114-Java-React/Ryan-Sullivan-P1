package org.example.ers.services;

import org.example.ers.data_access_objects.RegistrationDAO;
import org.example.ers.data_transfer_objects.requests.RegistrationRequest;
import org.example.ers.models.Registration;
import org.example.ers.utilities.UtilityMethods;
import org.example.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import java.util.Properties;

public class RegistrationService {
    private final Properties properties;
    private RegistrationDAO registrationDAO;

    public RegistrationService() {
        this.properties = new Properties();
        this.registrationDAO = new RegistrationDAO();
    }

    public void register(RegistrationRequest req) {
        validateRequest(req);
        Registration reg = new Registration(
                UtilityMethods.generateId(),
                req.getUsername(),
                req.getEmail(),
                UtilityMethods.hashPasswordWithSalt(req.getPasswordOne()),
                req.getGivenName(),
                req.getSurname(),
                req.getRoleId()
        );
        this.registrationDAO.register(reg);
    }

    private void validateRequest(RegistrationRequest req) {
    }
}
