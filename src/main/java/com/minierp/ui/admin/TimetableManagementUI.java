package com.minierp.ui.admin;


import com.minierp.controllers.TimetableController;
import com.minierp.models.Timetable;
import com.minierp.ui.components.UITheme;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TimetableManagementUI extends JPanel {
    private final TimetableController ctrl = new TimetableController();
    private JTable table;
    private DefaultTableModel model;
    private static final String[] COLS = {"Day", "Start", "End", "Subject", "Faculty", "Room", "ID"};

    public TimetableManagementUI() {
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initUI();
        loadData();
    }

    private void initUI() {
        JLabel title = UITheme.makeLabel("All Faculty Timetables - Admin", UITheme.FONT_HEADING, UITheme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topBar.setOpaque(false);

        JButton addBtn = UITheme.makeAccentButton("+ Add Timetable Entry");
        JButton deleteBtn = UITheme.makeDangerButton("🗑️ Delete Selected");
        JButton refreshBtn = UITheme.makeButton("Refresh", UITheme.BG_CARD);

        // Admin always sees buttons
        // UIUtils.setAdminOnly(addBtn, deleteBtn); // Already in admin context

        addBtn.addActionListener(e -> showAddDialog());
        deleteBtn.addActionListener(e -> deleteSelected());
        refreshBtn.addActionListener(e -> loadData());

        topBar.add(addBtn);
        topBar.add(deleteBtn);
        topBar.add(refreshBtn);

        model = new DefaultTableModel(COLS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = UITheme.makeTable(model);
        table.getColumn("ID").setMaxWidth(60);
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
        List<Timetable> entries = ctrl.getAll();
        // Sort logic same as view
        String[] dayOrder = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        java.util.Map<String, Integer> dayIdx = new java.util.HashMap<>();
        for (int i = 0; i < dayOrder.length; i++) dayIdx.put(dayOrder[i], i);
        entries.sort((a, b) -> {
            int d = dayIdx.getOrDefault(a.getDayOfWeek(), 9) - dayIdx.getOrDefault(b.getDayOfWeek(), 9);
            return d != 0 ? d : a.getStartTime().compareTo(b.getStartTime());
        });
        for (Timetable t : entries) {
            model.addRow(new Object[]{
                t.getDayOfWeek(), t.getStartTime(), t.getEndTime(),
                t.getSubjectName(), t.getFacultyName(), t.getRoom(), t.getId()
            });
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry to delete.");
            return;
        }
        int id = (Integer) model.getValueAt(row, COLS.length - 1);
        if (JOptionPane.showConfirmDialog(this, "Delete this timetable entry?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (ctrl.delete(id)) {
                JOptionPane.showMessageDialog(this, "Deleted!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }
        }
    }

    private void showAddDialog() {
        JTextField dayField = UITheme.makeTextField();
        dayField.setText("Monday");
        JTextField startField = UITheme.makeTextField();
        JTextField endField = UITheme.makeTextField();
        JTextField subjectField = UITheme.makeTextField();
        JTextField facultyField = UITheme.makeTextField();
        JTextField roomField = UITheme.makeTextField();

        Object[] fields = {
            "Day:", dayField,
            "Start Time:", startField,
            "End Time:", endField,
            "Subject:", subjectField,
            "Faculty:", facultyField,
            "Room:", roomField
        };

        if (JOptionPane.showConfirmDialog(this, fields, "Add Timetable", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Timetable t = new Timetable();
            t.setDayOfWeek(dayField.getText().trim());
            t.setStartTime(startField.getText().trim());
            t.setEndTime(endField.getText().trim());
            t.setSubjectName(subjectField.getText().trim());
            t.setFacultyName(facultyField.getText().trim());
            t.setRoom(roomField.getText().trim());
            if (ctrl.add(t)) {
                JOptionPane.showMessageDialog(this, "Added!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Add failed - backend security check?");
            }
        }
    }
}
