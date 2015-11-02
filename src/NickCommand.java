/**
 * Created by Rogdan on 02.11.2015.
 */
public class NickCommand extends Command{
    private boolean busy;
    private String nick, version;

    public NickCommand (String version, String nick, boolean busy) {
        super(Command.CommandType.valueOf("NICK"));
        this.version = version;
        this.busy = busy;
        this.nick = nick;
    }

    public String toString (){
        if (busy)
            return version + " user " + nick + " busy";
        else
            return version + " user " + nick;
    }
}
