/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.ListeRechercheEmploi;
import classes.Membre;
import classes.Objet;
import classes.RechercheEmploi;
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
public class PetitesAnnoncesEmploi extends HttpServlet {
    private ListeRechercheEmploi liste;

    @Override
    public void init() throws ServletException {
        super.init();
        this.liste=new ListeRechercheEmploi();
        this.liste.initListe();
    }
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
            session.setAttribute("uriRetour", "./petites-annonces-emploi-1.html");
            RechercheEmploi recherche=new RechercheEmploi();
            recherche.initValues(request);
            recherche.initCondition();
            recherche.initListe();
            request.setAttribute("recherche", recherche);
            request.setAttribute("liste", liste);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/annoncesemploi.jsp");
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
            session.setAttribute("uriRetour", "./petites-annonces-emploi-1.html");
            RechercheEmploi recherche=new RechercheEmploi();
            if(request.getParameter("reset")!=null)
                recherche.reset(request);
            recherche.getGets(request);
            recherche.initValues(request);
            recherche.initTags(request);
            recherche.initCondition();
            recherche.initListe();
            request.setAttribute("recherche", recherche);
            request.setAttribute("liste", liste);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/annoncesemploi.jsp");
            dispatch.forward(request, response);            
        } catch(Exception ex) {
            out.println(ex.getMessage());
        } finally {            
            out.close();
        }
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
            session.setAttribute("uriRetour", "./petites-annonces-emploi-1.html");
            RechercheEmploi recherche=new RechercheEmploi();
            if(request.getParameter("kermit")!=null) {
                recherche.getPosts(request);
                recherche.verifPosts(request);
            } else if(request.getParameter("reset")!=null) {
                recherche.reset(request);
            }
            recherche.initValues(request);
            recherche.initCondition();
            recherche.initListe();
            request.setAttribute("recherche", recherche);
            request.setAttribute("liste", liste);
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/annoncesemploi.jsp");
            dispatch.forward(request, response);            
        } catch(Exception ex) {
            out.println(ex.getMessage());
        } finally {            
            out.close();
        }
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
