import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainDashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainDashboard() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar (Left)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5, 1, 5, 10));
        sidebar.setBackground(new Color(30, 30, 30));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton btnDashboard = new JButton("🏠 Dashboard");
        JButton btnStudents = new JButton("👨‍🎓 Students");
        JButton btnCourses = new JButton("📚 Courses");
        JButton btnFee = new JButton("💰 Fee");
        JButton btnTimetable = new JButton("🕒 Timetable");

        JButton[] buttons = { btnDashboard, btnStudents, btnCourses, btnFee, btnTimetable };
        for (JButton b : buttons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(45, 45, 45));
            b.setForeground(Color.WHITE);
            sidebar.add(b);
        }

        // Content area
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Dashboard Panel (Home screen with icons)
        JPanel dashboardPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        dashboardPanel.add(createIconPanel("Students", "👨‍🎓"));
        dashboardPanel.add(createIconPanel("Courses", "📚"));
        dashboardPanel.add(createIconPanel("Fee", "💰"));
        dashboardPanel.add(createIconPanel("Timetable", "🕒"));

        // Add other panels
        JPanel studentPanel = new StudentPanel();
        JPanel coursePanel = new CoursePanel();
        JPanel feePanel = new FeePanel();        // Added new Fee feature
        JPanel timetablePanel = new TimetablePanel();

        // Add to content panel
        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(studentPanel, "Students");
        contentPanel.add(coursePanel, "Courses");
        contentPanel.add(feePanel, "Fee");
        contentPanel.add(timetablePanel, "Timetable");

        // Action listeners for sidebar
        btnDashboard.addActionListener(e -> cardLayout.show(contentPanel, "Dashboard"));
        btnStudents.addActionListener(e -> cardLayout.show(contentPanel, "Students"));
        btnCourses.addActionListener(e -> cardLayout.show(contentPanel, "Courses"));
        btnFee.addActionListener(e -> cardLayout.show(contentPanel, "Fee"));
        btnTimetable.addActionListener(e -> cardLayout.show(contentPanel, "Timetable"));

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createIconPanel(String name, String emoji) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setLayout(new BorderLayout());

        JLabel icon = new JLabel(emoji, SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(icon, BorderLayout.CENTER);
        panel.add(label, BorderLayout.SOUTH);

        // Click event to open that panel
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, name);
            }

            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(220, 220, 220));
            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(245, 245, 245));
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDashboard());
    }
}
