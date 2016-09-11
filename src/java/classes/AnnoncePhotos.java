/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
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
public class AnnoncePhotos extends Annonce {

    private String extensions[];

    public AnnoncePhotos(Membre membre) {
        super(membre);
        this.extensions=new String[5];
    }

    public void testAnnoncePhotos(int type, long idAnnonce) {
        try {
            this.setType(type);
            this.setId(idAnnonce);
            Objet.getConnection();
            String table="";
            switch(this.getType()) {
                case 1:
                    table="table_annonces_normales";
                    break;
                case 2:
                    table="table_annonces_automobile";
                    break;
                case 3:
                    table="table_annonces_immobilier";
                    break;
                default:
                    this.setId(0);
                    break;
            }
            String query="SELECT titre,extension1,extension2,extension3,extension4,extension5 FROM "+table+" WHERE id=? AND id_membre=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getId());
            prepare.setLong(2, this.getMembre().getId());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.setTitre(result.getString("titre"));
            this.extensions[0]=result.getString("extension1");
            this.extensions[1]=result.getString("extension2");
            this.extensions[2]=result.getString("extension3");
            this.extensions[3]=result.getString("extension4");
            this.extensions[4]=result.getString("extension5");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AnnoncePhotos.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        } catch (SQLException ex) {
            Logger.getLogger(AnnoncePhotos.class.getName()).log(Level.SEVERE, null, ex);
            this.setId(0);
        }
    }

    public void getPosts(HttpServletRequest request) {
        try {
            Objet.getConnection();
            Img img=new Img();
            String table="";
            switch(this.getType()) {
                case 1:
                    table="table_annonces_normales";
                    break;
                case 2:
                    table="table_annonces_automobile";
                    break;
                case 3:
                    table="table_annonces_immobilier";
                    break;
                case 4:
                    table="table_annonces_emploi";
                    break;
            }
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
                    if((name.equals("1")||name.equals("2")||name.equals("3")||name.equals("4")||name.equals("5"))&&item.getSize()>0) {
                        String ext=(item.getName().substring(item.getName().lastIndexOf("."))).toLowerCase();
                        if(!Objet.testExtension(ext)) {
                            this.setErrorMsg("<div>Mauvais FORMAT DE FICHIER (Uniquement PNG, JPG, JPEG, GIF).</div>");
                        }
                        if(this.getErrorMsg().length()==0) {
                        String query="SELECT extension"+name+" FROM "+table+" WHERE id=? AND id_membre=? LIMIT 0,1";
                        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                        prepare.setLong(1, this.getId());
                        prepare.setLong(2, this.getMembre().getId());
                        ResultSet result=prepare.executeQuery();
                        result.next();
                        String extension=result.getString("extension"+name);
                        result.close();
                        prepare.close();
                        String filename=Datas.DIR+"photos/"+this.getType()+"_"+this.getId()+"_"+name+extension;
                        String filenameMini1=Datas.DIR+"photos/mini1_"+this.getType()+"_"+this.getId()+"_"+name+extension;
                        String filenameMini2=Datas.DIR+"photos/mini2_"+this.getType()+"_"+this.getId()+"_"+name+extension;
                        File file=new File(filename);
                        File fileMini1=new File(filenameMini1);
                        File fileMini2=new File(filenameMini2);
                        if(file.exists())
                            file.delete();
                        if(fileMini1.exists())
                            fileMini1.delete();
                        if(fileMini2.exists())
                            fileMini2.delete();
                        filename=Datas.DIR+"photos/"+this.getType()+"_"+this.getId()+"_"+name+ext;
                        filenameMini1=Datas.DIR+"photos/mini1_"+this.getType()+"_"+this.getId()+"_"+name+ext;
                        filenameMini2=Datas.DIR+"photos/mini2_"+this.getType()+"_"+this.getId()+"_"+name+ext;
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
                            query="UPDATE "+table+" SET extension"+name+"=? WHERE id=? AND id_membre=?";
                            prepare=Objet.getConn().prepareStatement(query);
                            prepare.setString(1, ext);
                            prepare.setLong(2, this.getId());
                            prepare.setLong(3, this.getMembre().getId());
                            prepare.executeUpdate();
                            prepare.close();
                            this.extensions[Integer.parseInt(name)-1]=ext;
                        }
                        }
                    }
                }
            }
            Objet.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(AnnoncePhotos.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("<div>ERREUR INTERNE : "+ex.getMessage()+"</div>");
        }
    }

    /**
     * @return the extensions
     */
    @Override
    public String[] getExtensions() {
        return extensions;
    }
}
