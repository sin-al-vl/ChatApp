import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rogdan on 06.12.2015.
 */
public class LogicForDialog implements Observer{
    private Connection connection;
    private CallListenerThread callListenerThread;
    private Caller caller;
    private PopUpWindowGenerator popUpWindow;
    private DialogModule dialogPanel;

    public LogicForDialog(PopUpWindowGenerator popUpWindow) {
        this.popUpWindow = popUpWindow;
    }

    public void initDialogPanelLogic(DialogModule dialogPanel) {
        this.dialogPanel = dialogPanel;
        initializeDialogPanelButtons();
    }

    public void setNickAddressAndConnect(String nick, String address){
        dialogPanel.setRemoteNick(nick);
        dialogPanel.setRemoteAddress(address);

        dialogPanel.getConnectButton().doClick();
    }

    private void initializeDialogPanelButtons() {
        initializeSendButtonLogic();
        initializeApplyButtonLogic();
        initializeConnectButtonAction();
        initializeDisconnectButtonAction();
    }

    //Start connect button initialize
    private void initializeConnectButtonAction(){
        JButton connectButton = dialogPanel.getConnectButton();

        connectButton.addActionListener(e -> {
            if (remoteAddressAndRemoteNickIsEmpty())
                popUpWindow.notEnoughParametersNotification();
            else
                runCallingThread();
        });
    }

    private boolean remoteAddressAndRemoteNickIsEmpty(){
        String remoteNick = dialogPanel.getRemoteNick();
        String remoteAddress = dialogPanel.getRemoteAddress();

        return remoteNick.equals("") && remoteAddress.equals("");
    }

    private void runCallingThread(){
        new Thread(() -> {
            caller = new Caller(dialogPanel.getLocalNick(), dialogPanel.getRemoteAddress());
            connection = caller.call();
            checkCallStatus(caller.getCallStatus());
        }).start();
    }

    private void checkCallStatus(Caller.CallStatus status) {
        String remoteNick = caller.getRemoteNick();

        switch (status) {
            case OK:
                dialogPanel.setRemoteNick(remoteNick);
                popUpWindow.successConnectionNotification(remoteNick);
                break;

            case BUSY:
                popUpWindow.remoteUserIsBusyNotification(caller.getRemoteNick());
                break;

            case REJECTED:
                popUpWindow.remoteUserIsRejectedYourCallNotification(remoteNick);
                break;

            case NOT_ACCESSIBLE:
                popUpWindow.remoteUserIsNotAccessibleNotification();
                break;

            case NO_SERVICE:
                popUpWindow.remoteUserDoesNoteExistNotification();
                break;
        }
    }
    //End connect button initialize

    private void initializeDisconnectButtonAction(){
        JButton disconnectButton = dialogPanel.getDisconnectButton();

        disconnectButton.addActionListener(e -> {
            if (connection != null)
                try {
                    connection.disconnect();
                } catch (IOException ignored){
                }
            disconnect();
        });
    }

    private void disconnect(){
        if (callListenerThread != null)
            callListenerThread.setBusy(false);

        dialogPanel.setRemoteNick("");
        dialogPanel.setRemoteAddress("");
        connection = null;
        caller = null;
    }

    private void initializeSendButtonLogic() {
        JButton sendButton = dialogPanel.getSendButton();

        //Send button action
        sendButton.addActionListener(e -> {
            if (connection == null)
                popUpWindow.noInterlocutorNotification();
            else {
                dialogPanel.printNickAndMessage(dialogPanel.getLocalNick(), dialogPanel.getEnteredMessage());
                try {
                    connection.sendMessage(dialogPanel.getEnteredMessage());
                } catch (IOException | NullPointerException ex) {
                    popUpWindow.messageNotSandNotification();
                    disconnect();
                }

            }
            dialogPanel.clearEnterMessageFieldAndRequestFocus();
        });

        //Initialize "Sending message on press enter"

        JTextField enterMessageField = dialogPanel.getEnterMessageField();
        enterMessageField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initializeApplyButtonLogic(){
        JButton applyButton = dialogPanel.getApplyButton();
        applyButton.addActionListener(e -> {
            if (callListenerThread == null) {
                System.out.println("Added obs");
                callListenerThread = new CallListenerThread(new CallListener(dialogPanel.getLocalNick()));
                callListenerThread.addObserver(this);
            } else {
                callListenerThread.setLocalNick(dialogPanel.getLocalNick());
            }
        });
    }

    public void update(Observable who, Object updateInfo) {
        if (updateInfo instanceof CallListener)
            getInsideCall((CallListener) updateInfo);

        else if (updateInfo instanceof Connection)
            connection = (Connection) updateInfo;

        else if (updateInfo instanceof Command)
        {
            Command updateCommand = (Command) updateInfo;

            if (updateCommand instanceof MessageCommand)
                dialogPanel.printNickAndMessage(dialogPanel.getRemoteNick(), updateInfo.toString());
            else if (updateCommand.toString().equals("DISCONNECT") || updateCommand.toString().equals("REJECT"))
            {
                popUpWindow.disconnectNotification(dialogPanel.getRemoteNick());
                disconnect();
            }
        }
        else if (updateInfo instanceof String){
            String remoteNick = updateInfo.toString().substring(0, updateInfo.toString().lastIndexOf(' '));
            String remoteAddress = updateInfo.toString().substring(updateInfo.toString().lastIndexOf(' ') + 1);

            MainForm.tabbedPane.setSelectedIndex(0);

            dialogPanel.setRemoteNick(remoteNick);
            dialogPanel.setRemoteAddress(remoteAddress);
            dialogPanel.getConnectButton().doClick();
        }
    }

    public void getInsideCall(CallListener callListener){
        callListenerThread.suspend();
        boolean isReceiveCall = popUpWindow.acceptOrRejectMessage(callListener.getRemoteNick(), callListener.getRemoteAddress());

        if (isReceiveCall){
            dialogPanel.setRemoteNick(callListener.getRemoteNick());
            dialogPanel.setRemoteAddress(callListener.getRemoteAddress());
        }

        callListenerThread.setReceive(isReceiveCall);
        callListenerThread.resume();
    }
}
