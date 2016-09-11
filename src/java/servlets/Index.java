/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Annonces;
import classes.AnnoncesAuto;
import classes.AnnoncesEmploi;
import classes.AnnoncesImmo;
import classes.AnnoncesNormales;
import classes.ListeCategories;
import classes.Membre;
import classes.Membres;
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
public class Index extends HttpServlet {
    
    private ListeCategories listeCategories;
    private AnnoncesNormales annoncesNormales;
    private AnnoncesAuto annoncesAuto;
    private AnnoncesImmo annoncesImmo;
    private AnnoncesEmploi annoncesEmploi;
    private Membres membres;
   
    @Override
    public void init() throws ServletException {
        super.init();
        this.listeCategories=new ListeCategories();
        this.listeCategories.initListe();
        this.annoncesNormales=new AnnoncesNormales();
        this.annoncesNormales.initListeIndex();
        this.annoncesAuto=new AnnoncesAuto();
        this.annoncesAuto.initListeIndex();
        this.annoncesImmo=new AnnoncesImmo();
        this.annoncesImmo.initListeIndex();
        this.annoncesEmploi=new AnnoncesEmploi();
        this.annoncesEmploi.initListeIndex();
        this.membres=new Membres();
        this.membres.calculNb();
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
            session.setAttribute("uriRetour", "./");
            Annonces annonces=new Annonces();
            annonces.testListes();
            //out.println(annonces.getErrorMsg());
            if(annonces.getTypes()[0]==true) {
                this.annoncesNormales=new AnnoncesNormales();
                this.annoncesNormales.initListeIndex();
                this.membres=new Membres();
                this.membres.calculNb();
            }
            if(annonces.getTypes()[1]==true) {
                this.annoncesAuto=new AnnoncesAuto();
                this.annoncesAuto.initListeIndex();
                this.membres=new Membres();
                this.membres.calculNb();
            }
            if(annonces.getTypes()[2]==true) {
                this.annoncesImmo=new AnnoncesImmo();
                this.annoncesImmo.initListeIndex();
                this.membres=new Membres();
                this.membres.calculNb();
            }
            if(annonces.getTypes()[3]==true) {
                this.annoncesEmploi=new AnnoncesEmploi();
                this.annoncesEmploi.initListeIndex();
                this.membres=new Membres();
                this.membres.calculNb();
            }
            request.setAttribute("listeCategories", this.listeCategories);
            request.setAttribute("annoncesNormales", this.annoncesNormales);
            request.setAttribute("annoncesAuto", this.annoncesAuto);
            request.setAttribute("annoncesImmo", this.annoncesImmo);
            request.setAttribute("annoncesEmploi", this.annoncesEmploi);
            request.setAttribute("membres", this.membres);
            //out.println(annoncesNormales.getErrorMsg());
            RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/WEB-INF/scripts/index.jsp");
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
