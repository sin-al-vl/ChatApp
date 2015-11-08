import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Александр on 01.11.2015.
 */
public class Connection  {
    private Socket socket;

    public Connection(Socket socet) {
        this.socket = socet;
    }


    public void sendNickHello(String nick) throws IOException {
        socket.getOutputStream().write((ChatApp_VERSION + " user " + nick + "\n").getBytes(CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void sendNickBusy(String nick) throws IOException {
        socket.getOutputStream().write((ChatApp_VERSION + " user " + nick + " busy\n").getBytes(CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void accept() throws IOException {
        socket.getOutputStream().write("Accepted\n".getBytes(CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void reject() throws IOException {
        socket.getOutputStream().write("Rejected\n".getBytes(CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void sendMessage(String message) throws IOException {
        socket.getOutputStream().write("Message\n".getBytes(CHARSET_NAME));
        socket.getOutputStream().write((message + "\n").getBytes(CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void disconnect() throws IOException {
        socket.getOutputStream().write("Disconnect\n".getBytes(CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public Command receive() throws IOException {

        String line = "";
        int i = 0;
        while (i != -1) {
            if((i = socket.getInputStream().read()) != '\n')
                line += (char) i;
            else
                break;
        }


        if(line.contains(" ") && line.startsWith(ChatApp_VERSION)){

            Boolean isBusy = line.toLowerCase().endsWith(" busy");
            String nick;

            if (isBusy)
                nick = line.substring(line.indexOf(" user ") + 1, line.indexOf(" busy"));
            else
                nick = line.substring(line.lastIndexOf(" ") + 1, line.length());

            return new NickCommand(ChatApp_VERSION, nick, isBusy);

        } else if(COMMAND_HASH_MAP.containsKey(line.toLowerCase())) {

            if(line.toUpperCase().equals(Command.CommandType.MESSAGE.toString())) {

                line = "";
                while (true) {
                    if((i = socket.getInputStream().read()) == '\n')
                        break;
                    else
                        line += (char) i;
                }
                return new MessageCommand(line);

            } else
                return COMMAND_HASH_MAP.get(line.toLowerCase());

        } else return null;

    }

    static final String ChatApp_VERSION = "ChatApp 2015";
    static final String CHARSET_NAME = "UTF-8";
    static final HashMap<String, Command> COMMAND_HASH_MAP = new HashMap<String, Command>(){{
        put("accepted", new Command(Command.CommandType.ACCEPT));
        put("disconnect", new Command(Command.CommandType.DISCONNECT));
        put("message", new Command(Command.CommandType.MESSAGE));
        put(ChatApp_VERSION, new Command(Command.CommandType.NICK));
        put("rejected", new Command(Command.CommandType.REJECT));
    }};
}