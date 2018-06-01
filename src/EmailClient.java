import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class EmailClient {

    // declaring variables global so they can be used in any method
    private static JFrame mainFrame = new JFrame("Bobo's email client");
    private static JPanel mainPanel = new JPanel(new GridBagLayout());
    private static GridBagConstraints constraints = new GridBagConstraints();
    private static JTextField textFieldUsername = new JTextField(32);
    private static JPasswordField textFieldPassword = new JPasswordField(32);
    private static JTextField textFieldTo = new JTextField(32);
    private static JTextField textFieldSubject = new JTextField(32);
    private static JTextPane mainText = new JTextPane();
    private static JButton italicButton = new JButton();
    private static JButton underlineButton = new JButton();
    private static JButton boldButton = new JButton();
    private static JButton sendButton = new JButton("Send");
    private static JLabel labelUsername = new JLabel("Username:");
    private static JLabel labelPassword = new JLabel("Password:");
    private static JLabel labelTo = new JLabel("To:");
    private static JLabel labelSubject = new JLabel("Subject:");
    private static Connection connection;
    private static boolean sessionSaved = false;


    public static void main(String[] args) {

        EmailClient emailClient = new EmailClient();

        connection = emailClient.createConnection();

        emailClient.createAll();

    }


    // connecting to the database, where I store the IP addresses and the information (for saving session)
    private Connection createConnection(){
        Temp t = new Temp();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + t.getA() + ":" + t.getB() + "/" + t.getC() + "?autoReconnect=true&useSSL=false", t.getD(), t.getE());
            System.out.println("Successfully connected to database!");
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    // creating the main frame
    private void createMainFrame(){
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveQuestion();
                System.exit(0);
            }
        });

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
    protected void setPanelTheme(Theme theme){

        if (theme == Theme.BLACK){

            EmailClient.mainPanel.setBackground(Color.BLACK);
            sendButton.setBackground(new Color(0x1F1F1F));
            boldButton.setBackground(new Color(0x1F1F1F));
            italicButton.setBackground(new Color(0x1F1F1F));
            underlineButton.setBackground(new Color(0x1F1F1F));
            labelUsername.setForeground(Color.WHITE);
            labelPassword.setForeground(Color.WHITE);
            labelTo.setForeground(Color.WHITE);
            labelSubject.setForeground(Color.WHITE);
            boldButton.setForeground(Color.WHITE);
            italicButton.setForeground(Color.WHITE);
            underlineButton.setForeground(Color.WHITE);
            sendButton.setForeground(Color.WHITE);

        } else {

            labelUsername.setForeground(Color.BLACK);
            labelPassword.setForeground(Color.BLACK);
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



    // creating the text area where the actual message will be written
    private void createMainTextArea(){
        mainText.setPreferredSize(new Dimension(900, 470));
        mainText.setBorder(BorderFactory.createLineBorder(new Color(0x796B22)));
        // setting a description font that disappears when the text field gains focus
        mainText.setFont(new Font("Arial", Font.ITALIC, 14));
        // setting font color
        mainText.setForeground(new Color(0x8E8987));
        mainText.setText("Enter your message here...");
        mainText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mainText.getText().equals("Enter your message here...")) mainText.setText("");
                mainText.setFont(new Font("Arial", Font.PLAIN, 14));
                mainText.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (mainText.getText().equals("")){
                    mainText.setFont(new Font("Arial", Font.ITALIC, 14));
                    mainText.setForeground(new Color(0x8E8987));
                    mainText.setText("Enter your message here...");
                }
            }
        });
        constraints.gridwidth = 4;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(0,0,25,0);
        mainPanel.add(mainText, constraints);
    }



    // creating text fields for "username", "password", "to" and "subject"
    private void createTextFields(){
        // setting a description font that disappears when the text field gains focus
        textFieldUsername.setFont(new Font("Arial", Font.ITALIC, 14));
        // setting font color
        textFieldUsername.setForeground(new Color(0x8E8987));
        textFieldUsername.setText("Your Google e-mail address...");
        textFieldUsername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldUsername.getText().equals("Your Google e-mail address...")) textFieldUsername.setText("");
                textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 14));
                textFieldUsername.setForeground(Color.BLACK);
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldUsername.getText().equals("")){
                    textFieldUsername.setFont(new Font("Arial", Font.ITALIC, 14));
                    textFieldUsername.setForeground(new Color(0x8E8987));
                    textFieldUsername.setText("Your Google e-mail address...");
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
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,10,20,10);
        mainPanel.add(textFieldTo, constraints);

        textFieldSubject.setFont(new Font("Arial", Font.PLAIN, 14));
        // setting font color
        textFieldSubject.setForeground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(0,10,25,10);
        mainPanel.add(textFieldSubject, constraints);
    }



    // creating labels for "username", "password", "to" and "subject"
    private void createLabels(){
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(labelUsername, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        mainPanel.add(labelPassword, constraints);

        constraints.gridx = 0;
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
        boldButton.setAction(new StyledEditorKit.BoldAction());
        boldButton.setPreferredSize(new Dimension(43,35));
        boldButton.setText("B");
        boldButton.setBackground(new Color(0xF0E09B));
        boldButton.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        boldButton.setFocusPainted(false);
        boldButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridheight = 2;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(0,0,15,110);
        mainPanel.add(boldButton, constraints);

        italicButton.setAction(new StyledEditorKit.ItalicAction());
        italicButton.setPreferredSize(new Dimension(43,35));
        italicButton.setText("I");
        italicButton.setBackground(new Color(0xF0E09B));
        italicButton.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        italicButton.setFocusPainted(false);
        italicButton.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,0,15,0);
        mainPanel.add(italicButton, constraints);

        underlineButton.setAction(new StyledEditorKit.UnderlineAction());
        underlineButton.setPreferredSize(new Dimension(43,35));
        underlineButton.setText("<HTML><U>U</U></HTML>");
        underlineButton.setBackground(new Color(0xF0E09B));
        underlineButton.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        underlineButton.setFocusPainted(false);
        underlineButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.insets = new Insets(0,110,15,0);
        mainPanel.add(underlineButton, constraints);

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
        exit.addActionListener(e -> {saveQuestion(); System.exit(0);});




        JMenu session = new JMenu("Session");
        menuBar.add(session);

        JMenuItem saveSession = new JMenuItem("Save");
        session.add(saveSession);
        saveSession.addActionListener(new ActionSaveSession());

        JMenuItem restoreSession = new JMenuItem("Restore");
        session.add(restoreSession);
        restoreSession.addActionListener(new ActionRestoreSession());




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
        textFieldUsername.setText("Your Google e-mail address...");

        textFieldPassword.setFont(new Font("Arial", Font.ITALIC, 14));
        textFieldPassword.setForeground(new Color(0x8E8987));
        textFieldPassword.setEchoChar((char) 0);
        textFieldPassword.setText("...and password");

        textFieldTo.setFont(new Font("Arial", Font.ITALIC, 14));
        textFieldTo.setForeground(new Color(0x8E8987));
        textFieldTo.setText("E-mail...");
        textFieldSubject.setText("");

        mainText.setFont(new Font("Arial", Font.ITALIC, 14));
        mainText.setForeground(new Color(0x8E8987));
        mainText.setText("Enter your message here...");

        sendButton.requestFocus();
    }



    // creating all components
    private void createAll(){

        this.createMainFrame();

        this.createMainPanel();

        this.createMainTextArea();

        this.createTextFields();

        this.createLabels();

        this.createButtons();

        this.createMenuBar();

        mainFrame.setVisible(true);

        whatsNew();
    }



    // get the current theme
    public Theme getTheme(){
        if (sendButton.getBackground().equals(new Color(0xF0E09B))) return Theme.BEIGE;
        if (sendButton.getBackground().equals(new Color(0x4BA2C7))) return Theme.BLUE;
        if (sendButton.getBackground().equals(new Color(0xD67A98))) return Theme.PINK;
        return Theme.BLACK;
    }

    public String getUsername() {
        return textFieldUsername.getText();
    }

    public String getPass() {
        return new String (textFieldPassword.getPassword());
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

    public Connection getConnecion() {
        return connection;
    }

    // check if the session has been saved
    public boolean getSessionSaved(){
        return sessionSaved;
    }

    public void setSessionSaved(boolean b){
        sessionSaved = b;
    }

    // getting the information that has been saved in the database
    public void restoreSession(String user, String to, String subject, String text, int theme){

        if (!user.equals("") && !user.equals("Your Google e-mail address...")) {
            textFieldUsername.setText(user);
            textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 14));
            textFieldUsername.setForeground(Color.BLACK);
        }

        if (!to.equals("") && !to.equals("E-mail...")) {
            textFieldTo.setText(to);
            textFieldTo.setFont(new Font("Arial", Font.PLAIN, 14));
            textFieldTo.setForeground(Color.BLACK);
        }

        textFieldSubject.setText(subject);
        textFieldSubject.setFont(new Font("Arial", Font.PLAIN, 14));
        textFieldSubject.setForeground(Color.BLACK);

        if (!text.equals("") && !text.equals("Enter your message here...")) {
            mainText.setText(text);
            mainText.setFont(new Font("Arial", Font.PLAIN, 14));
            mainText.setForeground(Color.BLACK);
        }

        if (!getUsername().equals("Your Google e-mail address...")) {
            textFieldPassword.setForeground(new Color(0x8E8987));
            textFieldPassword.setEchoChar((char) 0);
            textFieldPassword.setText("Password");
        }

        if (theme == 0) setPanelTheme(Theme.BEIGE);
        if (theme == 1) setPanelTheme(Theme.BLUE);
        if (theme == 2) setPanelTheme(Theme.PINK);
        if (theme == 3) setPanelTheme(Theme.BLACK);
    }

    // the ip address of the current PC
    public static String getIP(){
        InetAddress IP;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            return null;
        }
        return IP.toString();
    }

    // checking if this is the first time the program is started on that PC
    public boolean ipInDatabase(){
        String ip = getIP();
        Statement stmt;
        boolean res = false;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select ip from email_client");
            while (rs.next()) {
                String ips = rs.getString("ip");
                if (ip != null && ip.equals(ips)){
                    res = true;
                    break;
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return res;
    }

    public void addIpToDB(){
        String ip = getIP();
        PreparedStatement prSt;
        try {
            prSt = connection.prepareStatement("insert into email_client values (?, '', '', '', '', 0)");
            prSt.setString(1, ip);
            prSt.execute();
            prSt.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            errorDialog("There was a problem saving your session.");
        }
    }

    public void errorDialog(String message){

        JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JPanel buttonPanel = (JPanel)optionPane.getComponent(1);
        JButton buttonOk = (JButton)buttonPanel.getComponent(0);

        buttonOk.setPreferredSize(new Dimension(43, 25));
        // setting "ok" button background color, depending on the current theme
        buttonOk.setBackground(getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );

        if (getTheme().equals(Theme.BLACK)) buttonOk.setForeground(Color.WHITE);
        else buttonOk.setForeground(Color.BLACK);

        buttonOk.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        buttonOk.setFocusPainted(false);

        JDialog dialog = optionPane.createDialog(null, "Error");
        dialog.setVisible(true);
    }

    public void successDialog(String message, String title){

        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JPanel buttonPanel = (JPanel)optionPane.getComponent(1);
        JButton buttonOk = (JButton)buttonPanel.getComponent(0);

        buttonOk.setPreferredSize(new Dimension(50, 25));
        // setting "ok" button background color, depending on the current theme
        buttonOk.setBackground(getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );

        if (getTheme().equals(Theme.BLACK)) buttonOk.setForeground(Color.WHITE);
        else buttonOk.setForeground(Color.BLACK);

        buttonOk.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        buttonOk.setFocusPainted(false);

        JDialog dialog = optionPane.createDialog(null, title);
        dialog.setVisible(true);
    }

    public void customOptionPane(String message, String title){

        JButton buttonOk = new JButton("OK");
        JButton buttonExit = new JButton("Exit");

        buttonOk.setPreferredSize(new Dimension(50, 25));
        buttonExit.setPreferredSize(new Dimension(50, 25));
        // setting buttons background color, depending on the current theme
        buttonOk.setBackground(getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );
        buttonExit.setBackground(getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );

        if (getTheme().equals(Theme.BLACK)){
            buttonOk.setForeground(Color.WHITE);
            buttonExit.setForeground(Color.WHITE);
        } else {
            buttonOk.setForeground(Color.BLACK);
            buttonExit.setForeground(Color.BLACK);
        }

        buttonOk.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        buttonExit.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        buttonOk.setFocusPainted(false);
        buttonExit.setFocusPainted(false);

        buttonOk.addActionListener(al -> JOptionPane.getRootFrame().dispose());
        buttonExit.addActionListener(al -> System.exit(0));

        Object[] options = {buttonOk, buttonExit};
        JOptionPane.showOptionDialog(null,
                                        message,
                                        title,
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        null,
                                        options,
                                        "default");
    }

    // a dialog asking if the user want to save the session before exit
    public void saveQuestion(){

        if ((!getUsername().equals("Your Google e-mail address...")
                || !getToText().equals("E-mail...")
                || !getSubject().equals("")
                || !getMailText().equals("Enter your message here...")) && !getSessionSaved() ){


            JButton buttonYes = new JButton("Yes");
            JButton buttonNo = new JButton("No");

            buttonYes.setPreferredSize(new Dimension(50, 25));
            buttonNo.setPreferredSize(new Dimension(50, 25));
            // setting buttons background color, depending on the current theme
            buttonYes.setBackground(getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );
            buttonNo.setBackground(getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );

            if (getTheme().equals(Theme.BLACK)){
                buttonYes.setForeground(Color.WHITE);
                buttonNo.setForeground(Color.WHITE);
            } else {
                buttonYes.setForeground(Color.BLACK);
                buttonNo.setForeground(Color.BLACK);
            }

            buttonYes.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
            buttonNo.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
            buttonYes.setFocusPainted(false);
            buttonNo.setFocusPainted(false);

            buttonYes.addActionListener(e -> {
                new ActionSaveSession().actionPerformed(e);
                JOptionPane.getRootFrame().dispose();
            });
            buttonNo.addActionListener(al -> JOptionPane.getRootFrame().dispose());

            Object[] options = {buttonYes, buttonNo};
            JOptionPane.showOptionDialog(null,
                    "Do you want to save your session?",
                    "Save session",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    "default");
        }
    }

    // a dialog showing what is new in the current version; only once per PC
    private void whatsNew(){
        ActionSaveSession ss = new ActionSaveSession();
        if (!ipInDatabase()){

            GridBagConstraints con = new GridBagConstraints();
            JFrame whatsNewFrame = new JFrame("What's new");
            whatsNewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // setting the frame's size and positioning it in the middle of the screen
            double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            whatsNewFrame.setBounds(((int)screenWidth - 450)/2, ((int)screenHeight - 280)/2,550, 250);

            JPanel aboutPanel = new JPanel(new GridBagLayout());
            whatsNewFrame.getContentPane().add(aboutPanel, BorderLayout.CENTER);

            // labels that contain the information
            JLabel label1 = new JLabel("What's new in version 1.2?");
            JLabel label2 = new JLabel("");
            JLabel label3 = new JLabel("- You can now save your session and restore it whenever you want");
            JLabel label4 = new JLabel("- Some UI changes");

            // creating an "ok" button
            JButton okBut = new JButton("OK");
            okBut.setPreferredSize(new Dimension(43,25));
            // setting "ok" button background color, depending on the current theme
            okBut.setBackground(getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );
            if (getTheme().equals(Theme.BLACK)) okBut.setForeground(Color.WHITE);
            else okBut.setForeground(Color.BLACK);
            okBut.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
            okBut.setFocusPainted(false);
            // after pressing the "ok" button the frame closes
            okBut.addActionListener(ex -> whatsNewFrame.dispatchEvent(new WindowEvent(whatsNewFrame, WindowEvent.WINDOW_CLOSING)));

            // adding the information
            con.gridx = 0;
            con.gridy = 0;
            con.insets = new Insets(25,0,0,0);
            aboutPanel.add(label1, con);

            con.gridx = 0;
            con.gridy = 1;
            con.insets = new Insets(30,0,0,0);
            aboutPanel.add(label2, con);

            con.gridx = 0;
            con.gridy = 2;
            con.insets = new Insets(5,0,0,0);
            aboutPanel.add(label3, con);

            con.gridx = 0;
            con.gridy = 3;
            con.insets = new Insets(15,0,20,0);
            aboutPanel.add(label4, con);

            con.gridx = 0;
            con.gridy = 4;
            aboutPanel.add(okBut, con);

            whatsNewFrame.setVisible(true);

            addIpToDB();
        }

    }

}
