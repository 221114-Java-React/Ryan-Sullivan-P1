package com.revature.ers.services;

import com.revature.ers.data_access_objects.RegistrationDAO;
import com.revature.ers.data_transfer_objects.responses.RegistrationResponseDTO;
import com.revature.ers.models.Registration;
import com.revature.ers.models.User;
import org.apache.commons.beanutils.BeanUtils;
import com.revature.ers.data_access_objects.UserDAO;
import com.revature.ers.data_access_objects.UserRoleDAO;
import com.revature.ers.data_transfer_objects.requests.RegistrationRequest;
import com.revature.ers.utilities.UtilityMethods;
import com.revature.ers.utilities.custom_exceptions.InvalidUserFieldsException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class RegistrationService {
    private final RegistrationDAO registrationDAO;
    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;

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

            UtilityMethods.copyBean(dto, reg);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public RegistrationResponseDTO getByUsername(String username) {
        Registration reg = this.registrationDAO.findByUsername(username);
        RegistrationResponseDTO res = new RegistrationResponseDTO();
        UtilityMethods.copyBean(res, reg);
        return res;
    }



    public void delete(String username) {
        this.registrationDAO.delete(username);
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
