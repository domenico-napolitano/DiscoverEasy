$(document).ready(function(){

	
	$.post("CaricaMessaggi",
			{
				variabileSentinella: 'nessunValore'
			},

			function(data,status)
			{
				//alert("controllo verificato messaggi casella");
				
				var verificaRicezioneMessaggi = JSON.parse(data);

				if(verificaRicezioneMessaggi.nuoviMessaggi == true){
					
					$("#bottoneChat").css({"color": "red"});
					$(".glyphicon-comment").css({"color": "red"});
				}
				else
				{
					$("#bottoneChat").css({"color": "#ffd700"});
					$(".glyphicon-comment").css({"color": "#ffd700"});
				}
				
			});
});