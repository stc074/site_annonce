/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Annonce;
import classes.AnnoncesAuto;
import classes.AnnoncesEmploi;
import classes.AnnoncesImmo;
import classes.AnnoncesNormales;
import classes.Membre;
import classes.Objet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class MesAnnonces extends HttpServlet {
   
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
        try {
            HttpSession session=Objet.initSession(request, response);
            Membre mbr=new Membre();
            mbr.testConnecte(request);
            if(request.getParameter("kermitCnx")!=null) {
                mbr.getPostsCnx(request);
                mbr.verifPostsCnx(request, response);
            }
            request.setAttribute("mbr", mbr);
            Membre membre=new Membre();
            membre.testConnecte(request);
            long idMembre=membre.getId();
            if(idMembre==0)
                request.setAttribute("info", 1);
            else {
                membre.initInfos2();
                if(membre.getId()==0)
                    request.setAttribute("info", 1);
                else {
                    if(request.getParameter("type")!=null&&request.getParameter("idDel")!=null) {
                        Annonce annonce=new Annonce();
                        long idAnnonce=Long.parseLong(request.getParameter("idDel"));
                        int type=Integer.parseInt(request.getParameter("type"));
                        annonce.effaceAnnonce(idAnnonce, type, idMembre);
                    }
                    AnnoncesNormales annoncesNormales=new AnnoncesNormales(membre);
                    annoncesNormales.initListeEdit();
                    AnnoncesAuto annoncesAuto=new AnnoncesAuto(membre);
                    annoncesAuto.initListeEdit();
                    AnnoncesImmo annoncesImmo=new AnnoncesImmo(membre);
                    annoncesImmo.initListeEdit();
                    AnnoncesEmploi annoncesEmploi=new AnnoncesEmploi(membre);
                    annoncesEmploi.initListeEdit();
                    request.setAttribute("annoncesNormales", annoncesNormales);
                    request.setAttribute("annoncesAuto", annoncesAuto);
                    request.setAttribute("annoncesImmo", annoncesImmo);
                    request.setAttribute("annoncesEmploi", annoncesEmploi);
                }
            }
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/mesannonces.jsp");
            dispatch.forward(request, response);
        } catch(Exception ex) {
            out.println(ex.getMessage());
        } finally { 
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
