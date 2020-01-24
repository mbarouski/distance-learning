package distance.learning.common;

import javafx.scene.control.Alert;

public class ErrorWindowManager {
    public static void showErrorMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(null);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
