package sprint2;

import sprint1.DatabaseConnection;
import sprint2.entity.Etudiant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author SASSI KAHINA
 * 
 */
public class Inscription {
	
	Connection c;

	
	
	public Inscription(Connection c) throws ClassNotFoundException, SQLException{
		super();
		this.c = DatabaseConnection.getMyconnection();
	}


	public Connection getC() {
		return c;
	}


	public void setC(Connection c) {
		this.c = c;
	}


	public int exist_etudiant(String idEtud,String username) throws SQLException {
		     
		    int result=0;
		    int cpt=0;
			String req="SELECT `idEtud`,`username` FROM `etudiant` WHERE idEtud='"+idEtud+"' OR username='"+username+"'";

			System.out.println("existe?  "+req);
	    	try {
	    		
	    		PreparedStatement p_id=this.getC().prepareStatement(req);
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
	
	
		
		
	public int insert_etudiant(Etudiant etudiant) throws SQLException{
		
		int result=0;
		
		String req_1="INSERT INTO `authentification`(`username`, `password`) VALUES "
				+ "('"+etudiant.getUsername().getUsername()+"',"
				+ "'"+etudiant.getUsername().getPassword()+"')";
        
		String req_2="INSERT INTO `etudiant`(`idEtud`, `nom`, `prenom`, `adresse`, `mail`, `numTeleph`, `idDomaine`, `username`) VALUES "
				+ "('"+etudiant.getIdEtud()+"',"
				+ "'"+etudiant.getNom()+"',"
				+ "'"+etudiant.getPrenom()+"',"
				+ "'"+etudiant.getAdresse()+"',"
				+ "'"+etudiant.getEmail()+"',"
				+ "'"+etudiant.getNumTelph()+"',"
				+ "'"+etudiant.getDomaine()+"',"
				+ "'"+etudiant.getUsername().getUsername()+"')";
		
		System.out.println("INSCRIPTION ------------------ \nreq1:\n"+req_1+"\nrq2:\n"+req_2);
		try {
		 	PreparedStatement p=this.getC().prepareStatement(req_1);
		 	p.executeUpdate();
		 	PreparedStatement p2=this.getC().prepareStatement(req_2); 
		 	p2.executeUpdate();
			
            result=1;
        } catch (SQLException e ) {
        	System.out.println(e);
            result=-1;
        }

		
   
		return result;
	
	}
	
	public int inscription(Etudiant etudiant) throws SQLException {
		int test=this.exist_etudiant(etudiant.getIdEtud(),etudiant.getUsername().getUsername());
		System.out.println("0000000000000000000000000000__"+test);

		if( test == 0) {
			return insert_etudiant(etudiant);
		}
		else return test;
			
	}
}
