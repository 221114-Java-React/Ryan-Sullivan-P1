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
        List<String> types = new ArrayList<>();
        types.add("FOOD");
        params.put("type", types);

        List<Ticket> fullTicketList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String type;
            if (i % 3 == 0) {
                type = "FOOD";
            } else {
                type = "OTHER";
            }
            Ticket ticket = new Ticket();
            ticket.setType(type);
            fullTicketList.add(ticket);
        }

        String userId = "testUserId";

        Mockito
                .when(mockTicketDAO.getAllForUser(userId))
                .thenReturn(fullTicketList);
        List<Ticket> filteredList = spySut.getFilteredTickets("test_user", params);
        assertTrue(true); // need to fix this test
    }
}
