/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author thusa
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Database
{
    
    String URL = "jdbc:mysql://localhost:3307/company";
    String USERNAME = "Vohara";
    String PASSWORD = "1234";

    
    public static Connection getConnection()
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String password = "1234";
            String username = "Vohara";
            String url = "jdbc:mysql://localhost:3307/company";
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Connection Error: " + e.getMessage());
        }
        return null;
    }
}
