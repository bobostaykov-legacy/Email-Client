import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmailClient {

    //proba2

    // declaring variables global so they can be used in any method
    private static JFrame mainFrame = new JFrame("Bobo's email client");
    private static JPanel mainPanel = new JPanel(new GridBagLayout());
    private static GridBagConstraints constraints = new GridBagConstraints();
    private static JTextField textFieldUsername = new JTextField(32);
    private static JPasswordField textFieldPassword = new JPasswordField(32);
    private static JTextField textFieldFrom = new JTextField(32);
    private static JTextField textFieldTo = new JTextField(32);
    private static JTextField textFieldSubject = new JTextField(32);
    private static JTextPane mainText = new JTextPane();
    private static JButton italicButton = new JButton();
    private static JButton underlineButton = new JButton();
    private static JButton boldButton = new JButton();
    private static JButton sendButton = new JButton("Send");
    private static JLabel labelUsername = new JLabel("Username:");
    private static JLabel labelPassword = new JLabel("Password:");
    private static JLabel labelFrom = new JLabel("From:");
    private static JLabel labelTo = new JLabel("To:");
    private static JLabel labelSubject = new JLabel("Subject:");


    public static void main(String[] args) {

        EmailClient emailClient = new EmailClient();

        emailClient.createAll(Theme.BEIGE);

    }



    // creating the main frame
    private void createMainFrame(){
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // positioning tha main frame in the middle of the screen, no matter how big it is
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        mainFrame.setBounds(((int)screenWidth - 1000)/2, ((int)screenHeight - 760)/2, 1000, 760);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                sendButton.requestFocus();
            }
        });
    }



    // creating the main panel
    private void createMainPanel(){
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(0xE9C669));
    }



    // setting a new theme
    public void setPanelTheme(Theme theme){

        if (theme == Theme.BLACK){

            EmailClient.mainPanel.setBackground((Color.BLACK));
            sendButton.setBackground(new Color(0x1F1F1F));
            boldButton.setBackground(new Color(0x1F1F1F));
            italicButton.setBackground(new Color(0x1F1F1F));
            underlineButton.setBackground(new Color(0x1F1F1F));
            labelUsername.setForeground(Color.WHITE);
            labelPassword.setForeground(Color.WHITE);
            labelFrom.setForeground(Color.WHITE);
            labelTo.setForeground(Color.WHITE);
            labelSubject.setForeground(Color.WHITE);
            boldButton.setForeground(Color.WHITE);
            italicButton.setForeground(Color.WHITE);
            underlineButton.setForeground(Color.WHITE);
            sendButton.setForeground(Color.WHITE);

        } else {

            labelUsername.setForeground(Color.BLACK);
            labelPassword.setForeground(Color.BLACK);
            labelFrom.setForeground(Color.BLACK);
            labelTo.setForeground(Color.BLACK);
            labelSubject.setForeground(Color.BLACK);
            boldButton.setForeground(Color.BLACK);
            italicButton.setForeground(Color.BLACK);
            underlineButton.setForeground(Color.BLACK);
            sendButton.setForeground(Color.BLACK);

            if (theme == Theme.BEIGE){

                EmailClient.mainPanel.setBackground((new Color(0xE9C669)));
                sendButton.setBackground(new Color(0xF0E09B));
                boldButton.setBackground(new Color(0xF0E09B));
                italicButton.setBackground(new Color(0xF0E09B));
                underlineButton.setBackground(new Color(0xF0E09B));
            }

            if (theme == Theme.BLUE){

                EmailClient.mainPanel.setBackground((new Color(0x56B2DD)));
                sendButton.setBackground(new Color(0x4BA2C7));
                boldButton.setBackground(new Color(0x4BA2C7));
                italicButton.setBackground(new Color(0x4BA2C7));
                underlineButton.setBackground(new Color(0x4BA2C7));
            }

            if (theme == Theme.PINK){

                EmailClient.mainPanel.setBackground((new Color(0xEE8CAA)));
                sendButton.setBackground(new Color(0xD67A98));
                boldButton.setBackground(new Color(0xD67A98));
                italicButton.setBackground(new Color(0xD67A98));
                underlineButton.setBackground(new Color(0xD67A98));
            }
        }

    }



    // creating the text area where the actual e-mail text will be written
    private void createMainTextArea(){
        mainText.setPreferredSize(new Dimension(900, 470));
        mainText.setBorder(BorderFactory.createLineBorder(new Color(0x796B22)));
        // setting a description font that disappears when the text field gains focus
        mainText.setFont(new Font("Arial", Font.ITALIC, 14));
        // setting font color
        mainText.setForeground(new Color(0x8E8987));
        mainText.setText("Enter your E-mail here...");
        mainText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mainText.getText().equals("Enter your E-mail here...")) mainText.setText("");
                mainText.setFont(new Font("Arial", Font.PLAIN, 14));
                mainText.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (mainText.getText().equals("")){
                    mainText.setFont(new Font("Arial", Font.ITALIC, 14));
                    mainText.setForeground(new Color(0x8E8987));
                    mainText.setText("Enter your E-mail here...");
                }
            }
        });
        constraints.gridwidth = 4;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(0,0,25,0);
        mainPanel.add(mainText, constraints);
    }



    // creating text fields for "username", "password", "from", "to" and "subject"
    private void createTextFields(){
        // setting a description font that disappears when the text field gains focus
        textFieldUsername.setFont(new Font("Arial", Font.ITALIC, 14));
        // setting font color
        textFieldUsername.setForeground(new Color(0x8E8987));
        textFieldUsername.setText("Your Google username...");
        textFieldUsername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldUsername.getText().equals("Your Google username...")) textFieldUsername.setText("");
                textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 14));
                textFieldUsername.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldUsername.getText().equals("")){
                    textFieldUsername.setFont(new Font("Arial", Font.ITALIC, 14));
                    textFieldUsername.setForeground(new Color(0x8E8987));
                    textFieldUsername.setText("Your Google username...");
                }
            }
        });
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,10,20,10);
        mainPanel.add(textFieldUsername, constraints);

        // setting a description font that disappears when the text field gains focus
        textFieldPassword.setFont(new Font("Arial", Font.ITALIC, 14));
        // setting font color
        textFieldPassword.setForeground(new Color(0x8E8987));
        textFieldPassword.setEchoChar((char) 0);
        textFieldPassword.setText("...and password");
        textFieldPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String s1 = new String(textFieldPassword.getPassword());
                if (s1.equals("...and password")) textFieldPassword.setText("");
                textFieldPassword.setEchoChar('*');
                textFieldPassword.setFont(new Font("Arial", Font.PLAIN, 14));
                textFieldPassword.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                String s2 = new String(textFieldPassword.getPassword());
                if (s2.equals("")){
                    textFieldPassword.setEchoChar((char) 0);
                    textFieldPassword.setFont(new Font("Arial", Font.ITALIC, 14));
                    textFieldPassword.setForeground(new Color(0x8E8987));
                    textFieldPassword.setText("...and password");
                }
            }
        });
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,0,20,0);
        mainPanel.add(textFieldPassword, constraints);

        // setting a description font that disappears when the text field gains focus
        textFieldFrom.setFont(new Font("Arial", Font.ITALIC, 14));
        // setting font color
        textFieldFrom.setForeground(new Color(0x8E8987));
        textFieldFrom.setText("E-mail...");
        textFieldFrom.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldFrom.getText().equals("E-mail...")) textFieldFrom.setText("");
                textFieldFrom.setFont(new Font("Arial", Font.PLAIN, 14));
                textFieldFrom.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldFrom.getText().equals("")){
                    textFieldFrom.setFont(new Font("Arial", Font.ITALIC, 14));
                    textFieldFrom.setForeground(new Color(0x8E8987));
                    textFieldFrom.setText("E-mail...");
                }
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,10,20,10);
        mainPanel.add(textFieldFrom, constraints);

        // setting a description font that disappears when the text field gains focus
        textFieldTo.setFont(new Font("Arial", Font.ITALIC, 14));
        // setting font color
        textFieldTo.setForeground(new Color(0x8E8987));
        textFieldTo.setText("E-mail...");
        textFieldTo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldTo.getText().equals("E-mail...")) textFieldTo.setText("");
                textFieldTo.setFont(new Font("Arial", Font.PLAIN, 14));
                textFieldTo.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldTo.getText().equals("")){
                    textFieldTo.setFont(new Font("Arial", Font.ITALIC, 14));
                    textFieldTo.setForeground(new Color(0x8E8987));
                    textFieldTo.setText("E-mail...");
                }
            }
        });
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,20,0);
        mainPanel.add(textFieldTo, constraints);

        textFieldSubject.setFont(new Font("Arial", Font.PLAIN, 14));
        // setting font color
        textFieldSubject.setForeground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,10,25,10);
        mainPanel.add(textFieldSubject, constraints);
    }



    // creating labels for "username", "password", "from", "to" and "subject"
    private void createLabels(){
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(labelUsername, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        mainPanel.add(labelPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(labelFrom, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        mainPanel.add(labelTo, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(labelSubject, constraints);
    }



    // creating "send" and text edit buttons
    private void createButtons(){
        // creating the send button
        sendButton.setPreferredSize(new Dimension(120,30));
        sendButton.setBackground(new Color(0xF0E09B));
        sendButton.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(new ActionSend());
        constraints.gridx = 3;
        constraints.gridy = 4;
        constraints.insets = new Insets(0,200,0,0);
        mainPanel.add(sendButton, constraints);


        // creating text edit buttons
        italicButton.setAction(new StyledEditorKit.ItalicAction());
        italicButton.setPreferredSize(new Dimension(43,35));
        italicButton.setText("I");
        italicButton.setBackground(new Color(0xF0E09B));
        italicButton.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        italicButton.setFocusPainted(false);
        italicButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,0,15,0);
        mainPanel.add(italicButton, constraints);

        underlineButton.setAction(new StyledEditorKit.UnderlineAction());
        underlineButton.setPreferredSize(new Dimension(43,35));
        underlineButton.setText("<HTML><U>U</U></HTML>");
        underlineButton.setBackground(new Color(0xF0E09B));
        underlineButton.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        underlineButton.setFocusPainted(false);
        underlineButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,110,15,0);
        mainPanel.add(underlineButton, constraints);

        boldButton.setAction(new StyledEditorKit.BoldAction());
        boldButton.setPreferredSize(new Dimension(43,35));
        boldButton.setText("B");
        boldButton.setBackground(new Color(0xF0E09B));
        boldButton.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        boldButton.setFocusPainted(false);
        boldButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,0,15,110);
        mainPanel.add(boldButton, constraints);

    }



    // creating a menu bar with some options
    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        JMenu options = new JMenu("Options");
        menuBar.add(options);

        JMenuItem newMail = new JMenuItem("New E-Mail");
        options.add(newMail);
        newMail.addActionListener(e -> newMail());

        JMenuItem settings = new JMenuItem("Settings");
        options.add(settings);
        settings.addActionListener(new ActionSettings());

        JMenuItem exit = new JMenuItem("Exit");
        options.add(exit);
        exit.addActionListener(e -> System.exit(0));

        JMenu help = new JMenu("Help");
        menuBar.add(help);

        JMenuItem instructions = new JMenuItem("About");
        help.add(instructions);
        instructions.addActionListener(new ActionAbout());

    }



    // setting all text fields to initial state when "New E-mail" button has been pressed
    private static void newMail(){
        textFieldUsername.setFont(new Font("Arial", Font.ITALIC, 14));
        textFieldUsername.setForeground(new Color(0x8E8987));
        textFieldUsername.setText("Your Google username...");
        textFieldPassword.setFont(new Font("Arial", Font.ITALIC, 14));
        textFieldPassword.setForeground(new Color(0x8E8987));
        textFieldPassword.setEchoChar((char) 0);
        textFieldPassword.setText("...and password");
        textFieldFrom.setFont(new Font("Arial", Font.ITALIC, 14));
        textFieldFrom.setForeground(new Color(0x8E8987));
        textFieldFrom.setText("E-mail...");
        textFieldTo.setFont(new Font("Arial", Font.ITALIC, 14));
        textFieldTo.setForeground(new Color(0x8E8987));
        textFieldTo.setText("E-mail...");
        textFieldSubject.setText("");
        mainText.setFont(new Font("Arial", Font.ITALIC, 14));
        mainText.setForeground(new Color(0x8E8987));
        mainText.setText("Enter your E-mail here...");
        sendButton.requestFocus();
    }



    // creating all components
    private void createAll(Theme theme){

        this.createMainFrame();

        this.createMainPanel();

        this.createMainTextArea();

        this.createTextFields();

        this.createLabels();

        this.createButtons();

        this.createMenuBar();

        mainFrame.setVisible(true);
    }



    // getting the current theme
    public Theme getTheme(){
        if (sendButton.getBackground().equals(new Color(0xF0E09B))) return Theme.BEIGE;
        if (sendButton.getBackground().equals(new Color(0x4BA2C7))) return Theme.BLUE;
        if (sendButton.getBackground().equals(new Color(0xD67A98))) return Theme.PINK;
        return Theme.BLACK;
    }

    public String getFromText(){
        return textFieldFrom.getText();
    }

    public String getToText(){
        return textFieldTo.getText();
    }

    public String getSubject(){
        return textFieldSubject.getText();
    }

    public String getMailText(){
        return mainText.getText();
    }

    public String getUsername() {
        return textFieldUsername.getText();
    }

    public String getPass() {
        return new String (textFieldPassword.getPassword());
    }

}
