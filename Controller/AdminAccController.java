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
import View.AddAccAdmin_View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminAccController {
    private Admin model;
    private AddAccAdmin_View view;
    
    public AdminAccController(Admin model, AddAccAdmin_View view) {
        this.model = model;
        this.view = view;
        initController();
    }
    private void initController() {
        view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAdminAccount();
            }
        });
        
        view.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAdminAccount();
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

    private void addAdminAccount() {
        String adminId = view.getAdminIdField().getText();
        String password = new String(view.getPasswordField().getPassword());

        try {
            int rowsInserted = model.addAdminAccount(adminId, password);
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(view, "Admin account added successfully!");
                view.clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add admin account.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void deleteAdminAccount() {
        String adminId = view.getAdminIdField().getText();

        try {
            int rowsDeleted = model.deleteAdminAccount(adminId);
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(view, "Admin account deleted successfully!");
                view.clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete admin account. Check if the admin ID is correct.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }
    
     public void showViews() {
        view.setVisible(true);
    }
}
