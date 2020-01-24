package distance.learning.client.views;

import distance.learning.client.ClientMainApp;
import distance.learning.client.FigureClient;
import distance.learning.client.MessageClient;
import distance.learning.client.SoundReceiver;
import distance.learning.common.BaseController;
import distance.learning.common.instruments.Figure;
import distance.learning.common.Message;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import static distance.learning.common.ErrorWindowManager.showErrorMessage;

/**
 * Created by maxim_anatolevich on 24.04.16.
 */
public class ClientMainWindowController extends BaseController<ClientMainApp> {
    private SoundReceiver soundReceiver;
    private MessageClient messageClient;
    private FigureClient figureClient;

    private GraphicsContext baseG;

    @FXML
    private Canvas canvas;

    @FXML
    private Button btnSpeaker;
    @FXML
    private Button btnConnect;

    @FXML
    private TextField msgField;

    @FXML
    private TextArea msgArea;

    public void initialize(){
        btnSpeaker.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/icon/non-speaker.png"))));
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        baseG = canvas.getGraphicsContext2D();
    }


    public void drawAll(){
        baseG.setStroke(Color.WHITE);
        baseG.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Figure f : getMainApp().figureList){
            f.draw(baseG);
        }
    }

    @FXML
    public void onBtnConnectClick(){
        if(btnConnect.getText().equals("Connect")) {
            messageClient = new MessageClient(getMainApp(), msgArea, getMainApp().getServerIP(), getMainApp().messageServerPort);
            if (messageClient.connected) {
                figureClient = new FigureClient(getMainApp(), baseG, getMainApp().getServerIP(), getMainApp().figureServerPort);
                soundReceiver = new SoundReceiver(getMainApp());
                soundReceiver.start();
                btnConnect.setText("Disconnect");

            } else {
                btnConnect.setText("Connect");
                showErrorMessage("Не удалось подключиться (проверьте настройки).");
            }
        }
        else if(btnConnect.getText().equals("Disconnect")){
            messageClient.disconnect();
            messageClient = null;
            figureClient = null;
            soundReceiver.stop();
            soundReceiver = null;
            btnConnect.setText("Connect");
        }

    }

    @FXML
    public void onBtnSendClick(){
        if(messageClient != null) {
            String text = msgField.getText();
            if (!text.equals("")) {
                text = getMainApp().name + " -> " + text;
                messageClient.sendMessage(new Message(Message.MESSAGE, text, true));
                msgField.clear();
            }
        }
    }

    @FXML
    private void onSpeakerButtonClicked(){
        getMainApp().speakerState = !getMainApp().speakerState;
        if(getMainApp().speakerState)
            btnSpeaker.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/icon/speaker.png"))));
        else
            btnSpeaker.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../resources/icon/non-speaker.png"))));
    }


    public void clear(){
        getMainApp().figureList.clear();
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
