package servlet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import interfaccia.gestore.EliminaAnnuncioRicercaGestore;
import manager.ricerca.ManagerRicerca;
import manager.utente.ManagerUtente;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.utenteDAO;

public class EliminaAnnuncioRicercaGestoreTest {
	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static utenteDAO utenteDAO = new utenteDAO();
	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static EliminaAnnuncioRicercaGestore eliminaAnnuncioGestore = new EliminaAnnuncioRicercaGestore();
	private static utenteBean utente = new utenteBean();
	private static utenteBean amministratore = new utenteBean();
	private static OggettoBean oggetto = new OggettoBean();
	private static OggettoDAO oggettoDAO = new OggettoDAO();
	private static ManagerRicerca managerRicerca = new ManagerRicerca();
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
		utenteDAO.doSave(amministratore);
		oggetto = oggettoDAO.doSave(oggetto);
		print = new PrintWriter("tmp.txt");
	} catch (IllegalArgumentException | SQLException | FileNotFoundException e) {
		e.printStackTrace();
		fail("Test fallito, impossibile caricare utente");
	}
}
	

@Test
public void eliminaAnnuncioNegozio() {

try {
	Mockito.when(session.getAttribute("email")).thenReturn("amministratore@gmail.com");
	Mockito.when(session.getAttribute("accesso")).thenReturn(true);
	Mockito.when(session.getAttribute("amministratore")).thenReturn(1);
	Mockito.when(response.getWriter()).thenReturn(print);
	Mockito.when(request.getParameter("emailOggettoEliminato")).thenReturn("test@gmail.com");
	Mockito.when(request.getParameter("nomeOggettoEliminato")).thenReturn("test");
	Mockito.when(request.getParameter("annoOggettoEliminato")).thenReturn("" +oggetto.getDataOra().get(GregorianCalendar.YEAR));
	Mockito.when(request.getParameter("meseOggettoEliminato")).thenReturn("" +(oggetto.getDataOra().get(GregorianCalendar.MONTH)+1));
	Mockito.when(request.getParameter("giornoOggettoEliminato")).thenReturn("" +oggetto.getDataOra().get(GregorianCalendar.DAY_OF_MONTH));
	Mockito.when(request.getParameter("oraOggettoEliminato")).thenReturn("" +oggetto.getDataOra().get(GregorianCalendar.HOUR_OF_DAY));
	Mockito.when(request.getParameter("minutiOggettoEliminato")).thenReturn("" +oggetto.getDataOra().get(GregorianCalendar.MINUTE));
	Mockito.when(request.getParameter("secondiOggettoEliminato")).thenReturn("" +oggetto.getDataOra().get(GregorianCalendar.SECOND));
	Mockito.when(request.getSession(true)).thenReturn(session);
	
	assertEquals(managerRicerca.caricaAnnunci("test@gmail.com").size(), 1);
	eliminaAnnuncioGestore.doPost(request, response);
	assertEquals(managerRicerca.caricaAnnunci("test@gmail.com").size(), 0);
}
catch (ServletException | IOException | SQLException e) {
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
