import javax.swing.*;

/**
 * Created by Rogdan on 05.12.2015.
 */
public interface ContactsModule {
    void addFriend(String nick, String address);

    DefaultListModel getContactsShowing();
    JList getContactsList();

    JButton getAddButton();
    JButton getDeleteButton();
    JButton getConnectButton();

    String getEnteredNick();
    String getEnteredAddress();

    void clearNickAndAddressField();

    void setOnline(int pos);

    void setOffline(int pos);
}