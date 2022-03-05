package persistence;

import model.AllCallsForService;
import model.AllResponders;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Writes to the callstats and shift responders JSON data files, takes inspiration from the JSON example project
public class JsonWriter {
    private PrintWriter writer;
    private String destination;
    private static final int INDENT = 4;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of AllCallsForService to file
    public void write(AllCallsForService c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of AllResponders to file
    public void writeResponders(AllResponders c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}