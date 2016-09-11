<%@page import="java.util.Calendar"%>
<%@page import="java.io.File"%>
<%@page import="classes.Img"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.RechercheImmo"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%
try {
RechercheImmo recherche=null;
String tagTitle="Petites annonces immobilières gratuites";
String tagDescription="Megannonce - Site de petites annonces immobilières gratuites - Consultez ou déposez des annonces immobilières.";
if(request.getAttribute("recherche")!=null) {
    recherche=(RechercheImmo)request.getAttribute("recherche");
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
           <h1>Megannonce - Immobilier</h1>
           </header>
           <%
           if(recherche!=null) {
               Objet.getConnection();
               %>
               <div class="info">Pour affiner votre recherche, utilisez le formulaire ci-dessous :</div>
               <br/>
               <nav>
               <div id="form">
                   <fieldset>
                       <legend>Recherche</legend>
                       <form action="./petites-annonces-immobilieres-1.html#form" method="POST">
                           <p>
                               <label for="motsCles">Recherche :</label>
                               <input type="text" name="motsCles" id="motsCles" value="<%= recherche.getMotsCles()%>" size="40" maxlength="300" />
                               <br/>
                               <span>Je souhaite :</span>
                               <input type="radio" name="mode" id="mode1" value="1"<% if(recherche.getMode()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-1.html#form';" />
                               <label for="mode1">&rarr;Acheter&nbsp;</label>
                               <input type="radio" name="mode" id="mode2" value="2"<% if(recherche.getMode()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-2.html#form';" />
                               <label for="mode2">&rarr;Louer à l'année&nbsp;</label>
                               <input type="radio" name="mode" id="mode3" value="3"<% if(recherche.getMode()==3) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-3.html#form';" />
                               <label for="mode3">&rarr;Louer pour les vacances&nbsp;</label>
                               <input type="radio" name="mode" id="mode4" value="4"<% if(recherche.getMode()==4) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-4.html#form';" />
                               <label for="mode4">&rarr;Vendre&nbsp;</label>
                               <br/>
                               <label for="typeBien">Type de bien :</label>
                               <select name="typeBien" id="typeBien" onchange="javascript:window.location.href='./petites-annonces-immobilieres-3-'+this.value+'.html#form';">
                                   <option value="0"<% if(recherche.getTypeBien()==0) out.print(" selected=\"selected\""); %>>Tous</option>
                                   <%
                                   int i=0;
                                   for(String typeBien:Datas.arrayTypesBien) {
                                       i++;
                                       %>
                                       <option value="<%= i %>"<% if(recherche.getTypeBien()==i) out.print(" selected=\"selected\""); %>><%= typeBien %></option>
                                       <%
                                       }
                                   %>
                               </select>
                               <br/>
                                   <span>Localisation :</span>
                                   <label for="idRegion">Région :</label>
                                   <select name="idRegion" id="idRegion" onchange="javascript:window.location.href='./petites-annonces-immobilieres-8-'+this.value+'.html#form';">
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
                                   <label for="idDepartement">Département :</label>
                                   <select name="idDepartement" id="idDepartement" onchange="javascript:window.location.href='./petites-annonces-immobilieres-9-'+this.value+'.html#form';">
                                       <option value="0"<% if(recherche.getIdDepartement().equals("0")) out.print(" selected=\"selected\""); %>>Tous</option>
                                       <%
                                       if(!recherche.getIdRegion().equals("0")) {
                                           query="SELECT id_departement, departement FROM table_departements WHERE id_region=? ORDER BY id_departement ASC";
                                           PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                           prepare.setString(1, recherche.getIdRegion());
                                           result=prepare.executeQuery();
                                           while(result.next()) {
                                               String idDepartement=result.getString("id_departement");
                                               String departement=result.getString("departement");
                                               %>
                                               <option value="<%= idDepartement  %>"<% if(recherche.getIdDepartement().equals(idDepartement)) out.print(" selected=\"selected\""); %>><%= idDepartement %>-<%= departement %></option>
                                               <%
                                               }
                                           result.close();
                                           prepare.close();
                                           }
                                       %>
                                   </select>
                                   <label for="idCommune">Commune :</label>
                                   <select name="idCommune" id="idCommune" onchange="javascript:window.location.href='./petites-annonces-immobilieres-10-'+this.value+'.html#form';">
                                       <option value="0"<% if(recherche.getIdCommune()==0) out.print(" selected=\"selected\""); %>>Toutes</option>
                                       <%
                                       if((!recherche.getIdRegion().equals("0"))&&(!recherche.getIdDepartement().equals("0"))) {
                                           query="SELECT id,commune, code_postal FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune ASC";
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
                                   <br/>
                               <%
                               if(recherche.getMode()!=0&&recherche.getTypeBien()!=0) {
                                   String label1="",label2="";
                                   switch(recherche.getMode()) {
                                       case 1:
                                           label1=" à acheter";
                                           label2=" de votre achat";
                                           break;
                                       case 2:
                                       case 3:
                                           label1=" à louer";
                                           label2=" de la location";
                                           break;
                                       case 4:
                                           label1=" à vendre";
                                           label2=" de votre vente";
                                           break;
                                           }
                                   switch(recherche.getTypeBien()) {
                                       case 1: %>
                                       <label for="typeAppartement">Type d'appartement<%= label1 %> :</label>
                                       <select name="typeAppartement" id="typeAppartement" onchange="javascript:window.location.href='./petites-annonces-immobilieres-4-'+this.value+'.html#form';">
                                           <option value="0"<% if(recherche.getTypeAppartement()==0) out.print(" selected=\"selected\""); %>>Tous</option>
                                           <%
                                           i=0;
                                           for(String typeAppartement:Datas.arrayTypesAppart) {
                                               i++;
                                               %>
                                               <option value="<%= i %>"<% if(recherche.getTypeAppartement()==i) out.print(" selected=\"selected\""); %>><%= typeAppartement %></option>
                                               <%
                                               }
                                           %>
                                       </select>
                                       <br/>
                                       <%
                                       break;
                                       }
                                   %>
                                   <label for="surfaceMin">Surface minimum du bien<%= label1 %> :</label>
                                   <input type="text" name="surfaceMin" id="surfaceMin" value="<%= recherche.getSurfaceMin()%>" size="6" maxlength="10" />
                                   <span>m<sup>2</sup>&nbsp;</span>
                                   <label for="surfaceMax">Surface maximum du bien<%= label1 %> :</label>
                                   <input type="text" name="surfaceMax" id="surfaceMax" value="<%= recherche.getSurfaceMax() %>" size="6" maxlength="10" />
                                   <span>m<sup>2</sup></span>
                                   <br/>
                                   <%
                                   switch(recherche.getTypeBien()) {
                                       case 1:
                                       case 2:
                                       case 3: %>
                                       <span>Le bien<%= label1 %> est-il meublé ?</span>
                                       <input type="radio" name="meuble" id="meuble1" value="1"<% if(recherche.getMeuble()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-5-1.html#form';" />
                                       <label for="meuble1">&rarr;OUI&nbsp;</label>
                                       <input type="radio" name="meuble" id="meuble2" value="2"<% if(recherche.getMeuble()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-5-2.html#form';" />
                                       <label for="meuble2">&rarr;NON&nbsp;</label>
                                       <br/>
                                       <%
                                       break;
                                       }
                                   switch(recherche.getTypeBien()) {
                                       case 1:
                                       case 2:
                                       case 3:
                                       case 4:
                                       case 5: %>
                                       <label for="nbPiecesMin">Nombre de pièces mini :</label>
                                       <input type="text" name="nbPiecesMin" id="nbPiecesMin" value="<%= recherche.getNbPiecesMin()%>" size="6" maxlength="10" />
                                       <label for="nbPiecesMax">Nombre de pièces maxi :</label>
                                       <input type="text" name="nbPiecesMax" id="nbPiecesMax" value="<%= recherche.getNbPiecesMax()%>" size="6" maxlength="10" />
                                       <br/>
                                       <%
                                       break;
                                       case 6: %>
                                       <label for="nbEtagesMin">Nombre d'étages mini :</label>
                                       <input type="text" name="nbEtagesMin" id="nbEtagesMin" value="<%= recherche.getNbEtagesMin()%>" size="6" maxlength="10" />
                                       <label for="nbEtagesMax">Nombre d'étages maxi :</label>
                                       <input type="text" name="nbEtagesMax" id="nbEtagesMax" value="<%= recherche.getNbEtagesMax()%>" size="6" maxlength="10" />
                                       <br/>
                                       <%
                                       break;
                                       case 7: %>
                                       <span>Terrain viabilisé ?</span>
                                       <input type="radio" name="viabilise" id="viabilise1" value="1"<% if(recherche.getViabilise()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-6-1.html#form';" />
                                       <label for="viabilise1">&rarr;OUI&nbsp;</label>
                                       <input type="radio" name="viabilise" id="viabilise2" value="2"<% if(recherche.getViabilise()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-immobilieres-6-2.html#form';" />
                                       <label for="viabilise2">&rarr;NON&nbsp;</label>
                                       <br/>
                                       <span>Terrain constructible ?</span>
                                       <input type="radio" name="constructible" id="constructible1" value="1"<% if(recherche.getConstructible()==1) out.print(" checked=\"checked\""); %> onclick="javascipt:window.location.href='./petites-annonces-immobilieres-7-1.html#form';" />
                                       <label for="constructible1">&rarr;OUI&nbsp;</label>
                                       <input type="radio" name="constructible" id="constructible2" value="2"<% if(recherche.getConstructible()==2) out.print(" checked=\"checked\""); %> onclick="javascipt:window.location.href='./petites-annonces-immobilieres-7-2.html#form';" />
                                       <label for="constructible2">&rarr;NON&nbsp;</label>
                                       <br/>
                                       <%
                                       break;
                                       }
                                   switch(recherche.getMode()) {
                                       case 1:
                                       case 4: %>
                                       <label for="prixMin">Prix mini<%= label2 %> :</label>
                                       <input type="text" name="prixMin" id="prixMin" value="<%= recherche.getPrixMin()%>" size="6" maxlength="10" />
                                       <span>&euro;</span>
                                       <label for="prixMax">Prix maxi<%= label2 %> :</label>
                                       <input type="text" name="prixMax" id="prixMax" value="<%= recherche.getPrixMax()%>" size="6" maxlength="10" />
                                       <span>&euro;</span>
                                       <br/>
                                       <%
                                       break;
                                       case 2: %>
                                       <span>Loyer mensuel &rarr; </span>
                                       <label for="loyerMin">Minimum :</label>
                                       <input type="text" name="loyerMin" id="loyerMin" value="<%= recherche.getLoyerMin()%>" size="6" maxlength="10" />
                                       <span>&euro;&nbsp;</span>
                                       <label for="loyerMax">Maximum :</label>
                                       <input type="text" name="loyerMax" id="loyerMax" value="<%= recherche.getLoyerMax()%>" size="6" maxlength="10" />
                                       <span>&euro;</span>
                                       <br/>
                                       <%
                                       break;
                                       case 3: %>
                                       <span>Loyer à la semaine &rarr; </span>
                                       <label for="loyerMin">Minimum :</label>
                                       <input type="text" name="loyerMin" id="loyerMin" value="<%= recherche.getLoyerMin()%>" size="6" maxlength="10" />
                                       <span>&euro;&nbsp;</span>
                                       <label for="loyerMax">Maximum :</label>
                                       <input type="text" name="loyerMax" id="loyerMax" value="<%= recherche.getLoyerMax()%>" size="6" maxlength="10" />
                                       <span>&euro;</span>
                                       <br/>
                                       <%
                                       break;
                                       }
                                   %>
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
                                  query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces_immobilier AS t1,table_membres AS t2 "+recherche.getCondition()+" AND t2.id=t1.id_membre";
                                   state=Objet.getConn().createStatement();
                                   result=state.executeQuery(query);
                                   result.next();
                                   int nbAnnonces=result.getInt("nbAnnonces");
                                   result.close();
                                   state.close();
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
                                       int nbPages=(int)(Math.ceil(((double)nbAnnonces)/((double)Datas.NBANNONCESPAGE)));
                                       int nbAnnoncesPage=0;
                                       if(nbAnnonces<=Datas.NBANNONCESPAGE)
                                           nbAnnoncesPage=nbAnnonces;
                                       else if((recherche.getPage()+1)<nbPages)
                                           nbAnnoncesPage=Datas.NBANNONCESPAGE;
                                       else if((recherche.getPage()+1)==nbPages)
                                           nbAnnoncesPage=nbAnnonces-(Datas.NBANNONCESPAGE*recherche.getPage());
                                       query="SELECT t1.id,t1.mode,t1.type_bien,t1.type_appartement,t1.surface,t1.prix,t1.loyer,t1.charges,t1.titre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.region,t4.departement,t5.commune,t5.code_postal FROM table_annonces_immobilier AS t1,table_membres AS t2,table_regions AS t3,table_departements AS t4,table_communes AS t5"+recherche.getCondition()+" AND t2.id=t1.id_membre AND t3.id_region=t2.id_region AND t4.id_departement=t2.id_departement AND t5.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(recherche.getPage()*Datas.NBANNONCESPAGE)+","+Datas.NBANNONCESPAGE;
                                       state=Objet.getConn().createStatement();
                                       result=state.executeQuery(query);
                                       int nb=0;
                                       Img img=new Img();
                                       File fileMini1=null;
                                       Calendar cal=Calendar.getInstance();
                                       int j=0;
                                       while(result.next()) {
                                           nb++;
                                           long idAnnonce=result.getLong("id");
                                           int mode=result.getInt("mode");
                                           int typeBien=result.getInt("type_bien");
                                           int typeAppartement=result.getInt("type_appartement");
                                           String surface=result.getString("surface");
                                           String prix=result.getString("prix");
                                           String loyer=result.getString("loyer");
                                           String charges=result.getString("charges");
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
                                           String uri="./petite-annonce-immobiliere-"+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
                             %>
                                           <div class="listeAnnonce" onclick="javascript:window.location.href='<%= uri %>#annonce';">
                                               <div class="listeAnnonceGauche">
                                                   <%
                                                   int nbPhotos=0;
                                                   for(i=1;i<=5;i++) {
                                                       String extension=extensions[i-1];
                                                       if(extension.length()>0&&nbPhotos==0) {
                                                           String filenameMini1=Datas.DIR+"photos/mini1_3_"+idAnnonce+"_"+i+extension;
                                                           fileMini1=new File(filenameMini1);
                                                           if(fileMini1.exists()) {
                                                               nbPhotos++;
                                                               img.getSize(fileMini1);
                                                               int largeur=img.getWidth();
                                                               int hauteur=img.getHeight();
                                                               %>
                                                               <img src="./photo-mini-1-3-<%= idAnnonce%>-<%= i%><%= extension%>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
                                                               <%
                                                               }
                                                           }
                                                       }
                                                   if(nbPhotos==0) { %>
                                                   <img src="./GFXs/miniature.png" width="100" height="100" alt="miniature"/>
                                                   <%
                                                   }
                                                   %>
                                               </div>
                                               <div class="listeAnnonceDroite">
                                                   <h1>
                                                       <a href="<%= uri %>#annonce" title="<%= titre %>"><%= titre %></a>
                                                   </h1>
                                                       <p>
                                                           <strong>Annonce</strong> déposée, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %> par <%= pseudo %>.
                                                           <br/>
                                                       <%
                                                       switch(mode) {
                                                           case 1: %>
                                                           <span>Vente </span>
                                                           <%
                                                           break;
                                                           case 2: %>
                                                           <span>Location </span>
                                                           <%
                                                           break;
                                                           case 3: %>
                                                           <span>Location saisonnière </span>
                                                           <%
                                                           break;
                                                           case 4: %>
                                                           <span>Achat </span>
                                                           <%
                                                           break;
                                                           }
                                                       switch(typeBien) {
                                                           case 1: %>
                                                           <span>appartement de type <%= Datas.arrayTypesAppart[typeAppartement-1] %>.</span>
                                                           <%
                                                           break;
                                                           case 7: %>
                                                           <span>terrain d'une surface de <%= surface %> m<sup>2</sup>.</span>
                                                           <%
                                                           break;
                                                           default: %>
                                                           <span><%= Datas.arrayTypesBien[typeBien-1] %>.</span>
                                                           <%
                                                           break;
                                                           }
                                                       %>
                                                       <br/>
                                                       <%
                                                       switch(mode) {
                                                           case 1: %>
                                                           <span>Prix à la vente : <%= prix %>&euro;.</span>
                                                           <%
                                                           break;
                                                           case 2: %>
                                                           <span>Loyer mensuel : <%= loyer %>&euro; (+<%= charges %> &euro; de charges).</span>
                                                           <%
                                                           break;
                                                           case 3: %>
                                                           <span>Loyer à la semaine : <%= loyer %> &euro;.</span>
                                                           <%
                                                           break;
                                                           case 4: %>
                                                           <span>Prix à l'achat de <%= prix %> &euro;.</span>
                                                           <%
                                                           break;
                                                           }
                                                       %>
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
                                       }
                                       %>
                                       <div class="pages">
                                           <span>Pages d'annonces : </span>
                                           <%
                                           int prem=recherche.getPage()-5;
                                           if(prem<0)
                                               prem=0;
                                           int der=recherche.getPage()+5;
                                           if(der>=nbPages)
                                               der=nbPages-1;
                                           for(i=prem;i<=der;i++) {
                                               if(i==recherche.getPage()) { %>
                                               <span>[<span class="clign"><%= i+1 %></span>]</span>
                                               <% } else { %>
                                               <span>[<a href="./petites-annonces-immobilieres-11-<%= i %>.html" title="PAGE #<%= i+1 %>"><%= i+1 %></a>]</span>
                                               <%
                                               }
                                               }
                                           %>
                                       </div>
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