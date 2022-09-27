

package sprint1;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class UserAgent extends Agent{

    @Override
    protected void setup(){
        
        addBehaviour(new CyclicBehaviour() {
        @Override
        public void action() { // je ne fait rien jusqu'as la réception du message
            
            ACLMessage aclmessage=receive();

            if(aclmessage!=null){
                
                String contenu =aclmessage.getContent();
                System.out.println(contenu);
                
                if(contenu.toLowerCase().contains("agent"))
                {
                    System.out.println("receiving oreder from agentAccueil and sending acl message to "+contenu) ;
                    
                    ACLMessage messageDocument= new ACLMessage(ACLMessage.INFORM).createReply() ;
                    // envoyer le message au recepteur
                    messageDocument.addReceiver(new AID(contenu,AID.ISLOCALNAME)) ;
                    messageDocument.setContent("#USER_TO_USER salut je viens d'envoyer un document . consultez votre boite de réception!") ;
                    send(messageDocument);
                }
                else if(contenu.contains("#USER_TO_USER"))
                {
                    System.out.println("receiving message from an agent ( document ) ") ;
                    
                    // la reponse
                    ACLMessage aclmessage2= aclmessage.createReply();
                    aclmessage2.setPerformative(ACLMessage.INFORM);
                    aclmessage2.setContent("bien reçu ! merci");
                    send(aclmessage2) ;
                }
                else if(contenu.contains("tuer")){

                 takeDown();  
                 doDelete();
                 
                 }
                
             }
             else block();
                     }
                 });

        
    }}
    