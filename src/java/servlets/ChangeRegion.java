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
public class ChangeRegion extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if(request.getParameter("idRegion")!=null) {
                String idRegion=request.getParameter("idRegion");
                idRegion=Objet.codeHTML(idRegion);
                out.println("<label for=\"idDepartement\">Votre département :</label>");
                out.println("<select name=\"idDepartement\" id=\"idDepartement\" onchange=\"javascript:changeDepartement(this.value);\">");
                out.println("<option value=\"0\" selected=\"selected\">Choisissez</option>");
                if(!idRegion.equals("0")) {
                    Objet.getConnection();
                    String query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement";
                    PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                    prepare.setString(1, idRegion);
                    ResultSet result=prepare.executeQuery();
                    while(result.next()) {
                        String idDepartement=result.getString("id_departement");
                        String departement=result.getString("departement");
                        out.println("<option value=\""+idDepartement+"\">"+idDepartement+"-"+departement+"</option>");
                    }
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                }
                }
        } catch(Exception ex) {
            //out.println(ex.getMessage());
        } finally {
                out.println("</select>");
                out.println("<br/>");
                out.println("<div id=\"innerCommunes\">");
                out.println("<label for=\"idCommune\">Votre commune :</label>");
                out.println("<select name=\"idCommune\" id=\"idCommune\">");
                out.println("<option value=\"0\">Choisissez</option>");
                out.println("</select>");
                out.println("</div>");
            out.close();
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
