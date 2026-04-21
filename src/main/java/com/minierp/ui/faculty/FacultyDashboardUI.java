package com.minierp.ui.faculty;

import com.minierp.ui.components.*;
import com.minierp.ui.LoginUI;
import com.minierp.services.*;
import com.minierp.controllers.*;
import com.minierp.config.AppConfig;

import javax.swing.*;
import java.awt.*;

public class FacultyDashboardUI extends JFrame {

    private final AuthService auth = AuthService.getInstance();
    private final StudentController studentCtrl = new StudentController();
    private final ExamController examCtrl = new ExamController();
    private final NotificationService notifService = new NotificationService();

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private Navbar navbar;
    private Sidebar sidebar;

    public FacultyDashboardUI() {
        setTitle(AppConfig.APP_NAME + " — Faculty Dashboard");
        setSize(AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 650));

        initUI();
        navigateTo("dashboard");
    }

    private void initUI() {

        String name = (auth.getCurrentFaculty() != null)
                ? auth.getCurrentFaculty().getName()
                : "Faculty";

        String role = (auth.getCurrentRole() != null)
                ? auth.getCurrentRole()
                : "FACULTY";

        // ✅ Sidebar
        sidebar = new Sidebar(name, role);
        sidebar.addSectionLabel("Main");
        sidebar.addNavItem("📊", "Dashboard", "dashboard");
        sidebar.addNavItem("👥", "Students", "students");

        sidebar.addSectionLabel("Academic");
        sidebar.addNavItem("✅", "Attendance", "attendance");
        sidebar.addNavItem("📝", "Assignments", "assignments");
        sidebar.addNavItem("🗓", "My Timetable", "timetable");
        sidebar.addNavItem("📋", "Exam Scheduler", "exams");
        sidebar.addNavItem("🏆", "Results", "results");

        sidebar.addSectionLabel("Other");
        sidebar.addNavItem("🔔", "Notifications", "notifications");
        sidebar.addNavItem("🔍", "Lost & Found", "lostfound");

        sidebar.setOnNavigate(this::navigateTo);

        // ✅ Navbar (simple title)
        navbar = new Navbar("Faculty Dashboard",
                () -> notifService.getUnreadCount(role));

        // ✅ Content Panel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UITheme.BG_DARK);

        contentPanel.add(buildDashboard(), "dashboard");
        contentPanel.add(new StudentManagementUI(), "students");
        contentPanel.add(new AttendanceUI(), "attendance");
        contentPanel.add(new AssignmentUI(), "assignments");
        contentPanel.add(new FacultyTimetableViewUI(), "timetable");
        contentPanel.add(new ExamSchedulerUI(), "exams");
        contentPanel.add(new ResultManagementUI(), "results");
        contentPanel.add(new LostFoundManagementUI(), "lostfound");
        contentPanel.add(new NotificationPanel(), "notifications");

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UITheme.BG_DARK);
        main.add(navbar, BorderLayout.NORTH);
        main.add(contentPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);
    }

    private void navigateTo(String key) {

        if ("logout".equals(key)) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                auth.logout();
                dispose();
                new LoginUI().setVisible(true);
            }
            return;
        }

        sidebar.setSelected(key);
        navbar.setTitle(getTitleFor(key));
        cardLayout.show(contentPanel, key);
    }

    // ✅ Compatible with all Java versions
    private String getTitleFor(String key) {

        switch (key) {
            case "dashboard": return "Faculty Dashboard";
            case "students": return "Student Management";
            case "attendance": return "Attendance";
            case "assignments": return "Assignments";
            case "timetable": return "My Timetable";
            case "exams": return "Exam Scheduler";
            case "results": return "Results";
            case "lostfound": return "Lost & Found";
            case "notifications": return "Notifications";
            default: return "Dashboard";
        }
    }

    private JPanel buildDashboard() {

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcome = new JLabel("Welcome back!");
        welcome.setForeground(Color.WHITE);

        JPanel cards = new JPanel(new GridLayout(1, 3, 10, 10));
        cards.setOpaque(false);

        cards.add(new DashboardCard("Students",
                String.valueOf(studentCtrl.getTotalCount()),
                "Total", UITheme.SUCCESS));

        cards.add(new DashboardCard("Exams",
                String.valueOf(examCtrl.getExamCount()),
                "Total", UITheme.WARNING));

        cards.add(new DashboardCard("Notifications",
                String.valueOf(notifService.getUnreadCount("FACULTY")),
                "Unread", UITheme.ACCENT));

        panel.add(welcome, BorderLayout.NORTH);
        panel.add(cards, BorderLayout.CENTER);

        return panel;
    }
}