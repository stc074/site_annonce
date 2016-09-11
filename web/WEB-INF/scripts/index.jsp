<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Megannonce - Petites annonces gratuites</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content="PJ"/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta charset="UTF-8" />
<meta name="google-site-verification" content="LtDYI9mRjAPLvKJLErMtop6-qWvdhWSNBFEmxl61C0A" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
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
           <h1>Megannonce - Les annonces gratuites !</h1>
           <c:catch var="ex">
           <p>Megannonce - est un service gratuit pour annoncer, trouvez des à présent les meilleures <a href="./petites-annonces.html" title="PETITES ANNONCES" class="impLien">PETITES ANNONCES</a>
               - <a href="./petites-annonces-auto.html" title="AUTOMOBILES" class="impLien">AUTOMOBILES</a> - <a href="./petites-annonces-immobilieres.html" title="IMMOBILIER" class="impLien">IMMOBILIER</a>
               et <a href="./petites-annonces-emploi.html" title="EMPLOI" class="impLien">EMPLOI</a>.</p>
               <br/>
                   <div>
               <c:if test="${requestScope.listeCategories!=null}">
                   <c:set var="listeCat" value="${requestScope.listeCategories}" scope="page"></c:set>
                   <div class="colonneGauche">
                   <nav>
                       <ul class="listeCategories">
                           <li>CATÉGORIES</li>
                           <c:forEach var="i" begin="0" end="${listeCat.arrayLengthCat-1}" step="1">
                               <li class="cat" onclick="javascript:window.location.href='${listeCat.urlsCat[i]}';">
                                   <a href="${listeCat.urlsCat[i]}" title="<c:out value="${listeCat.titresCat[i]}"></c:out>"><c:out value="${listeCat.titresCat[i]}"></c:out></a>
                               </li>
                           </c:forEach>
                       </ul>
                       <br/>
                       <ul class="listeCategories">
                           <li>AUTOMOBILE</li>
                           <li class="cat" onclick="javascript:window.location.href='./petites-annonces-auto-2-1.html#form';">
                               <a href="./petites-annonces-auto-2-1.html#form" title="VENTES AUTO">VENTES</a>
                           </li>
                               <li class="cat" onclick="javascript:window.location.href='./petites-annonces-auto-2-2.html#form';">
                                   <a href="./petites-annonces-auto-2-2.html#form" title="ACHATS AUTO">ACHATS</a>
                               </li>
                       </ul>
                               <br/>
                               <ul class="listeCategories">
                                   <li>IMMOBILIER</li>
                                   <li class="cat" onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-1.html#form';">
                                       <a href="./petites-annonces-immobilieres-2-1.html#form" title="VENTES IMMOBILIER">VENTES</a>
                                   </li>
                                   <li class="cat" onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-2.html#form';">
                                       <a href="./petites-annonces-immobilieres-2-2.html#form" title="LOCATIONS IMMOBILIÈRES">LOCATIONS</a>
                                   </li>
                                   <li class="cat" onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-3.html#form';">
                                       <a href="./petites-annonces-immobilieres-2-3.html#form" title="LOCATIONS SAISONNIÈRES">LOCATIONS SAISONNIÈRES</a>
                                   </li>
                                   <li class="cat" onclick="javascript:window.location.href='./petites-annonces-immobilieres-2-4.html#form';">
                                       <a href="./petites-annonces-immobilieres-2-4.html#form" title="ACHATS IMMOBILIER">ACHATS</a>
                                   </li>
                               </ul>
                               <br/>
                               <ul class="listeCategories">
                                   <li>EMPLOI</li>
                                   <li class="cat" onclick="javascript:window.location.href='./petites-annonces-emploi-2-1.html#form';">
                                       <a href="./petites-annonces-emploi-2-1.html#form" title="OFFRES D'EMPLOIS">OFFRES D'EMPLOIS</a>
                                   </li>
                                   <li class="cat" onclick="javascript:window.location.href='./petites-annonces-emploi-2-2.html#form';">
                                       <a href="./petites-annonces-emploi-2-2.html#form" title="DEMANDES D'EMPLOIS">DEMANDES D'EMPLOIS</a>
                                   </li>
                               </ul>
                   </nav>
                   </div>
               </c:if>
                                   <div class="colonneDroite">
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
                                       <p></p>
                                      <c:if test="${requestScope.membres!=null}">
                                           <c:set var="mems" value="${requestScope.membres}" scope="page"></c:set>
                                           <c:if test="${mems.nb>0}">
                                               <div class="cadre2">
                                                   <div>En tout : <c:out value="${mems.nb}"></c:out> inscrit(e)s.</div>
                                               </div>
                                               <p></p>
                                           </c:if>
                                       </c:if>
                                        <h1>Annonces à la une :</h1>
                                        <c:if test="${requestScope.annoncesNormales!=null}">
                                           <c:set var="an" value="${requestScope.annoncesNormales}" scope="page"></c:set>
                                           <h2>Petites annonces (en tout <c:out value="${an.nb}"></c:out> annonces)</h2>
                                           <c:choose>
                                               <c:when test="${an.nbAnnonces==0}">
                                                   <div class="cadre2">
                                                       <div class="info">Pas d'annonces enregistrée.</div>
                                                   </div>
                                                   <br/>
                                               </c:when>
                                               <c:otherwise>
                                                   <c:forEach var="i" begin="0" end="${an.nbAnnonces-1}">
                                                       <div class="listeAnnonce2" onclick="javascript:window.location.href='${an.urlsIndex[i]}#annonce';">
                                                           <div class="listeAnnonceGauche">
                                                               ${an.codesMiniIndex[i]}
                                                           </div>
                                                           <div class="listeAnnonceDroite">
                                                               <h1>
                                                                   <a href="${an.urlsIndex[i]}#annonce" title="<c:out value="${an.titresIndex[i]}"></c:out>"><c:out value="${an.titresIndex[i]}"></c:out></a>
                                                               </h1>
                                                               <p>
                                                                   ${an.lignes1Index[i]}
                                                                   <br/>
                                                                   ${an.lignes3Index[i]}
                                                                   <br/>
                                                                   ${an.lignes4Index[i]}
                                                                   <br/>
                                                                   <br/>
                                                                   ${an.lignes2Index[i]}
                                                                   <br/>
                                                               </p>
                                                           </div>
                                                       </div>
                                                               <br/>
                                                   </c:forEach>
                                               </c:otherwise>
                                           </c:choose>
                                       </c:if>
                                                               <br/>
                                                               <div class="lienAnnonces">
                                                                   <a href="./petites-annonces.html" title="TOUTES LES PETITES ANNONCES">TOUTES LES PETITES ANNONCES</a>
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
                                       <p></p>
                                      <c:if test="${requestScope.annoncesAuto!=null}">
                                           <c:set var="an" value="${requestScope.annoncesAuto}" scope="page"></c:set>
                                           <h2>Automobile (En tout <c:out value="${an.nb}"></c:out> annonces)</h2>
                                           <c:choose>
                                               <c:when test="${an.nbAnnonces==0}">
                                                   <div class="cadre2">
                                                       <div class="info">Aucune annonce auto enregistrée.</div>
                                                   </div>
                                                   <br/>
                                               </c:when>
                                               <c:otherwise>
                                                   <c:forEach var="i" begin="0" end="${an.nbAnnonces-1}" step="1">
                                                       <div class="listeAnnonce2" onclick="javascript:window.location.href='${an.urlsIndex[i]}#annonce';">
                                                           <div class="listeAnnonceGauche">
                                                               ${an.codesMiniIndex[i]}
                                                           </div>
                                                           <div class="listeAnnonceDroite">
                                                               <h1>
                                                                   <a href="${an.urlsIndex[i]}#annonce" title="<c:out value="${an.titresIndex[i]}"></c:out>"><c:out value="${an.titresIndex[i]}"></c:out></a>
                                                               </h1>
                                                               <p>
                                                                   ${an.lignes1Index[i]}
                                                                   <br/>
                                                                   ${an.lignes3Index[i]}
                                                                   <br/>
                                                                   ${an.lignes4Index[i]}
                                                                   <br/>
                                                                   <br/>
                                                                   ${an.lignes2Index[i]}
                                                                   <br/>
                                                               </p>
                                                           </div>
                                                       </div>
                                                           <br/>
                                                   </c:forEach>
                                               </c:otherwise>
                                           </c:choose>
                                       </c:if>
                                                           <br/>
                                                           <div class="lienAnnonces">
                                                               <a href="./petites-annonces-auto.html" title="TOUTES LES ANNONCES AUTO">TOUTES LES ANNONCES AUTO</a>
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
                                       <p></p>
                                       <c:if test="${requestScope.annoncesImmo!=null}">
                                           <c:set var="an" value="${requestScope.annoncesImmo}" scope="page"></c:set>
                                           <h2>Immobilier (En tout <c:out value="${an.nb}"></c:out> annonces)</h2>
                                           <c:choose>
                                               <c:when test="${an.nbAnnonces==0}">
                                                   <div class="cadre2">
                                                       <div class="info">Aucune annonce enregistrée.</div>
                                                   </div>
                                               </c:when>
                                               <c:otherwise>
                                                   <c:forEach var="i" begin="0" end="${an.nbAnnonces-1}" step="1">
                                                       <div class="listeAnnonce2" onclick="javascript:window.location.href='${an.urlsIndex[i]}#annonce';">
                                                           <div class="listeAnnonceGauche">
                                                               ${an.codesMiniIndex[i]}
                                                           </div>
                                                           <div class="listeAnnonceDroite">
                                                               <h1>
                                                                   <a href="${an.urlsIndex[i]}#annonce" title="<c:out value="${an.titresIndex[i]}"></c:out>"><c:out value="${an.titresIndex[i]}"></c:out></a>
                                                               </h1>
                                                               <p>
                                                                   ${an.lignes1Index[i]}
                                                                   <br/>
                                                                   ${an.lignes3Index[i]}
                                                                   <br/>
                                                                   ${an.lignes4Index[i]}
                                                                   <br/>
                                                                   <br/>
                                                                   ${an.lignes2Index[i]}
                                                                   <br/>
                                                               </p>
                                                           </div>
                                                       </div>
                                                           <br/>
                                                   </c:forEach>
                                               </c:otherwise>
                                           </c:choose>
                                       </c:if>
                                                           <br/>
                                                           <div class="lienAnnonces">
                                                               <a href="./petites-annonces-immobilieres.html" title="TOUTES LES ANNONCES IMMOBILIÈRES">TOUTES LES ANNONCES IMMOBILIÈRES</a>
                                                           </div>
                                                           <br/>
                                                   <c:if test="${requestScope.annoncesEmploi!=null}">
                                                       <c:set var="an" value="${requestScope.annoncesEmploi}" scope="page"></c:set>
                                                       <h2>Emploi (En tout <c:out value="${an.nb}"></c:out> annonces)</h2>
                                                       <c:choose>
                                                           <c:when test="${an.nbAnnonces==0}">
                                                               <div class="cadre2">
                                                                   <div class="info">Aucune annonce enregistrée.</div>
                                                               </div>
                                                               <br/>
                                                           </c:when>
                                                           <c:otherwise>
                                                               <c:forEach var="i" begin="0" end="${an.nbAnnonces-1}" step="1">
                                                                   <div class="listeAnnonce2" onclick="javascript:window.location.href='${an.urlsIndex[i]}#annonce';">
                                                                       <div class="listeAnnonceGauche">
                                                                           ${an.codesMiniIndex[i]}
                                                                       </div>
                                                                       <div class="listeAnnonceDroite">
                                                                           <h1>
                                                                               <a href="${an.urlsIndex[i]}#annonce" title="<c:out value="${an.titresIndex[i]}"></c:out>"><c:out value="${an.titresIndex[i]}"></c:out></a>
                                                                           </h1>
                                                                           <p>
                                                                               ${an.lignes1Index[i]}
                                                                               <br/>
                                                                               ${an.lignes3Index[i]}
                                                                               <br/>
                                                                               ${an.lignes4Index[i]}
                                                                               <br/>
                                                                               <br/>
                                                                               ${an.lignes2Index[i]}
                                                                               <br/>
                                                                           </p>
                                                                       </div>
                                                                   </div>
                                                                       <br/>
                                                               </c:forEach>
                                                           </c:otherwise>
                                                       </c:choose>
                                                   </c:if>
                                                                       <br/>
                                                                       <div class="lienAnnonces">
                                                                           <a href="./petites-annonces-emploi.html" title="TOUTES LES ANNONCES D'EMPLOI">TOUTES LES ANNONCES D'EMPLOI</a>
                                                                       </div>
                                                                       <br/>
                                     </div>
                   </div>
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
