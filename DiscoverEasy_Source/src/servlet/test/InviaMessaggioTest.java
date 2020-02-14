package servlet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import interfaccia.chat.InviaMessaggio;
import interfaccia.gestore.*;
import manager.chat.ManagerChat;
import manager.negozio.ManagerNegozio;
import manager.utente.ManagerUtente;
import model.beans.*;
import model.dao.*;

public class InviaMessaggioTest {
	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

	private static ManagerUtente managerUtente = new ManagerUtente();
	private static ManagerNegozio managerNegozio = new ManagerNegozio();
	private static ManagerChat managerChat = new ManagerChat();

	private static utenteDAO utenteDAO = new utenteDAO();
	private static OggettoDAO oggettoDAO = new OggettoDAO();

	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static InviaMessaggio inviaMessaggio = new InviaMessaggio();

	private static utenteBean utente = new utenteBean();
	private static utenteBean utenteDue = new utenteBean();
	private static OggettoBean oggetto = new OggettoBean();

	@Before
	public void start() {
		utente.setEmail("test@gmail.com");
		utente.setPass("testtest");
		utente.setNome("test");
		utente.setAnno_nascita("1990");
		utente.setRegione("Campania");
		utente.setSesso("Maschio");
		utente.setTelefono(new Long("3333333333"));

		utenteDue.setEmail("testDue@gmail.com");
		utenteDue.setPass("testtest");
		utenteDue.setNome("test");
		utenteDue.setAnno_nascita("1990");
		utenteDue.setRegione("Campania");
		utenteDue.setSesso("Maschio");
		utenteDue.setTelefono(new Long("3333333333"));

		oggetto.setNome("test");
		oggetto.setCategoria("Veicoli");
		oggetto.setDescrizione("test_test_test");
		oggetto.setEmail("test@gmail.com");
		oggetto.setImmagine("Img\\Prodotti\\url_di_test.jpg");
		oggetto.setNumero_proprietario("3333333333");
		oggetto.setEliminato(false);
		oggetto.setNome_proprietario("test");
		oggetto.setPrezzo(300);
		oggetto.setRegione("Campania");

		try {
			managerUtente.registra("test@gmail.com", "testtest","test", "Maschio", "Campania", "3333333333", "1990", "on");

			managerUtente.registra("testDue@gmail.com", "testtest","test", "Maschio", "Campania", "3333333333", "1990", "on");

			managerNegozio.salvaAnnuncio(oggetto.getEmail(), oggetto.getNome(), oggetto.getCategoria(), oggetto.getRegione(), oggetto.getDescrizione(), ""+oggetto.getPrezzo(), oggetto.getImmagine());

			oggetto = oggettoDAO.doSearchByEmail(utente.getEmail()).get(0);

			GregorianCalendar oraAnnuncio = (GregorianCalendar)oggetto.getDataOra().clone();

			oraAnnuncio.set(GregorianCalendar.MONTH, oraAnnuncio.get(GregorianCalendar.MONTH)+1);

			managerChat.iniziaNuovaChat(utenteDue.getEmail(), utente.getEmail(),
					oggetto.getNome(), oraAnnuncio);

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile caricare utente");
		}


	}


	@Test
	public void inviaMessaggioTest() {




		Mockito.when(request.getSession(true)).thenReturn(session);
		Mockito.when(session.getAttribute("accesso")).thenReturn(true);		
		Mockito.when(session.getAttribute("email")).thenReturn(utenteDue.getEmail());
		Mockito.when(request.getParameter("emailMittenteChat")).thenReturn(utenteDue.getEmail());
		Mockito.when(request.getParameter("emailDestinatarioChat")).thenReturn(utente.getEmail());
		Mockito.when(request.getParameter("descrizione")).thenReturn("ciao");
		Mockito.when(request.getParameter("nomeAnnuncio")).thenReturn(oggetto.getNome());
		Mockito.when(request.getParameter("annoAnnuncio")).thenReturn(oggetto.getDataOra().get(GregorianCalendar.YEAR)+"");
		Mockito.when(request.getParameter("meseAnnuncio")).thenReturn((oggetto.getDataOra().get(GregorianCalendar.MONTH)+1)+"");
		Mockito.when(request.getParameter("giornoAnnuncio")).thenReturn(oggetto.getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+"");
		Mockito.when(request.getParameter("oraAnnuncio")).thenReturn(oggetto.getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+"");
		Mockito.when(request.getParameter("minutiAnnuncio")).thenReturn(oggetto.getDataOra().get(GregorianCalendar.MINUTE)+"");
		Mockito.when(request.getParameter("secondiAnnuncio")).thenReturn(oggetto.getDataOra().get(GregorianCalendar.SECOND)+"");

		try {
			
			GregorianCalendar oraAnnuncio = (GregorianCalendar)oggetto.getDataOra().clone();

			oraAnnuncio.set(GregorianCalendar.MONTH, oraAnnuncio.get(GregorianCalendar.MONTH)+1);

			inviaMessaggio.doPost(request, response);
			
			try {
				ArrayList<MessaggioBean> messaggi = managerChat.caricaMessaggi(utenteDue.getEmail(), utenteDue.getEmail(), utente.getEmail(), oggetto.getNome(), oggetto.getDataOra());
			
				assertEquals(messaggi.size(), 1);
				
				MessaggioBean messaggio = messaggi.get(0);
				
				assertEquals(messaggio.getNomeAnnuncioChat(), oggetto.getNome());
				
				assertEquals(messaggio.getDescrizione(), "ciao");
				
				assertEquals(messaggio.getEmailDestinatarioChat(), utente.getEmail());

				assertEquals(messaggio.getEmailMittenteChat(), utenteDue.getEmail());

				assertEquals(messaggio.getDataOraAnnuncio(), messaggio.getDataOraAnnuncio());

				assertEquals(messaggio.getEmailMittenteMessaggio(), utenteDue.getEmail());
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	




	@After 
	public void clean() {
		try {
			utenteDAO.removeByKey("test@gmail.com");
			utenteDAO.removeByKey(utenteDue.getEmail());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
