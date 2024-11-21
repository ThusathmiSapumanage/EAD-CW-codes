/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author thusa
 */
import Model.Employee;
import View.LeavesControlsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveController {
    private Employee model;
    private LeavesControlsView view;

    public LeaveController(Employee model, LeavesControlsView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getInsertButton().addActionListener(e -> insertLeave());
        view.getUpdateButton().addActionListener(e -> updateLeave());
        view.getDeleteButton().addActionListener(e -> deleteLeave());
        view.getClearButton().addActionListener(e -> view.clearFields());
        view.getCloseButton().addActionListener(e -> view.dispose());
        view.getShowTableButton().addActionListener(e -> loadLeavesIntoTable());
    }

    private void insertLeave() {
        try {
            int rowsInserted = model.insertLeave(
                view.getLidField().getText(),
                Integer.parseInt(view.getDallowedField().getText()),
                view.getTypeComboBox().getSelectedItem().toString(),
                Integer.parseInt(view.getDtakenField().getText()),
                view.getEidField().getText(),
                view.getStatusComboBox().getSelectedItem().toString()
            );

            JOptionPane.showMessageDialog(view, rowsInserted > 0 ? "Leave added successfully!" : "Failed to add leave.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void updateLeave() {
        try {
            int rowsUpdated = model.updateLeave(
                view.getLidField().getText(),
                Integer.parseInt(view.getDallowedField().getText()),
                view.getTypeComboBox().getSelectedItem().toString(),
                Integer.parseInt(view.getDtakenField().getText()),
                view.getEidField().getText(),
                view.getStatusComboBox().getSelectedItem().toString()
            );

            JOptionPane.showMessageDialog(view, rowsUpdated > 0 ? "Leave updated successfully!" : "Failed to update leave.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void deleteLeave() {
        try {
            int rowsDeleted = model.deleteLeave(view.getLidField().getText());
            JOptionPane.showMessageDialog(view, rowsDeleted > 0 ? "Leave deleted successfully!" : "Failed to delete leave.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void loadLeavesIntoTable() {
        try {
            ResultSet rs = model.getAllLeaves();
            DefaultTableModel tblModel = (DefaultTableModel) view.getLeavesTable().getModel();
            tblModel.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString("LeavId"),
                    rs.getString("Daysallowed"),
                    rs.getString("Type"),
                    rs.getString("Daystaken"),
                    rs.getString("EID"),
                    rs.getString("Status")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }
    
    public void showLeavesView() 
    {
        view.setVisible(true);
    }
}
