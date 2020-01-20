package distance.learning.client.view;

import distance.learning.client.ClientMainApp;
import javafx.stage.Stage;

/**
 * Created by maxim_anatolevich on 18.04.16.
 */
public class Controller {
    private ClientMainApp clientMainApp;
    private Stage stage;

    public void setMainApp(ClientMainApp clientMainApp){
        this.clientMainApp = clientMainApp;
    }

    public ClientMainApp getMainApp(){
        return this.clientMainApp;
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
