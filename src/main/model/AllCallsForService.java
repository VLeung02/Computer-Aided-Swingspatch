package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represents the list of all Calls For Service
public class AllCallsForService implements Writable {
    private List<CallForService> allCalls;

    // EFFECTS: creates a new empty list of all calls for service
    public AllCallsForService() {
        allCalls = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new call to the list of active calls for service
    public void openCall(CallForService c) {
        allCalls.add(c);
        EventLog.getInstance().logEvent(new Event("Call added, number: " + c.getCallNum()));
    }

    // REQUIRES: non-empty list of all calls for service
    // MODIFIES: this
    // EFFECTS: removes a completed call for service from the active calls and adds to the inactive calls
    public void closeCall(int caseNum) {
        CallForService currentCall = allCalls.get(caseNum);
        currentCall.completeCall();
        EventLog.getInstance().logEvent(new Event("Call completed, number: " + caseNum));
    }

    // EFFECTS: Returns number of calls in the stack
    public int getNumAllCalls() {
        return allCalls.size();
    }

    // REQUIRES: A list of calls to exist
    // EFFECTS: Returns a list of all the call numbers as integers
    public List<Integer> getCallNumbers() {
        List<Integer> callNumberList = new ArrayList<>();
        for (CallForService c : allCalls) {
            callNumberList.add(c.getCallNum());
        }
        return callNumberList;
    }

    // REQUIRES: A call to exist
    // EFFECTS: Searches the call list with the call with the case Number i
    public CallForService getCall(int i) {
        for (CallForService c : allCalls) {
            if (c.getCallNum() == i) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: A list of calls
    // EFFECTS: Returns a list of all the address in the call stack as a list of strings
    public List<String> getAllAddress() {
        List<String> allAddress = new LinkedList<>();

        for (CallForService c : allCalls) {
            allAddress.add(c.getCallAddress());
        }
        return allAddress;
    }

    // REQUIRES: A list of calls
    // EFFECTS: Returns a list of all the call types in the call stack as a list of strings
    public List<String> getAllCallTypes() {
        List<String> allCallTypes = new LinkedList<>();

        for (CallForService c : allCalls) {
            allCallTypes.add(c.getCallType());
        }
        return allCallTypes;
    }


    // REQUIRES: non-empty list
    // EFFECTS: Return true if  a call with the given call number i exists in the call stack
    public Boolean searchCallList(int i) {
        for (CallForService c : allCalls) {
            int callNum = c.getCallNum();
            if (callNum == i) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: non-empty list of all calls for service
    // EFFECTS: returns number of active calls in the stack
    public int getNumOpenCalls() {
        int i = 0;

        for (CallForService c : allCalls) {
            if (c.getCallStatus()) {
                continue;
            }
            i++;
        }
        return i;
    }

    // REQUIRES: non-empty list
    // EFFECTS: Returns list of in-progress calls only
    public List<CallForService> getInProgress() {
        List<CallForService> inProgressOnly = new LinkedList<>();

        for (CallForService c : allCalls) {
            if (c.getCallStatus()) {
                continue;
            }
            inProgressOnly.add(c);
        }
        return inProgressOnly;
    }

    // REQUIRES: non-empty list
    // EFFECTS: Returns list of completed calls only
    public List<CallForService>  getCompleted() {
        List<CallForService> completeOnly = new LinkedList<>();

        for (CallForService c : allCalls) {
            if (!c.getCallStatus()) {
                continue;
            }
            completeOnly.add(c);
        }
        return completeOnly;
    }

    // REQUIRES: non-empty list of all calls for service
    // EFFECTS: returns number of inactive/closed calls in the stack
    public int getNumClosedCalls() {
        int i = 0;

        for (CallForService c : allCalls) {
            if (c.getCallStatus()) {
                i++;
            }
            continue;
        }
        return i;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Calls For Service: ", callsToJson());
        return json;
    }

    // EFFECTS: returns each call for service in the list of calls as a JSON Array
    private JSONArray callsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CallForService c : allCalls) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
