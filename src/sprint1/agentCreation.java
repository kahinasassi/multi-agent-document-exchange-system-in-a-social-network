package sprint1;



import sprint1.controllers.EtudiantController;
import sprint1.entity.Etudiant;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 
 
public class agentCreation extends Agent {
 
String username,domaine;
 AgentController AgentFils; 
private static ContainerController containerController,cc ; 
 
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
                        username=(String) msg.getContentObject();
                    } catch (UnreadableException ex) {
                        Logger.getLogger(agentCreation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(username.equals("tuer")==false){
                    System.out.println("Actual Agent is    "+ getLocalName());
                    // création du conteneur spécifique au domaine des agents
                    Runtime rt = Runtime.instance();
                    Profile p = new ProfileImpl();
                     cc = rt.createAgentContainer(p);
                    containerController = cc ;                   
                    EtudiantController e = new EtudiantController();
                    this.setEtudiants(e.get_etudiant_domain(username));
                    if(etudiants!=null)
                    {                        
                        System.out.println("l'agent "+ getLocalName()+ " va lancer les agents ");
                        try {                            
                            for(Etudiant es: etudiants)
                            {
                                System.out.println("création d'agent pour l'utilisateur "+es.getNom()) ;
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
                        System.out.println("J4ENVOIE A AGENT ACCUEIL") ;
}

                    
                }else{
                  
                  for(Etudiant es: etudiants){                             
                    ACLMessage aclmessage= new ACLMessage(ACLMessage.INFORM).createReply() ;
                    aclmessage.setContent("tuer");
                    aclmessage.addReceiver(new AID("Agent_"+es.getUsername().getUsername(),AID.ISLOCALNAME)) ;
                    send(aclmessage);
                  }
                  
                  
                    }
                                       }else {
                        block();
                    }                            
                }
           }) ; 
            
	}

  @Override    
        
            protected void takeDown(){
        System.out.println(" l'agent:"+this.getAID().getName()+" i finish my job");

    }

        
        
    public static ContainerController getCc() {
        return containerController;
    }

        
        
        
}