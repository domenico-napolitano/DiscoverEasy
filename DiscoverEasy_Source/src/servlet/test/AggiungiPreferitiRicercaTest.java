package servlet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
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

import interfaccia.ricerca.AggiungiPreferitiRicerca;
import manager.negozio.ManagerNegozio;
import manager.ricerca.ManagerRicerca;
import manager.utente.ManagerUtente;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.PreferitiDAO;
import model.dao.utenteDAO;

public class AggiungiPreferitiRicercaTest {

	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static ManagerRicerca managerRicerca = new ManagerRicerca();
	private static utenteDAO utenteDAO = new utenteDAO();
	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static AggiungiPreferitiRicerca aggiungiPreferiti = new AggiungiPreferitiRicerca();
	private static utenteBean utente = new utenteBean();
	private static ManagerNegozio managerNegozio = new ManagerNegozio();
	private static PreferitiDAO preferiti = new PreferitiDAO();
	private static OggettoDAO oggetto = new OggettoDAO();
	private static OggettoBean c = new OggettoBean();
	
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
	} catch (IllegalArgumentException | SQLException e) {
		e.printStackTrace();
		fail("Test fallito, impossibile caricare utente");
	}
}
	

@Test
public void aggiungiAnnuncioGi‡PresenteInListaPreferiti() {
	
Hashtable<String, OggettoBean> lista = new Hashtable<String, OggettoBean>();
try {
	lista.put(managerRicerca.caricaAnnunci("test@gmail.com").get(0).getNome()+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getEmail()+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.YEAR)+
			managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MONTH)+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
			managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MINUTE)+
			managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.SECOND), managerRicerca.caricaAnnunci("test@gmail.com").get(0));
} catch (SQLException e1) {
	e1.printStackTrace();
}


try {
	Mockito.when(session.getAttribute("email")).thenReturn("test@gmail.com");
	Mockito.when(session.getAttribute("accesso")).thenReturn(true);
	Mockito.when(request.getParameter("emailP")).thenReturn("test@gmail.com");
	Mockito.when(request.getParameter("nomeP")).thenReturn("test");
	Mockito.when(request.getParameter("anno")).thenReturn("" + managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.YEAR));
	Mockito.when(request.getParameter("mese")).thenReturn("" + managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MONTH)+1);
	Mockito.when(request.getParameter("giorno")).thenReturn("" + managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.DAY_OF_MONTH));
	Mockito.when(request.getParameter("ora")).thenReturn("" + managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.HOUR_OF_DAY));
	Mockito.when(request.getParameter("minuti")).thenReturn("" + managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MINUTE));
	Mockito.when(request.getParameter("secondi")).thenReturn("" + managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.SECOND));
	Mockito.when(request.getSession(true)).thenReturn(session);
	Mockito.when(session.getAttribute("listaOggettiPreferiti")).thenReturn(lista);
	aggiungiPreferiti.doPost(request, response);
	
	assertEquals(preferiti.doSearchByEmail("test@gmail.com").size(), 1);
	assertTrue(preferiti.doSearchByEmail("test@gmail.com").contains(managerRicerca.caricaAnnunci("test@gmail.com").get(0)));
} catch (ServletException | IOException | SQLException e) {
	e.printStackTrace();
	fail("Test fallito");
}

}	

@Test
public void aggiungiAnnuncioNonPresenteInListaPreferiti() {
	
Hashtable<String, OggettoBean> lista = new Hashtable<String, OggettoBean>();
try {
	c.setCategoria("Veicoli");
	c.setDescrizione("test_test_test");
	c.setEmail("test@gmail.com");
	c.setImmagine("Img\\Prodotti\\url_di_test.jpg");
	c.setPrezzo(300);
	c.setRegione("Campania");
	c.setNome("test1");
	c = oggetto.doSave(c);
	System.out.println("Chiave buona oggetto" + c.getEmail() + c.getNome() + c.getDataOra());
	lista.put(managerRicerca.caricaAnnunci("test@gmail.com").get(0).getNome()+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getEmail()+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.YEAR)+
			managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MONTH)+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
			managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.MINUTE)+
			managerRicerca.caricaAnnunci("test@gmail.com").get(0).getDataOra().get(GregorianCalendar.SECOND), managerRicerca.caricaAnnunci("test@gmail.com").get(0));
} catch (SQLException e1) {
	e1.printStackTrace();
}


try {
	Mockito.when(session.getAttribute("email")).thenReturn("test@gmail.com");
	Mockito.when(session.getAttribute("accesso")).thenReturn(true);
	Mockito.when(request.getParameter("emailP")).thenReturn("test@gmail.com");
	Mockito.when(request.getParameter("nomeP")).thenReturn("test1");
	Mockito.when(request.getParameter("anno")).thenReturn("" +c.getDataOra().get(GregorianCalendar.YEAR));
	Mockito.when(request.getParameter("mese")).thenReturn("" + (c.getDataOra().get(GregorianCalendar.MONTH)+1));
	Mockito.when(request.getParameter("giorno")).thenReturn("" + c.getDataOra().get(GregorianCalendar.DAY_OF_MONTH));
	Mockito.when(request.getParameter("ora")).thenReturn("" + c.getDataOra().get(GregorianCalendar.HOUR_OF_DAY));
	Mockito.when(request.getParameter("minuti")).thenReturn("" + c.getDataOra().get(GregorianCalendar.MINUTE));
	Mockito.when(request.getParameter("secondi")).thenReturn("" + c.getDataOra().get(GregorianCalendar.SECOND));
	Mockito.when(request.getSession(true)).thenReturn(session);
	Mockito.when(session.getAttribute("listaOggettiPreferiti")).thenReturn(lista);
	aggiungiPreferiti.doPost(request, response);
	
	assertEquals(lista.size(), 2);
	assertEquals(preferiti.doSearchByEmail("test@gmail.com").size(), 2);
	assertTrue(preferiti.doSearchByEmail("test@gmail.com").contains(managerRicerca.caricaAnnunci("test@gmail.com").get(0)));
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
