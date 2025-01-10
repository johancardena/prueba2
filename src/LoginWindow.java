

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 10, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 10, 160, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10, 40, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 40, 160, 25);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, usernameField.getText());
                    ps.setString(2, new String(passwordField.getPassword()));

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login exitoso");
                        new GradesWindow().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginWindow().setVisible(true);
    }
}
