package ua.edu.tntu._121_se.midipiano.ui;

import ua.edu.tntu._121_se.midipiano.piano.PianoKey;

import java.awt.event.KeyEvent;

public class PianoKeyLayouts {

    public static final PianoKeyLayout NumberRowLayout = new PianoKeyLayout()
            .add(KeyEvent.VK_1, PianoKey.C)
            .add(KeyEvent.VK_2, PianoKey.Cs)
            .add(KeyEvent.VK_3, PianoKey.D)
            .add(KeyEvent.VK_4, PianoKey.Ds)
            .add(KeyEvent.VK_5, PianoKey.E)
            .add(KeyEvent.VK_6, PianoKey.F)
            .add(KeyEvent.VK_7, PianoKey.Fs)
            .add(KeyEvent.VK_8, PianoKey.G)
            .add(KeyEvent.VK_9, PianoKey.Gs)
            .add(KeyEvent.VK_0, PianoKey.A)
            .add(KeyEvent.VK_MINUS, PianoKey.As)
            .add(KeyEvent.VK_EQUALS, PianoKey.B)
            .add(KeyEvent.VK_BACK_SPACE, PianoKey.C_Far);

    public static final PianoKeyLayout TraditionalLayout = new PianoKeyLayout()
            .add(KeyEvent.VK_Q, PianoKey.C)
            .add(KeyEvent.VK_2, PianoKey.Cs)
            .add(KeyEvent.VK_W, PianoKey.D)
            .add(KeyEvent.VK_3, PianoKey.Ds)
            .add(KeyEvent.VK_E, PianoKey.E)
            .add(KeyEvent.VK_R, PianoKey.F)
            .add(KeyEvent.VK_5, PianoKey.Fs)
            .add(KeyEvent.VK_T, PianoKey.G)
            .add(KeyEvent.VK_6, PianoKey.Gs)
            .add(KeyEvent.VK_Y, PianoKey.A)
            .add(KeyEvent.VK_7, PianoKey.As)
            .add(KeyEvent.VK_U, PianoKey.B)
            .add(KeyEvent.VK_I, PianoKey.C_Far);

}
