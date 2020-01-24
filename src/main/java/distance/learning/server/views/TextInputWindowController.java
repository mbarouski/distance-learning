package distance.learning.server.views;

import distance.learning.common.BaseController;
import distance.learning.server.ServerMainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by maxim_anatolevich on 20.05.16.
 */
public class TextInputWindowController extends BaseController<ServerMainApp> {
    @FXML
    private TextArea textArea;

    private String text;

    public String getText(){
        return text;
    }

    @FXML
    public void initialize(){
        textArea.setFocusTraversable(true);
    }

    @FXML
    private void onKeyTyped(javafx.scene.input.KeyEvent event){
        //final KeyCombination kb = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
        if(event.isAltDown() && (event.getCode().ordinal() == 187)) {
            text = textArea.getText();
            getStage().close();
        }
    }
}
