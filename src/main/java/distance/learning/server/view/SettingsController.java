package distance.learning.server.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 * Created by maxim_anatolevich on 18.04.16.
 */
public class SettingsController extends Controller{
    Mixer.Info[] mixerInfo = null;

    @FXML
    private ChoiceBox mixerBox;

    @FXML
    private TextField serverIPField;

    @FXML
    private TextField groupIPField;

    @FXML
    private TextField messageServerPortField;

    @FXML
    private TextField figureServerPortField;

    @FXML
    private TextField audioServerPortField;

    @FXML
    private TextField passwordField;

    @FXML
    public void initialize(){
        showMicrophones();
        /*serverIPField.setText(serverMainApp.getServerIP().toString());
        groupIPField.setText(serverMainApp.getGroupIP().toString());
        messageServerPortField.setText(serverMainApp.messageServerPort + "");
        figureServerPortField.setText(serverMainApp.figureServerPort + "");
        passwordField.setText(serverMainApp.getPassword());*/
    }

    public void showMicrophones(){
        mixerInfo = AudioSystem.getMixerInfo();
        ObservableList<String> mixerList = FXCollections.observableArrayList();
        for(int cnt = 0; cnt < mixerInfo.length; cnt++){
            mixerList.add(mixerInfo[cnt].getDescription());
        }
        mixerBox.setItems(mixerList);
    }

    @FXML
    public void btnApplyClick(){
        getMainApp().setMixerInfo(mixerInfo[mixerBox.getSelectionModel().getSelectedIndex()]);
        getMainApp().setPassword(passwordField.getText());
        getMainApp().setServerIP(serverIPField.getText());
        getMainApp().setGroupIP(groupIPField.getText());
        getMainApp().messageServerPort = Integer.parseInt(messageServerPortField.getText());
        getMainApp().figureServerPort = Integer.parseInt(figureServerPortField.getText());
        getMainApp().audioServerPort = Integer.parseInt(audioServerPortField.getText());
        getStage().close();
    }
}
