$(document).ready(function(){

	$("#campoPassword").blur(function (){validazionePassword()});

	function validazionePassword(){

		var patternPassword = /^[0-9a-z]{8,40}$/i;/*Cerca dall'inizio alla fine se hai inserito una password di almeno 8
		 caratteri indicato nel il primo parametro della parentesi graffa e massimo 40 caratteri nel secondo 
		 parametro parentesi*/

		if(document.getElementById("campoPassword").value.match(patternPassword))
		{
			$("#campoPassword").css({"border-color": "#ccc"});
			$("#labelPassword").css({"color": "#ffd700"});
			$("#labelPasswordErrato").css({"display": "none"});

			return true;
		}
		else
		{
			$("#campoPassword").css({"border-color": "red"});
			$("#labelPassword").css({"color": "red"});
			$("#labelPasswordErrato").css({"display": "block"});

			return false;
		}
	}
	
	$("#formModificaPassword").submit(function() {

		/* se la funzione validaPassword() restituisce true viene fatto return true 
		  e quindi eseguito submit altrimenti no */
		
		return validazionePassword();

	});
	
	/*Quando clicco sul pulsante modifica password*/
	
	$("#bottoneModificaPassword").click(function(){

		$(this).css({"display": "none"});

		$("#campoPassword").css({"display": "block"});

		$("#bottoneConferma").css({"display": "inline"});

		$("#bottoneAnnulla").css({"display": "inline"});
		
		$("#labelPasswordModificata").css({"display": "none"});
		
		$("#bottoneModificaAccount").css({"display": "none"});
		
		return false;
	});
	

	/*Quando clicco sul pulsante annulla del modifica password*/

	$("#bottoneAnnulla").click(function(){

		$("#bottoneModificaPassword").css({"display": "inline"});

		$("#campoPassword").css({"display": "none"});

		$("#campoPassword").css({"border-color": "#ccc"});
		
		$("#labelPassword").css({"color": "#ffd700"});
		
		$("#labelPasswordErrato").css({"display": "none"});
		
		$("#bottoneConferma").css({"display": "none"});

		$("#bottoneAnnulla").css({"display": "none"});
		
		$("#labelConfermaEliminazioneAccount").css({"display": "none"});

		$("#bottoneModificaAccount").css({"display": "inline"});
		
		$("#bottoneConfermaCancellazioneAccount").css({"display": "none"});

		return false;
	});
	
	$("#bottoneModificaAccount").click(function(){

		$(this).css({"display": "none"});
		
		$("#labelConfermaEliminazioneAccount").css({"display": "block"});
		
		$("#bottoneModificaPassword").css({"display": "none"});
		
		$("#bottoneConfermaCancellazioneAccount").css({"display": "inline"});

		$("#bottoneAnnulla").css({"display": "inline"});
		
		$("#labelPasswordModificata").css({"display": "none"});

		return false;
	});
});
