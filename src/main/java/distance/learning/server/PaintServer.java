package distance.learning.server;

import java.net.InetAddress;
import java.net.ServerSocket;

import static distance.learning.common.ErrorWindowManager.showErrorMessage;

/**
 * Created by maxim_anatolevich on 22.05.16.
 */
public class PaintServer {
    private ServerSocket serverSocket = null;

    public PaintServer(InetAddress serverAddress, int serverPort) {
        try {
            serverSocket = new ServerSocket(serverPort, 0, serverAddress);
        } catch (Exception exc) {
            showErrorMessage(exc.getMessage());
        }

    }
}
