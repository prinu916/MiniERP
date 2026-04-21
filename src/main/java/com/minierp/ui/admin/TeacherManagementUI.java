package com.minierp.ui.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.minierp.dao.FacultyDAO;
import com.minierp.models.Faculty;
import com.minierp.ui.components.UITheme;
import com.minierp.ui.components.UIUtils;

public class TeacherManagementUI extends JPanel {
    private final FacultyDAO facultyDAO = new FacultyDAO();
    private final JTable table;
    private final DefaultTableModel model;

    public TeacherManagementUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UITheme.BG_DARK);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Header
        JLabel title = new JLabel("Faculty Management");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_PRIMARY);

        JButton addBtn = UITheme.makeAccentButton("+ Add New Faculty");
        addBtn.addActionListener(e -> showAddFacultyDialog());

        JButton deleteBtn = UITheme.makeDangerButton("🗑️ Delete Selected");

        // 🔥 FIX: Use proper delete method
        deleteBtn.addActionListener(e -> deleteSelectedFaculty());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);

        // RBAC
        UIUtils.setAdminOnly(addBtn, deleteBtn);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(title, BorderLayout.WEST);
        top.add(buttonPanel, BorderLayout.EAST);

        // Table
        String[] columns = {"ID", "Name", "Department", "Email", "Role"};
        model = new DefaultTableModel(columns, 0);
        table = UITheme.makeTable(model);

        loadFacultyData();

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadFacultyData() {
        model.setRowCount(0);
        List<Faculty> faculties = facultyDAO.findAll();

        for (Faculty f : faculties) {
            model.addRow(new Object[]{
                    f.getId(),
                    f.getName(),
                    f.getDepartment(),
                    f.getEmail(),
                    "FACULTY"
            });
        }
    }

    private void deleteSelectedFaculty() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a faculty.");
            return;
        }

        int facultyId = (Integer) model.getValueAt(selectedRow, 0);
        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            JOptionPane.showMessageDialog(this, "Faculty not found.");
            return;
        }

                UIManager.put("OptionPane.background", UITheme.BG_DARK);
                UIManager.put("OptionPane.messageForeground", UITheme.TEXT_PRIMARY);
                UIManager.put("OptionPane.buttonForeground", UITheme.TEXT_PRIMARY);
                int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete " + faculty.getName() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
                UIManager.put("OptionPane.background", null);



        if (confirm == JOptionPane.YES_OPTION) {
            // 🔥 CASCADE DELETE: Child records first
            // 1. Delete timetable entries
            com.minierp.database.QueryExecutor.executeUpdate(
                "DELETE FROM timetable WHERE faculty_id = ?", facultyId
            );
            // 2. Delete exams
            com.minierp.database.QueryExecutor.executeUpdate(
                "DELETE FROM exam_schedule WHERE faculty_id = ?", facultyId
            );
            // 3. Delete results
            com.minierp.database.QueryExecutor.executeUpdate(
                "DELETE FROM results WHERE faculty_id = ?", facultyId
            );
            // 4. Delete faculty FIRST (before users)
            com.minierp.database.QueryExecutor.executeUpdate(
                "DELETE FROM faculty WHERE id = ?", facultyId
            );
            // 5. Delete users LAST
            if (faculty.getUserId() > 0) {
                com.minierp.database.QueryExecutor.executeUpdate(
                    "DELETE FROM users WHERE id = ?", faculty.getUserId()
                );
            }

            JOptionPane.showMessageDialog(this, "Faculty & data deleted successfully!");
            loadFacultyData();
        }


    }

    private void showAddFacultyDialog() {
        JTextField nameF = UITheme.makeTextField();
        JTextField deptF = UITheme.makeTextField();
        JTextField emailF = UITheme.makeTextField();
        JPasswordField passF = new JPasswordField();

        Object[] fields = {
                "Full Name:", nameF,
                "Department:", deptF,
                "Email:", emailF,
                "Password:", passF
        };

                UIManager.put("OptionPane.background", UITheme.BG_DARK);
                UIManager.put("OptionPane.messageForeground", UITheme.TEXT_PRIMARY);
                UIManager.put("Panel.background", UITheme.BG_PANEL);
                int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add Faculty",
                JOptionPane.OK_CANCEL_OPTION
        );
                UIManager.put("OptionPane.background", null);

        if (result == JOptionPane.OK_OPTION) {
            Faculty f = new Faculty();
            f.setName(nameF.getText());
            f.setDepartment(deptF.getText());
            f.setEmail(emailF.getText());
            f.setPassword(new String(passF.getPassword()));

            if (facultyDAO.save(f)) {
                JOptionPane.showMessageDialog(this, "Added Successfully!");
                loadFacultyData();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving faculty!");
            }
        }
    }
}