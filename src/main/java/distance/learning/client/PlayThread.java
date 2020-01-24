package distance.learning.client;

import distance.learning.server.SoundSender;

import javax.sound.sampled.*;

import static distance.learning.common.ErrorWindowManager.showErrorMessage;

/**
 * Created by maxim_anatolevich on 23.04.16.
 */
public class PlayThread{
    private SoundSender soundSender;
    private SourceDataLine speakerLine;

    public PlayThread(){
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                8000, 16, 2, 4, 8000, false);
        Mixer mixer = AudioSystem.getMixer(AudioSystem.getMixerInfo()[0]);
        try {
            speakerLine = (SourceDataLine) mixer.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
            speakerLine.open(audioFormat);
            speakerLine.start();
        }
        catch (Exception exc){
            showErrorMessage("play: " + exc.getMessage());
        }
    }

    public void play(byte[] buffer, int length){
        speakerLine.write(buffer, 0, length);
    }
}
