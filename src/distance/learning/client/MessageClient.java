package distance.learning.client;


import distance.learning.client.util.Util;
import distance.learning.overall.Message;
import distance.learning.overall.MessageUtil;
import distance.learning.server.MessageServer;
import javafx.scene.control.TextArea;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by maxim_anatolevich on 22.05.16.
 */
public class MessageClient extends Thread{
    private ClientMainApp clientMainApp;
    private Socket clientSocket;
    private OutputStream os;
    private InputStream is;

    private TextArea msgArea;
    //private XMLDecoder xis;
    //private XMLEncoder xos;
    public boolean connected;

    public void sendMessage(Message message){
        if(clientSocket != null) {
            try {
                os.write(MessageUtil.getBytesFromMessage(message));
            } catch (Exception exc) {
                Util.showErrorMessage(exc.getMessage());
            }
        }
    }

    public void disconnect(){
        clientSocket = null;
    }

    public MessageClient(ClientMainApp clientMainApp, TextArea msgArea, InetAddress serverIP, int messegeServerPort){
        this.clientMainApp = clientMainApp;
        this.msgArea = msgArea;
        try {
            clientSocket = new Socket(serverIP, messegeServerPort);
            //os.flush();
            //clientSocket.setSoTimeout(2000);

            is = clientSocket.getInputStream();
            os = clientSocket.getOutputStream();
            connect();
        }

        catch (Exception exc){
            Util.showErrorMessage(exc.getLocalizedMessage());
            clientSocket = null;
        }
    }

    private void connect(){
        try {
            //oos.writeObject(new Message(Message.PASSWORD, clientMainApp.password, true));
            os.write(MessageUtil.getBytesFromMessage(new Message(Message.PASSWORD, clientMainApp.password, true)));
            if(getMessage().getSuccess()){
                connected = true;
                start();
            }
            else {
                connected = false;
            }
        }
        catch(Exception exc){
            Util.showErrorMessage(exc.getMessage());
        }
    }


    @Override
    public void run(){
        while(true){
            if(!clientMainApp.getRunning())
                break;
            Message message = getMessage();
            if (message != null)
                msgArea.appendText(message.getData() + "\n");
        }
    }

    private Message getMessage(){
        try {
            while(is.available() == 0){}
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            Message m = MessageUtil.getMessageFromBytes(buffer);
            if(m.getType() == Message.COMMAND){
                handleCommand(m.getData());
                return null;
            }
            return MessageUtil.getMessageFromBytes(buffer);
        }
        catch(Exception exc){
            Util.showErrorMessage(exc.getMessage());
        }
        return null;
    }

    public void handleCommand(String data){
        String commandData = "";
        String[] temp = data.split(":");
        String command =temp[0];
        if(temp.length > 1)
            commandData = data.split(":")[1];
        switch(command){
            case "del":
                clientMainApp.figureList.remove(Integer.parseInt(commandData));
                clientMainApp.clientMainWindowController.drawAll();
                break;
            case "clear":
                clientMainApp.clientMainWindowController.clear();
                break;

        }

    }

}
