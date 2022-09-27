

package sprint3.entity;

import sprint1.entity.*;
import java.io.Serializable;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class Etudiant implements Serializable{

    private String idEtud ; 
    private String nom ; 
    private String prenom ; 
    private String adresse ; 
    private String email ; 
    private String numTelph ; 
    
    private Domaine domaine ; 
    private Authentification username ; 
    
    
    public Etudiant()
    {
        
    }

    public Etudiant(String idEtud, String nom, String prenom, String adresse, String email, String numTelph) {
        this.idEtud = idEtud;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.numTelph = numTelph ; 
        
    }

    public String getIdEtud() {
        return idEtud;
    }

    public void setIdEtud(String idEtud) {
        this.idEtud = idEtud;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTelph() {
        return numTelph;
    }

    public void setNumTelph(String numTelph) {
        this.numTelph = numTelph;
    }
    

    public Authentification getUsername() {
        return username;
    }

    public void setUsername(Authentification username) {
        this.username = username;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }
    
    
    
    
}
