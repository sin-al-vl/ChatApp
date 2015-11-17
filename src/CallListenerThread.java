import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 08.11.2015.
 */
public class CallListenerThread extends Observable implements Runnable {
    private CallListener call;
    private volatile boolean isClose;
    private Caller.CallStatus callStatus;
    private Thread t;

    public CallListenerThread(){
        t = new Thread(this);
        isClose = false;
        t.start();
    }

    public CallListenerThread(CallListener call){
        this.call = call;
        t = new Thread(this);
        isClose = false;
        t.start();
    }

    public SocketAddress getListenAddress(){
        return call.getListenAddress();
    }

    public String getLocalNick (){
        return call.getLocalNick();
    }

    public SocketAddress getRemoteAddress(){
        return null; //where it take?
    }

    public boolean isBusy (){
        return call.isBusy();
    }

    public void setBusy (boolean isBusy){
        call.setBusy(isBusy);
    }

    public void setListenAddress(InetSocketAddress newAddress){
        call.setListenAddress(newAddress);
    }

    public void setLocalNick (String newNick){
        call.setLocalNick(newNick);
    }

    public void run(){
        while (!isClose){
            try {
               Connection c = call.getConnection();
                if (c == null)
                     callStatus = Caller.CallStatus.valueOf("BUSY");    //Sey observers: you are busy
                else
                     callStatus = Caller.CallStatus.valueOf("OK");        //Success connection

            } catch (IOException e){
                     callStatus = Caller.CallStatus.valueOf("REJECTED");  //Say observers about crashed connection?? I`m not sure
            }


            setChanged();
            notifyObservers();

        }
    }

    public void stop(){
        isClose = true;
    }
}
