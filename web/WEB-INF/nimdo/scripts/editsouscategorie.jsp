<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Categorie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>ADMINISTRATION - Edit sous-categorie</title>
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
           <h1>Megannonce - ADMINISTRATION - Editer des sous-catégories.</h1>
           <%
           try {
               if(request.getAttribute("cat")!=null) {
                   Categorie cat=(Categorie)request.getAttribute("cat");
                   %>
                   <div class="info">Formulaire pour editer une sous-categorie :</div>
                   <br/>
                   <div id="form">
                       <%
                       if(cat.getTest()==1) { %>
                       <div class="cadre2">
                           <div class="info">Sous-catégorie "<%= cat.getSousCategorie() %>" enregistrée pour la catégorie "<%= cat.getCategorie() %>".</div>
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
                           <legend>Nouvelle sous-catégorie</legend>
                           <form action="./edit-sous-categorie.html#form" method="POST">
                               <p>
                                   <label for="idCategorie">Catégorie : </label>
                                   <select name="idCategorie" id="idCategorie" onchange="javascript:window.location.href='./edit-sous-categorie-'+this.value+'.html';">
                                       <option value="0"<% if(cat.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                       <%
                                       Objet.getConnection();
                                       String query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
                                       Statement state=Objet.getConn().createStatement();
                                       ResultSet result=state.executeQuery(query);
                                       while(result.next()) {
                                           long idCategorie=result.getLong("id");
                                           String categorie=result.getString("categorie");
                                           %>
                                           <option value="<%= idCategorie %>"<% if(cat.getIdCategorie()==idCategorie) out.print(" selected=\"selected\""); %>><%= categorie %></option>
                                           <%
                                           }
                                       result.close();
                                       state.close();
                                       %>
                                   </select>
                                   <br/>
                                   <label for="sousCategorie">Nouvelle sous-catégorie :</label>
                                   <br/>
                                   <input type="text" name="sousCategorie" value="" size="40" maxlength="100" />
                                   <br/>
                                   <input type="submit" value="Valider" name="kermit" />
                               </p>
                           </form>
                       </fieldset>
                   </div>
                                   <%
                                   if(cat.getIdCategorie()!=0) { %>
                                   <h2>Sous-catégories de la catégorie "<%= cat.getCategorie() %>".</h2>
                                   <%
                                   query="SELECT sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                                   PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                   prepare.setLong(1, cat.getIdCategorie());
                                   result=prepare.executeQuery();
                                   int nbSousCats=0;
                                   while(result.next()) {
                                       nbSousCats++;
                                       String sousCategorie=result.getString("sous_categorie");
                                       %>
                                       <div><%= sousCategorie %></div>
                                       <br/>
                                       <%
                                       }
                                   result.close();
                                   prepare.close();
                                   if(nbSousCats==0) { %>
                                   <br/>
                                   <div class="cadre2">
                                       <div class="info">Aucune sous-catégorie enregistrée dans la catégorie "<%= cat.getCategorie() %>".</div>
                                   </div>
                                   <br/>
                                   <%
                                   }
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
