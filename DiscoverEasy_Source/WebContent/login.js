$(document).ready(function(){

	/*Controlla se è stato effettuato l'accesso*/

	controllaAutenticazione();

	function controllaAutenticazione(){

		$.post("Login",
				{

			verificaAccesso: "nessunValore" //variabile sentinella

				},

				function(data,status){

					var accesso = JSON.parse(data);

					if(accesso.accessoEffettuato == true)
					{
						loginEffettuato();

						if(accesso.accessoGestore == true)
						{
							gestoreAutenticato();
						}
					}

				});
	}

	/*Fine controllo*/

	/*MODAL ACCEDI*/

	/*Pulsante Accedi che effettua Login, serve per l'accesso*/
	$("#bottoneAccediModel").click(function(){

		var testoEmail = $("#inputModalEmail").val();/*La funzione val ritorna il valore di testo del campo email*/
		var testoPassword =$("#inputModalPassword").val();/*La funzione val ritorna il valore di testo del campo password*/
		$.post("Login",
				{
			email: testoEmail,

			password: testoPassword
				},

				function(data,status){

					if(!(JSON.parse(data).validita) == true)/*se hai inserito password ed email non presenti sul database*/
					{

						$("#labelEmailPasswordErrati").css({"display": "inline"});
						$(".labelModel").css({"color": "red"});
						$("#inputModalPassword").css({"border-color": "red"});
						$("#inputModalEmail").css({"border-color": "red"});
					}
					else
					{
						window.location.reload();
					}

				}); /*parentesi di $.post*/

	});

	function loginEffettuato(){
		
		$("#dropDownAccedi").css({"display": "inline"});
		$("#bottoneRegistratiBarraNavigazione").css({"display": "none"});
		$("#bottoneAccediBarraNavigazione").css({"display": "none"});
		$("#bottoneGestioneBarraNavigazione").css({"display": "none"});
		$("#bottoneChat").css({"display": "inline"});
		$("#bottoneChiudiModel").click();
		
		pulsanteRicerca();
	}

	function gestoreAutenticato(){

		$("#bottoneGestioneBarraNavigazione").css({"display": "inline"});

	}

	/*Pulsante per pagina Ricerca, è il pulsante messaggia*/
	
	function pulsanteRicerca(){
		 $(".pulsanteMessaggia").css({"display": "inline"});
	}
	
	/*Fine modal accedi*/

});