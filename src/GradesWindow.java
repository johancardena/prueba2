import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class GradesWindow extends JFrame {
    private JTextField cedulaField;
    private JTextField[] gradesFields;

    public GradesWindow() {
        setTitle("Gestión de Calificaciones");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaLabel.setBounds(10, 10, 80, 25);
        add(cedulaLabel);

        cedulaField = new JTextField();
        cedulaField.setBounds(100, 10, 160, 25);
        add(cedulaField);

        gradesFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel("Materia " + (i + 1) + ":");
            label.setBounds(10, 50 + (i * 30), 80, 25);
            add(label);

            gradesFields[i] = new JTextField();
            gradesFields[i].setBounds(100, 50 + (i * 30), 160, 25);
            add(gradesFields[i]);
        }

        JButton saveButton = new JButton("Guardar");
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
                    for (int i = 0; i < gradesFields.length; i++) {
                        double grade = Double.parseDouble(gradesFields[i].getText());
                        if (grade < 0 || grade > 20) {
                            JOptionPane.showMessageDialog(null, "Calificaciones deben estar entre 0 y 20");
                            return;
                        }
                        ps.setDouble(i + 2, grade);
                        total += grade;
                    }

                    double average = total / 5;
                    ps.setString(1, cedulaField.getText());
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
