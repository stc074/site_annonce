<%@page import="java.io.File"%>
<%@page import="classes.Img"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.RechercheAuto"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%
try {
RechercheAuto recherche=null;
String tagTitle="Megannonce - Petites annonces auto gratuites";
String tagDescription="Megannonce - Site de petites annonces auto gratuites - Consultez ou déposez des annonces auto.";
if(request.getAttribute("recherche")!=null) {
    recherche=(RechercheAuto)request.getAttribute("recherche");
    tagTitle=recherche.getTagTitle();
    tagDescription=recherche.getTagDescription();
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title><%= tagTitle %></title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="<%= tagDescription %>" />
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
</head>
    <body>
<div id="fb-root"></div>
<script>
    (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/fr_FR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
       <%@include file="./haut.jsp" %>
       <section>
           <header>
           <h1>Megannonce automobiles</h1>
           <%
           if(recherche!=null) {
               Objet.getConnection();
               int i=0;
               %>
           <div class="info">Pour affiner votre recherche, utilisez le formulaire ci-dessous :</div>
           <br/>
           </header>
           <nav>
           <div id="form">
               <fieldset>
                   <legend>Recherche</legend>
                   <form action="./petites-annonces-auto-1.html#form" method="POST">
                       <p>
                           <div>
                               <label for="motsCles">Recherche : </label>
                               <input type="text" name="motsCles" id="motsCles" value="<%= recherche.getMotsCles()%>" size="30" maxlength="300" />
                           </div>
                           <div>
                           <span>Je souhaite : </span>
                           <input type="radio" name="mode" id="mode1" value="1"<% if(recherche.getMode()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-auto-2-1.html#form';" />
                           <label for="mode1">&rarr;Acheter&nbsp;</label>
                           <input type="radio" name="mode" id="mode2" value="2"<% if(recherche.getMode()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-auto-2-2.html#form';" />
                           <label for="mode2">&rarr;Vendre&nbsp;</label>
                           </div>
                           <div>
                               <span>Type de véhicule : </span>
                               <%
                               i=0;
                               for(String typeVehicule:Datas.arrayTypeVehicules) {
                                   i++;
                                   %>
                                   <input type="radio" name="typeVehicule" id="typeVehicule<%= i %>" value="<%= i%>"<% if(recherche.getTypeVehicule()==i) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-auto-3-<%= i %>.html#form';" />
                                   <label for="typeVehicule<%= i %>">&rarr;<%= typeVehicule %></label>
                                   <%
                                   }
                               %>
                           </div>
                           <%
                           if(recherche.getTypeVehicule()!=0) {
                               switch(recherche.getTypeVehicule()) {
                                   case 11: %>
                                   <div>
                                       <label for="typeMoto">Type de moto : </label>
                                       <select name="typeMoto" id="typeMoto" onchange="javascript:window.location.href='./petites-annonces-auto-4-'+this.value+'.html#form';">
                                           <option value="0"<% if(recherche.getTypeMoto()==0) out.print(" selected=\"selected\""); %>>Tous</option>
                                           <%
                                           i=0;
                                           for(String typeMoto:Datas.arrayTypesMoto) {
                                               i++;
                                               %>
                                               <option value="<%= i %>"<% if(recherche.getTypeMoto()==i) out.print(" selected=\"selected\""); %>><%= typeMoto %></option>
                                               <%
                                               }
                                           %>
                                       </select>
                                   </div>
                                           <%
                                           break;
                                           case 12: %>
                                           <div>
                                               <label for="typeScooter">Type de scooter : </label>
                                               <select name="typeScooter" id="typeScooter" onchange="javascript:window.location.href='./petites-annonces-auto-5-'+this.value+'.html#form';">
                                                   <option value="0"<% if(recherche.getTypeScooter()==0) out.print(" selected=\"selected\""); %>>Tous</option>
                                                   <%
                                                   i=0;
                                                   for(String typeScooter:Datas.arrayTypesScooter) {
                                                       i++;
                                                       %>
                                                       <option value="<%= i %>"<% if(recherche.getTypeScooter()==i) out.print(" selected=\"selected\""); %>><%= typeScooter %></option>
                                                       <%
                                                       }
                                                   %>
                                               </select>
                                           </div>
                                                   <%
                                                   break;
                                           }
                               %>
                               <div>
                                   <label for="marque">Marque : </label>
                                   <input type="text" name="marque" id="marque" value="<%= recherche.getMarque()%>" size="25" maxlength="40" />
                                   <label for="modele">Modele : </label>
                                   <input type="text" name="modele" id="modele" value="<%= recherche.getModele()%>" size="25" maxlength="40" />
                               </div>
                               <%
                               switch(recherche.getTypeVehicule()) {
                                   case 1:
                                   case 2:
                                   case 3:
                                   case 4:
                                   case 5:
                                   case 6:
                                   case 7:
                                   case 8:
                                   case 9:
                                   case 10: %>
                                   <div>
                                   <label for="energie">Énergie : </label>
                                   <select name="energie" id="energie" onchange="javascript:window.location.href='./petites-annonces-auto-6-'+this.value+'.html#form';">
                                       <option value="0"<% if(recherche.getEnergie()==0) out.print(" selected=\"selected\""); %>>Toutes</option>
                                       <%
                                       i=0;
                                       for(String energie:Datas.arrayTypesEnergie) {
                                           i++;
                                           %>
                                           <option value="<%= i %>"<% if(recherche.getEnergie()==i) out.print(" selected=\"selected\""); %>><%= energie %></option>
                                           <%
                                           }
                                       %>
                                   </select>
                                   </div>
                                       <%
                                       break;
                                       }
                               %>
                               <div>
                                   <label for="annee">Année de mise en circulation :</label>
                                   <input type="text" name="annee" id="annee" value="<%= recherche.getAnnee()%>" size="4" maxlength="4" />
                               </div>
                               <div>
                                   <label for="kilometrageMin">Kilométrage mini : </label>
                                   <input type="text" name="kilometrageMin" id="kilometrageMin" value="<%= recherche.getKilometrageMin()%>" size="6" maxlength="10" />
                                   <span>Km</span>
                                   <label for="kilometrageMax">&nbsp;Kilométrage maxi : </label>
                                   <input type="text" name="kilometrageMax" id="kilometrageMax" value="<%= recherche.getKilometrageMax()%>" size="6" maxlength="10" />
                                   <span>Km</span>
                               </div>
                               <div>
                                   <label for="prixMin">Prix mini : </label>
                                   <input type="text" name="prixMin" id="prixMin" value="<%= recherche.getPrixMin()%>" size="6" maxlength="10" />
                                   <span>&euro;</span>
                                   <label for="prixMax">&nbsp;Prix maxi : </label>
                                   <input type="text" name="prixMax" id="prixMax" value="<%= recherche.getPrixMax()%>" size="6" maxlength="10" />
                                   <span>&euro;</span>
                               </div>
                                   <div>
                                       <span>Localisation : </span>
                                       <label for="idRegion">Région : </label>
                                       <select name="idRegion" id="idRegion" onchange="javascript:window.location.href='./petites-annonces-auto-7-'+this.value+'.html#form';">
                                           <option value="0"<% if(recherche.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Toutes</option>
                                           <%
                                           String query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
                                           Statement state=Objet.getConn().createStatement();
                                           ResultSet result=state.executeQuery(query);
                                           while(result.next()) {
                                               String idRegion=result.getString("id_region");
                                               String region=result.getString("region");
                                               %>
                                               <option value="<%= idRegion %>"<% if(recherche.getIdRegion().equals(idRegion)) out.print(" selected=\"selected\""); %>><%= region %></option>
                                               <%
                                               }
                                           result.close();
                                           state.close();
                                           %>
                                       </select>
                                       <label for="idDepartement">&nbsp;Département : </label>
                                       <select name="idDepartement" id="idDepartement" onchange="javascript: window.location.href='./petites-annonces-auto-8-'+this.value+'.html#form';">
                                           <option value="0"<% if(recherche.getIdDepartement().equals("0")) out.print(" selected=\"selected\""); %>>Tous</option>
                                           <%
                                           if(!recherche.getIdRegion().equals("0")) {
                                               query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement ASC";
                                               PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                               prepare.setString(1, recherche.getIdRegion());
                                               result=prepare.executeQuery();
                                               while(result.next()) {
                                                   String idDepartement=result.getString("id_departement");
                                                   String departement=result.getString("departement");
                                                   %>
                                                   <option value="<%= idDepartement %>"<% if(recherche.getIdDepartement().equals(idDepartement)) out.print(" selected=\"selected\""); %>><%= idDepartement %>-<%= departement %></option>
                                                   <%
                                                   }
                                               result.close();
                                               prepare.close();
                                               }
                                           %>
                                       </select>
                                       <label for="idCommune">&nbsp;Commune : </label>
                                       <select name="idCommune" id="idCommune" onchange="javascript:window.location.href='./petites-annonces-auto-9-'+this.value+'.html#form';">
                                           <option value="0"<% if(recherche.getIdCommune()==0) out.print(" selected=\"selected\""); %>>Toutes</option>
                                           <%
                                           if((!recherche.getIdRegion().equals("0"))&&(!recherche.getIdDepartement().equals("0"))) {
                                               query="SELECT id,commune,code_postal FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune ASC";
                                               PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                               prepare.setString(1, recherche.getIdRegion());
                                               prepare.setString(2, recherche.getIdDepartement());
                                               result=prepare.executeQuery();
                                               while(result.next()) {
                                                   int idCommune=result.getInt("id");
                                                   String commune=result.getString("commune");
                                                   String codePostal=result.getString("code_postal");
                                                   %>
                                                   <option value="<%= idCommune %>"<% if(recherche.getIdCommune()==idCommune) out.print(" selected=\"selected\""); %>><%= codePostal %>-<%= commune %></option>
                                                   <%
                                                   }
                                               result.close();
                                               prepare.close();
                                               }
                                           %>
                                       </select>
                                   </div>
                                       <input type="submit" value="Rechercher" name="kermit" />
                                       <input type="submit" value="Vider le formulaire" name="reset" />
                                   <%
                               }
                               %>
                       </p>
                   </form>
               </fieldset>
           </div>
           </nav>
           <%
           String query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces_automobile AS t1,table_membres AS t2"+recherche.getCondition()+" AND t2.id=t1.id_membre";
           //out.println(query);
           Statement state=Objet.getConn().createStatement();
           ResultSet result=state.executeQuery(query);
           result.next();
           int nbAnnonces=result.getInt("nbAnnonces");
           if(nbAnnonces==0) { %>
           <br/>
           <ul class="reseauxSoc">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
               <li>
                   <div class="fb-like" data-send="true" data-layout="button_count" data-width="450" data-show-faces="true"></div>
               </li>
           </ul>
           <p></p>
           <div class="cadre2">
               <div class="info">Désolé, aucune annonce pour cette recherche.</div>
           </div>
                                                  <br/>
                                                   <div class="listeAnnoncePub2">
                                              <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>                          
                                                   </div>
           <br/>
           <%
           } else {
           int nbPages=(int)(Math.ceil(((double)nbAnnonces)/((double)(Datas.NBANNONCESPAGE))));
           //out.println(nbPages);
           int nbAnnoncesPage=0;
           if(nbAnnonces<Datas.NBANNONCESPAGE)
               nbAnnoncesPage=nbAnnonces;
           else if((recherche.getPage()+1)<nbPages)
               nbAnnoncesPage=Datas.NBANNONCESPAGE;
           else if((recherche.getPage()+1)==nbPages)
               nbAnnoncesPage=nbAnnonces-((nbPages-1)*Datas.NBANNONCESPAGE);
           //out.println(nbAnnoncesPage+"-"+Datas.NBANNONCESPAGE);
    %>
           <h2><%= nbAnnonces %> annonce(s) pour cette recherche</h2>
                                                       <p></p>
           <ul class="reseauxSoc">
                <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
               <li>
                   <div class="fb-like" data-send="true" data-layout="button_count" data-width="450" data-show-faces="true"></div>
               </li>
           </ul>
           <br/>
          <div class="listeAnnoncePub">
<script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>                                                 
           </div>
           <br/>
           <%
           query="SELECT t1.id,t1.mode,t1.type_vehicule,t1.type_moto,t1.type_scooter,t1.marque,t1.modele,t1.annee,t1.prix,t1.titre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.commune,t5.code_postal FROM table_annonces_automobile AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5"+recherche.getCondition()+" AND t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(recherche.getPage()*Datas.NBANNONCESPAGE)+","+nbAnnoncesPage;
           state=Objet.getConn().createStatement();
           result=state.executeQuery(query);
           int nb=0;
           Calendar cal=Calendar.getInstance();
           Img img=new Img();
           int j=0;
           while(result.next()) {
    if(recherche.getNbAnnoncesPage()>5&&j==Math.floor(nbAnnoncesPage/2)) { %>
          <div class="listeAnnoncePub">
<script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>                                                 
           </div>
           <br/>
           <%
                     }
               j++;
           nb++;
               long idAnnonce=result.getLong("id");
               int mode=result.getInt("mode");
               int typeVehicule=result.getInt("type_vehicule");
               int typeMoto=result.getInt("type_moto");
               int typeScooter=result.getInt("type_scooter");
               String marque=result.getString("marque");
               String modele=result.getString("modele");
               String annee=result.getString("annee");
               String prix=result.getString("prix");
               String titre=result.getString("titre");
               String extensions[]=new String[5];
               extensions[0]=result.getString("extension1");
               extensions[1]=result.getString("extension2");
               extensions[2]=result.getString("extension3");
               extensions[3]=result.getString("extension4");
               extensions[4]=result.getString("extension5");
               long timestamp=result.getLong("timestamp");
               String pseudo=result.getString("pseudo");
               String region=result.getString("region");
               String departement=result.getString("departement");
               String commune=result.getString("commune");
               String codePostal=result.getString("code_postal");
               cal.setTimeInMillis(timestamp);
               String uri="./petite-annonce-auto-"+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
               %>
               <div class="listeAnnonce" onclick="javascript:window.location.href='<%= uri %>#annonce';">
                   <div class="listeAnnonceGauche">
                       <%
                       int nbPhotos=0;
                       for(i=1;i<=5;i++) {
                           String extension=extensions[i-1];
                           if(extension.length()>0&&nbPhotos==0) {
                               String filenameMini1=Datas.DIR+"photos/mini1_2_"+idAnnonce+"_"+i+extension;
                               File fileMini1=new File(filenameMini1);
                               if(fileMini1.exists()) {
                                   nbPhotos++;
                                   img.getSize(fileMini1);
                                   int largeur=img.getWidth();
                                   int hauteur=img.getHeight();
                                   %>
                                   <img src="./photo-mini-1-2-<%= idAnnonce%>-<%= i%><%= extension%>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
                                   <%
                                   }
                               }
                           }
                       if(nbPhotos==0) { %>
                       <img src="./GFXs/miniature.png" width="100" height="100" alt="miniature"/>
                       <% } %>
                   </div>
                   <div class="listeAnnonceDroite">
                       <h1>
                           <a href="<%= uri %>#annonce" title="<%= titre %>"><%= titre %></a>
                       </h1>
                       <p>
                           <%
                           switch(mode) {
                               case 1: %>
                               <span>Vends </span>
                               <%
                               break;
                               case 2: %>
                               <span>Achète </span>
                               <%
                               break;
                               }
                           %>
                           <span><%= Datas.arrayTypeVehicules[typeVehicule-1] %></span>
                           <span>&nbsp;<%= modele %>-<%= marque %></span>
                           <span>&nbsp;de <%= annee %></span>
                           <%
                           switch(typeVehicule) {
                               case 11: %>
                               <span>&nbsp;de type <%= Datas.arrayTypesMoto[typeMoto-1] %></span>
                               <%
                               break;
                               case 12: %>
                               <span>&nbsp;de type <%= Datas.arrayTypesScooter[typeScooter-1] %></span>
                               <%
                               break;
                               }
                           switch(mode) {
                               case 1: %>
                               <span>&nbsp;à <%= prix %>&euro;.</span>
                               <%
                               break;
                               case 2: %>
                               <span>&nbsp;pour <%= prix %>&euro;.</span>
                               <%
                               break;
                               }
                           %>
                           <br/>
                           Annonce déposée par <%= pseudo %>, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.
                           <br/>
                           <br/>
                           Localisation : <%= codePostal %>-<%= commune %>|<%= region %>-<%= departement %>.
                           <br/>
                       </p>
                   </div>
               </div>
                   <br/>
                   <%
           }
           result.close();
           state.close();
           if(nb==0) { %>
           <br/>
           <div class="cadre2">
               <div class="info">Aucune <strong>annonce</strong>.</div>
           </div>
                                                   <br/>
                                                   <div class="listeAnnoncePub2">
                                              <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>                          
                                                   </div>
          <br/>
           <%
           } else {
           int prem=recherche.getPage()-5;
           if(prem<0)
               prem=0;
           int der=recherche.getPage()+5;
           if(der>=nbPages)
               der=nbPages-1;
           %>
           <div class="pages">
               <span>Pages d'annonces : </span>
               <%
               for(i=prem;i<=der;i++) {
                   if(i==recherche.getPage()) { %>
                   <span>[<span class="clign"><%= (i+1) %></span>]</span>
                   <%
                   } else { %>
                   <span>[<a href="./petites-annonces-auto-10-<%= i %>.html" title="PAGE #<%= (i+1) %>"><%= (i+1) %></a>]</span>
                   <%
                   }
                   }
           }
           %>
           </div>
           <p></p>
          <div class="listeAnnoncePub">
<script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "2251233682";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>                                                 
           </div>
           <br/>
           <%
           }
           Objet.closeConnection();
               }
%>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
       <%
       } catch(Exception ex) { %>
       <html>
           <head>
               <title>Erreur !</title>
           </head>
           <body>
               <div class="contenu">
                   <div class="erreur">
                       <div>Erreur :</div>
                       <br/>
                       <div><%= ex.getMessage() %></div>
                   </div>
               </div>
           </body>
       </html>
<% } %>