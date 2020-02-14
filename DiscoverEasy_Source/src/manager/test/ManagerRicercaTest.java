package manager.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import manager.ricerca.ManagerRicerca;
import manager.utente.ManagerUtente;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.PreferitiDAO;
import model.dao.utenteDAO;

public class ManagerRicercaTest {

	static ManagerRicerca gestoreRicerca = new ManagerRicerca();
	static ManagerUtente gestoreUtente = new ManagerUtente();
	static utenteBean utente = new utenteBean();
	static utenteDAO utenteDao = new utenteDAO(); 
	static OggettoBean oggetto;
	static OggettoBean oggetto1;
	static OggettoBean oggetto2;
	static OggettoBean oggetto3;
	static OggettoBean oggetto4;	
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

		utente.setEmail("test1@gmail.com");
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

		oggetto = new OggettoBean("test", "Img\\Prodotti\\url_di_test.jpg", "test_test_test", "Veicoli", "Campania", "test@gmail.com","test", "3333333333", "null", 300, false, new GregorianCalendar(11,11,11,11,11,11));

		try {
			oggettoDao.doSave(oggetto);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, parametri non inizializzati");
		}

		oggetto1 = new OggettoBean("test1", "Img\\Prodotti\\url_di_test.jpg", "test_test_test", "Veicoli", "Campania", "test@gmail.com","test", "3333333333", "null", 300, false, new GregorianCalendar(11,11,11,11,11,11));

		try {
			oggettoDao.doSave(oggetto1);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, parametri non inizializzati");
		}

		oggetto2 = new OggettoBean("test2", "Img\\Prodotti\\url_di_test.jpg", "test_test_test", "Veicoli", "Campania", "test1@gmail.com","test", "3333333333", "null", 300, false, new GregorianCalendar(11,11,11,11,11,11));

		try {
			oggettoDao.doSave(oggetto2);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, parametri non inizializzati");
		}


		oggetto3 = new OggettoBean("test3", "Img\\Prodotti\\url_di_test.jpg", "test_test_test", "Veicoli", "Campania", "test@gmail.com","test", "3333333333", "amministratore@gmail.com", 300, false, new GregorianCalendar(11,11,11,11,11,11));

		try {
			oggettoDao.doSave(oggetto3);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, parametri non inizializzati");
		}
		
		oggetto4 = new OggettoBean("test4", "Img\\Prodotti\\url_di_test.jpg", "test_test_test", "Veicoli", "Campania", "test@gmail.com","test", "3333333333", "null", 300, true, new GregorianCalendar(11,11,11,11,11,11));

		try {
			oggettoDao.doSave(oggetto4);
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

		try {
			preferitiDao.doSave("test@gmail.com", "test@gmail.com", "test1", oggettoDao.doSearchByEmail("test@gmail.com").get(0).getDataOra());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile inizializzare preferiti utente");
		}

		try {
			preferitiDao.doSave("test1@gmail.com", "test1@gmail.com", "test2", oggettoDao.doSearchByEmail("test@gmail.com").get(0).getDataOra());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile inizializzare preferiti utente");
		}

	}




	@Test
	public void caricaAnnunciTest() {
		ArrayList<OggettoBean> lista = null;
		try {
			lista = gestoreRicerca.caricaAnnunci("test@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile carica annunci dal database");
		}

		assertTrue((lista.get(0).equals(oggetto) || lista.get(1).equals(oggetto)) && (lista.get(0).equals(oggetto1) || lista.get(1).equals(oggetto1)));
	}

	@Test
	public void cestinaAnnunciTest() {
		try {
			gestoreRicerca.cestinaAnnunci("test@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile eliminare preferiti in cestinaAnnunci");
		}
		try {
			assertTrue(preferitiDao.doSearchByEmail("test@gmail.com").isEmpty());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile verificare eliminazione annunci in cestinaAnnunci");
		}
	}

	@Test
	public void eliminaAnnuncioTest() {

		try {
			System.out.println(preferitiDao.doSearchByEmail("test@gmail.com").size());
			gestoreRicerca.eliminaAnnuncio("test@gmail.com", "test@gmail.com", "test1", oggettoDao.doSearchByEmail("test@gmail.com").get(0).getDataOra());
			System.out.println(preferitiDao.doSearchByEmail("test@gmail.com").size());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile eliminare preferito in eliminaAnnuncio");
		}
		try {
			
			assertTrue(preferitiDao.doSearchByEmail("test@gmail.com").values().contains(oggetto));
			assertFalse(preferitiDao.doSearchByEmail("test@gmail.com").values().contains(oggetto1));	
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile verificare eliminazione annuncio in eliminaAnnuncio");
		}
	}
	
	
	public void salvaAnnuncioAiPreferitiTest() {
		
		try {
			gestoreRicerca.salvaAnnuncioAiPreferiti("test1@gmail.com", oggetto2);
			Hashtable<String, OggettoBean> preferitiSuccessivi = preferitiDao.doSearchByEmail("test@gmail.com");
			int dimensione= preferitiSuccessivi.size();
			assertEquals(dimensione, 3);
			assertTrue(preferitiDao.doSearchByEmail("test1@gmail.com").values().contains(oggetto));
			assertTrue(preferitiDao.doSearchByEmail("test1@gmail.com").values().contains(oggetto1));
			assertTrue(preferitiDao.doSearchByEmail("test1@gmail.com").values().contains(oggetto2));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile verificare eliminazione annuncio in eliminaAnnuncio");
		}
	}
	
	

	@Test
	public void ricercaAnnunciTest () {
		ArrayList<OggettoBean> lista = gestoreRicerca.ricercaAnnunci("Campania", "Veicoli", "test");
		assertEquals(lista.size(), 5);
		assertTrue(lista.contains(oggetto) && lista.contains(oggetto1) && lista.contains(oggetto2) && lista.contains(oggetto3) && lista.contains(oggetto4));
		lista = gestoreRicerca.ricercaAnnunci("Campania", "Veicoli", "test1");
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(oggetto1));
	}

	@Test
	public void ricercaAnnuncioPerChiaveTest() {
		OggettoBean actual = null;
		try {
			actual = gestoreRicerca.ricercaAnnuncioPerChiave("test", "test@gmail.com", oggettoDao.doSearchNome("test").get(0).getDataOra());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile ricerca annuncio in ricercaAnnuncioPerChiave");
		}
		assertEquals(actual, oggetto);
	}

	@Test
	public void mergePreferitiSessioneDatabaseTest() {
		
		Hashtable<String, OggettoBean> preferitiSessione = new Hashtable<String, OggettoBean>();
				
				oggetto = oggettoDao.doSearchNome("test").get(0); 
				oggetto1 = oggettoDao.doSearchNome("test1").get(0); 
		preferitiSessione.put(oggetto.getNome()+oggetto.getEmail()+oggetto.getDataOra().get(GregorianCalendar.YEAR)+
				oggetto.getDataOra().get(GregorianCalendar.MONTH)+oggetto.getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
				oggetto.getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+oggetto.getDataOra().get(GregorianCalendar.MINUTE)+
				oggetto.getDataOra().get(GregorianCalendar.SECOND), oggetto);
		
		preferitiSessione.put(oggetto1.getNome()+oggetto1.getEmail()+oggetto1.getDataOra().get(GregorianCalendar.YEAR)+
				oggetto1.getDataOra().get(GregorianCalendar.MONTH)+oggetto1.getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
				oggetto1.getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+oggetto1.getDataOra().get(GregorianCalendar.MINUTE)+
				oggetto1.getDataOra().get(GregorianCalendar.SECOND), oggetto1);
		
		try {
			Hashtable<String, OggettoBean> preferitiPrecedenti = preferitiDao.doSearchByEmail("test1@gmail.com");
			int dimensionePrecedente = preferitiPrecedenti.size();	
			Hashtable<String, OggettoBean> hashRitorno = gestoreRicerca.mergePreferitiSessioneDatabase(preferitiSessione, "test1@gmail.com");
			Hashtable<String, OggettoBean> preferitiSuccessivi = preferitiDao.doSearchByEmail("test1@gmail.com");
				
			assertEquals(hashRitorno,preferitiSuccessivi);
			assertEquals(dimensionePrecedente + 2, preferitiSuccessivi.size());
			assertEquals(oggettoDao.doSearchByEmail("test@gmail.com").get(0), oggetto);
			assertTrue(preferitiSuccessivi.contains(oggetto));
			assertTrue(preferitiSuccessivi.contains(oggetto1));
			assertTrue(preferitiSuccessivi.contains(oggetto2));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile verificare preferiti in metodo unisci preferiti sessione database");
		}
		
	}

	@After
	public void stop() {
		try {
			utenteDao.removeByKey("test@gmail.com");
			utenteDao.removeByKey("test1@gmail.com");
			utenteDao.removeByKey("amministratore@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Pulizia non riuscita, test falllito");
		}
	}		

}
