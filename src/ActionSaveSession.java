import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ActionSaveSession implements ActionListener {

    private EmailClient ec = new EmailClient();

    @Override
    public void actionPerformed(ActionEvent e) {

        PreparedStatement prSt;
        Connection con = ec.getConnecion();
        String ip = EmailClient.getIP();

        // get text from all fields (except password) + theme

        if (ip == null || con == null) {
            ec.errorDialog("There was a problem saving your session.");
            return;
        }

        String savedUsername = ec.getUsername();
        String savedToText = ec.getToText();
        String savedSubject = ec.getSubject();
        String savedMailText = ec.getMailText();
        int savedTheme = 0;

        if (ec.getTheme() == Theme.BLUE) savedTheme = 1;
        if (ec.getTheme() == Theme.PINK) savedTheme = 2;
        if (ec.getTheme() == Theme.BLACK) savedTheme = 3;

        // the ip is already in the database, update values for every text field (except password)
        try {
            prSt = con.prepareStatement("update email_client set user = ?, toText = ?, subject = ?, mailText = ?, theme = ? where ip = ?");
            prSt.setString(1, savedUsername);
            prSt.setString(2, savedToText);
            prSt.setString(3, savedSubject);
            prSt.setString(4, savedMailText);
            prSt.setInt(5, savedTheme);
            prSt.setString(6, ip);
            prSt.execute();
            prSt.close();
            ec.customOptionPane("Your session was saved successfully!   ", "Saved");
            ec.setSessionSaved(true);
        } catch (SQLException e1) {
            e1.printStackTrace();
            ec.errorDialog("There was a problem saving your session.");
        }

    }
}
