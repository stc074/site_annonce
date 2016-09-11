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
import java.util.ArrayList;
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
public class Annonces extends Objet {
    private Membre membre;
    private ArrayList<Long> arrayIds;
    private ArrayList<String> arrayTitres;
    private ArrayList<String> arrayDates;
    private String table;
    private Connection con;
    private int longArray;
    private String tagTitle;
    private String tagDescription;
    private int page;
    private int nbPages;
    private int nbAnnonces;
    private int nbAnnoncesPage;
    private int nbAnnoncesPage2;
    private int prem;
    private int der;
    private String title;
    private String[] titresIndex;
    private String[] urlsIndex;
    private String[] lignes1Index;
    private String[] lignes2Index;
    private String[] codesMiniIndex;
    private int type;
    private boolean[] types;
    private int nb;
    
    public Annonces() {
        
    }
    
    public Annonces(Membre membre) {
        this.membre=membre;
        arrayIds=new ArrayList<Long>();
        arrayTitres=new ArrayList<String>();
        arrayDates=new ArrayList<String>();
        this.longArray=0;
    }
    
    public void initListeEdit() {
        try {
            this.setCon(Objet.getConnection2());
            String query="SELECT id,titre,timestamp FROM "+this.table+" WHERE id_membre=? ORDER BY timestamp DESC";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.membre.getId());
            ResultSet result=prepare.executeQuery();
            this.longArray=0;
            Calendar cal=Calendar.getInstance();
            while(result.next()) {
                getArrayIds().add(result.getLong("id"));
                getArrayTitres().add(result.getString("titre"));
                long timestamp=result.getLong("timestamp");
                cal.setTimeInMillis(timestamp);
                getArrayDates().add("Annonce déposée le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR));
                this.longArray++;
            }
            result.close();
            prepare.close();
            Objet.closeConnection2(this.getCon());
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    void getGets(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(request.getParameter("page")!=null) {
            this.page=Integer.parseInt(request.getParameter("page"));
            if(this.page>0)
                session.setAttribute("page", this.page);
            else
                session.setAttribute("page", null);
        }
    }
    
    void initValues(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        if(session.getAttribute("page")!=null)
            this.page=Integer.parseInt(session.getAttribute("page").toString());
    }

    public void initListeIndex() {
        try {
            this.con=Objet.getConnection2();
            String query="SELECT COUNT(id) AS nbAnnonces FROM "+table;
            Statement state=this.con.createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.nbAnnonces=result.getInt("nbAnnonces");
            result.close();
            this.nb=this.nbAnnonces;
            if(this.nbAnnonces>Datas.NBANNONCESINDEX)
                this.nbAnnonces=Datas.NBANNONCESINDEX;
            if(this.nbAnnonces>0) {
            this.titresIndex=new String[this.nbAnnonces];
            this.urlsIndex=new String[this.nbAnnonces];
            this.codesMiniIndex=new String[this.nbAnnonces];
            this.lignes1Index=new String[this.nbAnnonces];
            this.lignes2Index=new String[this.nbAnnonces];
            switch(this.type) {
                case 1:
                case 2:
                case 3:
                    query="SELECT t1.id,t1.titre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.commune,t5.code_postal FROM "+table+" AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5 WHERE t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT 0,"+this.nbAnnonces;
                    break;
                case 4:
                    query="SELECT t1.id,t1.titre,t1.extension1,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.commune,t5.code_postal FROM "+table+" AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5 WHERE t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT 0,"+this.nbAnnonces;
                    break;
            }
            result=state.executeQuery(query);
            int i=0;
            Calendar cal=Calendar.getInstance();
            Img img=new Img();
            File fileMini1;
            while(result.next()) {
                long idAnnonce=result.getLong("id");
                String titre=result.getString("titre");
                String[] exts = null;
                int k = 0;
                switch(this.type) {
                    case 1:
                    case 2:
                    case 3:
                        exts=new String[5];
                        exts[0]=result.getString("extension1");
                        exts[1]=result.getString("extension2");
                        exts[2]=result.getString("extension3");
                        exts[3]=result.getString("extension4");
                        exts[4]=result.getString("extension5");
                        k=5;
                        break;
                    case 4:
                        exts=new String[1];
                        exts[0]=result.getString("extension1");
                        k=1;
                }                        
                long timestamp=result.getLong("timestamp");
                String pseudo=result.getString("pseudo");
                String region=result.getString("region");
                String departement=result.getString("departement");
                String commune=result.getString("commune");
                String codePostal=result.getString("code_postal");
                cal.setTimeInMillis(timestamp);
                int nbPhotos=0;
                for(int j=1; j<=k; j++) {
                    String ext=exts[j-1];
                    if(ext.length()>0&&nbPhotos==0) {
                        String filenameMini1=Datas.DIR+"photos/mini1_"+this.type+"_"+idAnnonce+"_"+j+ext;
                        fileMini1=new File(filenameMini1);
                        if(fileMini1.exists()) {
                            nbPhotos++;
                            img.getSize(fileMini1);
                            int largeur=img.getWidth();
                            int hauteur=img.getHeight();
                            this.codesMiniIndex[i]="<img src=\"./photo-mini-1-"+this.type+"-"+idAnnonce+"-"+j+""+ext+"\" width=\""+largeur+"\" height=\""+hauteur+"\" alt=\"miniaiture\"/>";
                        }
                    }
                }
                if(nbPhotos==0)
                    this.codesMiniIndex[i]="<img src=\"./GFXs/miniature.png\" width=\"100\" height=\"100\" alt=\"miniature\"/>";
                String debUrl="";
                String typeAnnonce="";
                switch(this.type) {
                    case 1:
                        debUrl="./petite-annonce-";
                        break;
                    case 2:
                        debUrl="./petite-annonce-auto-";
                        typeAnnonce=" auto";
                        break;
                    case 3:
                        debUrl="./petite-annonce-immobiliere-";
                        typeAnnonce=" immobilière";
                        break;
                    case 4:
                        debUrl="./petite-annonce-emploi-";
                        typeAnnonce=" d'emploi";
                        break;
                }
                this.titresIndex[i]=titre;
                this.urlsIndex[i]=debUrl+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
                this.lignes1Index[i]="Annonce déposée par "+pseudo+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                this.lignes2Index[i]="Localisation : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                i++;
            }
            result.close();
            state.close();
            query="UPDATE table_test_listes SET type"+this.type+"=? WHERE id=?";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setInt(1, 0);
            prepare.setInt(2, 1);
            prepare.executeUpdate();
            prepare.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
            }
        }
    }

     public void testListes() {
        try {
            this.types=new boolean[4];
            this.types[0]=false;
            this.types[1]=false;
            this.types[2]=false;
            this.types[3]=false;
            this.con=Objet.getConnection2();
            String query="SELECT type1,type2,type3,type4 FROM table_test_listes WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.con.prepareStatement(query);
            prepare.setInt(1, 1);
            ResultSet result=prepare.executeQuery();
            result.next();
            int type1=result.getInt("type1");
            int type2=result.getInt("type2");
            int type3=result.getInt("type3");
            int type4=result.getInt("type4");
            result.close();
            prepare.close();
            if(type1==1)
                this.types[0]=true;
            if(type2==1)
                this.types[1]=true;
            if(type3==1)
                this.types[2]=true;
            if(type4==1)
                this.types[3]=true;
        } catch (NamingException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.con);
            } catch (SQLException ex) {
                Logger.getLogger(Annonces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reset(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.page=0;
        session.setAttribute("page", 0);
    }

  /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the arrayIds
     */
    public ArrayList<Long> getArrayIds() {
        return arrayIds;
    }

    /**
     * @return the arrayTitres
     */
    public ArrayList<String> getArrayTitres() {
        return arrayTitres;
    }


    /**
     * @return the longArray
     */
    public int getLongArray() {
        return longArray;
    }

    /**
     * @return the arrayDates
     */
    public ArrayList<String> getArrayDates() {
        return arrayDates;
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
     * @return the nbPages
     */
    public int getNbPages() {
        return nbPages;
    }

    /**
     * @param nbPages the nbPages to set
     */
    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    /**
     * @return the nbAnnonces
     */
    public int getNbAnnonces() {
        return nbAnnonces;
    }

    /**
     * @param nbAnnonces the nbAnnonces to set
     */
    public void setNbAnnonces(int nbAnnonces) {
        this.nbAnnonces = nbAnnonces;
    }

    /**
     * @return the nbAnnoncesPage
     */
    public int getNbAnnoncesPage() {
        return nbAnnoncesPage;
    }

    /**
     * @param nbAnnoncesPage the nbAnnoncesPage to set
     */
    public void setNbAnnoncesPage(int nbAnnoncesPage) {
        this.nbAnnoncesPage = nbAnnoncesPage;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the titresIndex
     */
    public String[] getTitresIndex() {
        return titresIndex;
    }

    /**
     * @return the lignes1Index
     */
    public String[] getLignes1Index() {
        return lignes1Index;
    }

    /**
     * @return the lignes2Index
     */
    public String[] getLignes2Index() {
        return lignes2Index;
    }

    /**
     * @return the codesMiniIndex
     */
    public String[] getCodesMiniIndex() {
        return codesMiniIndex;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the urlsIndex
     */
    public String[] getUrlsIndex() {
        return urlsIndex;
    }

    /**
     * @return the types
     */
    public boolean[] getTypes() {
        return types;
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

    /**
     * @param nbAnnoncesPage2 the nbAnnoncesPage2 to set
     */
    public void setNbAnnoncesPage2(int nbAnnoncesPage2) {
        this.nbAnnoncesPage2 = nbAnnoncesPage2;
    }

    /**
     * @param prem the prem to set
     */
    public void setPrem(int prem) {
        this.prem = prem;
    }

    /**
     * @param der the der to set
     */
    public void setDer(int der) {
        this.der = der;
    }

    /**
     * @return the nb
     */
    public int getNb() {
        return nb;
    }

}
