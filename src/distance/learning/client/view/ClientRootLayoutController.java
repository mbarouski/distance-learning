package distance.learning.client.view;

import distance.learning.client.util.Util;
import distance.learning.instrument.Figure;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

/**
 * Created by maxim_anatolevich on 24.04.16.
 */

public class ClientRootLayoutController extends Controller{
    //play
    private SourceDataLine speakerLine;
    private AudioFormat audioFormat = new AudioFormat(/*44100, 16, 2, true, true*/
            AudioFormat.Encoding.PCM_SIGNED,
            44100.0F, 16, 2, 4, 44100.0F, false);

    @FXML
    public void menuSettings(){
        Util.showWindow("Settings.fxml", "Client settings", getMainApp());
    }


    @FXML
    public void menuExit(){
        System.exit(0);
    }
}
