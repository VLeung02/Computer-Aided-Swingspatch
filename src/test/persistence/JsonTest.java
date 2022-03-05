package persistence;

import model.CallForService;
import model.Responder;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for json, takes inspiration from the JSON example project
public class JsonTest {
    protected void checkCall(CallForService call, String address, String caller, String callType, boolean status, int callNum) {
        assertEquals(address, call.getCallAddress());
        assertEquals(caller, call.getCaller());
        assertEquals(callType, call.getCallType());
        assertEquals(status, call.getCallStatus());
        assertEquals(callNum, call.getCallNum());
    }

    protected void checkResponder(Responder r, String type, Boolean status, CallForService call, int unit) {
        assertEquals(type, r.getUnitType());
        assertEquals(status, r.getStatus());
        assertEquals(call, r.getCurrentCall());
        assertEquals(unit, r.getUnitNum());

    }
}
