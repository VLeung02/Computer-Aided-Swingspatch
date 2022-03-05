package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ResponderTest {
    private CallForService availableForCalls;
    private CallForService unavailableForCalls;
    private String policeCall = "Police Needed";
    private String fireCall = "Fire Trouble";
    private String ambulanceCall = "Medical Assistance";
    private String addressOne = "2075 West Mall";
    private String addressTwo = "2468 Oxford St";
    private String addressThree = "V5K 2P5";
    private String callerOne = "Billy Joe";
    private String callerTwo = "Joe Joe";
    private String available = "none";
    private Boolean inProgress = false;
    private Boolean complete = true;

    @BeforeEach
    void runBefore() {
        unavailableForCalls = new CallForService(addressOne, callerTwo, fireCall, inProgress, 1);
        availableForCalls = new CallForService(available, available, available, complete, 0);
        Responder r = new Responder("Police", true, availableForCalls, 0);
    }


    @Test
    void testResponderTypePolice() {
        Responder r = new Responder("Police", true, availableForCalls, 0);
        assertFalse(r.getUnitType() == "Fire");
        assertFalse(r.getUnitType() == "Ambulance");
        assertTrue(r.getUnitType() == "Police");
    }

    @Test
    void testResponderTypeAmbulance() {
        Responder r = new Responder("Ambulance", true, availableForCalls, 0);
        assertFalse(r.getUnitType() == "Fire");
        assertTrue(r.getUnitType() == "Ambulance");
        assertFalse(r.getUnitType() == "Police");
    }

    @Test
    void testResponderTypeFire() {
        Responder r = new Responder("Fire", true, availableForCalls, 0);
        assertTrue(r.getUnitType() == "Fire");
        assertFalse(r.getUnitType() == "Ambulance");
        assertFalse(r.getUnitType() == "Police");
    }

    @Test
    void testPoliceBusy() {
        CallForService busyCall = new CallForService(addressOne, callerOne, policeCall, inProgress, 2);
        Responder r = new Responder("Police", false, busyCall, 3);
        assertTrue(r.getUnitType() == "Police");
        assertFalse(r.getUnitType() == "Ambulance");
        assertFalse(r.getUnitType() == "Fire");
        assertFalse(r.getStatus());
        assertTrue(r.getCurrentCall() == busyCall);

    }

    @Test
    void testPoliceAvailable() {
        Responder r = new Responder("Police", true, availableForCalls, 0);
        assertTrue(r.getUnitType() == "Police");
        assertFalse(r.getUnitType() == "Ambulance");
        assertFalse(r.getUnitType() == "Fire");
        assertTrue(r.getStatus());
        assertEquals(r.getCurrentCall(), availableForCalls);
    }

    @Test
    void testUnitNum() {
        Responder r = new Responder("Police", true, availableForCalls, 32);

        assertEquals(32, r.getUnitNum());
        assertTrue(r.getStatus());
        assertTrue(r.getStatus());

    }

    @Test
    void testChangeStatus() {
        Responder r = new Responder("Police", false, unavailableForCalls, 32);
        Responder t = new Responder("Police", true, availableForCalls, 32);

        r.makeAvailable();
        assertTrue(r.getStatus());
    }

    @Test
    void testChangeCall() {
        Responder r = new Responder("Police", false, unavailableForCalls, 32);
        Responder t = new Responder("Police", true, availableForCalls, 32);

        r.assignCall(availableForCalls); // changes call to the argument call
        CallForService current = r.getCurrentCall(); // gets the current call
        assertTrue(current.equals(availableForCalls)); // checks if current call is the call that was just assigned
        assertFalse(r.getStatus()); // status of availability should be false now
    }

//    @Test
//    void testBackInService() {
//        CallForService call = new CallForService("fire", "billy,", "police", false, 0);
//        Responder r = new Responder("Police", false, null, 32);
//        r.assignCall(call);
//        assertEquals(r.getCurrentCall(), call);
//        assertFalse(r.getStatus());
//        assertTrue(r.getCurrentCall().getCallAddress() == "fire");
//        call.completeCall();
//        assertTrue(call.getCallStatus());
//
//        assertTrue(r.getStatus());
//        assertNull(r.getCurrentCall());
//
//    }
}