package interfaccia.chat;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.chat.ManagerChat;

/**
 * Servlet implementation class IniziaNuovaChat
 */
@WebServlet("/IniziaNuovaChat")
public class IniziaNuovaChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static Logger log;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IniziaNuovaChat() {
        super();
        // TODO Auto-generated constructor stub
        
        log = Logger.getGlobal();

		log.setLevel(Level.INFO);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		log.logp(Level.INFO, "IniziaNuovaChat", "doGet", "entra nella classe");
		
		HttpSession sessione = request.getSession(true);

		String emailUtente="";

		synchronized(sessione) {

			Object accesso = sessione.getAttribute("accesso");

			if(accesso==null || (boolean) accesso == false) {
				response.sendRedirect("home.html");
				return;
			}

			emailUtente = (String) sessione.getAttribute("email");
		}
		
		
		String nomeAnnuncio = request.getParameter("nome");
		String emailDestinatarioChat = request.getParameter("email");
		int annoAnnuncio = Integer.parseInt(request.getParameter("anno"));
		int meseAnnuncio = Integer.parseInt(request.getParameter("mese"));
		int giornoAnnuncio = Integer.parseInt(request.getParameter("giorno"));
		int oraAnnuncio = Integer.parseInt(request.getParameter("ora"));
		int minutiAnnuncio = Integer.parseInt(request.getParameter("minuti"));
		int secondiAnnuncio = Integer.parseInt(request.getParameter("secondi"));
		
		GregorianCalendar dataOraAnnuncio = new GregorianCalendar(annoAnnuncio, meseAnnuncio, giornoAnnuncio,
				oraAnnuncio, minutiAnnuncio, secondiAnnuncio);
		
		
		try {
			
			log.logp(Level.INFO, "IniziaNuovaChat", "doGet", "sta per iniziare una nuova chat");
			
			new ManagerChat().iniziaNuovaChat(emailUtente, emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio);
			
			log.logp(Level.INFO, "IniziaNuovaChat", "doGet", "nuova chat iniziata");

		} 
		catch(SQLIntegrityConstraintViolationException e1) {
			
			log.logp(Level.INFO, "IniziaNuovaChat", "doGet", "chat già presente");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
			log.logp(Level.INFO, "IniziaNuovaChat", "doGet", "chat non iniziata , sollevata eccezione");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("CaricaChat");
		if(dispatcher != null)
		{
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
