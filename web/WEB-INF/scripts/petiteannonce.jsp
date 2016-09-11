<%@page import="classes.Img"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.AnnonceNormale"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%
try {
String tagTitle="Megannonce - Petite annonce gratuite";
String tagDescription="Megannonce - Toutes vos petites annonces gratuites sur internet !";
AnnonceNormale annonce=null;
if(request.getAttribute("annonce")!=null) {
    annonce=(AnnonceNormale)request.getAttribute("annonce");
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
               <a href="#" rel="nofollow" title="SIGNALER UN ABUS" onclick="javascript:signalerAbus(1, <%= annonce.getId() %>);">SIGNALER UN ABUS</a>
           </div>
           <br/>
           <%
           String uriRetour="./petites-annonces-1.html";
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
    <div><strong>Annonce</strong> déposée par <%= annonce.getMembre().getPseudo() %>, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
    <div><%= annonce.getCategorie() %>&rarr;<%= annonce.getSousCategorie() %>.</div>
    <div><% if(annonce.getMode()==1) out.print("Vente &rarr; "); else if(annonce.getMode()==2) out.print("Souhaite acheter au prix de "); %><%= annonce.getPrix() %>&nbsp;&euro;.</div>
    <br/>
    <div>Localisation : <%= annonce.getMembre().getCodePostal() %>-<%= annonce.getMembre().getCommune() %>|<%= annonce.getMembre().getRegion() %>-<%= annonce.getMembre().getDepartement() %>.</div>
</div>
<br/>
<div class="cadre3">
    <h2>Description de l'annonce :</h2>
    <article>
    <%= annonce.getDescription() %>
    </article>
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
          <div class="cadre" id="photos">
               <h2>Photos de l'annonce (cliquez dessus pour voir en taille réelle)</h2>
               <div class="photosMini">
               <%
               int nbPhotos=0;
               Img img=new Img();
               for(int i=1; i<=5; i++) {
                   String extension=annonce.getExtensions()[i-1];
                   if(extension.length()>0) {
                       String filenameMini2=Datas.DIR+"photos/mini2_1_"+annonce.getId()+"_"+i+extension;
                       String filename=Datas.DIR+"photos/1_"+annonce.getId()+"_"+i+extension;
                       File fileMini2=new File(filenameMini2);
                       File file=new File(filename);
                       if(fileMini2.exists()&&file.exists()) {
                           nbPhotos++;
                           img.getSize(fileMini2);
                           int largeur=img.getWidth();
                           int hauteur=img.getHeight();
                           %>
                           <div class="mini">
                               <a href="./photo-1-<%= annonce.getId() %>-<%= i %><%= extension %>"  title="<%= annonce.getTitre() %>" rel="nofollow" zoom="1">
                               <img src="./photo-mini-1-1-<%= annonce.getId()%>-<%= i%><%= extension%>" width="<%= largeur%>" height="<%= hauteur%>" alt="photo miniature"/>
                               </a>
                           </div>
                               <%
                               }
                       }
                   }
               if(nbPhotos==0) { %>
               <br/>
               <div class="info">Aucune photo pour cette <strong>annonce</strong>.</div>
               <br/>
               <% } %>
                           </div>
                           <br/>
           </div>
          <br/>
                           <div class="cadre">
                               <h2>Contacter l'annonceur : <%= annonce.getMembre().getPseudo() %></h2>
                               <div>Pour contacter l'annonceur, <%= annonce.getMembre().getPseudo() %>, <a href="./contacter-annonceur-1-<%= annonce.getId() %>.html" title="CONTACTER <%= annonce.getMembre().getPseudo() %>" rel="nofollow">CLIQUEZ ICI</a></div>
                               <br/>
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
