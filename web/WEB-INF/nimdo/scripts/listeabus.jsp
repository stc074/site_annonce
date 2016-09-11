<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<title>ADMINISTRATION - Liste des abus</title>
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
           <h1>Megannonce - ADMINISTRATION - Liste des abus.</h1>
           <c:catch var="ex">
               <c:if test="${requestScope.abus!=null}">
                   <c:set var="ab" value="${requestScope.abus}" scope="page"></c:set>
                   <c:if test="${ab.nbAbus==0}">
                       <div class="cadre2">
                           <div class="info">Aucun abus enregistré.</div>
                       </div>
                   </c:if>
                   <c:if test="${ab.nbAbus>0}">
                   <c:forEach var="i" begin="0" end="${ab.nbAbus-1}" step="1">
                       <div class="cadre2">
                           <div>
                               <a href="<c:url value="${ab.urls[i]}"></c:url>"><c:out value="${ab.titres[i]}"></c:out></a>
                               <span>&rarr;<c:out value="${ab.comments[i]}"></c:out></span>
                           </div>
                       </div>
                           <br/>
                   </c:forEach>
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
    </body>
</html>
