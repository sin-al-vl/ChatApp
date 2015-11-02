import java.io.IOException;
import java.net.Socket;

/**
 * Created by Александр on 01.11.2015.
 */
public class Connection  {
    private Socket socket;

    public Connection(Socket socet) {
        this.socket = socet;
    }


    public void sendNickHello(String nick) throws IOException {
        socket.getOutputStream().write("User\n".getBytes());
        socket.getOutputStream().write(("ChatApp 2015 user " + nick + "\n").getBytes());

    }

    public void sendNickBusy(String nick) throws IOException {
        socket.getOutputStream().write("User\n".getBytes());
        socket.getOutputStream().write(("ChatApp 2015 user " + nick + " busy\n").getBytes());
    }

    public void accept() throws IOException {
        socket.getOutputStream().write("Accepted\n".getBytes());
    }

    public void reject() throws IOException {
        socket.getOutputStream().write("Rejected\n".getBytes());
    }

    public void sendMessage(String message) throws IOException {
        socket.getOutputStream().write("Message\n".getBytes());
        socket.getOutputStream().write(message.getBytes());
    }

    public void disconnect() throws IOException {
        socket.getOutputStream().write("Disconnect\n".getBytes());
    }

    public Command receive() throws IOException {

        String line = "";
        int i;
        while (true) {
            if((i = socket.getInputStream().read()) == '\n') {
                if (Command.CommandType.contains(line.toUpperCase()))
                    break;
                else
                    line = "";
            } else
                line += (char) i;
        }

        if(Command.CommandType.valueOf(line.toUpperCase()).equals(Command.CommandType.MESSAGE)) {

            line = "";
            while (true) {
                if((i = socket.getInputStream().read()) == '\n')
                    break;
                else
                    line += (char) i;
            }

            return new MessageCommand(line);

        } else if(Command.CommandType.valueOf(line.toUpperCase()).equals(Command.CommandType.NICK)) {

            line = "";
            while (true) {
                if((i = socket.getInputStream().read()) == '\n')
                    break;
                else
                    line += (char) i;
            }

            String version = line.substring(0, line.toLowerCase().indexOf(" user "));
            Boolean busy = line.toLowerCase().endsWith(" busy");
            String nick;

            if (busy)
                nick = line.substring(line.toLowerCase().indexOf(" user ", line.toLowerCase().indexOf(" busy")));
            else
                nick = line.substring(line.toLowerCase().indexOf(" user ", line.indexOf("\n")));

            return new NickCommand(version, nick, busy);

        } else

            return new Command(Command.CommandType.valueOf(line.toUpperCase()));

    }
}