package distance.learning.server;

import distance.learning.client.util.Util;
import distance.learning.overall.Message;
import distance.learning.overall.MessageUtil;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by maxim_anatolevich on 22.05.16.
 */
public class MessageServer extends Thread {
    public static Object syn = new Object();
    private ServerSocket serverSocket;
    private ServerMainApp serverMainApp;
    private Socket clientSocket;
    private OutputStream os;
    private ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private FileInputStream fis;

    public static Object synMsg = new Object();

    private TextArea msgArea;
    public boolean connected;

    public MessageServer(ServerMainApp serverMainApp, TextArea msgArea, Socket clientSocket) {
        this.serverMainApp = serverMainApp;
        this.clientSocket = clientSocket;
        this.msgArea = msgArea;
        try {
            this.clientSocket.setReuseAddress(true);
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();
            if (checkConnection()) {
                start();
                connected = true;
            } else {
                connected = false;
            }
        } catch (Exception exc) {
            Util.showErrorMessage(exc.getMessage());
        }
    }

    private Message getMessage() {
        try {
            while (is.available() == 0) {
            }
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            Message m = MessageUtil.getMessageFromBytes(buffer);
            if (m.getType() == Message.COMMAND) {
                handleCommand(m.getData());
                return null;
            } else
                return m;
        } catch (Exception exc) {
            Util.showErrorMessage(exc.getMessage());
        }
        return null;
    }

    public boolean checkConnection() {
        try {
            Message message = getMessage();
            if (message.getType() == Message.PASSWORD) {
                if (message.getData().equals(serverMainApp.getPassword())) {
                    sendMessage(new Message(Message.REPLY, null, true));
                    return true;
                } else {
                    sendMessage(new Message(Message.REPLY, null, false));
                }
            }
        } catch (Exception exc) {
            Util.showErrorMessage(exc.getMessage());
        }
        return false;
    }

    public void run() {
        while (true) {
            if (!serverMainApp.getRunning())
                break;
            Message message = getMessage();
            if (message != null)
                synchronized (synMsg) {
                    msgArea.appendText(message.getData() + "\n");
                    for (MessageServer ms : serverMainApp.messageServerList)
                        ms.sendMessage(message);
                }

        }
        System.out.println("messageserver stopped");
    }

    public void sendMessage(Message message) {
        try {
            os.write(MessageUtil.getBytesFromMessage(message));
        } catch (Exception exc) {

        }
    }

    public void handleCommand(String data) {
        String command = data.split(":")[0];
        String commandData = data.split(":")[1];
        switch (command) {
            case "disconnect":
                break;

        }

    }
}
