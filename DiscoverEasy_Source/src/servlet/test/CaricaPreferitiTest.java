package servlet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import interfaccia.ricerca.CaricaPreferiti;
import manager.negozio.ManagerNegozio;
import manager.ricerca.ManagerRicerca;
import manager.utente.ManagerUtente;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.utenteDAO;

public class CaricaPreferitiTest {
	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static ManagerRicerca managerRicerca = new ManagerRicerca();
	private static utenteDAO utenteDAO = new utenteDAO();
	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static CaricaPreferiti caricaPreferiti = new CaricaPreferiti();
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
			managerRicerca.salvaAnnuncioAiPreferiti("test@gmail.com", managerRicerca.caricaAnnunci("test@gmail.com").get(0));
			print = new PrintWriter("tmp.txt");
			
		} catch (IllegalArgumentException | SQLException | FileNotFoundException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile caricare utente");
		}
	}


	@Test
	public void caricaPreferitiTest() {

		Hashtable<String, OggettoBean> lista = new Hashtable<String, OggettoBean>();
		Hashtable<String, OggettoBean> listaExpected = new Hashtable<String, OggettoBean>();
		try {
			listaExpected.put(managerRicerca.caricaAnnunci("test@gmail.com").get(0).getNome()+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getEmail()+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.YEAR)+
					managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MONTH)+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
					managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MINUTE)+
					managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.SECOND), managerRicerca.caricaAnnunci("test@gmail.com").get(0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		try {
			System.out.println(lista.size());
			Mockito.when(session.getAttribute("email")).thenReturn("test@gmail.com");
			Mockito.when(session.getAttribute("accesso")).thenReturn(true);
			Mockito.when(request.getSession(true)).thenReturn(session);
			Mockito.when(request.getParameter("email")).thenReturn("test@gmail.com");
			Mockito.when(request.getParameter("nome")).thenReturn("test");
			Mockito.when(response.getWriter()).thenReturn(print);
			Mockito.when(session.getAttribute("listaOggettiPreferiti")).thenReturn(lista);
			caricaPreferiti.doPost(request, response);
			
			assertEquals(listaExpected , caricaPreferiti.getListaOggettiPreferiti());
		} catch (ServletException | IOException e) {
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
