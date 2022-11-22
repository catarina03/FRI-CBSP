package cbsp;

import javax.sound.midi.*;

public class MIDIsynthesizer {
	protected Synthesizer synthesizer;
	protected Receiver receiver;
	
	public MIDIsynthesizer() {
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			receiver = synthesizer.getReceiver();

			task3and4();
			task5();
			task6();
			task7();
			task8();
			task9();

		} catch (MidiUnavailableException e) {
			throw new RuntimeException(e);
		}
	}

	protected void task3and4(){
		try {
			MidiMessage noteOn = createMidiNoteOnMessage(48,70);
			MidiMessage noteOff = createMidiNoteOffMessage(48, 70);
			receiver.send(noteOn,0);
			Thread.sleep(2000);
			receiver.send(noteOff,0);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected void task5(){
		try {
			int[] notes = new int[]{53, 53, 53, 48, 50, 50, 48, 57, 57, 55, 55, 53};
			int[] noteDurations = new int[]{500, 500, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000};

			for(int i = 0; i < notes.length; i++){
				MidiMessage noteOn = createMidiNoteOnMessage(notes[i], 70);
				MidiMessage noteOff = createMidiNoteOffMessage(notes[i], 70);
				receiver.send(noteOn,0);
				Thread.sleep(noteDurations[i]);
				receiver.send(noteOff,0);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected void task6(){
		try {
			int[] notes = new int[]{53, 53, 53, 48, 50, 50, 48, 57, 57, 55, 55, 53};
			int[] noteDurations = new int[]{500, 500, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000};
			MidiMessage changeInstrument = createMidiInstrumentChangeMessage(40);
			receiver.send(changeInstrument, 0);
			for(int i = 0; i < notes.length; i++){
				MidiMessage noteOn = createMidiNoteOnMessage(notes[i], 70);
				MidiMessage noteOff = createMidiNoteOffMessage(notes[i], 70);
				receiver.send(noteOn,0);
				Thread.sleep(noteDurations[i]);
				receiver.send(noteOff,0);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected void task7(){
		try {
			MidiChannel channel = synthesizer.getChannels()[0];
			int[] notes = new int[]{53, 53, 53, 48, 50, 50, 48, 57, 57, 55, 55, 53};
			int[] noteDurations = new int[]{500, 500, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000};

			channel.programChange(40);
			for(int i = 0; i < notes.length; i++){
				channel.noteOn(notes[i], 70);
				Thread.sleep(noteDurations[i]);
				channel.noteOff(notes[i], 70);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected void task8(){
		try {
			MidiChannel channel1 = synthesizer.getChannels()[0];
			MidiChannel channel2 = synthesizer.getChannels()[1];
			MidiChannel[] channels = new MidiChannel[]{channel1, channel2};

			int[] notes = new int[]{53, 53, 53, 48, 50, 50, 48, 57, 57, 55, 55, 53};
			int[] noteDurations = new int[]{500, 500, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000};

			channel1.programChange(0);
			channel2.programChange(73);
			for (int i = 0; i < notes.length; i++) {
				for (MidiChannel channel : channels) {
					channel.noteOn(notes[i], 70);
				}
					Thread.sleep(noteDurations[i]);
				for (MidiChannel channel : channels) {
					channel.noteOff(notes[i], 70);
				}
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected void task9(){
		try {
			MidiChannel channel1 = synthesizer.getChannels()[0];
			MidiChannel channel2 = synthesizer.getChannels()[1];
			MidiChannel[] channels = new MidiChannel[]{channel1, channel2};

			int[] notes = new int[]{53, 53, 53, 48, 50, 50, 48, 57, 57, 55, 55, 53};
			int[] noteDurations = new int[]{500, 500, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000};
			int octave = 12;

			channel1.programChange(0); //piano
			channel2.programChange(73); //flute
			for (int i = 0; i < notes.length; i++) {
				for (int ch = 0; ch < channels.length; ch++) {
					if (ch == 1){
						channels[ch].noteOn(notes[i]+octave, 50);
					}
					else {
						channels[ch].noteOn(notes[i], 70);
					}
				}
				Thread.sleep(noteDurations[i]);
				for (int ch = 0; ch < channels.length; ch++) {
					if (ch == 1){
						channels[ch].noteOff(notes[i]+octave, 50);
					}
					else {
						channels[ch].noteOff(notes[i], 70);
					}
				}
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected MidiMessage createMidiNoteOnMessage(int pitch, int velocity){
		return createMidiNoteMessage(ShortMessage.NOTE_ON, pitch, velocity);
	}

	protected MidiMessage createMidiNoteOffMessage(int pitch, int velocity){
		return createMidiNoteMessage(ShortMessage.NOTE_OFF, pitch, velocity);
	}

	public MidiMessage createMidiNoteMessage(int status, int pitch, int velocity){
		ShortMessage msg = new ShortMessage();
		try {
			msg.setMessage(status, 0,pitch,velocity);
		} catch (InvalidMidiDataException e) {
			throw new RuntimeException(e);
		}
		return msg;
	}

	protected static MidiMessage createMidiInstrumentChangeMessage(int instrument){
		ShortMessage msg = new ShortMessage();
		try {
			msg.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
		}
		catch (InvalidMidiDataException e){
			throw new RuntimeException(e);
		}
		return msg;
	}

	public static void main(String[] args) {
		MIDIsynthesizer synthesizer = new MIDIsynthesizer();
			
	}

}
