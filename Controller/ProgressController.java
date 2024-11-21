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
import View.EmpProgressView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgressController {
    private Employee model;
    private EmpProgressView view;
    
    public ProgressController(Employee model, EmpProgressView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getInsertButton().addActionListener(e -> insertPerformance());
        view.getUpdateButton().addActionListener(e -> updatePerformance());
        view.getDeleteButton().addActionListener(e -> deletePerformance());
        view.getClearButton().addActionListener(e -> view.clearFields());
        view.getCloseButton().addActionListener(e -> view.dispose());
        view.getShowTableButton().addActionListener(e -> loadPerformanceTable());
        view.getGenerateReportButton().addActionListener(e -> model.generateReport());
    }

    private void insertPerformance() {
        try {
            int rowsInserted = model.insertPerformance(
                view.getPerformanceIdField().getText(),
                view.getEmployeeIdField().getText(),
                view.getRatingField().getText(),
                view.getCommentsField().getText()
            );

            JOptionPane.showMessageDialog(view, rowsInserted > 0 ? "Performance added!" : "Failed to add performance.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void updatePerformance() {
        try {
            int rowsUpdated = model.updatePerformance(
                view.getPerformanceIdField().getText(),
                view.getEmployeeIdField().getText(),
                view.getRatingField().getText(),
                view.getCommentsField().getText()
            );

            JOptionPane.showMessageDialog(view, rowsUpdated > 0 ? "Performance updated!" : "Failed to update performance.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deletePerformance() {
        try {
            int rowsDeleted = model.deletePerformance(view.getPerformanceIdField().getText());
            JOptionPane.showMessageDialog(view, rowsDeleted > 0 ? "Performance deleted!" : "Failed to delete performance.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void loadPerformanceTable() {
        try {
            ResultSet rs = model.getAllPerformanceRecords();
            DefaultTableModel tblModel = (DefaultTableModel) view.getPerformanceTable().getModel();
            tblModel.setRowCount(0);

            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString("PID"),
                    rs.getString("EID"),
                    rs.getString("Rating"),
                    rs.getString("Comments")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
    public void showviews2()
    {
        view.setVisible(true);
    }
}
