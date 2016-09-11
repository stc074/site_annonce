/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Message extends Objet {
    private Membre membreExpediteur;
    private long idAnnonce;
    private int type;
    private Connection con;
    private String table;
    private String titreAnnonce;
    private Membre membreDestinataire;
    private String titreMsg;
    private String contenuMsg;
    private String captcha;
    private long id;
    private String pseudo;
    private long idPrec;
    private String titre;
    private String contenu;
    private long timestamp;
    private int etat;
    private String titrePrec;
    private String contenuPrec;
    private long timestampPrec;
    private String comment;
    private String commentPrec;
    
    public Message() {
        super();
        this.titreMsg="";
        this.contenuMsg="";
    }

    public void initExpediteur(Membre membre, long idAnnonce, int type) {
        try {
            this.membreExpediteur=membre;
            this.idAnnonce=idAnnonce;
            this.type=type;
            this.getMembreExpediteur().initInfos2();
            this.con =Objet.getConnection2();
            this.table="";
            switch(this.getType()) {
                case 1:
                    this.table="table_annonces_normales";
                    break;
                case 2:
                    this.table="table_annonces_automobile";
                    break;
                case 3:
                    this.table="table_annonces_immobilier";
                    break;
                case 4:
                    this.table="table_annonces_emploi";
                    break;
            }
            String query="SELECT id_membre,titre FROM "+this.table+" WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            ResultSet result=prepare.executeQuery();
            result.next();
            long idMembre=result.getLong("id_membre");
            this.titreAnnonce=result.getString("titre");
            result.close();
            prepare.close();
            this.membreDestinataire=new Membre();
            this.getMembreDestinataire().setId(idMembre);
            this.getMembreDestinataire().initInfos2();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idAnnonce=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idAnnonce=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.idAnnonce=0;
            }
        }
    }

     public void getPosts(HttpServletRequest request) {
        this.titreMsg=Objet.codeHTML(request.getParameter("titreMsg"));
        this.contenuMsg=Objet.codeHTML2(request.getParameter("contenuMsg"));
        this.captcha=Objet.codeHTML(request.getParameter("captcha")).toLowerCase();
    }

    public void verifPosts(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            if(this.titreMsg.length()==0)
                this.setErrorMsg("<div>Champ TITRE DU MESSAGE vide.</div>");
            else if(this.titreMsg.length()>80)
                this.setErrorMsg("<div>Champ TITRE DU MESSAGE trop long.</div>");
            if(this.contenuMsg.length()==0)
                this.setErrorMsg("<div>Champ CONTENU DU MESSAGE vide.</div>");
            else if(this.contenuMsg.length()>3000)
                this.setErrorMsg("<div>Champ CONTENU DU MESSAGE trop long.</div>");
            if(this.captcha.length()==0)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            else if(!session.getAttribute("captcha").toString().equals(Objet.getEncoded(this.captcha)))
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void enregPostsCtc(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                this.con=Objet.getConnection2();
                String query="INSERT INTO table_messages (id_annonce,type,id_membre_expediteur,id_membre_destinataire,titre,contenu,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                prepare.setInt(2, this.type);
                prepare.setLong(3, this.membreExpediteur.getId());
                prepare.setLong(4, this.membreDestinataire.getId());
                prepare.setString(5, this.titreMsg);
                prepare.setString(6, this.contenuMsg);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membreExpediteur.getId());
                prepare.setLong(2, this.membreDestinataire.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idMessage");
                result.close();
                prepare.close();
                Mail mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Message envoyé !");
                mail.initMailMessage1(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, this.getId());
                mail.send();
                mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Nouveau message !");
                mail.initMailMessage2(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, this.getId());
                mail.send();
                session.setAttribute("captcha", null);
                this.pseudo=this.membreDestinataire.getPseudo();
                this.blank();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initRecu(Membre membre, long idMessage) {
        try {
            this.setTest(0);
            Calendar cal=Calendar.getInstance();
            this.id=idMessage;
            this.membreDestinataire=membre;
            this.membreDestinataire.initInfos2();
            this.con=Objet.getConnection2();
            String query="SELECT t1.id_annonce,t1.type,t1.id_prec,t1.id_membre_expediteur,t1.titre,t1.contenu,t1.timestamp,t1.etat FROM table_messages AS t1 WHERE id=? AND id_membre_destinataire=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.membreDestinataire.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idAnnonce=result.getLong("id_annonce");
            this.type=result.getInt("type");
            this.idPrec=result.getLong("id_prec");
            this.titre=result.getString("titre");
            long idMembreExpediteur=result.getLong("id_membre_expediteur");
            this.contenu=result.getString("contenu");
            this.timestamp=result.getLong("timestamp");
            this.etat=result.getInt("etat");
            result.close();
            prepare.close();
            this.table="";
            switch(this.type) {
                case 1:
                    this.table="table_annonces_normales";
                    break;
                case 2:
                    this.table="table_annonces_automobile";
                    break;
                case 3:
                    this.table="table_annonces_immobilier";
                    break;
                case 4:
                    this.table="table_annonces_emploi";
                    break;
            }
            query="SELECT titre FROM "+this.table+" WHERE id=? LIMIT 0,1";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            result=prepare.executeQuery();
            result.next();
            this.titreAnnonce=result.getString("titre");
            result.close();
            prepare.close();
            cal.setTimeInMillis(this.timestamp);
            this.membreExpediteur=new Membre();
            this.membreExpediteur.setId(idMembreExpediteur);
            this.membreExpediteur.initInfos2();
            if(this.idPrec==0)
                this.comment=this.membreExpediteur.getPseudo()+" vous a écrit le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+" :";
            else
                this.comment=this.membreExpediteur.getPseudo()+" vous a répondu le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+" :";
            if(this.getEtat()==0) {
                query="UPDATE table_messages SET etat='1' WHERE id=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.getId());
                prepare.setLong(2, this.membreDestinataire.getId());
                prepare.executeUpdate();
                prepare.close();
            }
            if(this.getIdPrec()!=0) {
                query="SELECT titre,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_expediteur=? LIMIT 0,1";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.getIdPrec());
                prepare.setLong(2, this.membreDestinataire.getId());
                result=prepare.executeQuery();
                result.next();
                this.titrePrec=result.getString("titre");
                this.contenuPrec=result.getString("contenu");
                this.timestampPrec=result.getLong("timestamp");
                cal.setTimeInMillis(this.timestampPrec);
                result.close();
                prepare.close();
                this.commentPrec="Vous aviez écrit à "+this.membreExpediteur.getPseudo()+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+" :";
            }
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.id=0;
            }
        }
    }

    public void enregPostsRecu(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                this.con=Objet.getConnection2();
                String query="INSERT INTO table_messages (id_annonce,type,id_prec,id_membre_expediteur,id_membre_destinataire,titre,contenu,timestamp) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                prepare.setInt(2, this.type);
                prepare.setLong(3, this.id);
                prepare.setLong(4, this.membreDestinataire.getId());
                prepare.setLong(5, this.membreExpediteur.getId());
                prepare.setString(6, this.titreMsg);
                prepare.setString(7, this.contenuMsg);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(8, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMsg FROM table_messages WHERE id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membreDestinataire.getId());
                prepare.setLong(2, this.membreExpediteur.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                long idMessage=result.getLong("idMsg");
                result.close();
                prepare.close();
                session.setAttribute("captcha", null);
                Mail mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Message envoyé !");
                mail.initMailMessage1(this.membreDestinataire.getPseudo(), this.membreExpediteur.getPseudo(), this.titreMsg, idMessage);
                mail.send();
                mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Nouveau message !");
                mail.initMailMessage2(this.membreDestinataire.getPseudo(), this.membreExpediteur.getPseudo(), this.titreMsg, idMessage);
                mail.send();
                this.pseudo=this.membreExpediteur.getPseudo();
                this.blank();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initEnv(Membre membre, long idMessage) {
        try {
            this.setTest(0);
            this.membreExpediteur=membre;
            this.id=idMessage;
            this.membreExpediteur.initInfos2();
            this.con=Objet.getConnection2();
            String query="SELECT id_annonce,type,id_prec,id_membre_destinataire,titre,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_expediteur=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membreExpediteur.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idAnnonce=result.getLong("id_annonce");
            this.type=result.getInt("type");
            this.idPrec=result.getLong("id_prec");
            long idMembreDestinataire=result.getLong("id_membre_destinataire");
            this.titre=result.getString("titre");
            this.contenu=result.getString("contenu");
            this.timestamp=result.getLong("timestamp");
            this.membreDestinataire=new Membre();
            this.membreDestinataire.setId(idMembreDestinataire);
            this.membreDestinataire.initInfos2();
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            if(this.idPrec==0)
                this.comment="Vous aviez écrit à "+this.membreDestinataire.getPseudo()+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+" :";
            else if(idPrec!=0)
                this.comment="Vous aviez répondu à "+this.membreDestinataire.getPseudo()+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+" :";
            result.close();
            prepare.close();
            this.table="";
            switch(this.type) {
                case 1:
                    this.table="table_annonces_normales";
                    break;
                case 2:
                    this.table="table_annonces_automobile";
                    break;
                case 3:
                    this.table="table_annonces_immobilier";
                    break;
                case 4:
                    this.table="table_annonces_emploi";
                    break;
            }
            query="SELECT titre FROM "+this.table+" WHERE id=? LIMIT 0,1";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            result=prepare.executeQuery();
            result.next();
            this.titreAnnonce=result.getString("titre");
            result.close();
            prepare.close();
            if(this.idPrec!=0) {
                query="SELECT titre,contenu,timestamp FROM table_messages WHERE id=? AND id_membre_destinataire=? LIMIT 0,1";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idPrec);
                prepare.setLong(2, this.membreExpediteur.getId());
                result=prepare.executeQuery();
                result.next();
                this.titrePrec=result.getString("titre");
                this.contenuPrec=result.getString("contenu");
                this.timestampPrec=result.getLong("timestamp");
                result.close();
                prepare.close();
                cal.setTimeInMillis(this.timestampPrec);
                this.commentPrec=this.membreDestinataire.getPseudo()+" vous avait écrit, le "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+" :";
            }
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.id=0;
            }
        }
    }

    public void enregPostsEnv(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                this.con=Objet.getConnection2();
                String query="INSERT INTO table_messages (id_annonce,type,id_prec,id_membre_expediteur,id_membre_destinataire,titre,contenu,timestamp) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                prepare.setInt(2, this.type);
                prepare.setLong(3, this.idPrec);
                prepare.setLong(4, this.membreExpediteur.getId());
                prepare.setLong(5, this.membreDestinataire.getId());
                prepare.setString(6, this.titreMsg);
                prepare.setString(7, this.contenuMsg);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(8, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_membre_expediteur=? AND id_membre_destinataire=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membreExpediteur.getId());
                prepare.setLong(2, this.membreDestinataire.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                long idMessage=result.getLong("idMessage");
                result.close();
                prepare.close();
                session.setAttribute("captcha", null);
                this.pseudo=this.membreDestinataire.getPseudo();
                Mail mail=new Mail(this.membreExpediteur.getEmail(), this.membreExpediteur.getPseudo(), "Message envoyé !");
                mail.initMailMessage1(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, idMessage);
                mail.send();
                mail=new Mail(this.membreDestinataire.getEmail(), this.membreDestinataire.getPseudo(), "Nouveau message !");
                mail.initMailMessage2(this.membreExpediteur.getPseudo(), this.membreDestinataire.getPseudo(), this.titreMsg, idMessage);
                mail.send();                
                this.blank();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    @Override
    public void blank() {
        super.blank();
        this.membreDestinataire=null;
        this.membreExpediteur=null;
        this.titreMsg="";
        this.contenuMsg="";
    }
     
    /**
     * @return the idAnnonce
     */
    public long getIdAnnonce() {
        return idAnnonce;
    }

    /**
     * @return the membreExpediteur
     */
    public Membre getMembreExpediteur() {
        return membreExpediteur;
    }

    /**
     * @return the membreDestinataire
     */
    public Membre getMembreDestinataire() {
        return membreDestinataire;
    }

    /**
     * @return the titreAnnonce
     */
    public String getTitreAnnonce() {
        return titreAnnonce;
    }

    /**
     * @return the titreMsg
     */
    public String getTitreMsg() {
        return titreMsg;
    }

    /**
     * @return the contenuMsg
     */
    public String getContenuMsg() {
        return contenuMsg;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the idPrec
     */
    public long getIdPrec() {
        return idPrec;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the etat
     */
    public int getEtat() {
        return etat;
    }

    /**
     * @return the titrePrec
     */
    public String getTitrePrec() {
        return titrePrec;
    }

    /**
     * @return the contenuPrec
     */
    public String getContenuPrec() {
        return contenuPrec;
    }

    /**
     * @return the timestampPrec
     */
    public long getTimestampPrec() {
        return timestampPrec;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return the commentPrec
     */
    public String getCommentPrec() {
        return commentPrec;
    }

}
