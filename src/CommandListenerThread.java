import java.io.IOException;
import java.util.Observable;

/**
 * Created by Александр on 07.11.2015.
 */
public class CommandListenerThread extends Observable implements Runnable {
    private Connection connection;
    private Command lastCommand;
    private volatile boolean isDisconnected;
    private Thread t;

    public CommandListenerThread() {
        connection = null;
        t = new Thread(this);
        t.start();
    }

    public CommandListenerThread(Connection connection) {
        this.connection = connection;
        t = new Thread(this);
        t.start();
    }

    public Command getLastCommand(){
        return lastCommand;
    }

    public boolean isDisconnected(){
        return isDisconnected;
    }

    @Override
    public void run() {
        while (!isDisconnected){
            try {
                lastCommand = connection.receive();
            } catch (IOException e) {
                if(lastCommand == null){}
                                                                //Not protocol input
                else
                    e.printStackTrace();                        //Something else
            }

            setChanged();
            notifyObservers();

            if(lastCommand.toString().equals(Command.CommandType.DISCONNECT.toString()) ||
                    lastCommand.toString().equals(Command.CommandType.REJECT.toString()))
                stop();
        }
    }

    public void stop(){
        isDisconnected = true;
    }
}
