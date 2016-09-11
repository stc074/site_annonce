/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Datas;
import classes.Objet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class DisplayPhoto extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            if(request.getParameter("type")!=null&&request.getParameter("idAnnonce")!=null&&request.getParameter("index")!=null) {
                int type=Integer.parseInt(request.getParameter("type"));
                long idAnnonce=Long.parseLong(request.getParameter("idAnnonce"));
                int index=Integer.parseInt(request.getParameter("index"));
                String mini="";
                if(request.getParameter("mini")!=null)
                    mini="mini"+Objet.codeHTML(request.getParameter("mini"))+"_";
                String table="";
                switch(type) {
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
                Objet.getConnection();
                String query="SELECT extension"+index+" FROM "+table+" WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, idAnnonce);
                ResultSet result=prepare.executeQuery();
                result.next();
                String extension=result.getString("extension"+index).toLowerCase();
                result.close();
                prepare.close();
                String filename=Datas.DIR+"photos/"+mini+type+"_"+idAnnonce+"_"+index+extension;
                File file=new File(filename);
                if(file.exists()) {
                    extension=extension.replaceAll("\\.", "");
                    String mime="";
                    String format="";
                    if(extension.equals("jpg")) {
                        mime="img/jpeg";
                        format="JPEG";
                    } else {
                        mime="img/"+extension;
                        format=extension.toUpperCase();
                    }
                    response.setContentType(mime);
                    BufferedImage buffer = ImageIO.read(file);
                    OutputStream os = response.getOutputStream();
                    ImageIO.write(buffer, format, os);
                    os.close();
                }
            }
        } catch(Exception ex) {
        } finally { 
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
