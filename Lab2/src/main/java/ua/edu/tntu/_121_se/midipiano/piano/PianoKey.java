package ua.edu.tntu._121_se.midipiano.piano;

import ua.edu.tntu._121_se.midipiano.music.Pitch;

public class PianoKey {

    public static final PianoKey C = new PianoKey(new Pitch('C'));
    public static final PianoKey Cs = new PianoKey(new Pitch('C').transpose(1));
    public static final PianoKey D = new PianoKey(new Pitch('D'));
    public static final PianoKey Ds = new PianoKey(new Pitch('D').transpose(1));
    public static final PianoKey E = new PianoKey(new Pitch('E'));
    public static final PianoKey F = new PianoKey(new Pitch('F'));
    public static final PianoKey Fs = new PianoKey(new Pitch('F').transpose(1));
    public static final PianoKey G = new PianoKey(new Pitch('G'));
    public static final PianoKey Gs = new PianoKey(new Pitch('G').transpose(1));
    public static final PianoKey A = new PianoKey(new Pitch('A'));
    public static final PianoKey As = new PianoKey(new Pitch('A').transpose(1));
    public static final PianoKey B = new PianoKey(new Pitch('B'));
    public static final PianoKey C_Far = new PianoKey(new Pitch('C').transpose(Pitch.OCTAVE));

    private final Pitch pitch;

    public PianoKey(Pitch pitch) {
        this.pitch = pitch;
    }

    public Pitch getPitch() {
        return pitch;
    }

    @Override
    public int hashCode() {
        return pitch.toMidiFrequency();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        PianoKey that = (PianoKey) obj;
        return this.pitch.toMidiFrequency() == that.pitch.toMidiFrequency();
    }
}
