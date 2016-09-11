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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Membre extends Objet {

    private String pseudo;
    private String email;
    private String motDePasse;
    private String motDePasse2;
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private String captcha;
    private long id;
    private String pseudo2;
    private long timestamp;
    private boolean flagMdp;
    private String region;
    private String departement;
    private String commune;
    private String codePostal;
    private Connection con;
    
    public Membre() {
        super();
        this.pseudo="";
        this.email="";
        this.motDePasse="";
        this.motDePasse2="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.captcha="";
        this.flagMdp=false;
    }

    Membre(long idMembre) {
        try {
            this.id = idMembre;
            Objet.getConnection();
            String query="SELECT t1.pseudo,t2.region,t3.departement,t4.commune,t4.code_postal  FROM table_membres AS t1,table_regions AS t2,table_departements AS t3,table_communes AS t4 WHERE t1.id=? AND t2.id_region=t1.id_region AND t3.id_departement=t1.id_departement AND t4.id=t1.id_commune LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.pseudo=result.getString("pseudo");
            this.region=result.getString("region");
            this.departement=result.getString("departement");
            this.commune=result.getString("commune");
            this.codePostal=result.getString("code_postal");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        }
    }

    public void getPostsInscription(HttpServletRequest request) {
        this.pseudo=request.getParameter("pseudo");
        this.pseudo=Objet.codeHTML(this.pseudo);
        this.email=request.getParameter("email");
        this.email=Objet.codeHTML(this.email);
        this.motDePasse=request.getParameter("motDePasse");
        this.motDePasse=Objet.codeHTML(this.motDePasse);
        this.motDePasse2=request.getParameter("motDePasse2");
        this.motDePasse2=Objet.codeHTML(this.motDePasse2);
        this.idRegion=request.getParameter("idRegion");
        this.idRegion=Objet.codeHTML(this.idRegion);
        this.idDepartement=request.getParameter("idDepartement");
        this.idDepartement=Objet.codeHTML(this.idDepartement);
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
        this.captcha=request.getParameter("captcha").toLowerCase();
        this.captcha=Objet.codeHTML(this.captcha);
    }
    public void verifPostsInscription(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            Objet.getConnection();
            Pattern p = Pattern.compile("[a-zA-Z0-9]+");
            Matcher m = p.matcher(this.pseudo);
            if (this.pseudo.length() == 0) {
                this.setErrorMsg("Champ PSEUDONYME vide.<br/>");
            } else if (this.pseudo.length() < 2) {
                this.setErrorMsg("Champ PSEUDONYME trop court.<br/>");
            } else if (this.pseudo.length() > 20) {
                this.setErrorMsg("Champ PSEUDONYME trop long.<br/>");
            } else if (m.matches() == false) {
                this.setErrorMsg("Champ PSEUDONYME non-valide (Caractères alphanumériques uniquement).<br/>");
            } else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb!=0)
                    this.setErrorMsg("Désolé, ce PSEUDONYME est déjà pris.<br/>");
            }
            p=Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]+\\.[a-z]{2,4}$");
            m=p.matcher(this.email);
            if(this.email.length()==0)
                this.setErrorMsg("Champ ADRESSE EMAIL vide.<br/>");
            else if(this.email.length()>200)
                this.setErrorMsg("Champ ADRESSE EMAIL trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ ADRESSE EMAIL non-valide.<br/>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE email=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb!=0)
                    this.setErrorMsg("Désolé, cette ADRESSE EMAIL est déjà associée à un compte.<br/>");
            }
            p=Pattern.compile("[a-zA-Z0-9]+");
            m=p.matcher(this.motDePasse);
            Matcher m2=p.matcher(this.motDePasse2);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("Champ MOT DE PASSE vide.<br/>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("Champ MOT DE PASSE trop court.<br/>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("Champ MOT DE PASSE trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ MOT DE PASSE non-valide (Caractères alphanumériques uniquement) .<br/>");
            else if(this.motDePasse2.length() == 0)
                this.setErrorMsg("Champ CONFIRMATION vide.<br/>");
            else if(this.motDePasse2.length()<3)
                this.setErrorMsg("Champ CONFIRMATION trop court.<br/>");
            else if(this.motDePasse2.length()>15)
                this.setErrorMsg("Champ CONFIRMATION trop long.<br/>");
            else if(m2.matches()==false)
                this.setErrorMsg("Champ CONFIRMATION non-valide (Caractères alphanumériques uniquement) .<br/>");
            else if(!this.motDePasse.equals(this.motDePasse2))
                this.setErrorMsg("Vos 2 MOTS DE PASSE sont différents.<br/>");
            if(this.idRegion.equals("0"))
                this.setErrorMsg("Veuillez choisir votre RÉGION SVP.<br/>");
            if(this.idDepartement.equals("0"))
                this.setErrorMsg("Veuillez choisir votre DÉPARTEMENT SVP.<br/>");
            if(this.idCommune==0)
                this.setErrorMsg("Veuillez choisir votre COMMUNE SVP.<br/>");
            if(this.captcha.length()==0)
                this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
            else if(this.captcha.length()>5)
                this.setErrorMsg("Champ CODE ANTI-ROBOT trop long.<br/>");
            else if(session.getAttribute("captcha")==null)
                this.setErrorMsg("Session CODE ANTI-ROBOT dépassée.<br/>");
            else if(!session.getAttribute("captcha").toString().equals(Objet.getEncoded(this.captcha)))
                this.setErrorMsg("Mauvais CODE ANTI-ROBOT.<br/>");
            Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
        }
    }

    public void enregDatasInscription(HttpServletRequest request, HttpServletResponse response) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session = request.getSession(true);
                Objet.getConnection();
                String query="INSERT INTO table_membres (pseudo,email,mot_de_passe,id_region,id_departement,id_commune,timestamp) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, this.email);
                prepare.setString(3, Objet.getEncoded(this.motDePasse));
                prepare.setString(4, this.idRegion);
                prepare.setString(5, this.idDepartement);
                prepare.setInt(6, this.idCommune);
                Calendar cal=Calendar.getInstance();
                long ts=cal.getTimeInMillis();
                prepare.setLong(7, ts);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMembre FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("idMembre"));
                result.close();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
                session.setAttribute("captcha", null);
                Objet.setCookie(this.getId(), response);
                session.setAttribute("idMembre", this.getId());
                session.setAttribute("pseudo", this.pseudo);
                this.pseudo2=this.pseudo;
                Mail mail=new Mail(this.email, this.pseudo, "Inscription validée !");
                mail.initMailInscription(this.pseudo, this.email, this.motDePasse);
                mail.send();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"<br/>");
            }
        }
    }

    public void testConnecte(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.setId(0);
        this.pseudo2="";
        if(session.getAttribute("idMembre")!=null&&session.getAttribute("pseudo")!=null) {
            this.setId(Long.parseLong(session.getAttribute("idMembre").toString()));
            this.pseudo2=session.getAttribute("pseudo").toString();
            session.setAttribute("idMembre", this.getId());
            session.setAttribute("pseudo", this.getPseudo2());
        } else {
            Cookie cookies[]=request.getCookies();
            String cookieValue="";
            if(cookies!=null) {
                try {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("megannoncecook")) {
                            cookieValue = cookie.getValue();
                        }
                    }
                    Objet.getConnection();
                    String query="SELECT id,pseudo FROM table_membres WHERE cookie_code=? LIMIT 0,1";
                    PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                    prepare.setString(1, cookieValue);
                    ResultSet result=prepare.executeQuery();
                    boolean flag=result.next();
                    if(flag) {
                        this.setId(result.getLong("id"));
                        this.pseudo2=result.getString("pseudo");
                        session.setAttribute("idMembre", this.getId());
                        session.setAttribute("pseudo", this.getPseudo2());
                    }
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                } catch (NamingException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void getPostsCnx(HttpServletRequest request) {
        this.pseudo=request.getParameter("pseudo");
        this.pseudo=Objet.codeHTML(this.pseudo);
        this.motDePasse=request.getParameter("motDePasse");
        this.motDePasse=Objet.codeHTML(this.motDePasse);
    }

    public void verifPostsCnx(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session=request.getSession(true);
            Objet.getConnection();
            Pattern p = Pattern.compile("[a-zA-Z0-9]+");
            Matcher m = p.matcher(this.pseudo);
            if (this.pseudo.length() == 0) {
                this.setErrorMsg("Champ PSEUDO vide.<br/>");
            } else if (this.pseudo.length() < 2) {
                this.setErrorMsg("Champ PSEUDO trop court.<br/>");
            } else if (this.pseudo.length() > 20) {
                this.setErrorMsg("Champ PSEUDO trop long.<br/>");
            } else if (m.matches() == false) {
                this.setErrorMsg("Champ PSEUDO non-valide.<br/>");
            } else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("<div>PSEUDO inconnu.</div>");
                result.close();
                prepare.close();
            }
            m=p.matcher(this.motDePasse);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("<div>Champ MOT DE PASSE vide.</div>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop court.</div>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ MOT DE PASSE non-valide.</div>");
            else if(this.getErrorMsg().length()==0) {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("<div>Mauvais MOT DE PASSE.</div>");
                result.close();
                prepare.close();
            }
            if(this.getErrorMsg().length()==0) {
                String query="SELECT id FROM table_membres WHERE pseudo=? AND mot_de_passe=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("id"));
                result.close();
                prepare.close();
                session.setAttribute("idMembre", this.id);
                session.setAttribute("pseudo", this.pseudo);
                this.pseudo2=this.pseudo;
                Objet.setCookie(this.id, response);
            }
            Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void verifPostsCnx2() {
        try {
            Objet.getConnection();
            Pattern p = Pattern.compile("[a-zA-Z0-9]+");
            Matcher m=p.matcher(this.pseudo);
            if(this.pseudo.length()==0)
                this.setErrorMsg("<div>Champ PSEUDONYME vide.</div>");
            else if(this.pseudo.length()<2)
                this.setErrorMsg("<div>Champ PSEUDONYME trop court.</div>");
            else if(this.pseudo.length()>20)
                this.setErrorMsg("<div>Champ PSEUDONYME trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ PSEUDONYME non-valide (Caractères alphanumériques uniquement).</div>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("<div>Désolé, ce PSEUDONYME n'est pas enregistré chez nous.</div>");
                result.close();
                prepare.close();
            }
            m=p.matcher(this.motDePasse);
            Matcher m2=p.matcher(this.motDePasse2);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("<div>Champ MOT DE PASSE vide.</div>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop court.</div>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("<div>Champ MOT DE PASSE trop long.</div>");
            else if(m.matches()==false)
                this.setErrorMsg("<div>Champ MOT DE PASSE non-valide (Caractères alphanumériques uniquement).</div>");
            else if(this.getErrorMsg().length()==0) {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? AND mot_de_passe=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("<div>Mauvais MOT DE PASSE.</div>");
                result.close();
                prepare.close();
            }
            Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void getCnxDepot(HttpServletRequest request, HttpServletResponse response) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session = request.getSession(true);
                Objet.getConnection();
                String query="SELECT id FROM table_membres WHERE pseudo=? AND mot_de_passe=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, Objet.getEncoded(this.motDePasse));
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setId(result.getLong("id"));
                result.close();
                prepare.close();
                session.setAttribute("idMembre", this.id);
                this.pseudo2=this.pseudo;
                session.setAttribute("pseudo", this.pseudo2);
                Objet.setCookie(this.id, response);
                this.setTest(1);
                Objet.closeConnection();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void initInfos() throws NamingException, SQLException {
        Objet.getConnection();
        String query="SELECT pseudo,email FROM table_membres WHERE id=? LIMIT 0,1";
        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, this.id);
        ResultSet result=prepare.executeQuery();
        result.next();
        this.pseudo=result.getString("pseudo");
        this.email=result.getString("email");
        Objet.closeConnection();
    }
    public void initInfos2() {
        try {
            Objet.getConnection();
            String query = "SELECT pseudo,email,id_region,id_departement,id_commune,timestamp FROM table_membres WHERE id=? LIMIT 0,1";
            PreparedStatement prepare = Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result = prepare.executeQuery();
            result.next();
            this.pseudo = result.getString("pseudo");
            this.email = result.getString("email");
            this.idRegion=result.getString("id_region");
            this.idDepartement=result.getString("id_departement");
            this.idCommune=result.getInt("id_commune");
            this.timestamp=result.getLong("timestamp");
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.id=0;
        }
    }
    public void getPostsMdpOublie(HttpServletRequest request) {
        this.email=request.getParameter("email");
        this.email=Objet.codeHTML(this.email);
        this.captcha=request.getParameter("captcha").toLowerCase();
        this.captcha=Objet.codeHTML(this.captcha);
    }

    public void verifPostsMdpOublie(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            Pattern p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]+\\.[a-z]{2,4}$");
            Matcher m = p.matcher(this.email);
            if (this.email.length() == 0) {
                this.setErrorMsg("<div>Champ ADRESSE EMAIL vide.</div>");
            } else if (this.email.length() > 200) {
                this.setErrorMsg("<div>Champ ADRESSE EMAIL trop long.</div>");
            } else if (m.matches() == false) {
                this.setErrorMsg("<div>Champ ADRESSE EMAIL non-valide.</div>");
            } else {
                try {
                    Objet.getConnection();
                    String query = "SELECT COUNT(id) AS nb FROM table_membres WHERE email=?";
                    PreparedStatement prepare = Objet.getConn().prepareStatement(query);
                    prepare.setString(1, this.email);
                    ResultSet result = prepare.executeQuery();
                    result.next();
                    int nb = result.getInt("nb");
                    if (nb == 0) {
                        this.setErrorMsg("<div>Désolé, cette ADRESSE EMAIL nous est inconnue.</div>");
                    }
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                } catch (NamingException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : " + ex.getMessage() + "</div>");
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : " + ex.getMessage() + "</div>");
                }
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
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : " + ex.getMessage() + "</div>");
        }
    }

    public void enregMdpOublie(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                HttpSession session=request.getSession(true);
                Objet.getConnection();
                String query="SELECT id,pseudo FROM table_membres WHERE email=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.id=result.getLong("id");
                this.pseudo=result.getString("pseudo");
                result.close();
                prepare.close();
                this.motDePasse="";
                for(int i=0;i<5; i++)
                    this.motDePasse+=Datas.arrayChars[(int)(Math.random()*(double)(Datas.arrayChars.length-1))];
                query="UPDATE table_membres SET mot_de_passe=? WHERE id=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, Objet.getEncoded(this.motDePasse));
                prepare.setLong(2, this.id);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                session.setAttribute("captcha", null);
                Mail mail=new Mail(this.email, this.pseudo, "Nouveau mot de passe !");
                mail.initMailNouveauMdp(this.pseudo, this.email, this.motDePasse);
                mail.send();
                this.setTest(1);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : " + ex.getMessage() + "</div>");
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : " + ex.getMessage() + "</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : " + ex.getMessage() + "</div>");
            }
        }
    }

    public void getPostsEditInfos(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.motDePasse=request.getParameter("motDePasse");
        this.motDePasse=Objet.codeHTML(this.motDePasse);
        this.motDePasse2=request.getParameter("motDePasse2");
        this.motDePasse2=Objet.codeHTML(this.motDePasse2);
        this.idRegion=request.getParameter("idRegion");
        this.idRegion=Objet.codeHTML(this.idRegion);
        this.idDepartement=request.getParameter("idDepartement");
        this.idDepartement=Objet.codeHTML(this.idDepartement);
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
        this.captcha=request.getParameter("captcha").toLowerCase();
        this.captcha=Objet.codeHTML(this.captcha);
    }

    public void verifPostsEditInfos(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            if (this.motDePasse.length() > 0) {
                this.flagMdp = true;
                Pattern p = Pattern.compile("[a-zA-Z0-9]+");
                Matcher m = p.matcher(this.motDePasse);
                Matcher m2 = p.matcher(this.motDePasse2);
                if (this.motDePasse.length() < 3) {
                    this.setErrorMsg("<div>Champ MOT DE PASSE trop court.</div>");
                } else if (this.motDePasse.length() > 15) {
                    this.setErrorMsg("<div>Champ MOT DE PASSE trop long.</div>");
                } else if (m.matches() == false) {
                    this.setErrorMsg("<div>Champ MOT DE PASSE non-valide (Caractères alphanumériques uniquement.</div>");
                } else if (this.motDePasse2.length() == 0) {
                    this.setErrorMsg("<div>Champ CONFIRMATION vide.</div>");
                } else if (this.motDePasse2.length() < 3) {
                    this.setErrorMsg("<div>Champ CONFIRMATION trop court.</div>");
                } else if (this.motDePasse2.length() > 15) {
                    this.setErrorMsg("<div>Champ CONFIRMATION trop long.</div>");
                } else if (m2.matches() == false) {
                    this.setErrorMsg("<div>Champ CONFIRMATION non-valide (Caractères alphanumériques uniquement.</div>");
                } else if (!this.motDePasse.equals(this.motDePasse2)) {
                    this.setErrorMsg("<div>Vos 2 MOTS DE PASSE sont différents.</div>");
                }
            }
            if (this.idRegion.equals("0")) {
                this.setErrorMsg("<div>Veuillez choisir votre RÉGION SVP.</div>");
            }
            if (this.idDepartement.equals("0")) {
                this.setErrorMsg("<div>Veuillez choisir votre DÉPARTEMENT SVP.</div>");
            }
            if (this.idCommune == 0) {
                this.setErrorMsg("<div>Veuillez choisir votre COMMUNE SVP.</div>");
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
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    public void enregEditInfos(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
        try {
            HttpSession session = request.getSession(true);
            Objet.getConnection();
            if(this.flagMdp==true) {
                String query="UPDATE table_membres SET mot_de_passe=? WHERE id=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, Objet.getEncoded(this.motDePasse));
                prepare.setLong(2, this.id);
                prepare.executeUpdate();
                prepare.close();
                Mail mail=new Mail(this.email, this.pseudo, "Nouveau mot de passe !");
                mail.initMailNouveauMdp2(this.pseudo, this.email, this.motDePasse);
                mail.send();
            }
            String query="UPDATE table_membres SET id_region=?,id_departement=?,id_commune=?,timestamp=? WHERE id=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setString(1, this.idRegion);
            prepare.setString(2, this.idDepartement);
            prepare.setInt(3, this.idCommune);
            Calendar cal=Calendar.getInstance();
            this.timestamp=cal.getTimeInMillis();
            prepare.setLong(4, this.timestamp);
            prepare.setLong(5, this.id);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            this.setTest(1);
            session.setAttribute("captcha", null);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
        }
    }
    
    public void supprimerCompte(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session=request.getSession(true);
            this.con=Objet.getConnection2();
            Annonce annonce=new Annonce();
            String query="SELECT id FROM table_annonces_normales WHERE id_membre=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            ResultSet result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                annonce.effaceAnnonce(idAnnonce, 1, this.id);
            }
            result.close();
            prepare.close();
            query="SELECT id FROM table_annonces_automobile WHERE id_membre=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                annonce.effaceAnnonce(idAnnonce, 2, this.id);
            }
            result.close();
            prepare.close();
            query="SELECT id FROM table_annonces_immobilier WHERE id_membre=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                annonce.effaceAnnonce(idAnnonce, 3, this.id);
            }
            result.close();
            prepare.close();
            query="SELECT id FROM table_annonces_emploi WHERE id_membre=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            result=prepare.executeQuery();
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                annonce.effaceAnnonce(idAnnonce, 4, this.id);
            }
            result.close();
            prepare.close();
            query="DELETE FROM table_messages WHERE id_membre_expediteur=? OR id_membre_destinataire=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.setLong(2, this.id);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_membres WHERE id=?";
            prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.id);
            prepare.executeUpdate();
            prepare.close();
            session.setAttribute("idMembre", null);
            session.setAttribute("pseudo", null);
            Cookie cookie=new Cookie("megannoncecook", "");
            response.addCookie(cookie);
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    @Override
    public void blank() {
        super.blank();
        this.pseudo="";
        this.email="";
        this.motDePasse="";
        this.motDePasse2="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.captcha="";
        this.flagMdp=false;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the idRegion
     */
    public String getIdRegion() {
        return idRegion;
    }

    /**
     * @return the idDepartement
     */
    public String getIdDepartement() {
        return idDepartement;
    }

    /**
     * @return the idCommune
     */
    public int getIdCommune() {
        return idCommune;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the pseudo2
     */
    public String getPseudo2() {
        return pseudo2;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * @return the commune
     */
    public String getCommune() {
        return commune;
    }

    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

}
