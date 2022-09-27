
package sprint3;

import sprint3.controllers.MessageController;
import sprint3.entity.Etudiant;
import java.util.Map;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class LeaderOpinion {
    
    
    private Etudiant etudiant ; 
    private MessageController mc ; 
    private int nb_messages ;
    
    public LeaderOpinion()
    {
        mc = new MessageController() ; 
    }
    
    public Etudiant getLeader()
    {
        Map<Etudiant,Integer> leaders = mc.getLeaderOpinion() ; 
        Map.Entry<Etudiant, Integer> maxEntry = null;
        for (Map.Entry<Etudiant, Integer> entry : leaders.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        this.etudiant = maxEntry.getKey() ; 
        this.nb_messages = maxEntry.getValue() ; 
       
        return this.etudiant ; 
    }
    
    public int getNbMessages()
    {
        return this.nb_messages ; 
    }

}
