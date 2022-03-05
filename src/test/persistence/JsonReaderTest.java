package persistence;

import model.AllCallsForService;
import model.AllResponders;
import model.CallForService;
import model.Responder;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Test class for reader, takes inspiration from the JSON example project
public class JsonReaderTest extends JsonTest {

    CallForService callOne = new CallForService("here", "billy", "police needed", false, 2);
    CallForService callTwo = new CallForService("there", "john", "police needed", false, 7);
    CallForService callThree = new CallForService("there", "john", "police needed", true, 10);

    @Test
    void testReaderNoSuchFile() {
        JsonReader reader = new JsonReader("./data/my/nonexistantfile");

        try {
            AllCallsForService allCalls = reader.read();
            fail("But where is IOException...");
        } catch (IOException e) {
            // beautiful, incredible.
        }
    }

    @Test
    void testReaderEmptyCallStack() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalls");

        try {
            AllCallsForService allCalls = new AllCallsForService();
            assertEquals(0, allCalls.getNumAllCalls());
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyCalls");
            writer.open();
            writer.write(allCalls);
            writer.close();
            allCalls = reader.read();
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }

    @Test
    void testReaderEmptyResponders() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyResponders");

        try {
            AllResponders allResponders = new AllResponders();
            assertEquals(0, allResponders.numberOfResponders());
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyResponders");
            writer.open();
            writer.writeResponders(allResponders);
            writer.close();
            allResponders = reader.readResponders();
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testReaderGeneralCallStack() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalls");
        try {
            CallForService callOne = new CallForService("here", "billy", "police needed", false, 2);
            CallForService callTwo = new CallForService("there", "john", "police needed", false, 7);
            CallForService callThree = new CallForService("there", "john", "police needed", true, 10);
            AllCallsForService allCalls = new AllCallsForService();

            allCalls.openCall(callOne);
            allCalls.openCall(callTwo);
            allCalls.openCall(callThree);

            allCalls = reader.read();

            assertEquals(3, allCalls.getNumAllCalls());
            assertEquals(2, allCalls.getNumOpenCalls());
            assertEquals(1, allCalls.getNumClosedCalls());


            List<CallForService> inProgress = allCalls.getInProgress();
            List<CallForService> completed = allCalls.getCompleted();

            assertEquals(2, inProgress.size());
            assertEquals(1, completed.size());

            checkCall(callOne, "here", "billy", "police needed", false, 2);
            checkCall(callTwo, "there", "john", "police needed", false, 7);
            checkCall(callThree, "there", "john", "police needed", true, 10);


        } catch (IOException e) {
            fail("Exception should not have been passed, this file should be found and contain data");
        }
    }

    @Test
    void testReaderGeneralResponders() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralResponders");

        try {
            Responder responderOne = new Responder("Fire", false, callOne, 5);
            Responder responderTwo = new Responder("Fire", false, callOne, 6);
            Responder responderThree = new Responder("Fire", false, callOne, 7);
            AllResponders allResponders = new AllResponders();

            allResponders.addResponder(responderOne);
            allResponders.addResponder(responderTwo);
            allResponders.addResponder(responderThree);

            allResponders = reader.readResponders();

            assertEquals(3, allResponders.numberOfResponders());

            checkResponder(responderOne, "Fire", false, callOne, 5);
            checkResponder(responderTwo, "Fire", false, callOne, 6);
            checkResponder(responderThree, "Fire", false, callOne, 7);

        } catch (IOException e) {
            fail("Exception should not have been thrown.");

        }
    }
}
