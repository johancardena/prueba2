import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class REGISTRO extends JFrame{
    private JButton GUARDARButton;
    private JTextField txtcedula;
    private JTextField txtnotas;
    private JTextField txtnotas1;
    private JTextField txtnotas2;
    private JTextField txtnotas3;
    private JTextField txtnotas4;
    private JPanel panelRegistro;
    private JTextField[] NOTAS;


    public REGISTRO() {
        setTitle("REGISTRO");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel cedulaLabel = new JLabel("CEDULA:");
        cedulaLabel.setBounds(10, 10, 80, 25);
        add(cedulaLabel);

        txtcedula = new JTextField();
        txtcedula.setBounds(100, 10, 160, 25);
        add(txtcedula);

        NOTAS = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel("MATERIA " + (i + 1) + ":");
            label.setBounds(10, 50 + (i * 30), 80, 25);
            add(label);

            NOTAS[i] = new JTextField();
            NOTAS[i].setBounds(100, 50 + (i * 30), 160, 25);
            add(NOTAS[i]);
        }

        JButton saveButton = new JButton("GUARDAR" );
        saveButton.setBounds(10, 220, 100, 25);
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO estudiantes (cedula, subject1, subject2, subject3, subject4, subject5, average, status) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);

                    double total = 0;
                    for (int i = 0; i < NOTAS.length; i++) {
                        double grade = Double.parseDouble(NOTAS[i].getText());
                        if (grade < 0 || grade > 20) {
                            JOptionPane.showMessageDialog(null, "Calificaciones deben estar entre 0 y 20");
                            return;
                        }
                        ps.setDouble(i + 2, grade);
                        total += grade;
                    }

                    double average = total / 5;
                    ps.setString(1, txtcedula.getText());
                    ps.setDouble(7, average);
                    ps.setString(8, average >= 12 ? "Aprueba" : "Reprueba");

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Datos guardados exitosamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    
    
}
