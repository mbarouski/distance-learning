package distance.learning.common.instruments;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by maxim_anatolevich on 20.05.16.
 */
public class Polyline extends Figure implements Serializable{
    public Polyline(int width, Color color)
    {
        super(width, color);
        setDescription("Line(Pencil/Polyline)");
        countOfPoints = Integer.MAX_VALUE;
    }

    @Override
    protected boolean getParams()
    {
        if (points.size() < 2)
            return false;
        return true;
    }

    @Override
    public void draw(GraphicsContext g)
    {
        super.draw(g);
        if (getParams())
            for (int i = 0; i < points.size() - 1; i++)
                g.strokeLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
    }
}
