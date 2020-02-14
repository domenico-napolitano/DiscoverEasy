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

import interfaccia.negozio.AggiungiAnnuncioNegozio;
import manager.ricerca.ManagerRicerca;
import manager.utente.ManagerUtente;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.utenteDAO;

public class AggiungiAnnuncioNegozioTest {
	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static ManagerRicerca managerRicerca = new ManagerRicerca();
	private static utenteDAO utenteDAO = new utenteDAO();
	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static AggiungiAnnuncioNegozio aggiungiAnnuncio = new AggiungiAnnuncioNegozio();
	private static utenteBean utente = new utenteBean();
	private static OggettoBean oggetto = new OggettoBean();
	
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
			
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile caricare utente");
		}
	}


	@Test
	public void aggiungiAnnuncioTest() {

		

		try {			
			Mockito.when(session.getAttribute("accesso")).thenReturn(true);		
			Mockito.when(session.getAttribute("email")).thenReturn("test@gmail.com");
			Mockito.when(request.getSession(true)).thenReturn(session);
			Mockito.when(request.getParameter("nomeRegistrazione")).thenReturn("test");
			Mockito.when(request.getParameter("categoriaOggetto")).thenReturn("Veicoli");
			Mockito.when(request.getParameter("regioneOggetto")).thenReturn("Campania");
			Mockito.when(request.getParameter("descrizioneOggetto")).thenReturn("test_test_test");
			Mockito.when(request.getParameter("prezzoOggetto")).thenReturn("300");
			Mockito.when(request.getParameter("urlImmagineOggetto")).thenReturn("Img\\Prodotti\\url_di_test.jpg");
			
			aggiungiAnnuncio.doPost(request, response);
			assertEquals(managerRicerca.caricaAnnunci("test@gmail.com").get(0), oggetto);
			
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
