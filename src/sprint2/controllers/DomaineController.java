
package sprint2.controllers;

import sprint2.entity.Domaine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sprint2.DatabaseConnection;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class DomaineController {

    public int insertDomaine(Domaine domaine)
    { int resault=0;
         try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;

            String sql = "INSERT INTO domaine VALUES(?,?)" ;
            PreparedStatement ps = c.prepareStatement(sql) ;
            ps.setString(1,domaine.getIdDomaine()) ;
            ps.setString(2,domaine.getNomD()) ;
             
            ps.executeUpdate() ;
            resault=1;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resault;
    }
    

    
    
    
    
    
}
