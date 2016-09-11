/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class Recherche extends Objet {

    private String motsCles;
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private String condition;
    private int page;
    private String tagTitle;
    private String tagDescription;
    private int nbAnnonces;
    private int nbAnnoncesPage;
    private int nbAnnoncesPage2;
    private int prem;
    private int der;
    private int nbPages;
    private Connection con;
    private String table;
    private long[] ids;
    private String[] titres;
    private String[] uris;
    private String[] extensions;
    private int[] largeurs;
    private int[] hauteurs;
    private String lignes1[];
    private String lignes2[];
    
    public Recherche() {
        this.motsCles="";
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.page=0;
    }

    public void reset(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        session.setAttribute("motsCles", null);
        session.setAttribute("idRegion", null);
        session.setAttribute("idDepartement", null);
        session.setAttribute("idCommune", null);
        session.setAttribute("page", null);
    }
    public void initValues(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.motsCles="";
        if(session.getAttribute("motsCles")!=null)
            this.motsCles=Objet.codeHTML(session.getAttribute("motsCles").toString());
        this.idRegion="0";
        if(session.getAttribute("idRegion")!=null)
            this.idRegion=Objet.codeHTML(session.getAttribute("idRegion").toString());
        this.idDepartement="0";
        if(session.getAttribute("idDepartement")!=null)
            this.idDepartement=Objet.codeHTML(session.getAttribute("idDepartement").toString());
        this.idCommune=0;
        if(session.getAttribute("idCommune")!=null)
            this.idCommune=Integer.parseInt(session.getAttribute("idCommune").toString());
        this.page=0;
        if(session.getAttribute("page")!=null)
            this.setPage(Integer.parseInt(session.getAttribute("page").toString()));
    }
    public void getGets(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("idRegion")!=null) {
            this.idRegion=Objet.codeHTML(request.getParameter("idRegion"));
            if(!this.idRegion.equals("0"))
                session.setAttribute("idRegion", this.idRegion);
            else
                session.setAttribute("idRegion", null);
            this.idDepartement="0";
            session.setAttribute("idDepartement", null);
            this.idCommune=0;
            session.setAttribute("idCommune", null);
            this.setPage(0);
            session.setAttribute("page", null);
        }
        if(request.getParameter("idDepartement")!=null) {
            this.idDepartement=Objet.codeHTML(request.getParameter("idDepartement"));
            if(!this.idDepartement.equals("0"))
                session.setAttribute("idDepartement", this.idDepartement);
            else
                session.setAttribute("idDepartement", null);
            this.idCommune=0;
            session.setAttribute("idCommune", null);
            this.page=0;
            session.setAttribute("page", null);
        }
        if(request.getParameter("idCommune")!=null) {
            this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
            if(this.idCommune!=0)
                session.setAttribute("idCommune", this.idCommune);
            else
                session.setAttribute("idCommune", null);
            this.page=0;
            session.setAttribute("page", null);
        }
        if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page", this.page);
        }
    }

    public void initCondition() {
        this.condition=" WHERE t1.etat='1'";
        if(this.motsCles.length()>0) {
            String motsCles2=this.motsCles;
            for(String article:Datas.arrayArticles)
                motsCles2=motsCles2.replaceAll(article, " ");
            String arrayMots[]=motsCles2.split(" ");
            for(String mot:arrayMots)
                this.condition+=" AND (t1.titre LIKE '%" + mot + "%' OR t1.description LIKE '%" + mot + "%')";
        }
        if(this.idCommune!=0)
            this.condition+=" AND t2.id_commune='" + this.idCommune + "'";
        else if(!this.idDepartement.equals("0"))
            this.condition+=" AND t2.id_departement='" + this.idDepartement + "'";
        else if(!this.idRegion.equals("0"))
            this.condition+=" AND t2.id_region='" + this.idRegion + "'";
    }

    public void getPosts(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        session.setAttribute("page", null);
        this.motsCles=Objet.codeHTML(request.getParameter("motsCles"));
        this.idRegion=Objet.codeHTML(request.getParameter("idRegion"));
        this.idDepartement=Objet.codeHTML(request.getParameter("idDepartement"));
        this.idCommune=Integer.parseInt(request.getParameter("idCommune"));
    }

    public void verifPosts(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(this.motsCles.length()>0) {
            if(this.motsCles.length()>300) {
                this.motsCles="";
                session.setAttribute("motsCles", null);
            } else
                session.setAttribute("motsCles", this.motsCles);
        }
        else
            session.setAttribute("motsCles", null);
        if(!this.idRegion.equals("0"))
            session.setAttribute("idRegion", this.idRegion);
        else
            session.setAttribute("idRegion", null);
        if(!this.idDepartement.equals("0"))
            session.setAttribute("idDepartement", this.idDepartement);
        else
            session.setAttribute("idDepartement", null);
        if(this.idCommune!=0)
            session.setAttribute("idCommune", this.idCommune);
        else
            session.setAttribute("idCommune", null);
    }
    public void initTags(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            Objet.getConnection();
            if (request.getParameter("idCommune") != null&& this.idCommune!=0) {
                String query="SELECT id_region,id_departement,commune,code_postal FROM table_communes WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.idCommune);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.idRegion=result.getString("id_region");
                this.idDepartement=result.getString("id_departement");
                String commune=result.getString("commune");
                String codePostal=result.getString("code_postal");
                session.setAttribute("idRegion", this.idRegion);
                session.setAttribute("idDepartement", this.idDepartement);
                this.tagTitle+=" - "+codePostal+" - "+commune;
                this.tagDescription+=" - "+codePostal+" - "+commune+".";
                result.close();
                prepare.close();
            } else if(request.getParameter("idDepartement")!=null&&(!this.idDepartement.equals("0"))) {
                String query="SELECT departement FROM table_departements WHERE id_departement=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.idDepartement);
                ResultSet result=prepare.executeQuery();
                result.next();
                String departement=result.getString("departement");
                result.close();
                prepare.close();
                this.tagTitle+=" - "+this.idDepartement+" - "+departement;
                this.tagDescription+=" - "+this.idDepartement+" - "+departement+".";
            } else if(request.getParameter("idRegion")!=null&&(!this.idRegion.equals("0"))) {
                String query="SELECT region FROM table_regions WHERE id_region=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.idRegion);
                ResultSet result=prepare.executeQuery();
                result.next();
                String region=result.getString("region");
                result.close();
                prepare.close();
                this.tagTitle+=" en région "+region;
                this.tagDescription+=" en région "+region+".";
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
            this.tagTitle="Megannonce - Petites annonces";
            this.tagDescription="Megannonce - petites annonces.";
        } catch (SQLException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
            this.tagTitle="Megannonce - Petites annonces";
            this.tagDescription="Megannonce - petites annonces.";
        }
    }
    public void initListe() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(t1.id) AS nbAnnonces FROM "+this.table+" AS t1,table_membres AS t2"+this.condition+" AND t2.id=t1.id_membre";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            state.close();
            this.nbPages=(int)Math.ceil((double)this.nbAnnonces/(double)Datas.NBANNONCESPAGE);
            if(this.nbAnnonces<Datas.NBANNONCESPAGE)
                this.setNbAnnoncesPage(this.nbAnnonces);
            else if(this.page<(this.nbPages-1))
                this.setNbAnnoncesPage(Datas.NBANNONCESPAGE);
            else if(this.page==(this.nbPages-1))
                this.setNbAnnoncesPage(this.nbAnnonces-((this.nbPages-1)*Datas.NBANNONCESPAGE));
            if(this.nbAnnoncesPage!=0) {
                this.nbAnnoncesPage2=(int)(Math.ceil((double)this.nbAnnoncesPage/(double)2));
                this.prem=this.page-5;
                if(this.prem<0)
                    this.prem=0;
                this.der=this.page+5;
                if(this.der>this.nbPages-1);
                this.der=this.nbPages-1;
                this.ids=new long[this.nbAnnoncesPage];
                this.titres=new String[this.nbAnnoncesPage];
                this.uris=new String[this.nbAnnoncesPage];
                this.extensions=new String[this.nbAnnoncesPage];
                this.largeurs=new int[this.nbAnnoncesPage];
                this.hauteurs=new int[this.nbAnnoncesPage];
                this.lignes1=new String[this.nbAnnoncesPage];
                this.lignes2=new String[this.nbAnnoncesPage];
                query="SELECT t1.id,t1.titre,t1.extension1,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.commune,t5.code_postal FROM "+this.table+" AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5"+this.condition+" and t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.page*Datas.NBANNONCESPAGE)+","+this.nbAnnoncesPage;
                state=this.con.createStatement();
                result=state.executeQuery(query);
                File fileMini1=null;
                Img img=new Img();
                Calendar cal=Calendar.getInstance();
                int i=0;
                while(result.next()) {
                    long idAnnonce=result.getLong("id");
                    String titre=result.getString("titre");
                    String extension=result.getString("extension1");
                    long ts=result.getLong("timestamp");
                    String pseudo=result.getString("pseudo");
                    String region=result.getString("region");
                    String departement=result.getString("departement");
                    String commune=result.getString("commune");
                    String codePostal=result.getString("code_postal");
                    cal.setTimeInMillis(ts);
                    this.ids[i]=idAnnonce;
                    this.titres[i]=titre;
                    this.uris[i]="./petite-annonce-emploi-"+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
                    if(extension.length()>0) {
                        String filenameMini1=Datas.DIR+"photos/mini1_4_"+idAnnonce+"_1"+extension;
                        fileMini1=new File(filenameMini1);
                        if(fileMini1.exists()) {
                            try {
                                img.getSize(fileMini1);
                                this.largeurs[i]=img.getWidth();
                                this.hauteurs[i]=img.getHeight();
                            } catch (IOException ex) {
                                Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            extension="";
                        }
                    }
                    this.extensions[i]=extension;
                    this.lignes1[i]="Annonce déposée par "+pseudo+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    this.lignes2[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                    i++;
                }
                result.close();
                state.close();
           }
        } catch (NamingException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.setNbAnnoncesPage(0);
        } catch (SQLException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.setNbAnnoncesPage(0);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.setNbAnnoncesPage(0);
            }
        }
    }
    /**
     * @return the motsCles
     */
    public String getMotsCles() {
        return motsCles;
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
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition += condition;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the tagTitle
     */
    public String getTagTitle() {
        return tagTitle;
    }

    /**
     * @param tagTitle the tagTitle to set
     */
    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    /**
     * @return the tagDescription
     */
    public String getTagDescription() {
        return tagDescription;
    }

    /**
     * @param tagDescription the tagDescription to set
     */
    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    /**
     * @return the nbAnnonces
     */
    public int getNbAnnonces() {
        return nbAnnonces;
    }

    /**
     * @return the nbAnnoncesPage
     */
    public int getNbAnnoncesPage() {
        return nbAnnoncesPage;
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
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the nbPages
     */
    public int getNbPages() {
        return nbPages;
    }

    /**
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
    }

    /**
     * @return the uris
     */
    public String[] getUris() {
        return uris;
    }

    /**
     * @return the extensions
     */
    public String[] getExtensions() {
        return extensions;
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
     * @return the lignes1
     */
    public String[] getLignes1() {
        return lignes1;
    }

    /**
     * @return the lignes2
     */
    public String[] getLignes2() {
        return lignes2;
    }

    /**
     * @param nbAnnoncesPage the nbAnnoncesPage to set
     */
    public void setNbAnnoncesPage(int nbAnnoncesPage) {
        this.nbAnnoncesPage = nbAnnoncesPage;
    }

    /**
     * @return the nbAnnoncesPage2
     */
    public int getNbAnnoncesPage2() {
        return nbAnnoncesPage2;
    }

    /**
     * @return the prem
     */
    public int getPrem() {
        return prem;
    }

    /**
     * @return the der
     */
    public int getDer() {
        return der;
    }
}
