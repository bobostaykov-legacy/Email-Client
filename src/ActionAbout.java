import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ActionAbout implements ActionListener {

    // creating a new "about" theme in which general information about the program is displayed
    public void actionPerformed(ActionEvent e){

        EmailClient ec = new EmailClient();

        GridBagConstraints con = new GridBagConstraints();
        JFrame aboutFrame = new JFrame("About");
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // setting the frame's size and positioning it in the middle of the screen
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        aboutFrame.setBounds(((int)screenWidth - 450)/2, ((int)screenHeight - 280)/2,450, 280);

        JPanel aboutPanel = new JPanel(new GridBagLayout());
        aboutFrame.getContentPane().add(aboutPanel, BorderLayout.CENTER);

        // labels that contain the information
        JLabel label1 = new JLabel("You must log in with your Google Account.");
        JLabel label2 = new JLabel("You can then send an e-mail from any");
        JLabel label3 = new JLabel("e-mail address to any other.");
        JLabel version = new JLabel("Version: 1.1");

        // creating an "ok" button
        JButton okAbout = new JButton("OK");
        okAbout.setPreferredSize(new Dimension(43,25));
        // setting "ok" button background color, depending on the current theme
        okAbout.setBackground(ec.getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( ec.getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( ec.getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );
        if (ec.getTheme().equals(Theme.BLACK)) okAbout.setForeground(Color.WHITE);
        else okAbout.setForeground(Color.BLACK);
        okAbout.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        okAbout.setFocusPainted(false);
        // after pressing the "ok" button the frame closes
        okAbout.addActionListener(ex -> aboutFrame.dispatchEvent(new WindowEvent(aboutFrame, WindowEvent.WINDOW_CLOSING)));

        // adding the information
        con.gridx = 0;
        con.gridy = 0;
        con.insets = new Insets(35,0,10,0);
        aboutPanel.add(label1, con);

        con.gridx = 0;
        con.gridy = 1;
        con.insets = new Insets(0,0,10,0);
        aboutPanel.add(label2, con);

        con.gridx = 0;
        con.gridy = 2;
        con.insets = new Insets(0,0,30,0);
        aboutPanel.add(label3, con);

        con.gridx = 0;
        con.gridy = 3;
        con.insets = new Insets(0,0,30,0);
        aboutPanel.add(version, con);

        con.gridx = 0;
        con.gridy = 4;
        aboutPanel.add(okAbout, con);

        aboutFrame.setVisible(true);
    }

}
