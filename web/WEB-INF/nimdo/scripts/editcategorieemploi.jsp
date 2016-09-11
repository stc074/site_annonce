<%@page import="classes.CategorieEmploi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ADMINISTRATION - Edit categorie emploi</title>
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
           <h1>Megannonce - ADMINISTRATION - Editer une categorie emploi.</h1>
           <%
           try {
               if(request.getAttribute("categorie")!=null) {
                   CategorieEmploi cat=(CategorieEmploi)request.getAttribute("categorie");
                   %>
                   <div id="form">
                       <%
                       if(cat.getTest()==1) { %>
                       <br/>
                       <div class="cadre2">
                           <div class="info">Catégorie d'emploi "<%= cat.getCategorie() %>" enregistrée.</div>
                       </div>
                       <br/>
                       <%
                                             }
                       if(request.getParameter("kermit")!=null&&cat.getErrorMsg().length()>0) { %>
                       <div class="erreur">
                           <div>Erreur(s) :</div>
                           <br/>
                           <%= cat.getErrorMsg() %>
                       </div>
                       <% } %>
                       <fieldset>
                           <legend>Nouvelle catégorie d'emploi</legend>
                           <form action="./edit-categorie-emploi.html#form" method="POST">
                               <label for="categorie">Nouvelle catégorie d'emploi</label>
                               <input type="text" name="categorie" id="categorie" value="" size="40" maxlength="100" />
                               <br/>
                               <input type="submit" value="Valider" name="kermit" />
                           </form>
                       </fieldset>
                   </div>
                   <h2>Liste des catégories d'emploi</h2>
                   <%
                   for(int i=0; i<cat.getListeCategorie().length;i++) {
                       %>
                       <div><%= cat.getListeCategorie()[i] %></div>
                       <br/>
                       <%
                   }
                   if(cat.getListeCategorie().length==0) { %>
                   <br/>
                   <div class="cadre2">
                       <div class="info">Aucune catégorie d'emploi encore enregistré.</div>
                   </div>
                   <br/>
                   <%
                                     }
                    }
                   } catch(Exception ex) { %>
                   <br/>
                   <div class="erreur">
                       <div>Erreur :</div>
                       <br/>
                       <div><%= ex.getMessage() %></div>
                   </div>
                   <br/>
                   <% } %>
       </section>
    </body>
</html>
