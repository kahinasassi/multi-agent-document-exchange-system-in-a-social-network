package sprint1;
import sprint2.Inscription;
import sprint1.controllers.AuthentificationController;
import sprint1.controllers.EtudiantController;
import sprint1.entity.Etudiant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sprint1.entity.Authentification;
import sprint1.entity.Domaine;
import java.util.ArrayList;
/**
 *
 * @author SASSI KAHINA
 * 
 */
public class test {

	
	public static void main(String[]args) throws ClassNotFoundException, SQLException {
		
            
                DatabaseConnection d = new DatabaseConnection() ; 
                d.createDatabase() ; 
                
                Connection c = DatabaseConnection.getMyconnection();
		
                ArrayList<Etudiant> es = new ArrayList<>() ; 
                
                Etudiant e = new Etudiant("58648", "djezzar", "mehdi", "5 maisn", "mehdi@gmail.com", "055864885") ;
		e.setDomaine(new Domaine("mi","MI"));	
                e.setUsername(new Authentification("mehdi", "mehdi"));
		es.add(e) ; 
                Etudiant e2 = new Etudiant("58696", "elkefif", "nassim", "in naadja", "nassim@gmail.com", "055698665") ; 
                e2.setDomaine(new Domaine("mi","MI"));
		e2.setUsername(new Authentification("nassim", "nassim")) ;
		es.add(e2) ;  
                Etudiant e3 = new Etudiant("5685", "boudissa", "ihab", "oulad fayet", "ihab@gmail.com", "055685965") ; 
                e3.setDomaine(new Domaine("mi","MI"));
		e3.setUsername(new Authentification("ihab", "ihab")) ;
		es.add(e3) ; 
                Etudiant e4 = new Etudiant("5685", "amara", "ilyes", "kherouba", "ilyes@gmail.com", "055659885") ; 
                e4.setDomaine(new Domaine("sm","SM"));
		e4.setUsername(new Authentification("ilyas", "ilyas")) ;
		es.add(e4) ; 
                Etudiant e5 = new Etudiant("56885", "sassi", "kahina", "5 juillet", "kahina@gmail.com", "0558665") ; 
                e5.setDomaine(new Domaine("sm","SM"));
		e5.setUsername(new Authentification("kahina", "kahina")) ;
		es.add(e5) ; 
                
                AuthentificationController aut = new AuthentificationController(c);
                EtudiantController ec = new EtudiantController() ; 


                for(Etudiant etudiant : es)
                {
                    aut.insertUser(etudiant.getUsername()) ; 
                    ec.insertEtudiant(etudiant) ; 
                }
        }
}
