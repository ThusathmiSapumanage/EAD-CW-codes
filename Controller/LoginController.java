/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.*;
import Model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 *
 * @author thusa
 */
public class LoginController {
    
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLoginSelection();
            }
        });
    }

   private void handleLoginSelection() 
   {
        Connection conn = Database.getConnection(); // Retrieve a Connection instance
        if (view.getEmpRadioButton().isSelected()) {
            new EmployeeLoginController(new Employee(conn), new EmpLoginView()).showView();
            view.dispose();
        } else if (view.getAdminRadioButton().isSelected()) {
            new AdminLoginController(new Admin(conn), new AdminLoginView()).showView();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Please select either Employee or Admin!");
        }
   }
}

