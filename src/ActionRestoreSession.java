import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ActionRestoreSession implements ActionListener {

    EmailClient ec = new EmailClient();

    @Override
    public void actionPerformed(ActionEvent e) {
        Connection con = ec.getConnecion();
    }
}
