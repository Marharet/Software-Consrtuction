package ua.edu.tntu._121_se.midipiano;

import ua.edu.tntu._121_se.midipiano.piano.PianoMachine;
import ua.edu.tntu._121_se.midipiano.ui.PianoApp;

public class Main {
    public static void main(String[] args) {
        PianoMachine piano = new PianoMachine();
        PianoApp app = new PianoApp(piano);
        app.setVisible(true);
    }
}