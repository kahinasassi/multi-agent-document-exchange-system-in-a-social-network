

package sprint3.controllers;

//<editor-fold defaultstate="collapsed" desc="imports">
import sprint3.dao.MessageDao;
import sprint3.entity.Etudiant;
import sprint3.entity.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sprint3.DatabaseConnection;
//</editor-fold>

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class MessageController implements MessageDao {

    //<editor-fold defaultstate="collapsed" desc="inserer message">
    @Override
    public void insertMessage(Message message) {
        
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
            
            File file = message.getFichier() ;
            FileInputStream input = new FileInputStream(file);
            
            String sql = "INSERT INTO message VALUES(?,?,?,?,?,?)" ;
            PreparedStatement ps = c.prepareStatement(sql) ;
            ps.setString(1,message.generateId()) ;
            ps.setString(2,message.getIdEmeteur()) ;
            ps.setString(3,message.getIdRecepteur()) ;
            ps.setString(4,message.getFichier().getName()) ;
            ps.setBinaryStream(5,input) ;
            ps.setBoolean(6,message.isLu()) ;
            
            ps.executeUpdate() ;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="rendre un message lu">
    @Override
    public void updateMessage(Message message) {
        
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
            
            String sql = "UPDATE message SET lu = ? WHERE idMessage = ? ; " ;
            PreparedStatement ps = c.prepareStatement(sql) ;
            ps.setBoolean(1,true) ;
            ps.setString(2,message.getIdMessage()) ;
            
            ps.executeUpdate() ;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="avoir la liste des messages reçus par recepteur">
    @Override
    public List <Message> getNotReadRecepteur(String recepteur) {
        
        
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
            
            String sql = "SELECT * FROM message WHERE lu = 0 AND idRecepteur = ? ; ";
            PreparedStatement ps = c.prepareStatement(sql) ;
            ps.setString(1, recepteur) ;
            
            ResultSet rs = ps.executeQuery() ;
            
            ArrayList <Message> messages = new ArrayList<>() ;
            
            
            while(rs.next())
            {
                boolean lu = rs.getBoolean("lu") ;
                
                if(!lu)
                {
                    // avoir le nom du document
                    String document = rs.getString("document") ;
                    InputStream input = rs.getBinaryStream("fichier") ;
                    
                    // créer un document dans le dossier documents
                    /* File file = new File(System.getProperty("user.home")+"/Documents/"+document);
                    FileOutputStream output = new FileOutputStream(file);
                    
                    
                    InputStream input = rs.getBinaryStream("fichier");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                    output.write(buffer);
                    
                    }
                    */
                    
                    Message message = new Message(
                            rs.getString("idMessage"),
                            rs.getString("idEmeteur"),
                            rs.getString("idRecepteur")
                    ) ;
                    
                    message.setDocument(document) ;
                    //     message.setFileDownload(output) ;
                    //     message.setFichier(file) ;
                    message.setFileStream(input) ;
                    
                    messages.add(message) ;
                    
                }
            }
            
            return messages ;
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null ;
        
    }
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="avoir la list des messages envoyés par emeteur">
    @Override
    public List <Message> getNotReadEmeteur(String emeteur) {
        
        
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
            
            String sql = "SELECT * FROM message WHERE lu = 0 AND idEmeteur = ?; " ;
            PreparedStatement ps = c.prepareStatement(sql) ;
            ps.setString(1, emeteur) ;
            
            ResultSet rs = ps.executeQuery() ;
            
            ArrayList <Message> messages = new ArrayList<>() ;
            
            
            while(rs.next())
            {
                boolean lu = rs.getBoolean("lu") ;
                
                if(!lu)
                {
                    // avoir le nom du document
                    String document = rs.getString("document") ;
                    // créer un document dans le dossier documents
                    File file = new File(System.getProperty("user.home")+"/Documents/"+document);
                    FileOutputStream output = new FileOutputStream(file);
                    
                    
                    InputStream input = rs.getBinaryStream("fichier");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                        
                    }
                    
                    Message message = new Message(
                            rs.getString("idMessage"),
                            rs.getString("idEmeteur"),
                            rs.getString("idRecepteur")
                    ) ;
                    
                    message.setDocument(document) ;
                    message.setFileDownload(output) ;
                    message.setFichier(file) ;
                    
                    messages.add(message) ;
                    
                }
            }
            
            return messages ;
        }
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null ;
        
    }
//</editor-fold>
   
    @Override
    public List<Message> getNotRead() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNbMessagesEnvoyes(String username) 
    {
     
        int nb_messages = 0 ; 
        
        try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
            
            String sql = "SELECT COUNT(*) FROM message WHERE idEmeteur = ?; " ;
            PreparedStatement ps = c.prepareStatement(sql) ;
            ps.setString(1, username) ;
            
            ResultSet rs = ps.executeQuery() ;
            
            
            while(rs.next())
            {
                 nb_messages = rs.getInt(1) ;
                 
            }
            
        }
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nb_messages ;

    }
    
    
    public Map<Etudiant,Integer> getLeaderOpinion()
    {
        Map<Etudiant,Integer> opinions = new HashMap<>() ; 
        EtudiantController ec = new EtudiantController() ; 
        
         try {
            Connection c = null ;
            Class.forName("com.mysql.jdbc.Driver") ;
            c = DriverManager.getConnection(DatabaseConnection.DB_URL+DatabaseConnection.DB_NAME,DatabaseConnection.USER,DatabaseConnection.PASS) ;
            
            String sql = "SELECT idEmeteur, COUNT(*) FROM message GROUP BY idEmeteur ;  " ;
            PreparedStatement ps = c.prepareStatement(sql) ;
            
            ResultSet rs = ps.executeQuery() ;
            
           
            while(rs.next())
            {
                 String emeteur_username = rs.getString(1) ; 
                 Etudiant etudiant = ec.getOne(emeteur_username).get(0) ; 
                 int nb_messages = rs.getInt(2) ;
                 
                 opinions.put(etudiant, nb_messages) ; 
            }
            
        }
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EtudiantController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         return opinions ; 
    }
      }