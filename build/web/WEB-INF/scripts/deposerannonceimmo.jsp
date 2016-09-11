<%@page import="classes.AnnonceImmo"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Déposer une annonce immobilière - Contenu</title>
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
           <h1>Megannonce - Déposer une annonce immobilière - Contenu.</h1>
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
                       AnnonceImmo annonce=(AnnonceImmo)request.getAttribute("annonce");
                       if(annonce.getTest()==0) { %>
                       <br/>
                       <div class="info">Pour enregistrer le contenu de votre <strong>annonce immobilière</strong>, utilisez le formulaire ci-dessous :</div>
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
                               <legend>Contenu de mon annonce immobilière</legend>
                               <form action="./deposer-annonce-immo.html#form" method="POST">
                                   <p>
                                       <span>Type de transaction :</span>
                                       <input type="radio" name="mode" value="1" id="mode1"<% if(annonce.getMode()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-immo-1-1-<%= annonce.getTypeBien() %>.html#form';" />
                                       <label for="mode1">&rarr;Vente&nbsp;</label>
                                       <input type="radio" name="mode" value="2" id="mode2"<% if(annonce.getMode()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-immo-1-2-<%= annonce.getTypeBien() %>.html#form';" />
                                       <label for="mode2">&rarr;Location à l'année&nbsp;</label>
                                       <input type="radio" name="mode" value="3" id="mode3"<% if(annonce.getMode()==3) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-immo-1-3-<%= annonce.getTypeBien() %>.html#form';" />
                                       <label for="mode3">&rarr;Location saisonnière&nbsp;</label>
                                       <input type="radio" name="mode" value="4" id="mode4"<% if(annonce.getMode()==4) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-immo-1-4-<%= annonce.getTypeBien() %>.html#form';" />
                                       <label for="mode4">&rarr;Achat&nbsp;</label>
                                       <br/>
                                       <label for="typeBien">Type de bien &rarr; </label>
                                       <select name="typeBien" id="typeBien" onchange="javascript:window.location.href='./deposer-annonce-immo-1-<%= annonce.getMode() %>-'+this.value+'.html#form';">
                                           <option value="0"<% if(annonce.getTypeBien()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                           <%
                                           int i=0;
                                           for(String typeBien:Datas.arrayTypesBien) {
                                               i++;
                                               %>
                                               <option value="<%= i %>"<% if(annonce.getTypeBien()==i) out.print(" selected=\"selected\""); %>><%= typeBien %></option>
                                               <%
                                               }
                                           %>
                                       </select>
                                       <br/>
                                       <%
                                       if(annonce.getMode()!=0&&annonce.getTypeBien()!=0) {
                                           String label1="";
                                           String label2="";
                                           String label3="";
                                           switch(annonce.getMode()) {
                                               case 1:
                                                   label1=" à la vente";
                                                   label2=" à vendre";
                                                   break;
                                               case 2:
                                               case 3:
                                                   label1=" à la location";
                                                   label2=" à louer";
                                                   break;
                                               case 4:
                                                   label1=" souhaité à l'achat";
                                                   label2=" à acheter";
                                                   label3=" souhaité";
                                                   break;
                                                   }
                                           switch(annonce.getTypeBien()) {
                                               case 1: %>
                                               <label for="typeAppartement">Type d'appartement<%= label1 %> :</label>
                                               <select name="typeAppartement" id="typeAppartement">
                                                   <option value="0"<% if(annonce.getTypeAppartement()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                                   <%
                                                   i=0;
                                                   for(String typeAppartement:Datas.arrayTypesAppart) {
                                                       i++;
                                                       %>
                                                       <option value="<%= i %>"<% if(annonce.getTypeAppartement()==i) out.print(" selected=\"selected\""); %>><%= typeAppartement %></option>
                                                       <%
                                                       }
                                                   %>
                                               </select>
                                               <br/>
                                               <%
                                               break;
                                               }
                                           %>
                                           <label for="surface">Surface du bien<%= label3 %> :</label>
                                           <br/>
                                           <input type="text" name="surface" id="surface" value="<%= annonce.getSurface()%>" size="6" maxlength="10" />
                                           <span>m<sup>2</sup></span>
                                           <br/>
                                           <%
                                           switch(annonce.getTypeBien()) {
                                               case 1:
                                               case 2:
                                               case 3: %>
                                               <span>Bien<%= label2 %> meublé ?</span>
                                               <input type="radio" name="meuble" id="meuble1" value="1"<% if(annonce.getMeuble()==1) out.print(" checked=\"checked\""); %> />
                                               <label for="meuble1">&rarr;OUI&nbsp;</label>
                                               <input type="radio" name="meuble" id="meuble2" value="2"<% if(annonce.getMeuble()==2) out.print(" checked=\"checked\""); %> />
                                               <label for="meuble2">&rarr;NON&nbsp;</label>
                                               <br/>
                                               <%
                                               break;
                                               }
                                           switch(annonce.getTypeBien()) {
                                               case 1:
                                               case 2:
                                               case 3:
                                               case 4:
                                               case 5: %>
                                               <label for="nbPieces">Nombre de pièces<%= label3 %> :</label>
                                               <br/>
                                               <input type="text" name="nbPieces" id="nbPieces" value="<%= annonce.getNbPieces()%>" size="6" maxlength="10" />
                                               <span>Pièces.</span>
                                               <br/>
                                               <%
                                               break;
                                               }
                                           switch(annonce.getTypeBien()) {
                                               case 4:
                                               case 5: %>
                                               <label for="travaux">Travaux à réaliser sur le bien<%= label2 %> :</label>
                                               <br/>
                                               <textarea name="travaux" id="travaux" rows="4" cols="20"><%= annonce.getTravaux()%></textarea>
                                               <br/>
                                               <%
                                               break;
                                               case 6: %>
                                               <label for="nbEtages">Nombre d'étages de l'immeuble<%= label3 %> :</label>
                                               <br/>
                                               <input type="text" name="nbEtages" id="nbEtages" value="<%= annonce.getNbEtages()%>" size="6" maxlength="10" />
                                               <span>Étages.</span>
                                               <br/>
                                               <%
                                               break;
                                               case 7: %>
                                               <span>Terrain<%= label2 %> viabilisé ?</span>
                                               <input type="radio" name="viabilise" id="viabilise1" value="1"<% if(annonce.getViabilise()==1) out.print(" checked=\"checked\""); %> />
                                               <label for="viabilise1">&rarr;OUI&nbsp;</label>
                                               <input type="radio" name="viabilise" id="viabilise2" value="2"<% if(annonce.getViabilise()==2) out.print(" checked=\"checked\""); %> />
                                               <label for="viabilise2">&rarr;NON&nbsp;</label>
                                               <br/>
                                               <span>Terrain<%= label2 %> constructible ?</span>
                                               <input type="radio" name="constructible" id="constructible1" value="1"<% if(annonce.getConstructible()==1) out.print(" checked=\"checked\""); %> />
                                               <label for="constructible1">&rarr;OUI&nbsp;</label>
                                               <input type="radio" name="constructible" id="constructible2" value="2"<% if(annonce.getConstructible()==2) out.print(" checked=\"checked\""); %> />
                                               <label for="constructible2">&rarr;NON&nbsp;</label>
                                               <br/>
                                               <%
                                               break;
                                               }
                                           switch(annonce.getMode()) {
                                               case 1:
                                               case 4: %>
                                               <label for="prix">Prix<%= label1 %> :</label>
                                               <br/>
                                               <input type="text" name="prix" id="prix" value="<%= annonce.getPrix()%>" size="6" maxlength="10" />
                                               <span>&euro;</span>
                                               <br/>
                                               <%
                                               break;
                                               case 2: %>
                                               <label for="loyer">Loyer mensuel :</label>
                                               <br/>
                                               <input type="text" name="loyer" id="loyer" value="<%= annonce.getLoyer()%>" size="6" maxlength="10" />
                                               <br/>
                                               <label for="charges">Charges mensuelles :</label>
                                               <br/>
                                               <input type="text" name="charges" id="charges" value="<%= annonce.getCharges()%>" size="6" maxlength="10" />
                                               <br/>
                                               <%
                                               break;
                                               case 3: %>
                                               <label for="loyer">Loyer à la semaine :</label>
                                               <br/>
                                               <input type="text" name="loyer" id="loyer" value="<%= annonce.getLoyer()%>" size="6" maxlength="10" />
                                               <br/>
                                               <%
                                               break;
                                               }
                                           %>
                                           <br/>
                                           <label for="titre">Titre de votre annonce :</label>
                                           <br/>
                                           <input type="text" name="titre" value="<%= annonce.getTitre()%>" size="40" maxlength="80" />
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
                                                      <input type="radio" name="cgu" value="1" checked="checked" />
                                                      <label for="cgu">En validant ce formulaire, je déclare avoir pris connaissance des conditions générales d'utilisation de ce site et les accepter.</label>
                                                  </div>
                                                  <br/>
                                          <input type="submit" value="Valider" name="kermit" />
                                   <script type="text/javascript">
                                       CKEDITOR.replace( 'description' );
                                       CKEDITOR.replace( 'travaux' );
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
