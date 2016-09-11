<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Éditer mon annonce d'emploi</title>
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
           <h1>Megannonce - Éditer mon annonce de l'emploi - Contenu.</h1>
           </header>
           <c:catch var="ex">
               <c:if test="${info!=null}">
                   <c:choose>
                       <c:when test="${info==1}">
                           <br/>
                           <div class="cadre2">
                               <br/>
                               <div class="info">Vous devez être connecté pour pouvoir éditer vos <strong>annonces</strong>.</div>
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
                               <div class="info">Annonce inconnue !</div>
                           </div>
                           <br/>
                       </c:when>
                   </c:choose>
               </c:if>
                       <c:if test="${annonce!=null}">
                           <jsp:useBean id="Datas" class="classes.Datas"></jsp:useBean>
                           <c:set var="an" value="${annonce}" scope="page"></c:set>
                           <div class="info"><c:out value="${pageScope.an.dateString}"></c:out></div>
                           <br/>
                       </c:if>
                       <c:choose>
                           <c:when test="${pageScope.an.mode==1}">
                               <c:set var="modeTxt" value="Offre d'emploi" scope="page"></c:set>
                               <c:set var="label1" value="" scope="page"></c:set>
                           </c:when>
                           <c:when test="${pageScope.an.mode==2}">
                               <c:set var="modeTxt" value="Demande d'emploi" scope="page"></c:set>
                               <c:set var="label1" value=" souhaité" scope="page"></c:set>
                           </c:when>
                       </c:choose>
                           <h2><c:out value="${pageScope.modeTxt}"></c:out> <c:out value="${pageScope.an.categorieEmploi}"></c:out></h2>
                           <div class="info">Pour modifire le contenu de votre annonce, utilisez le formulaire ci-dessous:</div>
                           <br/>
                           <div id="form">
                               <c:if test="${not empty pageScope.an.errorMsg}">
                                   <div class="erreur">
                                       <div>Erreur(s) :</div>
                                       <br/>
                                       ${pageScope.an.errorMsg}
                                   </div>
                                   <br/>
                               </c:if>
                               <fieldset>
                                   <legend>Contenu de l'annonce</legend>
                                   <form action="./edit-annonce.html#form" method="POST">
                                       <p>
                                           <input type="hidden" name="idAnnonce" value="<c:out value="${annonce.id}"></c:out>" />
                                           <input type="hidden" name="type" value="4" />
                                       <label for="typeContrat">Type de contrat :</label>
                                       <select name="typeContrat" id="typeContrat">
                                           <c:set var="typeContratCheck" value="" scope="page"></c:set>
                                           <c:if test="${pageScope.an.typeContrat==0}">
                                               <c:set var="typeContratCheck" value=" selected=\"selected\"" scope="page"></c:set>
                                           </c:if>
                                           <option value="0"${pageScope.typeContratCheck}>Choisissez</option>
                                           <c:forEach var="i" begin="0" end="${Datas.arrayTypesContratLength-1}">
                                               <c:if test="${i+1==an.typeContrat}">
                                                   <c:set var="typeContratCheck" value=" selected=\"selected\""></c:set>
                                               </c:if>
                                               <option value="<c:out value="${i+1}"></c:out>"${pageScope.typeContratCheck}><c:out value="${Datas.arrayTypesContrat[i]}"></c:out></option>
                                               <c:set var="typeContratCheck" value=""></c:set>
                                           </c:forEach>
                                       </select>
                                           <br/>
                                           <label for="tarifHoraire">Tarif horaire<c:out value="${pageScope.label1}"></c:out> :</label>
                                           <input type="text" name="tarifHoraire" id="tarifHoraire" value="<c:out value="${pageScope.an.tarifHoraire}"></c:out>" size="6" maxlength="10" />
                                           <span>&euro;</span>
                                           <br/>
                                           <label for="titre">Titre de votre annonce :</label>
                                           <br/>
                                           <input type="text" name="titre" id="titre" value="<c:out value="${pageScope.an.titre}"></c:out>" size="30" maxlength="80" />
                                           <br/>
                                           <label for="description">Description de votre annonce :</label>
                                           <br/>
                                           <textarea name="description" id="description" rows="4" cols="20">${pageScope.an.description}</textarea>
                                           <br/>
                                           <br/>
                                           <div class="captcha"></div>
                                           <div class="captchaDroite">
                                               <label for="captcha">&rarr;Copiez le code SVP&rarr;</label>
                                               <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                           </div>
                                           <br/>
                                           <input type="submit" value="Valider" name="kermit" />
                                           <br/>
                                       </p>
                                   </form>
                               </fieldset>
                               <script type="text/javascript">
                                   CKEDITOR.replace( 'description' );
                               </script>
      </div>
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
