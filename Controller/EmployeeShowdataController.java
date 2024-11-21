 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javax.swing.JOptionPane;
import View.EmployeeView;
import Model.Employee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author thusa
 */


public class EmployeeShowdataController {

    private Employee model;
    private EmployeeView view;
    private String username;

    public EmployeeShowdataController(Employee model, EmployeeView view, String username) {
        this.model = model;
        this.view = view;
        this.username = username;

        initController();
    }

    private void initController() {
        view.getLoadDetailsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEmployeeData();
            }
        });
    }

     private void loadEmployeeData() {
        try {
            String[] employeeData = model.getEmployeeDetails(username);
            if (employeeData[0] == null) {
                JOptionPane.showMessageDialog(view, "Employee data not found.");
            } else {
                view.updateTable(employeeData); // Ensure `updateTable` is defined in EmployeeView
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error fetching data: " + ex.getMessage());
        }
    }
}

