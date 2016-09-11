<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Mon compte</title>
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
           <h1>Megannonce - Mon compte.</h1>
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
                           <div class="info">Vous devez être connecté pour pouvoir accéder à votre compte.</div>
                           <br/>
                           <div>
                               <a href="./inscription.html" title="S'INSCRIRE" rel="nofollow">S'INSCRIRE</a>
                           </div>
                           <br/>
                       </div>
                       <br/>
                       <%
                       break;
                       case 2: %>
                       <br/>
                       <div class="cadre2">
                           <div>
                               <a href="./infos-personnelles.html" title="INFOS PERSONNELLES" rel="nofollow">INFOS PERSONNELLES</a>
                           </div>
                       </div>
                           <br/>
                           <div class="cadre2">
                           <div>
                               <a href="./mes-annonces.html" title="MES ANNONCES" rel="nofollow">MES ANNONCES</a>
                           </div>
                           </div>
                           <br/>
                           <div class="cadre2">
                               <div>
                                   <a href="./messagerie.html" title="MA MESSAGERIE" rel="nofollow">MA MESSAGERIE</a>
                               </div>
                           </div>
                           <br/>
                           <div class="cadre2">
                               <div>
                                   <a href="./supprimer-compte.html" title="SUPPRIMER DÉFINITIVEMENT MON COMPTE" rel="nofollow">SUPPRIMER MON COMPTE</a>
                                   <span>&nbsp;(DÉFINITIF)</span>
                               </div>
                           </div>
                           <br/>
                           <%
                           break;
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
