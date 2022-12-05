package org.example.ers.services;

import com.revature.ers.data_access_objects.TicketDAO;
import com.revature.ers.models.Ticket;
import com.revature.ers.services.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TicketServiceTest {
    private TicketService sut;
    private final TicketDAO mockTicketDAO = Mockito.mock(TicketDAO.class);

    @Before
    public void init() {
        sut = new TicketService(mockTicketDAO);
    }

    @Test
    public void test_getFilteredList_properlyFiltersList() {
        TicketService spySut = Mockito.spy(sut);
        Map<String, List<String>> params = new HashMap<>();
        List<Ticket> fullTicketList = new ArrayList<>();
        List<Ticket> filteredList = spySut.getFilteredTickets("test_user", params);

        // controlled env
        //Mockito.when(mockUserDao.findAllUsernames()).thenReturn(stubbedUsernames);
        //Mockito.verify(mockUserDao, Mockito.times(1)).save(createdUser);
    }
}
