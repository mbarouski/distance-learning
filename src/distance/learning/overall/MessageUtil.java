package distance.learning.overall;

import distance.learning.client.util.Util;

import java.io.*;

/**
 * Created by maxim_anatolevich on 24.05.16.
 */
public class MessageUtil {

    public static byte[] getBytesFromMessage(Message msg) {
        ObjectOutputStream oos;
        ObjectInputStream ois;
        FileOutputStream fos;
        FileInputStream fis;

        try {
            File f = new File("os.txt");
            f.delete();
            f.createNewFile();
            fos = new FileOutputStream(f);
            fis = new FileInputStream(f);

            oos = new ObjectOutputStream(fos);
            ois = new ObjectInputStream(fis);
            oos.writeObject(msg);

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

    public static Message getMessageFromBytes(byte[] buffer){
        ObjectOutputStream oos;
        ObjectInputStream ois;
        FileOutputStream fos;
        FileInputStream fis;

        Message result = null;
        try {
            File f = new File("os.txt");
            f.delete();
            f.createNewFile();
            fos = new FileOutputStream(f);
            fis = new FileInputStream(f);
            oos = new ObjectOutputStream(fos);
            ois = new ObjectInputStream(fis);
            fos.write(buffer);
            result = (Message) ois.readObject();

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
