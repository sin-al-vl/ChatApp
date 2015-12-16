import javax.swing.*;
import java.awt.*;

/**
 * Created by Rogdan on 04.12.2015.
 *
 * Generate pop up window on main frame
 */
public class PopUpWindowGenerator {
    private JFrame mainFrame;

    public PopUpWindowGenerator(JFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    public void noInterlocutorNotification() {
        JOptionPane.showMessageDialog(mainFrame, "There is no interlocutor");
    }

    public void messageNotSandNotification(){
        JOptionPane.showMessageDialog(mainFrame, "Message don`t send. No connection.");
    }

    public void disconnectNotification(String disconnectUser){
        JOptionPane.showMessageDialog(mainFrame, "User " + disconnectUser + " was disconnected");
    }

    public void successConnectionNotification(String connectNick){
        JOptionPane.showMessageDialog(mainFrame, "User " + connectNick + " accept your call");
    }

    public void notEnoughParametersNotification(){
        JOptionPane.showMessageDialog(mainFrame, "Not enough actual parameters");
    }

    public void remoteUserIsBusyNotification(String remoteNick){
        JOptionPane.showMessageDialog(mainFrame, "User " + remoteNick + " is busy");
    }

    public void remoteUserIsRejectedYourCallNotification(String remoteNick){
        JOptionPane.showMessageDialog(mainFrame, "User " + remoteNick + " has declined your call.");
    }

    public void remoteUserIsNotAccessibleNotification(){
        JOptionPane.showMessageDialog(mainFrame, "User is not accessible");
    }

    public void remoteUserDoesNoteExistNotification(){
        JOptionPane.showMessageDialog(mainFrame, "User does not exist");
    }

    public boolean acceptOrRejectMessage(String remoteNick, String remoteAddress){
        Object[] options = {"Receive","Reject"};

        int dialogResult = JOptionPane.showOptionDialog(mainFrame, "User " + remoteNick + " with ip " + remoteAddress +
                        " is trying to connect with you", "Receive connection",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    public void remoteUserDoesNotRespondNotification(){
        JOptionPane.showMessageDialog(mainFrame, "Remote user does not respond");
    }

    public void noOneFriendSelectedNotification(){
        JOptionPane.showMessageDialog(mainFrame, "No one friend selected");
    }
}
