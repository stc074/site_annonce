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

/**
 *
 * @author pj
 */
public class Abus extends Objet {
    private int type;
    private long idAnnonce;
    private long id;
    private Connection con;
    private String[] urls;
    private String[] titres;
    private String[] comments;
    private int nbAbus;
    private String titre;
    private String description;
    private String comment;
    
    public Abus() {
        super();
    }

    public Abus(long idAbus) {
        this.id=idAbus;
    }
    public void signalAbus(int type, long idAnnonce) {
        try {
            this.type = type;
            this.idAnnonce = idAnnonce;
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nbAbus FROM table_abus WHERE type=? AND id_annonce=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, this.type);
            prepare.setLong(2, this.idAnnonce);
            ResultSet result=prepare.executeQuery();
            result.next();
            int nb=result.getInt("nbAbus");
            result.close();
            prepare.close();
            if(nb==0) {
                query="INSERT INTO table_abus (type,id_annonce,timestamp) VALUES (?,?,?)";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.type);
                prepare.setLong(2, this.idAnnonce);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(3, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idAbus FROM table_abus WHERE type=? AND id_annonce=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.type);
                prepare.setLong(2, this.idAnnonce);
                result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idAbus");
                result.close();
                prepare.close();
                Mail mail=new Mail(Datas.EMAILADMIN, "ADMINISTRATION", "Un abus signalé !");
                mail.initMailAbus(this.getId());
                mail.send();
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initListe() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbAbus FROM table_abus";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbAbus=result.getInt("nbAbus");
            result.close();
            if(this.getNbAbus()>0) {
            this.urls=new String[this.getNbAbus()];
            this.titres=new String[this.getNbAbus()];
            this.comments=new String[this.getNbAbus()];
            query="SELECT id,type,id_annonce,timestamp FROM table_abus ORDER BY timestamp ASC";
            result=state.executeQuery(query);
            int i=0;
            Calendar cal=Calendar.getInstance();
            while(result.next()) {
                long idAbus=result.getLong("id");
                int type2=result.getInt("type");
                long idAnnonce2=result.getLong("id_annonce");
                long timestamp=result.getLong("timestamp");
                cal.setTimeInMillis(timestamp);
                String table="";
                switch(type2) {
                    case 1:
                        table="table_annonces_normales";
                        break;
                    case 2:
                        table="table_annonces_automobile";
                        break;
                    case 3:
                        table="table_annonces_immobilier";
                        break;
                    case 4:
                        table="table_annonces_emploi";
                        break;
                }
                String query2="SELECT titre FROM "+table+" WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=this.con.prepareStatement(query2);
                prepare.setLong(1, idAnnonce2);
                ResultSet result2=prepare.executeQuery();
                result2.next();
                String titreAnnonce=result2.getString("titre");
                result2.close();
                prepare.close();
                this.urls[i]="./abus-"+idAbus+".html";
                this.titres[i]=titreAnnonce;
                this.comments[i]="Abus signalé le : "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                i++;
            }
            }
            result.close();
            state.close();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void initInfos() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT type,id_annonce,timestamp FROM table_abus WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.type=result.getInt("type");
            this.idAnnonce=result.getLong("id_annonce");
            long timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            String table="";
            switch(this.type) {
                case 1:
                    table="table_annonces_normales";
                    break;
                case 2:
                    table="table_annonces_automobile";
                    break;
                case 3:
                    table="table_annonces_immobilier";
                    break;
                case 4:
                    table="table_annonces_emploi";
                    break;
            }
            query="SELECT titre,description FROM "+table+" WHERE id=? LIMIT 0,1";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            result=prepare.executeQuery();
            result.next();
            this.titre=result.getString("titre");
            this.description=result.getString("description");
            this.comment="Abus signlé, le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
                this.id=0;
            }
        }
    }

    public void ingoreAbus() {
        try {
            this.con=Objet.getConnection2();
            String query="DELETE FROM table_abus WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
    public void effaceAnnonce() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT type,id_annonce FROM table_abus WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.type=result.getInt("type");
            this.idAnnonce=result.getLong("id_annonce");
            result.close();
            prepare.close();
            String table="";
            switch(this.type) {
                case 1:
                    table="table_annonces_normales";
                    break;
                case 2:
                    table="table_annonces_automobile";
                    break;
                case 3:
                    table="table_annonces_immobilier";
                    break;
                case 4:
                    table="table_annonces_emploi";
                    break;
            }
            query="SELECT id_membre FROM "+table+" WHERE id=? LIMIT 0,1";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            result=prepare.executeQuery();
            result.next();
            long idMembre=result.getLong("id_membre");
            result.close();
            prepare.close();
            Annonce annonce=new Annonce();
            annonce.effaceAnnonce(this.idAnnonce, this.type, idMembre);
        } catch (NamingException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Abus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
    }

    /**
     * @return the comments
     */
    public String[] getComments() {
        return comments;
    }

    /**
     * @return the nbAbus
     */
    public int getNbAbus() {
        return nbAbus;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
