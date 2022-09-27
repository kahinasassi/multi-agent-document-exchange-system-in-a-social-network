
package sprint1;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author lenovo
 */
public class InscriptionContainer {
 
   
 public static void main(String[] args){
         
    jade.core.Runtime runtime=Runtime.instance();
    Profile profile=new ProfileImpl(false);//profile pour un conteneur non pour un main conteneur
    profile.setParameter(Profile.MAIN_HOST,"localhost");  
    AgentContainer container = runtime.createAgentContainer(profile);
    
    try {
        AgentController ag = container.createNewAgent("etudiant", 
                                                      "sprint1.InscriptionAgent", 
                                                      new Object[] {});//arguments
        ag.start();
    } catch (StaleProxyException e) {
         e.printStackTrace();
    } 

        }

  
    
 

   
       
}
