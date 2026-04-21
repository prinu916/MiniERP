package com.minierp.ui.faculty;

import com.minierp.ui.components.UITheme;
import com.minierp.controllers.ExamController;
import com.minierp.services.ResultService;
import com.minierp.services.AuthService;
import com.minierp.models.Result;
import com.minierp.models.Exam;
import com.minierp.dao.StudentDAO;
import com.minierp.models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ResultManagementUI extends JPanel {

    private final ExamController examCtrl = new ExamController();
    private final ResultService resultService = new ResultService();
    private final StudentDAO studentDAO = new StudentDAO();

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> examCombo;
    private int currentExamId = 0;

    private static final String[] COLS = {
            "Student", "Exam", "Subject", "Marks", "Max", "Grade", "Entered At"
    };

    public ResultManagementUI() {
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG_DARK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initUI();
        loadData(0);
    }

    private void initUI() {

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topBar.setOpaque(false);

        AuthService auth = AuthService.getInstance();

        List<Exam> allExams = examCtrl.getAll();
        List<Exam> exams;

        if (com.minierp.ui.components.UIUtils.isAdmin()) {
            exams = allExams;
        } else {
            int facId = (auth.getCurrentFaculty() != null)
                    ? auth.getCurrentFaculty().getId()
                    : 0;

            exams = allExams.stream()
                    .filter(e -> e.getCreatedBy() == facId)
                    .collect(Collectors.toList());
        }

        String[] examOpts;
        if (exams.isEmpty()) {
            examOpts = new String[]{"No exams available"};
        } else {
            examOpts = exams.stream()
                    .map(e -> e.getId() + ": " + e.getTitle())
                    .toArray(String[]::new);
        }

        examCombo = UITheme.makeCombo(examOpts);
        examCombo.setPreferredSize(new Dimension(280, 36));

        examCombo.addActionListener(e -> {
            String s = (String) examCombo.getSelectedItem();
            if (s != null && !s.startsWith("No")) {
                try {
                    currentExamId = Integer.parseInt(s.split(":")[0].trim());
                    loadData(currentExamId);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid exam selection.");
                }
            }
        });

        JButton addBtn = UITheme.makeAccentButton("+ Add Result");
        JButton refreshBtn = UITheme.makeButton("Refresh", UITheme.BG_CARD);

        addBtn.addActionListener(e -> showAddResultDialog());
        refreshBtn.addActionListener(e -> loadData(currentExamId));

        topBar.add(UITheme.makeLabel("Exam:", UITheme.FONT_BODY, UITheme.TEXT_MUTED));
        topBar.add(examCombo);
        topBar.add(addBtn);
        topBar.add(refreshBtn);

        model = new DefaultTableModel(COLS, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = UITheme.makeTable(model);
        JScrollPane scroll = UITheme.makeScrollPane(table);

        add(topBar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void loadData(int examId) {

        model.setRowCount(0);
        AuthService auth = AuthService.getInstance();

        List<Result> results;

        if (com.minierp.ui.components.UIUtils.isAdmin()) {
            results = (examId > 0)
                    ? examCtrl.getResultsByExam(examId)
                    : resultService.getAllResults();
        } else {
            int facId = (auth.getCurrentFaculty() != null)
                    ? auth.getCurrentFaculty().getId()
                    : 0;

            results = resultService.getAllResults().stream()
                    .filter(r -> r.getEnteredBy() == facId)
                    .collect(Collectors.toList());

            if (examId > 0) {
                results = results.stream()
                        .filter(r -> r.getExamId() == examId)
                        .collect(Collectors.toList());
            }
        }

        if (results.isEmpty()) {
            model.addRow(new Object[]{"No data", "-", "-", "-", "-", "-", "-"});
            return;
        }

        for (Result r : results) {
            model.addRow(new Object[]{
                    r.getStudentName(),
                    r.getExamTitle(),
                    r.getSubjectName(),
                    r.getMarksObtained(),
                    r.getMaxMarks(),
                    r.getGrade(),
                    r.getEnteredAt()
            });
        }
    }

    private void showAddResultDialog() {

        String selExam = (String) examCombo.getSelectedItem();

        if (selExam == null || selExam.startsWith("No")) {
            JOptionPane.showMessageDialog(this, "Select an exam first.");
            return;
        }

        int examId = Integer.parseInt(selExam.split(":")[0].trim());

        Exam exam = examCtrl.getAll().stream()
                .filter(e -> e.getId() == examId)
                .findFirst()
                .orElse(null);

        if (exam == null) {
            JOptionPane.showMessageDialog(this, "Exam not found.");
            return;
        }

        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Add Result",
                true
        );

        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_DARK);

        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 8));
        panel.setBackground(UITheme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        List<Student> students = studentDAO.findAll();

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found.");
            return;
        }

        String[] stuOpts = students.stream()
                .map(s -> s.getId() + ": " + s.getName())
                .toArray(String[]::new);

        JComboBox<String> stuCombo = UITheme.makeCombo(stuOpts);

        JTextField marksField = UITheme.makeTextField();
        JTextField remarksField = UITheme.makeTextField();

        panel.add(UITheme.makeLabel("Student*", UITheme.FONT_BODY, UITheme.TEXT_MUTED));
        panel.add(stuCombo);

        panel.add(UITheme.makeLabel(
                "Marks (Max: " + exam.getMaxMarks() + ")",
                UITheme.FONT_BODY,
                UITheme.TEXT_MUTED
        ));
        panel.add(marksField);

        panel.add(UITheme.makeLabel("Remarks", UITheme.FONT_BODY, UITheme.TEXT_MUTED));
        panel.add(remarksField);

        JButton saveBtn = UITheme.makeAccentButton("Save");
        JButton cancelBtn = UITheme.makeButton("Cancel", UITheme.BG_CARD);

        cancelBtn.addActionListener(e -> dialog.dispose());

        saveBtn.addActionListener(e -> {
            try {
                String ss = (String) stuCombo.getSelectedItem();
                if (ss == null || marksField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Fill all required fields.");
                    return;
                }

                int stuId = Integer.parseInt(ss.split(":")[0].trim());
                double marks = Double.parseDouble(marksField.getText().trim());

                if (marks < 0 || marks > exam.getMaxMarks()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Marks must be between 0 and " + exam.getMaxMarks());
                    return;
                }

                Result r = new Result();
                r.setStudentId(stuId);
                r.setExamId(examId);
                r.setMarksObtained(marks);
                r.setMaxMarks(exam.getMaxMarks());
                r.setRemarks(remarksField.getText().trim());

                int facId = (AuthService.getInstance().getCurrentFaculty() != null)
                        ? AuthService.getInstance().getCurrentFaculty().getId()
                        : 1;

                r.setEnteredBy(facId);

                resultService.addResult(r);

                dialog.dispose();
                loadData(examId);

                JOptionPane.showMessageDialog(this,
                        "Result saved successfully!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Enter valid numeric marks.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Something went wrong: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(UITheme.BG_DARK);
        footer.add(cancelBtn);
        footer.add(saveBtn);

        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}