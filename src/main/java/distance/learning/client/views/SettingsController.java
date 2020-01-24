package distance.learning.client.views;

import distance.learning.client.ClientMainApp;
import distance.learning.common.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 * Created by maxim_anatolevich on 22.05.16.
 */
public class SettingsController extends BaseController<ClientMainApp> {
    Mixer.Info[] mixerInfo = null;

    @FXML
    private ChoiceBox mixerBox;

    @FXML
    private TextField serverIPField;

    @FXML
    private TextField messageServerPortField;

    @FXML
    private TextField figureServerPortField;

    @FXML
    private TextField groupIPField;

    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;

    @FXML
    public void initialize(){
        showSpeakers();
       /* serverIPField.setText(getMainApp().getServerIP().toString());
        groupIPField.setText(getMainApp().getGroupIP().toString());
        messageServerPortField.setText(getMainApp().messageServerPort + "");
        figureServerPortField.setText(getMainApp().figureServerPort + "");
        passwordField.setText(getMainApp().password);
        nameField.setText(getMainApp().name);*/
    }

    public void showSpeakers(){
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
        getMainApp().name = nameField.getText();
        getMainApp().password = passwordField.getText();
        getMainApp().setServerIP(serverIPField.getText());
        getMainApp().messageServerPort = Integer.parseInt(messageServerPortField.getText());
        getMainApp().figureServerPort = Integer.parseInt(figureServerPortField.getText());
        getStage().close();
    }
}
