package distance.learning.server.view;

import distance.learning.server.ServerMainApp;
import javafx.stage.Stage;

/**
 * Created by maxim_anatolevich on 18.04.16.
 */
public class Controller {
    protected ServerMainApp serverMainApp;
    protected Stage stage;

    public void setMainApp(ServerMainApp serverMainApp){
        this.serverMainApp = serverMainApp;
    }

    public ServerMainApp getMainApp(){
        return this.serverMainApp;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public Stage getStage(){
        return this.stage;
    }

    public void init(){

    }
}
