<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="model.beans.OggettoBean"%>
<%@ page import="model.beans.utenteBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.GregorianCalendar"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Ricerca - DiscoverEasy</title>
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
<link rel="stylesheet" href="stylesheet\stylePaginaRicerca.css">
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
						<form action="RicercaGestore">
							<button id="bottoneGestioneBarraNavigazione" type="submit"
								class="btn btn-primary btn-md" data-toggle="modal"
								data-target="#Accedi">
								<span class="glyphicon glyphicon-tasks"></span> <span>Gestione</span>
							</button>
						</form>
					</li>

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
					<!-- Tasto Accedi Registrati e Preferiti-->
					<!-- Bottone che innesca il model accedi-->
					<li>
						<button id="bottoneAccediBarraNavigazione" type="button"
							class="btn btn-primary btn-md" data-toggle="modal"
							data-target="#Accedi">
							<span class="glyphicon glyphicon-user"></span> <span>Accedi</span>
						</button>
					</li>

					<li id="bottoneRegistratiBarraNavigazione"><a
						href="Registrazione.html"><span
							class="glyphicon glyphicon-log-in"></span> <span>Registrati</span></a></li>

					<!-- Bottone che innesca il model registrati-->
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
		<div class="row" id="ricerca">
			<!-- dichiaro una riga di boostrap -->
			<div class="container-fluid text-center" id="containerRicerca">
				<!--inizio div ricerca -->
				<div class="row">

					<form action="Ricerca">
						<div class="col-md-5 form-group">
							<!--Inserisci nome oggetto -->
							<input type="text" class="form-control" name="barraRicerca"
								placeholder="Scrivi un oggetto da ricercare">
						</div>


						<div class="col-md-3 form-group">
							<select class="form-control" name="selectCategoria">
								<option>Tutte le categorie</option>
								<option>Veicoli</option>
								<option>Informatica</option>
								<option>Casalinghi</option>
								<option>Altro</option>
							</select>
						</div>

						<div class="col-md-3 form-group">
							<select class="form-control" name="selectRegione">
								<option>Tutta Italia</option>
								<option>Abruzzo</option>
								<option>Basilicata</option>
								<option>Calabria</option>
								<option>Campania</option>
								<option>Emilia-Romagna</option>
								<option>Friuli-Venezia-Giulia</option>
								<option>Lazio</option>
								<option>Liguria</option>
								<option>Lombardia</option>
								<option>Marche</option>
								<option>Molise</option>
								<option>Piemonte</option>
								<option>Puglia</option>
								<option>Sardegna</option>
								<option>Sicilia</option>
								<option>Toscana</option>
								<option>Trentino-Alto-Adige</option>
								<option>Umbria</option>
								<option>Valle d'Aosta</option>
								<option>Veneto</option>
							</select>
						</div>

						<div class="col-md-1 form-group">
							<button type="submit" class="btn btn-default" id="bottoneRicerca">
								<strong>Cerca</strong>
							</button>
						</div>

					</form>

				</div>
			</div>
			<!--fine div ricerca -->
			<div class="container">
				<table id="main" class="table table-hover table-condensed">
					<tbody>
						<%
							@SuppressWarnings("unchecked")
							ArrayList<OggettoBean> risultatiRicerca = (ArrayList<OggettoBean>) request.getAttribute("risultatiRicerca");
						%>


						<%
							if (risultatiRicerca.size() == 0) {
						%>

						<tr>
							<td>Nessun risultato trovato</td>
						</tr>

						<%
							}
						%>

						<%
							Object email = request.getAttribute("email");
						
							OggettoBean annuncio;
							
							GregorianCalendar dataOra;
						
							for (int i = 0; i < risultatiRicerca.size(); i++) {
								
							annuncio= risultatiRicerca.get(i);
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
							<form action="IniziaNuovaChat">
								<div>
									<h4>
										<strong data-th="nome"><%=annuncio.getNome()%></strong>
									</h4>
									<div id="bottoneAggiungiPreferiti"
										name="bottoneAggiungiPreferitiScript">
										<button class="btn btn-defoult">
											<span id="spanIconaPreferiti"
												class="glyphicon glyphicon-heart"> </span>
										</button>
									</div>
								</div>

								<div class="descrizioneProdotto"><%=annuncio.getDescrizione()%></div>
								<div></div>
								<div>
									<h4 class="prezzoProdotto"><%=annuncio.getPrezzo()%>€
									</h4>
								</div>
								<div data-th="regione"><%=annuncio.getRegione()%></div>
								<div>
									<hr>
									<span class="glyphicon glyphicon-envelope contatti"
										data-th="email"><%=annuncio.getEmail()%></span>
									&emsp;&emsp;&emsp; <span data-th="numero"
										class="glyphicon glyphicon-earphone contatti"> <%=annuncio.getNumero_proprietario()%>
									</span>

									<%dataOra = annuncio.getDataOra();%>
										<span id="labelDataOra">Pubblicato il: &emsp;
										<span data-th="anno"><%=dataOra.get(GregorianCalendar.YEAR)%></span><span>-</span>
										<span data-th="mese"><%=(dataOra.get(GregorianCalendar.MONTH)+1)%></span><span>-</span>
										<span data-th="giorno"><%=dataOra.get(GregorianCalendar.DAY_OF_MONTH)%></span>&emsp;
										<span data-th="ora"><%=dataOra.get(GregorianCalendar.HOUR_OF_DAY)%></span><span>:</span>
										<span data-th="minuti"><%=dataOra.get(GregorianCalendar.MINUTE)%></span><span>:</span>
										<span data-th="secondi"><%=dataOra.get(GregorianCalendar.SECOND)%></span>
									</span>
									
									 <input type="text" class="inputInviaMessaggio" name="nome" value=<%="\""+annuncio.getNome()+"\""%>>
									 <input type="text" class="inputInviaMessaggio" name="email" value=<%="\""+annuncio.getEmail()+"\""%>>
									 <input type="text" class="inputInviaMessaggio" name="anno" value=<%="\""+dataOra.get(GregorianCalendar.YEAR)+"\""%>>
									 <input type="text" class="inputInviaMessaggio" name="mese" value=<%="\""+(dataOra.get(GregorianCalendar.MONTH)+1)+"\""%>>
									 <input type="text" class="inputInviaMessaggio" name="giorno" value=<%="\""+dataOra.get(GregorianCalendar.DAY_OF_MONTH)+"\""%>>
									 <input type="text" class="inputInviaMessaggio" name="ora" value=<%="\""+dataOra.get(GregorianCalendar.HOUR_OF_DAY)+"\""%>>
									 <input type="text" class="inputInviaMessaggio" name="minuti" value=<%="\""+dataOra.get(GregorianCalendar.MINUTE)+"\""%>>
									 <input type="text" class="inputInviaMessaggio" name="secondi" value=<%="\""+dataOra.get(GregorianCalendar.SECOND)+"\""%>>
									
									<% if(email != null && !((String) email).trim().equals(annuncio.getEmail().trim())){%>
									<button type="submit" class="glyphicon glyphicon-comment pulsanteMessaggia">messaggia </button>
									<% }%>
									
								</div>								
							</form>
							</td>
						</tr>

						<%
							}
						%>

					</tbody>
				</table>
			</div>
			<!-- chiudo div che contiene ricerca-->
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

	<script src="login.js"></script>
	<script src="caricaPreferitiScript.js"></script>
	<script src="PaginaRicerca.js"></script>
	<script src="BottoneEliminaOggettoPreferiti.js"></script>
	<script src="controllaCasellaMessaggi.js"></script>

</body>
</html>
