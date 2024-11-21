/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class Employee 
{
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String type;
    private String position;
    private Connection conn;
    
    public Employee(Connection conn) 
    {
        this.conn = conn != null ? conn : Database.getConnection();
    }

    public String getId()
    { 
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id; 
    }

    public String getName()
    {
        return name; 
    }
    
    public void setName(String name)
    { 
        this.name = name; 
    }

    public String getPhoneNumber()
    { 
        return phoneNumber; 
    }
    
    public void setPhoneNumber(String phoneNumber)
    { 
        this.phoneNumber = phoneNumber; 
    }

    public String getEmail()
    { 
        return email;
    }
    
    public void setEmail(String email)
    { 
        this.email = email; 
    }

    public String getAddress() 
    {
        return address; 
    }
    
    public void setAddress(String address) 
    {
        this.address = address; 
    }

    public String getType() 
    {
        return type; 
    }
    
    public void setType(String type)
    {
        this.type = type; 
    }

    public String getPosition() 
    {
        return position; 
    }
    
    public void setPosition(String position) 
    {
        this.position = position; 
    }

    // employee
    public int addEmployee() throws SQLException {
        String sql = "INSERT INTO employee (eid, name, phonenumber, email, address, type, position) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, name);
        pstmt.setString(3, phoneNumber);
        pstmt.setString(4, email);
        pstmt.setString(5, address);
        pstmt.setString(6, type);
        pstmt.setString(7, position);
        return pstmt.executeUpdate();
    }

    public int updateEmployee() throws SQLException {
        String sql = "UPDATE employee SET name=?, phonenumber=?, email=?, address=?, type=?, position=? WHERE eid=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, phoneNumber);
        pstmt.setString(3, email);
        pstmt.setString(4, address);
        pstmt.setString(5, type);
        pstmt.setString(6, position);
        pstmt.setString(7, id);
        return pstmt.executeUpdate();
    }

    public int deleteEmployee() throws SQLException {
        String sql = "DELETE FROM employee WHERE eid = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeUpdate();
    }

    // employee view in table
    public ResultSet getAllEmployees() throws SQLException {
        String sqlquery = "SELECT * FROM employee";
        PreparedStatement pstmt = conn.prepareStatement(sqlquery);
        return pstmt.executeQuery();
    }
    
    //login emp
    public boolean validateEmployeeLogin(String empId, String password) throws SQLException {
        String query = "SELECT * FROM employeelogin WHERE EID = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, empId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // returns true if there is a match
        }
    }
    
    //employee dashboard
    public String[] getEmployeeDetails(String username) throws SQLException {
        String[] employeeData = new String[5];

        String sql = "SELECT e.eid, e.name, s.Amount AS salary, p.PID, p.Rating " +
                     "FROM employee e " +
                     "LEFT JOIN salary s ON e.eid = s.eid " +
                     "LEFT JOIN performance p ON e.eid = p.eid " +
                     "WHERE e.eid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                employeeData[0] = rs.getString("eid");       
                employeeData[1] = rs.getString("name");      
                employeeData[2] = rs.getString("salary");    
                employeeData[3] = rs.getString("PID");       
                employeeData[4] = rs.getString("Rating");    
            }
        }
        return employeeData;
    }
    
    //employee leaves
    public int insertLeave(String leaveId, int daysAllowed, String type, int daysTaken, String empId, String status) throws SQLException {
        String sql = "INSERT INTO leaveinfo (LeavId, Daysallowed, Type, Daystaken, EID, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, leaveId);
            pstmt.setInt(2, daysAllowed);
            pstmt.setString(3, type);
            pstmt.setInt(4, daysTaken);
            pstmt.setString(5, empId);
            pstmt.setString(6, status);
            return pstmt.executeUpdate();
        }
    }

    public int updateLeave(String leaveId, int daysAllowed, String type, int daysTaken, String empId, String status) throws SQLException {
        String sql = "UPDATE leaveinfo SET Daysallowed = ?, Type = ?, Daystaken = ?, EID = ?, Status = ? WHERE LeavId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, daysAllowed);
            pstmt.setString(2, type);
            pstmt.setInt(3, daysTaken);
            pstmt.setString(4, empId);
            pstmt.setString(5, status);
            pstmt.setString(6, leaveId);
            return pstmt.executeUpdate();
        }
    }

    public int deleteLeave(String leaveId) throws SQLException {
        String sql = "DELETE FROM leaveinfo WHERE LeavId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, leaveId);
            return pstmt.executeUpdate();
        }
    }

    public ResultSet getAllLeaves() throws SQLException {
        String sql = "SELECT * FROM leaveinfo";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
    
    //employee accounts
    public boolean addEmployeeAccount(String empID, String password) throws SQLException {
        String sql = "INSERT INTO employeelogin (EID, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empID);
            pstmt.setString(2, password);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployeeAccount(String empID) throws SQLException {
        String sql = "DELETE FROM employeelogin WHERE EID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empID);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    //employee performance
    public int insertPerformance(String pid, String eid, String rating, String comments) throws SQLException {
        String sql = "INSERT INTO performance (PID, EID, Rating, Comments) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pid);
            pstmt.setString(2, eid);
            pstmt.setString(3, rating);
            pstmt.setString(4, comments);
            return pstmt.executeUpdate();
        }
    }

    public int updatePerformance(String pid, String eid, String rating, String comments) throws SQLException {
        String sql = "UPDATE performance SET EID=?, Rating=?, Comments=? WHERE PID=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eid);
            pstmt.setString(2, rating);
            pstmt.setString(3, comments);
            pstmt.setString(4, pid);
            return pstmt.executeUpdate();
        }
    }

    public int deletePerformance(String pid) throws SQLException {
        String sql = "DELETE FROM performance WHERE PID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pid);
            return pstmt.executeUpdate();
        }
    }

    public ResultSet getAllPerformanceRecords() throws SQLException {
        String sql = "SELECT * FROM performance";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    //jasper reports
    public void generateReport() {
        try {
            String reportPath = "C:\\Users\\thusa\\JaspersoftWorkspace\\MyReports\\Employee_Record.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getReport() {
        try {
            String reportPath = "C:\\Users\\thusa\\JaspersoftWorkspace\\MyReports\\Salary_documentation.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //salary
    public int insertSalary(String salaryId, int amount, String employeeId) throws SQLException {
        String sql = "INSERT INTO salary (Salary_id, Amount, EID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, salaryId);
            pstmt.setInt(2, amount);
            pstmt.setString(3, employeeId);
            return pstmt.executeUpdate();
        }
    }

    public int deleteSalary(String salaryId) throws SQLException {
        String sql = "DELETE FROM salary WHERE Salary_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, salaryId);
            return pstmt.executeUpdate();
        }
    }

    public ResultSet getAllSalaries() throws SQLException {
        String sql = "SELECT * FROM salary";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
}