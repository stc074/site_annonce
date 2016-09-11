<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Infos personnelles</title>
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
           <h1>Megannonce - Infos personnelles.</h1>
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
                           <div class="info">Vous devez être connecté pour pouvoir accèder à vos infos persos.</div>
                           <br/>
                           <div>
                               <a href="./inscription.html" title="S'INSCRIRE" rel="nofollow">S'INSCRIRE</a>
                           </div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("membre")!=null) {
                       Membre membre=(Membre)request.getAttribute("membre");
                       Calendar cal=Calendar.getInstance();
                       cal.setTimeInMillis(membre.getTimestamp());
                       %>
                       <p>Vous pouvez modifier les informations de votre compte, remplissez le champ MOT DE PASSE uniquement en cas de changement.</p>
                       <div class="cadre2">
                       <br/>
                       <div class="info">Dernières modifications, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
                       <br/>
                       </div>
                       <br/>
                       <div id="form">
                           <%
                           if(membre.getTest()==1) { %>
                           <br/>
                           <div class="cadre2">
                               <div class="info">Modifications enregistrées !</div>
                           </div>
                           <br/>
                           <%
                           membre.setTest(0);
                           }
                           if(request.getParameter("kermit")!=null&&membre.getErrorMsg().length()>0) { %>
                           <div class="erreur">
                               <div>Erreur(s) :</div>
                               <br/>
                               <%= membre.getErrorMsg() %>
                           </div>
                           <br/>
                           <% } %>
                           <fieldset>
                               <legend>Infos personnelles</legend>
                               <form action="./infos-personnelles.html#form" method="POST">
                                   <p>
                                   <label for="pseudo">Mon PSEUDONYME :</label>
                                   <br/>
                                   <input type="text" name="pseudo" id="pseudo" value="<%= membre.getPseudo()%>" size="15" readonly="readonly" disabled="disabled" maxlength="20" />
                                   <br/>
                                   <label for="email">Mon ADRESSE EMAIL :</label>
                                   <br/>
                                   <input type="text" name="email" id="email" value="<%= membre.getEmail()%>" size="40" readonly="readonly" disabled="disabled" maxlength="200" />
                                   <br/>
                                   <label for="motDePasse">Nouveau MOT DE PASSE [Caractères alphanumériques] :</label>
                                   <br/>
                                   <input type="password" name="motDePasse" id="motDePasse" value="" size="15" maxlength="15" />
                                   <br/>
                                   <label for="motDePasse2">Confirmation du nouveau MOT DE PASSE :</label>
                                   <br/>
                                   <input type="password" name="motDePasse2" id="motDePasse2" value="" size="15" maxlength="15" />
                                   <br/>
                                   <div>Localisation :</div>
                                   <label for="idRegion">Votre région :</label>
                                   <select name="idRegion" id="idRegion" onchange="javascript:changeRegion(this.value);">
                                       <option value="0"<% if(membre.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Choississez</option>
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
                                               query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY id_departement ASC";
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
                                                   query="SELECT id,commune,code_postal FROM table_communes WHERE id_region=? AND id_departement=? ORDER BY commune ASC";
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
                                               <input type="text" name="captcha" value="" size="5" maxlength="5" />
                                           </div>
                                           <br/>
                                           <br/>
                                           <input type="submit" value="Valider" name="kermit" />
                                   </p>
                               </form>
                           </fieldset>
                       </div>
                       <%
                                           Objet.closeConnection();
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
