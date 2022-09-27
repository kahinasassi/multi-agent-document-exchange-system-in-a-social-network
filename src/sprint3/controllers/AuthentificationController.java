package sprint3.controllers;
import sprint1.controllers.*;
import sprint1.dao.AuthentificationDao;
import sprint1.entity.Authentification;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import  sprint1.*;
/**
 *
 * @author SASSI KAHINA
 * 
 */

public class AuthentificationController   {

	Connection c;

	
	public AuthentificationController() 
        {
            
        }
        
	public AuthentificationController(Connection c) throws ClassNotFoundException, SQLException{
		super();

		this.c = DatabaseConnection.getMyconnection();
	}


	public Connection getC() {
		return c;
	}


	public void setC(Connection c) {
		this.c = c;
	}


	
        
      public int insertUser(Authentification authentification) {
        
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
            // select le nombre de user dans la bdd, si il existe alors le compte sera = 1
            String sql = "INSERT INTO authentification(username,password) VALUES(?,?)" ;
            PreparedStatement ps = c.prepareStatement(sql) ;
            ps.setString(1,authentification.getUsername()) ;
            ps.setString(2,authentification.getPassword()) ;
           
            ps.executeUpdate() ;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
        return authentification(authentification) ; 
                  
        }
	
	
	public int authentification(Authentification authentification) {
		
		int cpt=0;
		String req="SELECT `username`, `password` FROM `authentification` WHERE "
				+ "username ='"+authentification.getUsername()+"' AND password = '"+authentification.getPassword()+"'";
		try {
    		PreparedStatement p=this.getC().prepareStatement(req);
	    	ResultSet rs=p.executeQuery();
	    	
		
	    	 
             while(rs.next()) cpt++;
	            //verifier l'existance
	            if(cpt > 0 ) 
	            	return 1; 
	            else
	            	return 0;
        } catch (SQLException e ) {
        	System.out.println(e);
            return -1;
        }

	}
	
}
