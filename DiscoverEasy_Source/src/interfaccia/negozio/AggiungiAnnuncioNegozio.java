package interfaccia.negozio;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.negozio.ManagerNegozio;

import java.util.regex.Pattern;

import model.beans.*;
import model.dao.*;

@WebServlet("/AggiungiAnnuncioNegozio")
public class AggiungiAnnuncioNegozio extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AggiungiAnnuncioNegozio() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sessione = request.getSession(true);
		
		String email = "";
		
		synchronized(sessione) {
			Object accesso = sessione.getAttribute("accesso");
			if(accesso==null || (boolean) accesso == false) {
				response.sendRedirect("home.html");
				return;
			}
		 
			email = (String) sessione.getAttribute("email");
		}
		
		//controllo dati
	
	
		System.out.println(email);
		String nome = request.getParameter("nomeRegistrazione");
		System.out.println(nome);
		String categoria = request.getParameter("categoriaOggetto");
		System.out.println(categoria);
		String regione = request.getParameter("regioneOggetto");
		System.out.println(regione);
		String descrizione = request.getParameter("descrizioneOggetto");
		System.out.println(descrizione);
		String prezzo = request.getParameter("prezzoOggetto");
		System.out.println(prezzo);
		String urlImmagine = request.getParameter("urlImmagineOggetto");
		System.out.println(urlImmagine);

		try {

			new ManagerNegozio().salvaAnnuncio(email, nome, categoria, regione, descrizione, prezzo, urlImmagine);

		}catch(IllegalArgumentException e2) {

			e2.printStackTrace();

			sendError(response);
			
			return;

		}catch(RuntimeException e1) {
			
			e1.printStackTrace();

			sendError(response);
			
			return;

		}catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

			sendError(response);
			
			return;
		}


		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/oggettoAggiunto.jsp");
		if(dispatcher != null)
		{
			dispatcher.forward(request, response);
		}

	}


	private void sendError(HttpServletResponse response) throws IOException {
		response.setStatus(400);
		response.sendRedirect("Page404.html");
	}

}

