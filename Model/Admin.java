package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    private Connection conn;

    public Admin(Connection conn) {
        this.conn = conn;
    }
    
    //login
    public boolean validateAdminLogin(String adminId, String password) throws SQLException {
        String query = "SELECT * FROM admin WHERE AdminID = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, adminId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // returns true if a match is found
        }
    }
    
    //dashbord
    public String[] getAllEmployeeData(String eid) throws SQLException {
        String[] employeeData = new String[7];
        
        String sql = "SELECT e.eid, e.name, s.Amount AS salary, l.Daystaken AS leavesTaken, " +
                     "p.PID, p.Rating, p.Comments " +
                     "FROM employee e " +
                     "LEFT JOIN salary s ON e.eid = s.eid " +
                     "LEFT JOIN leaveinfo l ON e.eid = l.eid AND l.Status = 'Approved' " +
                     "LEFT JOIN performance p ON e.eid = p.eid " +
                     "WHERE e.eid = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                employeeData[0] = rs.getString("eid");             
                employeeData[1] = rs.getString("name");            
                employeeData[2] = rs.getString("salary");          
                employeeData[3] = rs.getString("leavesTaken");     
                employeeData[4] = rs.getString("PID");             
                employeeData[5] = rs.getString("Rating");          
                employeeData[6] = rs.getString("Comments");        
            }
        }
        return employeeData;
    }
    
    //Admin Accounts
    public int addAdminAccount(String adminId, String password) throws SQLException {
        String sql = "INSERT INTO admin (AdminID, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adminId);
            pstmt.setString(2, password);
            return pstmt.executeUpdate();
        }
    }

    public int deleteAdminAccount(String adminId) throws SQLException {
        String sql = "DELETE FROM admin WHERE AdminID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adminId);
            return pstmt.executeUpdate();
        }
    }
    
    //Admin roles
     public int insertAdmin(String aid, String name, String phone, String email) throws SQLException {
        String sql = "INSERT INTO admininfo (AID, name, phone, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, aid);
            pstmt.setString(2, name);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            return pstmt.executeUpdate();
        }
    }

    public int updateAdmin(String aid, String name, String phone, String email) throws SQLException {
        String sql = "UPDATE admininfo SET name=?, phone=?, email=? WHERE AID=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, aid);
            return pstmt.executeUpdate();
        }
    }

    public int deleteAdmin(String aid) throws SQLException {
        String sql = "DELETE FROM admininfo WHERE AID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, aid);
            return pstmt.executeUpdate();
        }
    }

    public ResultSet getAllAdmins() throws SQLException {
        String sql = "SELECT * FROM admininfo";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
}
