package ua.edu.tntu._121_se.midipiano.ui.pianokeys;

import java.awt.event.KeyEvent;

public class KeyNameTranscriber {

    public static String getKeyString(int key) {
        String defaultKeyText = KeyEvent.getKeyText(key);
        if (defaultKeyText.length() == 1) {
            return defaultKeyText;
        }

        switch (key) {
            case KeyEvent.VK_MINUS -> { return "-"; }
            case KeyEvent.VK_EQUALS -> { return "="; }
            case KeyEvent.VK_BACK_SPACE -> { return "\u2190"; }
        }

        return "";
    }
}
