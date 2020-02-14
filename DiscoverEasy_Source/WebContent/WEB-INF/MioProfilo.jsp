<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="model.beans.utenteBean"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Mio profilo - DiscoverEasy</title>
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
<link rel="stylesheet" href="stylesheet\styleMioProfilo.css">
</head>
<body>

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
				<a href="home.html" id="logo" class="navbar-brand"><img
					id="immagineLogo" class="img-rounded" src="Img/Logo4.png"
					alt="logo"></a>
			</div>



			<div class="collapse navbar-collapse" id="navbar-collapse-main">
				<!--menu navbar destro -->

				<ul class="nav navbar-nav navbar-right">

					<%if((Integer) request.getAttribute("isAmministratore") == 1){ %>
					<li>
						<form action="RicercaGestore">
							<button id="bottoneGestioneBarraNavigazione" type="submit"
								class="btn btn-primary btn-md" data-toggle="modal"
								data-target="#Accedi">
								<span class="glyphicon glyphicon-tasks"></span> <span>Gestione</span>
							</button>
						</form>
					</li>
					<%} %>

					<li>
						<form action="CaricaChat">
							<button class="btn btn-default btn-md" id="bottoneChat"
								type="submit">
								<span>Chat</span> <span class="glyphicon glyphicon-comment"></span>
							</button>
						</form>
					</li>
					<li>
						<div class="dropdown">
							<button class="btn btn-default dropdown-toggle" type="button"
								id="dropDownAccedi" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="true">
								<span class="glyphicon glyphicon-user"></span><span>
									Utente</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1"
								id="menuUtente">

								<li>
									<form action="CaricaAnnunciNegozio">
										<button class="btn btn-default" id="bottoneMioNegozio"
											type="submit">
											<span>Mio negozio</span> <span
												class="glyphicon glyphicon-shopping-cart"></span>
										</button>
									</form>
								</li>
								<li>
									<form action="CaricaMioProfilo">
										<button class="btn btn-default" id="bottoneMioNegozio"
											type="submit">
											<span>Mio profilo</span> <span
												class="glyphicon glyphicon-th-list"></span>
										</button>
									</form>
								</li>
								<li>
									<form action="LogoutHome">
										<button class="btn btn-default" id="bottoneEsciLogin">
											<span>Esci</span> <span class="glyphicon glyphicon-off"></span>
										</button>
									</form>
								</li>

							</ul>
						</div>
					</li>

					<li><button id="bottonePreferitiBarraNavigazione"
							type="button" class="btn btn-primary btn-md" data-toggle="modal"
							data-target="#Preferiti">
							<span class="glyphicon glyphicon-heart"></span> <span>Preferiti</span>
						</button></li>
				</ul>

			</div>
		</div>

	</nav>

	<!-- Modal preferiti, codice va fuori alla navbar-->
	<div class="modal fade" id="Preferiti" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<!--intestazione model Preferiti -->
					<div class="row">
						<h3 class="modal-title col-xs-10">LISTA PREFERITI</h3>
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal" id="bottoneChiudiPreferiti">Chiudi</button>
					</div>
				</div>

				<!-- id bodyPreferiti usato in javascript-->
				<div class="modal-body" id="bodyPreferiti">
					<div class="container-fluid">


						<table id="main" class="table table-hover table-condensed">
							<tbody id="tbodyContenutoPreferiti">
							</tbody>

						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer" id="modalFooterPreferiti">
				<!-- contiene bottoni modelPreferiti -->
				<button class="btn btn-default btn-lg" type="button"
					id="cestinoPreferiti">
					<span class="glyphicon glyphicon-trash"></span>
				</button>
			</div>

		</div>
	</div>
	<!-- Fine modal preferiti -->


	<%
		utenteBean utente = (utenteBean) request.getAttribute("utente");
	%>


	<div class="container" id="home">
		<!-- div che contiene tutto il form di registrazione-->
		<div class="row" id="registrazione">
			<!-- dichiaro una riga di boostrap -->
			<div class="col-xs-10 col-xs-offset-2">
				<!-- div di 8 colonne, centrato-->


				<div class="contenitoreDati">
					<label class="contenutoLabel">Nome</label>
					<p class="contenutoParagrafi">
						<%=utente.getNome()%>
					</p>
				</div>

				<div class="contenitoreDati">
					<label class="contenutoLabel">Email</label>
					<p class="contenutoParagrafi">
						<%=utente.getEmail()%>
					</p>
				</div>

				<div class="contenitoreDati">
					<label class="contenutoLabel">Regione</label>
					<p class="contenutoParagrafi">
						<%=utente.getRegione()%>
					</p>
				</div>

				<div class="contenitoreDati">
					<label class="contenutoLabel">Sesso</label>
					<p class="contenutoParagrafi">
						<%=utente.getSesso()%>
					</p>
				</div>

				<div class="contenitoreDati">
					<label class="contenutoLabel">Anno di nascita</label>
					<p class="contenutoParagrafi">
						<%=utente.getAnno_nascita()%>
					</p>
				</div>

				<div class="contenitoreDati">

					<form action="ModificaDatiMioProfilo" id="formModificaPassword"
						method="post">

						<label class="contenutoLabel" id="labelPassword">Password</label>
						<input name="password" id="campoPassword" placeholder="password">

						<!--nascosto -->
						<label id="labelPasswordErrato">Inserisci una password
							alfanumerica valida, di almeno 8 caratteri</label>

						<button type="submit" class="bottoneGestionePassword"
							id="bottoneConferma">
							<strong>Conferma</strong>
						</button>

					</form>

					<form action="ModificaDatiMioProfilo" id="formEliminazioneAccount">

						<label id="labelConfermaEliminazioneAccount">Sei sicuro di
							voler eliminare l'account?</label> <input
							id="inputNascostoEliminaAccount" name="eliminaAccount" />
						<button type="submit" id="bottoneConfermaCancellazioneAccount">
							<strong>Conferma</strong>
						</button>

					</form>

					<button class="bottoneGestionePassword" id="bottoneAnnulla">
						<strong>Annulla</strong>
					</button>
					<button class="bottoneGestionePassword"
						id="bottoneModificaPassword">
						<strong>Modifica password</strong>
					</button>

					<%
						Object passwordModificata = request.getAttribute("passwordModificata");

						if (passwordModificata != null) {
					%>

					<label id="labelPasswordModificata">Password modificata</label>

					<%
						}
					%>

					<button id="bottoneModificaAccount">
						<strong>Cancella account</strong>
					</button>

				</div>


			</div>
			<!--chiudo riga di bootstrap-->
		</div>
		<!-- chiudo div che contiene form-->
	</div>

	<footer class="footer container-fluid text-center" id="footer">
		<!--inizio footer -->
		<div class="row">
			<div class="payments col-md-4">
				<a href="chisiamo.html"><Strong>Chi siamo </Strong></a> <a
					href="chisiamo.html"><span class="glyphicon glyphicon-zoom-in"></span></a>
			</div>

			<div class="social col-md-4">
				<a href="https://it-it.facebook.com"><Strong>Seguici</Strong></a> <a
					href="https://it-it.facebook.com"><span
					class="glyphicon glyphicon-random"></span></a>
			</div>
			<div class="payments col-md-4">
				<a href="contattaci.html"><Strong>Contattaci </Strong></a> <a
					href="contattaci.html"><span
					class="glyphicon glyphicon-bullhorn"></span></a>
			</div>
		</div>

	</footer>
	<!--fine footer -->

	<script src="mioProfiloScript.js"></script>
	<script src="caricaPreferitiScript.js"></script>
	<script src="BottoneEliminaOggettoPreferiti.js"></script>
	<script src="controllaCasellaMessaggi.js"></script>

</body>
</html>
