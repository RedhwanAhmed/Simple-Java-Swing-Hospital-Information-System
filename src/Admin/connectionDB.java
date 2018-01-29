package Admin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author redhwan
 */

import java.sql.*;
import javax.swing.*;


public class connectionDB {

     Connection con=null;

	public static Connection DBconnection() 
	{
		try 
		{
			
                    Class.forName("com.mysql.jdbc.Driver");
                   Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop","root","Pass");
			
				
			return con;		
		} catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null , ex);
                        return null;
		}
		
	}
    
}
