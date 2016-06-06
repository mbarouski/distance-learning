package distance.learning.server.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Created by maxim_anatolevich on 20.05.16.
 */
public class TextInputWindowController extends Controller {
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
            stage.close();
        }
    }
}
