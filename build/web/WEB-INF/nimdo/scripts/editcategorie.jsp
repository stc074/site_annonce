<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Categorie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ADMINISTRATION - Edit categorie</title>
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
           <h1>Megannonce - ADMINISTRATION - Editer categorie.</h1>
           <%
           try {
               if(request.getAttribute("categorie")!=null) {
                   Categorie cat=(Categorie)request.getAttribute("categorie");
                   %>
                   <br/>
                   <div class="info">Enregistrer une nouvelle catégorie :</div>
                   <br/>
                   <div id="form">
                       <%
                       if(cat.getTest()==1) { %>
                       <div class="cadre2">
                           <div class="info">Catégorie "<%= cat.getCategorie() %>" enregistrée !</div>
                       </div>
                       <br/>
                       <%
                       cat.blank();
                       }
                       if(request.getParameter("kermit")!=null&&cat.getErrorMsg().length()>0) { %>
                       <div class="erreur">
                           <div>Erreur(s) :</div>
                           <br/>
                           <%= cat.getErrorMsg() %>
                       </div>
                       <% } %>
                       <fieldset>
                           <legend>Nouvelle catégorie</legend>
                           <form action="./edit-categorie.html#form" method="POST">
                               <p>
                               <label for="categorie">Nouvelle CATÉGORIE :</label>
                               <br/>
                               <input type="text" name="categorie" value="" size="40" maxlength="80" />
                               <br/>
                               <input type="submit" value="Valider" name="kermit" />
                               </p>
                           </form>
                       </fieldset>
                   </div>
                   <h2>Liste des catégories enregistrées</h2>
                   <%
                   Objet.getConnection();
                   String query="SELECT categorie FROM table_categories ORDER BY categorie ASC";
                   Statement state=Objet.getConn().createStatement();
                   ResultSet result=state.executeQuery(query);
                   int nbCats=0;
                   while(result.next()) {
                       nbCats++;
                       String categorie=result.getString("categorie");
                       %>
                       <div><%= categorie %></div>
                       <%
                       }
                   result.close();
                   state.close();
                   if(nbCats==0) { %>
                   <br/>
                   <div class="cadre2">
                       <br/>
                       <div class="info">Aucune catégorie enregistrée.</div>
                       <br/>
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
