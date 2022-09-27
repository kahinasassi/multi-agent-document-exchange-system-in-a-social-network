
package sprint1;

//<editor-fold defaultstate="collapsed" desc="imports">
import sprint1.controllers.EtudiantController;
import sprint1.entity.Authentification;
import sprint1.controllers.AuthentificationController;
import sprint1.entity.Etudiant;
import jade.core.AID;
import jade.wrapper.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
//</editor-fold>
//[231,229,255]

/**
 *
 * @author lenovo
 */
public class myAgent extends GuiAgent{

    private LoginGui gui;// lié l'agent son interface graphique
    private String username;
    private String password;
    private AuthentificationController auth;
    private java.util.ArrayList <Etudiant> etudiants = new java.util.ArrayList<>() ;


    public AuthentificationController getAuth() {
        return auth;
    }

    public void setAuth(AuthentificationController auth) {
        this.auth = auth;
    }
    //connection a la base de donnée
    Connection c;

    public myAgent() throws ClassNotFoundException, SQLException {
        this.c = DatabaseConnection.getMyconnection();
        auth = new AuthentificationController(c);

    }

    public java.util.ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(java.util.ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }


    public LoginGui getGui() {
        return gui;
    }

    public void setGui(LoginGui gui) {
        this.gui = gui;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usernmae) {
        this.username = usernmae;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
     @Override
    protected void setup(){
        gui=new LoginGui();
        gui.setVisible(true);
        gui.setMyagent(this);
        System.out.println("Creation et initialisation de l'agent:"+this.getAID().getName());
   

    addBehaviour(new CyclicBehaviour() {
            @Override
    public void action() {
    ACLMessage msg=receive();
    if (msg!= null){      

             java.util.Map<String, Object>  comptes;

             gui.showm(msg.getContent(),true);
             gui.showm(username,true);
             gui.showm(password,true);                      
            }
        else{
        block();
    }                            
            }
            });}  
    
    
    protected void beforMove() throws ControllerException{ 
         try {
        System.out.println(" Avant la migration de  l'agent:"+this.getAID().getName());
       
        System.out.println("de"+this.getContainerController().getContainerName());
          } catch (ControllerException ex) {
            Logger.getLogger(myAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    protected void afterMove(){
        System.out.println("Aprés la migration de  l'agent:"+this.getAID().getName());
        try {
            System.out.println("vers"+this.getContainerController().getContainerName());
        } catch (ControllerException ex) {
            Logger.getLogger(myAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }    
    protected void takeDown(){
        System.out.println(" l'agent:"+this.getAID().getName()+" i finish my job");

    }

    @Override
    protected void onGuiEvent(GuiEvent ge) {
        int D_MIN=0;

    
    java.util.Map<String, Object> compt = (java.util.Map<String, Object>) ge.getParameter(0);
    String username=(String)compt.get("username");
    String password=(String)compt.get("password");
    String domaine;
    
    
    this.setUsername(username);
    this.setPassword(password);
    int au = this.getAuth().authentification(new Authentification(this.getUsername(),this.getPassword())) ;
    
    if( au == 0){gui.showm(" MOT DE PASSE OU NOM D'UTILISATEUR FAUX ",false);}
    if( au == 1){
        
        
        //reccuperer tout les agent du meme domaine 
        EtudiantController e = new EtudiantController();
        this.setEtudiants(e.get_etudiant_domain(username));
        
        // domaine de l'eutdiant courant
//        domaine = this.getEtudiants().get(1).getDomaine().getIdDomaine();
        
        for(Etudiant es: etudiants)
        {
            System.out.println(es) ;
        }
        // envoyer la liste des etuidant qui ont le meme domaine que l'etudiant courant
				
            try {
                
                ACLMessage msg= new ACLMessage(ACLMessage.INFORM);
              //  msg.setContentObject(this.getEtudiants());
                                msg.setContentObject(username);

                msg.addReceiver(new AID("AgentCreation",AID.ISLOCALNAME));
		send(msg);
                                
            } catch (IOException ex) {
                Logger.getLogger(myAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            
	//* crrer l'agent courant dans son conteneur, ----- faire un run de container du domaine, et envoyer username et liste des etudiant a l'agent acceuil ensuite agent acceil se deplace vers container du domaine.
        
      
        
        
        
        
        gui.showm("BIENVENU "+username,true);
    }
    if( au == -1){gui.showm("ERROR BASE DE DONNEE "+username,false);}


    } 
}

