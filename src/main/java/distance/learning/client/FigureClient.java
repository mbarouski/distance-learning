package distance.learning.client;

import distance.learning.common.instruments.Figure;
import distance.learning.common.FigureUtil;
import javafx.scene.canvas.GraphicsContext;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static distance.learning.common.ErrorWindowManager.showErrorMessage;

/**
 * Created by maxim_anatolevich on 24.05.16.
 */
public class FigureClient extends Thread {
    private ClientMainApp clientMainApp;
    private Socket clientSocket;
    private OutputStream os;
    private InputStream is;
    private GraphicsContext g;

    public FigureClient(ClientMainApp clientMainApp, GraphicsContext g, InetAddress serverIP, int figureServerPort){
        this.clientMainApp = clientMainApp;

        this.g = g;
        try {
            clientSocket = new Socket(serverIP, figureServerPort);
            is = clientSocket.getInputStream();
            os = clientSocket.getOutputStream();
        }

        catch (Exception exc){
            showErrorMessage(exc.getLocalizedMessage());
            clientSocket = null;
        }
        start();
    }

    public void run(){
        while(true){
            if(!clientMainApp.getRunning())
                stop();
            Figure f = getFigure();
            f.draw(g);
        }
    }

    private Figure getFigure(){
        try {
            while(is.available() == 0){}
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            Figure f = FigureUtil.getFigureFromBytes(buffer);
            clientMainApp.figureList.add(f);
            return FigureUtil.getFigureFromBytes(buffer);
        }
        catch(Exception exc){
            showErrorMessage(exc.getMessage());
        }
        return null;
    }
}
