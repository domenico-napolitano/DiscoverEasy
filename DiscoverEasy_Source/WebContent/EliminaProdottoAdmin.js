$(document).ready(function(){


	$(".bottoneEliminaOggetto").click(function() {

		var tr = $(this).parents("tr[data-th=rigaOggetto]");

		var nome = tr.find("strong[data-th=nome]").text();

		var email = tr.find("span[data-th=email]").text();

		var anno = tr.find("span[data-th=anno]").text();

		var mese = tr.find("span[data-th=mese]").text();

		var giorno = tr.find("span[data-th=giorno]").text();

		var ora = tr.find("span[data-th=ora]").text();

		var minuti = tr.find("span[data-th=minuti]").text();

		var secondi = tr.find("span[data-th=secondi]").text();


		$.post("EliminaAnnuncioRicercaGestore",
				{
			nomeOggettoEliminato : nome,
			emailOggettoEliminato : email,
			annoOggettoEliminato : anno,
			meseOggettoEliminato : mese,
			giornoOggettoEliminato : giorno,
			oraOggettoEliminato : ora,
			minutiOggettoEliminato : minuti,
			secondiOggettoEliminato : secondi
				} ,

				function(data, status){

					var oggettoRisposta = JSON.parse(data);

					if(oggettoRisposta.status == 400){

						window.location.href = oggettoRisposta.redirect;
					}
					else
					{
						window.location.reload();
					}			
				});
	});


});