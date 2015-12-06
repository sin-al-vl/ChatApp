import javax.swing.*;
import java.awt.*;
import java.sql.Date;

/**
    Created by Sofia. Refactored by Rogdan.

    This panel is fully transparent, so you can
	set background in main form and it will change here

    init - initialize. For best reading

    it`s clear code, bitch!
 */

public class DialogPanel extends JPanel implements DialogModule {
    private JButton connectButton, applyButton, disconnectButton, sendButton;
    private JTextField remoteNickField, remoteAddressField, localNickField, enterMessageField;
    private JPanel messageShowingPanel, connectApplyDisconnectPanel, sendMessagePanel;
    private JScrollPane messageScrolling;
    private DefaultListModel messageList;
    private JList messageShowing;

    public DialogPanel(Logic logic) {
        initAll();
        logic.initDialogPanelLogic(this);
        setVisible(true);
    }

    private void initAll(){
        initMainPanel();
        initMainPanelComponents();
    }

    private void initMainPanel(){
        setOpaque(false);
        setBounds(25, 25, 404, 382);
        setLayout(new BorderLayout());
    }

    private void initMainPanelComponents(){
        initConnectApplyDisconnectPanel();
        initComponentsOnConnectApplyDisconnectPanel();

        initMessageShowingPanel();
        initMessageShowingPanelComponents();

        initSendMessagePanel();
        initSendMessagePaneComponents();
    }

    private void initConnectApplyDisconnectPanel(){
        connectApplyDisconnectPanel = new JPanel();

        connectApplyDisconnectPanel.setOpaque(false);
        connectApplyDisconnectPanel.setLayout(new GridLayout(2, 5, 5, 0));
        add(connectApplyDisconnectPanel, BorderLayout.NORTH);
    }

    private void initComponentsOnConnectApplyDisconnectPanel(){
        initLocalNickLabel();
        initLocalNickField();
        initRemoteNickLabel();
        initRemoteNickField();
        initDisconnectButton();
        initEmptyLabel();
        initApplyButton();
        initRemoteAddressLabel();
        initRemoteAddressField();
        initConnectButton();
    }

    private void initLocalNickLabel(){
        JLabel localLoginLabel = new JLabel("Local login:");
        localLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectApplyDisconnectPanel.add(localLoginLabel);
    }

    private void initLocalNickField(){
        localNickField = new JTextField();
        localNickField.setColumns(10);
        connectApplyDisconnectPanel.add(localNickField);
    }

    private void initRemoteNickLabel(){
        JLabel remoteLoginLabel = new JLabel("Remote login:");
        remoteLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectApplyDisconnectPanel.add(remoteLoginLabel);
    }

    private void initRemoteNickField(){
        remoteNickField = new JTextField();
        connectApplyDisconnectPanel.add(remoteNickField);
    }

    private void initDisconnectButton(){
        disconnectButton = new JButton("Disconnect");
        connectApplyDisconnectPanel.add(disconnectButton);
    }

    private void initEmptyLabel(){
        JLabel label = new JLabel("");
        connectApplyDisconnectPanel.add(label);
    }

    private void initApplyButton(){
        applyButton = new JButton("Apply");
        connectApplyDisconnectPanel.add(applyButton);
    }

    private void initRemoteAddressLabel(){
        JLabel remoteAddressLabel = new JLabel("Remote address:");
        connectApplyDisconnectPanel.add(remoteAddressLabel);
        remoteAddressLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void initRemoteAddressField(){
        remoteAddressField = new JTextField();
        connectApplyDisconnectPanel.add(remoteAddressField);
        remoteAddressField.setColumns(10);
    }

    private void initConnectButton(){
        connectButton = new JButton("Connect");
        connectApplyDisconnectPanel.add(connectButton);
    }

    private void initMessageShowingPanel(){
        messageShowingPanel = new JPanel(new BorderLayout(0, 10));
        messageShowingPanel.setOpaque(false);

        add(messageShowingPanel, BorderLayout.CENTER);
    }

    private void initMessageShowingPanelComponents(){
        initMessageScrolling();
        initMessageList();
    }

    private void initMessageScrolling(){
        messageScrolling = new JScrollPane();
        messageShowingPanel.add(messageScrolling, BorderLayout.CENTER);
    }

    private void initMessageList(){
        messageShowing = new JList<>();
        messageList = new DefaultListModel<>();
        messageScrolling.setViewportView(messageShowing);
        messageScrolling.setHorizontalScrollBar(null);
    }

    private void initSendMessagePanel(){
        sendMessagePanel = new JPanel(new BorderLayout(10,15));
        sendMessagePanel.setOpaque(false);
        messageShowingPanel.add(sendMessagePanel, BorderLayout.SOUTH);
    }

    private void initSendMessagePaneComponents(){
        initEnterMessageField();
        initSendButton();
    }

    private void initEnterMessageField(){
        enterMessageField = new JTextField();

        sendMessagePanel.add(enterMessageField, BorderLayout.CENTER);
    }

    private void initSendButton(){
        sendButton = new JButton("Send");

        sendMessagePanel.add(sendButton, BorderLayout.EAST);
    }

    public void printNickAndMessage(String nick, String message){
        //TODO if length of message >= length od main panel - add new line;

        messageList.addElement("<html>" + nick + " " + new Date(System.currentTimeMillis()).toLocaleString()
                + ":<br>" + message + " </span></html>");
        messageShowing.setModel(messageList);
    }

    public void clearEnterMessageFieldAndRequestFocus(){
        enterMessageField.setText("");
        enterMessageField.requestFocus();
    }

    //Getters
    public String getLocalNick(){
        return localNickField.getText();
    }

    public String getRemoteNick(){
        return remoteNickField.getText();
    }

    public String getRemoteAddress(){
       return remoteAddressField.getText();
    }

    public String getEnteredMessage(){
        return enterMessageField.getText();
    }

    public JTextField getEnterMessageField() {
        return enterMessageField;
    }

    public JButton getSendButton(){
        return sendButton;
    }

    public JButton getApplyButton() {
        return applyButton;
    }

    public JButton getConnectButton() {
        return connectButton;
    }

    public JButton getDisconnectButton() {
        return disconnectButton;
    }

    //Setters
    public void setRemoteAddress(String newAddress){
        remoteAddressField.setText(newAddress);
    }

    public void setRemoteNick(String newLogin){
        remoteNickField.setText(newLogin);
    }

    //For testing. Now don`t work, because logic
    // in CallListenerThread takes from MainProgram
    public static void main(String[] args) {
        JFrame j = new JFrame();
        DialogPanel d = new DialogPanel(new Logic(new PopUpWindowGenerator(j)));
        j.getContentPane().add(d);
        j.setSize(Constants.MINIMAL_PROGRAM_DIMENSION);
        j.setVisible(true);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
