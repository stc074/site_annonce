<%@page import="java.util.Calendar"%>
<%@page import="classes.AnnonceAuto"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Éditer mon annonce auto</title>
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
           <h1>Megannonce - Éditer mon annonce auto - Contenu.</h1>
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
                           <div class="info"><strong>Annonce auto</strong> inconnue !</div>
                       </div>
                       <br/>
                       <%
                       break;
                       }
                   } else if(request.getAttribute("annonce")!=null) {
                       AnnonceAuto annonce=(AnnonceAuto)request.getAttribute("annonce");
                       if(annonce.getTest()==0) {
                       Calendar cal=Calendar.getInstance();
                       cal.setTimeInMillis(annonce.getTimestamp());
                       %>
                       <br/>
                       <div class="info">Pour éditer votre <strong>annonce auto</strong>, utilisez le formulaire ci-dessous :</div>
                       <br/>
                       <div class="info">Dernière modification, le <%= cal.get(cal.DATE) %>-<%= cal.get(cal.MONTH)+1 %>-<%= cal.get(cal.YEAR) %>.</div>
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
                               <form action="./edit-annonce.html#form" method="POST">
                                   <p>
                                       <input type="hidden" name="type" value="2" />
                                       <input type="hidden" name="idAnnonce" value="<%= annonce.getId()%>" />
                                   <input type="radio" name="mode" id="mode1" value="1" disabled="disabled"<% if(annonce.getMode()==1) out.print(" checked=\"checked\""); %> />
                                   <label for="mode1">&rarr;Vente</label>
                                   <input type="radio" name="mode" id="mode2" value="2" disabled="disabled"<% if(annonce.getMode()==2) out.print(" checked=\"checked\""); %> />
                                   <label for="mode2">&rarr;Achat</label>
                                   <div>
                                       <%
                                       int i=0;
                                       for(String typeVehicule:Datas.arrayTypeVehicules) {
                                           i++;
                                           %>
                                           <input type="radio" name="typeVehicule" id="typeVehicule<%= i %>" value="<%= i%>" disabled="disabled"<% if(annonce.getTypeVehicule()==i) out.print(" checked=\"checked\""); %> />
                                           <label for="typeVehicule<%= i %>">&rarr;<%= typeVehicule %></label>
                                           <%
                                           }
                                       %>
                                   </div>
                                       <%
                                       if(annonce.getMode()!=0&&annonce.getTypeVehicule()!=0) {
                                           String label1="";
                                           String label3="";
                                           switch(annonce.getMode()) {
                                               case 1:
                                                   label1=" que vous souhaitez vendre";
                                                   label3=" à la vente";
                                                   break;
                                                   case 2:
                                                       label1=" que vous souhaitez acheter";
                                                       label3=" à l'achat";
                                                       break;
                                                       }
                                           String label2="";
                                           switch(annonce.getTypeVehicule()) {
                                               case 11:
                                                   label2=" de la moto";
                                       %>
                                               <label for="typeMoto">Type de moto <%= label1 %>:</label>
                                               <select name="typeMoto" id="typeMoto">
                                                   <option value="0"<% if(annonce.getTypeMoto()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                   <%
                                                   i=0;
                                                   for(String typeMoto:Datas.arrayTypesMoto) {
                                                       i++;
                                                       %>
                                                       <option value="<%= i %>"<% if(annonce.getTypeMoto()==i) out.print(" selected=\"selected\""); %>><%= typeMoto %></option>
                                                       <%
                                                       }
                                                   %>
                                               </select>
                                               <br/>
                                               <%
                                               break;
                                               case 12:
                                               label2=" du scooter";
                                                %>
                                               <label for="typeScooter">Type de scooter <%= label1 %>:</label>
                                               <select name="typeScooter" id="typeScooter">
                                                   <option value="0"<% if(annonce.getTypeScooter()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                   <%
                                                   i=0;
                                                   for(String typeScooter:Datas.arrayTypesScooter) {
                                                       i++;
                                                       %>
                                                       <option value="<%= i %>"<% if(annonce.getTypeScooter()==i) out.print(" selected=\"selected\""); %>><%= typeScooter %></option>
                                                       <%
                                                       }
                                                   %>
                                               </select>
                                               <br/>
                                               <%
                                               break;
                                               default:
                                                   label2=" du véhicule";
                                                   break;
                                               }
                                           %>
                                           <label for="marque">Marque<%= label2 %><%= label1 %> :</label>
                                           <br/>
                                           <input type="text" name="marque" id="marque" value="<%= annonce.getMarque()%>" size="20" maxlength="40" />
                                           <br/>
                                           <label for="modele">Modèle<%= label2 %><%= label1 %></label>
                                           <br/>
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
                                               <label for="energie">Énergie<%= label2 %><%= label1 %> :</label>
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
                                           <label for="annee">Année<%= label2 %><%= label1 %> :</label>
                                           <br/>
                                           <input type="text" name="annee" id="annee" value="<%= annonce.getAnnee()%>" size="4" maxlength="4" />
                                           <br/>
                                           <label for="kilometrage">Kilométrage<%= label2 %><%= label1 %> :</label>
                                           <br/>
                                           <input type="text" name="kilometrage" id="kilometrage" value="<%= annonce.getKilometrage()%>" size="6" maxlength="10" />
                                           <span>KM</span>
                                           <br/>
                                           <label for="prix">Prix<%= label3 %> :</label>
                                           <br/>
                                           <input type="text" name="prix" id="prix" value="<%= annonce.getPrix()%>" size="6" maxlength="10" />
                                           <span>&euro;</span>
                                           <br/>
                                           <label for="titre">Titre de votre annonce :</label>
                                           <br/>
                                           <input type="text" name="titre" id="titre" value="<%= annonce.getTitre()%>" size="40" maxlength="80" />
                                           <br/>
                                           <label for="description">Description de votre annonce :</label>
                                           <textarea name="description" id="description" rows="4" cols="20"><%= annonce.getDescription()%></textarea>
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
                                           <%
                                                   }
                                           %>
                                   </p>
                               </form>
                           </fieldset>
                                   <script type="text/javascript">
                                       CKEDITOR.replace( 'description' );
                                   </script>
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
