import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Александр on 07.11.2015.
 */
public class CommandListenerThread implements Runnable {
    private Connection connection;
    private Command lastCommand;
    private volatile boolean isDisconnected;
    private Thread t;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

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
        System.out.println("Run command listener");
        while (!isDisconnected){
            try {
                lastCommand = connection.receive();
            } catch (IOException e) {
                    System.out.println("Command listener Thread stopped");
                    lastCommand = new Command(Command.CommandType.REJECT);
            }
            catch (NoSuchElementException e){
                System.out.println("Command listener Thread stopped");
                lastCommand = new Command(Command.CommandType.DISCONNECT);
            }

            if(lastCommand.toString().equals(Command.CommandType.DISCONNECT.toString()) ||
                    lastCommand.toString().equals(Command.CommandType.REJECT.toString()))
                stop();

             myObservable.notifyObservers(lastCommand);
        }
    }

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public void stop(){
        isDisconnected = true;
    }
}
