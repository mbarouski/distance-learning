package distance.learning.server;
/**
 * Created by maxim_anatolevich on 06.04.16.
 */

import distance.learning.common.FXMLLoader;
import distance.learning.server.views.ServerMainWindowController;
import distance.learning.server.views.ServerRootLayoutController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static java.lang.System.exit;

public class ServerMainApp extends Application {
    public ServerRootLayoutController rootController;
    public ServerMainWindowController serverMainWindowController;

    public ArrayList<MessageServer> messageServerList = new ArrayList<>();
    public ArrayList<FigureServer> figureServerList = new ArrayList<>();

    public boolean microphoneState = false;
    private Mixer.Info mixerInfo = null;

    private final FXMLLoader fxmlLoader;

    public ServerMainApp() {
        this.fxmlLoader = new FXMLLoader(FXMLLoader.Mode.SERVER);
    }

    public void setMixerInfo(Mixer.Info mixerInfo) {
        this.mixerInfo = mixerInfo;
    }

    public Mixer.Info getMixerInfo() {
        return this.mixerInfo;
    }

    private boolean running = true;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getRunning() {
        return this.running;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Distance Learning Server");
        primaryStage.setFullScreen(true);
        primaryStage.setOnCloseRequest(windowEvent -> exit(0));

        var rootLayout = createRootLayout(primaryStage);
        showServerMainWindow(rootLayout, primaryStage);
    }

    public BorderPane createRootLayout(Stage primaryStage) {
        Pair<BorderPane, ServerRootLayoutController> pair = getFxmlLoader().load("ServerRootLayout");

        var rootLayout = pair.getKey();
        rootController = pair.getValue();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        rootController.setMainApp(this);
        return rootLayout;
    }

    private void showServerMainWindow(BorderPane rootLayout, Stage primaryStage) {
        Pair<BorderPane, ServerMainWindowController> pair = getFxmlLoader().load("ServerMainWindow");

        BorderPane serverMainWindow = pair.getKey();
        rootLayout.setCenter(serverMainWindow);

        primaryStage.show();

        serverMainWindowController = pair.getValue();
        serverMainWindowController.setMainApp(this);

        initParams();
    }

    public void initParams() {
        try {
            password = "777";
            serverIP = InetAddress.getByName("127.0.0.1");
            groupIP = InetAddress.getByName("239.255.255.255");
            messageServerPort = 8841;
            figureServerPort = 8842;
            mixerInfo = AudioSystem.getMixerInfo()[0];
        } catch (UnknownHostException exc) {
            throw new IllegalStateException(exc);
        }
    }

    //settings
    private String password = "777";
    private InetAddress serverIP;
    private InetAddress groupIP;
    public int messageServerPort;
    public int figureServerPort;
    public int audioServerPort;

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }
}
