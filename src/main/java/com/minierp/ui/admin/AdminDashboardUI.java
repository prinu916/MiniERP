package com.minierp.ui.admin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.minierp.services.AuthService;
import com.minierp.ui.LoginUI;
import com.minierp.ui.components.Sidebar;
import com.minierp.ui.components.UITheme;
import com.minierp.ui.faculty.ExamSchedulerUI;
import com.minierp.ui.faculty.NotificationPanel;
import com.minierp.ui.faculty.ResultManagementUI;

public class AdminDashboardUI extends JFrame {

    private final AuthService auth = AuthService.getInstance();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private Sidebar sidebar;

    public AdminDashboardUI() {
        setTitle("MiniERP — Admin Panel");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {

        // ✅ Sidebar (logo already inside Sidebar.java)
        sidebar = new Sidebar("Admin", "SYSTEM ADMINISTRATOR");

        sidebar.addSectionLabel("Management");
        sidebar.addNavItem("👨‍🏫", "Manage Teachers", "teachers");
        sidebar.addNavItem("📅", "Timetable", "timetable");
        sidebar.addNavItem("📋", "Exam Schedule", "exams");
        sidebar.addNavItem("🏆", "Results", "results");
        sidebar.addNavItem("🔔", "Notifications", "notifications");
        sidebar.addNavItem("📊", "System Stats", "stats");

        // ✅ Navigation handling
        sidebar.setOnNavigate(key -> {

            if ("logout".equals(key)) {
                UIManager.put("OptionPane.background", UITheme.BG_DARK);
                UIManager.put("OptionPane.messageForeground", UITheme.TEXT_PRIMARY);
                UIManager.put("OptionPane.buttonForeground", UITheme.TEXT_PRIMARY);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );
                UIManager.put("OptionPane.background", null); // reset

                if (confirm == JOptionPane.YES_OPTION) {
                    auth.logout();
                    dispose();
                    new LoginUI().setVisible(true);
                }

            } else {
                cardLayout.show(contentPanel, key);
                sidebar.setSelected(key);
            }
        });

        // ✅ Panels
        contentPanel.setBackground(UITheme.BG_DARK);

        contentPanel.add(new TeacherManagementUI(), "teachers");
        contentPanel.add(new TimetableManagementUI(), "timetable");
        contentPanel.add(new ExamSchedulerUI(), "exams");
        contentPanel.add(new ResultManagementUI(), "results");
        contentPanel.add(new NotificationPanel(), "notifications");

        // ✅ Stats placeholder
        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setBackground(UITheme.BG_DARK);
        JLabel statsLabel = new JLabel("📊 System Statistics Coming Soon");
        statsLabel.setForeground(Color.WHITE);
        statsPanel.add(statsLabel, new GridBagConstraints());

        contentPanel.add(statsPanel, "stats");

        // ✅ Layout
        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // ✅ Default screen
        cardLayout.show(contentPanel, "teachers");
        sidebar.setSelected("teachers");
    }
}