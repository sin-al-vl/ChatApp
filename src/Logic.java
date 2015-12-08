import javax.swing.*;
import java.awt.*;
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

    public Logic(PopUpWindowGenerator popUpWindow) {
        this.popUpWindow = popUpWindow;
    }

    public void initDialogPanelLogic(DialogModule dialogPanel) {
        logicForDialog = new LogicForDialog(popUpWindow);
        logicForDialog.initDialogPanelLogic(dialogPanel);
    }

    public void initContactsLogic(ContactsModule contacts){
        logicForContacts = new LogicForContacts(popUpWindow);
        logicForContacts.addObserver(logicForDialog);
        logicForContacts.initContactsLogic(contacts);
    }

    public void giveLogic(Component component){

    }
}
