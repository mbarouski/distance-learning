package distance.learning.server;

import distance.learning.client.util.Util;
import distance.learning.instrument.Figure;
import distance.learning.overall.FigureUtil;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.net.Socket;

/**
 * Created by maxim_anatolevich on 24.05.16.
 */
public class FigureServer{
    private Socket clientSocket;
    private ServerMainApp serverMainApp;
    private GraphicsContext g;
    private OutputStream os;
    private InputStream is;

    public FigureServer(ServerMainApp serverMainApp, GraphicsContext g, Socket clientSocket){
        this.serverMainApp = serverMainApp;
        this.clientSocket = clientSocket;

        this.g = g;

        try{
            this.clientSocket.setReuseAddress(true);
            this.clientSocket.setSendBufferSize(200 * 1024);
            os = clientSocket.getOutputStream();

            is = clientSocket.getInputStream();
            for(Figure f: serverMainApp.serverMainWindowController.figureList)
                sendFigure(f);
        }
        catch (Exception exc){
            Util.showErrorMessage(exc.getMessage());
        }
    }



    public void sendFigure(Figure figure){
        try {
            os.write(FigureUtil.getBytesFromFigure(figure));
        }
        catch (Exception exc){
            //Util.showErrorMessage(exc.getMessage());
        }
    }
}
