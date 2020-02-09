package model.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.DriverManagerConnectionPool;
import model.dao.PreferitiDAO;

public class PreferitiDAOTest {

	private static utenteBean utenteUno;
	
	private static utenteBean gestore;

	private static OggettoBean annuncioUno;

	private static OggettoBean annuncioDue;
	
	private static OggettoBean annunciotre;
	
	private static OggettoBean annuncioQuattro;
	
	private static PreferitiDAO preferitiDAO = new PreferitiDAO();

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

		annuncioUno = new OggettoBean();

		annuncioUno.setEmail(utenteUno.getEmail());

		annuncioUno.setDataOra(new GregorianCalendar(2010,10,10, 10,10,10));

		annuncioUno.setNome("annuncioTestTest");

		annuncioUno.setCategoria("Veicoli");

		annuncioUno.setRegione("Campania");

		annuncioUno.setPrezzo(33);

		annuncioUno.setImmagine("immagine");

		annuncioUno.setDescrizione("descrizione");

		annuncioUno.setNome_proprietario(utenteUno.getNome());

		annuncioUno.setNumero_proprietario(utenteUno.getTelefono()+"");
		
		salvaAnnuncio(annuncioUno, null);
		
		annuncioDue = new OggettoBean();

		annuncioDue.setEmail(utenteUno.getEmail());

		annuncioDue.setDataOra(new GregorianCalendar(2011,11,11, 10,10,10));

		annuncioDue.setNome("annuncioTestTest");

		annuncioDue.setCategoria("Veicoli");

		annuncioDue.setRegione("Campania");

		annuncioDue.setPrezzo(33);

		annuncioDue.setEliminato(true);
		
		annuncioDue.setImmagine("immagine");

		annuncioDue.setDescrizione("descrizione");

		annuncioDue.setNome_proprietario(utenteUno.getNome());

		annuncioDue.setNumero_proprietario(utenteUno.getTelefono()+"");
		
		salvaAnnuncio(annuncioDue, null);
		
		annunciotre = new OggettoBean();

		annunciotre.setEmail(utenteUno.getEmail());

		annunciotre.setDataOra(new GregorianCalendar(2012,11,11, 10,10,10));

		annunciotre.setNome("annuncioTestTest");

		annunciotre.setCategoria("Veicoli");

		annunciotre.setRegione("Campania");

		annunciotre.setPrezzo(33);
		
		annunciotre.setImmagine("immagine");

		annunciotre.setDescrizione("descrizione");

		annunciotre.setNome_proprietario(utenteUno.getNome());

		annunciotre.setNumero_proprietario(utenteUno.getTelefono()+"");
		
		salvaAnnuncio(annunciotre, "gestore@utenteDAO.it");
		
		annuncioQuattro = new OggettoBean();

		annuncioQuattro.setEmail(utenteUno.getEmail());

		annuncioQuattro.setDataOra(new GregorianCalendar(2013,11,11, 10,10,10));

		annuncioQuattro.setNome("annuncioTestTest");

		annuncioQuattro.setCategoria("Veicoli");

		annuncioQuattro.setRegione("Campania");

		annuncioQuattro.setPrezzo(33);
		
		annuncioQuattro.setImmagine("immagine");

		annuncioQuattro.setDescrizione("descrizione");

		annuncioQuattro.setNome_proprietario(utenteUno.getNome());

		annuncioQuattro.setNumero_proprietario(utenteUno.getTelefono()+"");
		
		salvaAnnuncio(annuncioQuattro, null);

	}

	@Test
	public void doSaveTest() {

		try {
			preferitiDAO.doSave(utenteUno.getEmail(), annuncioUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra());

			Hashtable<String, OggettoBean> preferiti = recuperaPreferiti(utenteUno.getEmail());

			Set<String> set = preferiti.keySet();

			Iterator chiavi = set.iterator();

			OggettoBean oggetto = preferiti.get(chiavi.next());

			assertTrue(oggetto.getDataOra().equals(annuncioUno.getDataOra()) && oggetto.getNome().equals(annuncioUno.getNome()) &&
					oggetto.getEmail().equals(annuncioUno.getEmail()) && oggetto.getEmailPreferitiUtente().equals(utenteUno.getEmail()));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo doSave fallito");
		}
	}

	@Test
	public void doSearchByEmailTest() {

		try {
			
			
			salvaPreferito(utenteUno.getEmail(), annuncioUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra(), false);

			salvaPreferito(utenteUno.getEmail(), annuncioDue.getEmail(), annuncioDue.getNome(), annuncioDue.getDataOra(), false);
			
			salvaPreferito(utenteUno.getEmail(), annunciotre.getEmail(), annunciotre.getNome(), annunciotre.getDataOra(), false);

			salvaPreferito(utenteUno.getEmail(), annuncioQuattro.getEmail(), annuncioQuattro.getNome(), annuncioQuattro.getDataOra(), true);
			
			Hashtable<String, OggettoBean> preferiti = preferitiDAO.doSearchByEmail(utenteUno.getEmail());

			Set<String> set = preferiti.keySet();
			
			Iterator chiavi = set.iterator();

			OggettoBean oggetto = preferiti.get(chiavi.next());
			
			assertTrue(preferiti.size() == 1);
			
			assertTrue(oggetto.getEmailPreferitiUtente().equals(utenteUno.getEmail()) &&
					oggetto.getEmail().equals(annuncioUno.getEmail()));
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("il metodo doSearchByEmail è fallito");
		}
	}
	
	@Test
	public void deleteByKey() {
		
		try {
			
			salvaPreferito(utenteUno.getEmail(), annuncioUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra(), false);
			
			Hashtable<String, OggettoBean> lista = recuperaPreferiti(utenteUno.getEmail());
			
			assertEquals(lista.size(), 1);
			
			preferitiDAO.deleteByKey(utenteUno.getEmail(), annuncioUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra());
		
			lista = recuperaPreferiti(utenteUno.getEmail());

			assertEquals(lista.size(), 0);
			
			//lista = recuperaPreferiti(utenteUno.getEmail());
			
			//assertTrue(lista.size() == 0);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("metodo deleteByKey fallito");
		}
	}
	
	@Test
	public void deleteAll() {
		
		salvaPreferito(utenteUno.getEmail(), annuncioUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra(), false);
		
		salvaPreferito(utenteUno.getEmail(), annuncioQuattro.getEmail(), annuncioQuattro.getNome(), annuncioQuattro.getDataOra(), false);

		try {
			
			Hashtable<String, OggettoBean> lista = recuperaPreferiti(utenteUno.getEmail());
			
			assertEquals(lista.size(), 2);
			
			preferitiDAO.deleteAll(utenteUno.getEmail());
			
			lista = recuperaPreferiti(utenteUno.getEmail());
			
			assertEquals(lista.size(), 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("il metodo deleteAll è fallito");
		}
	}

	@After
	public void stop(){

		eliminaUtente(utenteUno.getEmail());

		eliminaUtente(gestore.getEmail());
	}

	public void salvaPreferito(String emailUtente, String emailVenditore, String nomeAnnuncio,
			GregorianCalendar dataOra, boolean eliminato){

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("INSERT INTO progettois.PREFERITI value(?,?,?,?,?,?)");

			GregorianCalendar dataOraSalvataggio = new GregorianCalendar();

			int annoSalvataggio = dataOraSalvataggio.get(GregorianCalendar.YEAR);

			int meseSalvataggio = dataOraSalvataggio.get(GregorianCalendar.MONTH)+1;

			int giornoSalvataggio = dataOraSalvataggio.get(GregorianCalendar.DAY_OF_MONTH);

			int oraSalvataggio = dataOraSalvataggio.get(GregorianCalendar.HOUR_OF_DAY);

			int minutiSalvataggio = dataOraSalvataggio.get(GregorianCalendar.MINUTE);

			int secondiSalvataggio = dataOraSalvataggio.get(GregorianCalendar.SECOND);

			String data_ora_salvataggio = annoSalvataggio+"-"+meseSalvataggio+"-"+giornoSalvataggio+" "+oraSalvataggio+":"+minutiSalvataggio+":"+secondiSalvataggio;

			ps.setString(1, data_ora_salvataggio);

			ps.setString(2, emailUtente);

			System.out.println("emailUtente: "+ emailUtente);

			ps.setString(3, emailVenditore);

			System.out.println("emailVenditore: "+ emailVenditore);

			ps.setString(4, nomeAnnuncio);

			System.out.println("nomeAnnuncio: "+ nomeAnnuncio);

			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			System.out.println("data_ora: "+ data_ora);

			ps.setString(5, data_ora);

			ps.setBoolean(6, eliminato);

			boolean res;

			System.out.println( res = ps.execute());


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("Il medoto salvaPreferito è fallito");
		}

	}


	public Hashtable<String, OggettoBean> recuperaPreferiti(String email_utente) {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.PREFERITI inner join progettois.UTENTE where ANNUNCIO.email_venditore=PREFERITI.email_venditore and ANNUNCIO.nome=PREFERITI.nome_annuncio and ANNUNCIO.data_ora=PREFERITI.data_ora_annuncio and UTENTE.email = PREFERITI.email_utente and email_utente = ?  and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null and PREFERITI.eliminato = false");
			ps.setString(1, email_utente);
			ResultSet res = ps.executeQuery();

			Hashtable<String, OggettoBean> listaOggetti = new Hashtable<>();

			Date data;

			Time orario;

			GregorianCalendar data_ora;

			OggettoBean oggetto;

			// Prendi il risultato
			while(res.next())
			{
				oggetto = new OggettoBean();
				oggetto.setNome(res.getString("nome"));
				oggetto.setDescrizione(res.getString("descrizione"));
				oggetto.setImmagine(res.getString("immagine"));
				oggetto.setPrezzo(res.getInt("prezzo"));
				oggetto.setRegione(res.getString("regione"));
				oggetto.setEmail(res.getString("email_venditore"));
				oggetto.setEmailPreferitiUtente(res.getString("email_utente"));
				oggetto.setNome_proprietario(res.getString("nome_venditore"));
				oggetto.setCategoria(res.getString("categoria"));
				oggetto.setNumero_proprietario(res.getString("telefono"));

				data = res.getDate("data_ora");

				orario = res.getTime("data_ora");

				data_ora = new GregorianCalendar();

				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				oggetto.setDataOra(data_ora);

				listaOggetti.put(oggetto.getNome()+oggetto.getEmail()+data_ora.get(GregorianCalendar.YEAR)+
						data_ora.get(GregorianCalendar.MONTH)+data_ora.get(GregorianCalendar.DAY_OF_MONTH)+
						data_ora.get(GregorianCalendar.HOUR_OF_DAY)+data_ora.get(GregorianCalendar.MINUTE)+
						data_ora.get(GregorianCalendar.SECOND),oggetto);				
			}

			return listaOggetti;

		}catch(SQLException e1) {

			e1.printStackTrace();

			fail("metodo recupera preferiti fallito");

		}  finally{

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
