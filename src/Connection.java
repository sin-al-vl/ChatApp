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
        socket.getOutputStream().write(("Hello" + nick + "\n").getBytes());

    }

    public void sendNickBusy(String nick) throws IOException {
        socket.getOutputStream().write(("Busy" + nick + "\n").getBytes());
    }

    public void accept() throws IOException {
        socket.getOutputStream().write("Accept\n".getBytes());
    }

    public void reject() throws IOException {
        socket.getOutputStream().write("Reject\n".getBytes());
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
        while ((i = socket.getInputStream().read()) != '\n') {
            line += (char) i;
        }


    }
}
