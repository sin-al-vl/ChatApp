import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
/**
 * Created by Rogdan on 08.11.2015.
 */
public class CallListener {
    private String localNick;
    private InetSocketAddress localAddress;
    private volatile boolean isBusy;
    private String remoteNick, remoteAddress;
    private ServerSocket serverSocket;

    public CallListener(){
        this("Untitled");
    }

    public CallListener(String localNick){
        this.localNick = localNick;
        try {
            serverSocket = new ServerSocket(Constants.PORT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public CallListener(String localNick, String localIP){
        this(localNick);
        this.localAddress = new InetSocketAddress(localIP, Constants.PORT);
    }

    private String receiveRemoteNick(Connection connection) throws IOException{
   //     System.out.println("First receiving in call listener(remote nick): " + new Date().toLocaleString());
        Command c = connection.receive();
        return c.toString().substring((Constants.START_NICK_POSITION));
    }

    public Connection getConnection () throws IOException{
        Socket socket = serverSocket.accept();
        remoteAddress = serverSocket.getInetAddress().getCanonicalHostName();
        Connection connection = new Connection(socket);
        remoteNick = receiveRemoteNick(connection);

        return connection;
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

    public String getRemoteAddress(){
        return remoteAddress;
    }

    public static void main(String[] args) throws IOException {
        // Maybe this main for testing?
        CallListener c = new CallListener("Lammer");
        c.getConnection();
        System.out.println(c.getRemoteNick());
    }
}
