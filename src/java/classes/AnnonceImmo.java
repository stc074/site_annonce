/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class AnnonceImmo extends Annonce {

    private int mode;
    private int typeBien;
    private String typeBienString;
    private int typeAppartement;
    private String typeAppartementString;
    private String surface;
    private int meuble;
    private String nbPieces;
    private String travaux;
    private String nbEtages;
    private int viabilise;
    private int constructible;
    private String prix;
    private String loyer;
    private String charges;
    private Connection con;
    private String meuble1Check;
    private String meuble2Check;
    private String viabilise1Check;
    private String viabilise2Check;
    private String constructible1Check;
    private String constructible2Check;
    private String label1="";
    
    public AnnonceImmo(Membre membre) {
        super(membre);
        this.mode=0;
        this.typeBien=0;
        this.typeAppartement=0;
        this.surface="";
        this.meuble=0;
        this.nbPieces="";
        this.travaux="";
        this.nbEtages="";
        this.viabilise=0;
        this.constructible=0;
        this.prix="";
        this.loyer="";
        this.charges="";
        this.typeBienString="";
        this.typeAppartementString="";
        this.meuble1Check="";
        this.meuble2Check="";
        this.viabilise1Check="";
        this.viabilise2Check="";
        this.constructible1Check="";
        this.constructible2Check="";
        this.label1="";
        this.setType(3);
    }

    public AnnonceImmo(Long idAnnonce) {
        super(idAnnonce);
    }


    @Override
    public void getPostsDepot(HttpServletRequest request) {
        super.getPostsDepot(request);
        this.mode=Integer.parseInt(request.getParameter("mode"));
        this.typeBien=Integer.parseInt(request.getParameter("typeBien"));
        switch(this.typeBien) {
            case 1:
                this.typeAppartement=Integer.parseInt(request.getParameter("typeAppartement"));
                break;
        }
        this.surface=Objet.codeHTML(request.getParameter("surface"));
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
                if(request.getParameter("meuble")!=null)
                    this.meuble=Integer.parseInt(request.getParameter("meuble"));
                break;
        }
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                this.nbPieces=Objet.codeHTML(request.getParameter("nbPieces"));
                break;
        }
        switch(this.typeBien) {
            case 4:
            case 5:
                this.travaux=Objet.codeHTML2(request.getParameter("travaux"));
                break;
            case 6:
                this.nbEtages=Objet.codeHTML(request.getParameter("nbEtages"));
                break;
            case 7:
                if(request.getParameter("viabilise")!=null)
                    this.viabilise=Integer.parseInt(request.getParameter("viabilise"));
                if(request.getParameter("constructible")!=null)
                    this.constructible=Integer.parseInt(request.getParameter("constructible"));
                break;
        }
        switch(this.mode) {
            case 1:
            case 4:
                this.prix=Objet.codeHTML(request.getParameter("prix"));
                break;
            case 2:
                this.loyer=Objet.codeHTML(request.getParameter("loyer"));
                this.charges=Objet.codeHTML(request.getParameter("charges"));
                break;
            case 3:
                this.loyer=Objet.codeHTML(request.getParameter("loyer"));
                break;
        }
    }

    public void getGets(HttpServletRequest request) {
        if(request.getParameter("mode")!=null)
            this.mode=Integer.parseInt(request.getParameter("mode"));
        if(request.getParameter("typeBien")!=null)
            this.typeBien=Integer.parseInt(request.getParameter("typeBien"));
    }

    @Override
    public void verifPostsDepot(HttpServletRequest request) {
        super.verifPostsDepot(request);
        if(this.mode==0)
            this.setErrorMsg("<div>Veuillez choisir le TYPE DE TRANSACTION SVP.</div>");
        if(this.typeBien==0)
            this.setErrorMsg("<div>Veuillez choisir le TYPE DE BIEN SVP.</div>");
        switch(this.typeBien) {
            case 1:
                if(this.typeAppartement==0)
                    this.setErrorMsg("<div>Veuillez choisisr le TYPE D'APPARTEMENT SVP.</div>");
                break;
        }
        Pattern p=Pattern.compile("[0-9]{1,10}");
        Matcher m=p.matcher(this.surface);
        if(this.surface.length()==0)
            this.setErrorMsg("<div>Champ SURFACE DU BIEN vide.</div>");
        else if(this.surface.length()>10)
            this.setErrorMsg("<div>Champ SURFACE DU BIEN trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ SURFACE DU BIEN non-valide.</div>");
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
                if(this.meuble==0)
                    this.setErrorMsg("<div>Veuillez spécifier s'il s'agit d'un BIEN MEUBLÉ ou non SVP.</div>");
                break;
        }
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                p=Pattern.compile("[0-9]{1,10}");
                m=p.matcher(this.nbPieces);
                if(this.nbPieces.length()==0)
                    this.setErrorMsg("<div>Champ NOMBRE DE PIÈCES DU BIEN vide.</div>");
                else if(this.nbPieces.length()>10)
                    this.setErrorMsg("<div>Champ NOMBRE DE PIÈCES DU BIEN trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ NOMBRE DE PIÈCE DU BIEN non-valide.</div>");
                break;
        }
        switch(this.typeBien) {
            case 4:
            case 5:
                if(this.travaux.length()==0)
                    this.setErrorMsg("<div>Champ TRAVAUX À RÉALISER vide.</div>");
                else if(this.travaux.length()>5000)
                    this.setErrorMsg("<div>Champ TRAVAUX À RÉALISER trop long.</div>");
                break;
            case 6:
                p=Pattern.compile("[0-9]{1,10}");
                m=p.matcher(this.nbEtages);
                if(this.nbEtages.length()==0)
                    this.setErrorMsg("<div>Champ NOMBRE D'ÉTAGES vide.</div>");
                else if(this.nbEtages.length()>10)
                    this.setErrorMsg("<div>Champ NOMBRE D'ÉTAGES trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ NOMBRE D'ÉTAGES non-valide.</div>");
                break;
            case 7:
                if(this.viabilise==0)
                    this.setErrorMsg("<div>Spécifiez si le terrain est VIABILISÉ SVP.</div>");
                if(this.constructible==0)
                    this.setErrorMsg("<div>Spécifiez si le terrrain est CONSTRUCTIBLE SVP.</div>");
                break;
        }
        p=Pattern.compile("[0-9]{1,10}");
        switch(this.mode) {
            case 1:
            case 4:
                m=p.matcher(this.prix);
                if(this.prix.length()==0)
                    this.setErrorMsg("<div>Champ PRIX vide.</div>");
                else if(this.prix.length()>10)
                    this.setErrorMsg("<div>Champ PRIX trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ PRIX non-valide.</div>");
                break;
            case 2:
                m=p.matcher(this.loyer);
                Matcher m2=p.matcher(this.charges);
                if(this.loyer.length()==0)
                    this.setErrorMsg("<div>Champ LOYER vide.</div>");
                else if(this.loyer.length()>10)
                    this.setErrorMsg("<div>Champ LOYER trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ LOYER non-valide.</div>");
                if(this.charges.length()==0)
                    this.setErrorMsg("<div>Champ CHARGES vide.</div>");
                else if(this.charges.length()>10)
                    this.setErrorMsg("<div>Champ CHARGES trop long.</div>");
                else if(m2.matches()==false)
                    this.setErrorMsg("<div>Champ CHARGES non-valide.</div>");
                break;
            case 3:
                m=p.matcher(this.loyer);
                if(this.loyer.length()==0)
                    this.setErrorMsg("<div>Champ LOYER À LA SEMAINE vide.</div>");
                else if(this.loyer.length()>10)
                    this.setErrorMsg("<div>Champ LOYER À LA SEMAINE trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ LOYER À LA SEMAINE non-valide.</div>");
                break;
        }
    }

    @Override
    public void enregDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.setType(3);
                this.setTable("table_annonces_immobilier");
                super.enregDepot(request);
                Objet.getConnection();
                String query="UPDATE table_annonces_immobilier SET mode=?,type_bien=?,type_appartement=?,surface=?,meuble=?,nb_pieces=?,travaux=?,nb_etages=?,viabilise=?,constructible=?,prix=?,loyer=?,charges=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.mode);
                prepare.setInt(2, this.typeBien);
                prepare.setInt(3, this.typeAppartement);
                prepare.setString(4, this.surface);
                prepare.setInt(5, this.meuble);
                prepare.setString(6, this.nbPieces);
                prepare.setString(7, this.travaux);
                prepare.setString(8, this.nbEtages);
                prepare.setInt(9, this.viabilise);
                prepare.setInt(10, this.constructible);
                prepare.setString(11, this.prix);
                prepare.setString(12, this.loyer);
                prepare.setString(13, this.charges);
                prepare.setLong(14, this.getId());
                prepare.setLong(15, this.getMembre().getId());
                this.setErrorMsg(prepare.toString()+"<br/>");
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    @Override
    public void initDonnees(long idAnnonce) {
        try {
            this.setTable("table_annonces_immobilier");
            super.initDonnees(idAnnonce);
            if(this.getId()!=0) {
            Objet.getConnection();
            String query="SELECT mode,type_bien,type_appartement,surface,meuble,nb_pieces,travaux,nb_etages,viabilise,constructible,prix,loyer,charges FROM table_annonces_immobilier WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.getMembre().getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.typeBien=result.getInt("type_bien");
            this.typeAppartement=result.getInt("type_appartement");
            this.surface=result.getString("surface");
            this.meuble=result.getInt("meuble");
            this.nbPieces=result.getString("nb_pieces");
            this.travaux=result.getString("travaux");
            this.nbEtages=result.getString("nb_etages");
            this.viabilise=result.getInt("viabilise");
            this.constructible=result.getInt("constructible");
            this.prix=result.getString("prix");
            this.loyer=result.getString("loyer");
            this.charges=result.getString("charges");
            result.close();
            prepare.close();
            Objet.closeConnection();
            this.setUri("./petite-annonce-immobiliere-"+this.getId()+"-"+Objet.encodeTitre(this.getTitre())+".html");
            this.setTagTitle("Annonce immobilière - "+this.getTitre());
            this.setTagDescription("Megannonces - petite annonce immobilière - "+this.getTitre()+".");
            }
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }
    
    @Override
    public void initInfosEdit(long idAnnonce) {
        try {
            this.setTable("table_annonces_immobilier");
            super.initInfosEdit(idAnnonce);
            this.con=Objet.getConnection2();
            String query="SELECT mode,type_bien,type_appartement,surface,meuble,nb_pieces,travaux,nb_etages,viabilise,constructible,prix,loyer,charges FROM table_annonces_immobilier WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.getMembre().getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.typeBien=result.getInt("type_bien");
            this.typeAppartement=result.getInt("type_appartement");
            this.surface=result.getString("surface");
            this.meuble=result.getInt("meuble");
            this.nbPieces=result.getString("nb_pieces");
            this.travaux=result.getString("travaux");
            this.nbEtages=result.getString("nb_etages");
            this.viabilise=result.getInt("viabilise");
            this.constructible=result.getInt("constructible");
            this.prix=result.getString("prix");
            this.loyer=result.getString("loyer");
            this.charges=result.getString("charges");
            this.typeBienString=Datas.arrayTypesBien[this.typeBien-1];
            switch(this.mode) {
                case 1:
                    this.label1=" à vendre";
                    break;
                case 2:
                case 3:
                    this.label1=" à louer";
                    break;
                case 4:
                    this.label1=" à acheter";
                    break;
            }
            if(this.meuble==1)
                this.meuble1Check=" checked=\"checked\"";
            else if(this.meuble==2)
                this.meuble2Check=" checked=\"checked\"";
            switch(this.typeBien) {
                case 1:
                    this.typeAppartementString=Datas.arrayTypesAppart[this.typeAppartement-1];
                    break;
            }
            switch(this.typeBien) {
                case 7:
                    switch(this.viabilise) {
                        case 1:
                            this.viabilise1Check=" checked=\"checked\"";
                            break;
                        case 2:
                            this.viabilise2Check=" checked=\"checked\"";
                            break;
                    }
                    switch(this.constructible) {
                        case 1:
                            this.constructible1Check=" checked=\"checked\"";
                            break;
                        case 2:
                            this.constructible2Check=" checked=\"checked\"";
                            break;
                    }
                    break;
            }
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } finally  {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }
    
    @Override
    public void getPostsEdit(HttpServletRequest request) {
        super.getPostsEdit(request);
        this.surface=Objet.codeHTML(request.getParameter("surface"));
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
                this.meuble=Integer.parseInt(request.getParameter("meuble"));
                switch(this.meuble) {
                    case 1:
                        this.meuble1Check=" checked=\"checked\"";
                        this.meuble2Check="";
                        break;
                    case 2:
                        this.meuble1Check="";
                        this.meuble2Check=" checked=\"checked\"";
                        break;
                }
                break;
        }
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                this.nbPieces=Objet.codeHTML(request.getParameter("nbPieces"));
                break;
        }
        switch(this.typeBien) {
            case 4:
            case 5:
                this.travaux=Objet.codeHTML2(request.getParameter("travaux"));
                break;
            case 6:
                this.nbEtages=Objet.codeHTML(request.getParameter("nbEtages"));
                break;
            case 7:
                this.viabilise=Integer.parseInt(request.getParameter("viabilise"));
                this.constructible=Integer.parseInt(request.getParameter("constructible"));
                switch(this.viabilise) {
                    case 1:
                        this.viabilise1Check=" checked=\"checked\"";
                        this.viabilise2Check="";
                        break;
                    case 2:
                        this.viabilise1Check="";
                        this.viabilise2Check=" checked=\"checked\"";
                        break;
                }
                break;
        }
        switch(this.mode) {
            case 1:
            case 4:
                this.prix=Objet.codeHTML(request.getParameter("prix"));
                break;
            case 2:
                this.loyer=Objet.codeHTML(request.getParameter("loyer"));
                this.charges=Objet.codeHTML(request.getParameter("charges"));
                break;
            case 3:
                this.loyer=Objet.codeHTML(request.getParameter("loyer"));
                break;
        }
    }

    @Override
    public void verifPostsEdit(HttpServletRequest request) {
        super.verifPostsEdit(request);
        Pattern p=Pattern.compile("[0-9]{1,10}");
        Matcher m=p.matcher(this.surface);
        if(this.surface.length()==0)
            this.setErrorMsg("<div>Champ SURFACE vide.</div>");
        else if(this.surface.length()>10)
            this.setErrorMsg("<div>Champ SURFACE trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ SURFACE non-valide.</div>");
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
                if(!(this.meuble==1||this.meuble==2))
                    this.setErrorMsg("<div>Veuillez spécifier s'il s'agit d'un bien MEUBLÉ SVP.</div>");
                break;
        }
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                m=p.matcher(this.nbPieces);
                if(this.nbPieces.length()==0)
                    this.setErrorMsg("<div>Champ NOMBRE DE PIÈCES vide.</div>");
                else if(this.nbPieces.length()>10)
                    this.setErrorMsg("<div>Champ NOMBRE DE PIÈCES trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ NOMBRE DE PIÈCES non-valide.</div>");
                break;
        }
        switch(this.typeBien) {
            case 4:
            case 5:
                if(this.travaux.length()==0)
                    this.setErrorMsg("<div>Champ TRAVAUX vide.</div>");
                else if(this.travaux.length()>5000)
                    this.setErrorMsg("<div>Champ TRAVAUX trop long.</div>");
                break;
            case 6:
                m=p.matcher(this.nbEtages);
                if(this.nbEtages.length()==0)
                    this.setErrorMsg("<div>Champ NOMBRE D'ÉTAGES vide.</div>");
                else if(this.nbEtages.length()>10)
                    this.setErrorMsg("<div>Champ NOMBRE D'ÉTAGES trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ NOMBRE D'ÉTAGES non-valide.</div>");
                break;
            case 7:
                if(!(this.viabilise==1||this.viabilise==2))
                    this.setErrorMsg("<div>Veuillez spécifier s'il s'agit d'un TERRAIN VIAIBILISÉ SVP.</div>");
                if(!(this.constructible==1||this.constructible==2))
                    this.setErrorMsg("<div>Veuillez spécifier s'il s'agit d'un TERRAIN CONSTRUCTIBLE SVP.</div>");
                break;
        }
        switch(this.mode) {
            case 1:
            case 4:
                m=p.matcher(this.prix);
                if(this.prix.length()==0)
                    this.setErrorMsg("<div>Champ PRIX vide.</div>");
                else if(this.prix.length()>10)
                    this.setErrorMsg("<div>Champ PRIX trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ PRIX non-valide.</div>");
                break;
            case 2:
                m=p.matcher(this.loyer);
                if(this.loyer.length()==0)
                    this.setErrorMsg("<div>Champ LOYER vide.</div>");
                else if(this.loyer.length()>10)
                    this.setErrorMsg("<div>Champ LOYER trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ LOYER non-valide.</div>");
                m=p.matcher(this.charges);
                if(this.charges.length()==0)
                    this.setErrorMsg("<div>Champ CHARGES vide.</div>");
                else if(this.charges.length()>10)
                    this.setErrorMsg("<div>Champ CHARGES trop long.</div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ CHARGES non-valide.</div>");
                break;
            case 3:
                m=p.matcher(this.loyer);
                if(this.loyer.length()==0)
                    this.setErrorMsg("<div>Champ LOYER vide.</div>");
                else if(this.loyer.length()>10)
                    this.setErrorMsg("<div>Champ LOYER trop long.>/div>");
                else if(m.matches()==false)
                    this.setErrorMsg("<div>Champ LOYER non-valide.</div>");
                break;
        }
    }
    
    @Override
    public void enregEdit(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
        try {
            this.setType(3);
            this.setTable("table_annonces_immobilier");
            super.enregEdit(request);
            this.con=Objet.getConnection2();
            String query="UPDATE table_annonces_immobilier SET surface=?,meuble=?,nb_pieces=?,travaux=?,nb_etages=?,viabilise=?,constructible=?,prix=?,loyer=?,charges=? WHERE id=? AND id_membre=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setString(1, this.surface);
            prepare.setInt(2, this.meuble);
            prepare.setString(3, this.nbPieces);
            prepare.setString(4, this.travaux);
            prepare.setString(5, this.nbEtages);
            prepare.setInt(6, this.viabilise);
            prepare.setInt(7, this.constructible);
            prepare.setString(8, this.prix);
            prepare.setString(9, this.loyer);
            prepare.setString(10, this.charges);
            prepare.setLong(11, this.getId());
            prepare.setLong(12, this.getMembre().getId());
            prepare.executeUpdate();
            prepare.close();
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(AnnonceImmo.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        }
    }
    
    @Override
    public void effaceOlds() {
        this.setType(3);
        this.setTable("table_annonces_immobilier");
        super.effaceOlds();
    }
    
    @Override
    public void blank() {
        super.blank();
        this.mode=0;
        this.typeBien=0;
        this.typeAppartement=0;
        this.surface="";
        this.meuble=0;
        this.nbPieces="";
        this.travaux="";
        this.nbEtages="";
        this.viabilise=0;
        this.constructible=0;
        this.prix="";
        this.loyer="";
        this.charges="";
        this.meuble1Check="";
        this.meuble2Check="";
        this.viabilise1Check="";
        this.viabilise2Check="";
        this.constructible1Check="";
        this.constructible2Check="";
    }
    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @return the typeBien
     */
    public int getTypeBien() {
        return typeBien;
    }

    /**
     * @return the typeAppartement
     */
    public int getTypeAppartement() {
        return typeAppartement;
    }

    /**
     * @return the surface
     */
    public String getSurface() {
        return surface;
    }

    /**
     * @return the meuble
     */
    public int getMeuble() {
        return meuble;
    }

    /**
     * @return the nbPieces
     */
    public String getNbPieces() {
        return nbPieces;
    }

    /**
     * @return the travaux
     */
    public String getTravaux() {
        return travaux;
    }

    /**
     * @return the nbEtages
     */
    public String getNbEtages() {
        return nbEtages;
    }

    /**
     * @return the viabilise
     */
    public int getViabilise() {
        return viabilise;
    }

    /**
     * @return the constructible
     */
    public int getConstructible() {
        return constructible;
    }

    /**
     * @return the prix
     */
    public String getPrix() {
        return prix;
    }

    /**
     * @return the loyer
     */
    public String getLoyer() {
        return loyer;
    }

    /**
     * @return the charges
     */
    public String getCharges() {
        return charges;
    }

    /**
     * @return the typeBienString
     */
    public String getTypeBienString() {
        return typeBienString;
    }

    /**
     * @return the typeAppartementString
     */
    public String getTypeAppartementString() {
        return typeAppartementString;
    }

    /**
     * @return the meuble1Check
     */
    public String getMeuble1Check() {
        return meuble1Check;
    }

    /**
     * @return the meuble2Check
     */
    public String getMeuble2Check() {
        return meuble2Check;
    }

    /**
     * @return the viabilise1Check
     */
    public String getViabilise1Check() {
        return viabilise1Check;
    }

    /**
     * @return the viabilise2Check
     */
    public String getViabilise2Check() {
        return viabilise2Check;
    }

    /**
     * @return the constructible1Check
     */
    public String getConstructible1Check() {
        return constructible1Check;
    }

    /**
     * @return the constructible2Check
     */
    public String getConstructible2Check() {
        return constructible2Check;
    }

    /**
     * @return the label1
     */
    public String getLabel1() {
        return label1;
    }

}
