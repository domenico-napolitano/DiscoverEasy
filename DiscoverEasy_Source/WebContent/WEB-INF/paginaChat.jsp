<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="model.beans.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.GregorianCalendar"%>


<!-- nuovoo -->
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Chat DiscoverEasy</title>
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
<link rel="stylesheet" href="stylesheet/stylePaginaChat.css">
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

					<!-- Bottone che innesca il model preferiti-->
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

	<!--<div id="corpoHomePage"></div>-->

	<div class="container-fluid text-center"
		id="contenitoreConversazioniContatti">

		<div class="row">

			<label class="col-xs-12 col-sm-6" id="labelContatti">Chat</label> <label
				class="col-xs-0 col-sm-6" id="labelConversazione">Messaggi</label>

			<div id="divPulsanteIndietro" class="col-xs-0">
				<button id="pulsanteIndietro">X</button>
			</div>
		</div>

		<div class="row">

			<div class="col-xs-12 col-sm-6" id="contatti">


				<%
					ArrayList<ChatBean> listaChat = (ArrayList<ChatBean>) request.getAttribute("listaChat");
				%>

				<%
					if (listaChat.size() == 0) {
				%>

				<p>Non hai iniziato nessuna chat</p>

				<%
					}
				%>

				<%
					ChatBean chat;

					GregorianCalendar dataOra;

					String email = (String) request.getAttribute("email");

					for (int i = 0; i < listaChat.size(); i++) {

						chat = listaChat.get(i);
				%>

				<div class="contenitoreContatto">
					<img src="<%=chat.getImmagine()%>" id="immagineAnnuncioChat">

					<div class="divNomeVenditoreAnnuncio">

						<strong id="labelNomeVenditore"> <% if (!email.equals(chat.getEmailMittente())) {%>

							<%=chat.getNomeMittenteChat()%> <% } else { %> <%=chat.getNomeVenditoreAnnuncio()%>

							<%
								}
							%>

						</strong> <span id="labelNomeAnnuncio" data-th=nomeAnnuncio><%=chat.getNomeAnnuncio()%></span>


						<%if(email.equals(chat.getEmailMittente()) && chat.getMittenteMessaggioNonLetto() == 1 ){%>

						<span class="glyphicon glyphicon-info-sign simboloNotificaChat"></span>

						<%
						}
						else
						{	
							if(email.equals(chat.getEmailDestinatario()) && chat.getDestinatarioMessaggioNonLetto() == 1){%>

						<span class="glyphicon glyphicon-info-sign simboloNotificaChat"></span>
						<%
							}
						}
							
					 %>
						<span class="labelInformazioniMessaggi" data-th="emailMittente"><%=chat.getEmailMittente()%></span>
						<span class="labelInformazioniMessaggi"
							data-th="emailDestinatario"><%=chat.getEmailDestinatario()%></span>

						<%
							dataOra = chat.getDataOraAnnuncio();
						%>

						<span class="labelInformazioniMessaggi" data-th="anno"><%=dataOra.get(GregorianCalendar.YEAR)%></span>
						<span class="labelInformazioniMessaggi" data-th="mese"><%=(dataOra.get(GregorianCalendar.MONTH) + 1)%></span>
						<span class="labelInformazioniMessaggi" data-th="giorno"><%=dataOra.get(GregorianCalendar.DAY_OF_MONTH)%></span>
						<span class="labelInformazioniMessaggi" data-th="ora"><%=dataOra.get(GregorianCalendar.HOUR_OF_DAY)%></span>
						<span class="labelInformazioniMessaggi" data-th="minuti"><%=dataOra.get(GregorianCalendar.MINUTE)%></span>
						<span class="labelInformazioniMessaggi" data-th="secondi"><%=dataOra.get(GregorianCalendar.SECOND)%></span>
					</div>
				</div>

				<%
					}
				%>


			</div>

			<div class="col-xs-0 col-sm-6" id="conversazione">

				<div id="divBarraInputMessaggio">

					<span class="labelInformazioniBarraInput"
						data-th="nomeAnnuncioBarraInput"></span> <span
						class="labelInformazioniBarraInput"
						data-th="emailMittenteChatBarraInput"></span> <span
						class="labelInformazioniBarraInput"
						data-th="emailDestinatarioChatBarraInput"></span> <span
						class="labelInformazioniBarraInput" data-th="annoBarraInput"></span>
					<span class="labelInformazioniBarraInput" data-th="meseBarraInput"></span>
					<span class="labelInformazioniBarraInput"
						data-th="giornoBarraInput"></span> <span
						class="labelInformazioniBarraInput" data-th="oraBarraInput"></span>
					<span class="labelInformazioniBarraInput"
						data-th="minutiBarraInput"></span> <span
						class="labelInformazioniBarraInput" data-th="secondiBarraInput"></span>

					<div class="input-group">
						<input id="barraInputMessaggio" type="text" class="form-control"
							placeholder="Scrivi un messaggio"> <span
							class="input-group-btn">
							<button id="inviaMessaggio" class="btn btn-default" type="button">
								<span class="glyphicon glyphicon-send"></span>
							</button>
						</span>
					</div>
				</div>
			</div>

		</div>
		<!-- riga, row -->

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

	<script src="caricaMessaggi.js"></script>
	<script src="caricaPreferitiScript.js"></script>
	<script src="BottoneEliminaOggettoPreferiti.js"></script>
	<script src="controllaCasellaMessaggi.js"></script>

</body>
</html>
