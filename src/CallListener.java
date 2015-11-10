import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * Created by Rogdan on 08.11.2015.
 */
public class CallListener {
    private String localNick;
    private InetSocketAddress localAddress;
    private boolean isBusy;
    private String remoteNick;

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
        System.out.println("Accepted");

        Connection connection = new Connection(socket);
        Scanner in = new Scanner(new BufferedInputStream(socket.getInputStream()));

         new Thread(new Runnable() {
             @Override
             public void run() {
                 remoteNick = "Untitled";

                 if ((in.next() + " " + in.next()).equals(Constants.ChatApp_VERSION)) {
                     in.next();
                     remoteNick = in.nextLine();
                 }
             }
         }).start();

        if (isBusy) {
            connection.sendNickBusy(localNick);
            return null;
        }
        else{
            isBusy = true;
            connection.sendNickHello(localNick);
            return connection;
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

    public String getRemoteNick(){
        return remoteNick;
    }

    public static void main(String[] args) throws IOException{
        // Maybe this main for testing?
       CallListener c = new CallListener("Lammer");
       c.getConnection();
    }
}
