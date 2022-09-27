


package sprint1;

//<editor-fold defaultstate="collapsed" desc="imports">
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
//</editor-fold>

/**
 *
 * @author lenovo
 */
public class LoginContainer {
 
   
 public static void main(String[] args){
         
    jade.core.Runtime runtime=Runtime.instance();
    Profile profile=new ProfileImpl(false);//profile pour un conteneur non pour un main conteneur
    profile.setParameter(Profile.MAIN_HOST,"localhost");  
    AgentContainer container = runtime.createAgentContainer(profile);
    
    try {
        AgentController ag = container.createNewAgent("STUDENT", 
                                 "sprint1.myAgent", 
                                      new Object[] {});//arguments
        ag.start();
        AgentController ag2= container.createNewAgent("AgentCreation", 
                                 "sprint1.agentCreation", 
                                      new Object[] {});//arguments
        ag2.start();
        AgentController ag3= container.createNewAgent("agentAccueil", 
                                 "sprint2.agentAccueil", 
                                      new Object[] {});//arguments
        ag3.start();
        AgentController ag4= container.createNewAgent("Agent_google", 
                                 "sprint3.AgentGoogle", 
                                      new Object[] {});//arguments
        ag4.start();
        
} catch (StaleProxyException e) {
    e.printStackTrace();
} 

        }

  
    
 

   
       
}
