$(document).ready(function(){


	setTimeout(function(){$(".bottoneEliminaOggettoPreferiti").click(function(){/*Quando clicchi il pulsante x*/


		var rigaTr = $(this).parents("tr");

		/*parent fa riferimento al padre del pulsante x che è il div oggetto preferiti e remove , rimuove l'oggetto preferiti
	      con tutti i suoi figli quindi anche il pulsante x*/

		var nome =  rigaTr.find("strong[data-th=nome]").text();

	//	alert("nome"+nome);
		
		var email =  rigaTr.find("span[data-th=email]").text();

//		alert("email"+email);
		
		var anno = rigaTr.find("span[data-th=anno]").text();

	//	alert("anno"+anno);

		var mese = rigaTr.find("span[data-th=mese]").text();

	//	alert("mese"+mese);

		var giorno = rigaTr.find("span[data-th=giorno]").text();

	//	alert("giorno"+giorno);

		var ora = rigaTr.find("span[data-th=ora]").text();

	//	alert("ora"+ora);

		var minuti = rigaTr.find("span[data-th=minuti]").text();

	//	alert("minuti"+minuti);

		var secondi = rigaTr.find("span[data-th=secondi]").text();

	//	alert("secondi"+secondi);

		$.post("RimuoviAnnuncioPreferiti",
				{
			nome: nome,
			email: email,
			anno: anno,
			mese: mese,
			giorno: giorno,
			ora: ora,
			minuti: minuti,
			secondi: secondi
				},

				function(data,status)
				{
					rigaTr.remove();
					
					var check = JSON.parse(data);
										
					if(check.vuoto == true){

						$("#tbodyContenutoPreferiti").html('<td id="listaPreferitiVuota"> Aggiungi un annuncio ai preferiti per visualizzarlo qui </td>');
						
						$("#cestinoPreferiti").hide();
					}
					
				}
		);

	});}, 1000);


	$("#cestinoPreferiti").click(function(){/*Quando clicchi il pulsante cestino*/


		$(this).hide();/*si nasconde il pulsante*/
	
		//$("#bodyPreferiti").empty();/*Elimina i figli cioè gli oggetti del div body quindi tutti i preferiti contenuti nel div body*/
		
		$("#tbodyContenutoPreferiti").html('<td id="listaPreferitiVuota"> Aggiungi un annuncio ai preferiti per visualizzarlo qui </td>');

		/*rimuovi da sessione oggetti*/

		$.post("CestinaAnnunciPreferiti",
				{

				},
				function(data,status){
					
				});
	});



});