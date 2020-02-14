package manager.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import manager.negozio.ManagerNegozio;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.utenteDAO;

public class ManagerNegozioTest {

	private static ManagerNegozio gestoreNegozio = new ManagerNegozio();
	private static utenteDAO utente = new utenteDAO();
	private static OggettoDAO annuncio = new OggettoDAO();
	private static OggettoBean test = new OggettoBean("test", "Img\\Prodotti\\url_di_test.jpg", "test_test_test", "Veicoli", "Campania", "test@gmail.com",
			"test", "3333333333", "null", 300, false, new GregorianCalendar(11,11,11,11,11,11));
	
	
	@BeforeClass
	public static void start () {

		try {
			
			utenteBean utente_test = new utenteBean();
			utente_test.setEmail("test@gmail.com");
			utente_test.setPass("testtest");
			utente_test.setNome("test");
			utente_test.setSesso("Maschio");
			utente_test.setRegione("Campania");
			utente_test.setTelefono(new Long("3333333333"));
			utente_test.setEmail_ban(null);
			utente_test.setEliminato(false);
			utente_test.setAmministratore(0);
			utente_test.setAnno_nascita("1990");
			utente.doSave(utente_test);
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
			fail("Test fallito, utente di test non inizializzato");
		}


	}

	@Test
	public void salvaAnnuncioTest() throws SQLException {
System.out.println("Test salvaAnnuncio iniziato");
		try {
			assertEquals(0, annuncio.doSearchByEmail("test@gmail.com").size());
			gestoreNegozio.salvaAnnuncio("test@gmail.com", "test", "Veicoli", "Campania", "test_test_test", "300", "url_di_test.jpg");
			assertEquals(1, annuncio.doSearchByEmail("test@gmail.com").size());
			ArrayList<OggettoBean> lista = annuncio.doSearchByEmail("test@gmail.com");
			assertTrue(lista.get(0).equals(test));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, annuncio non creato");
		}
		System.out.println("Test salvaAnnuncio superato");
	
	}

	@Test
	public void eliminaAnnuncio() {	
		System.out.println("Test eliminaAnnuncio iniziato");
		OggettoBean annuncioDaEliminare = null;
		try {
			int dimensione = annuncio.doSearchByEmail("test@gmail.com").size();
			annuncioDaEliminare = annuncio.doSearchByEmail("test@gmail.com").get(0);
			gestoreNegozio.eliminaAnnuncio(annuncioDaEliminare.getNome(), annuncioDaEliminare.getEmail(), annuncioDaEliminare.getDataOra());
			assertEquals(annuncio.doSearchByEmail("test@gmail.com").size(), dimensione -1);
		} catch (SQLException e1) {
			e1.printStackTrace();
			fail("Test fallito, impossibile recupeare oggetto dal database");
		}
	}

	@AfterClass
	public static void stop() {

		try {
			utente.removeByKey("test@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Pulizia non riuscita, account di test non eliminato");
		}


	}


}
