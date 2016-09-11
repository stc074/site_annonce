<header>
<%
if(request.getAttribute("mbr")!=null) {
    Membre mbr=(Membre)request.getAttribute("mbr");
    %>
<div class="connexion">
    <div id="formCnx">
        <%
        if(request.getParameter("kermitCnx")!=null&&mbr.getErrorMsg().length()>0) { %>
        <div class="erreur">
            <div>Erreur(s) :</div>
            <br/>
            <div><%= mbr.getErrorMsg() %></div>
        </div>
        <% } %>
        <form action="#formCnx" method="POST">
            <p>
            <div>Connexion</div>
            <label for="pseudoCnx">PSEUDONYME :</label>
            <br/>
            <input type="text" name="pseudo" id="pseudoCnx" value="<%= mbr.getPseudo()%>" size="15" maxlength="20" />
            <br/>
            <label for="motDePasseCnx">MOT DE PASSE :</label>
            <br/>
            <input type="password" name="motDePasse" id="motDePasseCnx" value="" size="15" maxlength="15" />
            <br/>
            <input type="submit" value="Connexion" name="kermitCnx" />
        </p>
        </form>
    </div>
            <%
            if(mbr.getId()==0) { %>
            <div>Statut &rarr; Déconnecté(e)</div>
            <% } else if(mbr.getId()!=0&&mbr.getPseudo2().length()>0) { %>
            <div>Statut &rarr; Connecté : <%= mbr.getPseudo2() %></div>
            <% } %>
            <div>
                <a href="./mdp-oublie.html" title="MOT DE PASSE OUBLIÉ">Mot de passe oublié</a>
            </div>
</div>
        <% } %>
            <div class="logo" onclick="javascipt:window.location.href='./';"></div>
</header>
<nav>
    <ul class="menu">
        <li>
            <a href="./" title="ACCUEIL">ACCUEIL</a>
        </li>
        <li>
            <a href="./petites-annonces.html" title="PETITES ANNONCES">PETITES ANNONCES</a>
        </li>
        <li>
            <a href="./petites-annonces-auto.html" title="ANNONCES AUTO">AUTOMOBILE</a>
        </li>
        <li>
            <a href="./petites-annonces-immobilieres.html" title="ANNONCES IMMOBILIÈRES">IMMOBILIER</a>
        </li>
        <li>
            <a href="./petites-annonces-emploi.html" title="ANNONCES D'EMPLOI">EMPLOI</a>
        </li>
        <li>
            <a href="./inscription.html" title="INSCRIPTION" rel="nofollow">INSCRIPTION</a>
        </li>
        <li>
            <a href="./deposer-annonce-1.html" title="DÉPOSER UNE ANNONCE" rel="nofollow">DÉPOSER UNE ANNONCE</a>
        </li>
        <li>
            <a href="./mon-compte.html" title="MON COMPTE" rel="nofollow">MON COMPTE</a>
        </li>
        <li>
            <a href="./messagerie.html" title="MESSAGERIE" rel="nofollow">MESSAGERIE</a>
        </li>
    </ul>
</nav>