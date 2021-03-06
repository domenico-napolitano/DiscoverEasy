<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.beans.utenteBean"%>
    
<!DOCTYPE html>
<html>
<head>
<head>
  <meta charset="utf-8">
  <title>Registrazione Avvenuta - DiscoverEasy </title>
  <link rel="stylesheet"
  href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
  <link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script
  src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
  <script
  src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="stylesheet/styleRegistrazioneAvvenuta.css">
</head>
</head>
<body>
<%
utenteBean utente = (utenteBean) request.getAttribute("datiAnagrafici");
%>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<!-- navbar fissata in alto-->
		<div class="container">

			<div class="navbar-header">
				<!-- Pulsante del menu per finestra piccola -->
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#navbar-collapse-main">
					<span class="sr-only"> Toggle navigation </span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!--Logo-->
				<a href="home.html" id="logo" class="navbar-brand"><img id="immagineLogo"
					class="img-rounded" src="Img/Logo4.png" alt="logo"></a>
			</div>	
			</div>

	</nav>



    <div class="container" id="home"> <!-- div che contiene tutto il form di registrazione-->
      <div class="row" id="registrazione"> <!-- dichiaro una riga di boostrap -->
        <div class="col-xs-8 col-xs-offset-2"> <!-- div di 8 colonne, centrato-->
          <h4> Congratulazioni <%= utente.getNome() %>, la tua registrazione � avvenuta con successo! Usa la tua email: <%= utente.getEmail() %> per effettuare il login </h4>
          <h4> clicca <a href="home.html">qui</a> per ritornare alla Home </h4>

      </div>
      </div> <!--chiudo riga di bootstrap-->
    </div> <!-- chiudo div che contiene form-->


    <footer class="footer container-fluid text-center" id="footer"> <!--inizio footer -->
        <div class="row">
          <div class="payments col-md-4">
            <a href="chisiamo.html"><Strong>Chi siamo </Strong></a>
            <a href="chisiamo.html"><span class="glyphicon glyphicon-zoom-in"></span></a>
          </div>

          <div class="social col-md-4">
            <a href="https://it-it.facebook.com"><Strong>Seguici</Strong></a>
              <a href="https://it-it.facebook.com"><span class="glyphicon glyphicon-random"></span></a>
          </div>
              <div class="payments col-md-4">
                <a href="contattaci.html"><Strong>Contattaci </Strong></a>
                <a href="contattaci.html"><span class="glyphicon glyphicon-bullhorn"></span></a>
              </div>
          </div>

    </footer> <!--fine footer -->


</body>
</html>