package distance.learning.client.util;

import distance.learning.client.ClientMainApp;
import distance.learning.client.view.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by maxim_anatolevich on 18.04.16.
 */
public class Util {
    public static Controller showWindow(String resource, String title, ClientMainApp clientMainApp) {
        BorderPane pane;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource(resource));
            pane = (BorderPane) loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(null);
            stage.setScene(scene);
            Controller controller = loader.getController();
            controller.setMainApp(clientMainApp);
            controller.setStage(stage);
            stage.showAndWait();
            return controller;
        } catch (Exception exc) {
            Util.showErrorMessage(exc.getMessage());
            return null;
        }
    }

    public static void showErrorMessage(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(null);
        alert.setTitle("Error");
        alert.setHeaderText("smth error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
