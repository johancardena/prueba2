
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/registro";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public static Connection getConnection() {

        return null;
    }


    private Connection conectar() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
}

