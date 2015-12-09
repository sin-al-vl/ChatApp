import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by Rogdan on 27.11.2015.
 *
 * this class give logic to all components.
 */

public class Logic {
    private PopUpWindowGenerator popUpWindow;
    public static LogicForDialog logicForDialog;
    public static LogicForContacts logicForContacts;
    private ServerConnection serverConnection;

    public Logic(PopUpWindowGenerator popUpWindow, ServerConnection serverConnection) {
        this.popUpWindow = popUpWindow;
        this.serverConnection = serverConnection;
    }

    public void initDialogPanelLogic(DialogModule dialogPanel) {
        logicForDialog = new LogicForDialog(popUpWindow, serverConnection);
        logicForDialog.initDialogPanelLogic(dialogPanel);
    }

    public void initContactsLogic(ContactsModule contacts){
        logicForContacts = new LogicForContacts(popUpWindow, serverConnection);
        logicForContacts.addObserver(logicForDialog);
        logicForContacts.initContactsLogic(contacts);
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

}
