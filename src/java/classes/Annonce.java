/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
import java.io.IOException;
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
public class Annonce extends Objet {

    private Membre membre;
    private String titre;
    private String description;
    private String captcha;
    private long id;
    private int type;
    private String table;
    private String extensions[];
    private int largeurs[];
    private int hauteurs[];
    private long timestamp;
    private long idMembre;
    private String tagTitle;
    private String tagDescription;
    private String uri;
    private Connection con;
    private String dateString;
    private String ligne1;
    private String ligne2;
    private String uriRetour;
    private Connection con2;

    public Annonce() {
        super();
        this.extensions=new String[5];
        this.tagTitle="Petites annonces gratuites";
        this.tagDescription="Megannonce - Petites annonces gratuites.";
    }
    
    public Annonce(Membre membre) {
        super();
        try {
            this.membre = membre;
            this.membre.initInfos();
            this.titre="";
            this.description="";
            this.captcha="";
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.membre.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.membre.setId(0);
        }
    }

    public Annonce(long idAnnonce) {
        this.id=idAnnonce;
    }

    public void getPostsDepot(HttpServletRequest request) {
        this.titre=request.getParameter("titre");
        this.titre=Objet.codeHTML(this.titre);
        this.description=request.getParameter("description");
        this.description=Objet.codeHTML2(this.description);
        this.captcha=request.getParameter("captcha").toLowerCase();
        this.captcha=Objet.codeHTML(this.captcha);
    }

    public void verifPostsDepot(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            if (this.titre.length() == 0) {
                this.setErrorMsg("<div>Champ TITRE DE L'ANNONCE vide.</div>");
            } else if (this.titre.length() > 80) {
                this.setErrorMsg("<div>Champ TITRE trop long.</div>");
            }
            if (this.description.length() == 0) {
                this.setErrorMsg("<div>Champ DESCRIPTION vide.</div>");
            } else if (this.description.length() > 5000) {
                this.setErrorMsg("<div>Champ DESCRIPTION trop long.</div>");
            }
            if (this.captcha.length() == 0) {
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            } else if (this.captcha.length() > 5) {
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            } else if (session.getAttribute("captcha") == null) {
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            } else if (!session.getAttribute("captcha").toString().equals(Objet.getEncoded(this.getCaptcha()))) {
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }

    }
    public void initDonnees(long idAnnonce) {
        try {
            this.id=idAnnonce;
            Objet.getConnection();
            /*String query="UPDATE "+this.table+" SET last_timestamp=? WHERE id=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            prepare.setLong(1, ts);
            prepare.setLong(2, this.id);
            prepare.executeUpdate();
            prepare.close();*/
            String query="SELECT t1.id_membre,t1.titre,t1.description,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp FROM "+this.table+" AS t1 WHERE t1.id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idMembre=result.getLong("id_membre");
            this.titre=result.getString("titre");
            this.description=result.getString("description");
            this.extensions=new String[5];
            this.extensions[0]=result.getString("extension1");
            this.extensions[1]=result.getString("extension2");
            this.extensions[2]=result.getString("extension3");
            this.extensions[3]=result.getString("extension4");
            this.extensions[4]=result.getString("extension5");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            Objet.closeConnection();
            this.membre=new Membre(this.idMembre);
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        }
    }

    public void enregDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session = request.getSession(true);
                this.con=Objet.getConnection2();
                String query="";
                switch(this.type) {
                    case 3:
                        query="INSERT INTO "+this.table+" (id_membre,travaux,titre,description,timestamp,last_timestamp) VALUES (?,?,?,?,?,?)";
                        break;
                    default:
                        query="INSERT INTO "+this.table+" (id_membre,titre,description,timestamp,last_timestamp) VALUES (?,?,?,?,?)";
                        break;
                }
                PreparedStatement prepare=this.con.prepareStatement(query);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                switch(this.type) {
                    case 3:
                        prepare.setLong(1, this.membre.getId());
                        prepare.setString(2, "");
                        prepare.setString(3, this.titre);
                        prepare.setString(4, this.description);
                        prepare.setLong(5, ts);
                        prepare.setLong(6, ts);
                        break;
                    default:
                        prepare.setLong(1, this.membre.getId());
                        prepare.setString(2, this.titre);
                        prepare.setString(3, this.description);
                        prepare.setLong(4, ts);
                        prepare.setLong(5, ts);
                        break;
                }
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idAnnonce FROM "+this.table+" WHERE id_membre=?";
                prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.membre.getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("idAnnonce");
                result.close();
                prepare.close();
                query="UPDATE table_test_listes SET type"+this.type+"=? WHERE id=?";
                prepare=this.con.prepareStatement(query);
                prepare.setInt(1, 1);
                prepare.setInt(2, 1);
                prepare.executeUpdate();
                prepare.close();
                session.setAttribute("idAnnonce", this.id);
                session.setAttribute("type", this.type);
                session.setAttribute("captcha", null);
                switch(this.type) {
                    case 1:
                        this.uri="petite-annonce-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                    case 2:
                        this.uri="petite-annonce-auto-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                    case 3:
                        this.uri="petite-annonce-immobiliere-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                    case 4:
                        this.uri="petite-annonce-emploi-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                }
                Mail mail=new Mail(this.membre.getEmail(), this.membre.getPseudo(), "Annonce publiée !");
                mail.initMailAnnonce1(this.membre.getPseudo(), this.titre, this.uri, this.id, this.type);
                mail.send();
            } catch (NamingException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }
    public void initInfosEdit(long idAnnonce) {
        try {
            this.id = idAnnonce;
            Objet.getConnection();
            String query="SELECT titre,description,timestamp FROM "+table+" WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre=result.getString("titre");
            this.description=result.getString("description");
            this.timestamp=result.getLong("timestamp");
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.dateString="Dernières modification, le "+cal.get(Calendar.DATE)+"-"+((cal.get(Calendar.MONTH))+1)+"-"+cal.get(Calendar.YEAR)+".";
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        }
    }
    public void getPostsEdit(HttpServletRequest request) {
        this.titre=Objet.codeHTML(request.getParameter("titre"));
        this.description=Objet.codeHTML2(request.getParameter("description"));
        this.captcha=Objet.codeHTML(request.getParameter("captcha").toLowerCase());
    }

    public void verifPostsEdit(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            if (this.titre.length() == 0) {
                this.setErrorMsg("<div>Champ TITRE vide.</div>");
            } else if (this.titre.length() > 80) {
                this.setErrorMsg("<div>Champ TITRE trop long.</div>");
            }
            if (this.description.length() == 0) {
                this.setErrorMsg("<div>Champ DESCRIPTION vide.</div>");
            } else if (this.description.length() > 5000) {
                this.setErrorMsg("<div>Champ DESCRIPTION trop long.</div>");
            }
            if (this.captcha.length() == 0) {
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT vide.</div>");
            } else if (this.captcha.length() > 5) {
                this.setErrorMsg("<div>Champ CODE ANTI-ROBOT trop long.</div>");
            } else if (session.getAttribute("captcha") == null) {
                this.setErrorMsg("<div>Session CODE ANTI-ROBOT dépassée.</div>");
            } else if (!session.getAttribute("captcha").toString().equals(Objet.getEncoded(this.captcha))) {
                this.setErrorMsg("<div>Mauvais CODE ANTI-ROBOT.</div>");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void enregEdit(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session = request.getSession(true);
                this.setCon(Objet.getConnection2());
                String query="UPDATE "+table+" SET titre=?,description=?,last_timestamp=?,timestamp=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=this.getCon().prepareStatement(query);
                prepare.setString(1, this.titre);
                prepare.setString(2, this.description);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(3, ts);
                prepare.setLong(4, ts);
                prepare.setLong(5, this.id);
                prepare.setLong(6, this.membre.getId());
                prepare.executeUpdate();
                prepare.close();
                query="UPDATE table_test_listes SET type"+this.type+"=? WHERE id=?";
                prepare=this.con.prepareStatement(query);
                prepare.setInt(1, 1);
                prepare.setInt(2, 1);
                prepare.executeUpdate();
                prepare.close();
                this.uri="";
                switch(this.type) {
                    case 1:
                        this.uri="petite-annonce-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                    case 2:
                        this.uri="petite-annonce-auto-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                    case 3:
                        this.uri="petite-annonce-immobiliere-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                    case 4:
                        this.uri="petite-annonce-emploi-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
                        break;
                }
                Mail mail=new Mail(this.membre.getEmail(), this.membre.getPseudo(), "Annonce modifiée !");
                mail.initMailAnnonceModif1(this.membre.getPseudo(), this.titre, this.uri, this.id, this.type);
                mail.send();
                session.setAttribute("captcha", null);
                session.setAttribute("idAnnonce", this.id);
                session.setAttribute("type", this.type);
            } catch (NamingException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                    Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }

    public void initDonneesEmploi(long idAnnonce) {
        try {
            this.id=idAnnonce;
            this.con=Objet.getConnection2();
            String query="UPDATE table_annonces_emploi SET last_timestamp=? WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis();
            prepare.setLong(1, ts);
            prepare.setLong(2, this.id);
            prepare.executeUpdate();
            prepare.close();
            query="SELECT id_membre,titre,description,extension1,timestamp FROM table_annonces_emploi WHERE id=? LIMIT 0,1";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.idMembre=result.getLong("id_membre");
            this.titre=result.getString("titre");
            this.description=result.getString("description");
            String extension1=result.getString("extension1");
            this.timestamp=result.getLong("timestamp");
            result.close();
            prepare.close();
            this.uri="petite-annonce-emploi-"+this.id+"-"+Objet.encodeTitre(this.titre)+".html";
            this.tagTitle="Annonce emploi - "+this.titre;
            this.tagDescription="Megannonce - Petite annonce emploi - "+this.titre+".";
            this.extensions=new String[1];
            this.largeurs=new int[1];
            this.hauteurs=new int[1];
            if(extension1.length()>0) {
                String filename=Datas.DIR+"photos/4_"+this.id+"_1"+extension1;
                String filenameMini2=Datas.DIR+"photos/mini2_4_"+this.id+"_1"+extension1;
                File file=new File(filename);
                File fileMini2=new File(filenameMini2);
                if(file.exists()&&fileMini2.exists()) {
                    Img img=new Img();
                    img.getSize(fileMini2);
                    largeurs[0]=img.getWidth();
                    hauteurs[0]=img.getHeight();
                } else {
                    extension1="";
                }
            }
            this.extensions[0]=extension1;
            this.membre=new Membre(this.idMembre);
            cal.setTimeInMillis(this.timestamp);
            this.ligne1="<strong>Annonce emploi</strong> déposée le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+" par "+this.membre.getPseudo()+".";
            this.ligne2="Localisation de l'annonceur : "+this.membre.getCodePostal()+"-"+this.membre.getCommune()+"|"+this.membre.getRegion()+"-"+this.membre.getDepartement()+".";
            this.uriRetour="./petites-annonces-emploi-1.html";
        } catch (IOException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.id=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.id=0;
            }
        }
    }
    
    public void effaceAnnonce(long idAnnonce, int type, long idMembre) {
        try {
            this.con2=Objet.getConnection2();
            String tbl="";
            switch(type) {
                case 1:
                    tbl="table_annonces_normales";
                    break;
                case 2:
                    tbl="table_annonces_automobile";
                    break;
                case 3:
                    tbl="table_annonces_immobilier";
                    break;
                case 4:
                    tbl="table_annonces_emploi";
                    break;
            }
            String query="";
            PreparedStatement prepare=null;
            ResultSet result=null;
            switch(type) {
                case 1:
                case 2:
                case 3:
                    query="SELECT extension1,extension2,extension3,extension4,extension5 FROM "+tbl+" WHERE id=? AND id_membre=? LIMIT 0,1";
                    prepare=this.con2.prepareStatement(query);
                    prepare.setLong(1, idAnnonce);
                    prepare.setLong(2, idMembre);
                    result=prepare.executeQuery();
                    result.next();
                    String[] exts=new String[5];
                    exts[0]=result.getString("extension1");
                    exts[1]=result.getString("extension2");
                    exts[2]=result.getString("extension3");
                    exts[3]=result.getString("extension4");
                    exts[4]=result.getString("extension5");
                    result.close();
                    prepare.close();
                    for(int i=1; i<=5; i++) {
                        String ext=exts[i-1];
                        if(ext.length()>0) {
                            String filename=Datas.DIR+"photos/"+type+"_"+idAnnonce+"_"+i+ext;
                            String filenameMini1=Datas.DIR+"photos/mini1_"+type+"_"+idAnnonce+"_"+i+ext;
                            String filenameMini2=Datas.DIR+"photos/mini2_"+type+"_"+idAnnonce+"_"+i+ext;
                            File file=new File(filename);
                            File fileMini1=new File(filenameMini1);
                            File fileMini2=new File(filenameMini2);
                            if(file.exists())
                                file.delete();
                            if(fileMini1.exists())
                                fileMini1.delete();
                            if(fileMini2.exists())
                                fileMini2.delete();
                        }
                    }
                    break;
                case 4:
                    query="SELECT extension1 FROM table_annonces_emploi WHERE id=? AND id_membre=? LIMIT 0,1";
                    prepare=this.con2.prepareStatement(query);
                    prepare.setLong(1, idAnnonce);
                    prepare.setLong(2, idMembre);
                    result=prepare.executeQuery();
                    result.next();
                    String ext=result.getString("extension1");
                    if(ext.length()>0) {
                        String filename=Datas.DIR+"photos/4_"+idAnnonce+"_1"+ext;
                        String filenameMini1=Datas.DIR+"photos/mini1_4_"+idAnnonce+"_1"+ext;
                        String filenameMini2=Datas.DIR+"photos/mini2_4_"+idAnnonce+"_1"+ext;
                        File file=new File(filename);
                        File fileMini1=new File(filenameMini1);
                        File fileMini2=new File(filenameMini2);
                        if(file.exists())
                            file.delete();
                        if(fileMini1.exists())
                            fileMini1.delete();
                        if(fileMini2.exists())
                            fileMini2.delete();
                    }
                    break;
            }
            query="DELETE FROM table_messages WHERE id_annonce=? AND type=?";
            prepare=this.con2.prepareStatement(query);
            prepare.setLong(1, idAnnonce);
            prepare.setInt(2, type);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM "+tbl+" WHERE id=?";
            prepare=this.con2.prepareStatement(query);
            prepare.setLong(1, idAnnonce);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_abus WHERE id_annonce=?";
            prepare=this.con2.prepareStatement(query);
            prepare.setLong(1, idAnnonce);
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_test_listes SET type"+type+"=? WHERE id=?";
            prepare=this.con2.prepareStatement(query);
            prepare.setInt(1, 1);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con2);
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void effaceOlds() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT id,id_membre FROM "+this.table+" WHERE last_timestamp<?";
            Calendar cal=Calendar.getInstance();
            long ts=cal.getTimeInMillis()-(1000l*60l*60l*24l*30l*6l);
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, ts);
            ResultSet result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                long idM=result.getLong("id_membre");
                this.effaceAnnonce(idAnnonce, this.type, idM);
            }
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonce.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   @Override
    public void blank() {
        super.blank();
        this.membre=null;
            this.setTitre("");
            this.setDescription("");
            this.setCaptcha("");
    }

    /**
     * @return the membre
     */
    public Membre getMembre() {
        return membre;
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

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the captcha
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the extensions
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the tagTitle
     */
    public String getTagTitle() {
        return tagTitle;
    }

    /**
     * @return the tagDescription
     */
    public String getTagDescription() {
        return tagDescription;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param tagTitle the tagTitle to set
     */
    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    /**
     * @param tagDescription the tagDescription to set
     */
    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @return the dateString
     */
    public String getDateString() {
        return dateString;
    }

    /**
     * @return the largeurs
     */
    public int[] getLargeurs() {
        return largeurs;
    }

    /**
     * @return the hauteurs
     */
    public int[] getHauteurs() {
        return hauteurs;
    }

    /**
     * @return the ligne1
     */
    public String getLigne1() {
        return ligne1;
    }

    /**
     * @return the ligne2
     */
    public String getLigne2() {
        return ligne2;
    }

    /**
     * @return the uriRetour
     */
    public String getUriRetour() {
        return uriRetour;
    }

}
