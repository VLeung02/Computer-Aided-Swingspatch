package model;


import org.json.JSONObject;
import persistence.Writable;

// A single responder with a type name, availability status, current call and unit number
public class Responder implements Writable {
    private String type;
    private Boolean status;
    private int unit;
    private CallForService call;


    // EFFECTS: Creates a new responder
    public Responder(String type, Boolean status, CallForService call, int unit) {
        this.type = type;
        this.status = status;
        this.call = call;
        this.unit = unit;
    }

    // REQUIRES: A responder
    // MODIFIES: this
    // EFFECTS: assigns the responder to the given call
    public void assignCall(CallForService nextCall) {
        call = nextCall;
        EventLog.getInstance().logEvent(
                new Event("Assigned responder: " + getUnitNum() + " to call at: " + nextCall.getCallAddress()));
    }

    // REQUIRES: A responder
    // MODIFIES: this
    // EFFECTS: gets the responder's status
    public Boolean getStatus() {
        return status;
    }

    // REQUIRES: A responder
    // MODIFIES: this
    // EFFECTS: gets the responder's unit type
    public String getUnitType() {
        return type;
    }

    // REQUIRES: A responder
    // MODIFIES: this
    // EFFECTS: gets the responder's current call
    public CallForService getCurrentCall() {
        return call;
    }

    // REQUIRES: A responder
    // MODIFIES: this
    // EFFECTS: gets the responder's unit number
    public int getUnitNum() {
        return unit;
    }

    // REQUIRES: a responder
    // MODIFIES: this
    // EFFECTS: changes the status to true
    public void makeAvailable() {
        status = true;
    }

    // REQUIRES: a responder and a call
    // MODIFIES: this
    // EFFECTS: changes the status to false
    public void makeUnAvailable() {
        status = false;
    }

    // MODIFIES: this
    // EFFECTS: returns a responder to service
    public void returnToService() {
        CallForService available = new CallForService("none", "none", "none", true, -1);
        this.status = true;
        this.call = available;
        EventLog.getInstance().logEvent(new Event("Returned: "
                +
                getUnitNum() + ", " + getUnitType() + " back to service"));
    }

    // EFFECTS: parses call details and unit type to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Unit Type", type);
        json.put("Unit Status", status);
        json.put("Current Call", call);
        json.put("Unit Number", unit);
        json.put("Call Number", call.getCallNum());
        json.put("Call Address", call.getCallAddress());
        json.put("Call Type", call.getCallType());
        json.put("Status", call.getCallStatus());
        json.put("Reporting Party", call.getCaller());
        return json;

    }

}
