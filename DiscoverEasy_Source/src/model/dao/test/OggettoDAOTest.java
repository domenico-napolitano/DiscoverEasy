package model.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.DriverManagerConnectionPool;
import model.dao.OggettoDAO;

public class OggettoDAOTest {

	private static utenteBean utenteUno;

	private static utenteBean gestore;

	private static OggettoDAO oggettoDAO = new OggettoDAO();

	private static OggettoBean annuncioDue;

	private static OggettoBean annuncioTre;

	private static OggettoBean annuncioQuattro;

	@Before
	public void start() {

		utenteUno = new utenteBean();

		utenteUno.setNome("nomeTestUno");
		utenteUno.setPass("testtest");
		utenteUno.setEmail("testUno@test.it");
		utenteUno.setRegione("Campania");
		utenteUno.setSesso("maschio");
		utenteUno.setTelefono(3334445556L);
		utenteUno.setEmail_ban(null);
		utenteUno.setAmministratore(0);
		utenteUno.setAnno_nascita("1995");
		utenteUno.setEliminato(false);

		salvaUtente(utenteUno);

		gestore = new utenteBean();

		gestore.setNome("gestoreTestUno");
		gestore.setPass("testtest");
		gestore.setEmail("gestoreUno@test.it");
		gestore.setRegione("Campania");
		gestore.setSesso("maschio");
		gestore.setTelefono(3334445556L);
		gestore.setEmail_ban(null);
		gestore.setAmministratore(1);
		gestore.setAnno_nascita("1995");
		gestore.setEliminato(false);

		salvaUtente(gestore);

		annuncioDue = new OggettoBean();

		annuncioDue.setEmail(utenteUno.getEmail());

		annuncioDue.setDataOra(new GregorianCalendar(2010,10,10, 10,10,10));

		annuncioDue.setNome("annuncioTestTest");

		annuncioDue.setCategoria("Veicoli");

		annuncioDue.setRegione("Campania");

		annuncioDue.setPrezzo(33);

		annuncioDue.setEliminato(false);

		annuncioDue.setImmagine("immagine");

		annuncioDue.setDescrizione("descrizione");

		annuncioDue.setNome_proprietario(utenteUno.getNome());

		annuncioDue.setNumero_proprietario(utenteUno.getTelefono()+"");

		salvaAnnuncio(annuncioDue, null);

		annuncioTre = new OggettoBean();

		annuncioTre.setEmail(utenteUno.getEmail());

		annuncioTre.setDataOra(new GregorianCalendar(2010,5,5, 5,5,5));

		annuncioTre.setNome("annuncioTestTest");

		annuncioTre.setCategoria("Veicoli");

		annuncioTre.setRegione("Campania");

		annuncioTre.setPrezzo(33);

		annuncioTre.setEliminato(true);

		annuncioTre.setImmagine("immagine");

		annuncioTre.setDescrizione("descrizione");

		annuncioTre.setNome_proprietario(utenteUno.getNome());

		annuncioTre.setNumero_proprietario(utenteUno.getTelefono()+"");

		salvaAnnuncio(annuncioTre, null);

		annuncioQuattro = new OggettoBean();

		annuncioQuattro.setEmail(utenteUno.getEmail());

		annuncioQuattro.setDataOra(new GregorianCalendar(2010,3,3, 3,3,3));

		annuncioQuattro.setNome("annuncioTestTest");

		annuncioQuattro.setCategoria("Veicoli");

		annuncioQuattro.setRegione("Campania");

		annuncioQuattro.setPrezzo(33);

		annuncioQuattro.setEliminato(false);

		annuncioQuattro.setImmagine("immagine");

		annuncioQuattro.setDescrizione("descrizione");

		annuncioQuattro.setNome_proprietario(utenteUno.getNome());

		annuncioQuattro.setNumero_proprietario(utenteUno.getTelefono()+"");

		salvaAnnuncio(annuncioQuattro, "gestoreUno@test.it");


	}

	@Test
	public void doSaveTest() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		OggettoBean annuncioUno = new OggettoBean();

		annuncioUno.setEmail(utenteUno.getEmail());

		annuncioUno.setNome("annuncioTest");

		annuncioUno.setCategoria("Veicoli");

		annuncioUno.setRegione("Campania");

		annuncioUno.setPrezzo(33);

		annuncioUno.setEliminato(false);

		annuncioUno.setImmagine("immagine");

		annuncioUno.setDescrizione("descrizione");

		annuncioUno.setNome_proprietario(utenteUno.getNome());

		annuncioUno.setNumero_proprietario(utenteUno.getTelefono()+"");

		try {

			oggettoDAO.doSave(annuncioUno);

			OggettoBean annuncio = cercaAnnunci().get(0);

			assertEquals(annuncio, annuncioUno);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo doSave test fallito");
		}

	}

	@Test
	public void doSearchTest() {

		ArrayList<OggettoBean> lista =  oggettoDAO.doSearch(annuncioDue.getNome(), annuncioDue.getRegione(),
				annuncioDue.getCategoria());

		assertEquals(lista.size(), 1);

		assertTrue(lista.get(0).getNome().equals(annuncioDue.getNome()) && lista.get(0).getRegione().equals(annuncioDue.getRegione()) &&
				lista.get(0).getCategoria().equals(annuncioDue.getCategoria()));

		/*prova che il metodo dosearch non restituisce annunci bannati o eliminati*/

		/*metodo cerca annuncio, estrapola solo annunci non bannati o eliminati*/

		assertEquals(cercaAnnunci().size(), 1);

		OggettoBean annuncio = cercaAnnunci().get(0);

		assertEquals(annuncio, lista.get(0));
	}

	@Test
	public void doSearchByEmailTest() {

		try {
			ArrayList<OggettoBean> listaAnnunci = oggettoDAO.doSearchByEmail(utenteUno.getEmail());

			assertEquals(listaAnnunci.size(), 1);

			assertEquals(listaAnnunci.get(0), annuncioDue);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo doSearchByEmail fallito");
		}

	}

	@Test
	public void doSearchByKeyTest() {

		try {
			OggettoBean annuncio = oggettoDAO.doSearchByKey(annuncioDue.getNome(), annuncioDue.getEmail(),  annuncioDue.getDataOra());

			assertEquals(annuncio, annuncioDue);


			annuncio = oggettoDAO.doSearchByKey(annuncioTre.getNome(), annuncioTre.getEmail(),  annuncioTre.getDataOra());

			assertNull(annuncio);


			annuncio = oggettoDAO.doSearchByKey(annuncioQuattro.getNome(), annuncioQuattro.getEmail(),  annuncioQuattro.getDataOra());

			assertNull(annuncio);


			annuncio = oggettoDAO.doSearchByKey("annuncio non presente", annuncioQuattro.getEmail(),  annuncioQuattro.getDataOra());

			assertNull(annuncio);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("fallito metodo doSearchByTes");
		}

	}


	@Test
	public void doSearchNomeTest() {

		ArrayList<OggettoBean> lista =  oggettoDAO.doSearchNome(annuncioDue.getNome());

		assertEquals(lista.size(), 1);

		assertTrue(lista.get(0).getNome().equals(annuncioDue.getNome()));

		/*prova che il metodo dosearch non restituisce annunci bannati o eliminati*/

		/*metodo cerca annuncio, estrapola solo annunci non bannati o eliminati*/

		assertEquals(cercaAnnunci().size(), 1);

		OggettoBean annuncio = cercaAnnunci().get(0);

		assertEquals(annuncio, lista.get(0));	

	}

	@Test
	public void doSearchNomeCategoriaTest() {

		ArrayList<OggettoBean> lista =  oggettoDAO.doSearchNomeCategoria(annuncioDue.getNome(), annuncioDue.getCategoria());

		assertEquals(lista.size(), 1);

		assertTrue(lista.get(0).getNome().equals(annuncioDue.getNome()) && lista.get(0).getCategoria().equals(annuncioDue.getCategoria()));

		/*prova che il metodo dosearch non restituisce annunci bannati o eliminati*/

		/*metodo cerca annuncio, estrapola solo annunci non bannati o eliminati*/

		assertEquals(cercaAnnunci().size(), 1);

		OggettoBean annuncio = cercaAnnunci().get(0);

		assertEquals(annuncio, lista.get(0));	

	}

	@Test
	public void doSearchNomeRegioneTest() {

		ArrayList<OggettoBean> lista =  oggettoDAO.doSearchNomeRegione(annuncioDue.getNome(), annuncioDue.getRegione());

		assertEquals(lista.size(), 1);

		assertTrue(lista.get(0).getNome().equals(annuncioDue.getNome()) && lista.get(0).getRegione().equals(annuncioDue.getRegione()));

		/*prova che il metodo dosearch non restituisce annunci bannati o eliminati*/

		/*metodo cerca annuncio, estrapola solo annunci non bannati o eliminati*/

		assertEquals(cercaAnnunci().size(), 1);

		OggettoBean annuncio = cercaAnnunci().get(0);

		assertEquals(annuncio, lista.get(0));	

	}

	@Test
	public void removeByKeyTest() {

		try {
			oggettoDAO.removeByKey(annuncioDue.getEmail(), annuncioDue.getNome(), annuncioDue.getDataOra());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo removeByKey fallito");
		}

		assertEquals(0, cercaAnnunci().size());
	}

	@Test
	public void deleteByKey() {


		try {
			oggettoDAO.deleteByKey(annuncioDue.getEmail(), annuncioDue.getNome(), annuncioDue.getDataOra());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo deleteByKey fallito");
		}

		try {
			OggettoBean annuncio = cercaOggettoPerChiave(annuncioDue.getNome(), annuncioDue.getEmail(), annuncioDue.getDataOra());

			assertTrue(annuncio.getEliminato() == true);

			assertTrue(annuncio.getEmail_ban() == null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo cercaOggettoPerChiave fallito");
		}

	}

	@Test
	public void banByKey() {

		try {
			oggettoDAO.banByKey(gestore.getEmail(), annuncioDue.getEmail(), annuncioDue.getNome(), annuncioDue.getDataOra());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo banByKey fallito");
		}

		OggettoBean annuncio;
		try {
			annuncio = cercaOggettoPerChiave(annuncioDue.getNome(), annuncioDue.getEmail(), annuncioDue.getDataOra());
		
			assertTrue(annuncio.getEmail_ban().equals(gestore.getEmail()) &&
					annuncio.getEliminato() == false);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo cercaOggettoPerChiave fallito");
		}

	}

	@After
	public void stop() {

		eliminaUtente(utenteUno);
		eliminaUtente(gestore);
	}

	private void salvaUtente(utenteBean utente) {

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.UTENTE value(?,?,?,?,?,?,?,?,?,?)");

			ps.setString(1, utente.getEmail());
			ps.setString(2, utente.getPass());
			ps.setString(3, utente.getNome());
			ps.setString(4, utente.getSesso());
			ps.setInt(5, utente.getAnno_nascita());
			ps.setString(6, utente.getRegione());
			ps.setInt(7, 0);
			ps.setLong(8, utente.getTelefono());
			ps.setBoolean(9, false);
			ps.setString(10, null);

			ps.execute();

		} catch(SQLException e) {

			e.printStackTrace();

			fail("elimina utente metodo");

		}

		finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

				fail("salva utente metodo");

			}

		}
	}



	public ArrayList<OggettoBean> cercaAnnunci() {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore = UTENTE.email and annuncio.email_ban is null and annuncio.eliminato = false and email = 'testUno@test.it'");

			ArrayList<OggettoBean> lista = new ArrayList<>();

			ResultSet res = ps.executeQuery();

			Date data;

			Time orario;

			GregorianCalendar dataOraAnnuncio;

			// Prendi il risultato
			while(res.next())
			{
				OggettoBean oggetto = new OggettoBean();
				oggetto.setNome(res.getString("nome"));
				oggetto.setDescrizione(res.getString("descrizione"));
				oggetto.setImmagine(res.getString("immagine"));
				oggetto.setPrezzo(res.getInt("prezzo"));
				oggetto.setRegione(res.getString("regione"));
				oggetto.setEmail(res.getString("email_venditore"));
				oggetto.setNome_proprietario(res.getString("nome_venditore"));
				oggetto.setNumero_proprietario(res.getString("telefono"));
				oggetto.setCategoria(res.getString("categoria"));
				data = res.getDate("data_ora");

				orario = res.getTime("data_ora");

				dataOraAnnuncio = new GregorianCalendar();

				dataOraAnnuncio.setTime(data);

				dataOraAnnuncio.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				dataOraAnnuncio.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				dataOraAnnuncio.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				oggetto.setDataOra(dataOraAnnuncio);

				lista.add(oggetto);
			}

			return lista;

		} catch(SQLException e1) {

			e1.printStackTrace();

			fail("Ricerca annunci fallita");
		}
		finally{


			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return null;
	}


	public synchronized OggettoBean cercaOggettoPerChiave(String nome, String email_venditore, GregorianCalendar dataOra) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore = UTENTE.email and nome = ? and email_venditore = ? and data_ora = ?");

			ps.setString(1, nome.trim());
			ps.setString(2, email_venditore.trim());

			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			ps.setString(3, data_ora);

			ResultSet res = ps.executeQuery();

			Date data;

			Time orario;

			GregorianCalendar dataOraAnnuncio;

			// Prendi il risultato
			if(res.next())
			{
				OggettoBean oggetto = new OggettoBean();
				oggetto.setNome(res.getString("nome"));
				oggetto.setDescrizione(res.getString("descrizione"));
				oggetto.setImmagine(res.getString("immagine"));
				oggetto.setPrezzo(res.getInt("prezzo"));
				oggetto.setRegione(res.getString("regione"));
				oggetto.setEmail(res.getString("email_venditore"));
				oggetto.setNome_proprietario(res.getString("nome_venditore"));
				oggetto.setNumero_proprietario(res.getString("telefono"));
				oggetto.setCategoria(res.getString("categoria"));

				int eliminato = Integer.parseInt(res.getString("eliminato"));

				if(eliminato == 1)
				{
					oggetto.setEliminato(true);
				}
				else
				{
					oggetto.setEliminato(false);
				}

				oggetto.setEmail_ban(res.getString("email_ban"));

				data = res.getDate("data_ora");

				orario = res.getTime("data_ora");

				dataOraAnnuncio = new GregorianCalendar();

				dataOraAnnuncio.setTime(data);

				dataOraAnnuncio.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				dataOraAnnuncio.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				dataOraAnnuncio.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				oggetto.setDataOra(dataOraAnnuncio);

				return oggetto;
			}

		} finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return null;
	}


	public void eliminaUtente (utenteBean utente) {

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("DELETE from progettois.UTENTE where email = ?");

			ps.setString(1, utente.getEmail());
			ps.execute();

		} catch(SQLException e) {

			e.printStackTrace();

			fail("elimina utente metodo");

		}
		finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

				fail("elimina utente metodo");

			}

		}
	}

	public void salvaAnnuncio (OggettoBean annuncio, String emailBan) {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.ANNUNCIO value(?,?,?,?,?,?,?,?,?,?)");

			ps.setString(1, annuncio.getNome());
			ps.setString(2, annuncio.getImmagine());
			ps.setString(3, annuncio.getDescrizione());
			ps.setString(4, annuncio.getCategoria());
			ps.setString(5, annuncio.getRegione());
			ps.setInt(6, annuncio.getPrezzo());
			ps.setString(7, annuncio.getEmail());

			GregorianCalendar dataOra = annuncio.getDataOra();

			annuncio.setDataOra(dataOra);

			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			ps.setString(8, data_ora);

			ps.setBoolean(9, annuncio.getEliminato());

			ps.setString(10, emailBan);

			ps.execute();


		} catch(SQLException e) {

			e.printStackTrace();

			fail("salva annuncio metodo");

		}
		finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

				fail("salva annuncio metodo");
			}

		}

	}

}
