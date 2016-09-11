/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class RechercheImmo extends Recherche {

    private int mode;
    private int typeBien;
    private int typeAppartement;
    private String surfaceMin;
    private String surfaceMax;
    private int meuble;
    private String nbPiecesMin;
    private String nbPiecesMax;
    private String nbEtagesMin;
    private String nbEtagesMax;
    private int viabilise;
    private int constructible;
    private String prixMin;
    private String prixMax;
    private String loyerMin;
    private String loyerMax;

    public RechercheImmo() {
        super();
        this.setTagTitle("Petites annonces immobilières gratuites");
        this.setTagDescription("Megannonce - Petites annonces immobières gratuites dans toute la France.");
        this.mode=0;
        this.typeBien=0;
        this.typeAppartement=0;
        this.surfaceMin="";
        this.surfaceMax="";
        this.meuble=0;
        this.nbPiecesMin="";
        this.nbPiecesMax="";
        this.nbEtagesMin="";
        this.nbEtagesMax="";
        this.viabilise=0;
        this.constructible=0;
        this.prixMin="";
        this.prixMax="";
        this.loyerMin="";
        this.loyerMax="";
    }

    @Override
    public void reset(HttpServletRequest request) {
        super.reset(request);
        HttpSession session=request.getSession(true);
        session.setAttribute("mode", null);
        session.setAttribute("typeBien", null);
        session.setAttribute("typeAppartement", null);
        session.setAttribute("surfaceMin", null);
        session.setAttribute("surfaceMax", null);
        session.setAttribute("meuble", null);
        session.setAttribute("nbPiecesMin", null);
        session.setAttribute("nbPiecesMax", null);
        session.setAttribute("nbEtagesMin", null);
        session.setAttribute("nbEtagesMax", null);
        session.setAttribute("viabilise", null);
        session.setAttribute("constructible", null);
        session.setAttribute("prixMin", null);
        session.setAttribute("prixMax", null);
        session.setAttribute("loyerMin", null);
        session.setAttribute("loyerMax", null);
    }

    @Override
    public void initValues(HttpServletRequest request) {
        super.initValues(request);
        HttpSession session=request.getSession(true);
        this.mode=0;
        if(session.getAttribute("mode")!=null)
            this.mode=Integer.parseInt(session.getAttribute("mode").toString());
        this.typeBien=0;
        if(session.getAttribute("typeBien")!=null)
            this.typeBien=Integer.parseInt(session.getAttribute("typeBien").toString());
        this.typeAppartement=0;
        if(session.getAttribute("typeAppartement")!=null)
            this.typeAppartement=Integer.parseInt(session.getAttribute("typeAppartement").toString());
        this.surfaceMin="";
        if(session.getAttribute("surfaceMin")!=null)
            this.surfaceMin=Objet.codeHTML(session.getAttribute("surfaceMin").toString());
        this.surfaceMax="";
        if(session.getAttribute("surfaceMax")!=null)
            this.surfaceMax=Objet.codeHTML(session.getAttribute("surfaceMax").toString());
        this.meuble=0;
        if(session.getAttribute("meuble")!=null)
            this.meuble=Integer.parseInt(session.getAttribute("meuble").toString());
        this.nbPiecesMin="";
        if(session.getAttribute("nbPiecesMin")!=null)
            this.nbPiecesMin=Objet.codeHTML(session.getAttribute("nbPiecesMin").toString());
        this.nbPiecesMax="";
        if(session.getAttribute("nbPiecesMax")!=null)
            this.nbPiecesMax=Objet.codeHTML(session.getAttribute("nbPiecesMax").toString());
        this.nbEtagesMin="";
        if(session.getAttribute("nbEtagesMin")!=null)
            this.nbEtagesMin=Objet.codeHTML(session.getAttribute("nbEtagesMin").toString());
        this.nbEtagesMax="";
        if(session.getAttribute("nbEtagesMax")!=null)
            this.nbEtagesMax=Objet.codeHTML(session.getAttribute("nbEtagesMax").toString());
        this.viabilise=0;
        if(session.getAttribute("viabilise")!=null)
            this.viabilise=Integer.parseInt(session.getAttribute("viabilise").toString());
        this.constructible=0;
        if(session.getAttribute("constructible")!=null)
            this.constructible=Integer.parseInt(session.getAttribute("constructible").toString());
        this.prixMin="";
        if(session.getAttribute("prixMin")!=null)
            this.prixMin=Objet.codeHTML(session.getAttribute("prixMin").toString());
        this.prixMax="";
        if(session.getAttribute("prixMax")!=null)
            this.prixMax=Objet.codeHTML(session.getAttribute("prixMax").toString());
        this.loyerMin="";
        if(session.getAttribute("loyerMin")!=null)
            this.loyerMin=Objet.codeHTML(session.getAttribute("loyerMin").toString());
        this.loyerMax="";
        if(session.getAttribute("loyerMax")!=null)
            this.loyerMax=Objet.codeHTML(session.getAttribute("loyerMax").toString());
    }

    @Override
    public void getGets(HttpServletRequest request) {
        super.getGets(request);
        HttpSession session=request.getSession(true);
        if(request.getParameter("mode")!=null) {
            this.mode=Integer.parseInt(request.getParameter("mode"));
            if(this.mode<=0||this.mode>4) {
                this.mode=0;
                session.setAttribute("mode", null);
            } else {
                session.setAttribute("mode", this.mode);
            }
        } else if(request.getParameter("typeBien")!=null) {
            this.typeBien=Integer.parseInt(request.getParameter("typeBien"));
            this.typeAppartement=0;
            session.setAttribute("typeAppartement", null);
            this.meuble=0;
            session.setAttribute("meuble", null);
            this.surfaceMin="";
            session.setAttribute("surfaceMin", null);
            this.surfaceMax="";
            session.setAttribute("surfaceMax", null);
            this.nbPiecesMin="";
            session.setAttribute("nbPiecesMin", null);
            this.nbPiecesMax="";
            session.setAttribute("nbPiecesMax", null);
            this.nbEtagesMin="";
            session.setAttribute("nbEtagesMin", null);
            this.nbEtagesMax="";
            session.setAttribute("nbEtagesMax", null);
            this.viabilise=0;
            session.setAttribute("viabilise", null);
            this.constructible=0;
            session.setAttribute("constructible", null);
            this.prixMin="";
            session.setAttribute("prixMin", null);
            this.prixMax="";
            session.setAttribute("prixMax", null);
            this.loyerMin="";
            session.setAttribute("loyerMin", null);
            this.loyerMax="";
            session.setAttribute("loyerMax", null);
            if(this.typeBien==0) {
                session.setAttribute("typeBien", null);
            } else {
                session.setAttribute("typeBien", this.typeBien);
            }
        } else if(request.getParameter("typeAppartement")!=null) {
            this.typeAppartement=Integer.parseInt(request.getParameter("typeAppartement"));
            if(this.typeAppartement==0)
                session.setAttribute("typeAppartement", null);
            else
                session.setAttribute("typeAppartement", this.typeAppartement);
        } else if(request.getParameter("meuble")!=null) {
            this.meuble=Integer.parseInt(request.getParameter("meuble"));
            if(this.meuble<1||this.meuble>2) {
                this.meuble=0;
                session.setAttribute("meuble", null);
            } else {
                session.setAttribute("meuble", this.meuble);
            }
        } else if(request.getParameter("viabilise")!=null) {
            this.viabilise=Integer.parseInt(request.getParameter("viabilise"));
            if(!(this.viabilise==1||this.viabilise==2)) {
                this.viabilise=0;
                session.setAttribute("viabilise", null);
            } else {
                session.setAttribute("viabilise", this.viabilise);
            }
        } else if(request.getParameter("constructible")!=null) {
            this.constructible=Integer.parseInt(request.getParameter("constructible"));
            if(!(this.constructible==1||this.constructible==2)) {
                this.constructible=0;
                session.setAttribute("constructible", null);
            } else {
                session.setAttribute("constructible", this.constructible);
            }
        }
    }

    @Override
    public void getPosts(HttpServletRequest request) {
        super.getPosts(request);
        if(request.getParameter("mode")!=null) {
            this.mode=Integer.parseInt(request.getParameter("mode"));
        }
        this.typeBien=Integer.parseInt(request.getParameter("typeBien"));
        switch(this.typeBien) {
            case 1:
                this.typeAppartement=Integer.parseInt(request.getParameter("typeAppartement"));
                break;
        }
        this.surfaceMin=Objet.codeHTML(request.getParameter("surfaceMin"));
        this.surfaceMax=Objet.codeHTML(request.getParameter("surfaceMax"));
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
                if(request.getParameter("meuble")!=null)
                    this.meuble=Integer.parseInt(request.getParameter("meuble"));
                break;
        }
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                this.nbPiecesMin=Objet.codeHTML(request.getParameter("nbPiecesMin"));
                this.nbPiecesMax=Objet.codeHTML(request.getParameter("nbPiecesMax"));
                break;
            case 6:
                this.nbEtagesMin=Objet.codeHTML(request.getParameter("nbEtagesMin"));
                this.nbEtagesMax=Objet.codeHTML(request.getParameter("nbEtagesMax"));
                break;
            case 7:
                if(request.getParameter("viabilise")!=null)
                    this.viabilise=Integer.parseInt(request.getParameter("viabilise"));
                if(request.getParameter("constructible")!=null)
                    this.constructible=Integer.parseInt(request.getParameter("constructible"));
                break;
        }
        switch(this.mode) {
            case 1:
            case 4:
                this.prixMin=Objet.codeHTML(request.getParameter("prixMin"));
                this.prixMax=Objet.codeHTML(request.getParameter("prixMax"));
                break;
            case 2:
            case 3:
                this.loyerMin=Objet.codeHTML(request.getParameter("loyerMin"));
                this.loyerMax=Objet.codeHTML(request.getParameter("loyerMax"));
                break;
        }
    }

    @Override
    public void verifPosts(HttpServletRequest request) {
        super.verifPosts(request);
        HttpSession session=request.getSession(true);
        if(this.mode<=0||this.mode>4) {
            this.mode=0;
            session.setAttribute("mode", null);
        } else {
            session.setAttribute("mode", this.mode);
        }
        if(this.typeBien==0)
            session.setAttribute("typeBien", null);
        else
            session.setAttribute("typeBien", this.typeBien);
        switch(this.typeBien) {
            case 1:
                if(this.typeAppartement==0)
                    session.setAttribute("typeAppartement", null);
                else
                    session.setAttribute("typeAppartement", this.typeAppartement);
                break;
        }
        Pattern p=Pattern.compile("[0-9]{1,10}");
        Matcher m=p.matcher(this.surfaceMin);
        if(this.surfaceMin.length()==0||this.surfaceMin.length()>10||m.matches()==false) {
            this.surfaceMin="";
            session.setAttribute("surfaceMin", null);
        } else {
            session.setAttribute("surfaceMin", this.surfaceMin);
        }
        m=p.matcher(this.surfaceMax);
        if(this.surfaceMax.length()==0||this.surfaceMax.length()>10||m.matches()==false) {
            this.surfaceMax="";
            session.setAttribute("surfaceMax", null);
        } else {
            session.setAttribute("surfaceMax", this.surfaceMax);
        }
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
                if(this.meuble<1||this.meuble>2) {
                    this.meuble=0;
                    session.setAttribute("meuble", null);
                } else {
                    session.setAttribute("meuble", this.meuble);
                }
                break;
        }
        switch(this.typeBien) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                p=Pattern.compile("[0-9]{1,10}");
                m=p.matcher(this.nbPiecesMin);
                if(this.nbPiecesMin.length()==0||this.nbPiecesMin.length()>10||m.matches()==false) {
                    this.nbPiecesMin="";
                    session.setAttribute("nbPiecesMin", null);
                } else {
                    session.setAttribute("nbPiecesMin", this.nbPiecesMin);
                }
                m=p.matcher(this.nbPiecesMax);
                if(this.nbPiecesMax.length()==0||this.nbPiecesMax.length()>10||m.matches()==false) {
                    this.nbPiecesMax="";
                    session.setAttribute("nbPiecesMax", null);
                } else {
                    session.setAttribute("nbPiecesMax", this.nbPiecesMax);
                }
                break;
            case 6:
                p=Pattern.compile("[0-9]{1,10}");
                m=p.matcher(this.nbEtagesMin);
                if(this.nbEtagesMin.length()==0||this.nbEtagesMin.length()>10||m.matches()==false) {
                    this.nbEtagesMin="";
                    session.setAttribute("nbEtagesMin", null);
                } else {
                    session.setAttribute("nbEtagesMin", this.nbEtagesMin);
                }
                m=p.matcher(this.nbEtagesMax);
                if(this.nbEtagesMax.length()==0||this.nbEtagesMax.length()>10||m.matches()==false) {
                    this.nbEtagesMax="";
                    session.setAttribute("nbEtagesMax", null);
                } else {
                    session.setAttribute("nbEtagesMax", this.nbEtagesMax);
                }
                break;
            case 7:
                if(!(this.viabilise==1||this.viabilise==2)) {
                    this.viabilise=0;
                    session.setAttribute("viabilise", null);
                } else {
                    session.setAttribute("viabilise", this.viabilise);
                }
                if(this.constructible==1||this.constructible==2) {
                    session.setAttribute("constructible", this.constructible);
                } else {
                    this.constructible=0;
                    session.setAttribute("constructible", null);
                }
                break;
        }
        switch(this.mode) {
            case 1:
            case 4:
                p=Pattern.compile("[0-9]{1,10}");
                m=p.matcher(this.prixMin);
                if(this.prixMin.length()==0||this.prixMin.length()>10||m.matches()==false) {
                    this.prixMin="";
                    session.setAttribute("prixMin", null);
                } else {
                    session.setAttribute("prixMin", this.prixMin);
                }
                m=p.matcher(this.prixMax);
                if(this.prixMax.length()==0||this.prixMax.length()>10||m.matches()==false) {
                    this.prixMax="";
                    session.setAttribute("prixMax", null);
                } else {
                    session.setAttribute("prixMax", this.prixMax);
                }
                break;
            case 2:
            case 3:
                p=Pattern.compile("[0-9]{1,10}");
                m=p.matcher(this.loyerMin);
                if(this.loyerMin.length()==0||this.loyerMin.length()>10||m.matches()==false) {
                    this.loyerMin="";
                    session.setAttribute("loyerMin", null);
                } else {
                    session.setAttribute("loyerMin", this.loyerMin);
                }
                m=p.matcher(this.loyerMax);
                if(this.loyerMax.length()==0||this.loyerMax.length()>10||m.matches()==false) {
                    this.loyerMax="";
                    session.setAttribute("loyerMax", null);
                } else {
                    session.setAttribute("loyerMax", this.loyerMax);
                }
                break;
        }
    }
    @Override
    public void initCondition() {
        super.initCondition();
        if(this.mode>0&&this.mode<=4)
            this.setCondition(" AND t1.mode='"+this.mode+"'");
        if(this.typeBien!=0)
            this.setCondition(" AND t1.type_bien='"+this.typeBien+"'");
        if(this.typeAppartement!=0)
            this.setCondition(" AND t1.type_appartement='"+this.typeAppartement+"'");
        if(this.surfaceMin.length()>0)
            this.setCondition(" AND CAST(t1.surface AS UNSIGNED)>='"+this.surfaceMin+"'");
        if(this.surfaceMax.length()>0)
            this.setCondition(" AND CAST(t1.surface AS UNSIGNED)<='"+this.surfaceMax+"'");
        if(this.meuble==1||this.meuble==2)
            this.setCondition(" AND t1.meuble='"+this.meuble+"'");
        if(this.nbPiecesMin.length()>0)
            this.setCondition(" AND CAST(t1.nb_pieces AS UNSIGNED)>='"+this.nbPiecesMin+"'");
        if(this.nbPiecesMax.length()>0)
            this.setCondition(" AND CAST(t1.nb_pieces AS UNSIGNED)<='"+this.nbPiecesMax+"'");
        if(this.nbEtagesMin.length()>0)
            this.setCondition(" AND CAST(t1.nb_etages AS UNSIGNED)>='"+this.nbEtagesMin+"'");
        if(this.nbEtagesMax.length()>0)
            this.setCondition(" AND CAST(t1.nb_etages AS UNSIGNED)<='"+this.nbEtagesMax+"'");
        if(this.viabilise==1||this.viabilise==2)
            this.setCondition(" AND t1.viabilise='"+this.viabilise+"'");
        if(this.constructible==1||this.constructible==2)
            this.setCondition(" AND t1.constructible='"+this.constructible+"'");
        if(this.prixMin.length()>0)
            this.setCondition(" AND CAST(t1.prix AS UNSIGNED)>='"+this.prixMin+"'");
        if(this.prixMax.length()>0)
            this.setCondition(" AND CAST(t1.prix AS UNSIGNED)<='"+this.prixMax+"'");
        if(this.loyerMin.length()>0)
            this.setCondition(" AND CAST(t1.loyer AS UNSIGNED)>='"+this.loyerMin+"'");
        if(this.loyerMax.length()>0)
            this.setCondition(" AND CAST(t1.loyer AS UNSIGNED)<='"+this.loyerMax+"'");
    }

    @Override
    public void initTags(HttpServletRequest request) {
        this.setTagTitle("Petites annonces immobilières");
        this.setTagDescription("Megannonce - Petites annonces immobilières gratuites");
        super.initTags(request);
        if(request.getParameter("mode")!=null) {
            switch(this.mode) {
                case 1:
                    this.setTagTitle("Petites annonces immobilières - Ventes");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Vantes.");
                    break;
                case 2:
                    this.setTagTitle("Petites annonces immobilières - Locations à l'année");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Locations à l'année.");
                    break;
                case 3:
                    this.setTagTitle("Petites annonces immobilières - Locations saisonnières");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Locations saisonnières.");
                    break;
                case 4:
                    this.setTagTitle("Petites annonces immobilières - Achats");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Achats.");
                    break;
            }
        } else if(request.getParameter("typeBien")!=null) {
            if(this.typeBien>0&&this.typeBien<=7) {
                String tb=Datas.arrayTypesBien[this.typeBien-1];
                this.setTagTitle("Petites annonces immobilières - "+tb);
                this.setTagDescription("Petites annonces immobilières gratuites - "+tb+".");
                }
            } else if(request.getParameter("typeAppartement")!=null) {
                if(this.typeAppartement!=0) {
                    String typeAppart=Datas.arrayTypesAppart[this.typeAppartement-1];
                    this.setTagTitle("Petites annonces immobilières - Appartements de type "+typeAppart);
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Appartements de type "+typeAppart+".");
                }
        } else if(request.getParameter("viabilise")!=null) {
            switch(this.viabilise) {
                case 1:
                    this.setTagTitle("Petites annonces immobilières - Terrains viabilisés");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Térrains viabilisés.");
                    break;
                case 2:
                    this.setTagTitle("Petites annonces immobilières - Terrains non-viabilisés");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Térrains non-viabilisés.");
                    break;
            }
        } else if(request.getParameter("constructible")!=null) {
            switch(this.constructible) {
                case 1:
                    this.setTagTitle("Petites annonces immobilières - Terrains constructibles");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Terrains constructibles.");
                    break;
                case 2:
                    this.setTagTitle("Petites annonces immobilières - Terrains non-constructibles");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Terrains non-constructibles.");
                    break;
            }
        } else if(request.getParameter("meuble")!=null) {
            switch(this.meuble) {
                case 1:
                    this.setTagTitle("Petites annonces immobilières - Biens meublés");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Biens meublés.");
                    break;
                case 2:
                    this.setTagTitle("Petites annonces immobilières - Biens non-meublés");
                    this.setTagDescription("Megannonce - Petites annonces immobilières gratuites - Biens non-meublés.");
                    break;
            }
        }
    }

    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @return the typeBien
     */
    public int getTypeBien() {
        return typeBien;
    }

    /**
     * @return the typeAppartement
     */
    public int getTypeAppartement() {
        return typeAppartement;
    }

    /**
     * @return the surfaceMin
     */
    public String getSurfaceMin() {
        return surfaceMin;
    }

    /**
     * @return the surfaceMax
     */
    public String getSurfaceMax() {
        return surfaceMax;
    }

    /**
     * @return the meuble
     */
    public int getMeuble() {
        return meuble;
    }

    /**
     * @return the nbPiecesMin
     */
    public String getNbPiecesMin() {
        return nbPiecesMin;
    }

    /**
     * @return the nbPiecesMax
     */
    public String getNbPiecesMax() {
        return nbPiecesMax;
    }

    /**
     * @return the nbEtagesMin
     */
    public String getNbEtagesMin() {
        return nbEtagesMin;
    }

    /**
     * @return the nbEtagesMax
     */
    public String getNbEtagesMax() {
        return nbEtagesMax;
    }

    /**
     * @return the viabilise
     */
    public int getViabilise() {
        return viabilise;
    }

    /**
     * @return the constructible
     */
    public int getConstructible() {
        return constructible;
    }

    /**
     * @return the prixMin
     */
    public String getPrixMin() {
        return prixMin;
    }

    /**
     * @return the prixMax
     */
    public String getPrixMax() {
        return prixMax;
    }

    /**
     * @return the loyerMin
     */
    public String getLoyerMin() {
        return loyerMin;
    }

    /**
     * @return the loyerMax
     */
    public String getLoyerMax() {
        return loyerMax;
    }

}
