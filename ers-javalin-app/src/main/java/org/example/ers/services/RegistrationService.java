package org.example.ers.services;

import org.example.ers.data_access_objects.RegistrationDAO;
import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_transfer_objects.requests.RegistrationRequest;
import org.example.ers.models.Registration;
import org.example.ers.models.User;
import org.example.ers.utilities.UtilityMethods;
import org.example.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import java.util.Properties;

public class RegistrationService {
    private final Properties properties;
    private RegistrationDAO registrationDAO;
    private UserDAO userDAO;

    public RegistrationService() {
        this.properties = new Properties();
        this.registrationDAO = new RegistrationDAO();
        this.userDAO = new UserDAO();
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

    public void approve(String registrationId) {
        Registration registration = registrationDAO.findById(registrationId);
        userDAO.createFromRegistration(registration);
        registrationDAO.delete(registrationId);
    }

    private void validateRequest(RegistrationRequest req) {
    }
}
