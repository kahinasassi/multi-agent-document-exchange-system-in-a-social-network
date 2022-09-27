
package sprint2.controllers;

import sprint2.dao.EtudiantDao;
import sprint2.entity.Authentification;
import sprint2.entity.Domaine;
import sprint2.entity.Etudiant;
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
public class EtudiantController implements EtudiantDao {

  
    
     @Override
    public ArrayList <Etudiant> getAll(String domaine) {
        
        ArrayList <Etudiant> etudiants = new ArrayList<>() ; 

                    
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;

            String sql = "SELECT * FROM etudiant WHERE domaine = ? ; " ;
            
            PreparedStatement s = c.prepareStatement(sql) ;
            s.setString(1,domaine) ;
            
            ResultSet rs =  s.executeQuery() ; 
           
            
            int i = 1 ; 
            
            while(rs.next())
            {
                Etudiant e = rs.getObject(i, Etudiant.class) ; 
                etudiants.add(e) ; 
                
                i++ ; 
            }
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                  
        return etudiants ; 
    }
    
    
     @Override
    public int insertEtudiant(Etudiant etudiant){
		
		int result=0;
		
		  Connection c = null ;
         try {
             Class.forName("com.mysql.jdbc.Driver") ;
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
             c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
         } catch (SQLException ex) {
             Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
         }

        
		String req_2="INSERT INTO `etudiant`(`idEtud`, `nom`, `prenom`, `adresse`, `mail`, `numTeleph`, `idDomaine`, `username`) VALUES "
				+ "('"+etudiant.getIdEtud()+"',"
				+ "'"+etudiant.getNom()+"',"
				+ "'"+etudiant.getPrenom()+"',"
				+ "'"+etudiant.getAdresse()+"',"
				+ "'"+etudiant.getEmail()+"',"
				+ "'"+etudiant.getNumTelph()+"',"
				+ "'"+etudiant.getDomaine().getIdDomaine()+"',"
				+ "'"+etudiant.getUsername().getUsername()+"')";
	
		System.out.println("INSCRIPTION ------------------ \nreq1:\n"+req_2+"\nrq2:\n"+req_2);
		try {
		 	PreparedStatement p2=c.prepareStatement(req_2); 
		 	p2.executeUpdate();
			
            result=1;
        } catch (SQLException e ) {
        	System.out.println(e);
            result=-1;
        }


   
		return result;
	
	}

    
    
    
    
    

    /**
     *
     * @param idEtud
     * @param username
     * @return
     * @throws SQLException
     */
    public int exist_etudiant(String idEtud,String username) throws SQLException {
		        Connection c = null ;
        try {
            Class.forName("com.mysql.jdbc.Driver") ;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;

		    int result=0;
		    int cpt=0;
			String req="SELECT `idEtud`,`username` FROM `etudiant` WHERE idEtud='"+idEtud+"' OR username='"+username+"'";

			System.out.println("existe?  "+req);
	    	try {
	    		
	    		PreparedStatement p_id=c.prepareStatement(req);
		    	ResultSet rs_id=p_id.executeQuery();
		    	
	            while(rs_id.next()) cpt++;
	            //verifier l'existance
	            if(cpt > 0 ) 
	            	return 1; 
	            else
	            	return 0;
	            

	        } catch (SQLException e ) {
	        	System.out.println(e);
	            result= -1;
	        }
			return result;

		}
	
    
    public ArrayList<Etudiant> idEtudiant(String username) throws SQLException {
		        Connection c = null ;
                                  ArrayList <Etudiant> etudiants = new ArrayList<>() ;

        try {
            Class.forName("com.mysql.jdbc.Driver") ;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;

		    int result=0;
		    int cpt=0;
			String req="SELECT `idEtud` FROM `etudiant` WHERE  username=?;";

			System.out.println("existe?  "+req);
                                PreparedStatement p_id;

	    	  try {
             p_id = c.prepareStatement(req);
             ResultSet rs=p_id.executeQuery();
             
             int i = 0 ;
             while(rs.next())
            {
                String nom = rs.getString("nom") ; 
                String prenom = rs.getString("prenom") ; 
                String id = rs.getString("idEtud") ; 
                String adresse = rs.getString("adresse") ; 
                String num = rs.getString("numTeleph") ;  
                String idDomain = rs.getString("idDomaine") ;
                String mail = rs.getString("mail") ;  
                String user = rs.getString("username") ; 
                
                
                
                Etudiant e =  new Etudiant(id,nom,prenom,adresse,mail,num) ; 
                e.setDomaine(new Domaine(idDomain,idDomain)) ;
                
                etudiants.add(e) ; 
                
                i++ ; 
            }

         } catch (SQLException ex) {
             Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
         }
		   
		
             		        System.out.println("hnaaaaaaa"+etudiants.size());
    
            
                
                return etudiants; 

		}
    
    
    
    /**
     *
     * @param etudiant
     * @return
     * @throws SQLException
     */
    @Override
	public int inscription(Etudiant etudiant) {
		int test = 0;
        try {
            test = this.exist_etudiant(etudiant.getIdEtud(),etudiant.getUsername().getUsername());
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
		System.out.println("0000000000000000000000000000__"+test);

		if( test == 0) {
			return insertEtudiant(etudiant);
		}
		else return test;
			
	}



    @Override
    public ArrayList <Etudiant> get_etudiant_domain(String username) {
        
          Connection c = null ;
          ArrayList <Etudiant> etudiants = new ArrayList<>() ;
          
        try {
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
             Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
         }
      
        String requete;
        requete = "select distinct etudiant.* from etudiant where idDomaine in ( select idDomaine from etudiant where etudiant.username='"+username+"')";
        PreparedStatement p_id;
         try {
             p_id = c.prepareStatement(requete);
             ResultSet rs=p_id.executeQuery();
             
             int i = 1 ;
             while(rs.next())
            {
                String nom = rs.getString("nom") ; 
                String prenom = rs.getString("prenom") ; 
                String id = rs.getString("idEtud") ; 
                String adresse = rs.getString("adresse") ; 
                String num = rs.getString("numTeleph") ;  
                String idDomain = rs.getString("idDomaine") ;
                String mail = rs.getString("mail") ;  
                String user = rs.getString("username") ; 
                
                
                
                Etudiant e =  new Etudiant(id,nom,prenom,adresse,mail,num) ; 
                e.setDomaine(new Domaine(idDomain,idDomain)) ;
                e.setUsername(new Authentification(user,"")) ;
                
                etudiants.add(e) ; 
                
                i++ ; 
            }

         } catch (SQLException ex) {
             Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
         }
		    
        
        return etudiants;
    }

    @Override
    public int get_id(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public ArrayList <Etudiant> getOne(String username) {
        
        ArrayList <Etudiant> etudiants = new ArrayList<>() ; 

                    
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;

            String sql = "SELECT * FROM etudiant WHERE username = ? ; " ;
            
            PreparedStatement s = c.prepareStatement(sql) ;
            s.setString(1,username) ;
            
            ResultSet rs =  s.executeQuery() ; 
           
            
            int i = 1 ; 
            
            while(rs.next())
            {
           String nom = rs.getString("nom") ; 
                String prenom = rs.getString("prenom") ; 
                String id = rs.getString("idEtud") ; 
                String adresse = rs.getString("adresse") ; 
                String num = rs.getString("numTeleph") ;  
                String idDomain = rs.getString("idDomaine") ;
                String mail = rs.getString("mail") ;  
                String user = rs.getString("username") ; 
                
                
                
                Etudiant e =  new Etudiant(id,nom,prenom,adresse,mail,num) ; 
                e.setDomaine(new Domaine(idDomain,idDomain)) ;
                e.setUsername(new Authentification(username,username)) ; 
                
                etudiants.add(e) ; 
                i++ ; 
            }
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
                                 		        System.out.println("hnaaaaaaa"+etudiants.size());

                  
        return etudiants ; 
    }
   
    
    
            
    
    
}
