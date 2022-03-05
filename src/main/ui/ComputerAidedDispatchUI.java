package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

// Represents main Computer Aided Dispatch, dispatch panel, strong inspiration from the 210 Alarm Controller project
public class ComputerAidedDispatchUI extends JFrame implements ActionListener {
    private static final int WIDTH = 2500;
    private static final int HEIGHT = 1000;
    protected AllCallsForService allCalls;
    protected AllResponders allResponders;
    private JDesktopPane desktop;
    private String timeStamp;
    private JTextField address;
    private JTextField caller;
    private JTextField callType;
    private JLabel addressLabel;
    private JLabel callerLabel;
    private JLabel callTypeLabel;
    private String callAddress;
    private String callReporter;
    private String callCallType;
    private JTextField responderType;
    private JTextField responderNum;
    private String responderResponderType;
    private int callNumber;
    private ComputerAidedDispatchAudio channel;
    protected JMenuBar menuBar;
    private JMenuItem loadItem;
    private JMenuItem saveItem;
    private JsonWriter jsonWriter;
    private JsonWriter jsonWriterResponder;
    private JsonReader jsonReader;
    private JsonReader jsonReaderResponder;
    private static final String DATA_CALLSTATS = "./data/callstats";
    private static final String DATA_RESPONDERSTATS = "./data/shiftresponders";
    private Font font1;

    // EFFECTS: starts the CADUI
    public ComputerAidedDispatchUI() {
        initialize();
        desktop = new JDesktopPane();
        login();
        setContentPane(desktop);
        setTitle("University Police SMORTCAD2.0");
        setSize(WIDTH, HEIGHT);
        setupButtons();
        setJMenuBar(menuBar);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        // https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes the CAD with all the default settings
    public void initialize() {
        DispatchSplashGUI splash = new DispatchSplashGUI();
        jsonWriter = new JsonWriter(DATA_CALLSTATS);
        jsonWriterResponder = new JsonWriter(DATA_RESPONDERSTATS);
        jsonReader = new JsonReader(DATA_CALLSTATS);
        jsonReaderResponder = new JsonReader(DATA_RESPONDERSTATS);
        channel = new ComputerAidedDispatchAudio();
        allCalls = new AllCallsForService();
        allResponders = new AllResponders();
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        font1 = new Font("SansSerif", Font.BOLD, 20);
    }

    // MODIFIES: this
    // EFFECTS: sets up button panel
    public void setupButtons() {
        addButtonPanel();
        addCallPanel();
        listCallPanel();
        addResponderPanel();
        listResponderPanel();
        menuBar();
    }

    // MODIFIES: this
    // EFFECTS: adds top menu bar that allows for load and saving
    public void menuBar() {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu reportMenu = new JMenu("Report a problem");

        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        JMenuItem reportItem = new JMenuItem("Report a problem");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        reportItem.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        reportMenu.add(reportItem);

        menuBar.add(fileMenu);
        menuBar.add(reportMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadItem) {
            loadAllCalls();
        } else if (e.getSource() == saveItem) {
            saveAllCalls();
        }
    }

    // EFFECTS: saves all calls to the callstats file
    public void saveAllCalls() {
        saveCallDetails();
        saveResponderDetails();
    }

    // EFFECTS: Saves all the call details
    public void saveCallDetails() {
        try {
            jsonWriter.open();
            jsonWriter.write(allCalls);
            jsonWriter.close();
            System.out.println("Saved this shift's call details to " + DATA_CALLSTATS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to " + DATA_CALLSTATS);
        }
    }


    // EFFECTS: Saves all the responder details
    private void saveResponderDetails() {
        try {
            jsonWriterResponder.open();
            jsonWriterResponder.writeResponders(allResponders);
            jsonWriterResponder.close();
            System.out.println("Saved this shift's responders to " + DATA_RESPONDERSTATS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to" + DATA_RESPONDERSTATS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads previous shift call history and responders from file
    public void loadAllCalls() {
        loadAllCallDetails();
        loadAllResponders();
    }

    // EFFECTS: loads all the call details from the previous shift
    private void loadAllCallDetails() {
        try {
            allCalls = jsonReader.read();
            System.out.println("Loaded last shift from" + DATA_CALLSTATS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + DATA_CALLSTATS);
        }
    }


    // EFFECTS: loads all the responder details from the previous shift
    private void loadAllResponders() {
        try {
            allResponders = jsonReaderResponder.readResponders();
            System.out.println("Loaded last shift's responders from " + DATA_RESPONDERSTATS);

        } catch (IOException e) {
            System.out.println("Unable to read from " + DATA_RESPONDERSTATS);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts a user to login, return true if successful, return false if unsucessful
    public Boolean login() {
        String username = JOptionPane.showInputDialog("Please log in, username:");
        if (username.contains("university") || username.contains("police")) {
            String password = JOptionPane.showInputDialog("Password?");
            if (password.contains("robust") || password.contains("password")) {
                System.out.println("Success!");
                return true;
            } else {
                System.out.println("Login failed, try again");
                login();
            }
        } else {
            System.out.println("Login failed, try again");
            login();
        }
        return false;
    }


    // MODIFIES: this
    // EFFECTS: adds a top level button panel
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(0, 0, WIDTH, 100);
        buttonPanel.add(new JButton(new SignalOneHundred()));
        buttonPanel.add(new JButton(new LockScreen()));
        buttonPanel.setBackground(new java.awt.Color(255, 165, 0));
        desktop.add(buttonPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds a call creation panel
    public void addCallPanel() {
        JPanel callPanel = new JPanel();
        createPanel();
        callPanel.setBounds(0, 100, 250, 500);
        createText();
        callPanel.add(addressLabel);
        callPanel.add(address, BorderLayout.CENTER);
        callPanel.add(callerLabel);
        callPanel.add(caller, BorderLayout.CENTER);
        callPanel.add(callTypeLabel);
        callPanel.add(callType, BorderLayout.CENTER);
        callPanel.add(new JButton(new SubmitCallDetails()));
        callPanel.add(new JButton(new CloseCall()));
        callPanel.add(new JButton(new UpdateList()));
        desktop.add(callPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates the call creation panel
    public void createPanel() {
        addressLabel = new JLabel();
        callerLabel = new JLabel();
        callTypeLabel = new JLabel();
        addressLabel.setText("Address: ");
        addressLabel.setForeground(Color.blue);
        callerLabel.setText("Caller: ");
        callerLabel.setForeground(Color.blue);
        callTypeLabel.setText("Call Type: ");
        callTypeLabel.setForeground(Color.blue);
    }

    // MODIFIES: this
    // EFFECTS: creates the text fields
    public void createText() {
        address = new JTextField();
        caller = new JTextField();
        callType = new JTextField();
        address.setFont(font1);
        caller.setFont(font1);
        callType.setFont(font1);
        address.setPreferredSize(new Dimension(250, 50));
        caller.setPreferredSize(new Dimension(250, 50));
        callType.setPreferredSize(new Dimension(250, 50));
    }

    // MODIFIES: this
    // EFFECTS: lists all the calls in a panel
    public void listCallPanel() {
        JPanel listCallPanel = new JPanel(new GridLayout(20, 5));
        listCallPanel.setBounds(250, 100, 700, 700);
        List<Integer> callNums = allCalls.getCallNumbers();
        for (int i : callNums) {
            CallForService thisCall = allCalls.getCall(i);
            String callStatus;
            if (thisCall.getCallStatus()) {
                callStatus = "Completed";
            } else {
                callStatus = "In-progress";
            }
            callAddress = thisCall.getCallAddress();
            callReporter = thisCall.getCaller();
            callCallType = thisCall.getCallType();
            callNumber = thisCall.getCallNum();
            String cn = Integer.toString(callNumber);
            JLabel callInformationLabel = new JLabel("Call Number: " + cn + " | Call Address: " + callAddress
                    + " | Reporting Party: " + callReporter
                    + " | Call type: " + callCallType
                    + " | Call status: " + callStatus);
            listCallPanel.add(callInformationLabel);
        }
        desktop.add(listCallPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates responder textfields
    public void createResponderText() {
        responderType = new JTextField();
        responderNum = new JTextField();
        responderType.setFont(font1);
        responderNum.setFont(font1);
        responderType.setPreferredSize(new Dimension(250, 50));
        responderNum.setPreferredSize(new Dimension(250, 50));
    }

    // MODIFIES: this
    // EFFECTS: creates panel to add responders
    public void addResponderPanel() {
        JPanel responderPanel = new JPanel();
        JLabel responderTypeLabel = new JLabel();
        JLabel responderUnitNumberLabel = new JLabel();
        responderTypeLabel.setText("Unit type: ");
        responderTypeLabel.setHorizontalTextPosition(JLabel.LEFT);
        responderTypeLabel.setForeground(Color.red);
        responderUnitNumberLabel.setText("Unit number: ");
        responderUnitNumberLabel.setHorizontalTextPosition(JLabel.LEFT);
        responderUnitNumberLabel.setForeground(Color.red);
        responderPanel.setBounds(950, 100, 250, 300);
        createResponderText();
        responderPanel.add(responderTypeLabel);
        responderPanel.add(responderType, BorderLayout.CENTER);
        responderPanel.add(responderUnitNumberLabel);
        responderPanel.add(responderNum, BorderLayout.CENTER);
        responderPanel.add(new JButton(new CreateNewResponder()));
        responderPanel.add(new JButton(new AssignToCall()));
        responderPanel.add(new JButton(new UpdateList()));
        desktop.add(responderPanel, BorderLayout.WEST);

    }

    // MODIFIES: this
    // EFFECTS: panel that lists all responders
    public void listResponderPanel() {
        JPanel listResponderPanel = new JPanel(new GridLayout(20, 5));
        listResponderPanel.setBounds(1200, 100, 700, 700);
        List<Integer> unitNumbers = allResponders.getAllUnitNumbers();
        for (int i : unitNumbers) {
            Responder thisResponder = allResponders.getResponder(i);
            String responderStatus;
            if (thisResponder.getStatus()) {
                responderStatus = "In-service";
            } else {
                responderStatus = "Out-of-service";
            }
            responderResponderType = thisResponder.getUnitType();
            int responderResponderNum = thisResponder.getUnitNum();
            callCallType = thisResponder.getCurrentCall().getCallType();
            callNumber = thisResponder.getCurrentCall().getCallNum();
            JLabel callInformationLabel = new JLabel(
                    "Unit Type: " + responderResponderType + " | Unit Number: " + responderResponderNum
                            + " | Currently assigned to: " + callNumber
                            + " | Call type: " + callCallType
                            + " | Current status: " + responderStatus);
            listResponderPanel.add(callInformationLabel);
        }
        desktop.add(listResponderPanel, BorderLayout.WEST);
    }

    // Represents action taken when lock screen button is pressed
    private class LockScreen extends AbstractAction {

        LockScreen() {
            super("Lock the panel");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            desktop.setVisible(false);
            if (login()) {
                desktop.setVisible(true);
            }
        }

    }

    // Represents creating a new responder action
    private class CreateNewResponder extends AbstractAction {

        CreateNewResponder() {
            super("Create new responder");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            responderResponderType = responderType.getText();
            String number = responderNum.getText();
            Integer cn = Integer.valueOf(number);
            CallForService call = new CallForService("none", "none", "none", true, -1);
            Responder r = new Responder(responderResponderType, true, call, cn);
            allResponders.addResponder(r);

            listResponderPanel();
        }

    }


    // Represents an assign to call action
    private class AssignToCall extends AbstractAction {

        AssignToCall() {
            super("Assign responder to a new call");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String callSelection = JOptionPane.showInputDialog(null,
                    "Input the call number of the call to be assigned",
                    "New Call Assignment", JOptionPane.INFORMATION_MESSAGE);

            String unitNumber = JOptionPane.showInputDialog(null,
                    "Select the unit number of the responder",
                    "New Call Assignment", JOptionPane.INFORMATION_MESSAGE);

            int callNum = Integer.valueOf(callSelection);
            int unitNum = Integer.valueOf(unitNumber);

            CallForService assignment = allCalls.getCall(callNum);
            Responder toAssign = allResponders.getResponder(unitNum);

            toAssign.assignCall(assignment);
            toAssign.makeUnAvailable();
            System.out.println(toAssign.getStatus());
            System.out.println(toAssign.getUnitType() + " assigned to " + assignment.getCallAddress());
            channel.newCallAssigned();
        }
    }


    // Represents an emergency action button
    private class SignalOneHundred extends AbstractAction {
        private Boolean channelHeld = false;

        SignalOneHundred() {
            super("SIGNAL 100");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            channelHeld = !channelHeld;

            if (channelHeld) {
                channel.channelTone(true);
                System.out.println("Channel held at " + timeStamp);
                JOptionPane.showMessageDialog(null,
                        "Channel held at: "
                                +
                                timeStamp
                                +
                                " hold all but emergency traffic", "Emergency",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                channel.channelTone(false);
                System.out.println("Channel clear at " + timeStamp);
                JOptionPane.showMessageDialog(null,
                        "Channel clear at "
                                +
                                timeStamp + " resume normal radio traffic",
                        "Emergency clear", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Represents a call submission from the text fields
    private class SubmitCallDetails extends AbstractAction {

        SubmitCallDetails() {
            super("Create call");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int callNum = allCalls.getNumAllCalls();

            callAddress = address.getText();
            callReporter = caller.getText();
            callCallType = callType.getText();

            CallForService call = new CallForService(callAddress, callReporter, callCallType, false, callNum);
            allCalls.openCall(call);

            callNum++;
            listCallPanel();
            channel.callCreationBeep();

        }
    }

    // Represents a refresh button that updates the lists
    private class UpdateList extends AbstractAction {

        UpdateList() {
            super("Refresh List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            listCallPanel();
            listResponderPanel();
        }
    }

    // Represents the close call function
    private class CloseCall extends AbstractAction {

        CloseCall() {
            super("Close a call");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String callNumClose = JOptionPane.showInputDialog(null,
                    "Input the call number you would like to close", "Close Call", JOptionPane.INFORMATION_MESSAGE);

            int callNumToClose = Integer.parseInt(callNumClose);
            allCalls.closeCall(callNumToClose);
            allResponders.backInService();
        }

    }

    // EFFECTS: runs the CAD
    public static void main(String[] args) {
        new ComputerAidedDispatchUI();
    }
}
