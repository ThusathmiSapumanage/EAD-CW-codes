/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Admin;
import Model.Employee;
import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thusa
 */
public class AdminViewController {
    private Admin model;
    private AdminView view;
    private Employee model2;
    private LeaveController leaveController;
    private AdminAccController adminAccController;
    private EmployeeAccController employeeAccController;
    private ProgressController progressContorller;
    private AdminAddController adminaddcon;
    private SalaryController salcal;
    private EmployeeController empcol;

    public AdminViewController(Admin model, AdminView view, Connection conn, Employee model2) {
        this.model = model;
        this.view = view;
        this.model2 = model2;

        LeavesControlsView leavesView = new LeavesControlsView();
        this.leaveController = new LeaveController(new Employee(conn), leavesView);

        AddAccAdmin_View adminAccView = new AddAccAdmin_View();
        this.adminAccController = new AdminAccController(model, adminAccView);

        AddAccEmp_View employeeAccView = new AddAccEmp_View();
        this.employeeAccController = new EmployeeAccController(model2, employeeAccView);
        
        EmpProgressView empprog = new EmpProgressView();
        this.progressContorller = new ProgressController(model2, empprog);
        
        AdminControlsView adv = new AdminControlsView();
        this.adminaddcon = new AdminAddController(model, adv);
        
        SalaryCalView scv = new SalaryCalView();
        this.salcal = new SalaryController(model2, scv);
        
        EmployeeControlsView emp = new EmployeeControlsView();
        this.empcol = new EmployeeController(model2, emp);

        initController();
    }

    private void initController() {
        // Menu item action listeners
        view.getLeavesMenuItem().addActionListener(e -> openLeavesView());
        view.getAdminAcc().addActionListener(e -> openAdminAccView());
        view.getEmployeeAcc().addActionListener(e -> openEmployeeAccView());
        view.getperformace().addActionListener(e -> openProgView());
        view.getAdminControl().addActionListener(e -> openadmincont());
        view.getCalitem().addActionListener(e -> opensalary());
        view.getCloseButton().addActionListener(e -> view.dispose());
        view.getEmployeeControls().addActionListener(e -> openemp());
        view.getSearchButton().addActionListener((ActionEvent e) -> {
             String eid = view.getEmployeeIdField().getText();
             fetchAndDisplayEmployeeData(eid);
        });
    }

    private void openLeavesView() {
        leaveController.showLeavesView();
    }

    private void openAdminAccView() {
        adminAccController.showViews();
    }

    private void openEmployeeAccView() {
        employeeAccController.showViewss();
    }
    
    private void openProgView()
    {
        progressContorller.showviews2();
    }
    
    private void openadmincont()
    {
        adminaddcon.showAdmincon();
    }
    
    private void opensalary()
    {
        salcal.showsalview();
    }
    
    public void fetchAndDisplayEmployeeData(String eid) {
        try {
            // Fetch all employee data in one call
            String[] employeeData = model.getAllEmployeeData(eid);

           
            if (employeeData[0] == null) { 
                JOptionPane.showMessageDialog(view, "Employee data not found.");
                return;
            }

            DefaultTableModel tblModel = (DefaultTableModel) view.getEmployeeTable().getModel();
            tblModel.setRowCount(0);  

            tblModel.addRow(employeeData);

            // Refresh table
            view.getEmployeeTable().revalidate();
            view.getEmployeeTable().repaint();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error fetching data: " + e.getMessage());
        }
    }
    
    private void openemp()
    {
        empcol.showcon();
    }
}