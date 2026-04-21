package com.minierp.main;

<<<<<<< HEAD

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

=======
import com.formdev.flatlaf.FlatDarculaLaf;
import com.minierp.config.AppConfig;
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
import com.minierp.database.DatabaseInitializer;
import com.minierp.ui.LoginUI;
import com.minierp.ui.components.UITheme;

<<<<<<< HEAD
public class MainApp {
    public static void main(String[] args) {
        // Theme setup - using system L&F
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("[Theme] System L&F applied.");
        } catch (ClassNotFoundException | InstantiationException |
                   IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("[Theme] Failed: " + e.getMessage());
=======
import javax.swing.*;
import java.awt.Font;

public class MainApp {
    public static void main(String[] args) {
        try {
            FlatDarculaLaf.install();
            System.out.println("[Theme] FlatDarculaLaf modern dark theme installed.");
        } catch (Exception e) {
            System.err.println("[Theme] FlatLaf failed, using system L&F: " + e.getMessage());
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {}
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
        }

        applyGlobalTheme();

        try {
            DatabaseInitializer.initialize();
            System.out.println("[DB] Initialized.");
<<<<<<< HEAD
        } catch (RuntimeException e) {
=======
        } catch (Exception e) {
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
            System.err.println("[DB] Failed: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

<<<<<<< HEAD

=======
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
        SwingUtilities.invokeLater(() -> {
            new LoginUI().setVisible(true);
            System.out.println("[App] Started with dark SaaS theme.");
        });
<<<<<<< HEAD

    }

private static void applyGlobalTheme() {
=======
    }

    private static void applyGlobalTheme() {
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
        UIManager.put("Panel.background", UITheme.BG_DARK);
        UIManager.put("Panel.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("Table.background", UITheme.BG_PANEL);
        UIManager.put("Table.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("TableHeader.background", UITheme.TABLE_HEADER);
        UIManager.put("TableHeader.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("Button.background", UITheme.BG_CARD);
        UIManager.put("Button.foreground", UITheme.TEXT_PRIMARY);
<<<<<<< HEAD
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
=======
        UIManager.put("TextField.background", UITheme.BG_CARD);
        UIManager.put("TextField.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("Label.foreground", UITheme.TEXT_PRIMARY);
        UIManager.put("ComboBox.background", UITheme.BG_CARD);
        UIManager.put("ComboBox.foreground", UITheme.TEXT_PRIMARY);
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb

        Font segoe = UITheme.FONT_BODY;
        UIManager.put("Label.font", segoe);
        UIManager.put("Table.font", segoe);
        UIManager.put("Button.font", segoe);
        UIManager.put("TextField.font", segoe);
        UIManager.put("ComboBox.font", segoe);
        UIManager.put("TableHeader.font", segoe.deriveFont(Font.BOLD));
    }
}

