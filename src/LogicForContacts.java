import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 06.12.2015.
 */
public class LogicForContacts {
    private ContactsModule contacts;
    private PopUpWindowGenerator popUpWindow;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public LogicForContacts(PopUpWindowGenerator popUpWindow){
        this.popUpWindow = popUpWindow;
    }

    public void initContactsLogic(ContactsModule contacts){
        this.contacts = contacts;
        initAllButtonsAction();
    }

    private void initAllButtonsAction(){
        initAddButtonAction();
        initDeleteButtonAction();
        initConnectButtonAction();
    }

    private void initAddButtonAction(){
        JButton addButton = contacts.getAddButton();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = contacts.getEnteredNick();
                String address = contacts.getEnteredAddress();

                if (nickAndAddressIsEmpty(nick, address))
                    popUpWindow.notEnoughParametersNotification();
                else {
                    contacts.addFriend(nick, address);
                    contacts.clearNickAndAddressField();
                }
            }


        });
    }

    private boolean nickAndAddressIsEmpty(String nick, String address){
        return nick.equals("") && address.equals("");
    }

    private void initDeleteButtonAction(){
        JButton deleteButton = contacts.getDeleteButton();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList friendList = contacts.getContactsList();
                DefaultListModel friendShowing = contacts.getContactsShowing();

                if (friendList.getSelectedIndex() != -1) {
                    friendShowing.remove(friendList.getSelectedIndex());
                    friendList.setModel(friendShowing);
                }
                else
                    popUpWindow.noOneFriendSelectedNotification();
            }
        });
    }

    private void initConnectButtonAction(){
        JButton connectButton = contacts.getConnectButton();

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList friendList = contacts.getContactsList();
                String addressAndNick = (String) friendList.getSelectedValue();

                if (addressAndNick == null)
                    popUpWindow.noOneFriendSelectedNotification();
                else
                    myObservable.notifyObservers(addressAndNick);
            }
        });
    }

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }
}
