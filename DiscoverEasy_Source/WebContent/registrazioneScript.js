$(document).ready(function(){

	caricaSessione();

	function caricaSessione(){

		$.post("Sessione",
				{
				},

				function(data,status){


					var listaOggettiPreferiti = JSON.parse(data);

					for(var i=1; i < listaOggettiPreferiti.length; i++)
					{
						$("#tbodyContenutoPreferiti").append(


								'<tr>'+
								'<td data-th="Prodotti">'+
								'<div class="row">'+
								'<div class="col-xs-12">'+
								'<img src="'+listaOggettiPreferiti[i].immagine+'" id="immagineOggetto">'+

								'</div>'+
								'</div>'+
								'</td>'+

								'<td data-th="Costo">'+
								'<div>'+
								'<h4>'+
								'<strong data-th="nome">'+listaOggettiPreferiti[i].nome+'</strong>'+
								'</h4>'+
								'</div>'+
								'<div class="descrizioneProdotto">'+
								listaOggettiPreferiti[i].descrizione+'</div>'+
								'<div></div>'+
								'<div>'+
								'<h4 class="prezzoProdotto">'+listaOggettiPreferiti[i].prezzo+'€</h4>'+
								'</div>'+
								'<div>'+listaOggettiPreferiti[i].regione+'</div>'+
								'<div>'+
								'<hr>'+
								'<span data-th="email" class="glyphicon glyphicon-envelope contatti">'+'&nbsp;'+
								listaOggettiPreferiti[i].email_venditore+'</span> &emsp;&emsp;&emsp;'+ 
								'<span class="glyphicon glyphicon-earphone contatti">'+'&nbsp;'+
								listaOggettiPreferiti[i].telefono+'</span>'+
								'</div>'+

								'<div class="divBottoneEliminaOggettoPreferiti">'+
								'<button class="btn btn-sm bottoneEliminaOggettoPreferiti" style="margin-top: 18px" type="button ">'+
								'<span style="font-size: 12px">Elimina dai preferiti</span>'+
								'</button>'+
								'</div>'+

								'</td>'+
								'</tr>'

						);
					}

				});

	}


	/*validazione campo email*/
	$("#EmaildiRegistrazione").blur(function(){validazioneEmailRegistrazione()});

	function validazioneEmailRegistrazione(){

		var patternEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/;

		if(document.getElementById("EmaildiRegistrazione").value.match(patternEmail))
		{
			$("#EmaildiRegistrazione").css({"border-color": "#ccc"});
			$("#labelModelEmailRegistrazione").css({"color": "#ffd700"});
			$("#labelEmailErratoRegistrazione").css({"display": "none"});

			/*Se ha inserito un email valida allora lo script controlla se l'email inserita già esiste o no*/

			var emailInseritaDaUtente = $("#EmaildiRegistrazione").val();
			
			$.post("Registrazione",
					{
				emailRegistrazione : emailInseritaDaUtente
					},
					function(data, status){

						/*email esiste se la variabile controlloEsisteEmail è uguale a true e quindi segnala errore 
						 altrimenti no*/

						var controlloEsisteEmail = JSON.parse(data);



						if(controlloEsisteEmail.emailEsistente)
						{
							$("#EmaildiRegistrazione").css({"border-color": "red"});
							$("#labelModelEmailRegistrazione").css({"color": "red"});
							$("#labelEmailEsistente").css({"display": "block"});
							return false;
						}
						else
						{
							$("#EmaildiRegistrazione").css({"border-color": "#ccc"});
							$("#labelModelEmailRegistrazione").css({"color": "#ffd700"});
							$("#labelEmailEsistente").css({"display": "none"});
						}
					});


			return true;	
		}
		else
		{
			$("#EmaildiRegistrazione").css({"border-color": "red"});
			$("#labelModelEmailRegistrazione").css({"color": "red"});
			$("#labelEmailErratoRegistrazione").css({"display": "block"});
			$("#labelEmailEsistente").css({"display": "none"});
			return false;
		}

	}

	/*validazione campo nome*/


	$("#nomeRegistrazione").blur(function(){ validazioneNomeRegistrazione()});


	function validazioneNomeRegistrazione(){

		var patternNome= /^[a-zA-Z]{1,30}$/;/*Cerca dall'inizio alla fine se hai inserito nome di almeno 1 lettera ,
		indicato nel primo parametro della parentesi graffa e massimo 250 lettere nel secondo parametro della parentesi graffa*/

		if(document.getElementById("nomeRegistrazione").value.match(patternNome))
		{

			$("#nomeRegistrazione").css({"border-color": "#ccc"});
			$("#labelNome").css({"color": "#ffd700"});
			$("#labelNomeErrato").css({"display": "none"});

			return true;
		}
		else
		{
			$("#nomeRegistrazione").css({"border-color": "red"});
			$("#labelNome").css({"color": "red"});
			$("#labelNomeErrato").css({"display": "block"});
			return false;
		}
	}

	/*validazione campo telefono*/

	$("#telefonoRegistrazione").blur(function(){validazioneTelefonoRegistrazione()});

	function validazioneTelefonoRegistrazione(){

		var patternTelefono = /^[0-9]{9,10}$/;/*Cerca dall'inizio alla fine se hai inserito un numero di 
		telefono di almeno 9 numeri ,indicato nel primo parametro della parentesi graffa e massimo 10 numeri
		 nel secondo parametro della parentesi graffa*/

		if(document.getElementById("telefonoRegistrazione").value.match(patternTelefono))
		{
			$("#telefonoRegistrazione").css({"border-color": "#ccc"});
			$("#labelTelefono").css({"color": "#ffd700"});
			$("#labelTelefonoErrato").css({"display": "none"});

			return true;
		}
		else
		{
			$("#telefonoRegistrazione").css({"border-color": "red"});
			$("#labelTelefono").css({"color": "red"});
			$("#labelTelefonoErrato").css({"display": "block"});

			return false;
		}
	}

	/*validazione campo Password*/
	$("#PasswordRegistrazione").blur(function (){validazionePasswordRegistrazione()});

	function validazionePasswordRegistrazione(){

		var patternPassword = /^[0-9a-z]{8,40}$/i;/*Cerca dall'inizio alla fine se hai inserito una password di almeno 8
		 caratteri indicato nel il primo parametro della parentesi graffa e massimo 40 caratteri nel secondo 
		 parametro parentesi*/

		if(document.getElementById("PasswordRegistrazione").value.match(patternPassword))
		{
			$("#PasswordRegistrazione").css({"border-color": "#ccc"});
			$("#labelPassword").css({"color": "#ffd700"});
			$("#labelPasswordErratoRegistrazione").css({"display": "none"});

			return true;
		}
		else
		{
			$("#PasswordRegistrazione").css({"border-color": "red"});
			$("#labelPassword").css({"color": "red"});
			$("#labelPasswordErratoRegistrazione").css({"display": "block"});

			return false;
		}
	}


	/*validazione campo radio button sesso*/

	/*Uso blur in modo che quando il campo perde il focus mi effettua validazione questo serve 
	 quando utente una volta visualizzato grafica errore nel campo , cioè scritta errore e colore input
	 rosso , quando riscrive nel campo il valore giusto ed esce dal campo quindi perde focus , devono
	 scomparire scritte errore*/
	$("#femminaRadio").blur(function(){validazioneCampoRadioSesso()});

	$("#maschioRadio").blur(function(){validazioneCampoRadioSesso()});


	function validazioneCampoRadioSesso(){

		var femmina = document.getElementById("femminaRadio").checked /*valore booleano*/

		var maschio = document.getElementById("maschioRadio").checked /*valore booleano*/


		if(femmina || maschio)
		{	
			$("#labelSessoRadioErrore").css({"display": "none"});
			$("#labelSessoRadio").css({"color": "#ffd700"});

			return true;
		}
		else
		{
			$("#labelSessoRadioErrore").css({"display": "block"});
			$("#labelSessoRadio").css({"color": "red"});

			return false;
		}
	}

	/*validazione campo select anno nascita*/

	$("#selezionaAnnoNascita").blur(function(){validazioneSelectAnnoNascita()});

	function validazioneSelectAnnoNascita(){

		/*funzione trim toglie gli spazi a destra e a sinistra della stringa , to lowercase rende
		  la stringa minuscola*/
		if($("#selezionaAnnoNascita").val().trim().toLowerCase() == "anno")
		{
			$("#selezionaAnnoNascita").css({"border-color": "red"});
			$("#labelAnnoNascita").css({"color": "red"});
			$("#labelAnnoNascitaErrore").css({"display": "block"});

			return false;
		}
		else
		{
			$("#selezionaAnnoNascita").css({"border-color": "#ccc"});
			$("#labelAnnoNascita").css({"color": "#ffd700"});
			$("#labelAnnoNascitaErrore").css({"display": "none"});

			return true;
		}
	}

	/*validazione campo select regione*/

	$("#selezionaRegione").blur(function(){validazioneSelectRegione()});

	function validazioneSelectRegione(){

		/*funzione trim toglie gli spazi a destra e a sinistra della stringa , to lowercase rende
		  la stringa minuscola*/
		if($("#selezionaRegione").val().trim().toLowerCase() == "regione")
		{
			$("#selezionaRegione").css({"border-color": "red"});
			$("#labelRegione").css({"color": "red"});
			$("#labelRegioneErrore").css({"display": "block"});

			return false;
		}
		else
		{
			$("#selezionaRegione").css({"border-color": "#ccc"});
			$("#labelRegione").css({"color": "#ffd700"});
			$("#labelRegioneErrore").css({"display": "none"});

			return true;
		}
	}

	/*validazione checkbox casella condizioni*/

	$("#checkboxCondizioni").blur(function(){controlloSpuntaCasellaCondizioni()});

	function controlloSpuntaCasellaCondizioni(){

		if(document.getElementById("checkboxCondizioni").checked)
		{
			$("#checkboxCondizioniErrore").css({"display": "none"});
			return true;
		}
		else
		{
			$("#checkboxCondizioniErrore").css({"display": "block"});
			return false;
		}
	}

	/*Funzione che richiama tutte le funzioni di validazione del form e viene associata al pulsante
	  registrati o meglio al submit quindi se i campi del form non sono riempiti in modo corretto
	  allora non viene mandata la richiesta al server di registrazione*/
	function validaFormRegistrazione(){

		/*basta almeno una delle funzioni nell'input dell'if che ritorna false quindi la validazione non è
		  andata a buon fine, che fa eseguire l'else dell'if in modo che la registrazione non venga
		  mandata al server finche l'utente non scrive dati nel form corretti */

		var email = validazioneEmailRegistrazione();

//		alert("email: "+email);


		var password = validazionePasswordRegistrazione();

//		alert("password: "+password);

		var radioSesso = validazioneCampoRadioSesso();

//		alert("sesso: "+radioSesso);

		var annoNascita = validazioneSelectAnnoNascita();

//		alert("nascita: "+annoNascita);

		var regione = validazioneSelectRegione();

//		alert("regione: "+regione);

		var nome = validazioneNomeRegistrazione();

//		alert("nome: "+nome);

		var telefono = validazioneTelefonoRegistrazione();

//		alert("telefono: "+telefono);

		var casellaCondizione = controlloSpuntaCasellaCondizioni();

//		alert("condizioni: "+casellaCondizione);

		if(email && password && radioSesso && annoNascita && regione && telefono && casellaCondizione && nome)
		{
			return true;
		}
		else
		{
			return false;
		}

	}


	$("#form").submit(function() {

		/* se la funzione validaFormRegistrazione restituisce true viene fatto return true 
		  e quindi eseguito submit altrimenti no return validaFormRegistrazione(); */

		return validaFormRegistrazione();
	});


});/*parentesi $(document).ready(*/
