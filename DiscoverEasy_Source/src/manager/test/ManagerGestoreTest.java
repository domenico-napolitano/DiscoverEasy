package manager.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import manager.gestore.ManagerGestore;
import manager.utente.ManagerUtente;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.PreferitiDAO;
import model.dao.utenteDAO;

public class ManagerGestoreTest {

	
	static ManagerGestore gestoreGestore = new ManagerGestore();
	static ManagerUtente gestoreUtente = new ManagerUtente();
	static utenteBean utente = new utenteBean();
	static utenteDAO utenteDao = new utenteDAO(); 
	static OggettoBean oggetto;
	static OggettoDAO oggettoDao = new OggettoDAO(); 
	static PreferitiDAO preferitiDao = new PreferitiDAO();
	
@Before 
	public void start() {

		utente.setEmail("test@gmail.com");
		utente.setNome("test");
		utente.setSesso("Maschio");
		utente.setAmministratore(0);
		utente.setPass("testtest");
		utente.setAnno_nascita("1990");
		utente.setEliminato(false);
		utente.setTelefono( new Long("3333333333"));
		utente.setEmail_ban(null);
		utente.setRegione("Campania");
		
		try {
			utenteDao.doSave(utente);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, parametri non inizializzati");
		}
		
		utente.setEmail("amministratore@gmail.com");
		utente.setNome("test");
		utente.setSesso("Maschio");
		utente.setAmministratore(1);
		utente.setPass("testtest");
		utente.setAnno_nascita("1990");
		utente.setEliminato(false);
		utente.setTelefono( new Long("3333333333"));
		utente.setEmail_ban(null);
		utente.setRegione("Campania");
		
		oggetto = new OggettoBean("test", "Img\\Prodotti\\url_di_test.jpg", "test_test_test", "Veicoli", "Campania", "test@gmail.com","test", "3333333333", "null", 300, false, new GregorianCalendar(11,11,11,11,11,11));
		
		try {
			utenteDao.doSave(utente);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, parametri non inizializzati");
		}
		try {
			oggettoDao.doSave(oggetto);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, parametri non inizializzati");
		}
		
		try {
			preferitiDao.doSave("test@gmail.com", "test@gmail.com", "test", oggettoDao.doSearchByEmail("test@gmail.com").get(0).getDataOra());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile inizializzare preferiti utente");
		}
	
}	

@Test
public void banAccountTest() {
try {
	gestoreGestore.banAccount("amministratore@gmail.com", "test@gmail.com");
} catch (SQLException e) {
	e.printStackTrace();
	fail("Test fallito, impossibile bannare account");
}
assertEquals(utenteDao.doRetrieveByKey("test@gmail.com").getEmail_ban(), "amministratore@gmail.com");
try {
	assertTrue(oggettoDao.doSearchByEmail("test@gmail.com").isEmpty());
} catch (SQLException e) {
	e.printStackTrace();
	fail("Test fallito, impossibile verificare gli annunci dell'account bannato");
	
}
try {
	assertTrue(preferitiDao.doSearchByEmail("test@gmail.com").isEmpty());
} catch (SQLException e) {
	e.printStackTrace();
	fail("Test fallito, impossibile verificare preferiti account bannato");
}
}

@Test 
	public void banAnnuncioTest() {
	try {
		gestoreGestore.banAnnuncio("amministratore@gmail.com", "test", "test@gmail.com", oggettoDao.doSearchByEmail("test@gmail.com").get(0).getDataOra());
	} catch (SQLException e) {
		e.printStackTrace();
		fail("Test fallito, impossibile bannare annuncio");
	}
	try {
		assertTrue(oggettoDao.doSearchByEmail("test@gmail.com").isEmpty());
	} catch (SQLException e) {
		e.printStackTrace();
		fail("Test fallito, impossibile verificare banAnnuncio");
	}
}




@After
	public void stop() {
	try {
		utenteDao.removeByKey("amministratore@gmail.com");
	} catch (SQLException e) {
		e.printStackTrace();
		fail("Pulizia non riuscita, test falllito");
	}
	try {
		utenteDao.removeByKey("test@gmail.com");
	} catch (SQLException e) {
		e.printStackTrace();
		fail("Pulizia non riuscita, test falllito");
	}
	
	
}
}
	
	
	
	
	
	
	
	

