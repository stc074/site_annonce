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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author pj
 */
public class AnnoncePhotoEmploi extends Annonce {
    private Connection con;
    private String extension1;
    private int largeur;
    private int hauteur;
    
    public AnnoncePhotoEmploi(Membre membre) {
        super(membre);
        this.largeur=0;
        this.hauteur=0;
        this.extension1="";
    }

    public void testAnnoncePhotos(int type, long idAnnonce) {
        this.setType(type);
        this.setId(idAnnonce);
        if(this.getType()!=4)
            this.setId(0);
        else {
            try {
                this.con=Objet.getConnection2();
                String query="SELECT titre,extension1 FROM table_annonces_emploi WHERE id=? AND id_membre=? LIMIT 0,1";
                PreparedStatement prepare=this.con.prepareStatement(query);
                prepare.setLong(1, this.getId());
                prepare.setLong(2, this.getMembre().getId());
                ResultSet result=prepare.executeQuery();
                result.next();
                this.setTitre(result.getString("titre"));
                this.extension1=result.getString("extension1");
                Img img=new Img();
                result.close();
                prepare.close();
                if(this.extension1.length()>0) {
                    String filename=Datas.DIR+"photos/4_"+this.getId()+"_1"+this.extension1;
                    String filenameMini2=Datas.DIR+"photos/mini2_4_"+this.getId()+"_1"+this.extension1;
                    File file=new File(filename);
                    File fileMini2=new File(filenameMini2);
                    if(file.exists()&&fileMini2.exists()) {
                        try {
                            img.getSize(fileMini2);
                            this.largeur=img.getWidth();
                            this.hauteur=img.getHeight();
                        } catch (IOException ex) {
                            Logger.getLogger(AnnoncePhotoEmploi.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        this.largeur=0;
                        this.hauteur=0;
                    }
                }    
                } catch (NamingException ex) {
                Logger.getLogger(AnnoncePhotoEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setId(0);
            } catch (SQLException ex) {
                Logger.getLogger(AnnoncePhotoEmploi.class.getName()).log(Level.SEVERE, null, ex);
                this.setId(0);
            } finally {
                try {
                    Objet.closeConnection2(this.con);
                } catch (SQLException ex) {
                    Logger.getLogger(AnnoncePhotoEmploi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void getPostsPhoto(HttpServletRequest request) {
        try {
            this.con=Objet.getConnection2();
            Img img=new Img();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(Datas.MAXUPLOADSIZE);
            factory.setRepository(new File("home/temp"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(Datas.MAXUPLOADSIZE);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while(iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if(!item.isFormField()) {
                    String name=item.getFieldName();
                    if(name.equals("image")&&item.getSize()>0) {
                        String extension=(item.getName().substring(item.getName().lastIndexOf("."))).toLowerCase();
                        if(!Objet.testExtension(extension))
                            this.setErrorMsg("<div>MAUVAIS FORMAT DE FICHIER (Uniquement GIF, PNG, JPG, JPEG).</div>");
                       if(this.getErrorMsg().length()==0) {
                           String query="SELECT titre,extension1 FROM table_annonces_emploi WHERE id=? AND id_membre=? LIMIT 0,1";
                           PreparedStatement prepare=this.con.prepareStatement(query);
                           prepare.setLong(1, this.getId());
                           prepare.setLong(2, this.getMembre().getId());
                           ResultSet result=prepare.executeQuery();
                           result.next();
                           this.setTitre(result.getString("titre"));
                           String ext=result.getString("extension1");
                           result.close();
                           prepare.close();
                           String filename=Datas.DIR+"photos/4_"+this.getId()+"_1"+ext;
                           String filenameMini1=Datas.DIR+"photos/mini1_4_"+this.getId()+"_1"+ext;
                           String filenameMini2=Datas.DIR+"photos/mini2_4_"+this.getId()+"_1"+ext;
                           File file=new File(filename);
                           File fileMini1=new File(filenameMini1);
                           File fileMini2=new File(filenameMini2);
                           if(file.exists())
                               file.delete();
                           if(fileMini1.exists())
                               fileMini1.delete();
                           if(fileMini2.exists())
                               fileMini2.delete();
                           filename=Datas.DIR+"photos/4_"+this.getId()+"_1"+extension;
                           filenameMini1=Datas.DIR+"photos/mini1_4_"+this.getId()+"_1"+extension;
                           filenameMini2=Datas.DIR+"photos/mini2_4_"+this.getId()+"_1"+extension;
                           file=new File(filename);
                           fileMini1=new File(filenameMini1);
                           fileMini2=new File(filenameMini2);
                           item.write(file);
                           img.resizeWidth(file, fileMini1, Datas.MINI1LARG);
                           img.resizeHeight(file, fileMini2, Datas.MINI2HAUT);
                           img.getSize(file);
                           if(img.getWidth()>Datas.MAXLARGPHOTO)
                               img.resizeWidth(file, file, Datas.MAXLARGPHOTO);
                           img.getSize(file);
                           if(img.getHeight()>Datas.MAXHAUTPHOTO)
                               img.resizeHeight(file, file, Datas.MAXHAUTPHOTO);
                           if(file.exists()&&fileMini1.exists()&&fileMini2.exists()) {
                               query="UPDATE table_annonces_emploi SET extension1=? WHERE id=? AND id_membre=?";
                               prepare=this.con.prepareStatement(query);
                               prepare.setString(1, extension);
                               prepare.setLong(2, this.getId());
                               prepare.setLong(3, this.getMembre().getId());
                               prepare.executeUpdate();
                               prepare.close();
                               this.extension1=extension;
                               img.getSize(fileMini2);
                               this.largeur=img.getWidth();
                               this.hauteur=img.getHeight();
                           }
                       } 
                    }
                }                
            }
            Objet.closeConnection2(this.con);
        } catch (Exception ex) {
            Logger.getLogger(AnnoncePhotoEmploi.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE :"+ex.getMessage()+"</div>");
        }
    }

    /**
     * @return the extension1
     */
    public String getExtension1() {
        return extension1;
    }

    /**
     * @return the largeur
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * @return the hauteur
     */
    public int getHauteur() {
        return hauteur;
    }
}
