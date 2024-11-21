/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalcw_ver2;

import Controller.*;
import View.*;

/**
 *
 * @author thusa
 */
public class Finalcw_ver2 {

    public static void main(String[] args) {
        try 
        {
            LoginView lV = new LoginView();
        
            new LoginController(lV);

            lV.setVisible(true);
        } 
        catch (Exception e) 
        {
            System.out.println("Error launching application: " + e.getMessage());
        }
    }
}
