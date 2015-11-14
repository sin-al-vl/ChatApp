import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Александр on 01.11.2015.
 */
public class Connection  {
    private Socket socket;

    public Connection(Socket socet) {
        this.socket = socet;
    }


    public void sendNickHello(String nick) throws IOException {
        socket.getOutputStream().write((Constants.ChatApp_VERSION + " user " + nick + "\n").getBytes(Constants.CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void sendNickBusy(String nick) throws IOException {
        socket.getOutputStream().write((Constants.ChatApp_VERSION + " user " + nick + " busy\n").getBytes(Constants.CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void accept() throws IOException {
        socket.getOutputStream().write("Accepted\n".getBytes(Constants.CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void reject() throws IOException {
        socket.getOutputStream().write("Rejected\n".getBytes(Constants.CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void sendMessage(String message) throws IOException {
        socket.getOutputStream().write("Message\n".getBytes(Constants.CHARSET_NAME));
        socket.getOutputStream().write((message + "\n").getBytes(Constants.CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public void disconnect() throws IOException {
        socket.getOutputStream().write("Disconnect\n".getBytes(Constants.CHARSET_NAME));
        socket.getOutputStream().flush();
    }

    public Command receive() throws IOException {
        Scanner in = new Scanner(new BufferedInputStream(socket.getInputStream()));
        String line = in.nextLine();


        if(line.contains(" ") && line.startsWith(Constants.ChatApp_VERSION)){

            Boolean isBusy = line.toLowerCase().endsWith(" busy");
            String nick;

            if (isBusy)
                nick = line.substring(line.indexOf(" user ") + 1, line.indexOf(" busy"));
            else
                nick = line.substring(line.lastIndexOf(" ") + 1, line.length());

            return new NickCommand(Constants.ChatApp_VERSION, nick, isBusy);

        } else if(COMMAND_HASH_MAP.containsKey(line.toLowerCase())) {

            if(line.toUpperCase().equals(Command.CommandType.MESSAGE.toString())) {

                line = in.nextLine();
                return new MessageCommand(line);

            } else
                return COMMAND_HASH_MAP.get(line.toLowerCase());

        } else return null;

    }

    static final HashMap<String, Command> COMMAND_HASH_MAP = new HashMap<String, Command>(){{
        put("accepted", new Command(Command.CommandType.ACCEPT));
        put("disconnect", new Command(Command.CommandType.DISCONNECT));
        put("message", new Command(Command.CommandType.MESSAGE));
        put(Constants.ChatApp_VERSION, new Command(Command.CommandType.NICK));
        put("rejected", new Command(Command.CommandType.REJECT));
    }};
}