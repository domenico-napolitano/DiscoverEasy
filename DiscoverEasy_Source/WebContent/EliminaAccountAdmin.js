$(document).ready(function(){

	$("#bottoneBanna").click(function() {

		var form = $(this).parents("form[data-th=formBarraRicercaGestione]");

		var emailBarraRicerca = form.find("input[data-th=inputBarraRicerca]").val();
		
		$.post("EliminaAccountRicercaGestore",
				{
			emailAccountBarraRicerca : emailBarraRicerca
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

		return false;
	});

});


