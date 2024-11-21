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
import View.SalaryCalView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryController {
     private Employee model;
    private SalaryCalView view;
    private int monthlySalary;

    public SalaryController(Employee model, SalaryCalView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getCalculateButton().addActionListener(e -> calculateSalary());
        view.getClearButton().addActionListener(e -> view.clearFields());
        view.getCloseButton().addActionListener(e -> view.dispose());
        view.getInsertButton().addActionListener(e -> insertSalary());
        view.getShowTableButton().addActionListener(e -> loadSalaryTable());
        view.getDeleteButton().addActionListener(e -> deleteSalary());
         view.getReport().addActionListener(e -> model.getReport());
    }

    private void calculateSalary() {
        try {
            int leaves = Integer.parseInt(view.getLeavesField().getText());
            int overtimeHours = Integer.parseInt(view.getOvertimeHoursField().getText());
            boolean isSenior = view.getSeniorRadioButton().isSelected();

            // Calculate basic salary and bonus
            int basicSalary = (isSenior ? 2000 : 1000) * (30 - leaves);
            int bonus = overtimeHours * (isSenior ? 1000 : 500);
            monthlySalary = basicSalary + bonus;

            view.getBasicSalaryField().setText(basicSalary + " LKR");
            view.getBonusField().setText(bonus + " LKR");
            view.getNetSalaryField().setText(monthlySalary + " LKR");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter valid numbers for overtime hours and leaves.");
        }
    }

    private void insertSalary() {
        try {
            int rowsInserted = model.insertSalary(
                view.getSalaryIdField().getText(),
                monthlySalary,
                view.getEmployeeIdField().getText()
            );

            JOptionPane.showMessageDialog(view, rowsInserted > 0 ? "Salary added successfully!" : "Failed to add salary.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteSalary() {
        try {
            int rowsDeleted = model.deleteSalary(view.getSalaryIdField().getText());
            JOptionPane.showMessageDialog(view, rowsDeleted > 0 ? "Salary deleted successfully!" : "Failed to delete salary.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void loadSalaryTable() {
        try {
            ResultSet rs = model.getAllSalaries();
            DefaultTableModel tblModel = (DefaultTableModel) view.getSalaryTable().getModel();
            tblModel.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                tblModel.addRow(new Object[]{
                    rs.getString("Salary_id"),
                    rs.getString("Amount"),
                    rs.getString("EID")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
    public void showsalview()
    {
        view.setVisible(true);
    }
}
