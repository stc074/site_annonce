<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Éditer mon annonce d'emploi - Photo</title>
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
           <h1>Megannonce - Éditer mon annonce de l'emploi - Photo.</h1>
           </header>
           <c:catch var="ex">
               <c:if test="${info!=null}">
                   <c:choose>
                       <c:when test="${info==1}">
                           <br/>
                           <div class="cadre2">
                               <br/>
                               <div class="info">Vous devez être connecté pour pouvoir éditer votre <strong>annonce</strong>.</div>
                               <br/>
                               <div>
                                   <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                               </div>
                               <br/>
                           </div>
                           <br/>
                       </c:when>
                       <c:when test="${info==2}">
                           <br/>
                           <div class="cadre2">
                               <<div class="info">Annonce inconnue !</div>
                           </div>
                           <br/>
                       </c:when>
                   </c:choose>
               </c:if>
                       <c:if test="${requestScope.annonce!=null}">
                           <c:set var="an" value="${requestScope.annonce}" scope="page"></c:set>
                           <h2><c:out value="${an.titre}"></c:out></h2>
                           <div class="cadre">
                           <h2>Photo deja enregistrée</h2>
                               <div class="photosMini">
                                   <div class="mini">
                                       <c:if test="${an.largeur!=0&&an.hauteur!=0}">
                                           <a href="<c:out value="./photo-4-${an.id}-1${an.extension1}"></c:out>" rel="nofollow" title="<c:out value="${an.titre}"></c:out>" zoom="1">
                                           <img src="./photo-mini-1-4-<c:out value="${an.id}"></c:out>-1<c:out value="${an.extension1}"></c:out>" width="<c:out value="${an.largeur}"></c:out>" height="<c:out value="${an.hauteur}"></c:out>" alt="miniature"/>
                                           </a>
                                       </c:if>
                                   </div>
                               </div>
                               <c:if test="${an.largeur==0&&an.hauteur==0}">
                                   <div class="info">Aucune photo enregistrée.</div>
                               </c:if>
                           </div>
                           <br/>
                           <div class="info">Pour modifier ou ajouter une photo à votre annonce, utilisez le formulaire ci-dessous :</div>
                           <br/>
                           <div id="form">
                               <c:if test="${not empty an.errorMsg}">
                                   <div class="erreur">
                                       <div>Erreur(s) :</div>
                                       <br/>
                                       ${an.errorMsg}
                                   </div>
                                   <br/>
                               </c:if>
                               <fieldset>
                                   <legend>Photo de mon annonce de l'emploi</legend>
                                   <form action="./edit-annonce-emploi-photo.html#form" method="POST" enctype="multipart/form-data">
                                       <p>
                                   <label for="image">Photo de mon annonce :</label>
                                   <input type="file" name="image" id="image" value="" />
                                   <br/>
                                   <input type="submit" value="Valider" name="kermit" />
                                       </p>
                                   </form>
                               </fieldset>
                           </div>
                       </c:if>
           </c:catch>
           <c:if test="${not empty ex}">
               <div class="erreur">
                   <div>Erreur :</div>
                   <br/>
                   <div>${ex.message}</div>
               </div>
           </c:if>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
