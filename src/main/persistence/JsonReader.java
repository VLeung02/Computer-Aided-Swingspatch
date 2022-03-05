package persistence;

import model.AllCallsForService;
import model.AllResponders;
import model.CallForService;
import model.Responder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads callstats and shiftresponders from JSON data stored in file
// Takes inspiration from the JSON example project
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source files
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads callstats from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AllCallsForService read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShiftDetails(jsonObject);
    }

    // EFFECTS: reads shiftresponders from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AllResponders readResponders() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShiftResponders(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AllCallsForService from JSON object and returns it
    private AllCallsForService parseShiftDetails(JSONObject jsonObject) {
        AllCallsForService c = new AllCallsForService();
        shiftCalls(c, jsonObject);
        return c;
    }

    // EFFECTS: parses AllResponders from JSON object and returns it
    private AllResponders parseShiftResponders(JSONObject jsonObject) {
        AllResponders r = new AllResponders();
        shiftResponders(r, jsonObject);
        return r;
    }

    // MODIFIES: c
    // EFFECTS: parses all calls for service from JSON object and adds them to all calls for service
    private void shiftCalls(AllCallsForService c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Calls For Service: ");
        for (Object json : jsonArray) {
            JSONObject nextCall = (JSONObject) json;
            addCall(c, nextCall);
        }
    }

    // MODIFIES: r
    // EFFECTS: parses all responders from JSON object and adds them to all calls for service
    private void shiftResponders(AllResponders r, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("All Responders: ");
        for (Object json : jsonArray) {
            JSONObject nextResponder = (JSONObject) json;
            addResponder(r, nextResponder);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses call for service from JSON object and adds it to All Calls For Service
    private void addCall(AllCallsForService c, JSONObject jsonObject) {
        String address = jsonObject.getString("Call Address");
        String caller = jsonObject.getString("Reporting Party");
        String callType = jsonObject.getString("Call Type");
        Boolean status = jsonObject.getBoolean("Status");
        Integer callNum = jsonObject.getInt("Call Number");
        CallForService call = new CallForService(address, caller, callType, status, callNum);
        c.openCall(call);
    }

    // MODIFIES: r
    // EFFECTS: parses responder from JSON object and adds it to All Responders
    private void addResponder(AllResponders r, JSONObject jsonObject) {
        String address = jsonObject.getString("Call Address");
        String caller = jsonObject.getString("Reporting Party");
        String callType = jsonObject.getString("Call Type");
        Boolean callStatus = jsonObject.getBoolean("Status");
        Integer callNum = jsonObject.getInt("Call Number");

        String type = jsonObject.getString("Unit Type");
        Boolean status = jsonObject.getBoolean("Unit Status");
        Integer unitNumber = jsonObject.getInt("Unit Number");
        CallForService call = new CallForService(address, caller, callType, callStatus, callNum);
        Responder t = new Responder(type, status, call, unitNumber);
        r.addResponder(t);
    }
}