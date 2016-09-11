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
public class AnnoncesImmo extends Annonces {
    
    private String[] lignes3Index;
    private String[] lignes4Index;
    
    public AnnoncesImmo(Membre membre) {
        super(membre);
        this.setTable("table_annonces_immobilier");
    }

    public AnnoncesImmo() {
        super();
    }
    
    @Override
    public void initListeEdit() {
        super.initListeEdit();
    }
    @Override
    public void initListeIndex() {
        this.setTable("table_annonces_immobilier");
        this.setType(3);
        super.initListeIndex();
        if(this.getNbAnnonces()>0) {
            try {
                this.lignes3Index=new String[this.getNbAnnonces()];
                this.lignes4Index=new String[this.getNbAnnonces()];
                this.setCon(Objet.getConnection2());
                String query="SELECT mode,type_bien,type_appartement,prix,loyer,charges FROM table_annonces_immobilier ORDER BY timestamp DESC LIMIT 0,"+this.getNbAnnonces();
                Statement state=this.getCon().createStatement();
                ResultSet result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    int mode=result.getInt("mode");
                    int typeBien=result.getInt("type_bien");
                    int typeAppartement=result.getInt("type_appartement");
                    String prix=result.getString("prix");
                    String loyer=result.getString("loyer");
                    String charges=result.getString("charges");
                    this.lignes3Index[i]="";
                    switch(mode) {
                        case 1:
                            this.lignes3Index[i]+="<span>Vente </span>";
                            break;
                        case 2:
                            this.lignes3Index[i]+="<span>Location </span>";
                            break;
                        case 3:
                            this.lignes3Index[i]+="<span>Location saisonnière </span>";
                            break;
                        case 4:
                            this.lignes3Index[i]+="<span>Achat </span>";
                            break;
                    }
                    switch(typeBien) {
                        case 1:
                            this.lignes3Index[i]+="<span>d'un appartement de type "+Datas.arrayTypesAppart[typeAppartement-1]+".</span>";
                            break;
                        default:
                            this.lignes3Index[i]+="<span>d'un bien de type "+Datas.arrayTypesBien[typeBien-1]+".</span>";
                            break;
                    }
                    this.lignes4Index[i]="";
                    switch(mode) {
                        case 1:
                            this.lignes4Index[i]+="<span>Prix de "+prix+" &euro;.</span>";
                            break;
                        case 2:
                            this.lignes4Index[i]+="<span>Loyer de "+loyer+" &euro; mensuel + "+charges+" &euro; de charges.</span>";
                            break;
                        case 3:
                            this.lignes4Index[i]+="<span>Loyer de "+loyer+" &euro; à la semaine.</span>";
                            break;
                        case 4:
                            this.lignes3Index[i]+="<span>Prix souhaité de "+prix+" &euro; à l'achat.</span>";
                            break;
                    }
                    i++;
                }
                result.close();
                state.close();
            } catch (NamingException ex) {
                Logger.getLogger(AnnoncesImmo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesImmo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    Objet.closeConnection2(this.getCon());
                } catch (SQLException ex) {
                    Logger.getLogger(AnnoncesImmo.class.getName()).log(Level.SEVERE, null, ex);
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
