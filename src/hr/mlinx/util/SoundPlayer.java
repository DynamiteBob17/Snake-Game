package hr.mlinx.util;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import hr.mlinx.game_object.GameObject;
import hr.mlinx.main.Game;

public class SoundPlayer {
	
	private static final int MAX_SCORE = (Game.WIDTH / GameObject.SIZE) * (Game.HEIGHT / GameObject.SIZE);
	private static final int EAT_NOTE_LOW_RANGE = 60;
	private static final int EAT_NOTE_HIGH_RANGE = 100;
	
	private MidiChannel[] mc;
	private Instrument[] instruments;
	
	public SoundPlayer() {
		try {
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
			instruments = midiSynth.getAvailableInstruments();
			mc = midiSynth.getChannels();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void defeat() {
		if (mc != null) {
			new Thread(() -> {
				mc[0].programChange(instruments[35].getPatch().getProgram());
				int[] notes = new int[] {71, 69, 67, 65, 64, 62, 60}; // cdefgab notes in reverse, 3rd octave
				for (int j = 0; j < notes.length; ++j) {
					mc[0].noteOn(notes[j], 100);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mc[0].noteOff(notes[j]);
				}
			}).start();
		}
	}
	
	public void victory() {
		if (mc != null) {
			new Thread(() -> {
				mc[0].programChange(instruments[9].getPatch().getProgram());
				int[] notes = new int[] {84, 86, 88, 89, 91, 93, 95}; // cdefgab notes, 5th octave
				
				for (int i = 0; i < 5; ++i) {
					for (int j = 0; j < notes.length; ++j) {
						mc[0].noteOn(notes[j], 100);
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mc[0].noteOff(notes[j]);
					}
				}
			}).start();
		}
	}
	
	public void eat(int score) {
		if (mc != null) {
			new Thread(() -> {
				mc[0].programChange(instruments[10].getPatch().getProgram());
				
				int note = (int) Util.map(score, 1, MAX_SCORE, EAT_NOTE_LOW_RANGE, EAT_NOTE_HIGH_RANGE);
				
				mc[0].noteOn(note, 75);
				mc[0].noteOff(note);
			}).start();
		}
	}
	
}
