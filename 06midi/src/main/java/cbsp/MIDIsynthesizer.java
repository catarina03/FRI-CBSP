package cbsp;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class MIDIsynthesizer {
	
	// TODO Create classes for MIDI messages for Task 4 in 6.
	
	public MIDIsynthesizer() {
		
		// TODO Task 1 - 9. Be mindful of handling exceptions.

	}

	protected MidiMessage createMidiNoteOnMessage(int status, int pitch, int velocity){

	}

	protected MidiMessage createMidiNoteOffMessage(int status, int pitch, int velocity){

	}

	protected MidiMessage createMidiProgramChangeMessage(int instrument){
		ShortMessage msg = new ShortMessage();
		try {
			msg.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
		}
		catch (InvalidMidiDataException e){
			throw new InvalidMidiDataException(e);
		}
	}

	public static void main(String[] args) {
		try {


			synthesizer.open();

			MidiMessage noteOn = createMidiNoteOnMessage(60, 60);
			MidiMessage noteOff = createMidiNoteOffMessage(60, 30);
			MidiMessage programChange = createMidiProgramChangeMessage(100);

			rcv.send(programChange, 0);
			rcv.send(noteOn, 0);
			Thread.sleep(1000);
			rcv,.send(noteOff, 0);

			MidiChannel channel = synth.getChannels();
			channel.noteOn(70,40);
			Thread.sleep(1000);
			channel.noteOff(70, 50);

		} catch (  InterruptedException e) {
			throw new RuntimeException(e);
		}

		MIDIsynthesizer synthesizer = new MIDIsynthesizer();
			
	}

}
