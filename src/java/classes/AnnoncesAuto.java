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
public class AnnoncesAuto extends Annonces {
    
    private String[] lignes3Index;
    private String[] lignes4Index;
    
    public AnnoncesAuto(Membre membre) {
        super(membre);
        this.setTable("table_annonces_automobile");
    }

    public AnnoncesAuto() {
        super();
    }
    
    @Override
    public void initListeEdit() {
        super.initListeEdit();
    }
    
    @Override
    public void initListeIndex() {
        this.setTable("table_annonces_automobile");
        this.setType(2);
        super.initListeIndex();
        if(this.getNbAnnonces()>0) {
            try {
                this.lignes3Index=new String[this.getNbAnnonces()];
                this.lignes4Index=new String[this.getNbAnnonces()];
                this.setCon(Objet.getConnection2());
                String query="SELECT mode,type_vehicule,type_moto,type_scooter,marque,modele,prix FROM table_annonces_automobile ORDER BY timestamp DESC LIMIT 0,"+this.getNbAnnonces();
                Statement state=this.getCon().createStatement();
                ResultSet result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    int mode=result.getInt("mode");
                    int typeVehicule=result.getInt("type_vehicule");
                    int typeMoto=result.getInt("type_moto");
                    int typeScooter=result.getInt("type_scooter");
                    String marque=result.getString("marque");
                    String modele=result.getString("modele");
                    String prix=result.getString("prix");
                    this.lignes3Index[i]="";
                    switch(mode) {
                        case 1:
                            this.lignes3Index[i]+="<span>Vends </span>";
                            break;
                        case 2:
                            this.lignes3Index[i]+="<span>Achete </span>";
                            break;
                    }
                    switch(typeVehicule) {
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
                            this.lignes3Index[i]+="<span>un véhicule de type "+Datas.arrayTypeVehicules[typeVehicule-1]+".</span>";
                            break;
                        case 11:
                            this.lignes3Index[i]+="<span>une moto de type "+Datas.arrayTypesMoto[typeMoto-1]+"/</span>";
                            break;
                        case 12:
                            this.lignes3Index[i]+="<span>un sccoter de type "+Datas.arrayTypesScooter[typeScooter-1]+".</span>";
                            break;
                        case 13:
                            this.lignes3Index[i]+="<span>une mobylette.</span>";
                            break;
                    }
                     this.lignes4Index[i]="";
                    this.lignes4Index[i]+="<span>"+modele+"-"+marque+" </span>";
                    this.lignes4Index[i]+="<span> au prix de "+prix+" &euro;.</span>";
                    i++;
                }
                result.close();
                state.close();
            } catch (NamingException ex) {
                Logger.getLogger(AnnoncesAuto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesAuto.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    Objet.closeConnection2(this.getCon());
                } catch (SQLException ex) {
                    Logger.getLogger(AnnoncesAuto.class.getName()).log(Level.SEVERE, null, ex);
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
