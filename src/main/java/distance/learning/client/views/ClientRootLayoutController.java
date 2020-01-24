package distance.learning.client.views;

import distance.learning.client.ClientMainApp;
import distance.learning.common.BaseController;
import javafx.fxml.FXML;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

/**
 * Created by maxim_anatolevich on 24.04.16.
 */

public class ClientRootLayoutController extends BaseController<ClientMainApp> {
    //play
    private SourceDataLine speakerLine;
    private AudioFormat audioFormat = new AudioFormat(/*44100, 16, 2, true, true*/
            AudioFormat.Encoding.PCM_SIGNED,
            44100.0F, 16, 2, 4, 44100.0F, false);

    @FXML
    public void menuSettings(){
        getMainApp().getFxmlLoader().loadWindow("Settings", "Client settings", getMainApp());
    }


    @FXML
    public void menuExit(){
        System.exit(0);
    }
}
