package me.conmy.emu.chip8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Program implements ActionListener {

    JFrame frame;
    Chip8DebugFrame debugFrame;
    Chip8Panel chip8Panel;

    private boolean runApplication;

    public Program() {
        frame = new JFrame("Chip8 Emulator");
        runApplication = false;

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException |
                        IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                init();
            }
        });
    }

    private void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(gridLayout);

        addProgramMenu();
        addChip8DisplayPane();
        addDebugPanel();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addProgramMenu() {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("Load Application", this, "FILE_LOAD_APPLICATION"));

        JMenu runMenu = new JMenu("Run");
        runMenu.add(createMenuItem("Play/Pause", this, "RUN_PLAY_PAUSE"));
        runMenu.add(createMenuItem("Load Debug Application", this, "RUN_DEBUG"));
        runMenu.add(createMenuItem("Next Step", this, "RUN_NEXT_STEP"));

        JMenu viewMenu = new JMenu("View");
        viewMenu.add(createMenuItem("Show/Hide Program Counter", this, "VIEW_TOGGLE_PC"));
        viewMenu.add(createMenuItem("Show/Hide Register Values", this, "VIEW_TOGGLE_REG"));
        viewMenu.add(createMenuItem("Write Debug Messages", this, "VIEW_DEBUG"));

        menuBar.add(fileMenu);
        menuBar.add(runMenu);
        menuBar.add(viewMenu);

        frame.setJMenuBar(menuBar);
    }

    private void addChip8DisplayPane() {
        chip8Panel = new Chip8Panel();

        chip8Panel.addKeyListener(new Chip8KeyMapper(chip8Panel.getChip8()));

        chip8Panel.setFocusable(true);
        chip8Panel.requestFocusInWindow();
        frame.add(chip8Panel);
    }

    private void addDebugPanel() {
        debugFrame = new Chip8DebugFrame();
        debugFrame.createAndShowGUI();
        debugFrame.setVisible(true);
    }

    private JMenuItem createMenuItem(String label, ActionListener actionListener, String command) {
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText(label);
        menuItem.setActionCommand(command);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    private void loadChip8(byte[] applicationCode) {
        chip8Panel.getChip8().loadApplication(applicationCode);
        debugFrame.setByteCode(applicationCode);

        // TODO: Assert that chip8 is loaded with an application
        // This should toggle more menu options to be available
        // And that the program can be run.
    }

    private void doCycle() {
        chip8Panel.getChip8().emulateChipCycle(1000);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "FILE_LOAD_APPLICATION":
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "/git/personal/chip8/src/test/resources/c8games"));
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = fileChooser.getSelectedFile();
                    // TODO: Validate file here
                    // Would validation include checking the contents??
                    String path = chosenFile.getPath();
                    //if (path.matches(".+\\.c8$")) {
                        try {
                            byte[] application = Files.readAllBytes(chosenFile.toPath());
                            loadChip8(application);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    //}
                    // TODO: ELSE: file didn't pass validation - Return feedback to the user.
                }
                break;
            case "RUN_DEBUG":
                byte[] testFile = new byte[]{
                        0x62, 0x00, // Set V(2) to 0x00 (0)
                        0x61, 0x0e, // Set V(1) to 0x01 (1)
                        (byte) 0xf1, 0x29, // Set I to address of sprite start for value in V(1)
                        (byte) 0xd2, 0x35, // draw I reg sprite to height of 5 at coords V(2) V(3)
                        0x62, 0x05, // Set V(2) to 0x05 (5)
                        0x61, 0x0d, // Set V(1) to 0x0a (10)
                        (byte) 0xf1, 0x29, // Set I to address of sprite start for value in V(1)
                        (byte) 0xd2, 0x35, // draw I reg sprite to height of 5 at coords V(2) V(3)
                        0x62, 0x0a, // Set V(2) to 0x0a (10)
                        0x61, 0x0c, // Set V(1) to 0x03 (3)
                        (byte) 0xf1, 0x29, // Set I to address of sprite start for value in V(1)
                        (byte) 0xd2, 0x35, // draw I reg sprite to height of 5 at coords V(2) V(3)
                        0x62, 0x0f, // Set V(2) to 0x0a (10)
                        0x61, 0x0b, // Set V(1) to 0x03 (3)
                        (byte) 0xf1, 0x29, // Set I to address of sprite start for value in V(1)
                        (byte) 0xd2, 0x35, // draw I reg sprite to height of 5 at coords V(2) V(3)
                        0x62, 0x15, // Set V(2) to 0x0a (10)
                        0x61, 0x0a, // Set V(1) to 0x03 (3)
                        (byte) 0xf1, 0x29, // Set I to address of sprite start for value in V(1)
                        (byte) 0xd2, 0x35, // draw I reg sprite to height of 5 at coords V(2) V(3)
                        0x62, 0x1a, // Set V(2) to 0x0f (15)
                        0x61, 0x06, // Set V(1) to 0x01 (1)
                        (byte) 0xf1, 0x29, // Set I to address of sprite start for value in V(1)
                        (byte) 0xd2, 0x35 // draw I reg sprite to height of 5 at coords V(2) V(3)
                };
                loadChip8(testFile);
                break;
            case "RUN_NEXT_STEP":
                doCycle();
                break;
            case "RUN_PLAY_PAUSE":
                if (!chip8Panel.isStarted()) {
                    chip8Panel.startChipCycle();
                } else if (chip8Panel.isRunning()) {
                    chip8Panel.stopChipCycle();
                } else {
                    chip8Panel.restartChipCycle();
                }
                break;
            case "RUN_PAUSE":
                chip8Panel.stopChipCycle();
                break;
            case "VIEW_TOGGLE_PC":

                break;
            case "VIEW_DEBUG":
                Chip8 chip8 = chip8Panel.getChip8();
                chip8.setDebug(!chip8.isDebug());
                break;
        }
    }

    public static void main(String[] args) {
        new Program();
    }
}