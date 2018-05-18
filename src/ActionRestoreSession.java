import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ActionRestoreSession implements ActionListener {

    private static EmailClient ec = new EmailClient();
    private static String ip = EmailClient.getIP();

    @Override
    public void actionPerformed(ActionEvent e) {

        ActionSaveSession ss = new ActionSaveSession();
        if (!ec.ipInDatabase()) {
            ec.errorDialog("You haven't saved any session yet.");
            return;
        }

        if (ip == null){
            ec.errorDialog("There was an error restoring your session.");
            return;
        }

        // getting values from database
        String user = getValue("user");
        String to = getValue("toText");
        String subject = getValue("subject");
        String text = getValue("message");
        int theme = Integer.parseInt(getValue("theme"));

        ec.restoreSession(user, to, subject, text, theme);
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
