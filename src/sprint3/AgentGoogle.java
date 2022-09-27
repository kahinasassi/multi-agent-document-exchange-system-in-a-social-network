

package sprint3;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class AgentGoogle extends Agent{

    public final String GOOGLE_SEARCH_URL = "https://www.google.com/search" ; 

    @Override
    public void setup()
    {
          
        addBehaviour(new CyclicBehaviour()
        {
            @Override
            public void action() {
                
                ACLMessage msg = receive() ;
                if(msg!=null)
                {
                    
                    try {
                        
                        ArrayList<GoogleResult> googleResult = new ArrayList<>() ; 
                        
                        String searchTerm = msg.getContent() ;
                        
                        
                        String searchURL =  GOOGLE_SEARCH_URL+"?q="+searchTerm+"+pdf" ; //+"&format=json" ;
                        //GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;
                        //without proper User-Agent, we will get 403 error
                        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
                        
                        Elements results = doc.select("a");
                        
                        for (Element result : results) {
                            String linkHref = result.attr("href");
                            
                            String extension = "";
                            
                            int i = linkHref.lastIndexOf('.');
                            if (i > 0) {
                                extension = linkHref.substring(i+1);
                            }
                            
                            if(linkHref.toLowerCase().contains(".pdf"))
                            {
                                
                                String linkText = result.text();
                                System.out.println("Text::" + linkText + ", URL::" + linkHref) ;
                                googleResult.add(new GoogleResult(linkText,linkHref)) ; 
                                
                            }
                            
                           
                        }
                        
                        // envoyer les résultat à agent accueil pour les afficher
                            
                        ACLMessage aclmessage2= msg.createReply();
                        aclmessage2.setPerformative(ACLMessage.INFORM);
                        aclmessage2.setContentObject(googleResult) ;
                        send(aclmessage2) ; 
                    
                    } catch (IOException ex) {
                        Logger.getLogger(AgentGoogle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 
                }
                else
                {
                    block() ;
                }
            }
            
        }) ; 
           
    }
}
