package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallForServiceTest {
    private String policeCall = "Police Needed";
    private String fireCall = "Fire Trouble";
    private String ambulanceCall = "Medical Assistance";
    private String addressOne = "2075 West Mall";
    private String addressTwo = "2468 Oxford St";
    private String addressThree = "V5K 2P5";
    private String callerOne = "Billy Joe";
    private String callerTwo = "Joe Joe";
    private Boolean inProgress = false;
    private Boolean complete = true;
    private String available = "none";


    @BeforeEach
    void runBefore() {
        CallForService call = new CallForService(addressOne, callerOne, fireCall, inProgress, 1);
    }

    @Test
    void testCallCreation() {
        CallForService call = new CallForService(addressTwo, callerTwo, policeCall, inProgress, 1);
        assertTrue(policeCall.equals(call.getCallType()));
        assertFalse(ambulanceCall.equals(call.getCallType()));
        assertFalse(fireCall.equals(call.getCallType()));

        assertTrue(addressTwo.equals(call.getCallAddress()));
        assertFalse(addressThree.equals(call.getCallAddress()));
        assertFalse(addressOne.equals(call.getCallAddress()));

        assertTrue(callerTwo.equals(call.getCaller()));
        assertFalse(callerOne.equals(call.getCaller()));

        assertFalse(call.getCallStatus());
        assertEquals(1, call.getCallNum());

    }

    @Test
    void testCompleteCall() {
        CallForService call = new CallForService(addressTwo, callerTwo, policeCall, inProgress, 1);
        CallForService callTwo = new CallForService(available, available, available, complete, 0);

        call.completeCall();

        assertTrue(call.getCallStatus());


        assertEquals(1, call.getCallNum());
        assertTrue(addressTwo.equals(call.getCallAddress()));
        assertTrue(callerTwo.equals(call.getCaller()));
        assertTrue(policeCall.equals(call.getCallType()));
    }

}