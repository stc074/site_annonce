/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Membres extends Objet {
    
    private int nb;
    
    public Membres() {
        super();
        this.nb=0;
    }
    
    public void calculNb() {
        try {
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nbMembres FROM table_membres";
            Statement state=Objet.getConn().createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nb=result.getInt("nbMembres");
            result.close();
            state.close();
        } catch (NamingException ex) {
            Logger.getLogger(Membres.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } catch (SQLException ex) {
            Logger.getLogger(Membres.class.getName()).log(Level.SEVERE, null, ex);
            this.nb=0;
        } finally {
            try {
                Objet.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Membres.class.getName()).log(Level.SEVERE, null, ex);
                this.nb=0;
            }
        }
    }

    /**
     * @return the nb
     */
    public int getNb() {
        return nb;
    }
}
