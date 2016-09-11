/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import servlets.EditCategorieEmploi;

/**
 *
 * @author pj
 */
public class CategorieEmploi extends Objet {
    
    private String[] listeCategorie;
    private int id;
    private String categorie;
    
    public CategorieEmploi() {
        super();
        this.id=0;
    }
    
    public void initListe() {
        try {
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_categories_emploi";
            Statement state=Objet.getConn().createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            int nb=result.getInt("nb");
            result.close();
            state.close();
            this.listeCategorie=new String[nb];
            if(nb!=0) {
            query="SELECT categorie FROM table_categories_emploi ORDER BY categorie";
            state=Objet.getConn().createStatement();
            result=state.executeQuery(query);
            int i=0;
            while(result.next()) {
                String cat=result.getString("categorie");
                this.listeCategorie[i]=cat;
                i++;
            }
            result.close();
            state.close();
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(CategorieEmploi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategorieEmploi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPosts(HttpServletRequest request) {
        this.categorie=Objet.codeHTML(request.getParameter("categorie"));
    }

    public void verifPosts() {
        if(this.categorie.length()==0)
            this.setErrorMsg("<div>Champ NOUVELLE CATÉGORIE vide.</div>");
        else if(this.categorie.length()>100)
            this.setErrorMsg("<div>Champ NOUVELLE CATÉGORIE trop long.</div>");
        else {
            try {
                Objet.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_categories_emploi WHERE categorie=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.categorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                if(nb!=0)
                    this.setErrorMsg("<div>La CATÉGORIE \""+this.categorie+"\" existe déjà.</div>");
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(CategorieEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(CategorieEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    public void enregCategorie(EditCategorieEmploi ed) {
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="INSERT INTO table_categories_emploi (categorie) VALUES (?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.categorie);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
                ed.destroy();
           } catch (NamingException ex) {
                Logger.getLogger(CategorieEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(CategorieEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    /**
     * @return the listeCategorie
     */
    public String[] getListeCategorie() {
        return listeCategorie;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

}
