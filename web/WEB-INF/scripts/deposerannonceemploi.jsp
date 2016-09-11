<%@page import="classes.CategorieEmploi"%>
<%@page import="classes.Categorie"%>
<%@page import="classes.AnnonceEmploi"%>
<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Déposer une annonce emploi - Contenu</title>
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
           <h1>Megannonce - Déposer une annonce emploi - Contenu.</h1>
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
                   } else if(request.getAttribute("annonce")!=null&&request.getAttribute("categorie")!=null) {
                       AnnonceEmploi annonce=(AnnonceEmploi)request.getAttribute("annonce");
                       CategorieEmploi categorie=(CategorieEmploi)request.getAttribute("categorie");
                       if(annonce.getTest()==0) {
                           %>
                           <div class="info">Utilisez le formulaire ci-dessous pour enregistrer le contenu de votre <strong>annonce</strong>.</div>
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
                                   <legend>Contenu de votre annonce emploi</legend>
                                   <form action="./deposer-annonce-emploi.html#form" method="POST">
                                       <p>
                                       <span>Type d'annonce :</span>
                                       <input type="radio" name="mode" id="mode1" value="1"<% if(annonce.getMode()==1) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-emploi-1-1.html#form';" />
                                       <label for="mode1">Offre d'emploi&nbsp;</label>
                                       <input type="radio" name="mode" id="mode2" value="2"<% if(annonce.getMode()==2) out.print(" checked=\"checked\""); %> onclick="javascript:window.location.href='./deposer-annonce-emploi-1-2.html#form';" />
                                       <label for="mode2">Demande d'emploi</label>
                                       <br/>
                                       <%
                                       if(annonce.getMode()!=0) {
                                           String label1="", label2="", label3="";
                                           switch(annonce.getMode()) {
                                            case 1:
                                              label1=" de l'offre d'emploi";
                                               label2=" de l'offre";
                                               label3=" proposé";
                                            break;
                                            case 2:
                                               label1=" de l'emploi demandé";
                                              label2=" recherché";
                                              label3=" souhaité";
                                            break;
                                           }
                                           %>
                                           <label for="idCategorie">Catégorie <%= label1 %> :</label>
                                           <select name="idCategorie" id="idCategorie">
                                               <option value="0"<% if(annonce.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                               <%
                                               for(int i=0;i<categorie.getListeCategorie().length;i++) {
                                                   %>
                                                   <option value="<%= (i+1) %>"<% if(annonce.getIdCategorie()==(i+1)) out.print(" selected=\"selected\""); %>><%= categorie.getListeCategorie()[i] %></option>
                                                   <%
                                                                                                     }
                                           %>
                                           </select>
                                           <br/>
                                           <label for="typeContrat">Type de contrat<%= label2 %> :</label>
                                           <select name="typeContrat" id="typeContrat">
                                               <option value="0"<% if(annonce.getTypeContrat()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                               <%
                                               int i=0;
                                               for(String typeContrat:Datas.arrayTypesContrat) {
                                                   i++;
                                                   %>
                                                   <option value="<%= i %>"<% if(annonce.getTypeContrat()==i) out.print(" selected=\"selected\""); %>><%= typeContrat %></option>
                                                   <%
                                               }
                                               %>
                                           </select>
                                           <br/>
                                           <label for="tarifHoraire">Tarif horaire<%= label3 %> :</label>
                                           <input type="text" name="tarifHoraire" id="tarifHoraire" value="<%= annonce.getTarifHoraire()%>" size="6" maxlength="10" />
                                           <span> &euro; NET</span>
                                           <br/>
                                           <label for="titre">Titre de votre annonce :</label>
                                           <br/>
                                           <input type="text" name="titre" id="titre" value="<%= annonce.getTitre()%>" size="30" maxlength="80" />
                                           <br/>
                                           <label for="description">Description plus longue de votre annonce :</label>
                                           <br/>
                                           <textarea name="description" id="description" rows="4" cols="20"><%= annonce.getDescription()%></textarea>
                                           <br/>
                                           <div class="captcha"></div>
                                           <div class="captchaDroite">
                                               <label for="captcha">&rarr;Copiez le code SVP&rarr;</label>
                                               <input type="text" name="captcha" id="captcha" value="" size="5" maxlength="5" />
                                           </div>
                                           <br/>
                                           <input type="radio" name="cgu" id="cgu" value="1" checked="checked" />
                                           <label for="cgu">En validant ce formulaire, je certifie avoir pris connaissance des Conditions Générales D'utilisation de ce site et les accepter.</label>
                                           <br/>
                                           <br/>
                                           <input type="submit" value="Valider" name="kermit" />
                                           <br/>
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
