package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for AllResponders
public class AllRespondersTest {
    private AllResponders responders;
    private int x = 0;
    private String policeCall = "Police Needed";
    private String fireCall = "Fire Trouble";
    private String ambulanceCall = "Medical Assistance";
    private String addressOne = "2075 West Mall";
    private String addressTwo = "2468 Oxford St";
    private String addressThree = "V5K 2P5";
    private String callerOne = "Billy Joe";
    private String callerTwo = "Joe Joe";
    private String available = "none";
    private CallForService availableForCalls;
    private CallForService unAvailableForCalls;
    private Boolean inProgress = false;
    private Boolean complete = true;

    @BeforeEach
    void runBefore() {
        availableForCalls = new CallForService(available, available, available, complete, 1);
        unAvailableForCalls = new CallForService(addressOne, callerOne, policeCall, inProgress, 1);
        responders = new AllResponders();
    }

    @Test
    void testEmpty() {
        assertEquals(0, responders.numberOfResponders());
    }


    @Test
    void testRemoveResponder() {
        AllResponders allResponders = new AllResponders();

        for (int i = 1; i <= 10; i++) {
            x++;
            Responder r = new Responder("Police", true, availableForCalls, x);
            allResponders.addResponder(r);
        }

        allResponders.removeResponder(2);
        assertEquals(9, allResponders.numberOfResponders());
        assertFalse(10 == allResponders.numberOfResponders());
    }

    @Test
    void testAddResponder() {
        AllResponders allResponders = new AllResponders();
        Responder t = new Responder("Police", true, availableForCalls, 0);

        allResponders.addResponder(t);
        assertEquals(1, allResponders.numberOfResponders());
    }

    @Test
    void testCallAssignment() {
        CallForService newFireCall = new CallForService(addressOne, callerOne, fireCall, inProgress,2);
        CallForService newPoliceCall = new CallForService(addressTwo, callerOne, policeCall, inProgress, 2);

        Responder unAvailableOne = new Responder("Police", false, unAvailableForCalls, 4);
        Responder unAvailableTwo = new Responder("Fire", false, unAvailableForCalls, 5);
        Responder unAvailableThree = new Responder("Ambulance", false, unAvailableForCalls, 6);

        Responder availableOne = new Responder("Police", true, availableForCalls, 1);
        Responder availableTwo = new Responder("Fire", true, availableForCalls, 2);
        Responder availableThree = new Responder("Ambulance", true, availableForCalls, 3);

        AllResponders busyResponders = new AllResponders();
        busyResponders.addResponder(unAvailableOne);
        busyResponders.addResponder(unAvailableTwo);
        busyResponders.addResponder(unAvailableThree);

        AllResponders availableResponders = new AllResponders();
        availableResponders.addResponder(availableOne);
        availableResponders.addResponder(availableTwo);
        availableResponders.addResponder(availableThree);


        assertTrue(availableTwo.getCurrentCall() == availableForCalls);  //testing available two before call assign
        assertFalse(availableTwo.getCurrentCall() == newPoliceCall);
        assertTrue(availableTwo.getStatus());

        assertTrue(availableResponders.assignResponder(availableTwo, newFireCall));  //testing what should happen if i assign
        assertFalse(availableResponders.assignResponder(availableTwo, newPoliceCall));

        availableResponders.assignResponder(availableTwo, newFireCall);            // assigning to call and testing the changed values after
        assertTrue(availableTwo.getCurrentCall() == newFireCall);  // testing if current call is infact the new call
        assertFalse(availableTwo.getStatus()); // testing if unit is now busy with false as status
        //assertEquals(0, availableResponders.numberOfResponders());

        assertFalse(availableResponders.assignResponder(availableOne, newFireCall));  // these should all fail because they arent available and of same type
        assertFalse(availableResponders.assignResponder(availableThree, newFireCall));
        assertFalse(busyResponders.assignResponder(unAvailableOne, newFireCall));
        assertFalse(busyResponders.assignResponder(unAvailableTwo, newFireCall));
        assertFalse(busyResponders.assignResponder(unAvailableThree, newFireCall));



        assertTrue(availableOne.getCurrentCall() == availableForCalls);
        assertFalse(availableOne.getCurrentCall() == newPoliceCall);
        assertTrue(availableOne.getStatus());

        assertTrue(availableResponders.assignResponder(availableOne, newPoliceCall));
        assertFalse(availableResponders.assignResponder(availableOne, newFireCall));

        availableResponders.assignResponder(availableOne, newPoliceCall);
        assertTrue(availableOne.getCurrentCall() == newPoliceCall);
        assertFalse(availableOne.getCurrentCall() == newFireCall);
        assertFalse(availableOne.getCurrentCall() == availableForCalls);
        assertFalse(availableOne.getStatus());

    }

    @Test
    void testAvailableOnly() {
        AllResponders all = new AllResponders();

        Responder unAvailableThree = new Responder("Ambulance", false, unAvailableForCalls, 6);
        Responder availableOne = new Responder("Police", true, availableForCalls, 1);
        Responder availableTwo = new Responder("Fire", true, availableForCalls, 2);
        Responder availableThree = new Responder("Ambulance", true, availableForCalls, 3);

        all.addResponder(unAvailableThree);
        all.addResponder(availableOne);
        all.addResponder(availableTwo);
        all.addResponder(availableThree);

        List<Responder> availableOnly = all.makeAvailableOnly();
        assertEquals(3, availableOnly.size());
        assertFalse(availableOnly.size() == 4);

        Responder unavailThree = all.getResponder(6);

        assertEquals(6,unavailThree.getUnitNum());
        assertNotEquals(1, unavailThree.getUnitNum());

    }

    @Test
    void testUnAvailableOnly() {
        AllResponders all = new AllResponders();

        Responder unAvailableThree = new Responder("Ambulance", false, unAvailableForCalls, 6);
        Responder availableOne = new Responder("Police", true, availableForCalls, 1);
        Responder availableTwo = new Responder("Fire", true, availableForCalls, 2);
        Responder availableThree = new Responder("Ambulance", true, availableForCalls, 3);

        all.addResponder(unAvailableThree);
        all.addResponder(availableOne);
        all.addResponder(availableTwo);
        all.addResponder(availableThree);

        List<Responder> unavailableOnly = all.makeUnavailableOnly();
        assertEquals(1, unavailableOnly.size());
        assertFalse(unavailableOnly.size() == 4);
    }

    @Test
    void testGetNullUnitNum() {
        AllResponders all = new AllResponders();

        Responder unAvailableThree = new Responder("Ambulance", false, unAvailableForCalls, 6);
        Responder availableOne = new Responder("Police", true, availableForCalls, 1);
        Responder availableTwo = new Responder("Fire", true, availableForCalls, 2);
        Responder availableThree = new Responder("Ambulance", true, availableForCalls, 3);

        all.addResponder(unAvailableThree);
        all.addResponder(availableOne);
        all.addResponder(availableTwo);
        all.addResponder(availableThree);

        assertTrue(all.getResponder(8) == null);


    }

    @Test
    void testGetAllUnitNumbers() {
        responders = new AllResponders();

        Responder unAvailableThree = new Responder("Ambulance", false, unAvailableForCalls, 6);
        Responder availableOne = new Responder("Police", true, availableForCalls, 1);
        Responder availableTwo = new Responder("Fire", true, availableForCalls, 2);
        Responder availableThree = new Responder("Ambulance", true, availableForCalls, 3);

        responders.addResponder(unAvailableThree);
        responders.addResponder(availableOne);
        responders.addResponder(availableTwo);
        responders.addResponder(availableThree);

        assertEquals(4, responders.getAllUnitNumbers().size());
        assertTrue(responders.getAllUnitNumbers().contains(6));
        assertTrue(responders.getAllUnitNumbers().contains(1));
        assertTrue(responders.getAllUnitNumbers().contains(2));
        assertTrue(responders.getAllUnitNumbers().contains(3));
        assertFalse(responders.getAllUnitNumbers().contains(4));
    }

    @Test
    void testBackInService() {
        responders = new AllResponders();
        Responder unAvailableThree = new Responder("Ambulance", false, unAvailableForCalls, 6);
        Responder availableOne = new Responder("Police", true, availableForCalls, 1);
        Responder availableTwo = new Responder("Fire", true, availableForCalls, 2);
        Responder availableThree = new Responder("Ambulance", true, availableForCalls, 3);

        responders.addResponder(unAvailableThree);
        responders.addResponder(availableOne);
        responders.addResponder(availableTwo);
        responders.addResponder(availableThree);

        responders.backInService();
        assertFalse(responders.getResponder(6).getCurrentCall().getCallStatus());
        unAvailableForCalls.completeCall();
        responders.backInService();

        assertEquals(4, responders.numberOfResponders());
        assertTrue(responders.getResponder(6).getCurrentCall().getCallStatus());

    }

    }



