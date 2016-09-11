<%@page import="classes.Membre"%>
<%@page import="classes.Datas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Petites annonces gratuites - Megannonce - Éditer mon annonce immobilière</title>
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
           <h1>Megannonce - Éditer mon annonce immobiliére - Contenu.</h1>
           </header>
           <c:catch var="ex">
               <c:if test="${info!=null}">
                   <c:choose>
                       <c:when test="${info==1}">
                           <br/>
                           <div class="cadre2">
                               <br/>
                               <div class="info">Vous devez être connecté pour pouvoir éditer vos <strong>annonces</strong>.</div>
                               <br/>
                               <div>
                                   <a href="./inscription.html" rel="nofollow" title="S'INSCRIRE">S'INSCRIRE</a>
                               </div>
                               <br/>
                           </div>
                           <br/>
                       </c:when>
                       <c:when test="$[info==2}">
                           <br/>
                           <div class="cadre2">
                               <div class="info">Annonce inconnue !</div>
                           </div>
                           <br/>
                       </c:when>
                   </c:choose>
               </c:if>
                       <c:if test="${annonce!=null}">
                           <div class="cadre2">
                            <div class="info">Utilisez le formulaire ci-dessous pour modifier votre annonce :</div>
                           </div>
                           <br/>
                           <div id="form">
                               <c:if test="${not empty annonce.errorMsg}">
                                   <div class="erreur">
                                       <div>Erreur(s) :</div>
                                       <br/>
                                       ${annonce.errorMsg}
                                   </div>
                                   <br/>
                               </c:if>
                               <fieldset>
                                   <legend>Contenu de mon annonce</legend>
                                   <form action="./edit-annonce.html#form" method="POST">
                                       <p>
                                           <input type="hidden" name="idAnnonce" value="${annonce.id}" />
                                           <input type="hidden" name="type" value="3" />
                                           <div>
                                               <c:choose>
                                                   <c:when test="${annonce.mode==1}">
                                                       <span>Vente </span>
                                                   </c:when>
                                                   <c:when test="${annonce.mode==2}">
                                                       <span>Location </span>
                                                   </c:when>
                                                   <c:when test="${annonce.mode==3}">
                                                       <span>Location saisonnière :</span>
                                                   </c:when>
                                                   <c:when test="${annonce.mode==4}">
                                                       <span>Achat </span>
                                                   </c:when>
                                               </c:choose>
                                                       <span>${annonce.typeBienString}</span>
                                                       <c:choose>
                                                           <c:when test="${annonce.typeBien==1}">
                                                           <span>${annonce.typeAppartementString}.</span>
                                                           </c:when>
                                                       </c:choose>
                                                           <br/>
                                                           <c:choose>
                                                               <c:when test="${annonce.mode==1}">
                                                                   <label for="surface">Surface du bien à vendre :</label>
                                                               </c:when>
                                                               <c:when test="${annonce.mode==2||annonce.mode==3}">
                                                                   <label for="surface">Surface du bien à louer :</label>
                                                               </c:when>
                                                               <c:when test="${annonce.mode==4}">
                                                                   <label for="surface">Surface souhaitée du bien à acheter :</label>
                                                               </c:when>
                                                           </c:choose>
                                                                   <br/>
                                                                   <input type="text" name="surface" id="surface" value="${annonce.surface}" size="6" maxlength="10" />
                                                                   <span>m<sup>2</sup></span>
                                                                   <br/>
                                                                   <c:if test="${annonce.typeBien==1||annonce.typeBien==2||annonce.typeBien==3}">
                                                                       <c:choose>
                                                                           <c:when test="${annonce.mode==1}">
                                                                               <span>Le bien à vendre est-il meublé ?</span>
                                                                           </c:when>
                                                                           <c:when test="${annonce.mode==2||annonce.mode==3}">
                                                                               <span>Le bien à louer est-il meublé ?</span>
                                                                           </c:when>
                                                                           <c:when test="${annonce.mode==4}">
                                                                               <span>Souhaité vous un bien meublé ?</span>
                                                                           </c:when>
                                                                       </c:choose>
                                                                               <input type="radio" name="meuble" id="meuble1" value="1"${annonce.meuble1Check} />
                                                                               <label for="meuble1">&rarr;OUI&nbsp;</label>
                                                                               <input type="radio" name="meuble" id="meuble2" value="2"${annonce.meuble2Check} />
                                                                               <label for="meuble2">&rarr;NON&nbsp;</label>
                                                                               <br/>
                                                                   </c:if>
                                                                   <c:if test="${annonce.typeBien>=1&&annonce.typeBien<=5}">
                                                                       <c:choose>
                                                                           <c:when test="${annonce.mode==1}">
                                                                               <label for="nbPieces">Nombre de pièces du bien à vendre :</label>
                                                                           </c:when>
                                                                           <c:when test="${annonce.mode==2||annonce.mode==3}">
                                                                               <label for="nbPieces">Nombre de pièces du bien à louer :</label>
                                                                           </c:when>
                                                                           <c:when test="${annonce.mode==4}">
                                                                               <label for="nbPieces">Nombre de pièces souhaité :</label>
                                                                           </c:when>
                                                                       </c:choose>
                                                                               <br/>
                                                                               <input type="text" name="nbPieces" id="nbPieces" value="${annonce.nbPieces}" size="6" maxlength="10" />
                                                                               <span>pièce(s)</span>
                                                                               <br/>
                                                                   </c:if>
                                                                   <c:if test="${annonce.typeBien==4||annonce.typeBien==5}">
                                                                       <c:choose>
                                                                           <c:when test="${annonce.mode==1||annonce.mode==2||annonce.mode==3}">
                                                                               <label for="travaux">Travaux à réaliser :</label>
                                                                           </c:when>
                                                                           <c:when test="${annonce.mode==4}">
                                                                               <label for="travaux">Travaux souhaités :</label>
                                                                           </c:when>
                                                                       </c:choose>
                                                                               <br/>
                                                                               <textarea name="travaux" id="travaux" rows="4" cols="20">${annonce.travaux}</textarea>
                                                                               <br/>
                                                                                <script type="text/javascript">
                                                                                    CKEDITOR.replace( 'travaux' );
                                                                                </script>
                                                                   </c:if>
                                                                                <c:if test="${annonce.typeBien==6}">
                                                                                    <c:choose>
                                                                                        <c:when test="${annonce.mode==1||annonce.mode==2||annonce.mode==3}">
                                                                                            <label for="nbEtages">Nombre d'étages :</label>
                                                                                        </c:when>
                                                                                        <c:when test="${annonce.mode==4}">
                                                                                            <label for="nbEtages">Nombre d'étages douhaité :</label>
                                                                                        </c:when>
                                                                                    </c:choose>
                                                                                            <br/>
                                                                                            <input type="text" name="nbEtages" value="${annonce.nbEtages}" size="6" maxlength="6" />
                                                                                            <span>étage(s)</span>
                                                                                            <br/>
                                                                                </c:if>
                                                                                <c:if test="${annonce.typeBien==7}">
                                                                                    <span>Le terrain${annonce.label1} est-il viabilisé ?</span>
                                                                                    <input type="radio" name="viabilise" id="viabilise1" value="1"${annonce.viabilise1Check} />
                                                                                    <label for="viabilise1">&rarr;OUI&nbsp;</label>
                                                                                    <input type="radio" name="viabilise" id="viabilise2" value="2"${annonce.viabilise2Check} />
                                                                                    <label for="viabilise2">&rarr;NON&nbsp;</label>
                                                                                    <br/>
                                                                                    <span>Le terrain${annonce.label1} est-il constructible ?</span>
                                                                                    <input type="radio" name="constructible" id="constructible1" value="1"${annonce.constructible1Check} />
                                                                                    <label for="constructible1">&rarr;OUI&nbsp;</label>
                                                                                    <input type="radio" name="constructible" id="constructible2" value="2"${annonce.constructible1Check} />
                                                                                    <label for="constructible2">&rarr;NON&nbsp;</label>
                                                                                    <br/>
                                                                                </c:if>
                                                                                <c:choose>
                                                                                    <c:when test="${annonce.mode==1}">
                                                                                        <label for="prix">Prix à la vente :</label>
                                                                                        <br/>
                                                                                        <input type="text" name="prix" id="prix" value="${annonce.prix}" size="6" maxlength="10" />
                                                                                        <span>&euro;</span>
                                                                                        <br/>
                                                                                    </c:when>
                                                                                    <c:when test="${annonce.mode==2}">
                                                                                        <label for="loyer">Loyer mensuel :</label>
                                                                                        <br/>
                                                                                        <input type="text" name="loyer" id="loyer" value="${annonce.loyer}" size="6" maxlength="10" />
                                                                                        <span>&euro;</span>
                                                                                        <br/>
                                                                                        <label for="charges">Charges mensuelle :</label>
                                                                                        <br/>
                                                                                        <input type="text" name="charges" id="charges" value="${annonce.charges}" size="6" maxlength="10" />
                                                                                        <span>&euro;</span>
                                                                                        <br/>
                                                                                    </c:when>
                                                                                    <c:when test="${annonce.mode==3}">
                                                                                        <label for="loyer">Loyer à la semaine :</label>
                                                                                        <br/>
                                                                                        <input type="text" name="loyer" id="loyer" value="${annonce.loyer}" size="6" maxlength="10" />
                                                                                        <span>&euro;</span>
                                                                                        <br/>
                                                                                    </c:when>
                                                                                    <c:when test="${annonce.mode==4}">
                                                                                        <label for="prix">Prix souhaité à l'achat :</label>
                                                                                        <br/>
                                                                                        <input type="text" name="prix" id="prix" value="${annonce.prix}" size="6" maxlength="10" />
                                                                                        <span>&euro;</span>
                                                                                        <br/>
                                                                                    </c:when>
                                                                                </c:choose>
                                           </div>
                                           <label for="titre">Titre de votre annonce :</label>
                                           <br/>
                                           <input type="text" name="titre" id="titre" value="${annonce.titre}" size="30" maxlength="80" />
                                           <br/>
                                           <label for="description">Description de votre annonce :</label>
                                           <br/>
                                           <textarea name="description" id="description" rows="4" cols="20">${annonce.description}</textarea>
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
                                           <br/>
                                       </p>
                                   </form>
                               </fieldset>
                               <script type="text/javascript">
                                   CKEDITOR.replace( 'description' );
                               </script>
                           </div>
                       </c:if>
           </c:catch>
           <c:if test="${not empty ex}">
               <div class="erreur">
                   <div>Erreur :</div>
                   <br/>
                   <div>${ex.message}</div>
               </div>
           </c:if>
       </section>
       <%@include file="./footer.jsp" %>
    </body>
</html>
