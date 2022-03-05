package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// A list of all the responders
public class AllResponders implements Writable {
    private List<Responder> allResponders;

    // EFFECTS: Creates a new empty list of all the responders
    public AllResponders() { // constructs new empty list of all responders
        allResponders = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: If the call the responder is attached to is completed, make that responder available for calls.
    public void backInService() {
        for (Responder r : allResponders) {
            CallForService current = r.getCurrentCall();
            if (current.getCallAddress() != "none") {
                r.returnToService();
            }
        }
    }

    // EFFECTS: returns a list of the responder unit numbers
    public List<Integer> getAllUnitNumbers() {
        List<Integer> unitNumbers = new LinkedList<>();
        for (Responder r : allResponders) {
            Integer unitNumber = r.getUnitNum();
            unitNumbers.add(unitNumber);
        }
        return unitNumbers;
    }

    // MODIFIES: this
    // EFFECTS: Makes a list of available responders only
    public List<Responder> makeAvailableOnly() {
        List<Responder> availableOnly = new LinkedList<>();
        for (Responder t : allResponders) {
            if (t.getStatus()) {
                availableOnly.add(t);
            }
        }
        return availableOnly;
    }

    // MODIFIES: this
    // EFFECTS: Return a new list of all the unavailable responders only.
    public List<Responder> makeUnavailableOnly() {
        List<Responder> unavailableResponders = new LinkedList<>();

        for (Responder t : allResponders) {
            if (!t.getStatus()) {
                unavailableResponders.add(t);
            }
        }
        return unavailableResponders;
    }

    // MODIFIES: this
    // EFFECTS: Adds a responder to the list of responders
    public void addResponder(Responder r) {
        allResponders.add(r);
        EventLog.getInstance().logEvent(new Event("New responder on-duty, number: "
                + r.getUnitNum() + ", " + r.getUnitType()));
    }

    // MODIFIES: this
    // EFFECTS: Removes a responder with the given unit number from the list of responders
    public void removeResponder(int unit) {
        allResponders.remove(unit);
        EventLog.getInstance().logEvent(new Event("Responder off-duty, number: " + unit));
    }

    // EFFECTS: Return the number of responders in the list
    public int numberOfResponders() {
        return allResponders.size();
    }

    // EFFECTS: Returns responder with given unit number
    public Responder getResponder(int unitNum) {

        for (Responder t : allResponders) {
            if (t.getUnitNum() == unitNum) {
                return t;
            }
            continue;
        }
        return null;
    }

    // REQUIRES: Responders in the available responder list
    // MODIFIES: this
    // EFFECTS: Assigns first available responder of the same call type to the call
    public Boolean assignResponder(Responder r, CallForService c) {
        String unitType = r.getUnitType();
        String callType = c.getCallType();
        Boolean status = r.getStatus();

        if (status) {
            if (callType.contains(unitType)) {
                r.assignCall(c);
                r.makeUnAvailable();
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("All Responders: ", respondersToJson());
        return json;
    }

    // EFFECTS: returns each call for service in the list of calls as a JSON Array
    private JSONArray respondersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Responder r : allResponders) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}
