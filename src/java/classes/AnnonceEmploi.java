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
public class AnnonceEmploi extends Annonce {
    
    private int mode;
    private int idCategorie;
    private int typeContrat;
    private String tarifHoraire;
    private double tarifHoraireFinal;
    private String categorieEmploi;
    private String ligne3;
    private String ligne4;
    
    public AnnonceEmploi(Membre membre) {
        super(membre);
        this.mode=0;
        this.idCategorie=0;
        this.typeContrat=0;
        this.tarifHoraire="";
        this.tarifHoraireFinal=0.0;
        this.setType(4);
    }

    public AnnonceEmploi(long idAnnonce) {
        super(idAnnonce);
    }

    public void getGets(HttpServletRequest request) {
        if(request.getParameter("mode")!=null)
            this.mode=Integer.parseInt(request.getParameter("mode"));
    }

    @Override
    public void getPostsDepot(HttpServletRequest request) {
        super.getPostsDepot(request);
        this.mode=Integer.parseInt(request.getParameter("mode"));
        this.idCategorie=Integer.parseInt(request.getParameter("idCategorie"));
        this.typeContrat=Integer.parseInt(request.getParameter("typeContrat"));
        this.tarifHoraire=Objet.codeHTML(request.getParameter("tarifHoraire"));
        this.tarifHoraire=this.tarifHoraire.replaceAll(",", "\\.");
    }

    @Override
    public void verifPostsDepot(HttpServletRequest request) {
        super.verifPostsDepot(request);
        if(!(this.mode==1||this.mode==2))
            this.setErrorMsg("<div>Veuillez choisir le TYPE D'ANNONCE SVP.</div>");
        if(this.idCategorie==0)
            this.setErrorMsg("<div>Veuillez choisir la CATEGORIE D'EMPLOI SVP.</div>");
        if(this.typeContrat==0)
            this.setErrorMsg("<div>Veuillez choisir le TYPE DE CONTRAT SVP.</div>");
        if(this.tarifHoraire.length()==0)
            this.setErrorMsg("<div>Champ TARIF HORAIRE vide.</div>");
        else if(this.tarifHoraire.length()>10)
            this.setErrorMsg("<div>Champ TARIF HORAIRE trop long.</div>");
        else {
            try {
                this.tarifHoraireFinal=Double.parseDouble(this.tarifHoraire);
            } catch(NumberFormatException ex) {
                this.tarifHoraire="";
                this.setErrorMsg("<div>Champ TARIF HORAIRE non-valide.</div>");
            }
        }
    }

    @Override
    public void enregDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
        try {
            this.setTable("table_annonces_emploi");
            this.setType(4);
            super.enregDepot(request);
            Objet.getConnection();
            String query="UPDATE table_annonces_emploi SET mode=?,id_categorie=?,type_contrat=?,tarif_horaire=? WHERE id=? AND id_membre=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, this.mode);
            prepare.setInt(2, this.idCategorie);
            prepare.setInt(3, this.typeContrat);
            prepare.setDouble(4, this.tarifHoraireFinal);
            prepare.setLong(5, this.getId());
            prepare.setLong(6, this.getMembre().getId());
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    @Override
    public void getPostsEdit(HttpServletRequest request) {
        super.getPostsEdit(request);
        this.typeContrat=Integer.parseInt(request.getParameter("typeContrat"));
        this.tarifHoraire=Objet.codeHTML(request.getParameter("tarifHoraire")).replaceAll(",", "\\.");
    }
    
    @Override
    public void verifPostsEdit(HttpServletRequest request) {
        super.verifPostsEdit(request);
        if(this.typeContrat<1||this.typeContrat>Datas.arrayTypesContrat.length)
            this.setErrorMsg("<div>Veuillez choisir le TYPE DE CONTRAT SVP.</div>");
        if(this.tarifHoraire.length()==0)
            this.setErrorMsg("<div>Champ TARIF HORAIRE vide.</div>");
        else if(this.tarifHoraire.length()>10)
            this.setErrorMsg("<div>Champ TARIF HORAIRE trop long.</div>");
        else {
            try {
                this.tarifHoraireFinal=Double.parseDouble(this.tarifHoraire);
            } catch(NumberFormatException ex) {
                this.setErrorMsg("<div>Champ TARIF HORAIRE non-valide.</div>");
                this.tarifHoraire="";
            }
        }
    }
    
    @Override
    public void enregEdit(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.setType(4);
                this.setTable("table_annonces_emploi");
                super.enregEdit(request);
                this.setCon(Objet.getConnection2());
                String query="UPDATE table_annonces_emploi SET type_contrat=?,tarif_horaire=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=this.getCon().prepareStatement(query);
                prepare.setInt(1, this.typeContrat);
                prepare.setDouble(2, this.tarifHoraireFinal);
                prepare.setLong(3, this.getId());
                prepare.setLong(4, this.getMembre().getId());
                prepare.executeUpdate();
                prepare.close();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } finally {
                try {
                    Objet.closeConnection2(this.getCon());
                } catch (SQLException ex) {
                    Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
                }
            }
        }
    }
    
    @Override
    public void initDonneesEmploi(long idAnnonce) {
        try {
            super.initDonneesEmploi(idAnnonce);
            this.setCon(Objet.getConnection2());
            String query="SELECT t1.mode,t1.type_contrat,t1.tarif_horaire,t2.categorie FROM table_annonces_emploi AS t1,table_categories_emploi AS t2 WHERE t1.id=? AND t2.id=t1.id_categorie LIMIT 0,1";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.typeContrat=result.getInt("type_contrat");
            this.tarifHoraire=result.getString("tarif_horaire").replaceAll("\\.", ",");
            String categorie=result.getString("categorie");
            result.close();
            prepare.close();
            this.ligne3="";
            switch(this.mode) {
                case 1:
                    this.ligne3+="Offre d'un contrat ";
                    break;
                case 2:
                    this.ligne3+="Demande d'un contrat ";
                    break;
            }
            if(this.typeContrat>0&&this.typeContrat<=Datas.arrayTypesContrat.length)
                this.ligne3+=Datas.arrayTypesContrat[this.typeContrat-1];
            this.ligne3+=" - Domaine : "+categorie+".";
            this.ligne4="";
            switch(this.mode) {
                case 1:
                    this.ligne4+="Tarif horaire proposé de "+this.tarifHoraire+"€ NET de l'heure.";
                    break;
                case 2:
                    this.ligne4+="Tarif horaire souhaité de "+this.tarifHoraire+"€ NET de l'heure.";
                    break;
            }
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.setId(0);
        } finally {
            try {
                Objet.closeConnection2(this.getCon());
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.setId(0);
            }
        }
    }
    
    @Override
    public void blank() {
        this.mode=0;
        this.idCategorie=0;
        this.typeContrat=0;
        this.tarifHoraire="";
        this.tarifHoraireFinal=0.0;        
    }
    @Override
    public void initInfosEdit(long idAnnonce) {
        try {
            this.setType(4);
            this.setTable("table_annonces_emploi");
            super.initInfosEdit(idAnnonce);
            this.setCon(Objet.getConnection2());
            String query="SELECT mode,id_categorie,type_contrat,tarif_horaire FROM table_annonces_emploi WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.getMembre().getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.idCategorie=result.getInt("id_categorie");
            this.typeContrat=result.getInt("type_contrat");
            this.tarifHoraire=result.getString("tarif_horaire");
            result.close();
            prepare.close();
            query="SELECT categorie FROM table_categories_emploi WHERE id=? LIMIT 0,1";
            prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            result=prepare.executeQuery();
            result.next();
            this.categorieEmploi=result.getString("categorie");
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE ; "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE ; "+ex.getMessage()+"</div>");
            try {
                Objet.closeConnection2(this.getCon());
            } catch (SQLException ex1) {
                Logger.getLogger(AnnonceEmploi.class.getName()).log(Level.SEVERE, null, ex1);
                this.setErrorMsg("<div>ERREUR INTERNE ; "+ex.getMessage()+"</div>");
            }
        }
    }
    
    @Override
    public void effaceOlds() {
        this.setType(4);
        this.setTable("table_annonces_emploi");
        super.effaceOlds();
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
     * @return the tarifHoraire
     */
    public String getTarifHoraire() {
        return tarifHoraire;
    }

    /**
     * @return the categorieEmploi
     */
    public String getCategorieEmploi() {
        return categorieEmploi;
    }

    /**
     * @return the ligne3
     */
    public String getLigne3() {
        return ligne3;
    }

    /**
     * @return the ligne4
     */
    public String getLigne4() {
        return ligne4;
    }
}
