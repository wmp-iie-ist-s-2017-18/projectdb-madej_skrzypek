package program;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DbConnection {
    
    Connection conn = null;
    
    public static Connection ConnectDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:apteka.db");
            JOptionPane.showMessageDialog(null, "Połączono z " +conn);
            return conn;
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Nie połączono z bazą danych");
            return null;
        }
    }
    
}
