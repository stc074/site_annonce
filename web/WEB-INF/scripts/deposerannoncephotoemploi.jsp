<%@page import="classes.Img"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.Objet"%>
<%@page import="java.sql.Connection"%>
<%@page import="classes.AnnoncePhotoEmploi"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Déposer une annonce emploi - Photos</title>
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
           <br/>
<div>
<a name="fb_share" type="button_count" share_url="<%= Datas.URLROOT %>">Cliquez pour partager !!!</a><script src="http://static.ak.fbcdn.net/connect.php/js/FB.Share" type="text/javascript"></script>
</div>
           <br/>
           <g:plusone></g:plusone>
           <br/>
           <h1>Megannonce - Déposer une annonce emploi - Photos.</h1>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Vous devez vous identifier avant de poster une <strong>annonce</strong>.</div>
                           <br/>
                           <div>
                               <a href="./deposer-annonce-1.html" title="S'IDENTIFIER" rel="nofollow">S'IDENTIFIER</a>
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
                       AnnoncePhotoEmploi annonce=(AnnoncePhotoEmploi)request.getAttribute("annonce");
                       %>
                       <p>Vous pouvez ajouter une photo de vous ou du logo de votre entreprise, ceci est facultatif mais contribue a une meilleure popularité de votre <strong>annonce</strong>.</p>
                       <h2>Photo déja enregistrée :</h2>
                       <div class="cadre2">
                           <div class="photosMini">
                               <%
                               int nbPhotos=0;
                               String extension=annonce.getExtension1();
                               if(extension.length()>0) {
                                   String filename=Datas.DIR+"photos/4_"+annonce.getId()+"_1"+extension;
                                   String filenameMini2=Datas.DIR+"photos/mini2_4_"+annonce.getId()+"_1"+extension;
                                   File file=new File(filename);
                                   File fileMini2=new File(filenameMini2);
                                   if(file.exists()&&fileMini2.exists()) {
                                       nbPhotos++;
                                       Img img=new Img();
                                       img.getSize(fileMini2);
                                       int largeur=img.getWidth();
                                       int hauteur=img.getHeight();
                                       %>
                                       <div class="mini">
                                       <a href="./photo-4-<%= annonce.getId() %>-1<%= extension %>" rel="nofollow" title="IMAGE" zoom="1">
                                           <img src="./photo-mini-2-4-<%= annonce.getId()%>-1<%= extension%>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
                                       </a>
                                       </div>
                                           <%
                                   }
                               }
                               %>
                            </div>
                               <%
                               if(nbPhotos==0) { %>
                               <div class="info">Aucune photo encore enregistrée.</div>
                               <% } %>
                       </div>
                            <br/>
                       <div class="info">Pour uploader une image, utilisez le formulaire ci-dessous :</div>
                       <br/>
                       <div id="form">
                           <%
                           if(annonce.getErrorMsg().length()>0) { %>
                           <div class="erreur">
                               <div>Erreur(s) :</div>
                               <br/>
                               <%= annonce.getErrorMsg() %>
                           </div>
                           <br/>
                           <% } %>
                           <fieldset>
                               <legend>Image illustrant mon annonce :</legend>
                               <form action="./deposer-annonce-emploi-photo.html#form" method="POST" enctype="multipart/form-data">
                                   <p>
                                       <label for="image">Fichier de votre image :</label>
                                       <input type="file" name="image" id="image" value="" />
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
