/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author thusa
 */

import Model.Admin;
import View.AdminControlsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAddController {
    
    private Admin model;
    private AdminControlsView view;

    public AdminAddController(Admin model, AdminControlsView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getInsertButton().addActionListener(e -> insertAdmin());
        view.getUpdateButton().addActionListener(e -> updateAdmin());
        view.getDeleteButton().addActionListener(e -> deleteAdmin());
        view.getClearButton().addActionListener(e -> view.clearFields());
        view.getCloseButton().addActionListener(e -> view.dispose());
        view.getShowTableButton().addActionListener(e -> loadAdminTable());
    }

    private void insertAdmin() {
        try {
            int rowsInserted = model.insertAdmin(
                view.getAidField().getText(),
                view.getAdminNameField().getText(),
                view.getPhoneNumberField().getText(),
                view.getEmailField().getText()
            );

            JOptionPane.showMessageDialog(view, rowsInserted > 0 ? "Admin added successfully!" : "Failed to add admin.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void updateAdmin() {
        try {
            int rowsUpdated = model.updateAdmin(
                view.getAidField().getText(),
                view.getAdminNameField().getText(),
                view.getPhoneNumberField().getText(),
                view.getEmailField().getText()
            );

            JOptionPane.showMessageDialog(view, rowsUpdated > 0 ? "Admin updated successfully!" : "Failed to update admin.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteAdmin() {
        try {
            int rowsDeleted = model.deleteAdmin(view.getAidField().getText());
            JOptionPane.showMessageDialog(view, rowsDeleted > 0 ? "Admin deleted successfully!" : "Failed to delete admin.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void loadAdminTable() {
        try {
            ResultSet rs = model.getAllAdmins();
            DefaultTableModel tblModel = (DefaultTableModel) view.getAdminTable().getModel();
            tblModel.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString("AID"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
    
    public void showAdmincon()
    {
        view.setVisible(true);
    }
}
