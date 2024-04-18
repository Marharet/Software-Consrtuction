package ua.edu.tntu._121_se.midipiano.ui.pianokeys;

import javax.swing.*;

public abstract class PianoKeyButton extends JComponent {

    protected static final String KEY_TEXT_FONT = "Arial";
    protected int correspondingKeyCode;
    protected boolean isPressed = false;

    public PianoKeyButton(int correspondingKeyCode) {
        this.correspondingKeyCode = correspondingKeyCode;
    }

    public void press() {
        isPressed = true;
        repaint();
    }

    public void release() {
        isPressed = false;
        repaint();
    }

    protected String getCorrespondingKeyName() {
        return KeyNameTranscriber.getKeyString(correspondingKeyCode);
    }
}
