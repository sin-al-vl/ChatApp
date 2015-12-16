import java.awt.*;

/**
 * Created by Rogdan on 08.11.2015.
 */

//Take and store all constants here

public class Constants {
    public static final int PORT = 28411;
    public static final String ChatApp_VERSION = "ChatApp 2015";
    public static final String CHARSET_NAME = "UTF-8";
    public static final int START_NICK_POSITION = 18;
    public static final long WAIT_UNTIL_REMOTE_USER_START_ACCEPTING_IN_MILLIS = 30;
    public static final long WAIT_UNTIL_REMOTE_USER_ACCEPT_OR_REJECT_YOUR_CALL_IN_MILLIS = 6000;

    public static final Font TITLE_FONT = new Font("Algerian", Font.PLAIN, 16);
    public static final Font MAIN_FONT = new Font("Microsoft JhengHei UI", Font.BOLD, 15);
    public static final Font BUTTON_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 12);

    public static final Dimension MINIMAL_PROGRAM_DIMENSION = new Dimension(531, 450);

    public static final String SERVER_ADDRESS_PATH = "jdbc:mysql://files.litvinov.in.ua/chatapp_server?characterEncoding=" +
            "utf-8&useUnicode=true";
    public static final String CONTACTS_LIST_PATH = "res//contacts.rip";
    public static final int NICK_AND_IP_MAX_LENGTH = 15;
    public static final int MAX_NICK_LENGTH = 22;
}