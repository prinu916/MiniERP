package com.minierp.ui.faculty;

import com.minierp.ui.components.*;
import com.minierp.ui.LoginUI;
import com.minierp.services.*;
import com.minierp.controllers.*;
import com.minierp.config.AppConfig;

import javax.swing.*;
import java.awt.*;

public class FacultyDashboardUI extends JFrame {
<<<<<<< HEAD

=======
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
    private final AuthService auth = AuthService.getInstance();
    private final StudentController studentCtrl = new StudentController();
    private final ExamController examCtrl = new ExamController();
    private final NotificationService notifService = new NotificationService();

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private Navbar navbar;
    private Sidebar sidebar;

<<<<<<< HEAD
=======
    // Sub-panels
    private JPanel dashboardPanel;
    private StudentManagementUI studentPanel;
    private AttendanceUI attendancePanel;
    private AssignmentUI assignmentPanel;
    private TimetableUI timetablePanel;
    private ExamSchedulerUI examPanel;
    private ResultManagementUI resultPanel;
    private LostFoundManagementUI lostFoundPanel;
    private NotificationPanel notifPanel;

>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
    public FacultyDashboardUI() {
        setTitle(AppConfig.APP_NAME + " — Faculty Dashboard");
        setSize(AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 650));
<<<<<<< HEAD

=======
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
        initUI();
        navigateTo("dashboard");
    }

    private void initUI() {
<<<<<<< HEAD

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
=======
        String name = auth.getCurrentFaculty() != null ? auth.getCurrentFaculty().getName() : auth.getCurrentUsername();
        sidebar = new Sidebar(name, auth.getCurrentRole());
        sidebar.addSectionLabel("Main");
        sidebar.addNavItem("📊", "Dashboard", "dashboard");
        sidebar.addNavItem("👥", "Students", "students");
        sidebar.addSectionLabel("Academic");
        sidebar.addNavItem("✅", "Attendance", "attendance");
        sidebar.addNavItem("📝", "Assignments", "assignments");
        sidebar.addNavItem("🗓", "Timetable", "timetable");
        sidebar.addNavItem("📋", "Exam Scheduler", "exams");
        sidebar.addNavItem("🏆", "Results", "results");
        sidebar.addSectionLabel("Other");
        sidebar.addNavItem("🔔", "Notifications", "notifications");
        sidebar.addNavItem("🔍", "Lost & Found", "lostfound");
        sidebar.setOnNavigate(this::navigateTo);

        navbar = new Navbar("Dashboard",
            () -> notifService.getUnreadCount(auth.getCurrentRole()));

>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UITheme.BG_DARK);

<<<<<<< HEAD
        contentPanel.add(buildDashboard(), "dashboard");
        contentPanel.add(new StudentManagementUI(), "students");
        contentPanel.add(new AttendanceUI(), "attendance");
        contentPanel.add(new AssignmentUI(), "assignments");
        contentPanel.add(new FacultyTimetableViewUI(), "timetable");
        contentPanel.add(new ExamSchedulerUI(), "exams");
        contentPanel.add(new ResultManagementUI(), "results");
        contentPanel.add(new LostFoundManagementUI(), "lostfound");
        contentPanel.add(new NotificationPanel(), "notifications");
=======
        // Build dashboard home
        dashboardPanel = buildDashboard();
        studentPanel    = new StudentManagementUI();
        attendancePanel = new AttendanceUI();
        assignmentPanel = new AssignmentUI();
        timetablePanel  = new TimetableUI();
        examPanel       = new ExamSchedulerUI();
        resultPanel     = new ResultManagementUI();
        lostFoundPanel  = new LostFoundManagementUI();
        notifPanel      = new NotificationPanel();

        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(studentPanel, "students");
        contentPanel.add(attendancePanel, "attendance");
        contentPanel.add(assignmentPanel, "assignments");
        contentPanel.add(timetablePanel, "timetable");
        contentPanel.add(examPanel, "exams");
        contentPanel.add(resultPanel, "results");
        contentPanel.add(lostFoundPanel, "lostfound");
        contentPanel.add(notifPanel, "notifications");
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UITheme.BG_DARK);
        main.add(navbar, BorderLayout.NORTH);
        main.add(contentPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);
    }

    private void navigateTo(String key) {
<<<<<<< HEAD

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
=======
        if ("logout".equals(key)) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                auth.logout();
                dispose();
                SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
            }
            return;
        }
        sidebar.setSelected(key);
        navbar.setTitle(getTitleFor(key));
        cardLayout.show(contentPanel, key);
        // Refresh on navigate
        if ("dashboard".equals(key)) refreshDashboard();
    }

    private String getTitleFor(String key) {
        return switch (key) {
            case "dashboard"    -> "Faculty Dashboard";
            case "students"     -> "Student Management";
            case "attendance"   -> "Attendance Management";
            case "assignments"  -> "Assignment Management";
            case "timetable"    -> "Timetable Manager";
            case "exams"        -> "Exam Scheduler";
            case "results"      -> "Result Management";
            case "lostfound"    -> "Lost & Found Management";
            case "notifications"-> "Notifications";
            default             -> "Dashboard";
        };
    }

    private JPanel buildDashboard() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel welcome = new JLabel("Welcome back! Here's your overview.");
        welcome.setFont(UITheme.FONT_BODY);
        welcome.setForeground(UITheme.TEXT_MUTED);

        // Cards row
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 16, 0));
        cardsPanel.setOpaque(false);

        int students = studentCtrl.getTotalCount();
        int exams    = examCtrl.getExamCount();
        int notifs   = notifService.getUnreadCount("FACULTY");

        cardsPanel.add(new DashboardCard("Total Students", String.valueOf(students), "Enrolled", UITheme.SUCCESS));
        cardsPanel.add(new DashboardCard("Upcoming Exams", String.valueOf(examCtrl.getUpcoming().size()), "Scheduled", UITheme.WARNING));
        cardsPanel.add(new DashboardCard("Total Exams", String.valueOf(exams), "All time", UITheme.ACCENT));
        cardsPanel.add(new DashboardCard("Notifications", String.valueOf(notifs), "Unread", UITheme.ACCENT2));

        // Quick actions
        JPanel quickPanel = new JPanel(new BorderLayout(0, 12));
        quickPanel.setOpaque(false);

        JLabel qTitle = UITheme.makeLabel("Quick Actions", UITheme.FONT_HEADING, UITheme.TEXT_PRIMARY);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btnPanel.setOpaque(false);

        JButton addStudentBtn = UITheme.makeAccentButton("+ Add Student");
        JButton markAttendBtn = UITheme.makeSuccessButton("✅ Mark Attendance");
        JButton scheduleExamBtn = UITheme.makeButton("📋 Schedule Exam", UITheme.BG_CARD);
        JButton addAssignBtn = UITheme.makeButton("📝 New Assignment", UITheme.BG_CARD);

        addStudentBtn.addActionListener(e -> navigateTo("students"));
        markAttendBtn.addActionListener(e -> navigateTo("attendance"));
        scheduleExamBtn.addActionListener(e -> navigateTo("exams"));
        addAssignBtn.addActionListener(e -> navigateTo("assignments"));

        btnPanel.add(addStudentBtn);
        btnPanel.add(markAttendBtn);
        btnPanel.add(scheduleExamBtn);
        btnPanel.add(addAssignBtn);

        quickPanel.add(qTitle, BorderLayout.NORTH);
        quickPanel.add(btnPanel, BorderLayout.CENTER);

        // Upcoming exams table
        JPanel examSection = new JPanel(new BorderLayout(0, 10));
        examSection.setOpaque(false);
        JLabel examTitle = UITheme.makeLabel("Upcoming Exams", UITheme.FONT_HEADING, UITheme.TEXT_PRIMARY);

        String[] cols = {"#", "Exam", "Subject", "Date", "Time", "Venue", "Max Marks"};
        var upcomingExams = examCtrl.getUpcoming();
        Object[][] data = new Object[Math.min(upcomingExams.size(), 8)][7];
        for (int i = 0; i < data.length; i++) {
            var ex = upcomingExams.get(i);
            data[i] = new Object[]{i+1, ex.getTitle(), ex.getSubjectName(), ex.getExamDate(),
                ex.getStartTime() + "-" + ex.getEndTime(), ex.getVenue(), ex.getMaxMarks()};
        }

        var tableModel = new javax.swing.table.DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = UITheme.makeTable(tableModel);
        JScrollPane sp = UITheme.makeScrollPane(table);
        sp.setPreferredSize(new Dimension(0, 200));

        examSection.add(examTitle, BorderLayout.NORTH);
        examSection.add(sp, BorderLayout.CENTER);

        panel.add(welcome, BorderLayout.NORTH);
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.add(cardsPanel);
        center.add(Box.createVerticalStrut(20));
        center.add(quickPanel);
        center.add(Box.createVerticalStrut(20));
        center.add(examSection);
        panel.add(center, BorderLayout.CENTER);

        panel.putClientProperty("cardsPanel", cardsPanel);
        return panel;
    }

    private void refreshDashboard() {
        // Cards are rebuilt on first load; for live refresh remove and re-add
    }
}
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
