/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.IOException;
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
public class AnnoncesNormales extends Annonces {
    
    private long idCategorie;
    private long idSousCategorie;
    private String[] urlsSousCat;
    private String[] titresSousCat;
    private long[] idsSousCat;
    private int arrayLengthSousCat;
    private String categorie;
    private String sousCategorie;
    private long[] ids;
    private String[] urls;
    private String[] titres;
    private String[] lignes1;
    private String[] lignes2;
    private String[] lignes3;
    private String[] lignes3Index;
    private String[] lignes4Index;
    private String[] extensions;
    private int[] largeurs;
    private int[] hauteurs;
    private int[] indexs;
    private String encodedCat;
    private String encodedSousCat;
    private String urlParent;
    
    public AnnoncesNormales() {
        super();
    }
    public AnnoncesNormales(Membre membre) {
        super(membre);
        this.setTable("table_annonces_normales");
    }
    
    public AnnoncesNormales(long idCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.idSousCategorie=0;
        this.setPage(0);
    }
    
    public AnnoncesNormales(long idCategorie, long idSousCategorie) {
        super();
        this.idCategorie=idCategorie;
        this.idSousCategorie=idSousCategorie;
        this.setPage(0);
    }
    
    public void initListeSousCat() {
        try {
            this.setCon(Objet.getConnection2());
            String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            String cat=result.getString("categorie");
            result.close();
            prepare.close();
            this.urlParent="./annonces-categorie-"+this.idCategorie+"-"+Objet.encodeTitre(cat)+".html";
            query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=?";
            prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.getIdCategorie());
            result=prepare.executeQuery();
            result.next();
            this.arrayLengthSousCat=result.getInt("nb");
            result.close();
            prepare.close();
            this.idsSousCat=new long[this.getArrayLengthSousCat()];
            this.urlsSousCat=new String[this.getArrayLengthSousCat()];
            this.titresSousCat=new String[this.getArrayLengthSousCat()];
            query="SELECT t1.id,t1.sous_categorie,t2.categorie FROM table_sous_categories AS t1,table_categories AS t2 WHERE t1.id_categorie=? AND t2.id=t1.id_categorie ORDER BY sous_categorie ASC";
            prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.getIdCategorie());
            result=prepare.executeQuery();
            int i=0;
            while(result.next()) {
                long idSousCat=result.getLong("id");
                String sousCat=result.getString("sous_categorie");
                cat=result.getString("categorie");
                this.idsSousCat[i]=idSousCat;
                this.urlsSousCat[i]="./annonces-sous-categorie-"+this.getIdCategorie()+"-"+idSousCat+"-"+Objet.encodeTitre(sousCat)+"-"+Objet.encodeTitre(cat)+".html";
                this.titresSousCat[i]=sousCat;
                i++;
            }
            result.close();
            prepare.close();
        } catch (NamingException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idCategorie=0;
        } finally {
            try {
                Objet.closeConnection2(this.getCon());
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.idCategorie=0;
            }
        }
    }
    
    public void initTags(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            this.setTagTitle("Megannonce - Petites annonces gratuites");
            this.setTagDescription("Megannonce - Petites annonces gratuites en France.");
            this.setCon(Objet.getConnection2());
            String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            String cat=result.getString("categorie");
            result.close();
            prepare.close();
            this.setTagTitle("Petites annonces gratuites - "+cat);
            this.setTagDescription("Megannonce - Petites annonces gratuites - "+cat+".");
            this.setTitle("Megannonce - "+cat);
            this.categorie=cat;
            this.encodedCat=Objet.encodeTitre(cat);
            session.setAttribute("uriRetour", "./annonces-categorie2-"+this.idCategorie+"-"+Objet.encodeTitre(cat)+".html");
        } catch (NamingException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idCategorie=0;
        } finally {
            try {
                Objet.closeConnection2(this.getCon());
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.idCategorie=0;
            }
        }
    }
    
    public void initListeAnnoncesCat() {
        try {
             this.setCon(Objet.getConnection2());
             String query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces_normales WHERE id_categorie=?";
             PreparedStatement prepare=this.getCon().prepareStatement(query);
             prepare.setLong(1, this.idCategorie);
             ResultSet result=prepare.executeQuery();
             result.next();
             this.setNbAnnonces(result.getInt("nbAnnonces"));
             result.close();
             prepare.close();
             this.setNbPages((int)Math.ceil((double)this.getNbAnnonces()/(double)Datas.NBANNONCESPAGE));
             this.setNbAnnoncesPage(0);
             if(this.getNbAnnonces()<=Datas.NBANNONCESPAGE)
                 this.setNbAnnoncesPage(this.getNbAnnonces());
             else if(this.getPage()<(this.getNbPages()-1))
                 this.setNbAnnoncesPage(Datas.NBANNONCESPAGE);
             else if(this.getPage()==(this.getNbPages()-1))
                 this.setNbAnnoncesPage(this.getNbAnnonces()-((this.getNbPages()-1)*Datas.NBANNONCESPAGE));
             if(this.getNbAnnoncesPage()>0) {
                 this.setNbAnnoncesPage2((int)(Math.ceil((double)this.getNbAnnoncesPage()/(double)2)));
                 this.setPrem(this.getPage()-5);
                 if(this.getPrem()<0)
                     this.setPrem(0);
                 this.setDer(this.getPage()+5);
                 if(this.getDer()>(this.getNbPages()-1))
                     this.setDer(this.getNbPages()-1);
                 this.urls=new String[this.getNbAnnoncesPage()];
                 this.titres=new String[this.getNbAnnoncesPage()];
                 this.lignes1=new String[this.getNbAnnoncesPage()];
                 this.lignes2=new String[this.getNbAnnoncesPage()];
                 this.lignes3=new String[this.getNbAnnoncesPage()];
                 this.extensions=new String[this.getNbAnnoncesPage()];
                 this.largeurs=new int[this.getNbAnnoncesPage()];
                 this.hauteurs=new int[this.getNbAnnoncesPage()];
                 this.ids=new long[this.getNbAnnoncesPage()];
                 this.indexs=new int[this.getNbAnnoncesPage()];
                 query="SELECT t1.id,t1.mode,t1.prix,t1.titre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.sous_categorie,t4.region,t5.departement,t6.commune,t6.code_postal FROM table_annonces_normales AS t1,table_membres AS t2,table_sous_categories AS t3,table_regions AS t4,table_departements AS t5,table_communes AS t6 WHERE t1.id_categorie=? AND t2.id=t1.id_membre AND t3.id=t1.id_sous_categorie AND t4.id_region=t2.id_region AND t5.id_departement=t2.id_departement AND t6.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.getPage()*Datas.NBANNONCESPAGE)+","+this.getNbAnnoncesPage();
                 prepare=this.getCon().prepareStatement(query);
                 prepare.setLong(1, this.idCategorie);
                 result=prepare.executeQuery();
                 int i=0;
                 Calendar cal=Calendar.getInstance();
                 Img img=new Img();
                 File fileMini1;
                 while(result.next()) {
                     long idAnnonce=result.getLong("id");
                     int mode=result.getInt("mode");
                     String prix=result.getString("prix").replaceAll("\\.", ",");
                     String titre=result.getString("titre");
                     String[] exts=new String[5];
                     exts[0]=result.getString("extension1");
                     exts[1]=result.getString("extension2");
                     exts[2]=result.getString("extension3");
                     exts[3]=result.getString("extension4");
                     exts[4]=result.getString("extension5");
                     long timestamp=result.getLong("timestamp");
                     String pseudo=result.getString("pseudo");
                     String sousCat=result.getString("sous_categorie");
                     String region=result.getString("region");
                     String departement=result.getString("departement");
                     String commune=result.getString("commune");
                     String codePostal=result.getString("code_postal");
                     cal.setTimeInMillis(timestamp);
                     int nbPhotos=0;
                     for(int j=1; j<=5; j++) {
                         String ext=exts[j-1];
                         if(ext.length()>0&&nbPhotos==0) {
                             String filenameMini1=Datas.DIR+"photos/mini1_1_"+idAnnonce+"_"+j+ext;
                             fileMini1=new File(filenameMini1);
                             if(fileMini1.exists()) {
                                 nbPhotos++;
                                 img.getSize(fileMini1);
                                 this.largeurs[i]=img.getWidth();
                                 this.hauteurs[i]=img.getHeight();
                                 this.extensions[i]=ext;
                                 this.indexs[i]=j;
                             }
                         }
                     }
                     if(nbPhotos==0) {
                         this.extensions[i]="";
                         this.largeurs[i]=0;
                         this.hauteurs[i]=0;
                         this.indexs[i]=0;
                     }
                     this.ids[i]=idAnnonce;
                     this.titres[i]=titre;
                     this.urls[i]="./petite-annonce-"+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
                     this.lignes1[i]="";
                     this.lignes1[i]+="<span>"+this.categorie+"-"+sousCat+" &rarr;</span>";
                     switch(mode) {
                         case 1:
                             this.lignes1[i]+="<span>Vente - </span>";
                             break;
                         case 2:
                             this.lignes1[i]+="<span>Achat - </span>";
                             break;
                     }
                     this.lignes1[i]+="<span>"+prix+" &euro;.</span>";
                     this.lignes2[i]="<strong>Annonce</strong> déposée par "+pseudo+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                     this.lignes3[i]="Localisation de l'annonceur : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                     i++;
                 }
                 result.close();
                 prepare.close();
             }
        } catch (IOException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } catch (NamingException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg(ex.getMessage());
            this.idCategorie=0;
        } finally {
            try {
                Objet.closeConnection2(this.getCon());
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
                this.idCategorie=0;
            }
        }
    }

    public void initTags2(HttpServletRequest request) {
        try {
            HttpSession session=request.getSession(true);
            this.setTagTitle("Petites annonces gratuites");
            this.setTagDescription("Megannonce - Petites annonces gratuites en France.");
            this.setCon(Objet.getConnection2());
            String query="SELECT t1.categorie,t2.sous_categorie FROM table_categories AS t1, table_sous_categories AS t2 WHERE t2.id=? AND t1.id=t2.id_categorie LIMIT 0,1";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.idSousCategorie);
            ResultSet result=prepare.executeQuery();
            result.next();
            this.categorie=result.getString("categorie");
            this.sousCategorie=result.getString("sous_categorie");
            result.close();
            prepare.close();
            this.setTagTitle("Petites annonces - "+this.categorie+" - "+this.getSousCategorie());
            this.setTagDescription("Megannonce - Petites annonces gratuites - "+this.categorie+" - "+this.getSousCategorie()+".");
            this.encodedCat=Objet.encodeTitre(this.categorie);
            this.encodedSousCat=Objet.encodeTitre(this.getSousCategorie());
            session.setAttribute("uriRetour", "./annonces-sous-categorie2-"+this.idCategorie+"-"+this.idSousCategorie+"-"+this.encodedSousCat+"-"+this.encodedCat+".html");
        } catch (NamingException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } catch (SQLException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
        } finally {
            try {
                Objet.closeConnection2(this.getCon());
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
            }
        }
    }

    public void initListeAnnoncesSousCat() {
        try {
            this.setCon(Objet.getConnection2());
            String query="SELECT COUNT(id) AS nbAnnonces FROM table_annonces_normales WHERE id_categorie=? AND id_sous_categorie=?";
            PreparedStatement prepare=this.getCon().prepareStatement(query);
            prepare.setLong(1, this.idCategorie);
            prepare.setLong(2, this.idSousCategorie);
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.setNbAnnonces(result.getInt("nbAnnonces"));
            result.close();
            prepare.close();
            if(this.getNbAnnonces()>0) {
            this.setNbPages((int)(Math.ceil((double)this.getNbAnnonces()/(double)Datas.NBANNONCESPAGE)));
            if(this.getNbAnnonces()<=Datas.NBANNONCESPAGE)
                this.setNbAnnoncesPage(this.getNbAnnonces());
            else if(this.getPage()<(this.getNbPages()-1))
                this.setNbAnnoncesPage(Datas.NBANNONCESPAGE);
            else if(this.getPage()==(this.getNbPages()-1))
                this.setNbAnnoncesPage(this.getNbAnnonces()-((this.getNbPages()-1)*Datas.NBANNONCESPAGE));
            if(this.getNbAnnoncesPage()>0) {
                this.setNbAnnoncesPage2((int)(Math.ceil((double)this.getNbAnnoncesPage()/(double)2)));
                this.setPrem(this.getPage()-5);
                if(this.getPrem()<0)
                    this.setPrem(0);
                this.setDer(this.getPage()+5);
                if(this.getDer()>(this.getNbPages()-1))
                    this.setDer(this.getNbPages()-1);
                this.urls=new String[this.getNbAnnoncesPage()];
                this.titres=new String[this.getNbAnnoncesPage()];
                this.ids=new long[this.getNbAnnoncesPage()];
                this.lignes1=new String[this.getNbAnnoncesPage()];
                this.lignes2=new String[this.getNbAnnoncesPage()];
                this.lignes3=new String[this.getNbAnnoncesPage()];
                this.indexs=new int[this.getNbAnnoncesPage()];
                this.extensions=new String[this.getNbAnnoncesPage()];
                this.largeurs=new int[this.getNbAnnoncesPage()];
                this.hauteurs=new int[this.getNbAnnoncesPage()];
                query="SELECT t1.id,t1.mode,t1.prix,t1.titre,t1.extension1,t1.extension2,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.commune,t5.code_postal FROM table_annonces_normales AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5 WHERE t1.id_categorie=? AND t1.id_sous_categorie=? AND t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(this.getPage()*Datas.NBANNONCESPAGE)+","+this.getNbAnnoncesPage();
                prepare=this.getCon().prepareStatement(query);
                prepare.setLong(1, this.idCategorie);
                prepare.setLong(2, this.idSousCategorie);
                result=prepare.executeQuery();
                Calendar cal=Calendar.getInstance();
                Img img=new Img();
                File fileMini1;
                int i=0;
                while(result.next()) {
                    long idAnnonce=result.getLong("id");
                    int mode=result.getInt("mode");
                    String prix=result.getString("prix").replaceAll("\\.", ",");
                    String titre=result.getString("titre");
                    String[] exts=new String[5];
                    exts[0]=result.getString("extension1");
                    exts[1]=result.getString("extension2");
                    exts[2]=result.getString("extension3");
                    exts[3]=result.getString("extension4");
                    exts[4]=result.getString("extension5");
                    long timestamp=result.getLong("timestamp");
                    String pseudo=result.getString("pseudo");
                    String region=result.getString("region");
                    String departement=result.getString("departement");
                    String commune=result.getString("commune");
                    String codePostal=result.getString("code_postal");
                    cal.setTimeInMillis(timestamp);
                    int nbPhotos=0;
                    for(int j=1; j<=5; j++) {
                        String ext=exts[j-1];
                        if(ext.length()>0&&nbPhotos==0) {
                            String filenameMini1=Datas.DIR+"photos/mini1_1_"+idAnnonce+"_"+j+ext;
                            fileMini1=new File(filenameMini1);
                            if(fileMini1.exists()) {
                                nbPhotos++;
                                img.getSize(fileMini1);
                                this.largeurs[i]=img.getWidth();
                                this.hauteurs[i]=img.getHeight();
                                this.indexs[i]=j;
                                this.extensions[i]=ext;
                            }
                        }
                    }
                    if(nbPhotos==0) {
                        this.largeurs[i]=0;
                        this.hauteurs[i]=0;
                        this.indexs[i]=0;
                        this.extensions[i]="";
                    }
                    this.ids[i]=idAnnonce;
                    this.urls[i]="./petite-annonce-"+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
                    this.titres[i]=titre;
                    this.lignes1[i]="";
                    this.lignes1[i]+="<span>"+this.categorie+"-"+this.getSousCategorie()+" &rarr; </span>";
                    switch(mode) {
                        case 1:
                            this.lignes1[i]+="<span>Vente : </span>";
                            break;
                        case 2:
                            this.lignes1[i]+="<span>Achat : </span>";
                            break;
                    }
                    this.lignes1[i]+="<span>"+prix+" &euro;.</span>";
                    this.lignes2[i]="<strong>Annonce</strong> déposée par "+pseudo+" le, "+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+".";
                    this.lignes3[i]="Localisation de l'annonceur : "+codePostal+"-"+commune+"|"+region+"-"+departement+".";
                    i++;
                }
                result.close();
                prepare.close();
            }
            }
        } catch (IOException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
            this.setErrorMsg(ex.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
            this.setErrorMsg(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            this.idCategorie=0;
            this.setErrorMsg(ex.getMessage());
        } finally {
            try {
                Objet.closeConnection2(this.getCon());
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
                this.idCategorie=0;
                this.setErrorMsg(ex.getMessage());
            }
        }
    }

    @Override
    public void initListeIndex() {
        this.setTable("table_annonces_normales");
        this.setType(1);
        super.initListeIndex();
        if(this.getNbAnnonces()>0) {
            try {
                this.lignes3Index=new String[this.getNbAnnonces()];
                this.lignes4Index=new String[this.getNbAnnonces()];
                this.setCon(Objet.getConnection2());
                String query="SELECT t1.mode,t1.prix,t2.categorie,t3.sous_categorie FROM table_annonces_normales AS t1,table_categories AS t2,table_sous_categories AS t3 WHERE t2.id=t1.id_categorie AND t3.id=t1.id_sous_categorie ORDER BY t1.timestamp DESC LIMIT 0,"+this.getNbAnnonces();
                Statement state=this.getCon().createStatement();
                ResultSet result=state.executeQuery(query);
                int i=0;
                while(result.next()) {
                    int mode=result.getInt("mode");
                    String prix=result.getString("prix");
                    String cat=result.getString("categorie");
                    String sousCat=result.getString("sous_categorie");
                    this.lignes3Index[i]=""
                            +"<span>"+cat+"-"+sousCat+".</span>";
                    this.lignes4Index[i]="";
                    switch(mode) {
                        case 1:
                            this.lignes4Index[i]+="<span>Vente au prix de </span>";
                            break;
                        case 2:
                            this.lignes4Index[i]+="<span>Achat au prix de </span>";
                            break;
                    }
                    this.lignes4Index[i]+="<span>"+prix+" &euro;.</span>";
                    i++;
                }
                result.close();
                state.close();
            } catch (NamingException ex) {
                Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg(ex.getMessage());
            } finally {
                try {
                    Objet.closeConnection2(this.getCon());
                } catch (SQLException ex) {
                    Logger.getLogger(AnnoncesNormales.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void initValues(HttpServletRequest request) {
        super.initValues(request);
    }

    @Override
    public void getGets(HttpServletRequest request) {
        super.getGets(request);
    }

    @Override
    public void initListeEdit() {
        super.initListeEdit();
    }

    /**
     * @return the idCategorie
     */
    public long getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public long getIdSousCategorie() {
        return idSousCategorie;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the urlsSousCat
     */
    public String[] getUrlsSousCat() {
        return urlsSousCat;
    }

    /**
     * @return the titresSousCat
     */
    public String[] getTitresSousCat() {
        return titresSousCat;
    }

    /**
     * @return the arrayLengthSousCat
     */
    public int getArrayLengthSousCat() {
        return arrayLengthSousCat;
    }

    /**
     * @return the ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * @return the urls
     */
    public String[] getUrls() {
        return urls;
    }

    /**
     * @return the titres
     */
    public String[] getTitres() {
        return titres;
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
     * @return the lignes3
     */
    public String[] getLignes3() {
        return lignes3;
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
     * @return the indexs
     */
    public int[] getIndexs() {
        return indexs;
    }

    /**
     * @return the encodedCat
     */
    public String getEncodedCat() {
        return encodedCat;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @return the encodedSousCat
     */
    public String getEncodedSousCat() {
        return encodedSousCat;
    }

    /**
     * @return the idsSousCat
     */
    public long[] getIdsSousCat() {
        return idsSousCat;
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

    /**
     * @return the urlParent
     */
    public String getUrlParent() {
        return urlParent;
    }

}
