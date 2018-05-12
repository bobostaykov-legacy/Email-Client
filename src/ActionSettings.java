import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ActionSettings implements ActionListener {

    private static boolean b;

    // creating a new settings frame in which the theme can be changed
    public void actionPerformed(ActionEvent e){

        EmailClient ec = new EmailClient();

        b = true;

        GridBagConstraints con = new GridBagConstraints();
        JFrame settFrame = new JFrame("Settings");
        settFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // setting settings's frame size and positioning it in the middle of the screen
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        settFrame.setBounds(((int)screenWidth - 350)/2, ((int)screenHeight - 300)/2,315, 270);

        JPanel settPanel = new JPanel(new GridBagLayout());
        settFrame.getContentPane().add(settPanel, BorderLayout.CENTER);

        // creating options for changing the theme
        JLabel setTheme = new JLabel("Set Theme:");
        JRadioButton beige = new JRadioButton("Beige");
        beige.setFocusPainted(false);
        JRadioButton blue = new JRadioButton("Blue  ");
        blue.setFocusPainted(false);
        JRadioButton pink = new JRadioButton("Pink  ");
        pink.setFocusPainted(false);
        JRadioButton black = new JRadioButton("Black");
        black.setFocusPainted(false);

        // adding them to a group (depending on the current theme, the initially selected radio button is different)
        ButtonGroup buttGroup = new ButtonGroup();
        buttGroup.add(beige);
        if (ec.getTheme().equals(Theme.BEIGE)) beige.setSelected(true);
        buttGroup.add(blue);
        if (ec.getTheme().equals(Theme.BLUE)) blue.setSelected(true);
        buttGroup.add(pink);
        if (ec.getTheme().equals(Theme.PINK)) pink.setSelected(true);
        buttGroup.add(black);
        if (ec.getTheme().equals(Theme.BLACK)) black.setSelected(true);

        JButton okSett = new JButton("OK");
        okSett.setPreferredSize(new Dimension(43,25));
        // setting "ok" button background color, depending on the current theme
        okSett.setBackground(ec.getTheme().equals(Theme.BEIGE) ? new Color(0xF0E09B) : ( ec.getTheme().equals(Theme.BLUE) ? new Color(0x4BA2C7) : ( ec.getTheme().equals(Theme.PINK) ? new Color(0xD67A98) : new Color(0x1F1F1F) ) ) );
        if (ec.getTheme().equals(Theme.BLACK)) okSett.setForeground(Color.WHITE);
        else okSett.setForeground(Color.BLACK);
        okSett.setBorder(BorderFactory.createLineBorder(new Color(0x858585)));
        okSett.setFocusPainted(false);
        // when the "ok" button is pressed, the theme is changed
        okSett.addActionListener(ev -> {
            if (beige.isSelected() && b) ec.setPanelTheme(Theme.BEIGE);
            if (blue.isSelected() && b) ec.setPanelTheme(Theme.BLUE);
            if (pink.isSelected() && b) ec.setPanelTheme(Theme.PINK);
            if (black.isSelected() && b) ec.setPanelTheme(Theme.BLACK);
            makeBFalse();
            settFrame.dispatchEvent(new WindowEvent(settFrame, WindowEvent.WINDOW_CLOSING));
        });

        // adding components to the panel
        con.gridx = 0;
        con.gridy = 0;
        con.insets = new Insets(0,0,10,0);
        settPanel.add(setTheme, con);

        con.gridx = 0;
        con.gridy = 1;
        con.insets = new Insets(5,2,0,0);
        settPanel.add(beige, con);

        con.gridx = 0;
        con.gridy = 2;
        con.insets = new Insets(0,0,0,0);
        settPanel.add(blue, con);

        con.gridx = 0;
        con.gridy = 3;
        con.insets = new Insets(0,0,0,0);
        settPanel.add(pink, con);

        con.gridx = 0;
        con.gridy = 4;
        con.insets = new Insets(0,0,0,2);
        settPanel.add(black, con);

        con.gridx = 0;
        con.gridy = 5;
        con.insets = new Insets(20,0,0,0);
        settPanel.add(okSett, con);




        settFrame.setVisible(true);
    }


    private static void makeBFalse(){
        b = false;
    }

}
