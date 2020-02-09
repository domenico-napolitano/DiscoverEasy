package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.ChatBean;

/**
 * Servlet implementation class CaricaChat
 */
@WebServlet("/CaricaChat")
public class CaricaChat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CaricaChat() {
		super();
		// TODO Auto-generated constructor stub

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sessione = request.getSession(true);

		String email="";

		synchronized(sessione) {

			Object accesso = sessione.getAttribute("accesso");

			if(accesso==null || (boolean) accesso == false) {
				response.sendRedirect("home.html");
				return;
			}

			email = (String) sessione.getAttribute("email");
		}

		try {
			
			log.logp(Level.INFO, "CaricaChat", "doGet", "caricamento Chat");
			
			listaChat = new ManagerChat().caricaChat(email);

			log.logp(Level.INFO, "CaricaChat", "doGet", "Chat caricata :" + listaChat);
			
			request.setAttribute("listaChat", listaChat);
			
			request.setAttribute("email", email);


			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/paginaChat.jsp");
			if(dispatcher != null)
			{
				dispatcher.forward(request, response);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			log.logp(Level.INFO, "CaricaChat", "doGet", "Chat non caricata, verificata eccezione");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private ArrayList<ChatBean> listaChat;
	
	public ArrayList<ChatBean> getChat(){
		
		return listaChat;
	}
}
