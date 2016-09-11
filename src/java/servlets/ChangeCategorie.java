/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Objet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class ChangeCategorie extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
            if(request.getParameter("idCategorie")!=null) {
        try {
                long idCategorie=Long.parseLong(request.getParameter("idCategorie"));
                out.println("<label for=\"idSousCategorie\">Sous-catégorie de votre annonce :</label>");
                out.println("<select name=\"idSousCategorie\" id=\"idSousCategorie\">");
                out.println("<option value=\"0\" selected=\"selected\">Choisissez</option>");
                if(idCategorie!=0) {
                    Objet.getConnection();
                    String query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                    PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, idCategorie);
                    ResultSet result=prepare.executeQuery();
                    while(result.next()) {
                        long idSousCategorie=result.getLong("id");
                        String sousCategorie=result.getString("sous_categorie");
                        out.println("<option value=\""+idSousCategorie+"\">"+sousCategorie+"</option>");
                    }
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                }
        } catch(Exception ex) {
            out.println(ex.getMessage());
        } finally {
            out.println("</select>");
            out.close();
        }
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
