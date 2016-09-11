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
public class AnnonceAuto extends Annonce {

    private int mode;
    private int typeVehicule;
    private String marque;
    private int typeMoto;
    private int typeScooter;
    private String modele;
    private int energie;
    private String annee;
    private String kilometrage;
    private String prix;

    public AnnonceAuto(Membre membre) {
        super(membre);
        this.mode=0;
        this.typeVehicule=0;
        this.marque="";
        this.typeMoto=0;
        this.typeScooter=0;
        this.modele="";
        this.energie=0;
        this.annee="";
        this.kilometrage="";
        this.prix="";
        this.setType(2);
    }

    public AnnonceAuto() {
        super();
    }

    public void getGets(HttpServletRequest request) {
        if(request.getParameter("mode")!=null)
            this.mode=Integer.parseInt(request.getParameter("mode"));
        if(request.getParameter("typeVehicule")!=null)
            this.typeVehicule=Integer.parseInt(request.getParameter("typeVehicule"));
    }

    @Override
    public void getPostsDepot(HttpServletRequest request) {
        super.getPostsDepot(request);
        this.mode=Integer.parseInt(request.getParameter("mode"));
        this.typeVehicule=Integer.parseInt(request.getParameter("typeVehicule"));
        if(request.getParameter("typeMoto")!=null)
            this.typeMoto=Integer.parseInt(request.getParameter("typeMoto"));
        if(request.getParameter("typeScooter")!=null)
            this.typeScooter=Integer.parseInt(request.getParameter("typeScooter"));
        this.marque=request.getParameter("marque");
        this.marque=Objet.codeHTML(this.marque);
        this.modele=request.getParameter("modele");
        this.modele=Objet.codeHTML(this.modele);
        if(request.getParameter("energie")!=null)
            this.energie=Integer.parseInt(request.getParameter("energie"));
        this.annee=request.getParameter("annee");
        this.annee=Objet.codeHTML(this.annee);
        this.kilometrage=request.getParameter("kilometrage");
        this.kilometrage=Objet.codeHTML(this.kilometrage);
        this.prix=request.getParameter("prix");
        this.prix=Objet.codeHTML(this.prix);
        this.prix=this.prix.replaceAll("\\.", ",");
    }
    @Override
    public void verifPostsDepot(HttpServletRequest request) {
        super.verifPostsDepot(request);
        if(this.mode==0)
            this.setErrorMsg("<div>Choisissez si vous voulez VENDRE ou ACHETER SVP.</div>");
        if(this.typeVehicule==0)
            this.setErrorMsg("<div>Choisisssez le TYPE DE VÉHICULE SVP.</div>");
        if(this.typeVehicule==11&&this.typeMoto==0)
            this.setErrorMsg("<div>Choisissez le TYPE DE MOTO SVP.</div>");
        if(this.typeVehicule==12&&this.typeScooter==0)
            this.setErrorMsg("<div>Choisissez le TYPE DE SCOOTER SVP.</div>");
        if(this.marque.length()==0)
            this.setErrorMsg("<div>Champ MARQUE vide.</div>");
        else if(this.marque.length()>40)
            this.setErrorMsg("<div>Champ MARQUE trop long.</div>");
        if(this.modele.length()==0)
            this.setErrorMsg("<div>Champ MODELE vide.</div>");
        else if(this.modele.length()>40)
            this.setErrorMsg("<div>Champ MODÈLE trop long.</div>");
        if(this.typeVehicule>=1&&this.typeVehicule<=10&&this.energie==0)
            this.setErrorMsg("<div>Choisissez L'ENERGIE DU VÉHICULE SVP.</div>");
        Pattern p=Pattern.compile("[0-9]{4}");
        Matcher m=p.matcher(this.annee);
        if(this.annee.length()==0)
            this.setErrorMsg("<div>Champ ANNÉE vide.</div>");
        else if(this.annee.length()>4)
            this.setErrorMsg("<div>Champ ANNÉE trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ ANNÉE non-valide.</div>");
        p=Pattern.compile("[0-9]{1,10}");
        m=p.matcher(this.kilometrage);
        if(this.kilometrage.length()==0)
            this.setErrorMsg("<div>Champ KILOMÉTRAGE vide.</div>");
        else if(this.kilometrage.length()>10)
            this.setErrorMsg("<div>Champ KILOMÉTRAGE trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ KILOMÉTRAGE non-valide.</div>");
        p=Pattern.compile("[0-9]{1,10}(,){0,1}[0-9]{0,2}");
        m=p.matcher(this.prix);
        if(this.prix.length()==0)
            this.setErrorMsg("<div>Champ PRIX vide.</div>");
        else if(this.prix.length()>10)
            this.setErrorMsg("<div>Champ PRIX trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("Champ PRIX non-valide.</div>");
    }

    @Override
    public void enregDepot(HttpServletRequest request) {
        if(this.getErrorMsg().length()==0) {
            try {
                this.setTable("table_annonces_automobile");
                this.setType(2);
                super.enregDepot(request);
                Objet.getConnection();
                String query="UPDATE table_annonces_automobile SET mode=?,type_vehicule=?,type_moto=?,type_scooter=?,marque=?,modele=?,energie=?,annee=?,kilometrage=?,prix=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.mode);
                prepare.setInt(2, this.typeVehicule);
                prepare.setInt(3, this.typeMoto);
                prepare.setInt(4, this.typeScooter);
                prepare.setString(5, this.marque);
                prepare.setString(6, this.modele);
                prepare.setInt(7, this.energie);
                prepare.setString(8, this.annee);
                prepare.setString(9, this.kilometrage);
                prepare.setString(10, prix);
                prepare.setLong(11, this.getId());
                prepare.setLong(12, this.getMembre().getId());
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    @Override
    public void initDonnees(long idAnnonce) {
        try {
            this.setTable("table_annonces_automobile");
            super.initDonnees(idAnnonce);
            Objet.getConnection();
            String query="SELECT mode,type_vehicule,type_moto,type_scooter,marque,modele,energie,annee,kilometrage,prix FROM table_annonces_automobile WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.typeVehicule=result.getInt("type_vehicule");
            this.typeMoto=result.getInt("type_moto");
            this.typeScooter=result.getInt("type_scooter");
            this.marque=result.getString("marque");
            this.modele=result.getString("modele");
            this.energie=result.getInt("energie");
            this.annee=result.getString("annee");
            this.kilometrage=result.getString("kilometrage");
            this.prix=result.getString("prix");
            result.close();
            prepare.close();
            Objet.closeConnection();
            this.setUri("petite-annonce-auto-"+this.getId()+"-"+Objet.encodeTitre(this.getTitre())+".html");
            this.setTagTitle("Petite annonce auto - "+this.getTitre());
            this.setTagDescription("Megannonce - Petite annonce auto - "+this.getTitre()+".");
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }

    }

    @Override
    public void initInfosEdit(long idAnnonce) {
        try {
            this.setTable("table_annonces_automobile");
            super.initInfosEdit(idAnnonce);
            Objet.getConnection();
            String query="SELECT mode,type_vehicule,type_moto,type_scooter,marque,modele,energie,annee,kilometrage,prix FROM table_annonces_automobile WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.getMembre().getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.mode=result.getInt("mode");
            this.typeVehicule=result.getInt("type_vehicule");
            this.typeMoto=result.getInt("type_moto");
            this.typeScooter=result.getInt("type_scooter");
            this.marque=result.getString("marque");
            this.modele=result.getString("modele");
            this.energie=result.getInt("energie");
            this.annee=result.getString("annee");
            this.kilometrage=result.getString("kilometrage");
            this.prix=result.getString("prix");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }
    @Override
    public void getPostsEdit(HttpServletRequest request) {
        super.getPostsEdit(request);
        switch(this.typeVehicule) {
            case 11:
                this.typeMoto=Integer.parseInt(request.getParameter("typeMoto"));
                break;
            case 12:
                this.typeScooter=Integer.parseInt(request.getParameter("typeScooter"));
                break;
        }
        this.marque=Objet.codeHTML(request.getParameter("marque"));
        this.modele=Objet.codeHTML(request.getParameter("modele"));
        switch(this.typeVehicule) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                this.energie=Integer.parseInt(request.getParameter("energie"));
                break;
        }
        this.annee=Objet.codeHTML(request.getParameter("annee"));
        this.kilometrage=Objet.codeHTML(request.getParameter("kilometrage"));
        this.prix=Objet.codeHTML(request.getParameter("prix"));
        this.prix=this.prix.replaceAll("\\.", ",");
    }

    @Override
    public void verifPostsEdit(HttpServletRequest request) {
        super.verifPostsEdit(request);
        switch(this.typeVehicule) {
            case 11:
                if(this.typeMoto==0)
                    this.setErrorMsg("<div>Veuillez choisir le TYPE DE VOTRE MOTO SVP.</div>");
                break;
            case 12:
                if(this.typeScooter==0)
                    this.setErrorMsg("<div>Veuillez choisir le TYPE DE VOTRE SCOOTER SVP.</div>");
                break;
        }
        if(this.marque.length()==0)
            this.setErrorMsg("<div>Champ MARQUE vide.</div>");
        else if(this.marque.length()>40)
            this.setErrorMsg("<div>Champ MARQUE trop long.</div>");
        if(this.modele.length()==0)
            this.setErrorMsg("<div>Champ MODELE vide.</div>");
        else if(this.modele.length()>40)
            this.setErrorMsg("<div>Champ MODELE trop long.</div>");
        switch(this.typeVehicule) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                if(this.energie==0)
                    this.setErrorMsg("<div>Veuillez choisir L'ÉNERGIE du véhicule.</div>");
                break;
        }
        Pattern p=Pattern.compile("[0-9]{4}");
        Matcher m=p.matcher(this.annee);
        if(this.annee.length()==0)
            this.setErrorMsg("<div>Champ ANNÉE vide.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ ANNÉE non-valide.</div>");
        p=Pattern.compile("[0-9]{1,10}");
        m=p.matcher(this.kilometrage);
        if(this.kilometrage.length()==0)
            this.setErrorMsg("<div>Champ KILOMÉTRAGE vide.</div>");
        else if(this.kilometrage.length()>10)
            this.setErrorMsg("<div>Champ KILOMÉTRAGE trop long.</div>");
        else if(m.matches()==false)
            this.setErrorMsg("<div>Champ KILOMÉTRAGE non-valide.</div>");
        p=Pattern.compile("[0-9]{1,10},{0,1}[0-9]{0,2}");
        m=p.matcher(this.prix);
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
                this.setTable("table_annonces_automobile");
                this.setType(2);
                super.enregEdit(request);
                Objet.getConnection();
                String query="UPDATE table_annonces_automobile SET type_moto=?,type_scooter=?,marque=?,modele=?,energie=?,annee=?,kilometrage=?,prix=? WHERE id=? AND id_membre=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.typeMoto);
                prepare.setInt(2, this.typeScooter);
                prepare.setString(3, this.marque);
                prepare.setString(4, this.modele);
                prepare.setInt(5, this.energie);
                prepare.setString(6, this.annee);
                prepare.setString(7, this.kilometrage);
                prepare.setString(8, this.prix);
                prepare.setLong(9, this.getId());
                prepare.setLong(10, this.getMembre().getId());
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            } catch (SQLException ex) {
                Logger.getLogger(AnnonceAuto.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
            }
        }
    }

    @Override
    public void effaceOlds() {
        this.setType(2);
        this.setTable("table_annonces_automobile");
        super.effaceOlds();
    }
    @Override
    public void blank() {
        super.blank();
        this.mode=0;
        this.typeVehicule=0;
        this.marque="";
        this.typeMoto=0;
        this.typeScooter=0;
        this.modele="";
        this.energie=0;
        this.annee="";
        this.kilometrage="";
        this.prix="";
    }
    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @return the typeVehicule
     */
    public int getTypeVehicule() {
        return typeVehicule;
    }

    /**
     * @return the marque
     */
    public String getMarque() {
        return marque;
    }

    /**
     * @return the typeMoto
     */
    public int getTypeMoto() {
        return typeMoto;
    }

    /**
     * @return the typeScooter
     */
    public int getTypeScooter() {
        return typeScooter;
    }

    /**
     * @return the modele
     */
    public String getModele() {
        return modele;
    }

    /**
     * @return the energie
     */
    public int getEnergie() {
        return energie;
    }

    /**
     * @return the annee
     */
    public String getAnnee() {
        return annee;
    }

    /**
     * @return the kilometrage
     */
    public String getKilometrage() {
        return kilometrage;
    }

    /**
     * @return the prix
     */
    public String getPrix() {
        return prix;
    }

}
