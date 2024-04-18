package ua.edu.tntu._121_se.midipiano.piano;

import javax.sound.midi.MidiUnavailableException;

import ua.edu.tntu._121_se.midipiano.midi.Instrument;
import ua.edu.tntu._121_se.midipiano.midi.Midi;
import ua.edu.tntu._121_se.midipiano.music.NoteEvent;
import ua.edu.tntu._121_se.midipiano.music.Pitch;

import java.util.LinkedList;

public class PianoMachine {

    private static final int maxOctavesOffset = 2;
    private Midi midi;
    private Instrument instrument = Instrument.PIANO;
    private int octaveOffset = 0;
    private LinkedList<NoteEvent> recorded = new LinkedList<>();
    private boolean isRecording = false;

    /**
     * constructor for PianoMachine.
     * initialize midi device and any other state that we're storing.
     */
    public PianoMachine() {
        try {
            midi = Midi.getInstance();
        } catch (MidiUnavailableException e1) {
            System.err.println("Could not initialize midi device");
            e1.printStackTrace();
        }
    }

    /**
     * Begin playing a note in the current octave
     * @param rawPitch a pitch to be played
     */
    public void beginNote(Pitch rawPitch) {
        if(isRecording){
            long time = System.nanoTime();
            NoteEvent.Kind kind = NoteEvent.Kind.start;
            recorded.add(new NoteEvent(rawPitch, time, instrument, kind));
        }
        midi.beginNote(getPitch(rawPitch).toMidiFrequency(), instrument);
    }

    /**
     * Stop playing a note in the current octave
     * @param rawPitch a pitch to stop playing
     */
    public void endNote(Pitch rawPitch) {
        if(isRecording){
            long time = System.nanoTime();
            NoteEvent.Kind kind = NoteEvent.Kind.stop;
            recorded.add(new NoteEvent(rawPitch, time, instrument, kind));
        }
        midi.endNote(getPitch(rawPitch).toMidiFrequency(), instrument);
    }

    /**
     * Switch the instrument played by the machine to the next one available
     */
    public void changeInstrument() {
        instrument = instrument.next();
    }

    /**
     * Shift the PianoMachine note scale up by one octave
     */
    public void shiftUp() {
        if (octaveOffset < maxOctavesOffset) {
            octaveOffset++;
        }
    }

    /**
     * Shift the PianoMachine note scale down by one octave
     */
    public void shiftDown() {
        if (octaveOffset > -maxOctavesOffset) {
            octaveOffset--;
        }
    }

    /**
     * Changes state isRecording to opposite
     */
    public void toggleRecording() {
        if(!isRecording)
            recorded.clear();
        isRecording = !isRecording;
    }

    /**
     * Playback of recorded notes
     */
    public void playback() throws InterruptedException {
        long timeDiff = 0;
        long thisTime = 0;
        long prevTime = 0;
        for(NoteEvent ne: recorded){
            thisTime = ne.getTime();
            if(prevTime == 0) {
                prevTime = thisTime;
            }
            if(ne.getKind() == NoteEvent.Kind.start){
                timeDiff = (long) ((thisTime-prevTime) / Math.pow(10, 7));
                Midi.wait((int)timeDiff);
                midi.beginNote(getPitch(ne.getPitch()).toMidiFrequency(), ne.getInstr());
            }
            else{
                timeDiff = (long) ((thisTime-prevTime) / Math.pow(10, 7));
                Midi.wait((int)timeDiff);
                midi.endNote(getPitch(ne.getPitch()).toMidiFrequency(), ne.getInstr());
            }
            prevTime = thisTime;
        }
    }

    private Pitch getPitch(Pitch rawPitch) {
        return rawPitch.transpose(octaveOffset * Pitch.OCTAVE);
    }

    public Instrument getInstrument() {
        return instrument;
    }
}
