
package sprint2.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class Message {

    private String idMessage ; 
    private String idEmeteur ; 
    private String idRecepteur ; 
    private String document ; 
    private File fichier ; 
    private FileOutputStream fileDownload ; 
    private InputStream fileStream ; 
    private boolean lu ; 

    public Message(String idMessage, String idEmeteur, String idRecepteur, File fichier, boolean lu) {
        this.idMessage = idMessage;
        this.idEmeteur = idEmeteur;
        this.idRecepteur = idRecepteur;
        this.fichier = fichier;
        this.lu = lu;
    }

    public Message(String idEmeteur, String idRecepteur) {
        this.idEmeteur = idEmeteur;
        this.idRecepteur = idRecepteur;
    }

    public Message(String idMessage, String idEmeteur, String idRecepteur) {
        this.idMessage = idMessage;
        this.idEmeteur = idEmeteur;
        this.idRecepteur = idRecepteur;
    }
    
    public String getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    public String getIdEmeteur() {
        return idEmeteur;
    }

    public void setIdEmeteur(String idEmeteur) {
        this.idEmeteur = idEmeteur;
    }

    public String getIdRecepteur() {
        return idRecepteur;
    }

    public void setIdRecepteur(String idRecepteur) {
        this.idRecepteur = idRecepteur;
    }

    public File getFichier() {
        return fichier;
    }

    public void setFichier(File fichier) {
        this.fichier = fichier;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    
    public String generateId()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(2, 10);
    }
    
    public void readFile(String path) throws IOException
    {
        this.fichier = new File(path) ; 
        
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setFileDownload(FileOutputStream fileDownload) {
        this.fileDownload = fileDownload;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }
    
    
    
    public void download(String path) throws FileNotFoundException, IOException
    {
               File file = new File(path+"/"+document) ;
               
               
               if(path==null||path.isEmpty())
                 file = new File(System.getProperty("user.home")+"/Documents/"+document);
              
               
               System.out.println(path+"/"+document) ;
               
               FileOutputStream output = new FileOutputStream(file);


               byte[] buffer = new byte[1024];
               while (fileStream.read(buffer) > 0) {
                   output.write(buffer);

               }
        
    }
    
    
}
