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
        socket.getOutputStream().write(("ChatApp 2015 user " + nick + "\n").getBytes());

    }

    public void sendNickBusy(String nick) throws IOException {
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
        return new Command(Command.CommandType.valueOf(line.toUpperCase()));
    }
}