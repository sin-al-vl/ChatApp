import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

/**
 * Created by Rogdan on 02.11.2015.
 */
public class Caller {
    private String localNick;
    private SocketAddress remoteAddress;
    private String remoteNick;
    private CallStatus status;
    private Socket socket;

    public Caller (String localNick){
        this.localNick = localNick;
    }

    public Caller(){
        this("Untitled");                       // How to create socket in this constructor and in the previous?
    }

    public Caller (String localNick, String ip){

        this(localNick);

        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            status = CallStatus.NOT_ACCESSIBLE;  //or CallStatus.NO_SERVICE ???
            e.printStackTrace();                 //Wrong ip. User should check it
        }

        remoteAddress = socket.getRemoteSocketAddress();
    }

    public Caller (String localNick, SocketAddress remoteAddress){

        this(localNick);
        this.remoteAddress = remoteAddress;

        try {
            socket = new Socket(String.valueOf(remoteAddress), port);
        } catch (IOException e) {
            status = CallStatus.NOT_ACCESSIBLE;             //or CallStatus.NO_SERVICE ???
            e.printStackTrace();                            //Wrong remoteAddress. User should check it
        }
    }

    public Connection call() throws IOException{            //supposedly method must be called in constructors
        Connection connection = new Connection(socket);
        connection.sendNickHello(localNick);
        Command command = connection.receive();             //receive NickCommand

        if(command.getClass().equals(NickCommand.class)){   //If receive exactly NickCommand

            remoteNick = ((NickCommand)command).getNick();

            if(((NickCommand)command).isBusy()) {
                status = CALL_STATUS_HASH_MAP.get(Connection.ChatApp_VERSION);
            } else {
                command = connection.receive();             //if NOT busy then receive ACCEPT or REJECT
                status = CALL_STATUS_HASH_MAP.get(command.toString());
            }

            if(status.equals(CallStatus.OK))                //Only if Connection is accepted then return it
                return connection;
            else
                return null;

        } else {
            status = CallStatus.NOT_ACCESSIBLE;             //or CallStatus.NO_SERVICE ???
            return null;
        }
    }

    public void setLocalNick(String localNick) {
        this.localNick = localNick;
    }

    public void setRemoteAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public CallStatus getStatus() {
        return status;
    }

    public String getRemoteNick() {
        return remoteNick;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public String getLocalNick() {
        return localNick;
    }

    public String toString(){
        return String.valueOf(status);                      //not sure what exactly this method returns
    }

    static enum CallStatus {
        BUSY, NO_SERVICE, NOT_ACCESSIBLE, OK, REJECTED
    }

    static final int port = 28411;
    static final HashMap<String, CallStatus> CALL_STATUS_HASH_MAP = new HashMap<String, CallStatus>(){{
        put(Connection.ChatApp_VERSION, CallStatus.BUSY);
//        put( , CallStatus.NO_SERVICE);
//        put( , CallStatus.NOT_ACCESSIBLE);
        put(Command.CommandType.ACCEPT.toString(), CallStatus.OK);
        put(Command.CommandType.REJECT.toString(), CallStatus.REJECTED);
    }};
}
