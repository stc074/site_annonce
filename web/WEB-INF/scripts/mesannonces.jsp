<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.Objet"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Mes annonces</title>
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
           <h1>Megannonce - Mes annonces.</h1>
           </header>
           <c:catch var="erreur">
           <c:if test="${info!=null}">
               <c:choose>
               <c:when test="${info==1}">
               <br/>
               <div class="cadre2">
                   <br/>
                   <div class="info">Vous Devez être connecté pour pouvoir accéder à vos annonces.</div>
                   <br/>
                   <div>
                       <a href="./inscription.html" rel="nofollo" title="S'INSCRIRE">S'INSCRIRE</a>
                   </div>
                   <br/>
               </div>
               <br/>
               </c:when>
               </c:choose>
           </c:if>
               <c:if test="${annoncesNormales!=null}">
                   <h2>Annonces classiques</h2>
                   <c:if test="${annoncesNormales.longArray==0}">
                       <div class="cadre2">
                           <div class="info">Aucune <strong>annonce enregistrée</strong>.</div>
                       </div>
                       <br/>
                   </c:if>
                       <c:if test="${annoncesNormales.longArray>0}">
                   <c:forEach var="i" begin="0" end="${annoncesNormales.longArray-1}">
                       <div class="cadre2">
                           <div>
                               <span>${annoncesNormales.arrayTitres[i]}</span>
                               <span>${annoncesNormales.arrayDates[i]}&rarr;</span>
                               <a href="./edit-annonce-1-${annoncesNormales.arrayIds[i]}.html" rel="nofollow" title="ÉDITER CETTE ANNONCE">ÉDITER CETTE ANNONCE</a>
                           </div>
                           <div>
                               <span>&rArr;</span>
                               <a href="./efface-annonce-1-${annoncesNormales.arrayIds[i]}.html" rel="nofollow" title="ÉFFACER CETTE ANNONCE">ÉFFACER CETTE ANNONCE</a>
                           </div>
                       </div>
                           <br/>
                   </c:forEach>
                       </c:if>
               </c:if>
                               <c:if test="${annoncesAuto!=null}">
                           <h2>Annonces auto</h2>
                           <c:if test="${annoncesAuto.longArray==0}">
                               <div class="cadre2">
                                   <div class="info">Aucune <strong>annonce auto</strong> enregistrée.</div>
                               </div>
                           </c:if>
                               <c:if test="${annoncesAuto.longArray>0}">
                                   <c:forEach var="i" begin="0" end="${annoncesAuto.longArray-1}">
                                       <div class="cadre2">
                                           <div>
                                           <span>${annoncesAuto.arrayTitres[i]}</span>
                                           <span>${annoncesAuto.arrayDates[i]}&rarr;</span>
                                           <a href="./edit-annonce-2-${annoncesAuto.arrayIds[i]}.html" rel="nofollow" title="ÉDITER CETTE ANNONCE">ÉDITER CETTE ANNONCE</a>
                                           </div>
                                           <div>
                                           <span>&rArr;</span>
                                           <a href="./efface-annonce-2-${annoncesAuto.arrayIds[i]}.html" rel="nofollow" title="ÉFFACER CETTE ANNONCE">ÉFFACER CETTE ANNONCE</a>
                                       </div>
                                       </div>
                                       <br/>
                                   </c:forEach>
           </c:if>
                               </c:if>
                                           <c:if test="${annoncesImmo!=null}">
                                               <h2>Annonces immobilières</h2>
                                               <c:if test="${annoncesImmo.longArray==0}">
                                                   <div class="cadre2">
                                                       <div class="info">Aucune <strong>annonces immobilières</strong> enregistrée</div>
                                                   </div>
                                                   <br/>
                                               </c:if>
                                               <c:if test="${annoncesImmo.longArray>0}">
                                                   <c:forEach var="i" begin="0" end="${annoncesImmo.longArray-1}">
                                                       <div class="cadre2">
                                                           <div>
                                                               <span>${annoncesImmo.arrayTitres[i]}</span>
                                                               <span>${annoncesImmo.arrayDates[i]}&rarr;</span>
                                                               <a href="./edit-annonce-3-${annoncesImmo.arrayIds[i]}.html" rel="nofollow" title="ÉDITER CETTE ANNONCE">ÉDITER CETTE ANNONCE</a>
                                                           </div>
                                                           <div>
                                                               <span>&rArr;</span>
                                                               <a href="./efface-annonce-3-${annoncesImmo.arrayIds[i]}.html" rel="nofollow" title="ÉFFACER CETTE ANNONCE">ÉFFACER CETTE ANNONCE</a>
                                                           </div>
                                                       </div>
                                                           <br/>
                                                   </c:forEach>
                                               </c:if>
                                           </c:if>
                                                               <c:if test="${annoncesEmploi!=null}">
                                                                   <h2>Annonces de l'emploi</h2>
                                                                   <c:if test="${annoncesEmploi.longArray==0}">
                                                                       <div class="cadre2">
                                                                           <div class="info">Aucune <strong>annonce de l'emploi</strong> enregistrée.</div>
                                                                       </div>
                                                                       <br/>
                                                                   </c:if>
                                                                   <c:if test="${annoncesEmploi.longArray>0}">
                                                                       <c:forEach var="i" begin="0" end="${annoncesEmploi.longArray-1}">
                                                                           <div class="cadre2">
                                                                               <div>
                                                                                   <span>${annoncesEmploi.arrayTitres[i]}</span>
                                                                                   <span>${annoncesEmploi.arrayDates[i]}&rarr;</span>
                                                                                   <a href="./edit-annonce-4-${annoncesEmploi.arrayIds[i]}.html" rel="nofollow" title="ÉDITER CETTE ANNONCE">ÉDITER CETTE ANNONCE</a>
                                                                               </div>
                                                                               <div>
                                                                                   <span>&rArr;</span>
                                                                                   <a href="./efface-annonce-4-${annoncesEmploi.arrayIds[i]}.html" rel="nofollow" title="ÉFFACER CETTE ANNONCE">ÉFFACER CETTE ANNONCE</a>
                                                                               </div>
                                                                           </div>
                                                                               <br/>
                                                                       </c:forEach>
                                                                   </c:if>
                                                               </c:if>
            </c:catch>
                               <c:if test="${not empty erreur}">
                                   <div class="erreur">
                                       <div>Erreur :</div>
                                       <br/>
                                       <div>${erreur.message}</div>
                                   </div>
                               </c:if>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
