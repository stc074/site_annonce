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
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class RechercheNormale extends Recherche {

    private int mode;
    private long idCategorie;
    private long idSousCategorie;

    public RechercheNormale() {
        super();
        this.mode=0;
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.setTagTitle("Megannonce - Petites annonces gratuites");
        this.setTagDescription("Toutes vos petites annonces gratuites sont sur Megannonce.");
    }

    @Override
    public void reset(HttpServletRequest request) {
        super.reset(request);
        HttpSession session=request.getSession(true);
        session.setAttribute("mode", null);
        session.setAttribute("idCategorie", null);
        session.setAttribute("idSousCategorie", null);
    }
    @Override
    public void initValues(HttpServletRequest request) {
        super.initValues(request);
        HttpSession session=request.getSession(true);
        this.mode=0;
        if(session.getAttribute("mode")!=null)
            this.mode=Integer.parseInt(session.getAttribute("mode").toString());
        this.idCategorie=0;
        if(session.getAttribute("idCategorie")!=null)
            this.idCategorie=Long.parseLong(session.getAttribute("idCategorie").toString());
        this.idSousCategorie=0;
        if(session.getAttribute("idSousCategorie")!=null)
            this.idSousCategorie=Long.parseLong(session.getAttribute("idSousCategorie").toString());
    }

    @Override
    public void getGets(HttpServletRequest request) {
        super.getGets(request);
        HttpSession session=request.getSession(true);
        if(request.getParameter("mode")!=null) {
            this.mode=Integer.parseInt(request.getParameter("mode"));
            if(this.mode!=0)
                session.setAttribute("mode", this.mode);
            else
                session.setAttribute("mode", null);
            this.setPage(0);
            session.setAttribute("page", null);
        }
        if(request.getParameter("idCategorie")!=null) {
            this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
            if(this.idCategorie!=0)
                session.setAttribute("idCategorie", this.idCategorie);
            else
                session.setAttribute("idCategorie", null);
            this.idSousCategorie=0;
            session.setAttribute("idSousCategorie", null);
            this.setPage(0);
            session.setAttribute("page", null);
        }
        if(request.getParameter("idSousCategorie")!=null) {
            this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
            if(this.idSousCategorie!=0)
                session.setAttribute("idSousCategorie", this.idSousCategorie);
            else
                session.setAttribute("idSousCategorie", null);
            this.setPage(0);
            session.setAttribute("page", null);
        }
    }

    @Override
    public void getPosts(HttpServletRequest request) {
        super.getPosts(request);
        if(request.getParameter("mode")!=null) {
            this.mode=Integer.parseInt(request.getParameter("mode"));
        }
        this.idCategorie=Long.parseLong(request.getParameter("idCategorie"));
        this.idSousCategorie=Long.parseLong(request.getParameter("idSousCategorie"));
    }

    @Override
    public void verifPosts(HttpServletRequest request) {
        super.verifPosts(request);
        HttpSession session=request.getSession(true);
        if(this.mode!=0)
            session.setAttribute("mode", this.mode);
        else
            session.setAttribute("mode", null);
        if(this.idCategorie!=0)
            session.setAttribute("idCategorie", this.idCategorie);
        else
            session.setAttribute("idCategorie", null);
        if(this.idSousCategorie!=0)
            session.setAttribute("idSousCategorie", this.idSousCategorie);
        else
            session.setAttribute("idSousCategorie", null);
    }
    @Override
    public void initCondition() {
        super.initCondition();
        if(this.mode!=0)
            this.setCondition(" AND t1.mode='"+this.mode+"'");
        if(this.idSousCategorie!=0)
            this.setCondition(" AND t1.id_sous_categorie='"+this.idSousCategorie+"'");
        else if(this.idCategorie != 0)
            this.setCondition(" AND t1.id_categorie='"+this.idCategorie+"'");
    }

    @Override
    public void initTags(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            this.setTagTitle("Petites annonces");
            this.setTagDescription("Megannonce - petites annonces");
            super.initTags(request);
            Objet.getConnection();
            if (request.getParameter("mode") != null&&this.mode!=0) {
                switch (this.mode) {
                    case 1:
                        this.setTagTitle("Petites annonces - ventes - Megannonce");
                        this.setTagDescription("Megannonce - toutes les petites annonces de ventes.");
                        break;
                    case 2:
                        this.setTagTitle("Petites annonces - achats - Megannonce");
                        this.setTagDescription("Megannonce - toutes les petites annonces d'achats.");
                        break;
                }
            } else if (request.getParameter("idCategorie") != null&&this.idCategorie!=0) {
                String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                String categorie=result.getString("categorie");
                result.close();
                prepare.close();
                this.setTagTitle("Megannonce - Petites annonces - "+categorie);
                this.setTagDescription("Megannonce - Petites annonces gratuites - "+categorie+".");
            } else if(request.getParameter("idSousCategorie")!=null&&this.idSousCategorie!=0) {
                String query="SELECT t1.id_categorie,t1.sous_categorie,t2.categorie FROM table_sous_categories AS t1,table_categories AS t2 WHERE t1.id=? AND t2.id=t1.id_categorie LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idSousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.idCategorie=result.getLong("id_categorie");
                String sousCategorie=result.getString("sous_categorie");
                String categorie=result.getString("categorie");
                session.setAttribute("idCategorie", this.idCategorie);
                result.close();
                prepare.close();
                this.setTagTitle("Petites annonces - "+categorie+" - "+sousCategorie);
                this.setTagDescription("Megannonce - Toutes les petites annonces - "+categorie+" - "+sousCategorie+".");
            }
        } catch (NamingException ex) {
            Logger.getLogger(RechercheNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.setTagTitle("Megannonce - Petites annonces gratuites");
            this.setTagDescription("Megannonce - Toutes les petites annonces du net - automobile - immobilier -emploi.");
        } catch (SQLException ex) {
            Logger.getLogger(RechercheNormale.class.getName()).log(Level.SEVERE, null, ex);
            this.setTagTitle("Megannonce - Petites annonces gratuites");
            this.setTagDescription("Megannonce - Toutes les petites annonces du net - automobile - immobilier -emploi.");
        }
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

}
