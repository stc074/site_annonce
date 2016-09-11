<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Message reçu</title>
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
           </header>
           <c:catch var="ex">
               <c:if test="${requestScope.info!=null}">
                   <c:choose>
                       <c:when test="${requestScope.info==1}">
                           <br/>
                           <div class="cadre2">
                               <br/>
                               <div class="info">Vous devez être connecté pour pouvoir consulter vos messages.</div>
                               <br/>
                               <div>
                                   <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                               </div>
                               <br/>
                           </div>
                               <br/>
                       </c:when>
                       <c:when test="${requestScope.info==2}">
                           <br/>
                           <div class="cadre2">
                               <div class="info">Message inconnu !</div>    
                           </div>
                           <br/>
                       </c:when>
                   </c:choose>
               </c:if>
                       <c:if test="${requestScope.message!=null}">
                           <c:set var="msg" value="${requestScope.message}" scope="page"></c:set>
                           <c:if test="${msg.test==0}">
                               <h1><c:out value="${msg.titreAnnonce}"></c:out> - Message reçu</h1>
                               <c:if test="${msg.idPrec!=0}">
                                   <div class="info"><c:out value="${msg.commentPrec}"></c:out></div>
                                   <h2><c:out value="${msg.titrePrec}"></c:out></h2>
                                   ${msg.contenuPrec}
                               </c:if>
                                   <div class="info"><c:out value="${msg.comment}"></c:out></div>
                                   <c:if test="${msg.etat==0}">
                                       <div>[<span class="clign">NOUVEAU MESSAGE</span>]</div>
                                   </c:if>
                                   <h2><c:out value="${msg.titre}"></c:out></h2>
                                   ${msg.contenu}
                                   <br/>
                                   <div class="info">Pour répondre à <c:out value="${msg.membreExpediteur.pseudo}"></c:out>, utilisez le formulaire ci-dessous :</div>
                                   <br/>
                                   <div id="form">
                                       <c:if test="${not empty msg.errorMsg}">
                                           <div class="erreur">
                                               <div>Erreur(s) :</div>
                                               <br/>
                                               ${msg.errorMsg}
                                           </div>
                                           <br/>
                                       </c:if>
                                       <fieldset>
                                           <legend>Répondre à <c:out value="${msg.membreExpediteur.pseudo}"></c:out></legend>
                                           <form action="./message-recu.html#form" method="POST">
                                               <p>
                                               <input type="hidden" name="idMessage" value="<c:out value="${msg.id}"></c:out>" />
                                               <label for="titreMsg">Titre de votre message :</label>
                                               <br/>
                                               <input type="text" name="titreMsg" id="titreMsg" value="<c:out value="${msg.titreMsg}"></c:out>" size="30" maxlength="80" />
                                               <br/>
                                               <label for="contenuMsg">Contenu de votre message :</label>
                                               <br/>
                                               <textarea name="contenuMsg" id="contenuMsg" rows="4" cols="20">${msg.contenuMsg}</textarea>
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
                                   <c:if test="${msg.test==1}">
                                       <br/>
                                       <div class="cadre2">
                                           <div class="info">Message envoyé à <c:out value="${msg.pseudo}"></c:out></div>
                                       </div>
                                       <br/>
                                   </c:if>
                       </c:if>
           </c:catch>
           <c:if test="${not empty ex}">
               <div class="erreur">
                   <div>Erreur :</div>
                   <br/>
                   <div><c:out value="${ex.message}"></c:out></div>
               </div>
           </c:if>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
