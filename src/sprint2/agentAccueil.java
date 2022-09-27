
package sprint2;

import sprint2.controllers.EtudiantController;
import sprint2.controllers.MessageController;
import sprint2.entity.Etudiant;
import sprint2.entity.Message;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.ControllerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sprint3.GoogleResult;
import sprint1.agentCreation;
import sprint1.myAgent;

/**
 *
 * @author lenovo
 */
public class agentAccueil extends GuiAgent{
        private Accueil gui;// lié l'agent son interface graphique

    
    
        
 @Override
    protected void setup(){
     
        gui=new Accueil();
        gui.setAgentaccueil(this);
                                //    

        System.out.println("Creation et initialisation de l'agent:"+this.getAID().getName());
   
        addBehaviour(new CyclicBehaviour() {
               String username;
               ArrayList<Etudiant> etd;

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
                   
                if("Agent_google".equals(msg.getSender().getLocalName()))
                {
                    try {
                        ArrayList<GoogleResult> results = (ArrayList<GoogleResult>) msg.getContentObject() ;
                        gui.showGoogleSearch(results) ; 
                        
                    } catch (UnreadableException ex) {
                        Logger.getLogger(agentAccueil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    username=(String) msg.getContent(); 
                    System.out.println(username) ;   



                    // traitement 1 : afficher la liste des user : contacts               
                    EtudiantController e = new EtudiantController();
                    MessageController ms= new MessageController();

                    // traitement 1 : r la liste des user : contacts  *********************************************
                    ArrayList<Etudiant> etmp = new ArrayList<>() ; 
                    
                    for(Etudiant el : e.get_etudiant_domain(username))
                    {
                        if(!el.getUsername().getUsername().equals(username))
                            etmp.add(el) ; 
                    }
                    
                     this.setEtudiants(etmp) ;
                     
                     for(Etudiant es: etudiants)
                     {
                               gui.showm(es) ;                      

                     }  
                     System.out.println(etd) ;

                     gui.setUser(username) ;

                     ArrayList<Etudiant> etudiant=e.getOne(username);
                     if(etudiant.isEmpty() ){
                     System.out.println("vide") ;   

                       } else{
                           gui.setVisible(true);

                          System.out.println(etudiant.get(0));
     // traitement 2 : r la liste des DOCUMENT émis  *********************************************                      
                         List <Message>notReadEmeteur=  ms.getNotReadEmeteur(etudiant.get(0).getUsername().getUsername());
                        // affichage 
                          for(Message es: notReadEmeteur)
                          {
                             gui.showmdoclu(es.getDocument());                      

                           } 
     // traitement 3: r la liste des DOCUMENT reçus  *********************************************

                          List <Message>notReadrcepteur=  ms.getNotReadRecepteur(etudiant.get(0).getUsername().getUsername());
                          for(Message es: notReadrcepteur)
                          {
                               gui.showmdoNonclu(es) ; //es.getDocument());                      

                          } 
                    }
                
                   }      
      
                      
                    
                }else {
                        block();
                    } 
                
            }
    
            });
 
    }  
    
    
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
        
           switch(ge.getType())
           {
               case 1 : 
               {
                java.util.Map<String, Object> compt = (java.util.Map<String, Object>) ge.getParameter(0);
                String recepteur = (String)compt.get("recepteur");
                String emeteur = (String)compt.get("emeteur");
                ACLMessage aclmessage= new ACLMessage(ACLMessage.INFORM).createReply() ;
                // envoyer le message à l'emtteur du document
                aclmessage.addReceiver(new AID("Agent_"+emeteur,AID.ISLOCALNAME)) ;
                // lui envoyer l'aid du recepteur 
                aclmessage.setContent("Agent_"+recepteur) ;
                send(aclmessage);
               }
               break ; 
                case 2:{
            
                gui.setVisible(false);
                try {

                  ACLMessage msg= new ACLMessage(ACLMessage.INFORM);
                  String var="tuer";
                  msg.setContentObject(var);
                  msg.addReceiver(new AID("agentCreation",AID.ISLOCALNAME));
                  send(msg);                    
                } catch (IOException ex) {
                  Logger.getLogger(myAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
                gui.dispose();  

               
               }
                break ; 
               case 3 : 
               {
                java.util.Map<String, String> map = (java.util.Map<String, String>) ge.getParameter(0);
                String searchTerm = (String)map.get("searchTerm") ;
                ACLMessage aclmessage= new ACLMessage(ACLMessage.INFORM).createReply() ;
                // envoyer le message à l'agent google 
                aclmessage.addReceiver(new AID("Agent_google",AID.ISLOCALNAME)) ;
                // lui envoyer le terme à rechercher
                aclmessage.setContent(searchTerm) ;
                send(aclmessage);   
               }
               break ; 
           }
           
    
    }
    
}
