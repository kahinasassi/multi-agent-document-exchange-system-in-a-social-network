package sysmultagt;



import controllers.EtudiantController;
import entity.Etudiant;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 
 
public class AgentB extends Agent {
 
String username,domaine;

private static ContainerController containerController ; 
 
        @Override
	protected void setup(){
 
            
            addBehaviour(new CyclicBehaviour() 
            {
                
                private java.util.ArrayList <Etudiant> etudiants = new java.util.ArrayList<>() ;

                public ArrayList<Etudiant> getEtudiants() {
                    return etudiants;
                }

                public void setEtudiants(ArrayList<Etudiant> etudiants) {
                    this.etudiants = etudiants;
                }

               @Override
               public void action() 
               {
                   
                   
               //receive a message from authentification agent
                ACLMessage msg= receive();
                if(msg != null){
                    try { 
                      //  etudiants = (ArrayList<Etudiant>)msg.getContentObject() ;
                    username=(String) msg.getContentObject();

                    System.out.println("Actual Agent is    "+ getLocalName());
                       
                        // création du conteneur spécifique au domaine des agents
                        Runtime rt = Runtime.instance();
                        Profile p = new ProfileImpl();
                        ContainerController cc = rt.createAgentContainer(p);
                        containerController = cc ; 
                        
                        AgentController AgentFils;

                        
                        
                         EtudiantController e = new EtudiantController();
                         this.setEtudiants(e.get_etudiant_domain(username));
        
                        
                        
                        if(etudiants!=null)
                        {

                        System.out.println("l'agent "+ getLocalName()+ " va lancer les agents ");

                        try {

                               for(Etudiant es: etudiants)
                               {
                                   System.out.println("création d'agent pour l'utilisateur "+es.getNom()) ;
                                   //AgentFils = cc.createNewAgent("Agent_"+es.getNom()+"_"+es.getPrenom(),"sprint1.UserAgent",null);
                                   AgentFils = cc.createNewAgent("Agent_"+es.getUsername().getUsername(),"sprint1.UserAgent",null);
                                   AgentFils.start();
                               }

                        } catch (StaleProxyException es) {
                                es.printStackTrace();
                        }
                                     
                        
                                  
                        ACLMessage msge;
                        msge = new ACLMessage(ACLMessage.INFORM);
                        msge.setContent(username);
                        msge.addReceiver(new AID("agentAccueil",AID.ISLOCALNAME));
                        send(msge);
                           
                        }
                        
                    } catch (UnreadableException ex) {
                        Logger.getLogger(AgentB.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    }else {
                        block();
                    }    
                     
		 
                        
                }
           }) ; 
            
	}

    public static ContainerController getCc() {
        return containerController;
    }

        
        
        
}