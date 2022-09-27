

package sprint1;

//<editor-fold defaultstate="collapsed" desc="imports">
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//</editor-fold>

/**
 *
 * @author Nassim Bouhadouf
 * 
 */
public class DatabaseConnection {

    //<editor-fold defaultstate="collapsed" desc="constantes">
   
        public static final String DB_URL = "jdbc:mysql://localhost:3306/" ;
        public static final String DB_NAME = "db_agents" ;
        public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver" ;
        public static final String USER = "root" ;
        public static final String PASS = "" ;

    //</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="requetes">
        private final String sqlCreateEtud = "CREATE TABLE IF NOT EXISTS etudiant"
                + "  ("
                + "idEtud        VARCHAR(10) NOT NULL,"
                + "   nom            VARCHAR(20) ,"
                + "   prenom         VARCHAR(20) ,"
                + "   adresse        VARCHAR(50) ,"
                + "   mail           VARCHAR(50) ,"
                + "   numTeleph      VARCHAR(20) ,"
                + "   idDomaine      VARCHAR(10) NOT NULL ,"
                + "   username       VARCHAR(10) NOT NULL , "
                + "   PRIMARY KEY(idEtud) , "
                + "   CONSTRAINT f_etud_domaine FOREIGN KEY(idDomaine) REFERENCES domaine(idDomaine)  ,"
                + "   CONSTRAINT f_etud_user FOREIGN KEY(username) REFERENCES authentification(username) ); " ;
        
        private final String sqlCreateDomaine= "CREATE TABLE IF NOT EXISTS domaine"
                + "  ("
                + "idDomaine        VARCHAR(10) NOT NULL,"
                + "   nomD            VARCHAR(20)  ,"
                + "   PRIMARY KEY(idDomaine) ) ;";
        
        private final String sqlCreateMessage = "CREATE TABLE IF NOT EXISTS message"
                + "  ("
                + "idMessage        VARCHAR(10) NOT NULL ,"
                + "   idEmeteur         VARCHAR(10) NOT NULL ,"
                + "   idRecepteur       VARCHAR(10) NOT NULL ,"
                + "   document          VARCHAR(20) NOT NULL ,"
                + "   fichier           BLOB NOT NULL ,"
                + "   lu        TINYINT NOT NULL DEFAULT 0 , "
                + "   PRIMARY KEY(idMessage) , "
                + "   CONSTRAINT f_etud_message_emeteur FOREIGN KEY(idEmeteur) REFERENCES etudiant(username) ,"
                + "   CONSTRAINT f_etud_message_recepteur FOREIGN KEY(idRecepteur) REFERENCES etudiant(username) ) ; " ;
        
        private final String sqlCreateAuthentification = "CREATE TABLE IF NOT EXISTS authentification"
                + "  ("
                + "username        VARCHAR(10) NOT NULL PRIMARY KEY,"
                + "   password         VARCHAR(50) ) ;" ;
        
//</editor-fold>
 
        
    public DatabaseConnection()
    {
        
    }
    
    private void addDomain() throws ClassNotFoundException, SQLException
    {
        
        Connection c = getMyconnection() ; 
        Statement s = c.createStatement() ; 
        String sql = "SELECT COUNT(*) FROM domaine ; " ; 
        ResultSet rs = s.executeQuery(sql) ;
        int nb_domaines = 0 ; 
        while(rs.next())
        {
            nb_domaines = rs.getInt(1) ; 
        }
        
        if(nb_domaines==0)
        {
            sql = "INSERT INTO domaine VALUES('MI','MI') ; " ; 
            s.executeUpdate(sql) ; 
            sql = "INSERT INTO domaine VALUES('SNV','SNV') ; " ; 
            s.executeUpdate(sql) ; 
            sql = "INSERT INTO domaine VALUES('SM','SM') ; " ; 
            s.executeUpdate(sql) ; 
        
        }    
        
    }
    
    
    public static Connection getMyconnection() throws ClassNotFoundException, SQLException
    {
        Class.forName(JDBC_DRIVER) ; 
        Connection c = DriverManager.getConnection(DB_URL+DB_NAME,USER,PASS) ;
        
        return c ; 
    }
    
    
    public void createDatabase() throws ClassNotFoundException, SQLException
    {
        
        Class.forName(JDBC_DRIVER) ; 
        Connection c = DriverManager.getConnection(DB_URL,USER,PASS) ;
        Statement s = c.createStatement() ; 
        
        int r = s.executeUpdate("CREATE DATABASE IF NOT EXISTS "+DB_NAME+" ;") ; 
        
        c.close() ; 
        
        c = DriverManager.getConnection(DB_URL+DB_NAME,USER,PASS) ; 
        s = c.createStatement() ; 
        
        s.execute(sqlCreateDomaine) ;
        s.execute(sqlCreateAuthentification) ;
        s.execute(sqlCreateEtud) ;
        s.execute(sqlCreateMessage) ;
        
        
        addDomain() ; 
        
    }
}
