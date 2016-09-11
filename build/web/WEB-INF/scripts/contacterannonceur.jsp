<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Contacter un annonceur</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
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
           <h1>Megannonce - Contacter un annonceur.</h1>
           </header>
           <c:catch var="ex">
           <c:if test="${info!=null}">
               <c:choose>
               <c:when test="${info==1}">
               <br/>
               <div class="cadre2">
                   <br/>
                   <div class="info">Vous devez être connecté pour pouvoir contacter un annonceur.</div>
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
                       <div class="info"><strong>Annonce</strong> inconnue !</div>
                   </div>
                   <br/>
               </c:when>
               </c:choose>
           </c:if>
               <c:if test="${requestScope.message!=null}">
                   <c:set var="mes" value="${requestScope.message}" scope="page"></c:set>
                       <h2><c:out value="${mes.titreAnnonce}"></c:out></h2>
                       <c:if test="${mes.test==0}">
                       <div class="info">Pour contacter <c:out value="${mes.membreDestinataire.pseudo}"></c:out>, utilisez le formulaire ci-dessous :</div>
                       <br/>
                       <div id="form">
                           <c:if test="${not empty mes.errorMsg}">
                               <div class="erreur">
                                   <div>Erreur(s) :</div>
                                   <br/>
                                   ${mes.errorMsg}
                               </div>
                               <br/>
                           </c:if>
                           <fieldset>
                               <legend>Contacter <c:out value="${mes.membreDestinataire.pseudo}"></c:out></legend>
                               <form action="./contacter-annonceur.html#form" method="POST">
                                   <p>
                                   <input type="hidden" name="idAnnonce" value="<c:out value="${mes.idAnnonce}"></c:out>" />
                                   <input type="hidden" name="type" value="<c:out value="${mes.type}"></c:out>" />
                                   <label for="titreMsg">Titre de votre message :</label>
                                   <br/>
                                   <input type="text" name="titreMsg" id="titreMsg" value="<c:out value="${mes.titreMsg}"></c:out>" size="30" maxlength="80" />
                                   <br/>
                                   <label for="contenuMsg">Contenu de votre message :</label>
                                   <br/>
                                   <textarea name="contenuMsg" id="contenuMsg" rows="4" cols="20">${mes.contenuMsg}</textarea>
                                   <br/>
                                   <div class="captcha"></div>
                                   <div class="captchaDroite">
                                       <label for="captcha">&rarr;Copiez le code SVP&rarr;</label>
                                       <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                   </div>
                                   <br/>
                                   <br/>
                                   <input type="submit" value="Valider" name="kermit" />
                                   <br/>
                                   </p>
                               </form>
                           </fieldset>
                       </div>
                                   <script type="text/javascript">
                                       CKEDITOR.replace( 'contenuMsg' );
                                   </script>
                       </c:if>
                       <c:if test="${mes.test==1}">
                           <br/>
                           <div class="cadre2">
                               <div class="info">Message envoyé à <c:out value="${mes.pseudo}"></c:out> !</div>
                           </div>
                       </c:if>
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
