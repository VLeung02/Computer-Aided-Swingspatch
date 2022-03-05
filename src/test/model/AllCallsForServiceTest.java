package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for AllCallsForService
public class AllCallsForServiceTest {
    private AllCallsForService allCalls;
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
        allCalls = new AllCallsForService();
    }

    @Test
    void testSize() {
        allCalls = new AllCallsForService();
        CallForService call = new CallForService(addressOne, callerOne,policeCall, inProgress, 1);
        CallForService callTwo = new CallForService(addressTwo, callerOne,fireCall, inProgress, 2);
        CallForService callThree = new CallForService(available, available,available, complete  , 0);

        allCalls.openCall(call);
        allCalls.openCall(callTwo);
        allCalls.openCall(callThree);


        assertEquals(2, allCalls.getNumOpenCalls());
        assertNotEquals(3, allCalls.getNumOpenCalls());
        assertEquals(1, allCalls.getNumClosedCalls());
        assertNotEquals(3, allCalls.getNumClosedCalls());
    }

    @Test
    void testCloseCall() {
        allCalls = new AllCallsForService();
        CallForService call = new CallForService(addressOne, callerOne, policeCall, inProgress, 1);
        CallForService callTwo = new CallForService(addressTwo, callerOne, fireCall, inProgress, 2);
        CallForService callThree = new CallForService(available, available, available, complete, 0);

        allCalls.openCall(call);
        allCalls.openCall(callTwo);
        allCalls.openCall(callThree);

        allCalls.closeCall(1);


        assertEquals(1, allCalls.getNumOpenCalls());
        assertNotEquals(3, allCalls.getNumOpenCalls());
        assertEquals(2, allCalls.getNumClosedCalls());
        assertNotEquals(3, allCalls.getNumClosedCalls());
    }

    @Test
    void testGetOpenCalls() {
        allCalls = new AllCallsForService();

        CallForService call = new CallForService(addressOne, callerOne, policeCall, inProgress, 1);
        CallForService callTwo = new CallForService(addressTwo, callerOne, fireCall, inProgress, 2);
        CallForService callThree = new CallForService(available, available, available, complete, 0);

        allCalls.openCall(call);
        allCalls.openCall(callTwo);
        allCalls.openCall(callThree);


        assertEquals(3, allCalls.getNumAllCalls());
        assertEquals(2, allCalls.getNumOpenCalls());
        assertEquals(1, allCalls.getNumClosedCalls());
    }

    @Test
    void testSearchCallList() {
        allCalls = new AllCallsForService();

        CallForService call = new CallForService(addressOne, callerOne, policeCall, inProgress, 1);
        CallForService callTwo = new CallForService(addressTwo, callerOne, fireCall, inProgress, 2);
        CallForService callThree = new CallForService(available, available, available, complete, 0);

        allCalls.openCall(call);
        allCalls.openCall(callTwo);
        allCalls.openCall(callThree);

        assertTrue(allCalls.searchCallList(1));
        assertTrue(allCalls.searchCallList(0));
        assertTrue(allCalls.searchCallList(0));
        assertFalse(allCalls.searchCallList(3));
    }

    @Test
    void testCallNumList() {
        allCalls = new AllCallsForService();

        CallForService call = new CallForService(addressOne, callerOne, policeCall, inProgress, 1);
        CallForService callTwo = new CallForService(addressTwo, callerOne, fireCall, inProgress, 2);
        CallForService callThree = new CallForService(available, available, available, complete, 0);

        allCalls.openCall(call);
        allCalls.openCall(callTwo);
        allCalls.openCall(callThree);

        List<Integer> listCallNum = allCalls.getCallNumbers();

        assertTrue(listCallNum.contains(1));
        assertTrue(listCallNum.contains(2));
        assertTrue(listCallNum.contains(0));
        assertFalse(listCallNum.contains(3));

        CallForService gottenCall = allCalls.getCall(1);
        String firstAddress = gottenCall.getCallAddress();

        assertTrue(firstAddress == addressOne);

    }

    @Test
    void testAllAddress() {
        allCalls = new AllCallsForService();

        CallForService call = new CallForService(addressOne, callerOne, policeCall, inProgress, 1);
        CallForService callTwo = new CallForService(addressTwo, callerOne, fireCall, inProgress, 2);
        CallForService callThree = new CallForService(available, available, available, complete, 0);

        allCalls.openCall(call);
        allCalls.openCall(callTwo);
        allCalls.openCall(callThree);

        List<String> allAddress = allCalls.getAllAddress();
        List<String> allCallTypes = allCalls.getAllCallTypes();
        String policeCall = allCallTypes.get(0);

        assertEquals (3, allAddress.size());

        assertTrue(policeCall.equals("Police Needed"));

        List<CallForService> allInProgress = allCalls.getInProgress();

        assertEquals(2, allInProgress.size());

        List<CallForService>  allCompleted = allCalls.getCompleted();

        assertEquals(1, allCompleted.size());

    }

    @Test
    void testNullCallNum() {
        AllCallsForService all = new AllCallsForService();

        CallForService callTwo = new CallForService(addressTwo, callerOne, fireCall, inProgress, 2);
        CallForService callThree = new CallForService(available, available, available, complete, 0);

        all.openCall(callTwo);
        all.openCall(callThree);

        assertTrue(all.getCall(5) == null);

    }

}
