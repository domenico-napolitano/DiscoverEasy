package interfaccia.account;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.utente.ManagerUtente;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import model.beans.*;
import model.dao.*;

@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private static Logger log;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registrazione() {
		super();
		// TODO Auto-generated constructor stub

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		response.setContentType("text/html");

		String emailCliente = request.getParameter("emailRegistrazione"); //prende l'email dalla textfield

		/*Per vedere se la richiesta è fatta da AJAX con jquery allora l'header della request deve avere un parametro 
		 x-requested-with con valore XMLHttpRequest*/

		String headerRequest = request.getHeader("x-requested-with");

		if(headerRequest != null && headerRequest.equalsIgnoreCase("XMLHttpRequest"))
		{
			PrintWriter out = response.getWriter();

			log.logp(Level.INFO, "Registrazione", "doGet", "controllo email ajax");

			/*Se email esiste ritorna un JSON true, perchè utente ha inserito un email esistente*/
			if(new ManagerUtente().checkExistent(emailCliente) != null)
			{
				out.println("{\"emailEsistente\": true}");
				log.logp(Level.INFO, "Registrazione", "doGet", "controllo email ajax,"
						+ " email esistente, invio true");

			}
			else
			{
				out.println("{\"emailEsistente\": false}");

				log.logp(Level.INFO, "Registrazione", "doGet", "controllo email ajax,"
						+ " email non esistente, invio false");

			}

			return;
		}

		String password = request.getParameter("passwordRegistrazione"); //prende la password dalla textfield

		String regione = request.getParameter("regioneRegistrazione");

		String anno_nascita = request.getParameter("anno_nascitaRegistrazione");

		String checkbox = request.getParameter("checkboxRegistrazione");

		String sesso = request.getParameter("sessoRegistrazione");

		String nome = request.getParameter("nomeRegistrazione");

		String telefono = request.getParameter("telefonoRegistrazione");


		ManagerUtente manager = new ManagerUtente();

		try {

			utenteBean utente = manager.registra(emailCliente, password, nome, sesso, regione, 
					telefono, anno_nascita, checkbox);

			request.setAttribute("datiAnagrafici", utente);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/RegisterSucces.jsp");
			
			System.out.println("Stampa"+dispatcher+"Stampa dispatcher");
			if(dispatcher != null)
			{	
				dispatcher.forward(request, response);
			}
		}
		catch(SQLException e) {

			e.printStackTrace();

			sendError(response);

			return;
		}
		catch(IllegalArgumentException e1) {

			e1.printStackTrace();

			sendError(response);

			return;
		}

	}

	private void sendError(HttpServletResponse response) throws IOException {

		response.setStatus(400);

		response.sendRedirect("Page404.html");
	}

}

