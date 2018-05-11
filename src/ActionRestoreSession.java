import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ActionRestoreSession implements ActionListener {

    private static EmailClient ec = new EmailClient();
    private static String ip = EmailClient.getIP();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ip == null){
            errorRestoringSession();
            return;
        }

        // getting values from database
        String user = (String)getValue("user");
        String to = (String)getValue("toText");
        String subject = (String)getValue("subject");
        String text = (String)getValue("mailText");
        int theme = Integer.parseInt(getValue("theme"));

        ec.restoreSession(user, to, subject, text, theme);
    }

    private static void errorRestoringSession(){
        //TODO: show error window - "there was a problem restoring your session"
    }

    private static String getValue(String column){
        Statement stmt;
        Connection con = ec.getConnecion();
        String result = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select " + column + " from email_client where ip = '" + ip + "'");
            rs.next();
            result = rs.getString(column);
            rs.close();
            stmt.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return result;
    }
}
