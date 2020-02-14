<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="model.beans.OggettoBean"%>
<%@ page import="model.beans.utenteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Il Mio Negozio - DiscoverEasy</title>
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
<link rel="stylesheet" href="stylesheet\styleMioNegozio.css">
</head>
<body>


	<%
		@SuppressWarnings("unchecked")
		ArrayList<OggettoBean> risultatiRicerca = (ArrayList<OggettoBean>) request.getAttribute("listaOggetti");
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
				<a href="home.html" id="logo" class="navbar-brand"><img
					id="immagineLogo" class="img-rounded" src="Img/Logo4.png"
					alt="logo"></a>
			</div>


			<div class="collapse navbar-collapse" id="navbar-collapse-main">
				<!--menu navbar destro -->

				<ul class="nav navbar-nav navbar-right">

					<%
						if ((Integer) request.getAttribute("isAmministratore") == 1) {
					%>
					<li>
						<form action="RicercaGestore">
							<button id="bottoneGestioneBarraNavigazione" type="submit"
								class="btn btn-primary btn-md" data-toggle="modal"
								data-target="#Accedi">
								<span class="glyphicon glyphicon-tasks"></span> <span>Gestione</span>
							</button>
						</form>
					</li>
					<%
						}
					%>
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

					<!-- Tasto Preferiti-->

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
						<h3 class="modal-title col-xs-10" id="labelTitoloPreferiti">LISTA
							PREFERITI</h3>
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal" id="bottoneChiudiPreferiti">Chiudi</button>
					</div>
				</div>

				<!-- tbodyContenutoPreferiti usato in javascript-->
				<div class="modal-body" id="bodyPreferiti">
					<div class="container-fluid">

						<table class="table table-hover table-condensed">
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
					<span class="coloreCestino glyphicon glyphicon-trash "></span>
				</button>
			</div>

		</div>
	</div>
	<!-- Fine modal preferiti -->

	<!-- Modal pulsante Accedi, codice va fuori alla navbar-->
	<div class="modal fade" id="Accedi" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header" id="modalHeaderAccedi">
					<!--intestazione modelAccedi -->
					<h2 class="modal-title" id="modalAccedi">Accedi</h2>
				</div>

				<div class="modal-body" id="corpoModelAccedi">
					<!--corpo del modelAccedi -->

					<form id="formModalAccedi">
						<label class="labelModel" id="labelModelEmail">Email</label> <input
							type="email" class="form-control" id="inputModalEmail"
							name="emailModalAccedi" placeholder="Inserisci email"> <label
							id="labelEmailErrato">Inserisci un email valida</label>
						<!--nascosto -->

						<label class="labelModel" id="labelModelPassword">Password</label>
						<input type="password" class="form-control"
							name="passwordModalAccedi" id="inputModalPassword"
							placeholder="Inserisci password">
						<!--nascosto -->
						<label id="labelPasswordErrato">Inserisci una password
							alfanumerica valida, di almeno 8 caratteri</label>

						<!--nascosto -->
						<label id="labelEmailPasswordErrati">Hai inserito email o
							password errati</label>

						<!--bottoni modelAccedi -->
						<div class="contenitoreBottoniAccedi">
							<button id="bottoneAccediModel" type="button"
								class="btn btn-primary">Accedi</button>
							<button id="bottoneChiudiModel" type="button"
								class="btn btn-default" data-dismiss="modal">Chiudi</button>
						</div>
					</form>
				</div>

			</div>
			<!-- div moda-content-->
		</div>
	</div>
	<!--fine modal -->



	<div class="container" id="home">
		<!-- div che contiene il mio negozio-->
		<div class="row" id="mionegozio">
			<!-- dichiaro una riga di boostrap -->
			<div id="bottoneAggiungiProdotto">
				<a href="AggiungiAnnuncioNegozio.html"><span
					class="glyphicon glyphicon-plus"></span> <Strong>Aggiungi
						annuncio</Strong></a>
			</div>
			<div class="container-fluid">
				<table id="main" class="table table-hover table-condensed">


					<tbody>

						<%
							if (risultatiRicerca.size() == 0) {
						%>

						<tr>
							<td>Aggiungi un prodotto per visualizzarlo qui</td>
						</tr>

						<%
							}
						%>

						<%
							OggettoBean annuncio;

							GregorianCalendar dataOra;

							for (int i = 0; i < risultatiRicerca.size(); i++) {

								annuncio = risultatiRicerca.get(i);
						%>
						<tr data-th="rigaOggetto">
							<td data-th="Prodotti">
								<div class="row">
									<div class="col-xs-12">
										<img src="<%=annuncio.getImmagine()%>" id="immagineOggetto">

									</div>
								</div>
							</td>

							<td data-th="Costo">
								<div>
									<h4>
										<strong data-th="nome"><%=annuncio.getNome()%></strong>
									</h4>
								</div>

								<div class="descrizioneProdotto"><%=annuncio.getDescrizione()%></div>
								<div></div>
								<div>
									<h4 class="prezzoProdotto"><%=annuncio.getPrezzo()%>&euro;
									</h4>
								</div>
								<div data-th="regione"><%=annuncio.getRegione()%></div>
								<div>
									<hr>
									<span class="glyphicon glyphicon-envelope contatti"
										data-th="email"> <%=annuncio.getEmail()%>
									</span> &emsp;&emsp;&emsp; <span data-th="numero"
										class="glyphicon glyphicon-earphone contatti"> <%=annuncio.getNumero_proprietario()%></span>
								</div> <%
 	dataOra = annuncio.getDataOra();
 %>

								<div id="divDataOra">
									<span>Pubblicato il:</span> <span data-th="anno"><%=dataOra.get(GregorianCalendar.YEAR)%></span><span>-</span>
									<span data-th="mese"><%=(dataOra.get(GregorianCalendar.MONTH) + 1)%></span><span>-</span>
									<span data-th="giorno"><%=dataOra.get(GregorianCalendar.DAY_OF_MONTH)%></span>&emsp;
									<span data-th="ora"><%=dataOra.get(GregorianCalendar.HOUR_OF_DAY)%></span><span>:</span>
									<span data-th="minuti"><%=dataOra.get(GregorianCalendar.MINUTE)%></span><span>:</span>
									<span data-th="secondi"><%=dataOra.get(GregorianCalendar.SECOND)%></span>

								</div>
								<div class="divEliminaOggetto">
									<button class="btn btn-sm bottoneEliminaOggettoMioNegozio"
										style="margin-top: 18px" type="button">
										<span style="font-size: 12px">Elimina prodotto</span>
									</button>
								</div>

							</td>
						</tr>

						<%
							}
						%>


					</tbody>
				</table>
			</div>
			<!-- chiudo div che contiene mionegozio-->
		</div>
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

	<script src="EliminaProdottoMioNegozio.js"></script>
	<script src="caricaPreferitiScript.js"></script>
	<script src="BottoneEliminaOggettoPreferiti.js"></script>
	<script src="controllaCasellaMessaggi.js"></script>

</body>
</html>
