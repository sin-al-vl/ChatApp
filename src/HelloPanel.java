import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HelloPanel {
    private JFrame helloFrame;
    private boolean isGoOnline;
    private JTextField enterNickField;
    private String nick;
    private JButton goOnlineButton, stayOfflineButton;
    private static final String TITLE = "Initializing";

    public HelloPanel(){
        initialize();
        helloFrame.setVisible(true);
    }

    private void initialize(){
        initHelloWindow();
        initFrameComponents();
        initGoOnlineAction();
        initStayOfflineAction();

        helloFrame.setVisible(true);
    }

    private void initHelloWindow(){
        helloFrame = new JFrame();
        helloFrame.setAlwaysOnTop(true);
        helloFrame.setType(Type.UTILITY);
        helloFrame.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
        helloFrame.setBounds(100, 100, 450, 80);
        helloFrame.dispose();
        helloFrame.setUndecorated(true);
        helloFrame.setLocationRelativeTo(null);
    }

    private void initFrameComponents() {
        initTitle();
        initEnterNickPanel();
        initButtonsPanel();
    }

    private void initTitle() {
        JLabel titleLabel = new JLabel(TITLE);
        titleLabel.setFont(Constants.TITLE_FONT);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0x53C6FF));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helloFrame.add(titleLabel, BorderLayout.NORTH);
    }

    private void initEnterNickPanel() {
        JPanel enterNickPanel = new JPanel(new GridLayout(1, 2));
        enterNickPanel.setOpaque(true);
        enterNickPanel.setBackground(Color.WHITE);

        JLabel enterYourNickLabel = new JLabel("Enter your nick: ");
        enterYourNickLabel.setFont(Constants.MAIN_FONT);

        enterYourNickLabel.setOpaque(false);
        enterYourNickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enterNickField = new JTextField();
        enterNickField.setFont(new Font("Segoe Print", Font.BOLD, 17));

        //restriction on entry
        enterNickField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null)
                    return;
                if ((getLength() + str.length()) <= Constants.MAX_NICK_LENGTH) {
                    super.insertString(offset, str, attr);
                }
            }
        });

        enterNickPanel.add(enterYourNickLabel);
        enterNickPanel.add(enterNickField);

        helloFrame.add(enterNickPanel, BorderLayout.CENTER);
    }

    private void initButtonsPanel() {
        JPanel panelWithButtons = new JPanel(new GridLayout(1, 4, 20, 0));
        panelWithButtons.setOpaque(true);
        panelWithButtons.setBackground(new Color(0x53C6FF));

        goOnlineButton = new JButton("Go online");
        goOnlineButton.setFont(Constants.BUTTON_FONT);
        goOnlineButton.setBackground(new Color(0x21E652));

        stayOfflineButton = new JButton("Stay offline");
        stayOfflineButton.setFont(Constants.BUTTON_FONT);
        stayOfflineButton.setBackground(new Color(0xFF6261));

        panelWithButtons.add(new JLabel());
        panelWithButtons.add(goOnlineButton);
        panelWithButtons.add(stayOfflineButton);
        panelWithButtons.add(new JLabel());

        helloFrame.add(panelWithButtons, BorderLayout.SOUTH);
    }

    private void initGoOnlineAction(){
        goOnlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGoOnline = true;
                nick = enterNickField.getText();
                helloFrame.setVisible(false);
                MainForm.mainFrame.setVisible(true);
            }
        });
    }

    private void initStayOfflineAction(){
        stayOfflineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGoOnline = false;
                nick = enterNickField.getText();
                helloFrame.setVisible(false);
                MainForm.mainFrame.setVisible(true);
            }
        });
    }

    public String getNick(){
        return nick;
    }

    public boolean isGoOnline(){
        return isGoOnline;
    }

    public static void main(String[] args) {
        new HelloPanel();

    }
}