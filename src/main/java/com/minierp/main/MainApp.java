package com.minierp.main;


import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.minierp.database.DatabaseInitializer;
import com.minierp.ui.LoginUI;
import com.minierp.ui.components.UITheme;

public class MainApp {
    public static void main(String[] args) {
        // Theme setup - using system L&F
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("[Theme] System L&F applied.");
        } catch (ClassNotFoundException | InstantiationException |
                   IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("[Theme] Failed: " + e.getMessage());
        }

        applyGlobalTheme();

        try {
            DatabaseInitializer.initialize();
            System.out.println("[DB] Initialized.");
        } catch (RuntimeException e) {
            System.err.println("[DB] Failed: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }


        SwingUtilities.invokeLater(() -> {
            new LoginUI().setVisible(true);
            System.out.println("[App] Started with dark SaaS theme.");
        });

    }

private static void applyGlobalTheme() {
        UIManager.put("Panel.background", UITheme.BG_DARK);
        UIManager.put("Panel.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("Table.background", UITheme.BG_PANEL);
        UIManager.put("Table.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("TableHeader.background", UITheme.TABLE_HEADER);
        UIManager.put("TableHeader.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("Button.background", UITheme.BG_CARD);
        UIManager.put("Button.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("Button.disabledForeground", UITheme.TEXT_MUTED);
        UIManager.put("Button.disabledBackground", UITheme.ROW_ALT);
        UIManager.put("TextField.background", UITheme.BG_CARD);
        UIManager.put("TextField.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", UITheme.TEXT_PRIMARY);
        UIManager.put("Label.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("ComboBox.background", UITheme.BG_CARD);
        UIManager.put("ComboBox.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("OptionPane.background", UITheme.BG_DARK);
        UIManager.put("OptionPane.messageForeground", UITheme.TEXT_PRIMARY);
        UIManager.put("OptionPane.background", UITheme.BG_DARK);
        UIManager.put("OptionPane.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("OptionPane.messageForeground", UITheme.TEXT_PRIMARY);
        UIManager.put("OptionPane.buttonForeground", UITheme.TEXT_PRIMARY);
        UIManager.put("Button.background", UITheme.BG_CARD);
        UIManager.put("Button.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("Button.disabledForeground", UITheme.TEXT_MUTED);
        UIManager.put("Panel.font", UITheme.FONT_BODY);
        UIManager.put("OptionPane.font", UITheme.FONT_BODY);

        Font segoe = UITheme.FONT_BODY;
        UIManager.put("Label.font", segoe);
        UIManager.put("Table.font", segoe);
        UIManager.put("Button.font", segoe);
        UIManager.put("TextField.font", segoe);
        UIManager.put("ComboBox.font", segoe);
        UIManager.put("TableHeader.font", segoe.deriveFont(Font.BOLD));
    }
}

