<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Messagerie</title>
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
           <h1>Megannonce - Messagerie</h1>
           </header>
           <c:catch var="ex">
               <c:if test="${requestScope.info!=null}">
                   <c:choose>
                       <c:when test="${requestScope.info==1}">
                           <br/>
                           <div class="cadre2">
                               <br/>
                               <div class="info">Vous devez être connecté pour pouvoir consulter votre messagerie.</div>
                               <br/>
                               <div>
                                   <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                               </div>
                           </div>
                       </c:when>
                   </c:choose>
               </c:if>
                           <c:if test="${requestScope.messagerie!=null}">
                               <c:set var="msgrie" value="${requestScope.messagerie}" scope="page"></c:set>
                               <br/>
                               <div class="cadre2">
                                   <br/>
                                   <div>
                                       <a href="./messages-recus.html" rel="nofollow" title="MESSAGES REÇUS">MESSAGES REÇUS</a>
                                       <c:if test="${msgrie.recNonLus>0}">
                                           <span>&nbsp;[<span class="clign"><c:out value="${msgrie.recNonLus}"></c:out></span>]&nbsp;Non lu(s).</span>
                                       </c:if>
                                   </div>
                                       <br/>
                               </div>
                                       <br/>
                                       <div class="cadre2">
                                           <br/>
                                           <div>
                                               <a href="./messages-envoyes.html" accesskey="nofollow" title="MESSAGES ENVOYÉS">MESSAGES ENVOYÉS</a>
                                               <c:if test="${msgrie.envNonLus>0}">
                                                   <span>&nbsp;[<span class="clign"><c:out value="${msgrie.envNonLus}"></c:out></span>]&nbsp;Non consulté(s).</span>
                                               </c:if>
                                           </div>
                                               <br/>
                                       </div>
                           </c:if>
           </c:catch>
           <c:if test="${not empty ex}">
               <div class="erreur">
                   <div>erreur :</div>
                   <br/>
                   <div><c:out value="${ex.message}"></c:out></div>
               </div>
           </c:if>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
