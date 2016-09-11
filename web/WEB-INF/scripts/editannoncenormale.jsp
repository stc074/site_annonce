<%@page import="java.util.Calendar"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.AnnonceNormale"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Éditer mon annonce</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
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
           <h1>Megannonce - Éditer mon annonce - Contenu.</h1>
           </header>
           <%
           try {
               if(request.getAttribute("info")!=null) {
                   int info=Integer.parseInt(request.getAttribute("info").toString());
                   switch(info) {
                       case 1: %>
                       <br/>
                       <div class="cadre2">
                           <br/>
                           <div class="info">Vous devez être connecté pour pouvoir éditer votre <strong>annonce</strong>.</div>
                           <br/>
                           <div>
                               <a href="./inscription.html" title="S'INSCRIRE" rel="nofollow">S'INSCRIRE</a>
                           </div>
                           <br/>
                       </div>
                       <%
                       break;
                       case 2: %>
                       <br/>
                       <div class="cadre2">
                           <div class="info"><strong>Annonce</strong> inconnue !</div>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("annonce")!=null) {
                       AnnonceNormale annonce=(AnnonceNormale)request.getAttribute("annonce");
                       if(annonce.getTest()==0) {
                       Objet.getConnection();
                       Calendar cal=Calendar.getInstance();
                       cal.setTimeInMillis(annonce.getTimestamp());
                       %>
                       <div class="cadre2">
                           <br/>
                       <div class="info">Dernière modification, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
                       <br/>
                       <div class="info">Pour éditer et modifier votre <strong>annonce</strong>, utilisez le formulaire ci-desous :</div>
                       <br/>
                       </div>
                       <br/>
                       <div id="form">
                           <%
                           if(request.getParameter("kermit")!=null&&annonce.getErrorMsg().length()>0) { %>
                           <div class="erreur">
                               <div>Erreur(s) :</div>
                               <br/>
                               <%= annonce.getErrorMsg() %>
                           </div>
                           <br/>
                           <% } %>
                           <fieldset>
                               <legend>Éditer mon annonce</legend>
                               <form action="edit-annonce.html#form" method="POST">
                                   <p>
                                   <input type="hidden" name="idAnnonce" value="<%= annonce.getId()%>" />
                                   <input type="hidden" name="type" value="<%= annonce.getType()%>" />
                               <div>
                                   <input type="radio" name="mode" id="mode1" value="1" disabled="disabled"<% if(annonce.getMode()==1) out.print(" checked=\"checked\""); %> />
                                   <label for="mode1">&rarr;Vente</label>
                                   <input type="radio" name="mode" id="mode2" value="2" disabled="disabled"<% if(annonce.getMode()==2) out.print(" checked=\"checked\""); %> />
                                   <label for="mode2">&rarr;Achat</label>
                               </div>
                                   <div>
                                       <label for="idCategorie">Catégorie de votre annonce :</label>
                                       <select name="idCategorie" id="idCategorie" onchange="javascript:changeCategorie(this.value);">
                                           <option value="0"<% if(annonce.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                           <%
                                           String query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
                                           Statement state=Objet.getConn().createStatement();
                                           ResultSet result=state.executeQuery(query);
                                           while(result.next()) {
                                               long idCategorie=result.getLong("id");
                                               String categorie=result.getString("categorie");
                                               %>
                                               <option value="<%= idCategorie %>"<% if(annonce.getIdCategorie()==idCategorie) out.print(" selected=\"selected\""); %>><%= categorie %></option>
                                               <%
                                               }
                                           result.close();
                                           state.close();
                                           %>
                                       </select>
                                   </div>
                                       <div id="innerSousCategories">
                                           <label for="idSousCategorie">Sous-catégorie de votre annonce :</label>
                                           <select name="idSousCategorie" id="idSousCategorie">
                                               <option value="0"<% if(annonce.getIdSousCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                               <%
                                               if(annonce.getIdCategorie()!=0) {
                                                   query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                                                   PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                                   prepare.setLong(1, annonce.getIdCategorie());
                                                   result=prepare.executeQuery();
                                                   while(result.next()) {
                                                       long idSousCategorie=result.getLong("id");
                                                       String sousCategorie=result.getString("sous_categorie");
                                                       %>
                                                       <option value="<%= idSousCategorie %>"<% if(annonce.getIdSousCategorie()==idSousCategorie) out.print(" selected=\"selected\""); %>><%= sousCategorie %></option>
                                                       <%
                                                       }
                                                   result.close();
                                                   prepare.close();
                                                   }
                                           %>
                                           </select>
                                       </div>
                                           <div>
                                               <%
                                               switch(annonce.getMode()) {
                                                   case 1: %>
                                                   <label for="prix">Prix à la vente :</label>
                                                   <%
                                                   break;
                                                   case 2: %>
                                                   <label for="prix">Prix à l'achat souhaité :</label>
                                                   <%
                                                   break;
                                                   }
                                               %>
                                               <input type="text" name="prix" id="prix" value="<%= annonce.getPrix()%>" size="6" maxlength="10" />
                                               <span>&euro;</span>
                                           </div>
                                               <label for="titre">Titre de votre annonce :</label>
                                               <br/>
                                               <input type="text" name="titre" id="titre" value="<%= annonce.getTitre()%>" size="40" maxlength="80" />
                                               <br/>
                                               <label for="description">Description de votre annonce :</label>
                                               <br/>
                                               <textarea name="description" rows="4" cols="20"><%= annonce.getDescription()%></textarea>
                                               <br/>
                                               <br/>
                                               <div class="captcha"></div>
                                               <div class="captchaDroite">
                                                   <label for="captcha">&rarr;Copiez le code SVP&rarr;</label>
                                                   <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                               </div>
                                               <br/>
                                               <br/>
                                               <input type="submit" value="Valider" name="kermit" />
                                   </p>
                               </form>
                           </fieldset>
                       </div>
                                   <script type="text/javascript">
                                       CKEDITOR.replace( 'description' );
                                   </script>
                                   <%
                                               Objet.closeConnection();
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
       <%@include file="./footer.jsp" %>
    </body>
</html>
