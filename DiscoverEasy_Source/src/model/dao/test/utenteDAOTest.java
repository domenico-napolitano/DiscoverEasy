package model.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.beans.utenteBean;
import model.dao.DriverManagerConnectionPool;
import model.dao.utenteDAO;

public class utenteDAOTest {

	private static utenteDAO utenteDao= new utenteDAO();

	private static utenteBean utenteUno;
	
	private static utenteBean gestore;

	@Before
	public void start() {

		utenteUno = new utenteBean();

		utenteUno.setAnno_nascita("1997");

		utenteUno.setEmail("utenteUnoTest@utenteDAO.it");

		utenteUno.setNome("test");

		utenteUno.setPass("testtest");

		utenteUno.setRegione("Regione");

		utenteUno.setSesso("Maschio");

		utenteUno.setTelefono(3334445556L);

		salvaUtente(utenteUno);	
		
		gestore = new utenteBean();

		gestore.setAnno_nascita("1997");

		gestore.setEmail("gestore@utenteDAO.it");

		gestore.setNome("test");

		gestore.setPass("testtest");

		gestore.setRegione("Regione");

		gestore.setSesso("Maschio");

		gestore.setTelefono(3334445556L);

		salvaUtente(gestore);	
	}

	@Test
	public void doSaveTest() {

		utenteBean utente = new utenteBean();

		utente.setAnno_nascita("1997");

		utente.setEmail("utenteTest@utenteDAO.it");

		utente.setNome("test");

		utente.setPass("testtest");

		utente.setRegione("Regione");

		utente.setSesso("Maschio");

		utente.setTelefono(3334445556L);

		eliminaUtente(utente.getEmail());

		try {

			utenteDao.doSave(utente);

			assertEquals(utente, recuperaUtente(utente.getEmail()));

			eliminaUtente(utente.getEmail());


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo doSave fallito");
		}
	}
	
	@Test
	public void removeByKeyTest() {
		
		try {
			utenteDao.removeByKey(utenteUno.getEmail());
			
			assertTrue(recuperaUtente(utenteUno.getEmail()) == null);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("metodo removeByKey fallito");
		}
	}
	
	@Test
	public void doRetrieveByKeyTest() {
		
		utenteBean utente = utenteDao.doRetrieveByKey(utenteUno.getEmail());
		
		assertEquals(utente, utenteUno);

		assertNull(utenteDao.doRetrieveByKey("Email non esistente"));
	}
	
	@Test
	public void doChangePasswordTest() {
		
		try {
		
			utenteDao.doChangePassword(utenteUno.getEmail(), "nuova");
			
			utenteBean utente = recuperaUtente(utenteUno.getEmail());
			
			assertEquals(utente.getPass(), "nuova");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("metodo doChangePassword fallito");
		}
	}
	
	@Test
	public void deleteByKeyTest() {
		
		try {
			utenteDao.deleteByKey(utenteUno.getEmail());
			
			utenteBean utente = recuperaUtente(utenteUno.getEmail());
			
			assertEquals(utente.getEliminato(), true);
			
			assertNull(utente.getEmail_ban());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("metodo deleteByKey fallito");
		}
	}
	
	@Test
	public void banByKeyTest() {
		
		try {
			utenteDao.banByKey(gestore.getEmail(), utenteUno.getEmail());
			
			utenteBean utente = recuperaUtente(utenteUno.getEmail());
			
			assertEquals(utente.getEliminato(), false);
			
			assertEquals(utente.getEmail_ban(), gestore.getEmail());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("metodo deleteByKey fallito");
		}
	}

	@After
	public void stop(){
		
		eliminaUtente(utenteUno.getEmail());
		
		eliminaUtente(gestore.getEmail());
	}

	private utenteBean recuperaUtente(String email) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {

			utenteBean utente = new utenteBean(); 
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.UTENTE where email = ?");
			ps.setString(1, email);

			ResultSet res = ps.executeQuery();

			// Prendi il risultato
			if(res.next())
			{
				utente.setNome(res.getString("nome_venditore"));
				utente.setAnno_nascita(res.getString("anno_nascita"));
				utente.setRegione(res.getString("regione_venditore"));
				utente.setSesso(res.getString("sesso"));
				utente.setEmail(res.getString("email"));
				utente.setPass(res.getString("pass"));
				utente.setAmministratore(res.getInt("amministratore"));
				utente.setEliminato(res.getBoolean("eliminato"));
				utente.setTelefono(res.getLong("telefono"));
				utente.setEmail_ban(res.getString("email_ban"));

				return utente;
			}

		} catch (SQLException e) {

			e.printStackTrace();

			fail("metodo recuperaUtente fallito");

		}finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return null;
	}


	private void eliminaUtente(String email) {

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("DELETE from progettois.UTENTE where email = ?");

			ps.setString(1, email);
			ps.execute();

		} catch(SQLException e1) {

			e1.printStackTrace();

			fail("metodo eliminaUtene fallito");
		}
		finally{


			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
	}

	private void salvaUtente(utenteBean c) {
		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.UTENTE value(?,?,?,?,?,?,?,?,?,?)");

			ps.setString(1, c.getEmail());
			ps.setString(2, c.getPass());
			ps.setString(3, c.getNome());
			ps.setString(4, c.getSesso());
			ps.setInt(5, c.getAnno_nascita());
			ps.setString(6, c.getRegione());
			ps.setInt(7, 0);
			ps.setLong(8, c.getTelefono());
			ps.setBoolean(9, false);
			ps.setString(10, null);

			ps.execute();

		} catch(SQLException e) {

			e.printStackTrace();

			fail("metodo salvaUtente fallito");
		}finally{


			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
	}
}
