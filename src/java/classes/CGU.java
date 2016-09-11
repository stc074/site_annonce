/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class CGU extends Objet {
    
    private long id;
    private String texte;
    private long timestamp;
    private Connection con;
    private String ligne1;

    public CGU() {
        super();
    }
    
    public void initDonnees() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT id,texte,timestamp FROM table_cgus LIMIT 0,1";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.id=result.getLong("id");
            this.texte=result.getString("texte");
            this.timestamp=result.getLong("timestamp");
            result.close();
            state.close();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.ligne1="Dernières modifications, le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
        } catch (NamingException ex) {
            Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void getPosts(HttpServletRequest request) {
        this.texte=Objet.codeHTML2(request.getParameter("texte"));
    }

    public void verifPosts() {
        if(this.texte.length()==0)
            this.setErrorMsg("<div>Champ TEXTE vide.</div>");
        else if(this.texte.length()>10000)
            this.setErrorMsg("<div>Champ TEXTE trop long.</div>");
    }

    public void enregTexte() {
        if(this.getErrorMsg().length()==0) {
        try {
            this.con=Objet.getConnection2();
            String query="UPDATE table_cgus SET texte=?,timestamp=? WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.texte);
            Calendar cal=Calendar.getInstance();
            this.timestamp=cal.getTimeInMillis();
            prepare.setLong(2, this.timestamp);
            prepare.setLong(3, this.id);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(CGU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the texte
     */
    public String getTexte() {
        return texte;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the ligne1
     */
    public String getLigne1() {
        return ligne1;
    }

}
