

package sprint2.dao;

import sprint2.entity.Message;
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
     public List <Message> getNotReadRecepteur(String recepteur);
        public List <Message> getNotReadEmeteur(String emeteur);
}
