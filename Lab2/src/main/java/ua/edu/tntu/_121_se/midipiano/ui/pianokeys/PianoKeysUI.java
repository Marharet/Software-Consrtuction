package ua.edu.tntu._121_se.midipiano.ui.pianokeys;

import ua.edu.tntu._121_se.midipiano.ui.PianoKeyLayout;
import ua.edu.tntu._121_se.midipiano.piano.PianoKey;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PianoKeysUI extends JLayeredPane {

    private final ArrayList<PianoKeyButton> keys = new ArrayList<>();
    private final HashMap<PianoKey, PianoKeyButton> keyMap = new HashMap<>();

    public PianoKeysUI(PianoKeyLayout layout) {
        setLayout(null);

        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.C)), PianoKey.C);
        addKey(new BlackPianoKeyButton(layout.getKey(PianoKey.Cs)), PianoKey.Cs);
        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.D)), PianoKey.D);
        addKey(new BlackPianoKeyButton(layout.getKey(PianoKey.Ds)), PianoKey.Ds);
        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.E)), PianoKey.E);
        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.F)), PianoKey.F);
        addKey(new BlackPianoKeyButton(layout.getKey(PianoKey.Fs)), PianoKey.Fs);
        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.G)), PianoKey.G);
        addKey(new BlackPianoKeyButton(layout.getKey(PianoKey.Gs)), PianoKey.Gs);
        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.A)), PianoKey.A);
        addKey(new BlackPianoKeyButton(layout.getKey(PianoKey.As)), PianoKey.As);
        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.B)), PianoKey.B);
        addKey(new WhitePianoKeyButton(layout.getKey(PianoKey.C_Far)), PianoKey.C_Far);
    }

    @Override
    public Dimension getPreferredSize() {
        int x = 0;
        int y = WhitePianoKeyButton.BUTTON_HEIGHT;
        for (PianoKeyButton key : keys) {
            if (key instanceof WhitePianoKeyButton) {
                x += WhitePianoKeyButton.BUTTON_WIDTH;
            }
        }
        return new Dimension(x, y);
    }

    public void pressKey(PianoKey note) {
        PianoKeyButton key = keyFromNote(note);
        if (key != null) {
            key.press();
        }
    }

    public void releaseKey(PianoKey note) {
        PianoKeyButton key = keyFromNote(note);
        if (key != null) {
            key.release();
        }
    }

    private PianoKeyButton keyFromNote(PianoKey note) {
        if (keyMap.containsKey(note)) {
            return keyMap.get(note);
        }
        return null;
    }

    private void addKey(PianoKeyButton key, PianoKey note) {
        int x = 0;
        int y = 0;
        int w = key instanceof WhitePianoKeyButton ? WhitePianoKeyButton.BUTTON_WIDTH : BlackPianoKeyButton.BUTTON_WIDTH;
        int h = key instanceof WhitePianoKeyButton ? WhitePianoKeyButton.BUTTON_HEIGHT : BlackPianoKeyButton.BUTTON_HEIGHT;

        if (!keys.isEmpty()) {
            PianoKeyButton lastKey = keys.get(keys.size()-1);
            if (key instanceof BlackPianoKeyButton) {
                x = lastKey.getBounds().x + WhitePianoKeyButton.BUTTON_WIDTH - BlackPianoKeyButton.BUTTON_WIDTH / 2;
                if (lastKey instanceof WhitePianoKeyButton lastWhiteKey) {
                    switch(lastWhiteKey.getType()) {
                        case Whole -> {lastWhiteKey.setType(WhitePianoKeyButton.WhiteButtonType.RightGroove);}
                        case LeftGroove -> {lastWhiteKey.setType(WhitePianoKeyButton.WhiteButtonType.BothGrooves);}
                        default -> {
                            throw new IllegalArgumentException("Unexpected value: " + lastWhiteKey.getType());
                        }
                    }
                }
            } else if (key instanceof WhitePianoKeyButton) {
                if (lastKey instanceof WhitePianoKeyButton) {
                    x = lastKey.getBounds().x + WhitePianoKeyButton.BUTTON_WIDTH;
                } else if (lastKey instanceof BlackPianoKeyButton) {
                    ((WhitePianoKeyButton)key).setType(WhitePianoKeyButton.WhiteButtonType.LeftGroove);
                    x = lastKey.getBounds().x + BlackPianoKeyButton.BUTTON_WIDTH / 2;
                }
            }
        }

        key.setBounds(x, y, w, h);
        add(key, key instanceof WhitePianoKeyButton ? JLayeredPane.DEFAULT_LAYER : JLayeredPane.PALETTE_LAYER);
        keys.add(key);
        keyMap.put(note, key);
    }
}
