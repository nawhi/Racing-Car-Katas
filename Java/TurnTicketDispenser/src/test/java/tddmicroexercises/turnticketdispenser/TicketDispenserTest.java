package tddmicroexercises.turnticketdispenser;

import org.junit.After;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TicketDispenserTest {

    @After
    public void tearDown() {
        TurnNumberSequence.reset();
    }

    @Test
    public void dispenses_tickets_with_ascending_numbers() {
        TicketDispenser dispenser = new TicketDispenser();
        assertEquals(0, dispenser.getTurnTicket().getTurnNumber());
        assertEquals(1, dispenser.getTurnTicket().getTurnNumber());
        assertEquals(2, dispenser.getTurnTicket().getTurnNumber());
        assertEquals(3, dispenser.getTurnTicket().getTurnNumber());
    }

    @Test
    public void two_dispensers() {
        TicketDispenser dispenser1 = new TicketDispenser();
        TicketDispenser dispenser2 = new TicketDispenser();

        assertEquals(0, dispenser1.getTurnTicket().getTurnNumber());
        assertEquals(1, dispenser2.getTurnTicket().getTurnNumber());
        assertEquals(2, dispenser1.getTurnTicket().getTurnNumber());
        assertEquals(3, dispenser2.getTurnTicket().getTurnNumber());


    }
}
