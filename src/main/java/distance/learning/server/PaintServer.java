package distance.learning.server;

import distance.learning.server.util.Util;

import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by maxim_anatolevich on 22.05.16.
 */
public class PaintServer {
    private ServerSocket serverSocket = null;

    public PaintServer(InetAddress serverAddress, int serverPort){
        try{
            serverSocket = new ServerSocket(serverPort, 0, serverAddress);
        }
        catch(Exception exc){
            Util.showErrorMessage(exc.getMessage());
        }

    }
}
