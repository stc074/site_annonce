<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:catch var="ex">
    <c:set var="tagTitle" value="Petites annonces gratuites" scope="page"></c:set>
    <c:set var="tagDescription" value="Megannonce - Petites annonces gratuites en France" scope="page"></c:set>
    <c:if test="${requestScope.annonces!=null}">
        <c:set var="an" value="${requestScope.annonces}" scope="page"></c:set>
        <c:set var="tagTitle" value="${an.tagTitle}" scope="page"></c:set>
        <c:set var="tagDescription" value="${an.tagDescription}" scope="page"></c:set>
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
<script type="text/javascript" src="./ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="https://apis.google.com/js/plusone.js">
  {lang: 'fr'}
</script>
<%@include file="./analytics.jsp" %>
</head>
    <body>
<div id="fb-root"></div>
<script>
    (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/fr_FR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
       <%@include file="./haut.jsp" %>
       <section>
           <c:if test="${requestScope.info!=null}">
               <c:choose>
                   <c:when test="${requestScope.info==1}">
                       <br/>
                       <div class="cadre2">
                           <div class="info">Catégorie inconnue !</div>
                       </div>
                       <br/>
                   </c:when>
               </c:choose>
           </c:if>
                   <c:if test="${an!=null}">
                       <h1>Megannonce - <c:out value="${an.categorie}"></c:out> - <c:out value="${an.sousCategorie}"></c:out></h1>
                       <div>
                           <div class="colonneGauche">
                               <nav>
                               <ul class="listeCategories">
                                   <li class="parent" onclick="javascript:window.location.href='./';">
                                           <a href="./" title="TOUTES LES CATÉGORIES">TOUTES LES CATÉGORIES</a>
                                   </li>
                                   <li><c:out value="${an.categorie}"></c:out></li>
                                   <c:forEach var="i" begin="0" end="${an.arrayLengthSousCat-1}" step="1">
                                       <c:if test="${an.idsSousCat[i]==an.idSousCategorie}">
                                           <li class="catActuel" onclick="javascript:window.location.href='${an.urlsSousCat[i]}';">
                                               <a href="${an.urlsSousCat[i]}" title="<c:out value="${an.titresSousCat[i]}"></c:out>"><c:out value="${an.titresSousCat[i]}"></c:out></a>
                                           </li>
                                       </c:if>
                                       <c:if test="${an.idsSousCat[i]!=an.idSousCategorie}">
                                           <li class="cat" onclick="javascript:window.location.href='${an.urlsSousCat[i]}';">
                                               <a href="${an.urlsSousCat[i]}" title="<c:out value="${an.titresSousCat[i]}"></c:out>"><c:out value="${an.titresSousCat[i]}"></c:out></a>
                                           </li>
                                       </c:if>
                                   </c:forEach>
                               </ul>
                               </nav>
                           </div>
                                   <div class="colonneDroite">
                                       <c:if test="${an.nbAnnoncesPage==0}">
                                           <p></p>
           <ul class="reseauxSoc">
               <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
               <li>
                   <div class="fb-like" data-send="true" data-layout="button_count" data-width="450" data-show-faces="true"></div>
               </li>
           </ul>
           <p></p>
                                           <div class="cadre2">
                                               <div class="info">Pas d'annonce pour cette catégorie.</div>
                                           </div>
                                           <br/>
                                                    <div class="listeAnnoncePub2">
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
                              </c:if>
                                                    <c:if test="${an.nbAnnoncesPage>0}">
                                                        <h2><c:out value="${an.nbAnnonces}"></c:out> annonces dans la catégorie <c:out value="${an.categorie}"></c:out>-<c:out value="${an.sousCategorie}"></c:out></h2>
                                                        <p></p>
           <ul class="reseauxSoc">
                <li>
                   <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en">Tweet</a>
                   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
               </li>
               <li>
                   <g:plusone></g:plusone>
               </li>
               <li>
                   <div class="fb-like" data-send="true" data-layout="button_count" data-width="450" data-show-faces="true"></div>
               </li>
          </ul>
                                                                              <br/>
                                                                           <div class="listeAnnoncePub2">
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
                                                                           <br/>
                                                    <c:forEach var="i" begin="0" end="${an.nbAnnoncesPage-1}" step="1">
                                                                            <div class="listeAnnonce2" onclick="javascript:window.location.href='${an.urls[i]}#annonce'">
                                                                               <div class="listeAnnonceGauche">
                                                                                   <c:choose>
                                                                                       <c:when test="${empty an.extensions[i]}">
                                                                                           <img src="./GFXs/miniature.png" width="100" height="100" alt="miniature"/>
                                                                                       </c:when>
                                                                                       <c:when test="${not empty an.extensions[i]}">
                                                                                           <img src="./photo-mini-1-1-<c:out value="${an.ids[i]}"></c:out>-<c:out value="${an.indexs[i]}"></c:out><c:out value="${an.extensions[i]}"></c:out>" width="<c:out value="${an.largeurs[i]}"></c:out>" height="<c:out value="${an.hauteurs[i]}"></c:out>" alt="miniature"/>
                                                                                       </c:when>
                                                                                   </c:choose>
                                                                                </div>
                                                                               <div class="listeAnnonceDroite">
                                                                                   <h1>
                                                                                       <a href="${an.urls[i]}#annonce" title="<c:out value="${an.titres[i]}"></c:out>"><c:out value="${an.titres[i]}"></c:out></a>
                                                                                   </h1>
                                                                                   <p>
                                                                                       ${an.lignes1[i]}
                                                                                       <br/>
                                                                                       ${an.lignes2[i]}
                                                                                       <br/>
                                                                                       <br/>
                                                                                       ${an.lignes3[i]}
                                                                                       <br/>
                                                                                   </p>
                                                                               </div>
                                                                           </div>
                                                                               <br/>
                                                    </c:forEach>
                                                                              <div class="pages">
                                                                                   <span>Pages d'annonces : </span>
                                                                                   <c:forEach var="i" begin="${an.prem}" end="${an.der}" step="1">
                                                                                       <c:choose>
                                                                                       <c:when test="${an.page==i}">
                                                                                           <span>[<span class="clign"><c:out value="${i+1}"></c:out></span>]</span>
                                                                                       </c:when>
                                                                                       <c:otherwise>
                                                                                           <span>[<a href="./annonces-sous-cat-${an.idCategorie}-${an.idSousCategorie}-${i}-${an.encodedSousCat}-${an.encodedCat}.html" title="PAGE #<c:out value="${i+1}"></c:out>"><c:out value="${i+1}"></c:out></a>]</span>
                                                                                       </c:otherwise>
                                                                                       </c:choose>
                                                                                   </c:forEach>
                                                                               </div>
                                                                               <br/>
                                                         </c:if>
                                   </div>
                       </div>
                   </c:if>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
    </c:catch>
<c:if test="${not empty ex}">
    <html>
        <head>
            <title>Erreur !</title>
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
