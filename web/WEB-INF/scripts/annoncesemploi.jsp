<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<c:catch var="ex">
    <c:set var="rec" value="${null}" scope="page"></c:set>
<c:set var="tagTitle" value="Petites annonces gratuites de l'emploi" scope="page"></c:set>
<c:set var="tagDescription" value="Megannonce - Petites annonces gratuites de l'emploi"></c:set>
<c:if test="${requestScope.recherche!=null&&requestScope.liste!=null}">
    <c:set var="rec" value="${requestScope.recherche}" scope="page"></c:set>
    <c:set var="lst" value="${requestScope.liste}" scope="page"></c:set>
    <c:set var="tagTitle" value="${rec.tagTitle}"></c:set>
    <c:set var="tagDescription" value="${rec.tagDescription}"></c:set>
</c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${pageScope.tagTitle}"></c:out></title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="<c:out value="${pageScope.tagDescription}"></c:out>" />
<meta charset="UTF-8" />
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
           <h1>Megannonce - Emploi</h1>
           <c:if test="${rec!=null&&lst!=null}">
               <jsp:useBean class="classes.Datas" id="dat" scope="page"></jsp:useBean>
               <sql:setDataSource dataSource="jdbc/MyDB"></sql:setDataSource>
           <div class="info">Pour affiner votre recherche, utilisez le formulaire ci-dessous :</div>
           <br/>
           <div id="form">
               <fieldset>
                   <legend>Recherche d'<strong>annonces d'emploi</strong></legend>
                   <form action="./petites-annonces-emploi-1.html#form" method="POST">
                       <c:choose>
                           <c:when test="${rec.mode==1}">
                               <c:set var="label1" value=" recherché"></c:set>
                           </c:when>
                           <c:when test="${rec.mode==2}">
                               <c:set var="label1" value=""></c:set>
                           </c:when>
                       </c:choose>
                       <p>
                           <label for="motsCles">Recherche :</label>
                           <input type="text" name="motsCles" id="motsCles" value="${rec.motsCles}" size="30" maxlength="300" />
                           <br/>
                           <input type="radio" name="mode" id="mode1" value="1"<c:if test="${rec.mode==1}"> checked="checked"</c:if> onclick="javascript:window.location.href='./petites-annonces-emploi-2-1.html#form';" />
                           <label for="mode1">&rarr;Je cherche du travail&nbsp;</label>
                           <input type="radio" name="mode" id="mode2" value="2"<c:if test="${rec.mode==2}"> checked="checked"</c:if> onclick="javascript:window.location.href='./petites-annonces-emploi-2-2.html#form';" />
                           <label for="mode2">&rarr;Je cherche un employé&nbsp;</label>
                           <br/>
                           <label for="idCategorie">Catégorie de l'emploi<c:out value="${label1}"></c:out> :</label>
                           <select name="idCategorie" id="idCategorie" onchange="javascript:window.location.href='./petites-annonces-emploi-3-'+this.value+'.html#form';">
                               <option value="0"<c:if test="${rec.idCategorie==0}"> selected="selected"</c:if>>Toutes</option>
                               <c:forEach var="i" begin="0" end="${lst.arrayLength-1}" step="1">
                                   <option value="${lst.idsCategorie[i]}"<c:if test="${rec.idCategorie==lst.idsCategorie[i]}"> selected="selected"</c:if>><c:out value="${lst.categories[i]}"></c:out></option>
                               </c:forEach>
                           </select>
                               <br/>
                               <label for="typeContrat">Type de contrat<c:out value="${label1}"></c:out> :</label>
                               <select name="typeContrat" id="typeContrat" onchange="javascript:window.location.href='./petites-annonces-emploi-4-'+this.value+'.html#form';">
                                   <option value="0"<c:if test="${rec.typeContrat==0}"> selected="selected"</c:if>>Tous</option>
                                   <c:forEach begin="1" end="${dat.arrayTypesContratLength}" var="i" step="1">
                                       <option value="<c:out value="${i}"></c:out>"<c:if test="${rec.typeContrat==i}"> selected="selected"</c:if>><c:out value="${dat.arrayTypesContrat[i-1]}"></c:out></option>
                                   </c:forEach>
                               </select>
                                   <br/>
                                   <span>Tarif horaire :</span>
                                   <label for="tarifHoraireMin">Minimum &rarr;</label>
                                   <input type="text" name="tarifHoraireMin" id="tarifHoraireMin" value="<c:out value="${rec.tarifHoraireMin}"></c:out>" size="6" maxlength="10" />
                                   <span>&euro;&nbsp;</span>
                                   <label for="tarifHoraireMax">Maximum &rarr;</label>
                                   <input type="text" name="tarifHoraireMax" id="tarifHoraireMax" value="<c:out value="${rec.tarifHoraireMax}"></c:out>" size="6" maxlength="10" />
                                   <span>&euro;</span>
                                   <br/>
                                   <span>Localisation :</span>
                                   <label for="idRegion">Région &rarr;</label>
                                   <select name="idRegion" id="idRegion" onchange="javascript:window.location.href='./petites-annonces-emploi-5-'+this.value+'.html#form';">
                                       <option value="0"<c:if test="${rec.idRegion==0}"> selected="selected"</c:if>>Toutes</option>
                                       <c:forEach var="i" begin="0" end="${lst.regionLength-1}" step="1">
                                           <option value="<c:out value="${lst.idRegions[i]}"></c:out>"<c:if test="${rec.idRegion==lst.idRegions[i]}"> selected="selected"</c:if>><c:out value="${lst.regions[i]}"></c:out></option>
                                       </c:forEach>
                                   </select>
                                       <label for="idDepartement">Département &rarr;</label>
                                       <select name="idDepartement" id="idDepartement" onchange="javascript:window.location.href='./petites-annonces-emploi-6-'+this.value+'.html#form';">
                                           <option value="0"<c:if test="${rec.idDepartement==0}"> selected="selected"</c:if>>Tous</option>
                                           <c:if test="${rec.idRegion!=0}">
                                               <sql:query var="result" scope="page" sql="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement ASC">
                                                   <sql:param value="${rec.idRegion}"></sql:param>
                                               </sql:query>
                                               <c:forEach var="row" items="${result.rows}">
                                                   <option value="${row.id_departement}"<c:if test="${rec.idDepartement==row.id_departement}"> selected="selected"</c:if>><c:out value="${row.id_departement}"></c:out>-<c:out value="${row.departement}"></c:out></option>
                                               </c:forEach>
                                           </c:if>
                                        </select>
                                           <label for="idCommune">Commune &rarr;</label>
                                           <select name="idCommune" id="idCommune" onchange="javascript:window.location.href='./petites-annonces-emploi-7-'+this.value+'.html#form';">
                                               <option value="0"<c:if test="${rec.idCommune==0}"> selected="selected"</c:if>>Toutes</option>
                                               <c:if test="${rec.idRegion!=0&&rec.idDepartement!=0}">
                                                   <sql:query var="result" scope="page" sql="SELECT id,commune,code_postal FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune ASC">
                                                       <sql:param value="${rec.idRegion}"></sql:param>
                                                       <sql:param value="${rec.idDepartement}"></sql:param>
                                                   </sql:query>
                                                   <c:forEach var="row" items="${result.rows}">
                                                       <option value="${row.id}"<c:if test="${rec.idCommune==row.id}"> selected="selected"</c:if>><c:out value="${row.code_postal}"></c:out>-<c:out value="${row.commune}"></c:out></option>
                                                   </c:forEach>
                                               </c:if>
                                           </select>
                                               <br/>
                                   <input type="submit" value="Rechercher" name="kermit" />
                                   <input type="submit" value="Vider le formulaire" name="reset" />
                       </p>
                   </form>
               </fieldset>
           </div>
                                               <c:if test="${rec.nbAnnoncesPage==0}">
                                                   <br/>
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
                                                   <div class="info">Désolé, aucune annonce pour cette recherche.</div>
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
                                                   <c:if test="${rec.nbAnnoncesPage>0}">
                                                       <h2><c:out value="${rec.nbAnnonces}"></c:out> annonce(s) pour cette recherche</h2>
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
          <div class="listeAnnoncePub">
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
                                                        <c:forEach var="i" begin="0" end="${rec.nbAnnoncesPage-1}" step="1">
                                                           <div class="listeAnnonce" onclick="javascript:window.location.href='<c:out value="${rec.uris[i]}"></c:out>#annonce';">
                                                               <div class="listeAnnonceGauche">
                                                                   <c:if test="${not empty rec.extensions[i]}">
                                                                       <img src="./photo-mini-1-4-<c:out value="${rec.ids[i]}"></c:out>-1<c:out value="${rec.extensions[i]}"></c:out>" width="<c:out value="${rec.largeurs[i]}"></c:out>" height="<c:out value="${hauteurs[i]}"></c:out>" alt="miniature"/>
                                                                   </c:if>
                                                                   <c:if test="${empty rec.extensions[i]}">
                                                                       <img src="./GFXs/miniature.png" width="100" height="100" alt="miniature"/>
                                                                   </c:if>
                                                               </div>
                                                               <div class="listeAnnonceDroite">
                                                                   <h1>
                                                                       <a href="${rec.uris[i]}#annonce" title="<c:out value="${rec.titres[i]}"></c:out>"><c:out value="${rec.titres[i]}"></c:out></a>
                                                                   </h1>
                                                                           <p>
                                                                           <c:out value="${rec.lignes1[i]}"></c:out>
                                                                           <br/>
                                                                           <c:out value="${rec.lignes3[i]}"></c:out>
                                                                           <br/>
                                                                           <c:out value="${rec.lignes4[i]}"></c:out>
                                                                           <br/>
                                                                           <br/>
                                                                           <c:out value="${rec.lignes2[i]}"></c:out>
                                                                           <br/>
                                                                           </p>
                                                               </div>
                                                           </div>
                                                           <br/>
                                                       </c:forEach>
                                                               <div class="pages">
                                                                   <span>Pages d'annonces :</span>
                                                                   <c:forEach var="i" begin="${rec.prem}" end="${rec.der}" step="1">
                                                                       <c:if test="${i==rec.page}">
                                                                           <span>[<span class="clign"><c:out value="${i+1}"></c:out></span>]</span>
                                                                       </c:if>
                                                                       <c:if test="${i!=rec.page}">
                                                                           <span>[<a href="./petites-annonces-emploi-8-${i}.html#form" title="PAGE #<c:out value="${i+1}"></c:out>"><c:out value="${i+1}"></c:out></a>]</span>
                                                                       </c:if>
                                                                   </c:forEach>
                                                               </div>
                                                           <br/>
                                                   </c:if>
           </c:if>
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
                    <div>Erreur</div>
                    <br/>
                    <div><c:out value="${ex.message}"></c:out></div>
                </div>
            </div>
        </body>
    </html>
</c:if>
