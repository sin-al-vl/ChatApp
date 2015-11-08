import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Rogdan on 08.11.2015.
 */
public class CallListener {
    private String localNick;
    private InetSocketAddress localAddress;
    private boolean isBusy;

    public CallListener(){
        this("Untitled");
    }

    public CallListener(String localNick){
        this.localNick = localNick;
    }

    public CallListener(String localNick, String localIP){
        this(localNick);
        this.localAddress = new InetSocketAddress(localIP, Constants.PORT);
    }

    public Connection getConnection () throws IOException{
        ServerSocket serverSocket = new ServerSocket(Constants.PORT);  //I'm not sure it's right
        Socket socket = serverSocket.accept();
        if (isBusy) {
            /*There must be code which get information about subscriber, who is colling*/
            return null;
        }
        else{
            isBusy = true;
            return new Connection(socket);
        }
    }

    public String getLocalNick(){
        return localNick;
    }

    public boolean isBusy(){
        return isBusy;
    }

    public void setBusy(boolean isBusy){
        this.isBusy = isBusy;
    }

    public SocketAddress getListenAddress (){
        return localAddress;
    }

    public void setLocalNick(String localNick){
        this.localNick = localNick;
    }

    public void setListenAddress(InetSocketAddress listenAddress){
        localAddress = listenAddress;
    }

    public String toString(){
        return localNick + " " + localAddress.getHostString(); //Don`t sure
    }

    public static void main(String[] args) {
        //Maybe this main for testing?
    }
}
