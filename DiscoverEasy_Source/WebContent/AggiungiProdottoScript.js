$(document).ready(function(){

	$("#form").submit(function(){


		var nome = validazioneNome();

//		alert(nome)

		var selectCategoria = validazioneSelectCategoria();

//		alert(selectCategoria);

		var selectRegione = validazioneSelectRegione();

//		alert(selectRegione);

		var prezzo = validazionePrezzo();

//		alert(prezzo);

		var url = validazioneImmagine();

//		alert(url);

		var descrizione = validazioneDescrizione();

//		alert(descrizione);
		
		if(nome == false || selectCategoria == false || selectRegione == false || prezzo == false ||
				url == false || descrizione == false)
		{
			return false;
		}
		else
		{
			return true;
		}


	});


	$("input[name=nomeRegistrazione]").blur(function(){validazioneNome()});


	function validazioneNome(){

		var patternNome= /^[a-zA-Z0-9 ]{1,30}$/;/*Cerca dall'inizio alla fine se hai inserito nome di almeno 1 lettera ,
		indicato nel primo parametro della parentesi graffa e massimo 30 lettere nel secondo parametro della parentesi graffa*/

		if($("input[name=nomeRegistrazione]").val().match(patternNome))
		{
			$("input[name=nomeRegistrazione]").css({"border-color": "#ccc"});
			$("#labelNome").css({"color": "#ffd700"});
			$("#labelNomeErrato").css({"display": "none"});
			return true;
		}
		else
		{
			$("input[name=nomeRegistrazione]").css({"border-color": "red"});
			$("#labelNome").css({"color": "red"});
			$("#labelNomeErrato").css({"display": "block"});
			return false;
		}
	}

	$("#textAreaDescrizione").blur(function(){validazioneDescrizione()});


	function validazioneDescrizione(){

		if($("#textAreaDescrizione").val().length < 2000)
		{
			$("#textAreaDescrizione").css({"border-color": "#ccc"});
			$("#labelDescrizione").css({"color": "#ffd700"});
			$("#labelDescrizioneErrata").css({"display": "none"});
			return true;
		}
		else
		{
			$("#textAreaDescrizione").css({"border-color": "red"});
			$("#labelDescrizione").css({"color": "red"});
			$("#labelDescrizioneErrata").css({"display": "block"});
			return false;
		}
	}

	$("select[name=categoriaOggetto]").blur(function(){validazioneSelectCategoria()});

	function validazioneSelectCategoria(){

		/*funzione trim toglie gli spazi a destra e a sinistra della stringa , to lowercase rende
		  la stringa minuscola*/
		if($("select[name=categoriaOggetto]").val().trim().toLowerCase() == "categoria")
		{
			$("select[name=categoriaOggetto]").css({"border-color": "red"});
			$("#labelCategoria").css({"color": "red"});
			$("#labelCategoriaErrata").css({"display": "block"});

			return false;
		}
		else
		{
			$("select[name=categoriaOggetto]").css({"border-color": "#ccc"});
			$("#labelCategoria").css({"color": "#ffd700"});
			$("#labelCategoriaErrata").css({"display": "none"});

			return true;
		}
	}


	$("select[name=regioneOggetto]").blur(function(){validazioneSelectRegione()});

	function validazioneSelectRegione(){

		/*funzione trim toglie gli spazi a destra e a sinistra della stringa , to lowercase rende
		  la stringa minuscola*/
		if($("select[name=regioneOggetto]").val().trim().toLowerCase() == "regione")
		{
			$("select[name=regioneOggetto]").css({"border-color": "red"});
			$("#labelRegione").css({"color": "red"});
			$("#labelRegioneErrata").css({"display": "block"});

			return false;
		}
		else
		{
			$("select[name=regioneOggetto]").css({"border-color": "#ccc"});
			$("#labelRegione").css({"color": "#ffd700"});
			$("#labelRegioneErrata").css({"display": "none"});

			return true;
		}
	}


	function validazionePrezzo(){

		var prezzo = $("input[name=prezzoOggetto]").val();

		if(prezzo == "")
		{
			$("input[name=prezzoOggetto]").css({"border-color": "red"});
			$("#labelPrezzo").css({"color": "red"});
			$("#labelPrezzoErrato").css({"display": "block"});

			return false;
		}

		prezzo = parseInt(prezzo);

		if(prezzo < 0 || prezzo > 100000)
		{

			$("input[name=prezzoOggetto]").css({"border-color": "red"});
			$("#labelPrezzo").css({"color": "red"});
			$("#labelPrezzoErrato").css({"display": "block"});

			return false;
		}
		else
		{

			$("input[name=prezzoOggetto]").css({"border-color": "#ccc"});
			$("#labelPrezzo").css({"color": "#ffd700"});
			$("#labelPrezzoErrato").css({"display": "none"});

			return true;
		}
	}


	function validazioneImmagine(){

		var url = $("input[name=urlImmagineOggetto]").val();

		if(url == '')
		{
			$("input[name=urlImmagineOggetto]").css({"border-color": "red"});
			$("#scegliFile").css({"color": "red"});
			$("#labelUrlErrato").css({"display": "block"});

			return false;
		}

		if(!(url.substring(url.length-4) == '.jpg'))
		{

			$("input[name=urlImmagineOggetto]").css({"border-color": "red"});
			$("#scegliFile").css({"color": "red"});
			$("#labelUrlErrato").css({"display": "block"});

			return false;
		}
		else
		{

			$("input[name=urlImmagineOggetto]").css({"border-color": "#ccc"});
			$("#scegliFile").css({"color": "#ffd700"});
			$("#labelUrlErrato").css({"display": "none"});

			return true;
		}

	}

});




