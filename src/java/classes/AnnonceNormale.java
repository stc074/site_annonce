/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

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
public class AnnonceNormale extends Annonce {

    private int mode;
    private long idCategorie;
    private long idSousCategorie;
    private String prix;
    private String categorie;
    private String sousCategorie;

    public AnnonceNormale() {
        super();
    }

    public AnnonceNormale(Membre membre) {
        super(membre);
        this.mode=0;
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.prix="";
        this.setType(1);
    }

    public void getGets(HttpServletRequest request) {
        if(request.getParameter("mode")!=null)
            this.mode=Integer.parseInt(request.getParameter("mode"));
    }
    @Override
    public void getPostsDepot(HttpServletRequest request) {
        super.getPostsDepot(request);
        this.mode=Integer.parseInt(request.getParameter("mode"));
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
        this.prix=request.getParameter("prix");
        this.prix=this.prix.replaceAll("\\.", ",");
        this.prix=Objet.codeHTML(this.prix);
    }

    @Override
    public void verifPostsDepot(HttpServletRequest request) {
        super.verifPostsDepot(request);
            if (this.mode == 0) {
                this.setErrorMsg("<div>Veuillez choisir s'il s'agit d'une VENTE ou d'un ACHAT SVP.</div>");
            }
            if (this.idCategorie == 0) {
                this.setErrorMsg("<div>Veuillez choisir LA CATÉGORIE de votre annonce.</div>");
            }
            if (this.idSousCategorie == 0) {
                this.setErrorMsg("<div>Veuillez choisir la SOUS-CATÉGORIE de votre annonce SVP.</div>");
            }
            Pattern p=Pattern.compile("[0-9]{1,10}(,){0,1}[0-9]{0,9}");
            Matcher m=p.matcher(this.prix);
            if (this.prix.length() == 0) {
                this.setErrorMsg("<div>Champ PRIX vide.</div>");
            } else if (this.prix.length() > 10) {
                this.setErrorMsg("<div>Champ PRIX trop long.</div>");
            } else if(m.matches()==false)
                this.setErrorMsg("<div>Champ PRIX non-valide.</div>");
    }

    @Override
    public void enregDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.setTable("table_annonces_normales");
                this.setType(1);
                super.enregDepot(request);
                Objet.getConnection();
                String query="UPDATE table_annonces_normales SET mode=?,id_categorie=?,id_sous_categorie=?,prix=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.mode);
                prepare.setLong(2, this.idCategorie);
                prepare.setLong(3, this.idSousCategorie);
                prepare.setString(4, this.prix);
                prepare.setLong(5, this.getId());
                prepare.setLong(6, this.getMembre().getId());
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    @Override
    public void initDonnees(long idAnnonce) {
        try {
            this.setTable("table_annonces_normales");
            super.initDonnees(idAnnonce);
            Objet.getConnection();
            String query="SELECT t1.mode,t1.prix,t2.categorie,t3.sous_categorie FROM table_annonces_normales AS t1,table_categories AS t2,table_sous_categories AS t3 WHERE t1.id=? AND t2.id=t1.id_categorie AND t3.id=t1.id_sous_categorie LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.prix=result.getString("prix");
            this.categorie=result.getString("categorie");
            this.sousCategorie=result.getString("sous_categorie");
            result.close();
            prepare.close();
            this.setTagTitle("Petite annonce - "+this.getTitre());
            this.setTagDescription("Megannonce - Petite annonce - "+this.getTitre()+".");
            this.setUri("petite-annonce-"+this.getId()+"-"+Objet.encodeTitre(this.getTitre())+".html");
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }

    @Override
    public void initInfosEdit(long idAnnonce) {
        try {
            this.setTable("table_annonces_normales");
            this.setType(1);
            super.initInfosEdit(idAnnonce);
            Objet.getConnection();
            String query="SELECT mode,id_categorie,id_sous_categorie,prix FROM table_annonces_normales WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.getMembre().getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.idCategorie=result.getLong("id_categorie");
            this.idSousCategorie=result.getLong("id_sous_categorie");
            this.prix=result.getString("prix");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }

    @Override
    public void getPostsEdit(HttpServletRequest request) {
        super.getPostsEdit(request);
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
        this.prix=Objet.codeHTML(request.getParameter("prix"));
        this.prix=this.prix.replaceAll("\\.", ",");
    }

    @Override
    public void verifPostsEdit(HttpServletRequest request) {
        super.verifPostsEdit(request);
        if(this.idCategorie==0)
            this.setErrorMsg("<div>Choisissez la CATÉGORIE de votre annonce SVP.</div>");
        if(this.idSousCategorie==0)
            this.setErrorMsg("<div>Choisissez la SOUS-CATÉGORIE de votre annonce SVP.</div>");
        Pattern p=Pattern.compile("[0-9]{1,10},{0,1}[0-9]{1,9}");
        Matcher m=p.matcher(this.prix);
        if(this.prix.length()==0)
            this.setErrorMsg("<div>Champ PRIX vide.</div>");
        else if(this.prix.length()>10)
            this.setErrorMsg("<div>Champ PRIX trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ PRIX non-valide.</div>");
    }

    @Override
    public void enregEdit(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.setTable("table_annonces_normales");
                this.setType(1);
                super.enregEdit(request);
                Objet.getConnection();
                String query="UPDATE table_annonces_normales SET id_categorie=?,id_sous_categorie=?,prix=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setLong(2, this.idSousCategorie);
                prepare.setString(3, this.prix);
                prepare.setLong(4, this.getId());
                prepare.setLong(5, this.getMembre().getId());
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceNormale.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    @Override
    public void effaceOlds() {
        this.setType(1);
        this.setTable("table_annonces_normales");
        super.effaceOlds();
    }
    
    @Override
    public void blank() {
        super.blank();
        this.mode=0;
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.prix="";
    }
    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public long getIdSousCategorie() {
        return idSousCategorie;
    }

    /**
     * @return the prix
     */
    public String getPrix() {
        return prix;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

}
