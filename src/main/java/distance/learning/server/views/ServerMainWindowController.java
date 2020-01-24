package distance.learning.server.views;

import distance.learning.common.BaseController;
import distance.learning.common.instruments.*;
import distance.learning.common.Message;
import distance.learning.common.ResourceLoader;
import distance.learning.server.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.sound.sampled.*;
import java.net.ServerSocket;
import java.net.Socket;

import static distance.learning.common.ErrorWindowManager.showErrorMessage;

/**
 * Created by maxim_anatolevich on 06.04.16.
 */
public class ServerMainWindowController extends BaseController<ServerMainApp> {

    public Thread messageServerSocketThread;
    public Thread figureServerSocketThread;

    private ServerSocket messageSocketServer;
    private ServerSocket figureSocketServer;
    private RecordThread recordThread;
    private String currentInstrument = "";
    private GraphicsContext baseG;

    public ObservableList<Figure> figureList = FXCollections.observableArrayList();

    @FXML
    private TableView<Figure> tableView;

    @FXML
    private TableColumn<Figure, String> tableColumn;

    //@FXML
    public TextArea msgArea;

    @FXML
    private Label lblWidth;

    @FXML
    private Canvas canvas;

    @FXML
    private Slider widthSlider;

    @FXML
    private Button btnRectangle;
    @FXML
    private Button btnEllipse;
    @FXML
    private Button btnPolyline;
    //@FXML
    private Button btnPolygon;
    @FXML
    private Button btnPencil;
    @FXML
    private Button btnText;
    @FXML
    private Button btnMicrophone;
    @FXML
    private Button btnClear;
    @FXML
    private ColorPicker clrPicker;
    @FXML
    private TextField msgField;
    @FXML
    private Button btnStartTranslation;

    private Color color;
    private int width;

    private Figure figure;

    private ResourceLoader resourceLoader;

    public ServerMainWindowController() {
        this.color = Color.WHITE;
        this.width = 5;
        this.resourceLoader = new ResourceLoader();
    }

    @FXML
    private void onSliderChange() {
        width = (int) widthSlider.getValue();
        lblWidth.setText("Width: " + width + "px");
        canvas.getGraphicsContext2D().setLineWidth(widthSlider.getValue());
    }

    @FXML
    private void onColorDialogClick() {
        color = clrPicker.getValue();
        canvas.getGraphicsContext2D().setStroke(color);
    }

    @FXML
    private void onFigureButtonClick(ActionEvent event) {
        figure = null;
        currentInstrument = ((Button) event.getSource()).getText();
    }


    public Figure createFigure() {
        Figure result = null;
        switch (currentInstrument) {
            case "Ellipse":
                result = new Ellipse(width, color);
                break;
            case "Rectangle":
                result = new Rectangle(width, color);
                break;
            case "Text":
                currentInstrument = "";
                TextInputWindowController textInputWindowController = (TextInputWindowController) getMainApp().getFxmlLoader().loadWindow("TextInputWindow", "Adding text", getMainApp());
                String text = textInputWindowController.getText();
                currentInstrument = "Text";
                result = new Text(width, color, text);
                break;
            case "Polyline":
            case "Pencil":
                result = new Polyline(width, color);
                break;
        }
        //if(result != null) figureList.add(result);
        return result;
    }

    @FXML
    private void onCanvasPressed(javafx.scene.input.MouseEvent event) {
        if (figure == null)
            figure = createFigure();
    }

    @FXML
    private void onCanvasClicked(javafx.scene.input.MouseEvent event) {
        if (event.getClickCount() == 1) {
            if (figure == null) {
                figure = createFigure();
            }
            if (figure != null) {

                if (figure.getCountOfPoints() < figure.countOfPoints) {
                    figure.addPoint(new Point((int) Math.round(event.getX()), (int) Math.round(event.getY())));
                    if (figure.countOfPoints == Integer.MAX_VALUE) {
                        figure.draw(baseG);
                    }
                    if (currentInstrument.equals("Pencil")) {
                        sendFigureToAll(figure);
                        figureList.add(figure);
                        figure = null;
                    }

                }
            }
        } else if (event.getClickCount() == 2) {
            if (figure != null) {
                if ((figure.getCountOfPoints() >= figure.countOfPoints - 1) || (figure.countOfPoints == Integer.MAX_VALUE)) {
                    figure.setEndPoint(new Point((int) Math.round(event.getX()), (int) Math.round(event.getY())));
                    figure.draw(canvas.getGraphicsContext2D());
                    figureList.add(figure);
                    sendFigureToAll(figure);
                    figure = null;
                }
            }
        }
    }

    @FXML
    private void onCanvasDragged(javafx.scene.input.MouseEvent event) {
        if (figure != null)
            if (currentInstrument.equals("Pencil")) {
                figure.addPoint(new Point((int) Math.round(event.getX()), (int) Math.round(event.getY())));
                figure.draw(baseG);
            }
    }

    //@FXML
    private void onCanvasDoubleClick(javafx.scene.input.MouseEvent event) {
    }

    @FXML
    private void onMicrophoneButtonClicked() {
        getMainApp().microphoneState = !getMainApp().microphoneState;
        if (getMainApp().microphoneState)
            btnMicrophone.setGraphic(new ImageView(resourceLoader.loadImage("microphone")));
        else
            btnMicrophone.setGraphic(new ImageView(resourceLoader.loadImage("non-microphone")));
    }

    public void initialize() {
        btnRectangle.setGraphic(new ImageView(resourceLoader.loadImage("rectangle")));
        btnEllipse.setGraphic(new ImageView(resourceLoader.loadImage("ellipse")));
        btnPencil.setGraphic(new ImageView(resourceLoader.loadImage("pencil")));
        btnPolyline.setGraphic(new ImageView(resourceLoader.loadImage("polyline")));
        btnClear.setGraphic(new ImageView(resourceLoader.loadImage("clear")));
        btnMicrophone.setGraphic(new ImageView(resourceLoader.loadImage("non-microphone")));
        btnText.setGraphic(new ImageView(resourceLoader.loadImage("text")));

        onClearButtonClick();
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(figureList);
        baseG = canvas.getGraphicsContext2D();
        canvas.getGraphicsContext2D().setStroke(clrPicker.getValue());

    }

    @FXML
    private void onTableKeyTyped(javafx.scene.input.KeyEvent event) {
        if ((event.getCode().ordinal() == 187) && (event.isAltDown())) {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            sendMessageToAll(new Message(Message.COMMAND, "del:" + selectedIndex, true));
            if (selectedIndex > -1) {
                figureList.remove(selectedIndex);
                drawAll();
            }
        }
    }

    @FXML
    private void onClearButtonClick() {
        figure = null;
        figureList.clear();
        sendMessageToAll(new Message(Message.COMMAND, "clear:", true));
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    private void drawAll() {
        onClearButtonClick();
        for (Figure f : figureList) {
            f.draw(baseG);
        }
    }

    private AudioFormat audioFormat = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            8000, 16, 2, 4, 8000, false);

    //@FXML
    public void beginRecord() {
        if (btnStartTranslation.getText().equals("Start translation")) {
            try {
                messageSocketServer = new ServerSocket(getMainApp().messageServerPort, 0, getMainApp().getServerIP());
                messageSocketServer.setReuseAddress(true);
                figureSocketServer = new ServerSocket(getMainApp().figureServerPort, 0, getMainApp().getServerIP());
                figureSocketServer.setReuseAddress(true);
            } catch (Exception exc) {
                messageSocketServer = null;
                figureSocketServer = null;
                btnStartTranslation.setText("Start translation");
                getMainApp().rootController.setMenuFileActive(true);
                showErrorMessage(exc.getMessage());
            }

            figureServerSocketThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (!figureServerSocketThread.isInterrupted()) {
                            Socket clientSocket = messageSocketServer.accept();
                            MessageServer messageServer = new MessageServer(getMainApp(), msgArea, clientSocket);
                            if (messageServer.connected) {
                                getMainApp().messageServerList.add(messageServer);
                            }
                        }
                        System.out.println("messageserversocket stopped");
                    } catch (Exception exc) {
                        showErrorMessage(exc.getMessage());
                    }
                }
            });
            figureServerSocketThread.start();

            messageServerSocketThread = new Thread(new Runnable() {
                private boolean stopFlag = false;

                public void stop() {
                    stopFlag = true;
                }

                @Override
                public void run() {
                    try {
                        while (!stopFlag) {
                            Socket clientSocket = figureSocketServer.accept();
                            FigureServer figureServer = new FigureServer(getMainApp(), baseG, clientSocket);
                            getMainApp().figureServerList.add(figureServer);
                        }
                        System.out.println("figureserversocket stopped");
                    } catch (Exception exc) {
                        showErrorMessage(exc.getMessage());
                    }
                }
            });
            messageServerSocketThread.start();

            DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            Mixer mixer = AudioSystem.getMixer(/*getMainApp().getMixerInfo()*/
                    AudioSystem.getMixerInfo()[0]);
            try {
                //record
                TargetDataLine microphoneLine = (TargetDataLine) mixer.getLine(lineInfo);
                microphoneLine.open(audioFormat);
                microphoneLine.start();
                recordThread = new RecordThread(microphoneLine, getMainApp());
                recordThread.start();
                btnStartTranslation.setText("Stop translation");
                getMainApp().rootController.setMenuFileActive(false);
            } catch (Exception exc) {
                btnStartTranslation.setText("Start translation");
                getMainApp().rootController.setMenuFileActive(true);
                showErrorMessage("record" + exc.getMessage());
            }
        } else if (btnStartTranslation.getText().equals("Stop translation")) {
            try {
                messageSocketServer.close();
                figureSocketServer.close();
                recordThread.setStopFlag();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            messageSocketServer = null;
            figureSocketServer = null;
            getMainApp().figureServerList.clear();
            getMainApp().messageServerList.clear();
            btnStartTranslation.setText("Start translation");
            getMainApp().rootController.setMenuFileActive(true);
        }

    }

//    public void stopThreads() {
//        try {
//            messageServerSocketThread.interrupt();
//            figureServerSocketThread.stop();
//            recordThread.setStopFlag();
//        } catch (Exception exc) {
//        }
//    }

    @FXML
    private void onBtnSendClick() {
        if (messageSocketServer != null) {
            String text = msgField.getText();
            if (!text.equals("")) {
                msgArea.appendText("Server -> " + text + "\n");
                Message msg = new Message(Message.MESSAGE, text, true);
                for (MessageServer ms : getMainApp().messageServerList)
                    ms.sendMessage(msg);
                msgField.clear();
            }
        }
    }

    private void sendFigureToAll(Figure f) {
        if (figureSocketServer != null)
            for (FigureServer fs : getMainApp().figureServerList)
                fs.sendFigure(f);
    }

    private void sendMessageToAll(Message m) {
        if (messageSocketServer != null)
            for (MessageServer ms : getMainApp().messageServerList)
                ms.sendMessage(m);
    }
}
