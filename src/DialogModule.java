import javax.swing.*;

/**
 * Created by Rogdan on 05.12.2015.
 */
public interface DialogModule {
    String getLocalNick();
    String getRemoteNick();
    String getRemoteAddress();
    String getEnteredMessage();

    JTextField getEnterMessageField();

    JButton getSendButton();
    JButton getApplyButton();
    JButton getConnectButton();
    JButton getDisconnectButton();

    void setRemoteNick(String newNick);
    void setRemoteAddress(String newAddress);
    void printNickAndMessage(String nick, String message);
    void clearEnterMessageFieldAndRequestFocus();
}
