<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Supprimer mon compte</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
</head>
    <body>
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
           <h1>Megannonce - Supprimer mon compte</h1>
           </header>
           <c:catch var="ex">
               <c:if test="${requestScope.info!=null}">
                   <c:set var="inf" value="${requestScope.info}" scope="page"></c:set>
                       <c:choose>
                           <c:when test="${inf==1}">
                               <br/>
                               <div class="cadre2">
                                   <br/>
                                   <div class="info">Vous devez être connecter pour pouvoir supprimer votre compte.</div>
                                   <br/>
                                   <div>
                                       <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                                   </div>
                                   <br/>
                               </div>
                                   <br/>
                           </c:when>
                       </c:choose>
               </c:if>
                           <c:if test="${requestScope.membre!=null}">
                               <c:set var="mbr" value="${requestScope.membre}" scope="page"></c:set>
                               <c:if test="${mbr.test==0}">
                                   <br/>
                                   <p>Attention si vous supprimez votre compte, toutes les données lui étant associées (annonce, message) seront supprimées.</p>
                                   <br/>
                                   <div id="form">
                                       <c:if test="${not empty mbr.errorMsg}">
                                           <div class="erreur">
                                               <div>Erreur(s) :</div>
                                               <br/>
                                               ${mbr.errorMsg}
                                           </div>
                                           <br/>
                                       </c:if>
                                       <fieldset>
                                           <legend>SUPPRIMER MON COMPTE</legend>
                                           <form action="./supprimer-compte.html#form" method="POST">
                                               <p>
                                                   <div>Pour supprimer définitivement votre compte cliquez sur le bouton ci-dessous :</div>
                                                   <br/>
                                                   <input type="submit" value="SUPPRIMER MON COMPTE" name="kermit" />
                                               </p>
                                           </form>
                                       </fieldset>
                                   </div>
                               </c:if>
                                   <c:if test="${mbr.test==1}">
                                       <br/>
                                       <div class="cadre2">
                                           <div class="info">Votre compte vient d'être supprimé !</div>
                                       </div>
                                       <br/>
                                   </c:if>
                           </c:if>
           </c:catch>
           <c:if test="${not empty ex}">
               <div class="erreur">
                   <div>Erreur</div>
                   <br/>
                   <div><c:out value="${ex.message}"></c:out></div>
               </div>
           </c:if>
       </section>
       <%@include file="./footer.jsp" %>
   </body>
</html>
