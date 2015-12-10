import javax.swing.*;
import java.awt.*;

/**
	Created by Sonya, refactoring by Rogdan

	this panel is fully transparent, so you can
	set background in main form and it will change here

    init - initialize. For best reading.
 */

public class Contacts extends JPanel implements ContactsModule{
    private JButton connectButton, addButton, deleteButton;
    private JTextField searchField, nickField, addressField;
    private JPanel panelWithButton, nickAndAddressPanel, contactsAndSearchPanel, contactsPanel, searchPanel;
    private JScrollPane contactsScrolling;
    private DefaultListModel contactsShowing;
    private JList contactsList;

    public Contacts(LogicManager logicManager){
        initAll();
        logicManager.initContactsLogic(this);
    }

    private void initAll(){
        initContactPanel();

        initPanelWithButton();
        initAllButtonComponents();

        initLoginAndAddressPanel();
        initLoginAndAddressPanelComponents();

        initPanelWithContactsAndSearch();
        initPanelWithContactsAndSearchComponents();
    }

    private void initContactPanel(){
        setOpaque(false);
        setLayout(new BorderLayout(10, 15));
    }

    private void initPanelWithButton(){
        panelWithButton = new JPanel(new GridLayout(1, 3, 10, 0));
        panelWithButton.setOpaque(false);

        add(panelWithButton, BorderLayout.SOUTH);
    }

    private void initAllButtonComponents(){
        initAddButton();
        initDeleteButton();
        initConnectButton();
    }

    private void initPanelWithContactsAndSearch(){
        contactsAndSearchPanel = new JPanel(new BorderLayout(10, 0));
        contactsAndSearchPanel.setOpaque(false);

        add(contactsAndSearchPanel, BorderLayout.CENTER);
    }

    private void initPanelWithContactsAndSearchComponents(){
        initContactsPanel();
        initContactPanelComponents();
        initSearchPanel();
        initSearchPanelComponents();
    }

    private void initContactsPanel(){
        contactsPanel = new JPanel(new BorderLayout(100, 100));
        contactsPanel.setOpaque(false);

        contactsAndSearchPanel.add(contactsPanel, BorderLayout.CENTER);
    }

    private void initContactPanelComponents(){
        initContactScrolling();
        initContactShowing();
    }

    private void initContactsLabel(){
        JLabel contacts = new JLabel("Contacts: ");
        contacts.setOpaque(false);
        contacts.setHorizontalAlignment(SwingConstants.CENTER);

        searchPanel.add(contacts, BorderLayout.SOUTH);
    }

    private void initContactScrolling(){
        contactsScrolling = new JScrollPane();

        contactsPanel.add(contactsScrolling, BorderLayout.CENTER);
    }

    private void initContactShowing(){
        contactsList = new JList<>();
        contactsShowing = new DefaultListModel<>();

        contactsScrolling.setViewportView(contactsList);
        contactsScrolling.setHorizontalScrollBar(null);
    }

    private void initSearchPanelComponents(){
        initContactsLabel();
        initSearchField();
    }

    private void initSearchField(){
        searchField = new JTextField("Search");

        searchPanel.add(searchField, BorderLayout.CENTER);
    }

    private void initSearchPanel(){
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        contactsAndSearchPanel.add(searchPanel, BorderLayout.NORTH);
    }


    private void initLoginAndAddressPanel(){
        nickAndAddressPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        nickAndAddressPanel.setOpaque(false);

        add(nickAndAddressPanel, BorderLayout.NORTH);
    }

    private void initLoginAndAddressPanelComponents(){
        initNickLabel();
        initNickField();
        initAddressLabel();
        initAddressField();
    }

    private void initNickLabel(){
        JLabel nickLabel = new JLabel("Nick:");
        nickLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nickAndAddressPanel.add(nickLabel);
    }

    private void initNickField(){
        nickField = new JTextField();
        nickField.setColumns(10);

        nickAndAddressPanel.add(nickField);
    }

    private void initAddressLabel(){
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nickAndAddressPanel.add(addressLabel);
    }

    private void initAddressField(){
        addressField = new JTextField();
        nickAndAddressPanel.add(addressField);
    }

    private void initConnectButton(){
        connectButton = new JButton("Connect");

        panelWithButton.add(connectButton);
    }

    private void initAddButton(){
        addButton = new JButton("Add");
        panelWithButton.add(addButton);
    }

    private void initDeleteButton(){
        deleteButton = new JButton("Delete");
        panelWithButton.add(deleteButton);
    }

    @Override
    public void addFriend(String nick, String address) {
        contactsShowing.addElement(nick + " " + address);
        contactsList.setModel(contactsShowing);
    }

    @Override
    public DefaultListModel getContactsShowing() {
        return contactsShowing;
    }

    @Override
    public JList getContactsList() {
        return contactsList;
    }

    @Override
    public JButton getAddButton() {
        return addButton;
    }

    @Override
    public JButton getDeleteButton() {
        return deleteButton;
    }

    @Override
    public JButton getConnectButton() {
        return connectButton;
    }

    @Override
    public String getEnteredNick() {
        return nickField.getText();
    }

    @Override
    public String getEnteredAddress() {
        return addressField.getText();
    }

    @Override
    public void clearNickAndAddressField() {
        nickField.setText("");
        addressField.setText("");
    }
}
