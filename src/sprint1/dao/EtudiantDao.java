

package sprint1.dao;

import sprint1.entity.Etudiant;
import java.util.ArrayList;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public interface EtudiantDao {

    public int insertEtudiant(Etudiant etudiant) ; 
    public ArrayList <Etudiant> getAll(String domaine) ;
    public int inscription(Etudiant etudiant);
    public int get_id(String username);
    public ArrayList <Etudiant> getOne(String username);
    public ArrayList <Etudiant> get_etudiant_domain(String username);

}
