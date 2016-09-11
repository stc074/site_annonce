<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>ADMINISTRATION</title>
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
<script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
</head>
    <body>
       <%@include file="./haut.jsp" %>
       <section>
           <h1>Megannonce - ADMINISTRATION - CGUs.</h1>
           <c:catch var="ex">
               <c:if test="${requestScope.cgus!=null}">
                   <c:set var="cg" value="${requestScope.cgus}" scope="page"></c:set>
                   <br/>
                   <div class="info"><c:out value="${cg.ligne1}"></c:out></div>
                   <br/>
                   <div id="form">
                       <c:if test="${not empty cg.errorMsg}">
                           <div class="erreur">
                               <div>Erreur(s) :</div>
                               <br/>
                               ${cg.errorMsg}
                           </div>
                       </c:if>
                       <fieldset>
                           <legend>Texte des CGUs</legend>
                           <form action="./edit-cgus.html#form" method="POST">
                               <label for="texte">Texte des CGUs :</label>
                               <br/>
                               <textarea name="texte" id="texte" rows="4" cols="20">${cg.texte}</textarea>
                               <br/>
                               <input type="submit" value="Valider" name="kermit" />
                           </form>
                       </fieldset>
                                   <script type="text/javascript">
                                       CKEDITOR.replace( 'texte' );
                                   </script>
                  </div>
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
