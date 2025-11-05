import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Stack;

public class TimetablePanel extends JPanel {

    private final JTable table;
    private final JTextField txtSubject;
    private final JTextField txtCredits;
    private final JTextField txtTeacher;
    private final JComboBox<String> dayBox;
    private final JComboBox<String> timeBox;

    private static Stack<String[]> timetableStack = new Stack<>();

    public TimetablePanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));

        // --- Title ---
        JLabel title = new JLabel("📘 Class Timetable", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(20, 60, 90));
        add(title, BorderLayout.NORTH);

        // --- Form Section ---
        JPanel form = new JPanel(new GridLayout(3, 4, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Add / Update Timetable Record"));
        form.setBackground(Color.WHITE);

        txtSubject = new JTextField();
        txtCredits = new JTextField();
        txtTeacher = new JTextField();

        // Dropdown for Days
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        dayBox = new JComboBox<>(days);

        // Dropdown for Time Slots (8 AM to 3 PM)
        String[] times = {
            "8:00 AM - 9:00 AM", "9:00 AM - 10:00 AM", 
            "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM",
            "12:00 PM - 1:00 PM", "1:00 PM - 2:00 PM",
            "2:00 PM - 3:00 PM"
        };
        timeBox = new JComboBox<>(times);

        // Buttons
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");

        styleButton(btnAdd, new Color(70, 130, 180));
        styleButton(btnUpdate, new Color(0, 153, 102));

        // Add Fields
        form.add(new JLabel("Subject:"));
        form.add(txtSubject);
        form.add(new JLabel("Credit Hours:"));
        form.add(txtCredits);
        form.add(new JLabel("Subject Teacher:"));
        form.add(txtTeacher);
        form.add(new JLabel("Day:"));
        form.add(dayBox);
        form.add(new JLabel("Time Slot:"));
        form.add(timeBox);
        form.add(btnAdd);
        form.add(btnUpdate);

        add(form, BorderLayout.CENTER);

        // --- Table Section ---
        table = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Subject", "Credit Hours", "Teacher", "Day", "Time"}, 0
        ));
        styleTable();
        add(new JScrollPane(table), BorderLayout.SOUTH);

        // --- Actions ---
        btnAdd.addActionListener(e -> addTimetable());
        btnUpdate.addActionListener(e -> updateTimetable());

        // Fill selected row into form for editing
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtSubject.setText(table.getValueAt(row, 1).toString());
                txtCredits.setText(table.getValueAt(row, 2).toString());
                txtTeacher.setText(table.getValueAt(row, 3).toString());
                dayBox.setSelectedItem(table.getValueAt(row, 4).toString());
                timeBox.setSelectedItem(table.getValueAt(row, 5).toString());
            }
        });
    }

    // Button Style
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    // Table Style
    private void styleTable() {
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(20, 60, 90));
        table.getTableHeader().setForeground(Color.WHITE);
    }

    // Add Timetable Record
    private void addTimetable() {
        String subject = txtSubject.getText().trim();
        String credits = txtCredits.getText().trim();
        String teacher = txtTeacher.getText().trim();
        String day = (String) dayBox.getSelectedItem();
        String time = (String) timeBox.getSelectedItem();

        if (subject.isEmpty() || credits.isEmpty() || teacher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!");
            return;
        }

        String id = "T" + (timetableStack.size() + 1);
        timetableStack.push(new String[]{id, subject, credits, teacher, day, time});

        JOptionPane.showMessageDialog(this, "✅ Timetable record added successfully!");
        clearFields();
        refreshTable();
    }

    // Update Timetable Record
    private void updateTimetable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a record to update!");
            return;
        }

        String id = (String) table.getValueAt(selectedRow, 0);
        Stack<String[]> temp = new Stack<>();

        while (!timetableStack.isEmpty()) {
            String[] record = timetableStack.pop();
            if (record[0].equals(id)) {
                record[1] = txtSubject.getText().trim();
                record[2] = txtCredits.getText().trim();
                record[3] = txtTeacher.getText().trim();
                record[4] = (String) dayBox.getSelectedItem();
                record[5] = (String) timeBox.getSelectedItem();
            }
            temp.push(record);
        }

        while (!temp.isEmpty()) timetableStack.push(temp.pop());
        JOptionPane.showMessageDialog(this, "✅ Timetable record updated successfully!");
        clearFields();
        refreshTable();
    }

    // Refresh Table
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (String[] record : timetableStack) {
            model.addRow(record);
        }
    }

    // Clear Input Fields
    private void clearFields() {
        txtSubject.setText("");
        txtCredits.setText("");
        txtTeacher.setText("");
        dayBox.setSelectedIndex(0);
        timeBox.setSelectedIndex(0);
    }
}
