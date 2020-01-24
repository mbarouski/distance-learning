package distance.learning.common.instruments;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by maxim_anatolevich on 24.04.16.
 */
public class Ellipse extends Figure implements Serializable{
    protected int a;
    protected int b;

    public Ellipse(int width, Color color){
        super(width, color);
        setDescription("Ellipse");
        countOfPoints = 2;
    }

    protected boolean getParams()
    {
        if (points.size() != countOfPoints)
            return false;
        x1 = getMin(points.get(0).x, points.get(1).x);
        y1 = getMin(points.get(0).y, points.get(1).y);
        a = getMax(points.get(0).x, points.get(1).x) -getMin(points.get(0).x, points.get(1).x);
        b = getMax(points.get(0).y, points.get(1).y) - getMin(points.get(0).y, points.get(1).y);
        return true;
    }

    public void draw(GraphicsContext g)
    {
        super.draw(g);
        if (getParams())
            g.strokeOval(x1, y1, a, b);
    }
}
