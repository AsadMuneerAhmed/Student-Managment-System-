import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Stack;

class FeeRecord {
    private int id;
    private String rollNo;
    private double amount;
    private String status;
    private String date;

    public FeeRecord(int id, String rollNo, double amount, String status, String date) {
        this.id = id;
        this.rollNo = rollNo;
        this.amount = amount;
        this.status = status;
        this.date = date;
    }

    public int getId() { return id; }
    public String getRollNo() { return rollNo; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getDate() { return date; }

    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setStatus(String status) { this.status = status; }
    public void setDate(String date) { this.date = date; }
}

public class FeePanel extends JPanel {
    private JTextField rollNoField, amountField, dateField;
    private JComboBox<String> statusBox;
    private JTable table;
    private DefaultTableModel model;

    private static final Stack<FeeRecord> feeStack = new Stack<>();
    private static int idCounter = 1;

    public FeePanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));

        // ---------- Title ----------
        JLabel title = new JLabel("💰 Fee Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(40, 53, 147));
        add(title, BorderLayout.NORTH);

        // ---------- Form ----------
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Fee Details"));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Roll No:"));
        rollNoField = new JTextField();
        formPanel.add(rollNoField);

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Status:"));
        statusBox = new JComboBox<>(new String[]{"Paid", "Unpaid"});
        formPanel.add(statusBox);

        formPanel.add(new JLabel("Date:"));
        dateField = new JTextField("YYYY-MM-DD");
        formPanel.add(dateField);

        add(formPanel, BorderLayout.WEST);

        // ---------- Buttons ----------
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(new Color(245, 247, 250));

        JButton addBtn = createStyledButton("Add");
        JButton updateBtn = createStyledButton("Update");
        JButton deleteBtn = createStyledButton("Delete");
        JButton clearBtn = createStyledButton("Clear");

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // ---------- Table ----------
        model = new DefaultTableModel(new String[]{"ID", "Roll No", "Amount", "Status", "Date"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---------- Button Actions ----------
        addBtn.addActionListener(e -> addFee());
        updateBtn.addActionListener(e -> updateFee());
        deleteBtn.addActionListener(e -> deleteFee());
        clearBtn.addActionListener(e -> clearFields());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                rollNoField.setText(model.getValueAt(row, 1).toString());
                amountField.setText(model.getValueAt(row, 2).toString());
                statusBox.setSelectedItem(model.getValueAt(row, 3).toString());
                dateField.setText(model.getValueAt(row, 4).toString());
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(63, 81, 181));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(48, 63, 159));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(63, 81, 181));
            }
        });
        return btn;
    }

    private void addFee() {
        String rollNo = rollNoField.getText().trim();
        String amtText = amountField.getText().trim();
        String status = (String) statusBox.getSelectedItem();
        String date = dateField.getText().trim();

        if (rollNo.isEmpty() || amtText.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!");
            return;
        }

        try {
            double amount = Double.parseDouble(amtText);
            FeeRecord fr = new FeeRecord(idCounter++, rollNo, amount, status, date);
            feeStack.push(fr);
            JOptionPane.showMessageDialog(this, "✅ Fee record added successfully!");
            clearFields();
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Amount must be a valid number!");
        }
    }

    private void updateFee() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Select a record to update.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        Stack<FeeRecord> tempStack = new Stack<>();

        while (!feeStack.isEmpty()) {
            FeeRecord fr = feeStack.pop();
            if (fr.getId() == id) {
                fr.setRollNo(rollNoField.getText());
                fr.setAmount(Double.parseDouble(amountField.getText()));
                fr.setStatus((String) statusBox.getSelectedItem());
                fr.setDate(dateField.getText());
            }
            tempStack.push(fr);
        }

        while (!tempStack.isEmpty()) feeStack.push(tempStack.pop());
        JOptionPane.showMessageDialog(this, "✅ Record updated!");
        refreshTable();
    }

    private void deleteFee() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Select a record to delete.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        Stack<FeeRecord> tempStack = new Stack<>();

        while (!feeStack.isEmpty()) {
            FeeRecord fr = feeStack.pop();
            if (fr.getId() != id) tempStack.push(fr);
        }

        while (!tempStack.isEmpty()) feeStack.push(tempStack.pop());
        JOptionPane.showMessageDialog(this, "🗑️ Record deleted!");
        refreshTable();
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (FeeRecord fr : feeStack) {
            model.addRow(new Object[]{fr.getId(), fr.getRollNo(), fr.getAmount(), fr.getStatus(), fr.getDate()});
        }
    }

    private void clearFields() {
        rollNoField.setText("");
        amountField.setText("");
        dateField.setText("YYYY-MM-DD");
        statusBox.setSelectedIndex(0);
    }
}
