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
import View.AddAccEmp_View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EmployeeAccController {
    
    private Employee model;
    private AddAccEmp_View view;

    public EmployeeAccController(Employee model, AddAccEmp_View view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getAddAccountButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployeeAccount();
            }
        });

        view.getDeleteAccountButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployeeAccount();
            }
        });

        view.getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.clearFields();
            }
        });

        view.getCloseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
    }

    private void addEmployeeAccount() {
        String empID = view.getEmpIdField().getText();
        String password = new String(view.getPasswordField().getPassword());

        try {
            if (model.addEmployeeAccount(empID, password)) {
                JOptionPane.showMessageDialog(view, "Employee account added successfully!");
                view.clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add Employee account.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }

    private void deleteEmployeeAccount() {
        String empID = view.getEmpIdField().getText();

        try {
            if (model.deleteEmployeeAccount(empID)) {
                JOptionPane.showMessageDialog(view, "Employee account deleted successfully!");
                view.clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete Employee account. Check if the employee ID is correct.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
    
    public void showViewss()
    {
        view.setVisible(true);
    }
}
