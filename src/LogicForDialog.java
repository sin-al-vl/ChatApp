import javax.swing.*;
import java.awt.event.KeyAdapter;
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
    private ServerConnection serverConnection;

    public LogicForDialog(PopUpWindowGenerator popUpWindow, ServerConnection serverConnection) {
        this.popUpWindow = popUpWindow;
        this.serverConnection = serverConnection;
    }

    public void initDialogPanelLogic(DialogModule dialogPanel) {
        this.dialogPanel = dialogPanel;
        initializeDialogPanelButtons();
    }

    private void initializeDialogPanelButtons() {
        initializeSendButtonLogic();
        initializeApplyButtonLogic();
        initializeConnectButtonAction();
        initializeDisconnectButtonAction();

        setDisconnectAndSendButtonEnabled(false);
    }

    //Start connect button initialize
    private void initializeConnectButtonAction(){
        JButton connectButton = dialogPanel.getConnectButton();

        connectButton.addActionListener(e -> {
            setConnectButtonEnabled(false);
            if (remoteAddressAndRemoteNickIsEmpty()) {

                popUpWindow.notEnoughParametersNotification();
                setConnectButtonEnabled(true);

            } else
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

            if (caller.getCallStatus() != Caller.CallStatus.OK)
                setConnectButtonEnabled(true);
        }).start();
    }

    private void checkCallStatus(Caller.CallStatus status) {
        String remoteNick = caller.getRemoteNick();

        switch (status) {
            case OK:
                dialogPanel.setRemoteNick(remoteNick);
                popUpWindow.successConnectionNotification(remoteNick);

                if (callListenerThread != null)
                    callListenerThread.setBusy(true);

                setConnectButtonEnabled(false);
                setDisconnectAndSendButtonEnabled(true);
                clearMessageList();
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
        setConnectButtonEnabled(true);
        setDisconnectAndSendButtonEnabled(false);

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
        enterMessageField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }
        });
    }

    private void initializeApplyButtonLogic(){
        JButton applyButton = dialogPanel.getApplyButton();

        applyButton.addActionListener(e -> {
            if (dialogPanel.getLocalNick().equals("")) {
                popUpWindow.notEnoughParametersNotification();
            }
            else {
                callListenerThread = new CallListenerThread(new CallListener(dialogPanel.getLocalNick()));
                callListenerThread.addObserver(this);
                dialogPanel.getApplyButton().setEnabled(false);  //Set enabled false after first press

                serverConnection.setLocalNick(dialogPanel.getLocalNick());
                serverConnection.connect();
                serverConnection.goOnline();
            }
        });
    }

    private void setDisconnectAndSendButtonEnabled(boolean enabled){
        dialogPanel.getSendButton().setEnabled(enabled);
        dialogPanel.getDisconnectButton().setEnabled(enabled);
    }

    private void setConnectButtonEnabled(boolean enabled){
        dialogPanel.getConnectButton().setEnabled(enabled);
    }

    private void clearMessageList(){
        dialogPanel.cleatMessageList();
    }

    public void update(Observable who, Object updateInfo) {
        if (updateInfo instanceof CallListener)
            getInsideCall((CallListener) updateInfo);

        else if (updateInfo instanceof Connection) {
            connection = (Connection) updateInfo;
        }
        else if (updateInfo instanceof Command)
        {
            Command updateCommand = (Command) updateInfo;

            if (updateCommand instanceof MessageCommand)
                dialogPanel.printNickAndMessage(dialogPanel.getRemoteNick(), updateInfo.toString());
            else if (updateCommand.toString().equals("DISCONNECT") || updateCommand.toString().equals("REJECT"))
            {
                String remoteNick = dialogPanel.getRemoteNick();
                disconnect();
                popUpWindow.disconnectNotification(remoteNick);
            }
        }
        else if (updateInfo instanceof String){
            String remoteNick = updateInfo.toString().substring(0, updateInfo.toString().lastIndexOf(' '));
            String remoteAddress = updateInfo.toString().substring(updateInfo.toString().lastIndexOf(' ') + 1);

            if (connection != null)
            try {
                connection.disconnect();
                disconnect();
            } catch (IOException ignore){}

            dialogPanel.setRemoteNick(remoteNick);
            dialogPanel.setRemoteAddress(remoteAddress);
            dialogPanel.getConnectButton().doClick();
            MainForm.tabbedPane.setSelectedIndex(0);
        }
        else if(updateInfo instanceof ServerConnection){

        }
    }

    public void getInsideCall(CallListener callListener){
        callListenerThread.suspend();
        boolean isReceiveCall = popUpWindow.acceptOrRejectMessage(callListener.getRemoteNick(), callListener.getRemoteAddress());

        if (isReceiveCall){
            dialogPanel.setRemoteNick(callListener.getRemoteNick());
            dialogPanel.setRemoteAddress(callListener.getRemoteAddress());

            setConnectButtonEnabled(false);
            setDisconnectAndSendButtonEnabled(true);
            clearMessageList();
        }

        callListenerThread.setReceive(isReceiveCall);
        callListenerThread.resume();
    }
}
