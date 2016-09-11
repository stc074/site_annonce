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
public class RechercheAuto extends Recherche {

    private int mode;
    private int typeVehicule;
    private int typeMoto;
    private int typeScooter;
    private String marque;
    private String modele;
    private int energie;
    private String annee;
    private String kilometrageMin;
    private String kilometrageMax;
    private String prixMin;
    private String prixMax;
    
    public RechercheAuto() {
        super();
        this.mode=0;
        this.typeVehicule=0;
        this.typeMoto=0;
        this.typeScooter=0;
        this.marque="";
        this.modele="";
        this.energie=0;
        this.annee="";
        this.kilometrageMin="";
        this.kilometrageMax="";
        this.prixMin="";
        this.prixMax="";
        this.setTagTitle("Petites annonces auto gratuites");
        this.setTagDescription("Toutes vos petites annonces auto gratuites sont sur Megannonce.");
    }
    @Override
    public void reset(HttpServletRequest request) {
        super.reset(request);
        HttpSession session=request.getSession(true);
        session.setAttribute("mode", null);
        session.setAttribute("typeVehicule", null);
        session.setAttribute("typeMoto", null);
        session.setAttribute("typeScooter", null);
        session.setAttribute("marque", null);
        session.setAttribute("modele", null);
        session.setAttribute("energie", null);
        session.setAttribute("annee", null);
        session.setAttribute("kilometrageMin", null);
        session.setAttribute("kilometrageMax", null);
        session.setAttribute("prixMin", null);
        session.setAttribute("prixMax", null);
    }

    @Override
    public void initValues(HttpServletRequest request) {
        super.initValues(request);
        HttpSession session=request.getSession(true);
        this.mode=0;
        if(session.getAttribute("mode")!=null) {
            this.mode=Integer.parseInt(session.getAttribute("mode").toString());
        }
        this.typeVehicule=0;
        if(session.getAttribute("typeVehicule")!=null) {
            this.typeVehicule=Integer.parseInt(session.getAttribute("typeVehicule").toString());
        }
        this.typeMoto=0;
        if(session.getAttribute("typeMoto")!=null) {
            this.typeMoto=Integer.parseInt(session.getAttribute("typeMoto").toString());
        }
        this.typeScooter=0;
        if(session.getAttribute("typeScooter")!=null) {
            this.typeScooter=Integer.parseInt(session.getAttribute("typeScooter").toString());
        }
        this.marque="";
        if(session.getAttribute("marque")!=null) {
            this.marque=Objet.codeHTML(session.getAttribute("marque").toString());
        }
        this.modele="";
        if(session.getAttribute("modele")!=null) {
            this.modele=Objet.codeHTML(session.getAttribute("modele").toString());
        }
        this.energie=0;
        if(session.getAttribute("energie")!=null) {
            this.energie=Integer.parseInt(session.getAttribute("energie").toString());
        }
        this.annee="";
        if(session.getAttribute("annee")!=null) {
            this.annee=Objet.codeHTML(session.getAttribute("annee").toString());
        }
        this.kilometrageMin="";
        if(session.getAttribute("kilometrageMin")!=null) {
            this.kilometrageMin=Objet.codeHTML(session.getAttribute("kilometrageMin").toString());
        }
        this.kilometrageMax="";
        if(session.getAttribute("kilometrageMax")!=null) {
            this.kilometrageMax=Objet.codeHTML(session.getAttribute("kilometrageMax").toString());
        }
        this.prixMin="";
        if(session.getAttribute("prixMin")!=null) {
            this.prixMin=Objet.codeHTML(session.getAttribute("prixMin").toString());
        }
        this.prixMax="";
        if(session.getAttribute("prixMax")!=null) {
            this.prixMax=Objet.codeHTML(session.getAttribute("prixMax").toString());
        }
    }
    @Override
    public void getGets(HttpServletRequest request) {
        super.getGets(request);
        HttpSession session=request.getSession(true);
        if(request.getParameter("mode")!=null) {
            this.mode=Integer.parseInt(request.getParameter("mode"));
            if(this.mode!=0) {
                session.setAttribute("mode", this.mode);
            }
            else {
                session.setAttribute("mode", null);
            }
            this.setPage(0);
            session.setAttribute("page", null);
        } else if(request.getParameter("typeVehicule")!=null) {
            this.typeVehicule=Integer.parseInt(request.getParameter("typeVehicule"));
            if(this.typeVehicule!=0) {
                session.setAttribute("typeVehicule", this.typeVehicule);
            }
            else {
                session.setAttribute("typeVehicule", null);
            }
            this.typeMoto=0;
            session.setAttribute("typeMoto", null);
            this.typeScooter=0;
            session.setAttribute("typeScooter", null);
            this.energie=0;
            session.setAttribute("energie", null);
            this.marque="";
            session.setAttribute("marque", null);
            this.modele="";
            session.setAttribute("modele", null);
            this.setPage(0);
            session.setAttribute("page", null);
       } else if(request.getParameter("typeMoto")!=null) {
            this.typeMoto=Integer.parseInt(request.getParameter("typeMoto"));
            if(this.typeMoto!=0) {
               session.setAttribute("typeMoto", this.typeMoto);
           }
            else {
               session.setAttribute("typeMoto", null);
           }
            this.typeScooter=0;
            session.setAttribute("typeScooter", null);
            this.energie=0;
            session.setAttribute("energie", null);
            this.marque="";
            session.setAttribute("marque", null);
            this.modele="";
            session.setAttribute("modele", null);
            this.setPage(0);
            session.setAttribute("page", null);
        } else if(request.getParameter("typeScooter")!=null) {
            this.typeScooter=Integer.parseInt(request.getParameter("typeScooter"));
            if(this.typeScooter!=0) {
                session.setAttribute("typeScooter", this.typeScooter);
            }
            else {
                session.setAttribute("typeScooter", null);
            }
            this.typeMoto=0;
            session.setAttribute("typeMoto", null);
            this.energie=0;
            session.setAttribute("energie", null);
            this.marque="";
            session.setAttribute("marque", null);
            this.modele="";
            session.setAttribute("modele", null);
            this.setPage(0);
            session.setAttribute("page", null);
        } else if(request.getParameter("energie")!=null) {
            this.energie=Integer.parseInt(request.getParameter("energie"));
            if(this.energie!=0) {
                session.setAttribute("energie", this.energie);
            }
            else {
                session.setAttribute("emnergie", null);
            }
            this.setPage(0);
            session.setAttribute("page", null);
        }
    }
    @Override
    public void getPosts(HttpServletRequest request) {
        super.getPosts(request);
        this.mode=Integer.parseInt(request.getParameter("mode"));
        this.typeVehicule=Integer.parseInt(request.getParameter("typeVehicule"));
        if(this.typeVehicule==11) {
            this.typeMoto=Integer.parseInt(request.getParameter("typeMoto"));
        } else if(this.typeVehicule==12) {
            this.typeScooter=Integer.parseInt(request.getParameter("typeScooter"));
        }
        this.marque=Objet.codeHTML(request.getParameter("marque"));
        this.modele=Objet.codeHTML(request.getParameter("modele"));
        if(this.typeVehicule>=1&&this.typeVehicule<=10) {
            this.energie=Integer.parseInt(request.getParameter("energie"));
        }
        this.annee=Objet.codeHTML(request.getParameter("annee"));
        this.kilometrageMin=Objet.codeHTML(request.getParameter("kilometrageMin"));
        this.kilometrageMax=Objet.codeHTML(request.getParameter("kilometrageMax"));
        this.prixMin=Objet.codeHTML(request.getParameter("prixMin"));
        this.prixMax=Objet.codeHTML(request.getParameter("prixMax"));
        this.prixMin=this.prixMin.replaceAll("\\.", ",");
        this.prixMax=this.prixMax.replaceAll("\\.", ",");
    }
    @Override
    public void verifPosts(HttpServletRequest request) {
        super.verifPosts(request);
        HttpSession session=request.getSession(true);
        if(this.mode!=0) {
            session.setAttribute("mode", this.mode);
        }
        else {
            session.setAttribute("mode", null);
        }
        if(this.typeVehicule!=0) {
            session.setAttribute("typeVehicule", this.typeVehicule);
        }
        else {
            session.setAttribute("typeVehicule", null);
        }
        if(this.typeVehicule==11&&this.typeMoto!=0) {
            session.setAttribute("typeMoto", this.typeMoto);
        }
        else if(this.typeVehicule==11&&this.typeMoto==0) {
            session.setAttribute("typeMoto", null);
        }
        if(this.typeVehicule==12&&this.typeScooter!=0) {
            session.setAttribute("typeScooter", this.typeScooter);
        }
        else if(this.typeVehicule==12&&this.typeScooter==0) {
            session.setAttribute("typeScooter", null);
        }
        if(this.marque.length()>0) {
            if(this.marque.length()>40) {
                this.marque="";
                session.setAttribute("marque", null);
            } else {
                session.setAttribute("marque", this.marque);
            }
        } else {
            session.setAttribute("marque", null);
        }
        if(this.modele.length()>0) {
            if(this.modele.length()>40) {
                this.modele="";
                session.setAttribute("modele", null);
            } else {
                session.setAttribute("modele", this.modele);
            }
        }
        else {
            session.setAttribute("modele", null);
        }
        if(this.typeVehicule>=1&&this.typeVehicule<=10&&this.energie!=0) {
            session.setAttribute("energie", this.energie);
        }
        else if(this.typeVehicule>=1&&this.typeVehicule<=10&&this.energie==0) {
            session.setAttribute("energie", null);
        }
        if(this.annee.length()>0) {
            Pattern p=Pattern.compile("[0-9]{4}");
            Matcher m=p.matcher(this.annee);
            if(m.matches()==false) {
                this.annee="";
                session.setAttribute("annee", null);
            } else {
                session.setAttribute("annee", this.annee);
            }
        } else {
            session.setAttribute("annee", null);
        }
        if(this.kilometrageMin.length()>0) {
            Pattern p=Pattern.compile("[0-9]{1,10}");
            Matcher m=p.matcher(this.kilometrageMin);
            if(this.kilometrageMin.length()>10||m.matches()==false) {
                this.kilometrageMin="";
                session.setAttribute("kilometrageMin", null);
            } else {
                session.setAttribute("kilometrageMin", this.kilometrageMin);
            }
        } else {
            session.setAttribute("kilometrageMin", null);
        }
        if(this.kilometrageMax.length()>0) {
            Pattern p=Pattern.compile("[0-9]{1,10}");
            Matcher m=p.matcher(this.kilometrageMax);
            if(this.kilometrageMax.length()>10||m.matches()==false) {
                this.kilometrageMax="";
                session.setAttribute("kilometrageMax", null);
            } else {
                session.setAttribute("kilometrageMax", this.kilometrageMax);
            }
        } else {
            session.setAttribute("kilometrageMax", null);
        }
        if(this.prixMin.length()>0) {
            Pattern p=Pattern.compile("[0-9]{0,10},{0,1}[0-9]{0,8}");
            Matcher m=p.matcher(this.prixMin);
            if(this.prixMin.length()>10||m.matches()==false) {
                this.prixMin="";
                session.setAttribute("prixMin", this.prixMin);
            } else {
                session.setAttribute("prixMin", this.prixMin);
            }
        } else {
            session.setAttribute("prixMin", null);
        }
        if(this.prixMax.length()>0) {
            Pattern p=Pattern.compile("[0-9]{0,10},{0,1}[0-9]{0,8}");
            Matcher m=p.matcher(this.prixMax);
            if(this.prixMax.length()>10||m.matches()==false) {
                this.prixMax="";
                session.setAttribute("prixMax", null);
            } else {
                session.setAttribute("prixMax", this.prixMax);
            }
        } else {
            session.setAttribute("prixMax", null);
        }
    }
    @Override
    public void initTags(HttpServletRequest request) {
        switch(this.typeVehicule) {
            case 11:
                this.setTagTitle("Petites annonces Moto");
                this.setTagDescription("Megannonce - Petites annonces Moto");
                break;
            case 12:
                this.setTagTitle("Petites annonces Scooter");
                this.setTagDescription("Megannonce - Petites annonces Scooter");
                break;
            case 13:
                this.setTagTitle("Petites annonces Mobylette");
                this.setTagDescription("Megannonce - Petites annonces Mobylette");
                break;
            default:
                this.setTagTitle("Petites annonces auto");
                this.setTagDescription("Megannonce - Petites annonces auto");
                break;
        }
        super.initTags(request);
        if(request.getParameter("mode")!=null&&this.mode!=0) {
            switch(this.mode) {
                case 1:
                    this.setTagTitle("Petites annonces auto - Ventes");
                    this.setTagDescription("Megannonce - Toutes les petites annonces auto - Ventes.");
                    break;
                case 2:
                    this.setTagTitle("Petites annonces auto - Achats");
                    this.setTagDescription("Megannonce - Toutes les petites annonces auto - Achats.");
                    break;
            }
        } else if(request.getParameter("typeVehicule")!=null&&this.typeVehicule!=0) {
            String type=Datas.arrayTypeVehicules[this.typeVehicule-1];
            this.setTagTitle("Petites annonces auto - "+type);
            this.setTagDescription("Megannonce - Petites annonces auto - "+type+".");
        } else if(this.typeVehicule==11&&request.getParameter("typeMoto")!=null&&this.typeMoto!=0) {
            String type=Datas.arrayTypesMoto[this.typeMoto-1];
            this.setTagTitle("Petites annonces moto - "+type);
            this.setTagDescription("Megannonce - Petites annonces moto - "+type+".");
        } else if(this.typeVehicule==12&&request.getParameter("typeScooter")!=null&&this.typeScooter!=0) {
            String type=Datas.arrayTypesScooter[this.typeScooter-1];
            this.setTagTitle("Petites annonces Scooter - "+type);
            this.setTagDescription("Megannonce - Petites annonces Scooter - "+type+".");
        } else if(request.getParameter("energie")!=null&&this.energie!=0) {
            String nrj=Datas.arrayTypesEnergie[this.energie-1];
            this.setTagTitle("Petites annonces auto - "+nrj);
            this.setTagDescription("Megannonce - petites annonces auto - "+nrj+".");
        }
    }

    @Override
    public void initCondition() {
        super.initCondition();
        if(this.mode!=0) {
            this.setCondition(" AND t1.mode='"+this.mode+"'");
        }
        if(this.typeVehicule!=0) {
            this.setCondition(" AND t1.type_vehicule='"+this.typeVehicule+"'");
            switch(this.typeVehicule) {
                case 11:
                    if(this.typeMoto!=0) {
                this.setCondition(" AND t1.type_moto='"+this.typeMoto+"'");
            }
                    break;
                case 12:
                    if(this.typeScooter!=0) {
                this.setCondition(" AND t1.type_scooter='"+this.typeScooter+"'");
            }
                    break;
            }
        }
        if(this.marque.length()>0) {
            this.setCondition(" AND t1.marque LIKE '%"+this.marque+"%'");
        }
        if(this.modele.length()>0) {
            this.setCondition(" AND t1.modele LIKE '%"+this.modele+"%'");
        }
        if(this.energie!=0) {
            this.setCondition(" AND energie='"+this.energie+"'");
        }
        if(this.annee.length()>0) {
            this.setCondition(" AND annee='"+this.annee+"'");
        }
        if(this.kilometrageMin.length()>0) {
            this.setCondition(" AND CAST(t1.kilometrage AS UNSIGNED)>='"+this.kilometrageMin+"'");
        }
        if(this.kilometrageMax.length()>0) {
            this.setCondition(" AND CAST(t1.kilometrage AS UNSIGNED)<='"+this.kilometrageMax+"'");
        }
        if(this.prixMin.length()>0) {
            String value=this.prixMin.replaceAll(",", "\\.");
            this.setCondition(" AND CAST(t1.prix AS UNSIGNED)>=CAST("+value+" AS UNSIGNED)");
        }
        if(this.prixMax.length()>0) {
            String value=this.prixMax.replaceAll(",", "\\.");
            this.setCondition(" AND CAST(t1.prix AS UNSIGNED)<=CAST("+value+" AS UNSIGNED)");
        }
    }
    /**
     * @return the mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * @return the typeVehicule
     */
    public int getTypeVehicule() {
        return typeVehicule;
    }

    /**
     * @return the typeMoto
     */
    public int getTypeMoto() {
        return typeMoto;
    }

    /**
     * @return the typeScooter
     */
    public int getTypeScooter() {
        return typeScooter;
    }

    /**
     * @return the marque
     */
    public String getMarque() {
        return marque;
    }

    /**
     * @return the modele
     */
    public String getModele() {
        return modele;
    }

    /**
     * @return the energie
     */
    public int getEnergie() {
        return energie;
    }

    /**
     * @return the kilometrageMin
     */
    public String getKilometrageMin() {
        return kilometrageMin;
    }

    /**
     * @return the kilometrageMax
     */
    public String getKilometrageMax() {
        return kilometrageMax;
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
     * @return the annee
     */
    public String getAnnee() {
        return annee;
    }
}
