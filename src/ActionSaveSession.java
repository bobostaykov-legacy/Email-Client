import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class ActionSaveSession implements ActionListener {

    private EmailClient ec = new EmailClient();

    @Override
    public void actionPerformed(ActionEvent e) {

        // get text from all fields (except password) + theme
        Statement stmt;
        PreparedStatement prSt;
        String ip = null;
        boolean isInDatabase = false;
        String savedUsername = ec.getUsername();
        String savedToText = ec.getToText();
        String savedSubject = ec.getSubject();
        String savedMailText = ec.getMailText();
        int savedTheme = 0;

        if (ec.getTheme() == Theme.BLUE) savedTheme = 1;
        if (ec.getTheme() == Theme.PINK) savedTheme = 2;
        if (ec.getTheme() == Theme.BLACK) savedTheme = 3;

        // get the IP address of the computer on which the program is being used,
        // that way the database knows which information to resore
        try {
            InetAddress IP = InetAddress.getLocalHost();
            ip = IP.toString();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            errorSavingSession();
        }

        // create a connection to the database
        Connection con = ec.getConnecion();
        if (con == null){
            errorSavingSession();
            return;
        }

        // see if the ip address is already in the database
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ip from email_client");
            while (rs.next()) {
                String ips = rs.getString("ip");
                if (ip.equals(ips)){
                    isInDatabase = true;
                    break;
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        if (isInDatabase) {
            // if the ip is already in the database, this means this is not the first time a session is being saved on this computer
            // update values for every text field (except password)
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
            } catch (SQLException e1) {
                e1.printStackTrace();
                errorSavingSession();
            }
        } else {
            // else, save text from all fields (except password) + theme
            try {
                prSt = con.prepareStatement("insert into email_client values (?, ?, ?, ?, ?, ?)");
                prSt.setString(1, ip);
                prSt.setString(2, savedUsername);
                prSt.setString(3, savedToText);
                prSt.setString(4, savedSubject);
                prSt.setString(5, savedMailText);
                prSt.setInt(6, savedTheme);
                prSt.execute();
                prSt.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                errorSavingSession();
            }
        }

    }

    private static void errorSavingSession(){
        //TODO: show error window - "there was a problem saving your session"
    }
}
