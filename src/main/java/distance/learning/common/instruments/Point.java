package distance.learning.common.instruments;

import java.io.Serializable;

/**
 * Created by maxim_anatolevich on 24.04.16.
 */
public class Point implements Serializable{
    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
