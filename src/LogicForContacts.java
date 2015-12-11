import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 06.12.2015.
 */
public class LogicForContacts {
    private ContactsModule contacts;
    private PopUpWindowGenerator popUpWindow;
    private ServerConnection serverConnection;
    private ContactsFile contactsFile;


    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public LogicForContacts(PopUpWindowGenerator popUpWindow, ServerConnection serverConnection) {
        this.popUpWindow = popUpWindow;
        this.serverConnection = serverConnection;
    }

    private void openContactsFile(){
        contactsFile = new ContactsFile(Constants.CONTACTS_LIST_PATH);
    }

    public void initContactsLogic(ContactsModule contacts){
        this.contacts = contacts;
        initAllButtonsAction();

        String [] contactsList = null;

        openContactsFile();

        try {
            contactsList = contactsFile.getContacts();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < contactsList.length; i++) {
            contacts.addFriend(contactsList[i].substring(0, Constants.NICK_AND_IP_MAX_LENGTH).replace(" ", ""),
                    contactsList[i].substring(Constants.NICK_AND_IP_MAX_LENGTH ).replace(" ", ""));
        }

        contactsFile.close();

        //  initOnlineFriends();
    }


    public void initOnlineFriends(String [] contactsList){
        int pos = 0;
        for(String friend : contactsList){
            boolean isOnline;
            try{
                isOnline = serverConnection.isNickOnline(friend.substring(0, friend.indexOf(" ")));   //it doesn't throws for now
            }catch (Exception e){
                continue;
            }


            if(isOnline){
                contacts.setOnline(pos);
            }
            else {
                contacts.setOffline(pos);
            }

            pos++;
        }
    }

    private void initAllButtonsAction(){
        initAddButtonAction();
        initDeleteButtonAction();
        initConnectButtonAction();
    }

    private void initAddButtonAction(){
        JButton addButton = contacts.getAddButton();
        addButton.addActionListener(e -> {
            String nick = contacts.getEnteredNick();
            String address = contacts.getEnteredAddress();

            if (nickAndAddressIsEmpty(nick, address))
                popUpWindow.notEnoughParametersNotification();
            else {
                openContactsFile();

                try {
                    contactsFile.addContact(nick, address);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                contactsFile.close();

                contacts.addFriend(nick, address);
                contacts.clearNickAndAddressField();
            }
        });
    }

    private boolean nickAndAddressIsEmpty(String nick, String address){
        return nick.equals("") && address.equals("");
    }

    private void initDeleteButtonAction(){
        JButton deleteButton = contacts.getDeleteButton();

        deleteButton.addActionListener(e -> {
            JList friendList = contacts.getContactsList();
            DefaultListModel friendShowing = contacts.getContactsShowing();

            if (friendList.getSelectedIndex() != -1) {
                openContactsFile();

                try {
                    contactsFile.deleteContact(friendList.getSelectedIndex());
                } catch (IOException | ParseException e1) {
                    e1.printStackTrace();
                }

                contactsFile.close();


                friendShowing.remove(friendList.getSelectedIndex());
                friendList.setModel(friendShowing);
            }
            else
                popUpWindow.noOneFriendSelectedNotification();
        });
    }

    private void initConnectButtonAction(){
        JButton connectButton = contacts.getConnectButton();

        connectButton.addActionListener(e -> {
            JList friendList = contacts.getContactsList();
            String addressAndNick = (String) friendList.getSelectedValue();

            if (addressAndNick == null)
                popUpWindow.noOneFriendSelectedNotification();
            else
                myObservable.notifyObservers(addressAndNick);
        });
    }


    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }
}
