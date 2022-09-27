
package tr ;

import controllers.AuthentificationController;
import controllers.DomaineController;
import controllers.EtudiantController;
import entity.Authentification;
import entity.Domaine;
import entity.Etudiant;
import jade.wrapper.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import java.sql.SQLException;
/**
 *
 * @author sassi kahina
 */
public class InscriptionAgent extends GuiAgent{
private InscriptionGui gui;// lié l'agent son interface graphique

private  EtudiantController incs;  

    public InscriptionAgent() throws ClassNotFoundException, SQLException {
        this.athc = new AuthentificationController(DatabaseConnection.getMyconnection());
    }
public EtudiantController getIncs() {
        return incs;
    }
public void setIncs(EtudiantController incs) {
        this.incs = incs;
    }
private final EtudiantController cv= new EtudiantController();

public AuthentificationController athC;
public AuthentificationController getAthC() {
        return athC;
    }
public void setAthC(AuthentificationController athC) {
        this.athC = athC;
    }
private final AuthentificationController athc;

public DomaineController dnC;
public DomaineController getDnC() {
        return dnC;
    }
public void setDnC(DomaineController dnC) {
        this.dnC = dnC;
    }
private final DomaineController dnc= new DomaineController();

int value=0;


     @Override
    protected void setup(){
// interface
  gui=new InscriptionGui();
  gui.setVisible(true);
   gui.setInscriptionagent(this);

  System.out.println("Creation et initialisation de l'agent:"+this.getAID().getName());


  
  
  
    }            
    protected void beforMove() throws ControllerException{ 
         try {
        System.out.println(" Avant la migration de  l'agent:"+this.getAID().getName());
       
        System.out.println("de"+this.getContainerController().getContainerName());
          } catch (ControllerException ex) {
            Logger.getLogger(InscriptionAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    @Override
    protected void afterMove(){
        System.out.println("Aprés la migration de  l'agent:"+this.getAID().getName());
        try {
            System.out.println("vers"+this.getContainerController().getContainerName());
        } catch (ControllerException ex) {
            Logger.getLogger(InscriptionAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }    
    
    @Override
    protected void takeDown(){
        System.out.println(" l'agent:"+this.getAID().getName()+"vas mourir");

    }

    
    
    
    
    @Override
    protected void onGuiEvent(GuiEvent ge) {
        int D_MIN=0;
        switch(ge.getType()){
            case 1:
            java.util.Map<String, Object> compt = (java.util.Map<String, Object>) ge.getParameter(0);

            String Nom =(String)compt.get("Nom");
            String prenom=(String)compt.get("prenom");
            String mail=(String)compt.get("mail");
            String teleph=(String)compt.get("teleph");
            String user=(String)compt.get("user");
            String adress=(String)compt.get("adress");
            String domain=(String)compt.get("domain");
            String password=(String)compt.get("password");
            String matricule=(String)compt.get("matricule");
            String nomD = null;
            
            switch(domain){
                      case "MI":
                          nomD="mathematique et informatique";
                      case "SNV":
                          nomD="Science de la Vie et de la nature";
                      case "SM":
                          nomD="Science de la Matiere";
            }
            
            Domaine dm = new Domaine(domain,nomD);
            Authentification ath = new Authentification(user,password);
            Etudiant etud = new Etudiant(matricule,Nom, prenom,adress,mail, teleph);

            etud.setDomaine(dm);
            etud.setUsername(ath);

            
            // insertion dans la table authentification
            int var1 = athc.insertUser(ath);
            // insertion dans la table etudiant
            int var = cv.inscription(etud);




             /*TimerTask timerTask = new MyTimerTask();
                    //running timer task as daemon thread
                    Timer timer;
                    timer = new Timer(true);
                    timer.scheduleAtFixedRate(timerTask, 0, 10*1000);*/
            //affichage 
            if((var==1) && (var1==1)) {
            gui.showm(" inscription avec succèes ",true);}
            else{
            gui.showm(" Echec d'inscription ",false);
            }        //cancel after sometime
                    /*try {
                        Thread.sleep(120000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timer.cancel();
                    System.out.println("TimerTask cancelled");
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/



                }

    } 
}
