package distance.learning.client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static distance.learning.common.ErrorWindowManager.showErrorMessage;

/**
 * Created by maxim_anatolevich on 23.04.16.
 */
public class SoundReceiver extends Thread{
    private ClientMainApp clientMainApp;

    public void setMainApp(ClientMainApp clientMainApp){
        this.clientMainApp = clientMainApp;
    }

    public ClientMainApp getMainApp(){
        return this.clientMainApp;
    }

    private MulticastSocket clientSocket = null;
    private PlayThread playThread = null;

    public SoundReceiver(ClientMainApp clientMainApp){
        this.clientMainApp = clientMainApp;
        playThread = new PlayThread();
        try{
            clientSocket = new MulticastSocket(10000);
            clientSocket.joinGroup(InetAddress.getByName("239.255.255.255"));
        }
        catch(Exception exc){
            showErrorMessage(exc.getMessage());
        }
    }

    public void run(){
        byte[] buffer = new byte[4096];
        int read;
        DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
        while(true){
            if (!getMainApp().getRunning())
                break;
            try {
                clientSocket.receive(datagramPacket);
                buffer = datagramPacket.getData();
                read = datagramPacket.getLength();
                if(read > 0)
                    if(getMainApp().speakerState)
                        playThread.play(buffer, read);
            }
            catch(Exception exc){
                showErrorMessage(exc.getMessage());
            }
        }
    }
}
