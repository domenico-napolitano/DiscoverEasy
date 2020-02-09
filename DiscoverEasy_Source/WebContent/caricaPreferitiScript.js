$(document).ready(function(){

	/*Carica i preferiti associati alla sessione dell'utente e all'account dell'utente se l'utente non ha inserito preferiti
	   nella lista preferiti allora sara visualizzata vuota*/

	caricaPreferiti();

	function caricaPreferiti(){

		$.post("CaricaPreferiti",
				{
				},

				function(data,status){

					//alert(data+"prova");
					var listaOggettiPreferiti = JSON.parse(data);
					//alert(listaOggettiPreferiti);
					
					var annuncio;
					
					if(listaOggettiPreferiti.length == 0){

						$("#tbodyContenutoPreferiti").html('<td id="listaPreferitiVuota"> Aggiungi un annuncio ai preferiti per visualizzarlo qui </td>');
						
						$("#cestinoPreferiti").hide();
					}
					
					
					for(var i=0; i < listaOggettiPreferiti.length; i++)
					{
						annuncio = listaOggettiPreferiti[i];
						
						$("#tbodyContenutoPreferiti").append(

								'<tr>'+
								'<td data-th="Prodotti">'+
								'<div class="row">'+
								'<div class="col-xs-12">'+
								'<img src="'+annuncio.immagine+'" id="immagineOggetto">'+

								'</div>'+
								'</div>'+
								'</td>'+

								'<td data-th="Costo">'+
								'<div>'+
								'<h4>'+
								'<strong data-th="nome">'+annuncio.nome+'</strong>'+
								'</h4>'+
								'</div>'+
								'<div class="descrizioneProdotto">'+
								annuncio.descrizione+'</div>'+
								'<div></div>'+
								'<div>'+
								'<h4 class="prezzoProdotto">'+annuncio.prezzo+'€</h4>'+
								'</div>'+
								'<div>'+annuncio.regione+'</div>'+
								'<div>'+
								'<hr>'+
								'<span class="glyphicon glyphicon-envelope contatti" data-th="email">'+
								annuncio.email_venditore+'</span> &emsp;&emsp;&emsp;'+ 
								'<span class="glyphicon glyphicon-earphone contatti">'+'&nbsp;'+
								annuncio.telefono+'</span>'+
								'</div>'+
								
								'<div id="divDataOra">'+
								'<span>Pubblicato il:</span>'+
								
								'<span data-th="anno">'+annuncio.anno+'</span><span>-</span>'+
								'<span data-th="mese">'+annuncio.mese+'</span><span>-</span>'+
								'<span data-th="giorno">'+annuncio.giorno+'</span>&emsp;'+
								'<span data-th="ora">'+annuncio.ora+'</span><span>:</span>'+
								'<span data-th="minuti">'+annuncio.minuti+'</span><span>:</span>'+
								'<span data-th="secondi">'+annuncio.secondi+'</span>'+

								'</div>'+

								'<div class="divBottoneEliminaOggettoPreferiti">'+
								'<button class="btn btn-sm bottoneEliminaOggettoPreferiti" style="margin-top: 18px" type="button">'+
								'<span style="font-size: 12px">Elimina dai preferiti</span>'+
								'</button>'+
								'</div>'+

								'</td>'+
								'</tr>'
						);


					}

				});
	}

});
