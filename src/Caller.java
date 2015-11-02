import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Rogdan on 02.11.2015.
 */
public class Caller {
    private String localNick;
    private InetAddress remoteAddress;

    public Caller (String localNick){
        this.localNick = localNick;
    }

    public Caller(){
        this("Untitled");
    }

    public Caller (String localNick, String ip){
        this(localNick);

        try {
            this.remoteAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException ex){
            /*
                Выводится на главный экран сообщение о том,
                что пользователя с таким IP не существует.
                Иными словами - не верно введен IP
             */
        }
    }

    public Caller (String localNick, InetAddress remoteAddress){
        this(localNick);
        this.remoteAddress = remoteAddress;
    }

    public static enum CallStatus {
        BUSY, NO_SERVICE, NOT_ACCESSIBLE, OK, REJECTED;


    }
}
