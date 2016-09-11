<%@page import="classes.AnnonceAuto"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Déposer une annonce auto - Contenu</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
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
           <h1>Megannonce - Déposer une annonce auto - Contenu.</h1>
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
                       AnnonceAuto annonce=(AnnonceAuto)request.getAttribute("annonce");
                       int i=0;
                       if(annonce.getTest()==0) {
                           %>
                           <div class="cadre2">
                           <div class="info">Pour remplir le contenu de votre <strong>annonce</strong> de véhicule, utilisez le formulaire ci-dessous :</div>
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
                                   <legend>Contenu de mon <strong>annonce</strong></legend>
                                   <form action="./deposer-annonce-auto.html#form" method="POST">
                                       <p>
                                           <div>
                                               <span>Je souhaite : </span>
                                               <input type="radio" name="mode" id="mode1" value="1"<% if(annonce.getMode()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-auto-1-1-<%= annonce.getTypeVehicule() %>.html#form';" />
                                               <label for="mode1">Vendre&nbsp;</label>
                                               <input type="radio" name="mode" id="mode2" value="2"<% if(annonce.getMode()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-auto-1-2-<%= annonce.getTypeVehicule() %>.html#form';" />
                                               <label for="mode2">Acheter&nbsp;</label>
                                           </div>
                                               <%
                                               if(annonce.getMode()!=0) { %>
                                               <div>
                                                   <%
                                                   switch(annonce.getMode()) {
                                                       case 1: %>
                                                        <span>Type de véhicule que vous souhaitez vendre : </span>
                                                        <%
                                                        break;
                                                        case 2: %>
                                                        <span>Type de véhicule que vous souhaitez acheter : </span>
                                                        <%
                                                        break;
                                                        }
                                                   %>
                                                   <br/>
                                                   <%
                                                   i=0;
                                                   for(String typeVehicule:Datas.arrayTypeVehicules) {
                                                       i++;
                                                       %>
                                                       <input type="radio" name="typeVehicule" id="typeVehicule<%= i %>" value="<%= i%>"<% if(annonce.getTypeVehicule()==i) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-auto-1-<%= annonce.getMode() %>-<%= i %>.html#form';" />
                                                       <label for="typeVehicule<%= i %>">&rarr;<%= typeVehicule %></label>
                                                       <%
                                                       }
                                                   %>
                                               </div>
                                               <%
                                               } if(annonce.getMode()!=0&&annonce.getTypeVehicule()!=0) {
                                                   String label="";
                                                   String label2="";
                                                   String label3="";
                                                   String label4="";
                                                   switch(annonce.getMode()) {
                                                       case 1:
                                                           label="que vous souhaitez vendre";
                                                           label2="à la vente souhaité";
                                                           label3="";
                                                           label4="";
                                                           break;
                                                           case 2:
                                                               label="que vous souhaitez acheter";
                                                               label2="à l'achat souhaité";
                                                               label3=" souhaité";
                                                               label4=" souhaitée";
                                                               break;
                                                               }
                                                  switch(annonce.getTypeVehicule()) {
                                                      case 1:
                                                      case 2:
                                                      case 3:
                                                      case 4:
                                                      case 5:
                                                      case 6:
                                                      case 7:
                                                      case 8:
                                                      case 9:
                                                      case 10: %>
                                                      <label for="marque">Marque du véhicule <%= label %> : </label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      case 11: %>
                                                      <label for="typeMoto">Type de moto <%= label %> :</label>
                                                      <br/>
                                                      <select name="typeMoto" id="typeMoto">
                                                          <option value="0"<% if(annonce.getTypeMoto()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                          <%
                                                          i=0;
                                                          for(String typeMoto:Datas.arrayTypesMoto) {
                                                              i++;
                                                              %>
                                                              <option value="<%= i %>"<% if(annonce.getTypeMoto()==i) out.print(" selected=\"selected\""); %>><%= typeMoto %></option>
                                                              <% } %>
                                                      </select>
                                                      <br/>
                                                      <label for="marque">Marque de la moto <%= label %> : </label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      case 12: %>
                                                      <label for="typeScooter">Type du scooter <%= label %> :</label>
                                                      <br/>
                                                      <select name="typeScooter" id="typeScooter">
                                                          <option value="0"<% if(annonce.getTypeScooter()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                          <%
                                                          i=0;
                                                          for(String typeScooter:Datas.arrayTypesScooter) {
                                                              i++;
                                                              %>
                                                              <option value="<%= i %>"<% if(annonce.getTypeScooter()==i) out.print(" selected=\"selected\""); %>><%= typeScooter %></option>
                                                              <% } %>
                                                      </select>
                                                      <br/>
                                                      <label for="marque">Marque du sooter <%= label %> : </label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      case 13: %>
                                                      <label for="marque">Marque de la mobylette <%= label %> :</label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      }
                                                  %>
                                                  <input type="text" name="marque" id="marque" value="<%= annonce.getMarque()%>" size="20" maxlength="40" />
                                                  <br/>
                                                  <%
                                                  switch(annonce.getTypeVehicule()) {
                                                      case 1:
                                                      case 2:
                                                      case 3:
                                                      case 4:
                                                      case 5:
                                                      case 6:
                                                      case 7:
                                                      case 8:
                                                      case 9:
                                                      case 10: %>
                                                      <label for="modele">Modèle du véhicule <%= label %> :</label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      case 11: %>
                                                      <label for="modele">Modèle de moto <%= label %> :</label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      case 12: %>
                                                      <label for="modele">Modèle du scooter <%= label %> :</label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      case 13: %>
                                                      <label for="modele">Modèle de la mobylette <%= label %> :</label>
                                                      <br/>
                                                      <%
                                                      break;
                                                      }
                                                  %>
                                                  <input type="text" name="modele" id="modele" value="<%= annonce.getModele()%>" size="20" maxlength="40" />
                                                  <br/>
                                                  <%
                                                  switch(annonce.getTypeVehicule()) {
                                                      case 1:
                                                      case 2:
                                                      case 3:
                                                      case 4:
                                                      case 5:
                                                      case 6:
                                                      case 7:
                                                      case 8:
                                                      case 9:
                                                      case 10: %>
                                                      <label for="energie">Énergie du véhicule :</label>
                                                      <br/>
                                                      <select name="energie" id="energie">
                                                          <option value="0"<% if(annonce.getEnergie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                          <%
                                                          i=0;
                                                          for(String energie:Datas.arrayTypesEnergie) {
                                                              i++;
                                                              %>
                                                              <option value="<%= i %>"<% if(annonce.getEnergie()==i) out.print(" selected=\"selected\""); %>><%= energie %></option>
                                                              <%
                                                              }
                                                      %>
                                                      </select>
                                                      <br/>
                                                      <%
                                                      break;
                                                      }
                                                  %>
                                                  <label for="annee">Année de mise en circulation<%= label4 %> :</label>
                                                  <br/>
                                                  <input type="text" name="annee" id="annee" value="<%= annonce.getAnnee()%>" size="4" maxlength="4" />
                                                  <br/>
                                                  <label for="kilometrage">Kilométrage<%= label3 %> :</label>
                                                  <br/>
                                                  <input type="text" name="kilometrage" id="kilometrage" value="<%= annonce.getKilometrage()%>" size="6" maxlength="10" />
                                                  <br/>
                                                  <label for="prix">Prix <%= label2 %> :</label>
                                                  <br/>
                                                  <input type="text" name="prix" id="prix" value="<%= annonce.getPrix()%>" size="6" maxlength="10" />
                                                  <span>&euro;</span>
                                                  <br/>
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
                                                      <input type="text" name="captcha" value="" size="5" maxlength="5" />
                                                  </div>
                                                  <br/>
                                                  <br/>
                                                  <div>
                                                      <input type="radio" name="cgu" value="1" checked="checked" />
                                                      <label for="cgu">En validant ce formulaire, je déclare avoir pris connaissance des conditions générales d'utilisation de ce site et les accepter.</label>
                                                  </div>
                                                  <br/>
                                                  <input type="submit" value="Valider" name="kermit" />
                                   <script type="text/javascript">
                                       CKEDITOR.replace( 'description' );
                                   </script>
                                                  <%
                                                  }
                                               %>
                                       </p>
                                   </form>
                               </fieldset>
                           </div>
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
