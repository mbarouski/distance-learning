package distance.learning.client;

import distance.learning.client.views.ClientMainWindowController;
import distance.learning.common.BaseController;
import distance.learning.common.ResourceLoader;
import distance.learning.common.instruments.Figure;
import distance.learning.common.FXMLLoader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.net.InetAddress;
import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * Created by maxim_anatolevich on 23.04.16.
 */
public class ClientMainApp extends Application {
    private boolean running = true;
    public ArrayList<Figure> figureList = new ArrayList<>();
    public boolean speakerState = false;
    private Mixer.Info mixerInfo = null;

    private final FXMLLoader fxmlLoader;
    private final ResourceLoader resourceLoader;

    public ClientMainApp() {
        this.resourceLoader = new ResourceLoader();
        this.fxmlLoader = new FXMLLoader(FXMLLoader.Mode.CLIENT);
    }

    public void setMixerInfo(Mixer.Info mixerInfo) {
        this.mixerInfo = mixerInfo;
    }

    public Mixer.Info getMixerInfo() {
        return this.mixerInfo;
    }

    public ClientMainWindowController clientMainWindowController;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getRunning() {
        return this.running;
    }


    public static void main(String[] args) {
        launch(args);
    }

    //views
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(false);
        this.primaryStage.setTitle("Distance Learning Client");
        this.primaryStage.setOnCloseRequest(windowEvent -> exit(0));
        initRootLayout();
        showClientMainWindow();
    }

    public void initRootLayout() {
        Pair<BorderPane, BaseController<ClientMainApp>> pair = getFxmlLoader().load("ClientRootLayout");
        rootLayout = pair.getKey();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        BaseController<ClientMainApp> controller = pair.getValue();
        controller.setMainApp(this);
        primaryStage.show();
    }

    public void showClientMainWindow() {
        Pair<BorderPane, BaseController<ClientMainApp>> pair = getFxmlLoader().load("ClientMainWindow");
        BorderPane clientMainWindow = pair.getKey();
        rootLayout.setCenter(clientMainWindow);
        clientMainWindowController = (ClientMainWindowController) pair.getValue();
        clientMainWindowController.setMainApp(this);
        initParams();
    }

    public void initParams() {
        try {
            password = "777";
            name = "Client";
            serverIP = InetAddress.getByName("127.0.0.1");
            groupIP = InetAddress.getByName("239.255.255.255");
            messageServerPort = 8841;
            figureServerPort = 8842;
            mixerInfo = AudioSystem.getMixerInfo()[0];
        } catch (Exception exc) {
        }
    }

    public String password;
    public String name;
    private InetAddress serverIP;
    private InetAddress groupIP;
    public int messageServerPort;
    public int figureServerPort;

    public void setServerIP(String temp) {
        try {
            serverIP = InetAddress.getByName(temp);
        } catch (Exception exc) {
            serverIP = null;
        }
    }

    public InetAddress getServerIP() {
        return serverIP;
    }

    public void setGroupIP(String temp) {
        try {
            groupIP = InetAddress.getByName(temp);
        } catch (Exception exc) {
            groupIP = null;
        }
    }

    public InetAddress getGroupIP() {
        return groupIP;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
