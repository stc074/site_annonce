<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ADMINISTRATION - Abus</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="../GFXs/favicon.png" />
<link href="../CSS/style.css" type="text/css" rel="stylesheet" />
</head>
    <body>
       <%@include file="./haut.jsp" %>
       <section>
           <h1>Megannonce - ADMINISTRATION - Abus.</h1>
           <c:catch var="ex">
               <c:if test="${requestScope.info!=null}">
                   <c:choose>
                       <c:when test="${requestScope.info==1}">
                           <div class="cadre2">
                               <div class="info">Abus inconnu !</div>
                           </div>
                       </c:when>
                   </c:choose>
               </c:if>
               <c:if test="${requestScope.abus!=null}">
                   <c:set var="ab" value="${requestScope.abus}" scope="page"></c:set>
                   <div class="cadre2">
                   <div>
                       <a href="<c:url value="./ignore-abus-${ab.id}.html"></c:url>" title="IGNORER CET ABUS" rel="nofollow">IGNORER CET ABUS</a>
                   </div>
                   <br/>
                   <div>
                       <a href="<c:url value="./efface-abus-${ab.id}.html"></c:url>" title="EFFACER CETTE ANNONCE" rel="nofollow">ÉFFACER CETTE ANNONCE</a>
                   </div>
                   <br/>
                   </div>
                   <h2><c:out value="${ab.titre}"></c:out></h2>
                   ${ab.description}
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
    </body>
</html>
