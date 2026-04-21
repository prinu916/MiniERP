package com.minierp.ui.faculty;

import com.minierp.ui.components.UITheme;
import com.minierp.controllers.TimetableController;
import com.minierp.models.Timetable;
import com.minierp.services.AuthService;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FacultyTimetableViewUI extends JPanel {
    private final TimetableController ctrl = new TimetableController();
    private JTable table;
    private DefaultTableModel model;
    private static final String[] COLS = {"Day", "Start", "End", "Subject", "Faculty", "Room"};

    public FacultyTimetableViewUI() {
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initUI();
        loadData();
    }

    private void initUI() {
        JLabel title = UITheme.makeLabel("My Timetable", UITheme.FONT_HEADING, UITheme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topBar.setOpaque(false);
        JButton refreshBtn = UITheme.makeButton("Refresh", UITheme.BG_CARD);
        refreshBtn.addActionListener(e -> loadData());
        topBar.add(refreshBtn);

        model = new DefaultTableModel(COLS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = UITheme.makeTable(model);
        JScrollPane scroll = UITheme.makeScrollPane(table);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false);
        northPanel.add(title, BorderLayout.NORTH);
        northPanel.add(topBar, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Timetable> entries = ctrl.getByFacultyId(AuthService.getInstance().getCurrentFaculty().getId());
        String[] dayOrder = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        Map<String, Integer> dayIdx = new HashMap<>();
        for (int i = 0; i < dayOrder.length; i++) dayIdx.put(dayOrder[i], i);
        entries.sort((a, b) -> {
            int d = dayIdx.getOrDefault(a.getDayOfWeek(), 9) - dayIdx.getOrDefault(b.getDayOfWeek(), 9);
            return d != 0 ? d : a.getStartTime().compareTo(b.getStartTime());
        });
        for (Timetable t : entries) {
            model.addRow(new Object[]{t.getDayOfWeek(), t.getStartTime(), t.getEndTime(),
                t.getSubjectName(), t.getFacultyName(), t.getRoom()});
        }
    }
}
