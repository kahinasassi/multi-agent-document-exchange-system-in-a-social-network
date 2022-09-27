
package sprint1.entity;

import java.io.Serializable;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class Domaine implements Serializable{

    private String idDomaine ; 
    private String nomD ; 

    public Domaine(String idDomaine, String nomD) {
        this.idDomaine = idDomaine;
        this.nomD = nomD;
    }

    public String getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(String idDomaine) {
        this.idDomaine = idDomaine;
    }

    public String getNomD() {
        return nomD;
    }

    public void setNomD(String nomD) {
        this.nomD = nomD;
    }
    
    
    
}
