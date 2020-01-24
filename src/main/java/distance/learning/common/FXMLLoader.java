package distance.learning.common;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class FXMLLoader {
    private Mode mode;

    public FXMLLoader(Mode mode) {
        this.mode = mode;
    }

    public <L, C> Pair<L, C> load(String name) {
        try {
            var url = getClass().getResource(makePath(name));
            var loader = new javafx.fxml.FXMLLoader(url);
            return new Pair<>(loader.load(), loader.getController());
        } catch (IOException exc) {
            throw new IllegalStateException(exc);
        }
    }


    public <T> BaseController<T> loadWindow(String resource, String title, T mainApp) {
        Pair<BorderPane, BaseController<T>> pair = load(resource);

        BorderPane pane = pair.getKey();

        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(null);
        stage.setScene(scene);

        BaseController<T> controller = pair.getValue();
        controller.setMainApp(mainApp);
        controller.setStage(stage);

        stage.showAndWait();

        return controller;
    }

    private String makePath(String name) {
        return "/distance/learning/" + Mode.SERVER.name().toLowerCase() + "/" + name + ".fxml";
    }

    public static enum Mode {
        SERVER,
        CLIENT
    }
}
