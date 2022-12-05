package org.example.ers.services;

import com.revature.ers.data_access_objects.RegistrationDAO;
import com.revature.ers.data_access_objects.UserDAO;
import com.revature.ers.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService sut;
    private final UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
    private final RegistrationDAO registrationDAO = Mockito.mock(RegistrationDAO.class);

    @Before
    public void init() {
        sut = new UserService(mockUserDAO, registrationDAO);
    }

    @Test
    public void test_resetPassword_returnsNewPassword() {
        UserService spySut = Mockito.spy(sut);
        String newPassword = spySut.resetPassword("test person");
        assertNotNull(newPassword);
    }
}
