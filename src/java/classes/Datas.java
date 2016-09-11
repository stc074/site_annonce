/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

/**
 *
 * @author pj
 */
public class Datas {
    public static final String EMAILADMIN="hardibopj@yahoo.fr";
    public static final String URLROOT="http://www.megannonce.net";
    public static final String DIR="/var/datas/mannonce/";
    public static final String TITRESITE="Megannonce";
    public static final String[] arrayChars={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    public static final String[] arrayReplace1={"%","=","\\^",":","/","&quot;","&","\\?"," ","é","è","ê","ë","à","ü","ô","ö","'","\\.","\""};
    public static final String[] arrayReplace2={"pourcent","","","","-","","et","-","-","e","e","e","e","a","u","o","o","-","","-"};
    public static final String[] arrayArticles={" le "," la "," les "," l'"," l' "," de "," du "," des "," d' "," d'"," à "," a "," au "," aux "," dans "," en "," par "," ou "};
    public static final String[] arrayTypesAnnonce={"Annonce classique", "Annonce automobile-véhicule", "Annonce immobilière", "Annonce emploi"};
    public static final int MAXUPLOADSIZE=10000000;
    public static final int MINI1LARG=100;
    public static final int MINI2HAUT=100;
    public static final int MAXHAUTPHOTO=600;
    public static final int MAXLARGPHOTO=800;
    public static final int NBANNONCESPAGE=10;
    public static final int NBANNONCESINDEX=3;
    public static final String[] arrayTypeVehicules={"Autre","Berline", "Coupée", "4x4", "Monospace", "Citadine", "Tunée", "Utilitaire", "Camping-Car", "Bus Autocar", "Moto", "Scooter", "Mobylette"};
    public static final String[] arrayTypesEnergie={"Super", "Essence", "Diésel", "GPL", "Électrique", "Hybride", "Autre"};
    public static final String[] arrayTypesMoto={"Autre", "Sportive","Routière", "Grand tourisme", "Roadster", "Custom", "Rétro", "Trail","Enduro","Trial", "Motocross", "Supermotards", "Pit Bike"};
    public static final String[] arrayTypesScooter={"Autre", "Urbain", "Grand Tourisme", "Sport", "Grandes Roues"};
    public static final String[] arrayTypesBien={"Appartement", "Maison", "Villa","Maison à rénover", "Ferme à rénover", "Immeuble", "Terrain"};
    public static final String[] arrayTypesAppart={"Autre", "Studio", "F1", "F2", "F3", "F4", "F5", "T1", "T2", "T3", "T4", "T5", "T1 Bis", "T2 Bis", "T3 Bis", "T4 Bis", "T5 Bis", "Chambre"};
    public static final String[] arrayTypesContrat={"Autre", "CAE", "CDD", "CDI", "CJE", "CNE", "Civis", "Contrat d'aprentissage", "Contrat de professionalisation", "Interim"};

 
    public int getArrayTypesContratLength() {
        return getArrayTypesContrat().length;
    }

    /**
     * @return the arrayTypesContrat
     */
    public String[] getArrayTypesContrat() {
        return arrayTypesContrat;
    }
    public String getURLROOT() {
        return URLROOT;
    }
}
