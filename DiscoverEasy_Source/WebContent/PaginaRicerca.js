$(document).ready(function(){


	$("div [name=bottoneAggiungiPreferitiScript]").click(function(){

		var tagTr = $(this).parents("tr[data-th=rigaOggetto]");
		
		/*var prezzo = tagTr.find("h4[class=prezzoProdotto]").text();
		
		alert("prezzo"+prezzo);*/
		
		/*var urlImmagine = tagTr.find("img").attr("src");
		
		alert("urlImmagine"+urlImmagine);*/
		
		var nome = tagTr.find("strong[data-th=nome]").text();
		
		//alert("nome"+nome);
		
		/*var descrizione = tagTr.find("div[class=descrizioneProdotto]").text();

		alert("descrizione"+descrizione);
		
		var regione = tagTr.find("div[data-th=regione]").text();

		alert("regione"+regione);*/

		var email = tagTr.find("span[data-th=email]").text();
		
		//alert("email"+email);

	  /*var numero = tagTr.find("span[data-th=numero]").text();

		alert("numero"+numero);*/

		var anno = tagTr.find("span[data-th=anno]").text();

//		alert("anno"+anno);

		var mese = tagTr.find("span[data-th=mese]").text();

//		alert("mese"+mese);

		var giorno = tagTr.find("span[data-th=giorno]").text();

//		alert("giorno"+giorno);

		var ora = tagTr.find("span[data-th=ora]").text();

//		alert("ora"+ora);

		var minuti = tagTr.find("span[data-th=minuti]").text();

//		alert("minuti"+minuti);

		var secondi = tagTr.find("span[data-th=secondi]").text();

//		alert("secondi"+secondi);

		$.post("AggiungiPreferitiRicerca",

				{
					nomeP : nome,
					
					//descrizioneP : descrizione,
					
					//regioneP : regione,
					
					emailP : email,
					
					//numeroP : numero,
					
					//prezzoP : prezzo,
					
					//urlImmagineP : urlImmagine,
					
					anno : anno,
					
					mese : mese,

					giorno : giorno,

					ora : ora,

					minuti : minuti,

					secondi : secondi
				},
				
				function(data, status){

					
					/*Quando viene aggiunto preferito la pagina viene ricaricata e viene richiamato lo script caricaPreferiti.js
					  in modo che vengono cancellati i preferiti vecchi html aggiunti precedentemente con lo script caricaPreferiti.js
					  e quindi funziona anche lo script bottoneEliminaOggettoPreferiti*/
					
					window.location.reload(); 
					
				});
	});

});