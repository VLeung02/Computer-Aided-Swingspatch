package ui;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

// Represents the audio of the radio channel being held for an emergency, inspired by the AlarmController 210 lecture
public class ComputerAidedDispatchAudio {
    private Clip line;
    private Clip dispatched;
    private Clip siren;
    private Clip created;

    // EFFECTS: starts the audio methods
    public ComputerAidedDispatchAudio() {
        emergencyBeep();
        dispatchBeep();
        responderBeep();
        callCreationBeep();
    }

    // MODIFIES: this
    // EFFECTS: gets the audio file directory and plays it
    public void emergencyBeep() {
        try {
            String sep = System.getProperty("file.separator");
            File soundFile = new File(System.getProperty("user.dir") + sep
                    + "resources" + sep + "channel.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = sound.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                line = null;
            } else {
                line = (Clip) AudioSystem.getLine(info);
                //line.setLoopPoints(0, -1);
                line.open(sound);
            }
        } catch (Exception ex) {
            line = null;
        }
    }

    // MODIFIES: this
    // EFFECTS: gets the audio file directory and plays it
    public void dispatchBeep() {
        try {
            String sep = System.getProperty("file.separator");
            File soundFile = new File(System.getProperty("user.dir") + sep
                    + "resources" + sep + "dispatchbeep.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = sound.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                dispatched = null;
            } else {
                dispatched = (Clip) AudioSystem.getLine(info);
                //line.setLoopPoints(0, -1);
                dispatched.open(sound);
            }
        } catch (Exception ex) {
            dispatched = null;
        }
    }

    // MODIFIES: this
    // EFFECTS: gets the audio file directory and plays it
    public void callCreationBeep() {
        try {
            String sep = System.getProperty("file.separator");
            File soundFile = new File(System.getProperty("user.dir") + sep
                    + "resources" + sep + "newcallassigned.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = sound.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                created = null;
            } else {
                created = (Clip) AudioSystem.getLine(info);
                created.open(sound);
            }
        } catch (Exception ex) {
            created = null;
        }
    }

    // MODIFIES: this
    // EFFECTS: gets the audio file directory and plays it
    public void responderBeep() {
        try {
            String sep = System.getProperty("file.separator");
            File soundFile = new File(System.getProperty("user.dir") + sep
                    + "resources" + sep + "newresponder.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = sound.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                siren = null;
            } else {
                siren = (Clip) AudioSystem.getLine(info);
                //line.setLoopPoints(0, -1);
                siren.open(sound);
            }
        } catch (Exception ex) {
            siren = null;
        }
    }

    // REQUIRES: line != null
    // EFFECTS: Plays channel held tone
    public void channelTone(boolean channelHeld) {
        if (line != null) {
            if (channelHeld) {
                line.setFramePosition(2000);
                line.loop(20);
            } else {
                line.stop();
            }
        }
    }

    // REQUIRES: line != null
    // EFFECTS: Plays new call created tone
    public void callCreatedTone() {
        if (created != null) {
            if (true) {
                created.setFramePosition(2000);
                created.loop(0);
            } else {
                created.stop();
            }
        }
    }


    // REQUIRES: dispatched != null
    // EFFECTS: Plays the dispatch beep on new call assignment
    public void newCallAssigned() {
        if (dispatched != null) {
            if (true) {
                dispatched.setFramePosition(0);
                dispatched.loop(0);
            } else {
                dispatched.stop();
            }
        }
    }


    // REQUIRES: siren != null
    // EFFECTS: plays the siren sound
    public void siren() {
        if (siren != null) {
            if (true) {
                siren.setFramePosition(5500);
                siren.loop(0);
            } else {
                siren.stop();
            }
        }
    }

}
