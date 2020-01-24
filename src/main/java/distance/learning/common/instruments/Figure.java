package distance.learning.common.instruments;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by maxim_anatolevich on 24.04.16.
 */
public abstract class Figure implements Serializable{
    protected int width;
    //protected Color color;
    protected double blue;
    protected double red;
    protected double green;
    protected double opacity;
    private String description;
    protected int x1;
    protected int y1;
    protected ArrayList<Point> points;
    public int countOfPoints;

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public Figure(int width, Color color)
    {
        this.width = width;
        blue = color.getBlue();
        red = color.getRed();
        green = color.getGreen();
        opacity = color.getOpacity();
        points = new ArrayList<Point>();
    }

    public void addPoint(Point p)
    {
        points.add(p);
    }

    public void setEndPoint(Point p)
    {
        if (points.size() < countOfPoints)
            addPoint(p);
        else
            points.set(points.size() - 1, p);
    }

    public void draw(GraphicsContext g){
        g.setLineWidth(width);
        g.setStroke(new Color(red, green, blue, opacity));
    }

    protected abstract boolean getParams();

    protected static int getMin(int a, int b)
    {
        return a < b ? a : b;
    }

    protected static int getMax(int a, int b)
    {
        return a > b ? a : b;
    }

    public int getCountOfPoints()
    {
        return points.size();
    }
}
