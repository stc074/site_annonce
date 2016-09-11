/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Msgrie extends Objet {
    private Membre membre;
    private Connection con;
    private int recNonLus;
    private int envNonLus;
    private long[] idsRecus;
    private String[] titresRecus;
    private String[] commentsRecus;
    private int[] etatsRecus;
    private int nbArrayRecus;
    private long[] idsEnv;
    private String[] titresEnv;
    private String[] commentsEnv;
    private int[] etatsEnv;
    private int nbArrayEnv;
    
    public Msgrie() {
        super();
    }

    public Msgrie(Membre membre) {
        super();
        this.membre=membre;
        this.membre.initInfos2();
        this.recNonLus=0;
        this.envNonLus=0;
    }

    public void calculNonLus() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nb FROM table_messages WHERE id_membre_destinataire=? AND etat='0'";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.recNonLus=result.getInt("nb");
            result.close();
            prepare.close();
            query="SELECT COUNT(id) AS nb FROM table_messages WHERE id_membre_expediteur=? AND etat='0'";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            result=prepare.executeQuery();
            result.next();
            this.envNonLus=result.getInt("nb");
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            this.recNonLus=0;
            this.envNonLus=0;
        } catch (SQLException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            this.recNonLus=0;
            this.envNonLus=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
                this.recNonLus=0;
                this.envNonLus=0;
            }
        }
    }
    
    public void initMsgRecus() {
        try {
            this.nbArrayRecus=0;
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbMsg FROM table_messages WHERE id_membre_destinataire=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbArrayRecus=result.getInt("nbMsg");
            result.close();
            prepare.close();
            if(this.nbArrayRecus>0) {
            this.idsRecus=new long[this.nbArrayRecus];
            this.titresRecus=new String[this.nbArrayRecus];
            this.commentsRecus=new String[this.nbArrayRecus];
            this.etatsRecus=new int[this.nbArrayRecus];
            query="SELECT t1.id,t1.titre,t1.timestamp,t1.etat,t2.pseudo FROM table_messages AS t1,table_membres AS t2 WHERE t1.id_membre_destinataire=? AND t2.id=t1.id_membre_expediteur ORDER BY t1.timestamp DESC";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            result=prepare.executeQuery();
            int i=0;
            Calendar cal=Calendar.getInstance();
            while(result.next()) {
                long idMessage=result.getLong("id");
                String titre=result.getString("titre");
                long timestamp=result.getLong("timestamp");
                int etat=result.getInt("etat");
                String pseudo=result.getString("pseudo");
                cal.setTimeInMillis(timestamp);
                this.idsRecus[i]=idMessage;
                this.titresRecus[i]=titre;
                this.etatsRecus[i]=etat;
                this.commentsRecus[i]="Message envoyé par "+pseudo+", le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                i++;
            }
            result.close();
            prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void effaceRecu(long idMessage, long idMembre) {
        try {
            this.con=Objet.getConnection2();
            String query="UPDATE table_messages SET id_prec='0' WHERE id_prec=? AND id_membre_expediteur=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_messages WHERE id=? AND id_membre_destinataire=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, idMembre);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void initMsgEnv() {
        try {
            this.nbArrayEnv=0;
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbMsg FROM table_messages WHERE id_membre_expediteur=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.nbArrayEnv=result.getInt("nbMsg");
            result.close();
            prepare.close();
            if(this.getNbArrayEnv()>0) {
                this.idsEnv=new long[this.getNbArrayEnv()];
                this.titresEnv=new String[this.getNbArrayEnv()];
                this.commentsEnv=new String[this.getNbArrayEnv()];
                this.etatsEnv=new int[this.getNbArrayEnv()];
                query="SELECT t1.id,t1.titre,t1.timestamp,t1.etat,t2.pseudo FROM table_messages AS t1,table_membres AS t2 WHERE t1.id_membre_expediteur=? AND t2.id=t1.id_membre_destinataire ORDER BY t1.timestamp DESC";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membre.getId());
                result=prepare.executeQuery();
                int i=0;
                Calendar cal=Calendar.getInstance();
                while(result.next()) {
                    long idMessage=result.getLong("id");
                    String titre=result.getString("titre");
                    long timestamp=result.getLong("timestamp");
                    int etat=result.getInt("etat");
                    String pseudo=result.getString("pseudo");
                    cal.setTimeInMillis(timestamp);
                    this.idsEnv[i]=idMessage;
                    this.titresEnv[i]=titre;
                    this.commentsEnv[i]="Message envoyé à "+pseudo+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    this.etatsEnv[i]=etat;
                    i++;
                }
                result.close();
                prepare.close();
            }
        } catch (NamingException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void effaceEnv(long idMessage, long idMembre) {
        try {
            this.con=Objet.getConnection2();
            String query="UPDATE table_messages SET id_prec='0' WHERE id_prec=? AND id_membre_destinataire=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_messages WHERE id=? AND id_membre_expediteur=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, idMembre);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Msgrie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the membre
     */
    public Membre getMembre() {
        return membre;
    }

    /**
     * @return the recNonLus
     */
    public int getRecNonLus() {
        return recNonLus;
    }

    /**
     * @return the envNonLus
     */
    public int getEnvNonLus() {
        return envNonLus;
    }

    /**
     * @return the titresRecus
     */
    public String[] getTitresRecus() {
        return titresRecus;
    }

    /**
     * @return the commentsRecus
     */
    public String[] getCommentsRecus() {
        return commentsRecus;
    }

    /**
     * @return the idsRecus
     */
    public long[] getIdsRecus() {
        return idsRecus;
    }

    /**
     * @return the nbArrayRecus
     */
    public int getNbArrayRecus() {
        return nbArrayRecus;
    }

    /**
     * @return the etatsRecus
     */
    public int[] getEtatsRecus() {
        return etatsRecus;
    }

    /**
     * @return the idsEnv
     */
    public long[] getIdsEnv() {
        return idsEnv;
    }

    /**
     * @return the titresEnv
     */
    public String[] getTitresEnv() {
        return titresEnv;
    }

    /**
     * @return the commentsEnv
     */
    public String[] getCommentsEnv() {
        return commentsEnv;
    }

    /**
     * @return the etatsEnv
     */
    public int[] getEtatsEnv() {
        return etatsEnv;
    }

    /**
     * @return the nbArrayEnv
     */
    public int getNbArrayEnv() {
        return nbArrayEnv;
    }

}
