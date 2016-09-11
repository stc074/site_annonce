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
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class RechercheEmploi extends Recherche {
    
    private int mode;
    private int idCategorie;
    private int typeContrat;
    private String tarifHoraireMin;
    private String tarifHoraireMax;
    private Double tarifHoraireDoubleMin;
    private Double tarifHoraireDoubleMax;
    private String[] lignes3;
    private String[] lignes4;
    
    public RechercheEmploi() {
        super();
        this.setTagTitle("Petites annonces gratuites de l'emploi");
        this.setTagDescription("Megannonce - Petites annonces gratuites de l'enploi en France.");
        this.mode=0;
        this.idCategorie=0;
        this.typeContrat=0;
        this.tarifHoraireMin="";
        this.tarifHoraireMax="";
        this.tarifHoraireDoubleMin=0.0;
        this.tarifHoraireDoubleMax=0.0;
    }
    @Override
    public void reset(HttpServletRequest request) {
        super.reset(request);
        HttpSession session=request.getSession(true);
        session.setAttribute("mode", null);
        session.setAttribute("idCategorie", null);
        session.setAttribute("typeContrat", null);
        session.setAttribute("tarifHoraireMin", null);
        session.setAttribute("tarifHoraireMax", null);
        session.setAttribute("tarifHoraireDoubleMin", null);
        session.setAttribute("tarifHoraireDoubleMax", null);
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
            this.idCategorie=Integer.parseInt(session.getAttribute("idCategorie").toString());
        this.typeContrat=0;
        if(session.getAttribute("typeContrat")!=null)
            this.typeContrat=Integer.parseInt(session.getAttribute("typeContrat").toString());
        this.tarifHoraireMin="";
        if(session.getAttribute("tarifHoraireMin")!=null)
            this.tarifHoraireMin=Objet.codeHTML(session.getAttribute("tarifHoraireMin").toString()).replaceAll(",", "\\.");
        this.tarifHoraireMax="";
        if(session.getAttribute("tarifHoraireMax")!=null)
            this.tarifHoraireMax=Objet.codeHTML(session.getAttribute("tarifHoraireMax").toString()).replaceAll(",", "\\.");
        this.tarifHoraireDoubleMin=0.0;
        if(session.getAttribute("tarifHoraireDoubleMin")!=null)
            this.tarifHoraireDoubleMin=Double.parseDouble(session.getAttribute("tarifHoraireDoubleMin").toString());
        this.tarifHoraireDoubleMax=0.0;
        if(session.getAttribute("tarifHoraireDoubleMax")!=null)
            this.tarifHoraireDoubleMax=Double.parseDouble(session.getAttribute("tarifHoraireDoubleMax").toString());
    }

    @Override
    public void getGets(HttpServletRequest request) {
        super.getGets(request);
        HttpSession session=request.getSession(true);
        if(request.getParameter("mode")!=null) {
            this.mode=Integer.parseInt(request.getParameter("mode"));
            if(this.mode==1||this.mode==2) {
                session.setAttribute("mode", this.mode);
                this.setPage(0);
                session.setAttribute("page", null);
            } else {
                this.mode=0;
                session.setAttribute("mode", null);
            }
        } else if(request.getParameter("idCategorie")!=null) {
            this.idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
            if(this.idCategorie!=0) {
                session.setAttribute("idCategorie", this.idCategorie);
            } else {
                session.setAttribute("idCategorie", null);
            }                
        } else if(request.getParameter("typeContrat")!=null) {
            this.typeContrat=Integer.parseInt(request.getParameter("typeContrat"));
            if(this.typeContrat>0&&this.typeContrat<=Datas.arrayTypesContrat.length) {
                session.setAttribute("typeContrat", this.typeContrat);
            } else {
                this.typeContrat=0;
                session.setAttribute("typeContrat", null);
            }
        }
    }

    @Override
    public void getPosts(HttpServletRequest request) {
        super.getPosts(request);
        HttpSession session=request.getSession(true);
        if(request.getParameter("mode")!=null)
            this.mode=Integer.parseInt(request.getParameter("mode"));
        this.idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
        this.typeContrat=Integer.parseInt(request.getParameter("typeContrat"));
        this.tarifHoraireMin=Objet.codeHTML(request.getParameter("tarifHoraireMin")).replaceAll(",", "\\.");
        this.tarifHoraireMax=Objet.codeHTML(request.getParameter("tarifHoraireMax")).replaceAll(",", "\\.");
    }

    @Override
    public void verifPosts(HttpServletRequest request) {
        super.verifPosts(request);
        HttpSession session=request.getSession(true);
        if(this.mode==1||this.mode==2) {
            session.setAttribute("mode", this.mode);
        } else {
            this.mode=0;
            session.setAttribute("mode", null);
        }
        if(this.idCategorie!=0) {
            session.setAttribute("idCategorie", this.idCategorie);
        } else {
            session.setAttribute("idCategorie", null);
        }
        if(this.typeContrat>0&&this.typeContrat<=Datas.arrayTypesContrat.length) {
            session.setAttribute("typeContrat", this.typeContrat);
        } else {
            this.typeContrat=0;
            session.setAttribute("typeContrat", null);
        }
        if(this.tarifHoraireMin.length()==0||this.tarifHoraireMin.length()>10) {
            this.tarifHoraireMin="";
            session.setAttribute("tarifHoraireMin", null);
        } else {
            try {
                this.tarifHoraireDoubleMin=Double.parseDouble(this.tarifHoraireMin);
                session.setAttribute("tarifHoraireMin", this.tarifHoraireMin);
                session.setAttribute("tarifHoraireDoubleMin", this.tarifHoraireDoubleMin);
            } catch(NumberFormatException ex) {
                this.tarifHoraireMin="";
                this.tarifHoraireDoubleMin=0.0;
                session.setAttribute("tarifHoraireMin", null);
                session.setAttribute("tarifHoraireDoubleMin", null);
            }
        }
        if(this.tarifHoraireMax.length()==0||this.tarifHoraireMax.length()>10) {
            this.tarifHoraireMax="";
            session.setAttribute("tarifHoraireMax", null);
        } else {
            try {
                this.tarifHoraireDoubleMax=Double.parseDouble(this.tarifHoraireMax);
                session.setAttribute("tarifHoraireMax", this.tarifHoraireMax);
                session.setAttribute("tarifHoraireDoubleMax", this.tarifHoraireDoubleMax);
            } catch(NumberFormatException ex) {
                this.tarifHoraireDoubleMax=0.0;
                this.tarifHoraireMax="";
                session.setAttribute("tarifHoraireMax", null);
                session.setAttribute("tarifHoraireDoubleMax", null);
            }
        }
    }

    @Override
    public void initCondition() {
        super.initCondition();
        if(this.mode==1||this.mode==2)
            this.setCondition(" AND t1.mode='"+this.mode+"'");
        if(this.idCategorie!=0)
            this.setCondition(" AND t1.id_categorie='"+this.idCategorie+"'");
        if(this.typeContrat>0&&this.typeContrat<=Datas.arrayTypesContrat.length)
            this.setCondition(" AND t1.type_contrat='"+this.typeContrat+"'");
        if(this.tarifHoraireDoubleMin!=0.0)
            this.setCondition(" AND t1.tarif_horaire>='"+this.tarifHoraireDoubleMin+"'");
        if(this.tarifHoraireDoubleMax!=0.0)
            this.setCondition(" AND t1.tarif_horaire<='"+this.tarifHoraireDoubleMax+"'");
    }
    
    @Override
    public void initTags(HttpServletRequest request) {
        this.setTagTitle("Petites annonces de l'emploi");
        this.setTagDescription("Megannonce - Petites annonces gratuites de l'emploi");
        super.initTags(request);
        if(request.getParameter("mode")!=null) {
            switch(this.mode) {
                case 1:
                    this.setTagTitle("Petites annonces des offres d'emploi");
                    this.setTagDescription("Megannonce - Petites annonces gratuites des offres d'emploi en France.");
                    break;
                case 2:
                    this.setTagTitle("Petites annonces des demandes d'emploi");
                    this.setTagDescription("Megannonce - Petites annonces gratuites des demandes d'emploi en France.");
                    break;
            }
        } else if(request.getParameter("idCategorie")!=null) {
            try {
                this.setCon(Objet.getConnection2());
                String query="SELECT categorie FROM table_categories_emploi WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=this.getCon().prepareStatement(query);
                prepare.setInt(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                String categorieEmploi=result.getString("categorie");
                result.close();
                prepare.close();
                this.setTagTitle("Petites annonces de l'emploi - "+categorieEmploi);
                this.setTagDescription("Megannonce - Petites annonces gratuites de l'emploi : "+categorieEmploi+".");
            } catch (NamingException ex) {
                Logger.getLogger(RechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    Objet.closeConnection2(this.getCon());
                } catch (SQLException ex) {
                    Logger.getLogger(RechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if(request.getParameter("typeContrat")!=null) {
            if(this.typeContrat>0&&this.typeContrat<=Datas.arrayTypesContrat.length) {
                String contrat=Datas.arrayTypesContrat[this.typeContrat-1];
                this.setTagTitle("Petites annonces de l'emploi contrat "+contrat);
                this.setTagDescription("Megannonce - Petites annonces de l'emploi tous les contrats en "+contrat);
            }
        }
    }
    @Override
    public void initListe() {
        this.setTable("table_annonces_emploi");
        super.initListe();
        if(this.getNbAnnoncesPage()!=0) {
            try {
                this.setCon(Objet.getConnection2());
                this.lignes3=new String[this.getNbAnnoncesPage()];
                this.lignes4=new String[this.getNbAnnoncesPage()];
                String query="SELECT t1.mode,t1.type_contrat,t1.tarif_horaire,t3.categorie FROM table_annonces_emploi AS t1,table_membres AS t2,table_categories_emploi AS t3"+this.getCondition()+" AND t2.id=t1.id_membre AND t3.id=t1.id_categorie ORDER BY t1.timestamp DESC LIMIT "+(this.getPage()*Datas.NBANNONCESPAGE)+","+this.getNbAnnoncesPage();
                Statement state=this.getCon().createStatement();
                ResultSet result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    int mode2=result.getInt("mode");
                    int typeContrat2=result.getInt("type_contrat");
                    String tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.", ",");
                    String categorie=result.getString("categorie");
                    this.lignes3[i]="";
                    switch(mode2) {
                        case 1:
                            this.lignes3[i]+="Offre d'emploi";
                            break;
                        case 2:
                            this.lignes3[i]+="Demande d'emploi";
                            break;
                    }
                    if(typeContrat2>0&&typeContrat2<=Datas.arrayTypesContrat.length) {
                        this.lignes3[i]+=", contrat "+Datas.arrayTypesContrat[typeContrat2-1];
                    }
                    this.lignes3[i]+=" - domaine : "+categorie;
                    this.lignes4[i]="";
                    switch(mode2) {
                        case 1:
                            this.lignes4[i]+="Payé "+tarifHoraire+" € NET de l'heure.";
                            break;
                        case 2:
                            this.lignes4[i]+="Tarif horaire souhaité de "+tarifHoraire+" € NET de l'heure.";
                            break;
                    }
                    i++;
                }
                result.close();
                state.close();
            } catch (NamingException ex) {
                Logger.getLogger(RechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.setNbAnnoncesPage(0);
            } catch (SQLException ex) {
                Logger.getLogger(RechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.setNbAnnoncesPage(0);
            } finally {
                try {
                    Objet.closeConnection2(this.getCon());
                } catch (SQLException ex) {
                    Logger.getLogger(RechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg(ex.getMessage());
                    this.setNbAnnoncesPage(0);
                }
            }
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
    public int getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the typeContrat
     */
    public int getTypeContrat() {
        return typeContrat;
    }

    /**
     * @return the tarifHoraireMin
     */
    public String getTarifHoraireMin() {
        return tarifHoraireMin;
    }

    /**
     * @return the tarifHoraireMax
     */
    public String getTarifHoraireMax() {
        return tarifHoraireMax;
    }

    /**
     * @return the lignes3
     */
    public String[] getLignes3() {
        return lignes3;
    }

    /**
     * @return the lignes4
     */
    public String[] getLignes4() {
        return lignes4;
    }

}
