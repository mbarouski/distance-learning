package distance.learning.common;

import javafx.stage.Stage;

public abstract class BaseController<T> {
    private T mainApp;
    private Stage stage;

    public void setMainApp(T mainApp){
        this.mainApp = mainApp;
    }

    public T getMainApp(){
        return this.mainApp;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public Stage getStage(){
        return this.stage;
    }
}
