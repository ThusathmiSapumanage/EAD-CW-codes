/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.*;
import Model.Employee;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thusa
 */
public class EmployeeController {
    private Employee model;
    private EmployeeControlsView view;

    public EmployeeController(Employee model, EmployeeControlsView view) {
        this.model = model;
        this.view = view;

        // Using getter methods to access the buttons
        view.getBtnInsert().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertEmployee();
            }
        });

        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        view.getBtnClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        view.getBtnClose().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                
            }
        });
        
        view.getSelect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllEmployees();
            }
        });
    }

    private void insertEmployee() {
        try {
            model.setId(view.getEno().getText());
            model.setName(view.getEname().getText());
            model.setPhoneNumber(view.getEpno().getText());
            model.setEmail(view.getEemail().getText());
            model.setAddress(view.getEaddress().getText());
            model.setType(view.getEtype().getSelectedItem().toString());
            model.setPosition(view.getEpos().getText());


            if (model.addEmployee() > 0) {
                JOptionPane.showMessageDialog(view, "Employee added successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add employee.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        view.getEno().setText("");
        view.getEname().setText("");
        view.getEpno().setText("");
        view.getEemail().setText("");
        view.getEaddress().setText("");
        view.getEtype().setSelectedIndex(-1);
        view.getEpos().setText("");
    }
    
    private void updateEmployee() {
        try {
            model.setId(view.getEno().getText());
            model.setName(view.getEname().getText());
            model.setPhoneNumber(view.getEpno().getText());
            model.setEmail(view.getEemail().getText());
            model.setAddress(view.getEaddress().getText());
            model.setType(view.getEtype().getSelectedItem().toString());
            model.setPosition(view.getEpos().getText());

            if (model.updateEmployee() > 0) {
                JOptionPane.showMessageDialog(view, "Employee updated successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update employee.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            model.setId(view.getEno().getText());

            if (model.deleteEmployee() > 0) {
                JOptionPane.showMessageDialog(view, "Employee deleted successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete employee. Check if the Employee ID is correct.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
        
    private void showAllEmployees() {
        try {
            ResultSet rs = model.getAllEmployees();
            DefaultTableModel tableModel = (DefaultTableModel) view.getEmployeeTable().getModel();
            tableModel.setRowCount(0); 

            while (rs.next()) {
                Object[] row = {
                    rs.getString("eid"),
                    rs.getString("name"),
                    rs.getString("phonenumber"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("type"),
                    rs.getString("position")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error fetching employee data: " + e.getMessage());
        }
    }
    public void showcon()
    {
        view.setVisible(true);
    }
}

