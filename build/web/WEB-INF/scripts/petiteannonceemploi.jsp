<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:catch var="ex">
<c:set var="tagTitle" value="Petite annonce de l'emploi" scope="page"></c:set>
<c:set var="tagDescription" value="Megannonce - Petite annonce de l'emploi" scope="page"></c:set>
<c:set var="an" value="${null}" scope="page"></c:set>
<c:if test="${requestScope.annonce!=null}">
    <c:set var="an" value="${requestScope.annonce}" scope="page"></c:set>
    <c:set var="tagTitle" value="${an.tagTitle}"></c:set>
    <c:set var="tagDescription" value="${an.tagDescription}"></c:set>
</c:if>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${tagTitle}"></c:out></title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="<c:out value="${tagDescription}"></c:out>" />
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
               <c:if test="${requestScope.info!=null}">
                   <c:choose>
                       <c:when test="${requestScope.info==1}">
                           <div class="cadre2">
                               <div class="info"><strong>Annonce</strong> inconnue !</div>
                           </div>
                           <br/>
                           <div class="cadrePub">
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
                       </c:when>
                   </c:choose>
               </c:if>
                           <c:if test="${pageScope.an!=null}">
                               <jsp:useBean id="dat" class="classes.Datas" scope="page"></jsp:useBean>
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
               <a href="#" rel="nofollow" title="SIGNALER UN ABUS" onclick="javascript:signalerAbus(4, <c:out value="${an.id}"></c:out>);">SIGNALER UN ABUS</a>
           </div>
           <br/>
            <div>
                <a href="<c:out value="${an.uriRetour}"></c:out>" title="RETOUR À LA LISTE DES RÉSULTATS">RETOUR À LA LISTE</a>
           </div>
           <br/>
           <div>
               <a href="#photo" rel="nofollow" title="VOIR LA PHOTO">PHOTO DE CETTE ANNONCE</a>
           </div>
           <br/>
           </div>
           <br/>
           <div class="cadre">
               <h1><c:out value="${an.titre}"></c:out></h1>
               <div>${an.ligne1}</div>
               <div><c:out value="${an.ligne3}"></c:out></div>
               <div><c:out value="${an.ligne4}"></c:out></div>
               <br/>
               <div><c:out value="${an.ligne2}"></c:out></div>
           </div>
           <br/>
           <div class="cadre">
               <h2>Description de l'annonce d'emploi</h2>
               <article>
               ${an.description}
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
            <div class="cadre" id="photo">
               <h2>Photo (Cliquez dessus pour voir en taille réelle)</h2>
               <c:if test="${not empty an.extensions[0]}">
                   <div class="photosMini">
                       <div class="mini">
                           <a href="./photo-4-${an.id}-1${an.extensions[0]}" rel="nofollow" title="<c:out value="${an.titre}"></c:out>" zoom="1">
                           <img src="./photo-mini-2-4-<c:out value="${an.id}"></c:out>-1<c:out value="${an.extensions[0]}"></c:out>" width="<c:out value="${an.largeurs[0]}"></c:out>" height="<c:out value="${an.hauteurs[0]}"></c:out>" alt="miniature"/>
                           </a>
                       </div>
                   </div>
               </c:if>
               <c:if test="${empty an.extensions[0]}">
                   <div class="info">Aucune photo pour cette <strong>annonce</strong>.</div>
               </c:if>
           </div>
           <br/>
           <div class="cadre">
               <h2>Contacter l'annonceur : <c:out value="${an.membre.pseudo}"></c:out></h2>
               <div>Pour contacter <c:out value="${an.membre.pseudo}"></c:out> (l'auteur de cette <strong>annonce</strong>), <a href="./contacter-annonceur-4-${an.id}.html" rel="nofollow" title="CONTACTER <c:out value="${an.membre.pseudo}"></c:out>">CLIQUEZ ICI</a></div>
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
                           </c:if>
           </div>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
</c:catch>
<c:if test="${not empty ex}">
    <html>
        <head>
            <title>Erreur</title>
        </head>
        <body>
            <div class="contenu">
                <div class="erreur">
                    <div>Erreur :</div>
                    <br/>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </div>
        </body>
    </html>
</c:if>
