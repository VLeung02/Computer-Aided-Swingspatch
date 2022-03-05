//package ui;
//
//import model.AllCallsForService;
//import model.AllResponders;
//import model.CallForService;
//import model.Responder;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;
//
//// Computer Aided Dispatch application
//public class ComputerAidedDispatch {
//    private static final String DATA_CALLSTATS = "./data/callstats";
//    private static final String DATA_RESPONDERSTATS = "./data/shiftresponders";
//    private CallForService call;
//    private AllCallsForService allCalls;
//    private Responder responder;
//    private AllResponders allResponders;
//    private Scanner input;
//    private String available = "none";
//    int callNumber = -1;
//    private Boolean onShift = true;
//    private Boolean loggingIn = true;
//    private JsonWriter jsonWriter;
//    private JsonWriter jsonWriterResponder;
//    private JsonReader jsonReader;
//    private JsonReader jsonReaderResponder;
//
//    // EFFECTS: Starts the ComputerAidedDispatch application.
//    public ComputerAidedDispatch() throws FileNotFoundException {
//        jsonWriter = new JsonWriter(DATA_CALLSTATS);
//        jsonWriterResponder = new JsonWriter(DATA_RESPONDERSTATS);
//        jsonReader = new JsonReader(DATA_CALLSTATS);
//        jsonReaderResponder = new JsonReader(DATA_RESPONDERSTATS);
//        runComputerAidedDispatch();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Process
//    private void runComputerAidedDispatch() {
//        String key = null;
//        String entry = null;
//
//        start();
//        while (loggingIn) {
//            loginMenu();
//        }
//
//        while (onShift) {
//            dispatchMenu();
//            key = input.next();
//            key = key.toLowerCase();
//
//            if (key.equals("e")) {
//                onShift = false;
//            } else {
//                dispatchInput(key);
//            }
//        }
//
//        System.out.println("Please remember to restart your machine and cover your cough");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: interpret the dispatcher's input
//    private void dispatchInput(String key) {
//        if (key.equals("c")) {
//            newCall();
//        } else if (key.equals("k")) {
//            endCall();
//        } else if (key.equals("s")) {
//            seeCall();
//        } else if (key.equals("r")) {
//            createResponder();
//        } else if (key.equals("l")) {
//            displayAllCalls();
//        } else if (key.equals("a")) {
//            assignResponder();
//        } else if (key.equals("i")) {
//            saveAllCalls();
//        } else if (key.equals("o")) {
//            loadAllCalls();
//        } else if (key.equals("z")) {
//            listResponders();
//        } else {
//            System.out.println("Unknown patrol action, please try again");
//        }
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Starts the CAD
//    private void start() {
//        call = new CallForService(available, available, available, true, 0);
//        responder = new Responder("Police", true, call, 1);
//        allCalls = new AllCallsForService();
//        allResponders = new AllResponders();
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Shows the available dispatch functions.
//    private void dispatchMenu() {
//        System.out.println("\nPatrol actions:");
//        System.out.println("\tPress c to create a new call for service");
//        System.out.println("\tPress k to end a call for service");
//        System.out.println("\tPress s to see a call with a call number");
//        System.out.println("\tPress r to create a new patrolling responder");
//        System.out.println("\tPress z show all the on-duty responders");
//        System.out.println("\tPress a to assign a responder to a call");
//        System.out.println("\tPress l to see all the calls in the stack");
//        System.out.println("\tPress i to save your shift's calls");
//        System.out.println("\tPress o to load the last shift's calls");
//        System.out.println("\tPress e to end your shift and log out");
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Present login menu to dispatcher,
//    private Boolean loginMenu() {
//        System.out.println("\nWelcome to University Emergency Computer Aided Dispatch, please log in.");
//        System.out.println("\tLock screen while away with CTRL-L");
//        System.out.println("\tUsername:");
//
//        String entry = null;
//        entry = input.next();
//        entry = entry.toLowerCase();
//
//        if (entry.contains("dispatch") || entry.contains("university") || entry.contains("police")) {
//            System.out.println("\tPassword:");
//            entry = input.next();
//            entry = entry.toLowerCase();
//            if (entry.contains("password") || entry.contains("robust") || entry.contains("1234")) {
//                System.out.println("\tLogging you in...");
//                return loggingIn = false;
//            }
//        }
//        System.out.println("Login failed, please try again.");
//        return loggingIn = true;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Creates a new call
//    private void newCall() {
//        callNumber++;
//        System.out.println("\nCall address?");
//        String address = input.next();
//        System.out.println("\nWho is calling?");
//        String caller = input.next();
//        System.out.println("\nWho is needed?: One of: "
//                +
//                "Fire Trouble, "
//                +
//                "Medical Assistance, "
//                +
//                "Police Needed");
//        String callType = input.next();
//
//        CallForService call = new CallForService(address, caller, callType, false, callNumber);
//        allCalls.openCall(call);
//        System.out.println("\nAdded in-progress:");
//        System.out.println("\tCall Number: " + callNumber);
//        int all = allCalls.getNumAllCalls();
//        System.out.println("Number of all calls: " + all);
//        int open = allCalls.getNumOpenCalls();
//        System.out.println("Number of in-progress calls: " + open);
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Ends a call.
//    private void endCall() {
//        System.out.println("\nEnd call menu:");
//        System.out.println("\tPress b to return to the dispatch menu");
//        System.out.println("\tPress c to continuing to closing a call");
//
//        String key = null;
//        key = input.next();
//        key = key.toLowerCase();
//
//        if (key.equals("b")) {
//            dispatchMenu();
//        } else if (key.equals("c")) {
//            System.out.println("\nContinuing to end call...");
//
//            displayAllCalls();
//
//            System.out.println("\nPlease type the call number of the call that needs to be closed");
//            proceedClose();
//
//        } else {
//            System.out.println("Unknown patrol action, please try again");
//            endCall();
//        }
//        return;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Proceeds with closing a call
//    private void proceedClose() {
//        String close = null;
//        close = input.next();
//        close = close.toLowerCase();
//
//        if (!close.matches("[a-zA-Z]+")) {
//            if (allCalls.searchCallList(Integer.parseInt(close))) {
//                allCalls.closeCall(Integer.parseInt(close));
//                System.out.println("Call number: " + close + " has been closed");
//                return;
//            } else {
//                System.out.println("Call does not exist, reference list of call numbers");
//                endCall();
//            }
//            endCall();
//        }
//        System.out.println("Unknown patrol action, please try again");
//        endCall();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Show's call details
//    private void seeCall() {
//        System.out.println("\nSearching for a call with a call number");
//        System.out.println("\tPress b to return to the dispatch menu");
//        System.out.println("\tPress c to continuing searching for a call");
//        String see = null;
//        see = input.next();
//        see = see.toLowerCase();
//
//        if (see.equals("b")) {
//            return;
//        } else if (see.equals("c")) {
//            displayAllCalls();
//            System.out.println("\tSelect the call number for viewing");
//            proceedSee();
//        } else {
//            System.out.println("Unknown patrol action, please try again");
//            seeCall();
//        }
//        return;
//    }
//
//    // REQUIRES: input is an integer
//    // MODIFIES: this
//    // EFFECTS: Proceeds to viewing a call
//    private void proceedSee() {
//        String see = input.next();
//        if (!see.matches("[a-zA-Z]+")) {
//            CallForService thisCall = allCalls.getCall(Integer.parseInt(see));
//            if (thisCall == null) {
//                System.out.println("Call does not exist, please try again");
//                seeCall();
//                return;
//            }
//            Integer thisCallNumber;
//            thisCallNumber = thisCall.getCallNum();
//            Boolean status = thisCall.getCallStatus();
//            if (thisCallNumber == Integer.parseInt(see) && status) {
//                displayCompleteSingle(thisCall);
//                seeCall();
//            } else if (thisCallNumber == Integer.parseInt(see) && !status) {
//                displayInProgressSingle(thisCall);
//                seeCall();
//            } else {
//                System.out.println("Call does not exist, try again");
//                seeCall();
//            }
//        }
//        System.out.println("Returning to the dispatch menu...");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Prints a single in progress call for service
//    private void displayInProgressSingle(CallForService thisCall) {
//        System.out.println("Call Status: In-progress");
//        System.out.println("Call Number: " + thisCall.getCallNum());
//        System.out.println("Call Type: " + thisCall.getCallType());
//        System.out.println("Call Address: " + thisCall.getCallAddress());
//        System.out.println("Reporting party: " + thisCall.getCaller());
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Prints a single completed call for service
//    private void displayCompleteSingle(CallForService thisCall) {
//        System.out.println("Call Status: Completed call");
//        System.out.println("Call Number: " + thisCall.getCallNum());
//        System.out.println("Call Type: " + thisCall.getCallType());
//        System.out.println("Call Address: " + thisCall.getCallAddress());
//        System.out.println("Reporting party: " + thisCall.getCaller());
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Displays all the call numbers in a list
//    private void displayAllCalls() {
//        displayInProgress();
//        displayCompleted();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Display the in-progress calls only
//    private void displayInProgress() {
//        int callNum = -1;
//        List<CallForService> inProgress = allCalls.getInProgress();
//        System.out.println("\nAll In-Progress calls since the start of the shift: ");
//
//        for (CallForService c : inProgress) {
//            callNum++;
//            System.out.println("\nIn-Progress Call:");
//            System.out.println("\tCall Number: " + c.getCallNum());
//            System.out.println("\tCall Type: " + c.getCallType());
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Prints all the completed calls
//    private void displayCompleted() {
//        int callNum = -1;
//        List<CallForService> completedOnly = allCalls.getCompleted();
//        System.out.println("\nAll Completed calls since the start of the shift: ");
//
//        for (CallForService c : completedOnly) {
//            callNum++;
//            System.out.println("\nCompleted Call:");
//            System.out.println("\tCall Number: " + c.getCallNum());
//            System.out.println("\tCall Type: " + c.getCallType());
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Create new responder
//    private void createResponder() {
//        System.out.println("\nBegin a new responder's shift by entering their details...");
//        System.out.println("\tPress b to return to the dispatch menu");
//        System.out.println("\tPress c to continue creating a new responder");
//
//        String entry = null;
//        entry = input.next();
//        entry = entry.toLowerCase();
//
//        if (entry.equals("b")) {
//            dispatchMenu();
//        } else if (entry.equals("c")) {
//            continueCreating();
//        } else {
//            System.out.println("Unknown patrol action, please try again");
//            createResponder();
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Continues creating a new responder
//    private void continueCreating() {
//        int i = -1;
//
//        call = new CallForService("none", "none", "none", true, i);
//        System.out.println("Please enter the unit type: ");
//        String unitType = input.next();
//        System.out.println("Please enter the unit number:");
//        try {
//            Integer unitNum = Integer.parseInt(input.next());
//            Responder r = new Responder(unitType, true, call, unitNum);
//            allResponders.addResponder(r);
//            listResponders();
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid unit number, please use a number and try again");
//            createResponder();
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Lists all the responders
//    private void listResponders() {
//        System.out.println("--All on-duty responders--");
//        listAvailableResponders();
//        listUnavailableResponders();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Prints all the available responders
//    private void listAvailableResponders() {
//        List<Responder> allAvailable = allResponders.makeAvailableOnly();
//
//        System.out.println("\nAll responders currently available for calls: ");
//
//        for (Responder r : allAvailable) {
//            CallForService currentCall = r.getCurrentCall();
//            Integer callNum = currentCall.getCallNum();
//            System.out.println("\nUnit Type: " + r.getUnitType());
//            System.out.println("Available for calls?: " + r.getStatus());
//            System.out.println("Unit Number: " + r.getUnitNum());
//            System.out.println("Currently assigned to call number: " + callNum);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Prints all the unavailable responders
//    private void listUnavailableResponders() {
//        List<Responder> allUnavailable = allResponders.makeUnavailableOnly();
//
//        System.out.println("\nAll responders currently unavailable for calls: ");
//
//        for (Responder r : allUnavailable) {
//            CallForService currentCall = r.getCurrentCall();
//            Integer callNum = currentCall.getCallNum();
//            System.out.println("\nUnit Type: " + r.getUnitType());
//            System.out.println("Available for calls?: " + r.getStatus());
//            System.out.println("Unit Number: " + r.getUnitNum());
//            System.out.println("Currently assigned to call number: " + callNum + ", " + currentCall.getCallType());
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Assign responder to a call
//    private void assignResponder() {
//        continueAssigning(findAvailableResponder());
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Continues assigning a responder r
//    private void continueAssigning(Responder r) {
//        List<Integer> inProgressCallNumbers = allCalls.getCallNumbers();
//        System.out.println("Enter the call number of the call you want to assign to: " + r.getUnitNum()
//                +
//                ", "
//                +
//                r.getUnitType());
//        displayInProgress();
//
//        String call = input.next();
//        Integer callNum = Integer.parseInt(call);
//
//        for (int num : inProgressCallNumbers) {
//            if (num == callNum) {
//                r.makeUnAvailable();
//                r.assignCall(allCalls.getCall(callNum));
//                System.out.println("Assigned to: " + callNum + ", " + r.getCurrentCall().getCallType());
//                return;
//            }
//        }
//        System.out.println("Call not found, returning to the menu...");
//        return;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Finds an available responder with the scanner input
//    private Responder findAvailableResponder() {
//        List<Responder> allAvailable = allResponders.makeAvailableOnly();
//
//        System.out.println("Type the unit number of the available responder you want to find");
//        listAvailableResponders();
//
//        String unit = input.next();
//        Integer unitNum = Integer.parseInt(unit);
//
//        for (Responder r : allAvailable) {
//            if (r.getUnitNum() == unitNum) {
//                System.out.println("Current unit: " + r.getUnitNum() + ", " + r.getUnitType());
//                return r;
//            }
//            continue;
//        }
//        return null;
//    }
//
//    // EFFECTS: saves all calls to the callstats file
//    private void saveAllCalls() {
//        saveCallDetails();
//        saveResponderDetails();
//    }
//
//    // EFFECTS: Saves all the call details
//    private void saveCallDetails() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(allCalls);
//            jsonWriter.close();
//            System.out.println("Saved this shift's call details to " + DATA_CALLSTATS);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to save to " + DATA_CALLSTATS);
//        }
//    }
//
//
//    // EFFECTS: Saves all the responder details
//    private void saveResponderDetails() {
//        try {
//            jsonWriterResponder.open();
//            jsonWriterResponder.writeResponders(allResponders);
//            jsonWriterResponder.close();
//            System.out.println("Saved this shift's responders to " + DATA_RESPONDERSTATS);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to save to" + DATA_RESPONDERSTATS);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads previous shift call history and responders from file
//    private void loadAllCalls() {
//        loadAllCallDetails();
//        loadAllResponders();
//    }
//
//    // EFFECTS: loads all the call details from the previous shift
//    private void loadAllCallDetails() {
//        try {
//            allCalls = jsonReader.read();
//            System.out.println("Loaded last shift from" + DATA_CALLSTATS);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + DATA_CALLSTATS);
//        }
//    }
//
//
//    // EFFECTS: loads all the responder details from the previous shift
//    private void loadAllResponders() {
//        try {
//            allResponders = jsonReaderResponder.readResponders();
//            System.out.println("Loaded last shift's responders from " + DATA_RESPONDERSTATS);
//
//        } catch (IOException e) {
//            System.out.println("Unablve to read from " + DATA_RESPONDERSTATS);
//        }
//    }
//}
