<%@page import="classes.Img"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.RechercheNormale"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%
try {
RechercheNormale recherche=null;
String tagTitle="Megannonce - Petites annonces gratuites";
String tagDescription="Megannonce - Site de petites annonces gratuites - Consultez ou déposez des annonces.";
if(request.getAttribute("recherche")!=null) {
    recherche=(RechercheNormale)request.getAttribute("recherche");
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
           <h1>Megannonce</h1>
           <%
           if(recherche!=null) { %>
           <div class="info">Utilisez le formulaire ci-dessous pour affiner votre recherche.</div>
           <br/>
           </header>
           <nav>
           <div id="form">
               <fieldset>
                   <legend>Recherche</legend>
                   <form action="./petites-annonces-1.html#form" method="POST">
                       <p>
                           <div>
                               <label for="motsCles">Recherche :</label>
                               <input type="text" name="motsCles" id="motsCles" value="<%= recherche.getMotsCles()%>" size="40" maxlength="300" />
                           </div>
                           <span>Je veux :</span>
                           <input type="radio" name="mode" value="1" id="mode1"<% if(recherche.getMode()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-2-1.html#form';" />
                           <label for="mode1">Acheter&nbsp;</label>
                           <input type="radio" name="mode" value="2" id="mode2"<% if(recherche.getMode()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./petites-annonces-2-2.html#form';" />
                           <label for="mode2">Vendre&nbsp;</label>
                           <div>
                               <label for="idCategorie">Catégorie :</label>
                               <select name="idCategorie" id="idCategorie" onchange="javascript:window.location.href='./petites-annonces-3-'+this.value+'.html#form';">
                                   <option value="0"<% if(recherche.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Toutes</option>
                                   <%
                                   Objet.getConnection();
                                   String query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
                                   Statement state=Objet.getConn().createStatement();
                                   ResultSet result=state.executeQuery(query);
                                   while(result.next()) {
                                       long idCategorie=result.getLong("id");
                                       String categorie=result.getString("categorie");
                                       %>
                                       <option value="<%= idCategorie %>"<% if(recherche.getIdCategorie()==idCategorie) out.print(" selected=\"selected\""); %>><%= categorie %></option>
                                       <%
                                       }
                                   result.close();
                                   state.close();
                                   %>
                               </select>
                               <label for="idSousCategorie">&nbsp;Sous-catégorie :</label>
                               <select name="idSousCategorie" id="idSousCategorie" onchange="javascript:window.location.href='./petites-annonces-4-'+this.value+'.html#form';">
                                   <option value="0"<% if(recherche.getIdSousCategorie()==0) out.print(" selected=\"selected\""); %>>Toutes</option>
                                   <%
                                   if(recherche.getIdCategorie()!=0) {
                                       query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                                       PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                       prepare.setLong(1, recherche.getIdCategorie());
                                       result=prepare.executeQuery();
                                       while(result.next()) {
                                           long idSousCategorie=result.getLong("id");
                                           String sousCategorie=result.getString("sous_categorie");
                                           %>
                                           <option value="<%= idSousCategorie %>"<% if(recherche.getIdSousCategorie()==idSousCategorie) out.print(" selected=\"selected\""); %>><%= sousCategorie %></option>
                                           <%
                                           }
                                       result.close();
                                       prepare.close();
                                       }
                                   %>
                               </select>
                           </div>
                               <div>
                                   <span>Localisation &rarr; </span>
                                   <label for="idRegion">Région :</label>
                                   <select name="idRegion" id="idRegion" onchange="javascript:window.location.href='./petites-annonces-5-'+this.value+'.html#form';">
                                       <option value="0"<% if(recherche.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Toutes</option>
                                       <%
                                       query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
                                       state=Objet.getConn().createStatement();
                                       result=state.executeQuery(query);
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
                                   <label for="idDepartement">&nbsp;Département :</label>
                                   <select name="idDepartement" id="idDepartement" onchange="javascript:window.location.href='./petites-annonces-6-'+this.value+'.html#form';">
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
                                   <label for="idCommune">&nbsp;Commune :</label>
                                   <select name="idCommune" id="idCommune" onchange="javascript:window.location.href='./petites-annonces-7-'+this.value+'.html#form';">
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
                                   <div>
                                       <input type="submit" value="Rechercher" name="kermit" />
                                       <input type="submit" value="Vider le formulaire" name="reset" />
                                   </div>
                       </p>
                   </form>
               </fieldset>
           </div>
           </nav>
                       <%
                       query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces_normales AS t1,table_membres AS t2"+recherche.getCondition()+" AND t2.id=t1.id_membre";
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
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Désolé, aucune annonce pour cette recherche.</div>
                           <br/>
                       </div>
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
                       int nbPages=(int)Math.ceil((double)nbAnnonces/(double)Datas.NBANNONCESPAGE);
                       int nbAnnoncesPage=0;
                       if(nbAnnonces<=Datas.NBANNONCESPAGE)
                           nbAnnoncesPage=nbAnnonces;
                       else if((recherche.getPage()+1)<nbPages) {
                           nbAnnoncesPage=Datas.NBANNONCESPAGE;
                       } else if((recherche.getPage()+1)==nbPages) {
                           nbAnnoncesPage=nbAnnonces-(Datas.NBANNONCESPAGE*(nbPages-1));
                           }
                       //out.println("nbAnnoncesPages : "+nbAnnoncesPage);
                       query="SELECT t1.id,t1.mode,t1.prix,t1.titre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp,t2.pseudo,t3.categorie,t4.sous_categorie,t5.region,t6.departement,t7.commune,t7.code_postal FROM table_annonces_normales AS t1,table_membres AS t2,table_categories AS t3,table_sous_categories AS t4,table_regions AS t5,table_departements AS t6,table_communes AS t7"+recherche.getCondition()+" AND t2.id=t1.id_membre AND t3.id=t1.id_categorie AND t4.id=t1.id_sous_categorie AND t5.id_region=t2.id_region AND t6.id_departement=t2.id_departement AND t7.id=t2.id_commune ORDER BY t1.timestamp DESC LIMIT "+(recherche.getPage()*Datas.NBANNONCESPAGE)+","+Datas.NBANNONCESPAGE;
                       state=Objet.getConn().createStatement();
                       result=state.executeQuery(query);
                       Img img=new Img();
                       int j=0;
                       while(result.next()) {
                           if(nbAnnoncesPage>5&&j==Math.floor(nbAnnoncesPage/2)) { %>
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
                       long idAnnonce=result.getLong("id");
                       int mode=result.getInt("mode");
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
                       String categorie=result.getString("categorie");
                       String sousCategorie=result.getString("sous_categorie");
                       String region=result.getString("region");
                       String departement=result.getString("departement");
                       String commune=result.getString("commune");
                       String codePostal=result.getString("code_postal");
                       Calendar cal=Calendar.getInstance();
                       cal.setTimeInMillis(timestamp);
                       String uri="./petite-annonce-"+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
                        %>
                       <div class="listeAnnonce" onclick="javascript:window.location.href='<%= uri %>#annonce';">
                           <div class="listeAnnonceGauche">
                               <%
                               int nbPhotos=0;
                               for(int i=1; i<=5; i++) {
                                   String extension=extensions[i-1];
                                   if(extension.length()>0&&nbPhotos==0) {
                                       String filenameMini1=Datas.DIR+"photos/mini1_1_"+idAnnonce+"_"+i+extension;
                                       File fileMini1=new File(filenameMini1);
                                       if(fileMini1.exists()) {
                                           nbPhotos++;
                                           img.getSize(fileMini1);
                                           int largeur=img.getWidth();
                                           int hauteur=img.getHeight();
                                           %>
                                           <img src="./photo-mini-1-1-<%= idAnnonce%>-<%= i %><%= extension%>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
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
                                       <%= categorie %>&rarr;<%= sousCategorie %>.
                                       <br/>
                                       <% if(mode==1) out.print("Vente - prix : "); else if(mode==2) out.print("Achat - prix souhaité : "); %><%= prix %>&euro;.
                                       <br/>
                                       Annonce déposée par <%= pseudo %>, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>
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
                           for(int i=prem; i<=der; i++) {
                               if(i==recherche.getPage()) { %>
                               <span>[<span class="clign"><%= (i+1) %></span>]</span>
                               <% } else { %>
                               <span>[<a href="./petites-annonces-8-<%= i %>.html#form" title="PAGE #<%= (i+1) %>"><%= (i+1) %></a>]</span>
                               <% }
                               }
                        %>
                       </div>
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