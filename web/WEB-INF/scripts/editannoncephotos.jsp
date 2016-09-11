<%@page import="classes.Img"%>
<%@page import="java.io.File"%>
<%@page import="classes.AnnoncePhotos"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Éditer une annonce - Photos</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
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
           <header>
           <br/>
<div>
<a name="fb_share" type="button_count" share_url="<%= Datas.URLROOT %>">Cliquez pour partager !!!</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div>
           <br/>
           <g:plusone></g:plusone>
           <br/>
           <h1>Megannonce - Éditer une annonce - Photos.</h1>
           <header>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Vous devez être connecté pour pouvoir éditer vos <strong>annonces</strong>.</div>
                           <br/>
                           <div>
                               <a href="./inscription.html" title="S'INSCRIRE" rel="nofollow">S'INSCRIRE</a>
                           </div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       case 2: %>
                       <br/>
                       <div class="cadre2">
                           <div class="info">Annonce inconnue !</div>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("annonce")!=null) {
                       AnnoncePhotos annonce=(AnnoncePhotos)request.getAttribute("annonce");
                       %>
                       <div class="cadre2">
                           <div class="info">Photos enregistrée(s) :</div>
                           <br/>
                           <div class="photosMini">
                               <%
                               int nbPhotos=0;
                               Img img=new Img();
                               for(int i=1; i<=5; i++) {
                                   String extension=annonce.getExtensions()[i-1];
                                   if(extension.length()>0) {
                                       String filename=Datas.DIR+"photos/"+annonce.getType()+"_"+annonce.getId()+"_"+i+extension;
                                       String filenameMini2=Datas.DIR+"photos/mini2_"+annonce.getType()+"_"+annonce.getId()+"_"+i+extension;
                                       File file=new File(filename);
                                       File fileMini1=new File(filenameMini2);
                                       if(file.exists()&&fileMini1.exists()) {
                                           nbPhotos++;
                                           img.getSize(fileMini1);
                                           int largeur=img.getWidth();
                                           int hauteur=img.getHeight();
                                           %>
                                           <div class="mini">
                                               <a href="./photo-<%= annonce.getType() %>-<%= annonce.getId() %>-<%= i %><%= extension %>" title="<%= annonce.getTitre() %>" rel="nofollow" zoom="1">
                                                   <img src="./photo-mini-2-<%= annonce.getType()%>-<%= annonce.getId()%>-<%= i%><%= extension%>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
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
                               <br/>
                               <div class="info">Aucune photo enregistrée pour cette <strong>annonce</strong>.</div>
                               <br/>
                               <% } %>
                       </div>
                               <br/>
                               <div class="info">Pour modifier ou rajouter des photos utilisez le formulaire ci-dessous:</div>
                               <br/>
                               <div id="form">
                                   <% if(annonce.getErrorMsg().length()>0) { %>
                                   <div class="erreur">
                                       <div>Erreur(s) :</div>
                                       <br/>
                                       <div><%= annonce.getErrorMsg() %></div>
                                   </div>
                                   <br/>
                                   <% } %>
                                   <fieldset>
                                       <legend>Fichiers de photo</legend>
                                       <form action="./edit-annonce-photos.html#form" method="POST" enctype="multipart/form-data">
                                           <p>
                                               <label for="photo1">Photo N°1</label>
                                               <input type="file" name="1" id="photo1" />
                                               <br/>
                                               <label for="photo2">Photo N°2</label>
                                               <input type="file" name="2" id="photo2" />
                                               <br/>
                                               <label for="photo3">Photo N°3</label>
                                               <input type="file" name="3" id="photo3" />
                                               <br/>
                                               <label for="photo4">Photo N°4</label>
                                               <input type="file" name="4" id="photo4" />
                                               <br/>
                                               <label for="photo5">Photo N°5</label>
                                               <input type="file" name="5" id="photo5" />
                                               <br/>
                                               <input type="submit" value="Valider" name="kermit" />
                                           </p>
                                       </form>
                                   </fieldset>
                               </div>
                               <%
                       }
                   } catch(Exception ex) { %>
                   <br/>
                   <div class="erreur">
                       <div>Erreur :</div>
                       <br/>
                       <div><%= ex.getMessage() %></div>
                   </div>
                   <br/>
                   <% } %>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
