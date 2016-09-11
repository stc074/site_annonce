/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
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
public class ListeCategories extends Objet {
    
    private String[] urlsCat;
    private String[] titresCat;
    private int arrayLengthCat;
    private Connection con;
    
    public ListeCategories() {
        super();
        this.arrayLengthCat=0;
    }
    
    public void initListe() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nb FROM table_categories";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.arrayLengthCat=result.getInt("nb");
            result.close();
            this.urlsCat=new String[this.getArrayLengthCat()];
            this.titresCat=new String[this.getArrayLengthCat()];
            query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
            result=state.executeQuery(query);
            int i=0;
            while(result.next()) {
                long idCategorie=result.getLong("id");
                String categorie=result.getString("categorie");
                this.urlsCat[i]="./annonces-categorie-"+idCategorie+"-"+Objet.encodeTitre(categorie)+".html";
                this.titresCat[i]=categorie.toUpperCase();
                i++;
            }
            result.close();
            state.close();
        } catch (NamingException ex) {
            Logger.getLogger(ListeCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.arrayLengthCat=0;
        } catch (SQLException ex) {
            Logger.getLogger(ListeCategories.class.getName()).log(Level.SEVERE, null, ex);
            this.arrayLengthCat=0;
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(ListeCategories.class.getName()).log(Level.SEVERE, null, ex);
                this.arrayLengthCat=0;
            }
        }
    }

    /**
     * @return the urlsCat
     */
    public String[] getUrlsCat() {
        return urlsCat;
    }

    /**
     * @return the titresCat
     */
    public String[] getTitresCat() {
        return titresCat;
    }

    /**
     * @return the arrayLengthCat
     */
    public int getArrayLengthCat() {
        return arrayLengthCat;
    }
}
