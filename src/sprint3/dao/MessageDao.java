

package sprint3.dao;

import sprint3.entity.Authentification;
import sprint3.entity.Message;
import java.util.List;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public interface MessageDao {

    public void insertMessage(Message message) ; 
    public void updateMessage(Message message) ; 
    public List<Message> getNotRead() ; 
    public List<Message> getNotReadRecepteur(String recepteur) ; 
    public List<Message> getNotReadEmeteur(String emeteur) ;
    // pour déterminer le leader d'opinion
    public int getNbMessagesEnvoyes(String username) ;  
    
    
}
