package Controller;

import Model.Database;
import Model.Employee;
import View.EmpLoginView;
import View.EmployeeView;
import View.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeLoginController {
    private Employee model;
    private EmpLoginView view;
    private Connection conn;

    public EmployeeLoginController(Employee model, EmpLoginView view) {
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
                
                LoginView LV = new LoginView();
                new LoginController(LV);
                LV.setVisible(true);
            }
        });
    }

    public void showView() {
        view.setVisible(true);
    }

   private void handleLogin() {
    String empId = view.getUname().getText();
    String password = new String(view.getPass().getPassword());

        try
        {
            if (model.validateEmployeeLogin(empId, password)) 
            {
                JOptionPane.showMessageDialog(view, "Login successful!");
                view.dispose();
                
                EmployeeView eView = new EmployeeView();

                new EmployeeShowdataController(model, eView, empId);

                eView.setVisible(true);
            }
            else 
            {
                JOptionPane.showMessageDialog(view, "Invalid ID or Password.");
            }
        } 
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
}
