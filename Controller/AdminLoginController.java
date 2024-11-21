package Controller;

import Model.Admin;
import Model.Database;
import Model.Employee;
import View.AdminLoginView;
import View.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminLoginController {
    private Admin model;
    private AdminLoginView view;
    private Connection conn;

    public AdminLoginController(Admin model, AdminLoginView view) {
        this.model = model;
        this.view = view;
        this.conn = Database.getConnection();
        initController();
    }

    private void initController() {
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
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

    public void showView() {
        view.setVisible(true);
    }

    private void handleLogin() {
        String adminId = view.getUnameField().getText();
        String password = new String(view.getPasswordField().getPassword());

        try {
            if (model.validateAdminLogin(adminId, password)) {
                JOptionPane.showMessageDialog(view, "Login successful!");
                view.dispose();
                
                Employee model2 = new Employee(conn);
                AdminView adminView = new AdminView();
                new AdminViewController(model, adminView, conn, model2);
                adminView.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(view, "Invalid ID or Password.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
}
