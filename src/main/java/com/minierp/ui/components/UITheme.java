package com.minierp.ui.components;

<<<<<<< HEAD
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
=======
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb

public class UITheme {
    
    public static final Color BG_DARK = new Color(0x0F172A);
    public static final Color BG_PANEL = new Color(0x1F2937);
    public static final Color BG_CARD = new Color(0x1E293B);
    public static final Color TABLE_HEADER = new Color(0x111827);
    public static final Color ROW_ALT = new Color(0x273449);
    public static final Color ACCENT = new Color(0x3B82F6);
    public static final Color ACCENT2 = new Color(0x60A5FA);
    public static final Color SELECTION_BG = new Color(0x2563EB);
    public static final Color SUCCESS = new Color(0x10B981);
    public static final Color WARNING = new Color(0xF59E0B);
    public static final Color DANGER = new Color(0xEF4444);
    public static final Color TEXT_PRIMARY = Color.WHITE;  // Black visibility fix - pure white for max contrast
    public static final Color TEXT_MUTED = new Color(0xD1D5DB);
    public static final Color BORDER = new Color(51, 65, 85, 128);

<<<<<<< HEAD

    public static final Color BG_SIDEBAR = BG_DARK; // Uses your dark blue for the sidebar
    public static final Color HOVER = new Color(0x334155); // A nice grey-blue for mouse hover


=======
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 13);

<<<<<<< HEAD
public static JButton makeButton(String text, Color bgColor) {
=======
    public static JButton makeButton(String text, Color bgColor) {
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFont(FONT_BODY);
        btn.setFocusPainted(false);
<<<<<<< HEAD
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
=======
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb

        Color hoverColor = brighter(bgColor, 1.15f);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
<<<<<<< HEAD
                if (btn.isEnabled()) btn.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(bgColor);
=======
                btn.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
            }
        });
        return btn;
    }

    public static JButton makeAccentButton(String text) {
        return makeButton(text, ACCENT);
    }

    public static JButton makeSuccessButton(String text) {
        return makeButton(text, SUCCESS);
    }

    public static JButton makeDangerButton(String text) {
        return makeButton(text, DANGER);
    }

    public static JButton makeAccent2Button(String text) {
        return makeButton(text, ACCENT2);
    }

    public static JTextField makeTextField() {
        JTextField field = new JTextField();
        field.setBackground(BG_CARD);
        field.setForeground(TEXT_PRIMARY);
        field.setFont(FONT_BODY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return field;
    }

    public static JComboBox makeCombo(Object[] items) {
        JComboBox combo = new JComboBox<>(items);
        combo.setBackground(BG_CARD);
        combo.setForeground(TEXT_PRIMARY);
        combo.setFont(FONT_BODY);
        return combo;
    }

    public static JLabel makeLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static JScrollPane makeScrollPane(Component component) {
        JScrollPane scroll = new JScrollPane(component);
        scroll.setBackground(BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scroll.getViewport().setBackground(BG_DARK);
        return scroll;
    }

<<<<<<< HEAD
public static JTable makeDarkTable(TableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(42);
        table.setFont(FONT_BODY);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(BG_PANEL);
        table.setGridColor(BORDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setSelectionBackground(SELECTION_BG);
        table.setSelectionForeground(Color.WHITE);
        
        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(TEXT_PRIMARY);
        table.getTableHeader().setFont(FONT_BODY.deriveFont(Font.BOLD, 14));
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(BORDER, 1));
=======
    public static JTable makeTable(TableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(42);
        table.setFont(FONT_BODY);
        table.setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setSelectionBackground(new Color(230, 242, 255)); // Professional blue-white tint
        table.setSelectionForeground(Color.BLACK);
        
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(FONT_BODY.deriveFont(Font.BOLD, 14));
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
<<<<<<< HEAD
                if (isSelected) {
                    c.setBackground(SELECTION_BG);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? BG_PANEL : ROW_ALT);
                    c.setForeground(TEXT_PRIMARY);
=======
                c.setForeground(Color.BLACK);
                if (isSelected) {
                    c.setBackground(new Color(230, 242, 255)); // Subtle blue tint for "fill here" focus
                } else {
                    c.setBackground(Color.WHITE);
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
                }
                return c;
            }
        });
        
        return table;
    }

<<<<<<< HEAD
    public static JTable makeTable(TableModel model) {
        return makeDarkTable(model);
    }

=======
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
    private static Color brighter(Color c, float factor) {
        int r = Math.min(255, (int) (c.getRed() * factor));
        int g = Math.min(255, (int) (c.getGreen() * factor));
        int b = Math.min(255, (int) (c.getBlue() * factor));
        return new Color(r, g, b);
    }
}

