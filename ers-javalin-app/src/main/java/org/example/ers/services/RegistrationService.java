package org.example.ers.services;

import org.apache.commons.beanutils.BeanUtils;
import org.example.ers.data_access_objects.RegistrationDAO;
import org.example.ers.data_access_objects.UserDAO;
import org.example.ers.data_access_objects.UserRoleDAO;
import org.example.ers.data_transfer_objects.requests.RegistrationRequest;
import org.example.ers.data_transfer_objects.responses.RegistrationResponseDTO;
import org.example.ers.models.Registration;
import org.example.ers.utilities.UtilityMethods;
import org.example.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import org.apache.commons.beanutils.BeanUtils.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class RegistrationService {
    private RegistrationDAO registrationDAO;
    private UserDAO userDAO;
    private UserRoleDAO userRoleDAO;

    public RegistrationService() {
        this.registrationDAO = new RegistrationDAO();
        this.userDAO = new UserDAO();
        this.userRoleDAO = new UserRoleDAO();
    }

    public void register(RegistrationRequest req) throws InvalidUserFieldsException {
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

    public List<RegistrationResponseDTO> getAll() {
        List<RegistrationResponseDTO> dtoList = new ArrayList<RegistrationResponseDTO>();

        for (Registration reg : this.registrationDAO.getAll()) {
            RegistrationResponseDTO dto = new RegistrationResponseDTO();

            try {
                BeanUtils.copyProperties(dto, reg);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    public void delete(String username) {
        this.registrationDAO.delete(username);
    }

    public void approve(String registrationId) {
        Registration registration = registrationDAO.findById(registrationId);
        userDAO.createFromRegistration(registration);
        registrationDAO.delete(registrationId);
    }

    private void validateRequest(RegistrationRequest req) throws InvalidUserFieldsException {
        String username = req.getUsername();
        String email = req.getEmail();
        if (!UtilityMethods.validUsername(username)) throw new InvalidUserFieldsException("invalid username");
        if (!UtilityMethods.validEmail(email)) throw new InvalidUserFieldsException("invalid email");
        if (!req.getPasswordOne().equals(req.getPasswordTwo())) throw new InvalidUserFieldsException("passwords do not match");
        if (!UtilityMethods.validPassword(req.getPasswordOne())) throw new InvalidUserFieldsException("invalid password");

        if (this.userDAO.usernameTaken(username) || this.registrationDAO.usernameTaken(username)) {
            throw new InvalidUserFieldsException("username taken");
        }
        if (this.userDAO.emailTaken(email) || this.registrationDAO.emailTaken(email)) {
            throw new InvalidUserFieldsException("email taken");
        }
        if (!this.userRoleDAO.validRole(req.getRoleId())) throw new InvalidUserFieldsException("invalid role");
    }
}
