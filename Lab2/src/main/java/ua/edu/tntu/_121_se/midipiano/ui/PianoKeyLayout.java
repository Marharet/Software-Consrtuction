package ua.edu.tntu._121_se.midipiano.ui;

import ua.edu.tntu._121_se.midipiano.piano.PianoKey;

import java.util.HashMap;

public class PianoKeyLayout {

    private final HashMap<Integer, PianoKey> layoutMap = new HashMap<>();
    private final HashMap<PianoKey, Integer> inverseMap = new HashMap<>();

    public PianoKeyLayout add(int key, PianoKey pianoKey) {
        layoutMap.put(key, pianoKey);
        inverseMap.put(pianoKey, key);
        return this;
    }

    public PianoKey getPianoKey(int key) {
        if (layoutMap.containsKey(key)) {
            return layoutMap.get(key);
        }
        return null;
    }

    public int getKey(PianoKey pitch) {
        if (inverseMap.containsKey(pitch)) {
            return inverseMap.get(pitch);
        }
        return -1;
    }
}
