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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class Categorie extends Objet {
    private long idCategorie;
    private String categorie;
    private long idSousCategorie;
    private String sousCategorie;

    public Categorie() {
        this.idCategorie=0;
        this.categorie="";
        this.idSousCategorie=0;
        this.sousCategorie="";
    }

    public void getPostsCat(HttpServletRequest request) {
        this.categorie=request.getParameter("categorie");
        this.categorie=Objet.codeHTML(this.categorie);
    }

    public void verifPostsCat() {
        if(this.categorie.length()==0)
            this.setErrorMsg("<div>Champ CATÉGORIE vide.</div>");
        else if(this.categorie.length()>100)
            this.setErrorMsg("<div>Champ CATÉGORIE trop long.</div>");
        else {
            try {
                Objet.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_categories WHERE categorie=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.categorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb!=0)
                    this.setErrorMsg("<div>Cette CATÉGORIE est déjà enregistrée.</div>");
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void enregCat() {
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="INSERT INTO table_categories (categorie) VALUES (?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.categorie);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void getGetsSousCat(HttpServletRequest request) {
        if(request.getParameter("idCategorie")!=null)
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
    }

    public void initCategorie() {
        if(this.idCategorie!=0) {
            try {
                Objet.getConnection();
                String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.categorie=result.getString("categorie");
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getPostsSousCat(HttpServletRequest request) {
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.sousCategorie=request.getParameter("sousCategorie");
        this.sousCategorie=Objet.codeHTML(this.sousCategorie);
    }

    public void verifPostsSousCat() {
        if(this.idCategorie==0)
            this.setErrorMsg("<div>Choisissez une CATÉGORIE SVP.</div>");
        if(this.sousCategorie.length()==0)
            this.setErrorMsg("<div>Champ SOUS-CATÉGORIE vide.</div>");
        else if(this.sousCategorie.length()>100)
            this.setErrorMsg("<div>Champ SOUS-CATÉGORIE trop long.</div>");
        else {
            try {
                Objet.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=? AND sous_categorie=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb!=0)
                    this.setErrorMsg("<div>Désolé, la SOUS-CATÉGORIE \""+this.sousCategorie+"\" existe déjà pour cette catégorie.</div>");
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void enregSousCat() {
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="INSERT INTO table_sous_categories (id_categorie,sous_categorie) VALUES (?,?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(Categorie.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    @Override
    public void blank() {
        super.blank();
        this.idCategorie=0;
        this.categorie="";
    }
    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
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
