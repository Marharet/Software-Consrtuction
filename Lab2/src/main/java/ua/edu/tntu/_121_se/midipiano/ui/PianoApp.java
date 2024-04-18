package ua.edu.tntu._121_se.midipiano.ui;

import ua.edu.tntu._121_se.midipiano.midi.Instrument;
import ua.edu.tntu._121_se.midipiano.ui.pianokeys.PianoKeysUI;
import ua.edu.tntu._121_se.midipiano.piano.PianoKey;
import ua.edu.tntu._121_se.midipiano.piano.PianoMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class PianoApp extends JFrame {
    private final int KEY_SHIFT_PITCH_DOWN = KeyEvent.VK_C;
    private final int KEY_SHIFT_PITCH_UP = KeyEvent.VK_V;
    private final int KEY_REVERB = KeyEvent.VK_SPACE;
    private final int KEY_SWITCH_INSTRUMENT = KeyEvent.VK_I;
    private final int KEY_RECORD = KeyEvent.VK_R;
    private final int KEY_PLAY = KeyEvent.VK_P;

    private final PianoKeyLayout pianoKeyLayout = PianoKeyLayouts.NumberRowLayout;
    private final HashSet<Integer> keysPressed = new HashSet<>();
    private final PianoMachine piano;
    private final PianoKeysUI keysUI;
    private JLabel topRightLabel;
    private static long playbackTime = 0L;
    private static long playbackTimeEnd = 0L;


    private boolean reverbEnabled = false;

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }


    public PianoApp(PianoMachine piano) {
        this.piano = piano;

        setTitle("Midi Piano");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(0, 1));

        topRightLabel = new JLabel("Current instrument: " + piano.getInstrument().toString());
        topRightLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        topPanel.add(topRightLabel);

        JLabel istruct = new JLabel("<html><u>Instructions:</u></html>");
        istruct.setFont(new Font("Courier New", Font.BOLD, 13));
        topPanel.add(istruct);

        JLabel newLabelCI = new JLabel("I -> to change instrument");
        newLabelCI.setFont(new Font("Courier New", Font.BOLD, 13));
        topPanel.add(newLabelCI);

        JLabel newLabelSO = new JLabel("C or V -> switch octaves up or down");
        newLabelSO.setFont(new Font("Courier New", Font.BOLD, 13));
        topPanel.add(newLabelSO);

        JLabel newLabelR = new JLabel("R -> start/stop recording");
        newLabelR.setFont(new Font("Courier New", Font.BOLD, 13));
        topPanel.add(newLabelR);

        JLabel newLabelP = new JLabel("P -> playback");
        newLabelP.setFont(new Font("Courier New", Font.BOLD, 13));
        topPanel.add(newLabelP);


        add(topPanel, BorderLayout.NORTH);


        JPanel southPanel = new JPanel(new FlowLayout());
        keysUI = new PianoKeysUI(pianoKeyLayout);
        southPanel.add(keysUI);
        add(southPanel, BorderLayout.SOUTH);
        setSize(southPanel.getPreferredSize().width, 400);

        setUpKeyListeners();
    }

    public void beginNote(PianoKey note) {
        piano.beginNote(note.getPitch());
        keysUI.pressKey(note);
    }

    public void endNote(PianoKey note) {
        piano.endNote(note.getPitch());
        keysUI.releaseKey(note);
    }

    public void enableReverb() {
        reverbEnabled = true;
    }

    public void switchInstrument() {
        piano.changeInstrument();
        topRightLabel.setText("Current instrument: " + piano.getInstrument().toString());
    }

    public void disableReverb() {
        reverbEnabled = false;
    }

    public void shiftScaleUp() {
        piano.shiftUp();
    }

    public void startRecording() {
        piano.toggleRecording();
    }

    public void playback() throws InterruptedException {
        piano.playback();
    }

    public void shiftScaleDown() {
        piano.shiftDown();
    }

    private void setUpKeyListeners() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getWhen() > playbackTime && e.getWhen() < playbackTimeEnd) return;
                int keyCode = e.getKeyCode();

                // Apply reverb
                if (keyCode == KEY_REVERB && !reverbEnabled) {
                    enableReverb();
                }

                // Check if key is already pressed
                if (keysPressed.contains(keyCode)) {
                    return;
                }
                keysPressed.add(keyCode);

                PianoKey note = pianoKeyLayout.getPianoKey(keyCode);
                if (note != null) {
                    beginNote(note);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getWhen() > playbackTime && e.getWhen() < playbackTimeEnd) return;

                int keyCode = e.getKeyCode();

                keysPressed.remove(keyCode);

                if (keyCode == KEY_SHIFT_PITCH_DOWN) {  // Shift octave down
                    shiftScaleDown();
                } else if (keyCode == KEY_SHIFT_PITCH_UP) { // Shift octave up
                    shiftScaleUp();
                } else if (keyCode == KEY_SWITCH_INSTRUMENT) {
                    switchInstrument();
                }
                else if (keyCode == KEY_REVERB && reverbEnabled) { // Release reverb
                    disableReverb();
                }
                else if (keyCode == KEY_RECORD) { // Start recording
                    startRecording();
                }
                else if (keyCode == KEY_PLAY) { // Playback
                    playbackTime = System.currentTimeMillis();
                    try {
                        playback();
                        playbackTimeEnd = System.currentTimeMillis();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                // Release note
                PianoKey note = pianoKeyLayout.getPianoKey(keyCode);
                if (note != null) {
                    endNote(note);
                }
            }
        });
    }
}
