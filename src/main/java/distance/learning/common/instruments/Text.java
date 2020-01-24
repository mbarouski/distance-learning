package distance.learning.common.instruments;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by maxim_anatolevich on 20.05.16.
 */
public class Text extends Figure implements Serializable {
    protected String text = null;

    public Text(int width, Color color, String text) {
        super(1, color);
        setDescription("Text: " + text);
        this.text = text;
        countOfPoints = 1;
    }

    @Override
    public void draw(GraphicsContext g) {
        super.draw(g);
        if (getParams()) {
            g.strokeText(text, x1, y1);
        }
    }

    @Override
    protected boolean getParams() {
        if (points.size() != countOfPoints)
            return false;
        x1 = points.get(0).x;
        y1 = points.get(0).y;
        return true;
    }
}
