package manager.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.*;
import org.junit.Test;

import interfaccia.gestore.*;
import model.beans.*;
import model.dao.*;
import manager.utente.ManagerUtente;
public class ManagerUtenteTest{


	static ManagerUtente gestore = new ManagerUtente();
	static utenteBean utente = new utenteBean();
	static utenteBean utente1 = new utenteBean();
	static utenteDAO utenteDAO = new utenteDAO(); 
	static PreferitiDAO preferitiDAO = new PreferitiDAO();
	static OggettoDAO oggettoDAO = new OggettoDAO();

	@BeforeClass
	public static void inserisciUtente() {

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
			
			System.out.println("Funzione di inizializzazione..........Registro utente \n");
			gestore.registra("test@gmail.com", "testtest", "test", "Maschio", "Campania", "3333333333", "1990", "on");
		} 

		catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("registrazione fallita, parametri errati");
		} catch (SQLException e) {
			fail("registrazione fallita, query fallita");
			e.printStackTrace();
		}
		utente1.setEmail("test1@gmail.com");
		utente1.setNome("test");
		utente1.setSesso("Maschio");
		utente1.setAmministratore(0);
		utente1.setPass("testtest");
		utente1.setAnno_nascita("1990");
		utente1.setEliminato(false);
		utente1.setTelefono( new Long("3333333333"));
		utente1.setEmail_ban("amministratore@gmail.com");
		utente1.setRegione("Campania");
		try {
			utenteDAO.doSave(utente1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		utente1.setEmail("amministratore@gmail.com");
		utente1.setNome("test");
		utente1.setSesso("Maschio");
		utente1.setAmministratore(0);
		utente1.setPass("testtest");
		utente1.setAnno_nascita("1990");
		utente1.setEliminato(false);
		utente1.setTelefono( new Long("3333333333"));
		utente1.setEmail_ban("amministratore@gmail.com");
		utente1.setRegione("Campania");
		
		try {
			utenteDAO.doSave(utente1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			utenteDAO.banByKey("amministratore@gmail.com", "test1@gmail.com");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}



	@Test
	public void caricaMioProfiloTest() {
		System.out.println("Provo metodo caricaMioProfilo utente \n");
		utenteBean expected = utenteDAO.doRetrieveByKey("test@gmail.com");
		
		
		assertEquals(expected, utente); 
		System.out.println("Test superato");
	}
	
	@Test
	public void checkExistentTest() {
		ManagerUtenteTest.clean();
		assertNull(gestore.checkExistent("test@gmail.com"));
		ManagerUtenteTest.inserisciUtente();
		assertEquals(utenteDAO.doRetrieveByKey("test@gmail.com"), utente);
	}

	@Test
	public void modificaPasswordTest() {
		try {
			gestore.modificaPassword("test@gmail.com", "nuovaPassowrd");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		utenteBean a= utenteDAO.doRetrieveByKey("test@gmail.com");
		String nuovaPassword = a.getPass();
		assertEquals(nuovaPassword, a.getPass());;
	}
	
	@Test
	public void eliminaAccountTest() {
		System.out.println("Provo metodo modificaPassword utente");
		try {
			gestore.eliminaAccount("test@gmail.com");
		} catch (SQLException e) {
			e.printStackTrace();
		}
			utenteBean a = utenteDAO.doRetrieveByKey("test@gmail.com");	
		try {
			assertTrue(preferitiDAO.doSearchByEmail("test@gmail.com").isEmpty());
			assertTrue(oggettoDAO.doSearchByEmail("test@gmail.com").isEmpty());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile verificare annunci o preferiti sul database nel metodo eliminaAccount");
		}
		assertTrue(a.getEliminato());
		System.out.println("Provo metodo modificaPassword utente");
	}
	
	@Test
	public void autenticaTest(){
		utenteBean actual = gestore.autentica("test@gmail.com", "testtest");
		assertEquals(utente, actual);
		actual = gestore.autentica("test@gmail.com", "test");
		assertNull(actual);
		actual = gestore.autentica("test1@gmail.com", "testtest");
		assertNull(actual);
	}

	@AfterClass
	public static void clean (){
		try {
			utenteDAO.removeByKey("test@gmail.com");
			utenteDAO.removeByKey("test1@gmail.com");
			utenteDAO.removeByKey("amministratore@gmail.com");
			System.out.println("Funzione di pulizia.....pulizia fatta");
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail("pulizia test non riuscita");
		}
		
		
	
	}
	


}
