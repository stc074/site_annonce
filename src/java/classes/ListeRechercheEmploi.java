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
public class ListeRechercheEmploi extends Objet {
    private Connection con;
    private int[] idsCategorie;
    private String[] categories;
    private String[] idRegions;
    private String[] regions;
    private int regionLength;
    private int arrayLength;
    public ListeRechercheEmploi() {
        
    }
    
    public void initListe() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbCategories FROM table_categories_emploi";
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            int nbCategories=result.getInt("nbCategories");
            result.close();
            this.idsCategorie=new int[nbCategories];
            this.categories=new String[nbCategories];
            this.arrayLength=nbCategories;
            query="SELECT id,categorie FROM table_categories_emploi ORDER BY categorie ASC";
            result=state.executeQuery(query);
            int i=0;
            while(result.next()) {
                this.idsCategorie[i]=result.getInt("id");
                this.categories[i]=result.getString("categorie");
                i++;
            }
            result.close();
            query="SELECT COUNT(id) AS nbRegions FROM table_regions";
            result=state.executeQuery(query);
            result.next();
            this.regionLength=result.getInt("nbRegions");
            result.close();
            this.idRegions=new String[this.regionLength];
            this.regions=new String[this.regionLength];
            query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
            result=state.executeQuery(query);
            i=0;
            while(result.next()) {
                this.idRegions[i]=result.getString("id_region");
                this.regions[i]=result.getString("region");
                i++;
            }
            result.close();
            state.close();
        } catch (NamingException ex) {
            Logger.getLogger(ListeRechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListeRechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(ListeRechercheEmploi.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }

    /**
     * @return the idsCategorie
     */
    public int[] getIdsCategorie() {
        return idsCategorie;
    }

    /**
     * @return the categories
     */
    public String[] getCategories() {
        return categories;
    }

    /**
     * @return the arrayLength
     */
    public int getArrayLength() {
        return arrayLength;
    }

    /**
     * @return the idRegions
     */
    public String[] getIdRegions() {
        return idRegions;
    }

    /**
     * @return the regions
     */
    public String[] getRegions() {
        return regions;
    }

    /**
     * @return the regionLength
     */
    public int getRegionLength() {
        return regionLength;
    }
}
