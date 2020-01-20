package distance.learning.overall;

import distance.learning.client.util.Util;
import distance.learning.instrument.Figure;

import java.io.*;

/**
 * Created by maxim_anatolevich on 24.05.16.
 */
public class FigureUtil {
    public static byte[] getBytesFromFigure(Figure figure) {
        ObjectOutputStream oos;
        ObjectInputStream ois;
        FileOutputStream fos;
        FileInputStream fis;


        try {
            File f = new File("osi.txt");
            f.delete();
            f.createNewFile();
            fos = new FileOutputStream(f);
            fis = new FileInputStream(f);

            oos = new ObjectOutputStream(fos);
            ois = new ObjectInputStream(fis);
            oos.writeObject(figure);

            byte[] buffer = new byte[fis.available()];
            int read = fis.read(buffer, 0, buffer.length);
            fis.close();
            fos.close();
            oos.close();
            ois.close();

            return buffer;
        }
        catch(Exception exc){
            Util.showErrorMessage(exc.getMessage());
            return null;
        }
    }

    public static Figure getFigureFromBytes(byte[] buffer){
        ObjectOutputStream oos;
        ObjectInputStream ois;
        FileOutputStream fos;
        FileInputStream fis;

        Figure result = null;
        try {
            File f = new File("osi.txt");
            f.delete();
            f.createNewFile();
            fos = new FileOutputStream(f);
            fis = new FileInputStream(f);
            oos = new ObjectOutputStream(fos);
            ois = new ObjectInputStream(fis);
            fos.write(buffer);
            result = (Figure) ois.readObject();

            fis.close();
            fos.close();
            oos.close();
            ois.close();
        }
        catch(Exception exc){
            Util.showErrorMessage(exc.getMessage());
            return null;
        }
        return result;
    }
}
