package distance.learning.server.view;

import distance.learning.server.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;

/**
 * Created by maxim_anatolevich on 18.04.16.
 */
public class ServerRootLayoutController extends Controller{
    @FXML
    private MenuItem menuFile;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public void menuSettings(){
        Util.showWindow("Settings.fxml", "Server settings", getMainApp());
    }

    public void setMenuFileActive(boolean active){
            menuFile.setDisable(!active);
    }

    @FXML
    public void menuExit(){
        System.exit(0);
    }
}
