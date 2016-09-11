<%@page import="java.io.File"%>
<%@page import="classes.Img"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.AnnonceImmo"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%
try {
String tagTitle="Megannonce - Petite annonce immobilière gratuite";
String tagDescription="Megannonce - Toutes vos petites annonces immobilière gratuites sur internet !";
AnnonceImmo annonce=null;
if(request.getAttribute("annonce")!=null) {
    annonce=(AnnonceImmo)request.getAttribute("annonce");
    tagTitle=annonce.getTagTitle();
    tagDescription=annonce.getTagDescription();
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
<script type="text/javascript" src="./scripts/scripts.js"></script>
<script src="./js-global/FancyZoom.js" type="text/javascript"></script>
<script src="./js-global/FancyZoomHTML.js" type="text/javascript"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
</head>
    <body onload="setupZoom()">
       <%@include file="./haut.jsp" %>
       <section>
           <div id="annonce">
           <%
           if(request.getAttribute("info")!=null) {
               int info=Integer.parseInt(request.getAttribute("info").toString());
               switch(info) {
                   case 1: %>
                   <br/>
                   <div class="cadre2">
                       <br/>
                       <div class="info">Annonce inconnue !</div>
                       <br/>
                   </div>
                   <br/>
                   <%
                   break;
                   }
               } else if(annonce!=null) {
                   Calendar cal=Calendar.getInstance();
                   cal.setTimeInMillis(annonce.getTimestamp());
                   %>
          <br/>
           <div class="cadrePub">
               <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
           </div>
           <br/>
           <div class="cadre">
               <p></p>
           <ul class="reseauxSoc2">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
           </ul>
           <p></p>
           <div>
               <a href="#" rel="nofollow" title="SIGNALER UN ABUS" onclick="javascript:signalerAbus(3, <%= annonce.getId() %>);">SIGNALER UN ABUS</a>
           </div>
           <br/>
           <%
           String uriRetour="./petites-annonces-immobilieres-1.html";
           if(session.getAttribute("uriRetour")!=null)
               uriRetour=session.getAttribute("uriRetour").toString();
           %>
           <div>
               <a href="<%= uriRetour %>" title="RETOUR À LA LISTE DES RÉSULTATS">RETOUR À LA LISTE</a>
           </div>
           <br/>
           <div>
               <a href="#photos" rel="nofollow" title="VOIR LES PHOTOS">PHOTOS DE CETTE ANNONCE</a>
           </div>
           <br/>
           </div>
           <br/>
           <div class="cadre">
               <h1><%= annonce.getTitre() %></h1>
               <div><strong>Annonce immobilière</strong> déposée par <%= annonce.getMembre().getPseudo() %>, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
               <div>
                   <%
                   switch(annonce.getMode()) {
                       case 1: %>
                       <span>Vente </span>
                       <%
                       break;
                       case 2: %>
                       <span>Location à l'année </span>
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
                   switch(annonce.getTypeBien()) {
                       case 1: %>
                       <span>d'un appartement de type <%= Datas.arrayTypesAppart[annonce.getTypeAppartement()-1] %>.</span>
                       <%
                       break;
                       case 7: %>
                       <span>d'un terrain d'une surface de <%= annonce.getSurface() %> m<sup>2</sup> </span>
                       <%
                       switch(annonce.getViabilise()) {
                           case 1: %>
                           <span>viabilisé </span>
                           <%
                           break;
                           case 2: %>
                           <span>non-viabilisé </span>
                           <%
                           break;
                           }
                       switch(annonce.getConstructible()) {
                           case 1: %>
                           <span>et constructible.</span>
                           <%
                           break;
                           case 2: %>
                           <span>et non constructible.</span>
                           <%
                           break;
                           }
                       break;
                       default: %>
                       <span><%= Datas.arrayTypesBien[annonce.getTypeBien()-1] %>.</span>
                       <%
                       break;
                       }
                   %>
               </div>
               <div>
                   <%
                   switch(annonce.getTypeBien()) {
                       case 7:
                           break;
                       default: %>
                       <span>Surface de <%= annonce.getSurface() %> m<sup>2</sup> </span>
                       <%
                       break;
                       }
                   switch(annonce.getTypeBien()) {
                       case 1:
                       case 2:
                       case 3:
                           switch(annonce.getMeuble()) {
                               case 1: %>
                               <span>meublé(e) </span>
                               <%
                               break;
                               case 2: %>
                               <span>non-meublé(e) </span>
                               <%
                               break;
                               }
                           break;
                           }
                   switch(annonce.getTypeBien()) {
                       case 1:
                       case 2:
                       case 3:
                       case 4:
                       case 5: %>
                       <span>comportant <%= annonce.getNbPieces() %> pièces à vivre.</span>
                       <%
                       break;
                       case 6: %>
                       <span>comportant <%= annonce.getNbEtages() %> étages.</span>
                       <%
                       break;
                       }
                   %>
               </div>
               <div>
                   <%
                   switch(annonce.getMode()) {
                       case 1: %>
                       <span>Prix à la vente de <%= annonce.getPrix() %> &euro;.</span>
                       <%
                       break;
                       case 2: %>
                       <span>Prix de la location au mois de <%= annonce.getLoyer() %> &euro;<% if(!annonce.getCharges().equals("0")) { %>  plus <%= annonce.getCharges() %> &euro; de charges.<% } %></span>
                       <%
                       break;
                       case 3: %>
                       <span>Prix de la location à la semaine de <%= annonce.getLoyer() %> &euro;.</span>
                       <%
                       break;
                       case 4: %>
                       <span>Prix souhaité à l'achat de <%= annonce.getPrix() %> &euro;.</span>
                       <%
                       break;
                       }
                   %>
               </div>
               <div>Localisation : <%= annonce.getMembre().getCodePostal() %>-<%= annonce.getMembre().getCommune() %>|<%= annonce.getMembre().getRegion() %>-<%= annonce.getMembre().getDepartement() %>.</div>
           </div>
           <br/>
           <div class="cadre">
               <h2>Description de l'annonce immobilière</h2>
               <article>
               <%= annonce.getDescription() %>
               </article>
           </div>
           <br/>
           <%
           switch(annonce.getTypeBien()) {
               case 4:
               case 5: %>
               <div class="cadre">
                   <%
                   switch(annonce.getMode()) {
                       case 1:
                       case 2:
                       case 3: %>
                       <h2>Travaux de rénovation à effectuer</h2>
                       <%
                       break;
                       case 4: %>
                       <h2>Travaux de rénovation souhaités</h2>
                       <%
                       break;
                       }
                   %>
                   <%= annonce.getTravaux() %>
               </div>
               <br/>
               <%
               break;
               }
           %>
          <br/>
          <div class="cadrePub">
               <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script>
           </div>
           <br/>
           <div class="cadre" id="photos">
               <h2>Photos (Cliquez dessus pour voir en taille réelle)</h2>
               <div class="photosMini">
                   <%
                   int nbPhotos=0;
                   Img img=new Img();
                   File file=null;
                   File fileMini2=null;
                   for(int i=1;i<=5;i++) {
                       String extension=annonce.getExtensions()[i-1];
                       if(extension.length()>0) {
                           String filename=Datas.DIR+"photos/3_"+annonce.getId()+"_"+i+extension;
                           String filenameMini2=Datas.DIR+"photos/mini2_3_"+annonce.getId()+"_"+i+extension;
                           file=new File(filename);
                           fileMini2=new File(filenameMini2);
                           if(file.exists()&&fileMini2.exists()) {
                               nbPhotos++;
                               img.getSize(fileMini2);
                               int largeur=img.getWidth();
                               int hauteur=img.getHeight();
                               %>
                               <div class="mini">
                               <a href="./photo-3-<%= annonce.getId() %>-<%= i %><%= extension %>" title="<%= annonce.getTitre() %>" rel="nofollow" zoom="1">
                                   <img src="./photo-mini-2-3-<%= annonce.getId()%>-<%= i%><%= extension%>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
                               </a>
                               </div>
                                   <%
                                   }
                           }
                       }
                   %>
               </div>
                   <%
                   if(nbPhotos==0) { %>
                   <div class="info">Pas de photos pour cette <strong>annonce</strong>.</div>
                   <%
                   }
                   %>
           </div>
           <br/>
           <div class="cadre">
               <h2>Contacter l'annonceur - <%= annonce.getMembre().getPseudo() %></h2>
               <div>Pour contacter <%= annonce.getMembre().getPseudo() %> (l'auteur de cette <strong>annonce</strong>, <a href="./contacter-annonceur-3-<%= annonce.getId() %>.html" title="CONTACTER <%= annonce.getMembre().getPseudo() %>" rel="nofollow">CLIQUEZ ICI</a></div>
           </div>
           <br/>
          <div class="cadrePub">
               <script type="text/javascript"><!--
google_ad_client = "pub-0393835826855265";
/* 728x90, date de création 23/08/11 */
google_ad_slot = "7589010716";
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
           </div>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
       <%
       } catch(Exception ex) { %>
       <html>
           <head>
               <title>Erreur</title>
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
                   <% } %>
       </html>
