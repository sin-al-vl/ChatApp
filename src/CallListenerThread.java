import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 08.11.2015.
 */
public class CallListenerThread implements Runnable {
    private CallListener callListener;
    private volatile boolean isClose;
    private Caller.CallStatus callStatus;
    private Thread t;
    private volatile boolean  isReceive, flag;
    private Connection lastConnection;  //for accept and decline when busy

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public CallListenerThread(){
        t = new Thread(this);
        isClose = false;
        t.start();
    }

    public CallListenerThread(CallListener callListener){
        this.callListener = callListener;
        t = new Thread(this);
        isClose = false;
        t.start();
    }

    public SocketAddress getListenAddress(){
        return callListener.getListenAddress();
    }

    public String getLocalNick (){
        return callListener.getLocalNick();
    }

    public SocketAddress getRemoteAddress(){
        return null; //where it take?
    }

    public boolean isBusy (){
        return callListener.isBusy();
    }

    public void setBusy (boolean isBusy){
        callListener.setBusy(isBusy);
    }

    public void setListenAddress(InetSocketAddress newAddress){
        callListener.setListenAddress(newAddress);
    }

    public void setLocalNick (String newNick){
        callListener.setLocalNick(newNick);
    }

    public void run() {
        while (!isClose) {
            try {
                System.out.println("Before");
                Connection connection = callListener.getConnection();
                System.out.println("Get");
                myObservable.notifyObservers(callListener);
                waitAnswer();
                System.out.println("continued");

                if (!isReceive) {
                    System.out.println("False");
                    if (callListener.isBusy()) {
                        connection.sendNickBusy(callListener.getLocalNick());
                        callStatus = Caller.CallStatus.BUSY;
                    }
                    else {
                        connection.sendNickHello(callListener.getLocalNick());
                        connection.reject();
                        callStatus = Caller.CallStatus.REJECTED;
                    }
                }
                else{
                    connection.sendNickHello(callListener.getLocalNick());
                    if (callListener.isBusy())
                        lastConnection.disconnect();
                    else
                        callListener.setBusy(true);

                    System.out.println("OK");
                    callStatus = Caller.CallStatus.OK;        //Success connection

                    connection.accept();
                    lastConnection = connection;
                    myObservable.notifyObservers(connection);

                    CommandListenerThread commandListenerThread = new CommandListenerThread(connection);
                    commandListenerThread.addObserver(MainForm.window);
                }

            } catch (IOException e) {
                callStatus = Caller.CallStatus.NOT_ACCESSIBLE;  //Say observers about crashed connection?? I`m not sure
            }
        }
    }

    public void stop(){
        isClose = true;
    }

    public void setReceive(boolean isReceive){
        this.isReceive = isReceive;
    }

    public void suspend(){
        flag = false;
    }

    private synchronized void waitAnswer(){
        if (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(t + " Thread interrupted: " + e);
            }
        }
    }

    public synchronized void resume(){
        flag = true;
        notify();
    }

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public String getRemoteNick (){
        return callListener.getRemoteNick();
    }
}
