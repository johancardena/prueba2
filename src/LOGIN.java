import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LOGIN extends JFrame {
    private JTextField txtusuario;
    private JPasswordField txtpassword;
    private JButton btningresar;
    private JPanel panelMain;


    public LOGIN() {
        setTitle("LOGIN");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("USUARIO");
        userLabel.setBounds(10, 10, 80, 25);
        add(userLabel);

        txtusuario = new JTextField();
        txtusuario.setBounds(100, 10, 160, 25);
        add(txtusuario);

        JLabel passLabel = new JLabel("CONTRASEÑA");
        passLabel.setBounds(10, 40, 90, 25);
        add(passLabel);

        txtpassword = new JPasswordField();
        txtpassword.setBounds(100, 40, 160, 25);
        add(txtpassword);

        JButton loginButton = new JButton("INGRESAR");
        loginButton.setBounds(10, 80, 100, 25);
        add(loginButton);


    }

    private Connection conectar() {

        return null;
    }

    public static void main(String[] args) {
        new LOGIN().setVisible(true);
    }

    private class LOGINListener implements ActionListener {
        private final JLabel userLabel;

        private LOGINListener(JLabel userLabel) {
            this.userLabel = userLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String usuario = txtusuario.getText();
            String password = new String(txtpassword.getPassword());

            try (Connection conexion = conectar()) {
                if (conexion != null) {
                    String query = "SELECT * FROM Usuarios WHERE Nombre = ? AND Pass = ?";
                    PreparedStatement statement = conexion.prepareStatement(query);
                    statement.setString(1, usuario);
                    statement.setString(2, password);

                    ResultSet resultado = statement.executeQuery();
                    if (resultado.next()) {
                        JOptionPane.showMessageDialog(null, "Login exitoso");
                        userLabel.disable();
                        new REGISTRO();
                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al realizar login: " + ex.getMessage());
            }
        }
    }
}














