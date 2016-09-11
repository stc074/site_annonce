<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Mot de passe oublié</title>
<meta name="generator" content="NETBEANS 6.9"/>
<meta name="author" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="Megannonce est votre site de petites annonces gratuites - petites annonces - immobilier - auto -emploi." />
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta charset="UTF-8" />
<link rel="icon" type="image/png" href="./GFXs/favicon.png" />
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
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
           <h1>Megannonce - Mot de passe oublié.</h1>
           </header>
           <%
           try {
               if(request.getAttribute("membre")!=null) {
                   Membre membre=(Membre)request.getAttribute("membre");
                   if(membre.getTest()==0) {
               %>
               <h2>Vous avez oublié votre mot de passe...</h2>
               <p>...Ce n'est pas grave, nous allons vous en renvoyer un autre.</p>
               <div class="cadre2">
               <div class="info">Utilisez le formulaire ci-dessous :</div>
               </div>
               <br/>
               <div id="form">
                   <%
                   if(request.getParameter("kermit")!=null&&membre.getErrorMsg().length()>0) { %>
                   <div class="erreur">
                       <div>Erreur(s) :</div>
                       <br/>
                       <%= membre.getErrorMsg() %>
                   </div>
                   <br/>
                   <% } %>
                   <fieldset>
                       <legend>Adresse EMAIL de mon compte</legend>
                       <form action="./mdp-oublie.html#form" method="POST">
                           <p>
                           <label for="email">Saisissez l'ADRESSE EMAIL associée à votre compte :</label>
                           <br/>
                           <input type="text" name="email" value="<%= membre.getEmail()%>" size="40" maxlength="200" />
                           <br/>
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
                           } else if(membre.getTest()==1) { %>
                           <br/>
                           <div class="cadre2">
                               <div class="info">Un nouveau mot de passe vous a été envoyé !</div>
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
