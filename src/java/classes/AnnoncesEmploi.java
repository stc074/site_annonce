/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class AnnoncesEmploi extends Annonces {
    
    private String[] lignes3Index;
    private String[] lignes4Index;
    
    public AnnoncesEmploi(Membre membre) {
        super(membre);
        this.setTable("table_annonces_emploi");
    }

    public AnnoncesEmploi() {
        super();
    }
    
    @Override
    public void initListeEdit() {
        super.initListeEdit();
    }

    @Override
    public void initListeIndex() {
        this.setTable("table_annonces_emploi");
        this.setType(4);
        super.initListeIndex();
        if(this.getNbAnnonces()>0) {
            try {
                this.lignes3Index=new String[this.getNbAnnonces()];
                this.lignes4Index=new String[this.getNbAnnonces()];
                this.setCon(Objet.getConnection2());
                String query="SELECT t1.mode,t1.type_contrat,t1.tarif_horaire,t2.categorie FROM table_annonces_emploi AS t1,table_categories_emploi AS t2 WHERE t2.id=t1.id_categorie ORDER BY t1.timestamp DESC LIMIT 0,"+this.getNbAnnonces();
                Statement state=this.getCon().createStatement();
                ResultSet result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    int mode=result.getInt("mode");
                    int typeContrat=result.getInt("type_contrat");
                    String tarifHoraire=(result.getDouble("tarif_horaire")+"").replaceAll("\\.",",");
                    String cat=result.getString("categorie");
                    this.lignes3Index[i]="";
                    switch(mode) {
                        case 1:
                            this.lignes3Index[i]+="<span>Offre d'emploi - </span>";
                            break;
                        case 2:
                            this.lignes3Index[i]+="<span>Demande d'emploi - </span>";
                            break;
                    }
                    this.lignes3Index[i]+="<span>contrat de type "+Datas.arrayTypesContrat[typeContrat-1]+".</span>";
                    this.lignes4Index[i]="";
                    switch(mode) {
                        case 1:
                            this.lignes4Index[i]+="<span>Payé "+tarifHoraire+" &euro; NET de l'heure.</span>";
                            break;
                        case 2:
                            this.lignes4Index[i]+="<span>Tarif horaire souhaité de "+tarifHoraire+" &euro; NET de l'heure.</span>";
                            break;
                    }
                    i++;
                }
                result.close();
                state.close();
            } catch (NamingException ex) {
                Logger.getLogger(AnnoncesEmploi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesEmploi.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    Objet.closeConnection2(this.getCon());
                } catch (SQLException ex) {
                    Logger.getLogger(AnnoncesEmploi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * @return the lignes3Index
     */
    public String[] getLignes3Index() {
        return lignes3Index;
    }

    /**
     * @return the lignes4Index
     */
    public String[] getLignes4Index() {
        return lignes4Index;
    }

}
