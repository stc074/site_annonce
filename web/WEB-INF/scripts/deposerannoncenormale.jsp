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
<title>Petites annonces gratuites - Megannonce - Déposer une annonce - Contenu</title>
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
           <h1>Megannonce - Déposer une annonce - Contenu.</h1>
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
                           <div class="info">Vous devez vous identifier avant de poster une <strong>annonce</strong>.</div>
                           <br/>
                           <div>
                               <a href="./deposer-annonce-1.html" title="S'IDENTIFIER" rel="nofollow">S'IDENTIFIER</a>
                           </div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("annonce")!=null) {
                       AnnonceNormale annonce=(AnnonceNormale)request.getAttribute("annonce");
                       if(annonce.getTest()==0) {
                       %>
                       <br/>
                       <div class="info">Utilisez le formulaire ci-dessous pour le contenu de votre <strong>annonce</strong>.</div>
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
                               <legend>Contenu de mon annonce</legend>
                               <form action="./deposer-annonce-normale.html#form" method="POST">
                                   <p>
                                       <div>
                                           <span>Vous voulez :</span>
                                           <input type="radio" name="mode" id="mode1" value="1"<% if(annonce.getMode()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-normale-1-1.html#form';" />
                                           <label for="mode1">Vendre&nbsp;</label>
                                           <input type="radio" name="mode" id="mode2" value="2"<% if(annonce.getMode()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-normale-1-2.html#form';" />
                                           <label for="mode2">Acheter&nbsp;</label>
                                       </div>
                                           <%
                                           if(annonce.getMode()!=0) { %>
                                           <label for="idCategorie">Catégorie de votre annonce :</label>
                                           <select name="idCategorie" id="idCategorie" onchange="javascript:changeCategorie(this.value);">
                                               <option value="0"<% if(annonce.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                               <%
                                               Objet.getConnection();
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
                                           <%
                                           switch(annonce.getMode()) {
                                               case 1: %>
                                               <label for="prix">Prix à la vente :</label>
                                               <%
                                               break;
                                               case 2: %>
                                               <label for="prix">Prix souhaité à l'achat :</label>
                                               <%
                                               break;
                                               }
                                           %>
                                           <input type="text" name="prix" id="prix" value="<%= annonce.getPrix()%>" size="6" maxlength="10" />
                                           <span>&euro;</span>
                                           <br/>
                                           <label for="titre">Titre de votre annonce :</label>
                                           <br/>
                                           <input type="text" name="titre" id="titre" value="<%= annonce.getTitre()%>" size="30" maxlength="80" />
                                           <br/>
                                           <label for="description">Description de votre annonce :</label>
                                           <br/>
                                           <textarea name="description" id="description" rows="4" cols="20"><%= annonce.getDescription()%></textarea>
                                           <br/>
                                           <div class="captcha"></div>
                                           <div class="captchaDroite">
                                               <label for="captcha">&rarr;Copiez le code SVP&rarr;</label>
                                               <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                           </div>
                                           <br/>
                                           <br/>
                                                  <div>
                                                      <input type="radio" name="cgu" id="cgu" value="1" checked="checked" />
                                                      <label for="cgu">En validant ce formulaire, je déclare avoir pris connaissance des conditions générales d'utilisation de ce site et les accepter.</label>
                                                  </div>
                                                  <br/>
                                           <input type="submit" value="Valider" name="kermit" />
                                           <%
                                            Objet.closeConnection();
                                        }
                                               %>
                                   </p>
                               </form>
                           </fieldset>
                       </div>
                                   <script type="text/javascript">
                                       CKEDITOR.replace( 'description' );
                                   </script>
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
       <%@include file="./footer.jsp" %>
    </body>
</html>
