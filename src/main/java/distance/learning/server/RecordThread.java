package distance.learning.server;

import javax.sound.sampled.*;

import static distance.learning.common.ErrorWindowManager.showErrorMessage;

/**
 * Created by maxim_anatolevich on 18.04.16.
 */
public class RecordThread extends Thread{
    private ServerMainApp serverMainApp;
    private SoundSender soundSender;
    private boolean stopFlag = false;

    public void setMainApp(ServerMainApp serverMainApp){
        this.serverMainApp = serverMainApp;
    }

    public ServerMainApp getMainApp(){
        return this.serverMainApp;
    }

    private TargetDataLine microphoneLine;
    //

    public RecordThread(TargetDataLine microphoneLine, ServerMainApp serverMainApp){
        this.microphoneLine = microphoneLine;
        this.serverMainApp = serverMainApp;
        soundSender = new SoundSender(serverMainApp/*, serverMainApp.getServerIP(), serverMainApp.audioServerPort*/);
    }

    public void setStopFlag(){
        stopFlag = true;
    }

    public void run(){
        byte[] buffer = new byte[4096];
        try{
            while(!stopFlag){

                int read = microphoneLine.read(buffer, 0, buffer.length);
                if(getMainApp().microphoneState) {
                    if (read > 0) {
                        soundSender.sendSoundFrame(buffer, read);
                    }
                }
            }
            System.out.println("recordthread stopped");
        }
        catch (Exception exc){
            showErrorMessage(exc.getMessage());
        }
    }
}