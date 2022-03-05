package ui;

import javax.swing.*;

// Represents the small splash screen before the CAD starts
public class DispatchSplashGUI extends JFrame {
    private ImageIcon dispatchSplash;
    private static final int CTRX = 800;
    private static final int CTRY = 500;

    // https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
    // EFFECTS: sets up the splash screen
    public DispatchSplashGUI() {
        setup();
        JWindow window = new JWindow();
        window.getContentPane().add(
                new JLabel("", dispatchSplash, SwingConstants.CENTER));
        window.setBounds(CTRX, CTRY, 800, 400);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }

    // MODIFIES: this
    // EFFECTS: Gets the dispatch splash png from the directory
    public void setup() { // inspiration from TrafficLightGUI CPSC210 Lecture file
        String sep = System.getProperty("file.separator");
        dispatchSplash = new ImageIcon(System.getProperty("user.dir") + sep
                + "resources" + sep + "dispatchsplash.png");
    }

}
