package distance.learning.server.views;

import distance.learning.common.BaseController;
import distance.learning.server.ServerMainApp;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;

/**
 * Created by maxim_anatolevich on 18.04.16.
 */
public class ServerRootLayoutController extends BaseController<ServerMainApp> {
    @FXML
    private MenuItem menuFile;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public void menuSettings() {
        getMainApp().getFxmlLoader().loadWindow("Settings", "Server settings", getMainApp());
    }

    public void setMenuFileActive(boolean active) {
        menuFile.setDisable(!active);
    }

    @FXML
    public void menuExit() {
        System.exit(0);
    }
}
