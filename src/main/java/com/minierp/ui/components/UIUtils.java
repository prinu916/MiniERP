package com.minierp.ui.components;

import com.minierp.services.AuthService;
import javax.swing.*;
import java.util.List;

public class UIUtils {
    private static final AuthService auth = AuthService.getInstance();

    /** Hide/show components for admin only */
    public static void setAdminOnly(JComponent... components) {
        boolean isAdmin = auth.isAdmin();
        for (JComponent c : components) {
            c.setVisible(isAdmin);
            c.setEnabled(isAdmin);
        }
    }

    /** Load all data for admin, user-specific for faculty/student */
    public static boolean isAdmin() {
        return auth.isAdmin();
    }

    public static String getCurrentRole() {
        return auth.getCurrentRole();
    }

    /** Example usage in UI:
     * UIUtils.setAdminOnly(addBtn, delBtn, aiBtn);
     * if (UIUtils.isAdmin()) loadAllData(); else loadUserData();
     */
}
