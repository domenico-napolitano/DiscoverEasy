$(document).ready(function(){

	/*Setta dimensioni giuste */

	
	resize();
	
	function resize(){
		$("#conversazione").css({"height": ""+($(window).height()-276)});
		$("#contatti").css({"height": ""+($(window).height()-276)});

	}
	/*window.onresize = function () {


	};*/

	var chatAttiva = false;

	window.onresize = function (){

		$("#conversazione").css({"height": ""+($(window).height()-276)});
		$("#contatti").css({"height": ""+($(window).height()-276)});

		if($(window).width()  > 767){	

			$("#labelContatti").css({"display": "inline"});
			$("#contatti").css({"display": "inline"});

			$("#divPulsanteIndietro").css({"display": "none"});

		

		}
		else
		{
			/*Se la dimensione è minore uguale 767 che in bootstrap corrisponde alla classe xs e se l'utente ha cliccato
			  su un contatto quando si ridimensiona finestra allora bisogna rimanere blocco messaggi aperto, per questo
			  nell'if controllo se il div conversazione ha un attributo inline , se ce l'ha allora l'utente ha cliccato su
			  ad un contatto facendo attivare la funzione javascript carica messaggi che setta attributo display a inline,
			  al blocco di messaggi*/

			if(chatAttiva == true){

				$("#labelContatti").css({"display": "none"});
				$("#contatti").css({"display": "none"});

				$("#labelConversazione").css({"display": "inline"});
				$("#conversazione").css({"display": "inline"});
				
				$("#divPulsanteIndietro").css({"display": "inline"});

			}
			else
			{

				$("#labelConversazione").css({"display": "none"});
				$("#conversazione").css({"display": "none"});

			}

		}

	}

	$("#pulsanteIndietro").click(function(){

		chatAttiva = false;

		$("#divPulsanteIndietro").attr("class", "col-xs-0");
		$("#labelConversazione").attr("class", "col-xs-0 col-sm-6");
		$("#labelContatti").attr("class", "col-xs-12 col-sm-6");

		$("#conversazione").attr("class", "col-xs-0 col-sm-6");
		$("#contatti").attr("class", "col-xs-12 col-sm-6");

		$("#divPulsanteIndietro").css({"display": "none"});
		$("#labelConversazione").css({"display": "none"});
		$("#conversazione").css({"display": "none"});

		$("#labelContatti").css({"display": "inline"});
		$("#contatti").css({"display": "inline"});

	});

	$(".contenitoreContatto").click(function(){/*Quando clicchi la riga della chat*/

		$(this).find(".simboloNotificaChat").css({"display": "none"});

		chatAttiva = true;


		$("#divPulsanteIndietro").attr("class", "col-xs-2");
		$("#labelConversazione").attr("class", "col-xs-10 col-sm-6");
		$("#labelContatti").attr("class", "col-xs-0 col-sm-6");

		$("#conversazione").attr("class", "col-xs-12 col-sm-6");
		$("#contatti").attr("class", "col-xs-0 col-sm-6");
		
		$("#labelConversazione").css({"display": "inline", "color": "#ffd700", "font-size": "22px"});
		$("#conversazione").css({"display": "inline", "overflow-x": "hidden", "overflow-y": "scroll"});
	
		if($(window).width()  <= 767){	


			$("#divPulsanteIndietro").css({"display": "inline"});


			$("#labelContatti").css({"display": "none"});
			$("#contatti").css({"display": "none"});

		}
		
		var nomeAnnuncio =  $(this).find("span[data-th=nomeAnnuncio]").text();

		//alert("nome"+nomeAnnuncio);

		$("span[data-th=nomeAnnuncioBarraInput]").text(nomeAnnuncio);


		var emailMittente =  $(this).find("span[data-th=emailMittente]").text();

		//	alert("emailMittente"+emailMittente);

		$("span[data-th=emailMittenteChatBarraInput]").text(emailMittente);


		var emailDestinatario =  $(this).find("span[data-th=emailDestinatario]").text();

		//	alert("emailDestinatario"+emailDestinatario);

		$("span[data-th=emailDestinatarioChatBarraInput]").text(emailDestinatario);


		var anno = $(this).find("span[data-th=anno]").text();

		//	alert("anno"+anno);

		$("span[data-th=annoBarraInput]").text(anno);


		var mese = $(this).find("span[data-th=mese]").text();

		//	alert("mese"+mese);

		$("span[data-th=meseBarraInput]").text(mese);


		var giorno = $(this).find("span[data-th=giorno]").text();

		//	alert("giorno"+giorno);

		$("span[data-th=giornoBarraInput]").text(giorno);


		var ora = $(this).find("span[data-th=ora]").text();

		//	alert("ora"+ora);

		$("span[data-th=oraBarraInput]").text(ora);


		var minuti = $(this).find("span[data-th=minuti]").text();

		//	alert("minuti"+minuti);

		$("span[data-th=minutiBarraInput]").text(minuti);


		var secondi = $(this).find("span[data-th=secondi]").text();

		//	alert("secondi"+secondi);

		$("span[data-th=secondiBarraInput]").text(secondi);



		caricamessaggi(nomeAnnuncio, emailMittente, emailDestinatario, anno, mese, giorno, ora, minuti, secondi);

	});

	function caricamessaggi (nomeAnnuncio, emailMittente, emailDestinatario, anno, mese, giorno, ora, minuti, secondi){

		$.post("CaricaMessaggi",
				{
			nomeAnnuncio: nomeAnnuncio,
			emailMittente: emailMittente,
			emailDestinatario: emailDestinatario,
			anno: anno,
			mese: mese,
			giorno: giorno,
			ora: ora,
			minuti: minuti,
			secondi: secondi
				},

				function(data,status)
				{
					var listaMessaggi = JSON.parse(data);

					$("#nessunMessaggio").remove();
					$(".divMessaggioInviato").remove();
					$(".divMessaggioRicevuto").remove();

					if(listaMessaggi.length == 0){

						$("#conversazione").append("<span id=\"nessunMessaggio\">Nessun mesaggio inviato o ricevuto</span>");
					}


					for(var i=0; i < listaMessaggi.length-1; i++){

						if(listaMessaggi[i].email_mittente_messaggio == listaMessaggi[listaMessaggi.length-1].emailUtente){

							$("#conversazione").append(

									"<div class=\"divMessaggioInviato\">" +

									"<p class=\"messaggioInviato\">"+listaMessaggi[i].descrizione+"</p>"+

							"</div>");
						}
						else
						{
							$("#conversazione").append(

									"<div class=\"divMessaggioRicevuto\">"+

									"<p class=\"messaggioRicevuto\">"+listaMessaggi[i].descrizione+"</p>"+

							"</div>");	
						}

					}

				}
		);
	};




	$("#inviaMessaggio").click(function (){

		var descrizione = $("#barraInputMessaggio").val();

		$("#barraInputMessaggio").val("");

		var nomeAnnuncio = $("span[data-th=nomeAnnuncioBarraInput]").text();

		var emailMittenteChat = $("span[data-th=emailMittenteChatBarraInput]").text();

		var emailDestinatarioChat = $("span[data-th=emailDestinatarioChatBarraInput]").text();

		var annoAnnuncio = 	$("span[data-th=annoBarraInput]").text();

		var meseAnnuncio = 	$("span[data-th=meseBarraInput]").text();

		var giornoAnnuncio = $("span[data-th=giornoBarraInput]").text();

		var oraAnnuncio = $("span[data-th=oraBarraInput]").text();

		var minutiAnnuncio = $("span[data-th=minutiBarraInput]").text();

		var secondiAnnuncio = $("span[data-th=secondiBarraInput]").text();


		$.post("InviaMessaggio",
				{
			descrizione: descrizione,
			nomeAnnuncio: nomeAnnuncio,
			emailMittenteChat: emailMittenteChat,
			emailDestinatarioChat: emailDestinatarioChat,
			annoAnnuncio: annoAnnuncio,
			meseAnnuncio: meseAnnuncio,
			giornoAnnuncio: giornoAnnuncio,
			oraAnnuncio: oraAnnuncio,
			minutiAnnuncio: minutiAnnuncio,
			secondiAnnuncio: secondiAnnuncio

				},

				function(data,status)
				{

					caricamessaggi(nomeAnnuncio, emailMittenteChat, emailDestinatarioChat, annoAnnuncio, meseAnnuncio,
							giornoAnnuncio, oraAnnuncio, minutiAnnuncio, secondiAnnuncio);
				}
		);


	});

});
