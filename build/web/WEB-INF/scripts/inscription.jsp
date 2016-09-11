<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Inscription</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
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
           <h1>Megannonce - Inscription.</h1>
           </header>
           <%
           try {
               if(request.getAttribute("membre")!=null) {
                   Membre membre=(Membre)request.getAttribute("membre");
                   if(membre.getTest()==0) {
                   %>
                   <p>Pour vous inscrire en 2 minutes et profitez de toutes les fonctionnalités du site,<br/> c'est facile, utilisez le formulaire ci-dessous:</p>
                   <br/>
                   <div id="form">
                       <%
                       if(request.getParameter("kermit")!=null&&membre.getErrorMsg().length()>0) { %>
                       <div class="erreur">
                           <div>Erreur(s) :</div>
                           <br/>
                           <div><%= membre.getErrorMsg() %></div>
                       </div>
                       <br/>
                       <% } %>
                       <fieldset>
                           <legend>Formulaire d'inscription</legend>
                           <form action="./inscription.html#form" method="POST">
                               <p>
                               <label for="pseudo">Choisissez un PSEUDONYME [Caractères alphanumériques] :</label>
                               <br/>
                               <input type="text" name="pseudo" id="pseudo" value="<%= membre.getPseudo()%>" size="15" maxlength="20" />
                               <br/>
                               <label for="email">Votre ADRESSE EMAIL :</label>
                               <br/>
                               <input type="text" name="email" id="email" value="<%= membre.getEmail()%>" size="40" maxlength="200" />
                               <br/>
                               <label for="motDePasse">Choisissez un MOT DE PASSE [Caractères alphanumériques] :</label>
                               <br/>
                               <input type="password" name="motDePasse" id="motDePasse" value="" size="15" maxlength="15" />
                               <br/>
                               <label for="motDePasse2">Confirmation du MOT DE PASSE :</label>
                               <br/>
                               <input type="password" name="motDePasse2" id="motDePasse2" value="" size="15" maxlength="15" />
                               <div>Localisation :</div>
                               <label for="idRegion">Votre région : </label>
                               <select name="idRegion" id="idRegion" onchange="javascript:changeRegion(this.value);">
                                   <option value="0"<% if(membre.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                   <%
                                   Objet.getConnection();
                                   String query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
                                   Statement state=Objet.getConn().createStatement();
                                   ResultSet result=state.executeQuery(query);
                                   while(result.next()) {
                                       String idRegion=result.getString("id_region");
                                       String region=result.getString("region");
                                       %>
                                       <option value="<%= idRegion %>"<% if(membre.getIdRegion().equals(idRegion)) out.print(" selected=\"selected\""); %>><%= region %></option>
                                       <%
                                       }
                                   result.close();
                                   state.close();
                                   %>
                               </select>
                               <br/>
                               <div id="innerDepartements">
                               <label for="idDepartement">Votre département :</label>
                               <select name="idDepartement" id="idDepartement" onchange="javascript:changeDepartement(this.value);">
                                   <option value="0"<% if(membre.getIdDepartement().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                   <%
                                   if(!membre.getIdRegion().equals("0")) {
                                       query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement";
                                       PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                       prepare.setString(1, membre.getIdRegion());
                                       result=prepare.executeQuery();
                                       while(result.next()) {
                                           String idDepartement=result.getString("id_departement");
                                           String departement=result.getString("departement");
                                           %>
                                           <option value="<%= idDepartement %>"<% if(membre.getIdDepartement().equals(idDepartement)) out.print(" selected=\"selected\""); %>><%= idDepartement %>-<%= departement %></option>
                                           <%
                                           }
                                       result.close();
                                       prepare.close();
                                       }
                                       %>
                               </select>
                               <br/>
                               <div id="innerCommunes">
                                   <label for="idCommune">Votre commune :</label>
                                   <select name="idCommune" id="idCommune">
                                       <option value="0"<% if(membre.getIdCommune()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                       <%
                                       if((!membre.getIdRegion().equals("0"))&&(!membre.getIdDepartement().equals("0"))) {
                                           query="SELECT id,commune,code_postal FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune";
                                           PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                           prepare.setString(1, membre.getIdRegion());
                                           prepare.setString(2, membre.getIdDepartement());
                                           result=prepare.executeQuery();
                                           while(result.next()) {
                                               int idCommune=result.getInt("id");
                                               String commune=result.getString("commune");
                                               String codePostal=result.getString("code_postal");
                                               %>
                                               <option value="<%= idCommune %>"<% if(membre.getIdCommune()==idCommune) out.print(" selected=\"selected\""); %>><%= codePostal %>-<%= commune %></option>
                                               <%
                                               }
                                           result.close();
                                           prepare.close();
                                           }
                                   %>
                                   </select>
                               </div>
                               </div>
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
                                   <br/>
                               </p>
                           </form>
                       </fieldset>
                   </div>
                               <%
                               Objet.closeConnection();
                               } else if(membre.getTest()==1) { %>
                               <br/>
                               <div class="cadre">
                                   <br/>
                                   <div class="info">Vous êtes désormais inscrit !</div>
                                   <br/>
                               </div>
                               <br/>
                               <%
                               membre.blank();
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
