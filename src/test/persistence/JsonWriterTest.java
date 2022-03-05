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

// Test class for JsonWriter, takes inspiration from the JSON example project
public class JsonWriterTest extends JsonTest {

    @Test
    void testInvalidFileDest() {
        try {
            AllCallsForService allCalls = new AllCallsForService();
            JsonWriter writer = new JsonWriter("./data/my/doesntexist.json");
            writer.open();
            fail("IOExceptino expected");
        } catch (IOException e) {
            // exception caught! as expected...
        }
    }


    @Test
    void testEmptyCalls() {
        try {
            AllCallsForService allCalls = new AllCallsForService();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCalls");
            writer.open();
            writer.write(allCalls);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalls");
            allCalls = reader.read();
            assertEquals(0, allCalls.getNumAllCalls());
            assertEquals(0, allCalls.getNumOpenCalls());
            assertEquals(0, allCalls.getNumClosedCalls());

        } catch (IOException e) {
            fail("Exception should not have been thrown here");
        }
    }

    @Test
    void testEmptyResponders() {
        try {
            AllResponders allResponders = new AllResponders();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyResponders");
            writer.open();
            writer.writeResponders(allResponders);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyResponders");
            allResponders = reader.readResponders();
            assertEquals(0, allResponders.numberOfResponders());

        } catch (IOException e) {
            fail("Exception should not have been thrown here");
        }
    }

    @Test
    void testGeneralResponders() {
        try {
            CallForService callOne = new CallForService("here", "billy", "police needed", false, 2);
            Responder responderOne = new Responder("Fire", true, callOne, 5);
            Responder responderTwo = new Responder("Fire", false, callOne, 6);
            Responder responderThree = new Responder("Fire", false, callOne, 7);

            AllResponders allResponders = new AllResponders();

            allResponders.addResponder(responderOne);
            allResponders.addResponder(responderTwo);
            allResponders.addResponder(responderThree);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralResponders");
            writer.open();
            writer.writeResponders(allResponders);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralResponders");

            allResponders = reader.readResponders();

            assertEquals(3, allResponders.numberOfResponders());

            List<Responder> available = allResponders.makeAvailableOnly();
            List<Responder> unavailable = allResponders.makeUnavailableOnly();

            assertEquals(1, available.size());
            assertEquals(2, unavailable.size());

        } catch (IOException e) {
            fail("Did not expect to catch the IOException");
        }
    }

    @Test
    void testGeneralCalls() {
        try {
            AllCallsForService allCalls = new AllCallsForService();
            CallForService callOne = new CallForService("here", "billy", "police needed", false, 2);
            CallForService callTwo = new CallForService("there", "john", "police needed", false, 7);
            CallForService callThree = new CallForService("there", "john", "police needed", true, 10);

            allCalls.openCall(callOne);
            allCalls.openCall(callTwo);
            allCalls.openCall(callThree);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCalls");
            writer.open();
            writer.write(allCalls);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCalls");
            allCalls = reader.read();

            assertEquals(3, allCalls.getNumAllCalls());

            List<CallForService> inProgress = allCalls.getInProgress();
            List<CallForService> completed = allCalls.getCompleted();

            assertEquals(2, inProgress.size());
            assertEquals(1, completed.size());

            checkCall(callOne, "here", "billy", "police needed", false, 2);
            checkCall(callTwo, "there", "john", "police needed", false, 7);
            checkCall(callThree, "there", "john", "police needed", true, 10);
        } catch (IOException e) {
            fail("No exception here should have been thrown... Goofball");
        }

    }
}
