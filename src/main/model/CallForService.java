package model;

import org.json.JSONObject;
import persistence.Writable;

// A Call For Service with a string address, string caller, string call-type, boolean callStatus, integer call number.
public class CallForService implements Writable {
    private String callAddress;
    private String caller;
    private String callType;
    private Boolean callStatus;
    private Integer callNumber;

    // EFFECTS: Creates a new call for service
    public CallForService(String address, String caller, String callType, Boolean callStatus, int callNumber) {
        this.callAddress = address;
        this.caller = caller;
        this.callType = callType;
        this.callStatus = callStatus;
        this.callNumber = callNumber;

    }


    // REQUIRES: A call exists
    // EFFECTS: Returns address of call
    public String getCallAddress() {
        return callAddress;
    }

    // REQUIRES: A call exists
    // EFFECTS: Returns the person who requested a call for service of the call
    public String getCaller() {
        return caller;
    }

    // REQUIRES: A call exists
    // EFFECTS: Returns the call type ie: Fire Trouble, Medical Assistance, Police Needed
    public String getCallType() {
        return callType;
    }

    // REQUIRES: A call exists
    // EFFECTS: Returns current status of call, true = complete, false = in-progress
    public Boolean getCallStatus() {
        return callStatus;
    }

    // REQUIRES: A call exists
    // EFFECTS: Returns unique case number of call
    public int getCallNum() {
        return callNumber;
    }

    // REQUIRES: A call exists
    // MODIFIES: this
    // EFFECTS: Completes the call by making the status false
    public void completeCall() {
        callStatus = true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Call Number", callNumber);
        json.put("Call Address", callAddress);
        json.put("Call Type", callType);
        json.put("Status", callStatus);
        json.put("Reporting Party", caller);
        return json;
    }


}
