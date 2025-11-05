

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class EnrolmentPanel extends JPanel {

    private JComboBox<String> studentComboBox, courseComboBox;
    private JTable enrolmentTable;
    private DefaultTableModel tableModel;

    public EnrolmentPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // ===== Top Title Panel =====
        JLabel titleLabel = new JLabel("📘 Enroll Student in Course");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 60, 90));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(titleLabel, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels
        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Dropdowns
        studentComboBox = new JComboBox<>(new String[]{"Bilal Hussain", "Asad Ali", "Ayesha Khan"});
        studentComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        courseComboBox = new JComboBox<>(new String[]{"Software Engineering", "Data Structures", "DBMS"});
        courseComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Button
        JButton enrollBtn = new JButton("Enroll Student");
        enrollBtn.setBackground(new Color(40, 120, 220));
        enrollBtn.setForeground(Color.WHITE);
        enrollBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        enrollBtn.setFocusPainted(false);
        enrollBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // Hover effect
        enrollBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                enrollBtn.setBackground(new Color(30, 100, 200));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                enrollBtn.setBackground(new Color(40, 120, 220));
            }
        });

        // Add to layout
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(studentLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(studentComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(courseLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(courseComboBox, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(enrollBtn, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ===== Table Section =====
        String[] columns = {"Enrollment ID", "Student Name", "Course Name", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        enrolmentTable = new JTable(tableModel);
        enrolmentTable.setRowHeight(25);
        enrolmentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        enrolmentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(enrolmentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scrollPane, BorderLayout.SOUTH);

        // ===== Action =====
        enrollBtn.addActionListener((ActionEvent e) -> {
            String student = (String) studentComboBox.getSelectedItem();
            String course = (String) courseComboBox.getSelectedItem();
            String id = "E" + (tableModel.getRowCount() + 1);
            String date = LocalDate.now().toString();

            tableModel.addRow(new Object[]{id, student, course, date});
            JOptionPane.showMessageDialog(this, "✅ Student Enrolled Successfully!");
        });
    }
}
