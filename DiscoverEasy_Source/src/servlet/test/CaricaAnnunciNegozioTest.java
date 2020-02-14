package servlet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import interfaccia.negozio.CaricaAnnunciNegozio;
import manager.negozio.ManagerNegozio;
import manager.ricerca.ManagerRicerca;
import manager.utente.ManagerUtente;
import model.beans.utenteBean;
import model.dao.utenteDAO;

public class CaricaAnnunciNegozioTest {
	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static ManagerRicerca managerRicerca = new ManagerRicerca();
	private static utenteDAO utenteDAO = new utenteDAO();
	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static CaricaAnnunciNegozio caricaAnnunci = new CaricaAnnunciNegozio();
	private static utenteBean utente = new utenteBean();
	private static ManagerNegozio managerNegozio = new ManagerNegozio();
	private static PrintWriter print;
	
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
		

		try {
			managerUtente.registra("test@gmail.com", "testtest","test", "Maschio", "Campania", "3333333333", "1990", "on");
			managerNegozio.salvaAnnuncio("test@gmail.com", "test", "Veicoli", "Campania", "test_test_test", "300", "Img\\Prodotti\\url_di_test.jpg");
			print = new PrintWriter("tmp.txt");
		} catch (IllegalArgumentException | SQLException | FileNotFoundException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile caricare utente");
		}
	}


	@Test
	public void rimuoviAnnunciPreferitiTest() {

		

		try {
			Mockito.when(session.getAttribute("email")).thenReturn("test@gmail.com");
			Mockito.when(session.getAttribute("accesso")).thenReturn(true);		
			Mockito.when(response.getWriter()).thenReturn(print);
			Mockito.when(request.getSession(true)).thenReturn(session);
			caricaAnnunci.doPost(request, response);
			assertEquals(caricaAnnunci.getListaOggetti(), managerRicerca.caricaAnnunci("test@gmail.com"));
			
		} catch (ServletException | IOException | SQLException e) {
			e.printStackTrace();
			fail("Test fallito");
		}
		
	}	

	


	@After 
	public void clean() {
		try {
			utenteDAO.removeByKey("test@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
