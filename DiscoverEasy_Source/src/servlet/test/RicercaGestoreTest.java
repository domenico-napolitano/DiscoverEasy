package servlet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import interfaccia.gestore.RicercaGestore;
import manager.utente.ManagerUtente;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.utenteDAO;

public class RicercaGestoreTest {

	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static utenteDAO utenteDAO = new utenteDAO();
	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static RicercaGestore ricerca = new RicercaGestore();
	private static utenteBean utente = new utenteBean();
	private static utenteBean amministratore = new utenteBean();
	private OggettoBean annuncio = new OggettoBean();
	private static OggettoDAO annuncioDAO = new OggettoDAO();
	
	@Before
	public void start() {
		utente.setEmail("test@gmail.com");
		utente.setPass("testtest");
		utente.setNome("test");
		utente.setAnno_nascita("1990");
		utente.setEliminato(false);
		utente.setRegione("Campania");
		utente.setSesso("Maschio");
		utente.setTelefono(new Long("3333333333"));
		utente.setEmail_ban(null);
		utente.setAmministratore(0);
		
		amministratore.setEmail("amministratore@gmail.com");
		amministratore.setPass("testtest");
		amministratore.setNome("test");
		amministratore.setAnno_nascita("1990");
		amministratore.setEliminato(false);
		amministratore.setRegione("Campania");
		amministratore.setSesso("Maschio");
		amministratore.setTelefono(new Long("3333333333"));
		amministratore.setEmail_ban(null);
		amministratore.setAmministratore(1);
		
		annuncio.setCategoria("Veicoli");
		annuncio.setDescrizione("test_test_test");
		annuncio.setEliminato(false);
		annuncio.setRegione("Campania");
		annuncio.setEmail("test@gmail.com");
		annuncio.setImmagine("Img\\Prodotti\\url_di_test.jpg");
		annuncio.setNome("test");
		annuncio.setPrezzo(300);
		annuncio.setNumero_proprietario("3333333333");
		annuncio.setNome_proprietario("test");

		try {
			managerUtente.registra("test@gmail.com", "testtest","test", "Maschio", "Campania", "3333333333", "1990", "on");
			utenteDAO.doSave(amministratore);
			annuncioDAO.doSave(annuncio);
		} catch (IllegalArgumentException | SQLException  e) {
			e.printStackTrace();
			fail("Test fallito, impossibile caricare utente");
		}
	}


	@Test
	public void ricercaGestoreTest() {


		try {

			Mockito.when(session.getAttribute("email")).thenReturn("amministratore@gmail.com");
			Mockito.when(session.getAttribute("accesso")).thenReturn(true);
			Mockito.when(session.getAttribute("amministratore")).thenReturn(1);
			Mockito.when(request.getSession(true)).thenReturn(session);
			Mockito.when(request.getParameter("barraRicerca")).thenReturn("test@gmail.com");
			ricerca.doPost(request, response);
			
		assertEquals(annuncio, ricerca.getRisultatiRicerca().get(0));
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			fail("Test fallito");
		}

	}	

	


	@After 
	public void clean() {
		try {
			utenteDAO.removeByKey("test@gmail.com");
			utenteDAO.removeByKey("amministratore@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
