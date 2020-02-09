$(document).ready(function(){


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
						window.location.href = "home.html";
					}

				}); /*parentesi di $.post*/

	});

	/*Fine modal accedi*/

});