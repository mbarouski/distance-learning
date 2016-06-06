package distance.learning.server;

import distance.learning.server.util.Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by maxim_anatolevich on 23.04.16.
 */
public class SoundSender{
    private DatagramSocket serverDatagramSocket = null;
    private ServerMainApp serverMainApp;
    //private int serverUdpPort = 8841;

    public SoundSender(ServerMainApp serverMainApp){
        this.serverMainApp = serverMainApp;
        try {
            serverDatagramSocket = new DatagramSocket();
        }
        catch(Exception exc){
            serverDatagramSocket = null;
            Util.showErrorMessage(exc.getMessage());
        }
    }


    public void sendSoundFrame(byte[] buffer, int length){
        DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, length, serverMainApp.getGroupIP(), 10000);
        try {
            if (serverDatagramSocket != null)
                serverDatagramSocket.send(datagramPacket);
        }
        catch(Exception exc){
            Util.showErrorMessage(exc.getMessage());
        }
    }
}
