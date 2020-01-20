package distance.learning.server;
/**
 * Created by maxim_anatolevich on 06.04.16.
 */

import distance.learning.server.util.Util;
import distance.learning.server.view.ServerMainWindowController;
import distance.learning.server.view.ServerRootLayoutController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerMainApp extends Application {


    public ArrayList<MessageServer> messageServerList = new ArrayList<>();
    public ArrayList<FigureServer> figureServerList = new ArrayList<>();
    //microphones
    public boolean microphoneState = false;
    public ServerRootLayoutController rootController;
    public ServerMainWindowController serverMainWindowController;
    private Mixer.Info mixerInfo = null;

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

    //views
    private Stage primaryStage;
    private BorderPane rootLayout;

    private void stopAllThreads() {
        try {
            //serverMainWindowController.stopThreads();
            System.exit(0);
        } catch (Exception exc) {
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Distance Learning Server");
        primaryStage.setFullScreen(true);
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                //setRunning(false);
                stopAllThreads();
                try {
                    //Thread.sleep(4000);
                } catch (Exception exc) {
                }
            }
        });
        initRootLayout();
        showServerMainWindow();
    }

    public void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ServerMainApp.class.getResource("view/ServerRootLayout.fxml"));
        try {
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            rootController = loader.getController();

            rootController.setMainApp(this);
        } catch (Exception exc) {
        }
    }

    public void showServerMainWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ServerMainApp.class.getResource("view/ServerMainWindow.fxml"));
        try {
            BorderPane serverMainWindow = (BorderPane) loader.load();
            rootLayout.setCenter(serverMainWindow);

            primaryStage.show();
            serverMainWindowController = loader.getController();

            serverMainWindowController.setMainApp(this);
        } catch (Exception exc) {
            Util.showErrorMessage(exc.getMessage());
        }
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
        } catch (Exception exc) {
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
}
