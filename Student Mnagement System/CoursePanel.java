

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class CoursePanel extends JPanel {
    private JTextField nameField, codeField, creditField;
    private JTable table;
    private DefaultTableModel model;

    public CoursePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250)); // light gray background

        // 🔹 Header
        JLabel title = new JLabel("Course Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(33, 37, 41));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // 🔹 Center layout (Form + Table)
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBackground(getBackground());

        // ✅ Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField();
        codeField = new JTextField();
        creditField = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Course Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Course Code:"), gbc);
        gbc.gridx = 1;
        formPanel.add(codeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        formPanel.add(creditField, gbc);

        // ✅ Buttons
        JButton btnAdd = styledButton("➕ Add");
        JButton btnUpdate = styledButton("✏️ Update");
        JButton btnDelete = styledButton("🗑️ Delete");
        JButton btnClear = styledButton("🧹 Clear");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // ✅ Table
        String[] columns = {"Course Name", "Course Code", "Credits"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(233, 236, 239));
        table.setSelectionBackground(new Color(0, 123, 255));
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // ✅ Button Actions
        btnAdd.addActionListener(e -> {
            if (isEmpty()) return;
            model.addRow(new Object[]{nameField.getText(), codeField.getText(), creditField.getText()});
            clearFields();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a course to update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (isEmpty()) return;
            model.setValueAt(nameField.getText(), row, 0);
            model.setValueAt(codeField.getText(), row, 1);
            model.setValueAt(creditField.getText(), row, 2);
            clearFields();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) model.removeRow(row);
            else JOptionPane.showMessageDialog(this, "Select a row to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnClear.addActionListener(e -> clearFields());

        // Table row selection
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                nameField.setText(model.getValueAt(row, 0).toString());
                codeField.setText(model.getValueAt(row, 1).toString());
                creditField.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    // ✅ Reusable styled button (Blue Theme)
    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 123, 255));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 86, 179)); // hover color
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0, 123, 255));
            }
        });
        return btn;
    }

    private boolean isEmpty() {
        if (nameField.getText().trim().isEmpty() ||
            codeField.getText().trim().isEmpty() ||
            creditField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private void clearFields() {
        nameField.setText("");
        codeField.setText("");
        creditField.setText("");
    }
}
