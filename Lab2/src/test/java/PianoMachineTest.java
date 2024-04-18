import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sound.midi.MidiUnavailableException;
import ua.edu.tntu._121_se.midipiano.midi.Midi;
import ua.edu.tntu._121_se.midipiano.music.Pitch;
import ua.edu.tntu._121_se.midipiano.piano.PianoMachine;

public class PianoMachineTest {

    @Test
    public void singleNoteTest() throws MidiUnavailableException {
        PianoMachine pm = new PianoMachine();

        String expected0 = "on(61,PIANO) wait(100) off(61,PIANO)";

        Midi midi = Midi.getInstance();
        midi.clearHistory();

        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        assertEquals(expected0, midi.history());
    }

    @Test
    public void changeInstrumentTest() throws MidiUnavailableException {
        PianoMachine pm = new PianoMachine();
        // Arrange
        String expected0 = "on(61,PIANO) wait(100) off(61,PIANO) wait(0) on(61,ELECTRIC_PIANO_1) wait(100) off(61,ELECTRIC_PIANO_1)";
        // ELECTRIC_GRAND

        Midi midi = Midi.getInstance();
        midi.clearHistory();

        // Act
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        for(int i = 0; i<=3; i++){
            pm.changeInstrument();
        }
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        // Assert
        assertEquals(expected0, midi.history());
    }

    @Test
    public void shiftUpTest() throws MidiUnavailableException {
        PianoMachine pm = new PianoMachine();
        // Arrange
        String expected0 = "on(61,PIANO) wait(100) off(61,PIANO) wait(0) on(73,PIANO) wait(100) off(73,PIANO)";

        Midi midi = Midi.getInstance();
        midi.clearHistory();

        // Act
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        pm.shiftUp();
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        // Assert
        assertEquals(expected0, midi.history());
    }

    @Test
    public void shiftDownTest() throws MidiUnavailableException {
        PianoMachine pm = new PianoMachine();
        // Arrange
        String expected0 = "on(61,PIANO) wait(100) off(61,PIANO) wait(0) on(49,PIANO) wait(100) off(49,PIANO)";

        Midi midi = Midi.getInstance();
        midi.clearHistory();

        // Act
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        pm.shiftDown();
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        // Assert
        assertEquals(expected0, midi.history());
    }

    @Test
    public void shiftDownAndUpTest() throws MidiUnavailableException {
        PianoMachine pm = new PianoMachine();
        // Arrange
        String expected0 = "on(61,PIANO) wait(100) off(61,PIANO) wait(0) on(61,PIANO) wait(100) off(61,PIANO)";

        Midi midi = Midi.getInstance();
        midi.clearHistory();

        // Act
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        pm.shiftUp();
        pm.shiftDown();
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        // Assert
        assertEquals(expected0, midi.history());
    }

    @Test
    public void recordingAndPlaybackTest() throws MidiUnavailableException, InterruptedException {
        PianoMachine pm = new PianoMachine();
        // Arrange
        String expected0 = "on(61,PIANO) wait(100) off(61,PIANO) wait(0) on(61,PIANO) wait(100) off(61,PIANO)";

        Midi midi = Midi.getInstance();
        midi.clearHistory();

        // Act
        pm.toggleRecording();
        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));
        pm.toggleRecording();
        midi.clearHistory();
        pm.playback();

        // Assert
        assertEquals(expected0, midi.history());
    }

    @Test
    public void beginNoteWithoutEndingPrevTest() throws MidiUnavailableException {
        PianoMachine pm = new PianoMachine();

        String expected0 = "on(61,PIANO) wait(0) on(61,PIANO) wait(100) off(61,PIANO)";

        Midi midi = Midi.getInstance();
        midi.clearHistory();

        pm.beginNote(new Pitch(1));

        pm.beginNote(new Pitch(1));
        Midi.wait(100);
        pm.endNote(new Pitch(1));

        assertEquals(expected0, midi.history());
    }

}
