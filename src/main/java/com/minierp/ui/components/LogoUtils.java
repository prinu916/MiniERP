package com.minierp.ui.components;

import javax.swing.*;
import java.awt.*;

public class LogoUtils {
    
    /**
     * Creates a scaled logo label
     * @param path Resource path (e.g. "/logo.png") or file path
     * @param width Target width
     * @param height Target height
     * @return JLabel with scaled logo (empty if load fails)
     */
    public static JLabel createLogoLabel(String path, int width, int height) {
        try {
            ImageIcon logoIcon = new ImageIcon(LogoUtils.class.getResource(path));
            if (logoIcon.getIconWidth() == -1) {
                // Fallback file load
                logoIcon = new ImageIcon(path);
            }
            java.awt.Image scaled = logoIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaled));
            label.setToolTipText("CGC Logo Loaded");
            return label;
        } catch (Exception ex) {
            System.out.println("Logo load failed: " + path + " - " + ex.getMessage());
            JLabel fallback = new JLabel("🏛️");
            fallback.setFont(new Font("Segoe UI Emoji", Font.BOLD, 32));
            fallback.setToolTipText("Logo Missing: " + path);
            return fallback;
        }
    }
    
    /**
     * Creates logo + title panel (horizontal)
     * @param logoPath Logo resource path
     * @param title Title text
     * @param titleFont Title font
     * @param titleColor Title color
     * @return JPanel with logo left + title right
     */
    public static JPanel createLogoTitlePanel(String logoPath, String title, Font titleFont, Color titleColor) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setOpaque(false);
        
        JLabel logo = createLogoLabel(logoPath, 40, 40);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(titleColor);
        
        panel.add(logo);
        panel.add(titleLabel);
        
        return panel;
    }
}
