

package sprint3;

import java.io.Serializable;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class GoogleResult implements Serializable{

    private String linkText ; 
    private String linkHref ; 
    
    public GoogleResult()
    {
        
    }

    public GoogleResult(String linkText, String linkHref) {
        this.linkText = linkText;
        this.linkHref = linkHref;
    }
    
    
    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkHref() {
        return linkHref;
    }

    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
    }
    
    
}
