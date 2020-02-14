package manager.test;

import static org.junit.Assert.assertEquals;
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

import manager.chat.ManagerChat;
import model.beans.*;
import model.dao.*;

public class ManagerChatTest {

	private static utenteBean utenteUno = new utenteBean();

	private static utenteBean utenteDue = new utenteBean();

	private static OggettoBean annuncioUno = new OggettoBean();

	private static ManagerChat manager = new ManagerChat();

	private static utenteDAO utenteDao = new utenteDAO();

	private static OggettoDAO oggettoDAO = new OggettoDAO();

	private static ChatDAO chatDAO = new ChatDAO();

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

		utenteDue = new utenteBean();

		utenteDue.setNome("nomeTestDue");
		utenteDue.setPass("testtest");
		utenteDue.setEmail("testDue@test.it");
		utenteDue.setRegione("Campania");
		utenteDue.setSesso("maschio");
		utenteDue.setTelefono(3334445556L);
		utenteDue.setEmail_ban(null);
		utenteDue.setAmministratore(0);
		utenteDue.setAnno_nascita("1995");
		utenteDue.setEliminato(false);


		annuncioUno = new OggettoBean();

		annuncioUno.setEmail(utenteUno.getEmail());

		annuncioUno.setNome("annuncioUnoTest");

		GregorianCalendar dataOraAnnuncioUno = new GregorianCalendar(2020, 10, 10, 10, 10, 10);

		annuncioUno.setDataOra(dataOraAnnuncioUno);

		annuncioUno.setCategoria("Veicoli");

		annuncioUno.setRegione("Campania");

		annuncioUno.setPrezzo(33);

		annuncioUno.setImmagine("immagine");

		annuncioUno.setDescrizione("descrizione");

		try {

			utenteDao.doSave(utenteUno);

			utenteDao.doSave(utenteDue);

			oggettoDAO.doSave(annuncioUno);

			GregorianCalendar oraAnnuncio = (GregorianCalendar)annuncioUno.getDataOra().clone();

			oraAnnuncio.set(GregorianCalendar.MONTH, oraAnnuncio.get(GregorianCalendar.MONTH)+1);

			chatDAO.iniziaNuovaChat(utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), oraAnnuncio);

			MessaggioBean messaggioUno = new MessaggioBean();

			messaggioUno.setDescrizione("ciao");

			messaggioUno.setEmailMittenteMessaggio(utenteDue.getEmail());

			chatDAO.doSave(messaggioUno, utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), oraAnnuncio, 0, 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo salvataggio");
		}

	}

	@Test
	public void caricaChatTest() {

		try {

			ChatBean chat = manager.caricaChat(utenteUno.getEmail()).get(0);

			assertTrue(chat.getEmailDestinatario().equals(utenteUno.getEmail()));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo caricaChat eliminato");
		}
	}

	@Test
	public void caricaMessaggi() {

		try {

			/*vedo se mi setta a zero anche attributo destinatario_messaggio_non_letto
			 e se funziona il caricamento dei messaggi*/


			ChatBean chat = chatDAO.doSearchByEmail(utenteUno.getEmail()).get(0);

			assertTrue(chat.getDestinatarioMessaggioNonLetto() > 0);

			MessaggioBean messaggio = manager.caricaMessaggi(utenteUno.getEmail(), utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra()).get(0);

			chat = chatDAO.doSearchByEmail(utenteUno.getEmail()).get(0);

			assertTrue(chat.getDestinatarioMessaggioNonLetto() == 0);

			assertTrue(messaggio.getEmailMittenteMessaggio().equals(utenteDue.getEmail()));

			assertTrue(messaggio.getEmailMittenteChat().equals(utenteDue.getEmail()));

			assertTrue(messaggio.getEmailDestinatarioChat().equals(utenteUno.getEmail()));

			assertTrue(messaggio.getDataOraAnnuncio().equals(annuncioUno.getDataOra()));

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*vedo se mi setta a zero anche attributo mittente_messaggio_non_letto*/

			MessaggioBean messaggioDue = new MessaggioBean();

			messaggioDue.setDescrizione("ciao come va");

			messaggioDue.setEmailMittenteMessaggio(utenteUno.getEmail());

			GregorianCalendar oraAnnuncio = (GregorianCalendar)annuncioUno.getDataOra().clone();

			oraAnnuncio.set(GregorianCalendar.MONTH, oraAnnuncio.get(GregorianCalendar.MONTH)+1);

			chatDAO.doSave(messaggioDue, utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), oraAnnuncio, 1, 0);

			chat = chatDAO.doSearchByEmail(utenteDue.getEmail()).get(0);

			assertTrue(chat.getMittenteMessaggioNonLetto() > 0);

			messaggio = manager.caricaMessaggi(utenteDue.getEmail(), utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra()).get(0);

			chat = chatDAO.doSearchByEmail(utenteUno.getEmail()).get(0);

			assertTrue(chat.getMittenteMessaggioNonLetto() == 0);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("fallito metodo caricaMessaggi");
		}
	}

	@Test
	public void iniziaNuovaChat() {

		OggettoBean annuncioDue = new OggettoBean();

		annuncioDue.setEmail(utenteDue.getEmail());

		annuncioDue.setNome("annuncioUnoTest");

		GregorianCalendar dataOraAnnuncioUno = new GregorianCalendar(2020, 10, 10, 10, 10, 10);

		annuncioDue.setDataOra(dataOraAnnuncioUno);

		annuncioDue.setCategoria("Veicoli");

		annuncioDue.setRegione("Campania");

		annuncioDue.setPrezzo(33);

		annuncioDue.setImmagine("immagine");

		annuncioDue.setDescrizione("descrizione");

		try {
			oggettoDAO.doSave(annuncioDue);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			fail("fallito inserimento annuncio");
		}

		GregorianCalendar oraAnnuncio = (GregorianCalendar)annuncioDue.getDataOra().clone();

		oraAnnuncio.set(GregorianCalendar.MONTH, oraAnnuncio.get(GregorianCalendar.MONTH)+1);


		try {
			manager.iniziaNuovaChat(utenteUno.getEmail(), utenteDue.getEmail(), annuncioDue.getNome(), oraAnnuncio);

			ArrayList<ChatBean> chat = chatDAO.doSearchByEmail(utenteUno.getEmail());

			assertEquals(chat.size(), 2);

			assertTrue(chat.get(0).getEmailMittente().equals(utenteUno.getEmail()));
			assertTrue(chat.get(0).getEmailDestinatario().equals(utenteDue.getEmail()));
			assertTrue(chat.get(0).getNomeAnnuncio().equals(annuncioDue.getNome()));
			System.out.println(chat.get(0).getDataOraAnnuncio());

			System.out.println(annuncioDue.getDataOra());

			assertTrue(chat.get(0).getDataOraAnnuncio().get(GregorianCalendar.YEAR) == annuncioDue.getDataOra().get(GregorianCalendar.YEAR));
			assertTrue(chat.get(0).getDataOraAnnuncio().get(GregorianCalendar.MONTH) == annuncioDue.getDataOra().get(GregorianCalendar.MONTH));
			assertTrue(chat.get(0).getDataOraAnnuncio().get(GregorianCalendar.DAY_OF_MONTH) == annuncioDue.getDataOra().get(GregorianCalendar.DAY_OF_MONTH));
			assertTrue(chat.get(0).getDataOraAnnuncio().get(GregorianCalendar.HOUR_OF_DAY) == annuncioDue.getDataOra().get(GregorianCalendar.HOUR_OF_DAY));
			assertTrue(chat.get(0).getDataOraAnnuncio().get(GregorianCalendar.MINUTE) == annuncioDue.getDataOra().get(GregorianCalendar.MINUTE));
			assertTrue(chat.get(0).getDataOraAnnuncio().get(GregorianCalendar.SECOND) == annuncioDue.getDataOra().get(GregorianCalendar.SECOND));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo iniziaNuovaChat fallito");
		}
	}

	@Test
	public void inviaMessaggio() {

		try {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			GregorianCalendar oraAnnuncio = (GregorianCalendar)annuncioUno.getDataOra().clone();

			oraAnnuncio.set(GregorianCalendar.MONTH, oraAnnuncio.get(GregorianCalendar.MONTH)+1);
			
			manager.inviaMessaggio("a quanto", utenteDue.getEmail(), utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), oraAnnuncio);

			MessaggioBean messaggio = recuperaMessaggi(utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra()).get(1);

			assertEquals(messaggio.getEmailMittenteChat(), utenteDue.getEmail());
			assertEquals(messaggio.getEmailDestinatarioChat(), utenteUno.getEmail());
			assertEquals(messaggio.getNomeAnnuncioChat(), annuncioUno.getNome());
			assertEquals(messaggio.getDescrizione(), "a quanto");


			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.YEAR) == annuncioUno.getDataOra().get(GregorianCalendar.YEAR));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.MONTH) == annuncioUno.getDataOra().get(GregorianCalendar.MONTH));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.DAY_OF_MONTH) == annuncioUno.getDataOra().get(GregorianCalendar.DAY_OF_MONTH));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.HOUR_OF_DAY) == annuncioUno.getDataOra().get(GregorianCalendar.HOUR_OF_DAY));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.MINUTE) == annuncioUno.getDataOra().get(GregorianCalendar.MINUTE));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.SECOND) == annuncioUno.getDataOra().get(GregorianCalendar.SECOND));

			ChatBean chat = chatDAO.doSearchByEmail(utenteUno.getEmail()).get(0);

			assertTrue(chat.getDestinatarioMessaggioNonLetto() > 0);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			manager.inviaMessaggio("100 euro", utenteUno.getEmail(), utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), oraAnnuncio);

			messaggio = recuperaMessaggi(utenteDue.getEmail(), utenteUno.getEmail(), annuncioUno.getNome(), annuncioUno.getDataOra()).get(2);

			assertEquals(messaggio.getEmailMittenteChat(), utenteDue.getEmail());
			assertEquals(messaggio.getEmailDestinatarioChat(), utenteUno.getEmail());
			assertEquals(messaggio.getNomeAnnuncioChat(), annuncioUno.getNome());
			assertEquals(messaggio.getDescrizione(), "100 euro");


			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.YEAR) == annuncioUno.getDataOra().get(GregorianCalendar.YEAR));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.MONTH) == annuncioUno.getDataOra().get(GregorianCalendar.MONTH));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.DAY_OF_MONTH) == annuncioUno.getDataOra().get(GregorianCalendar.DAY_OF_MONTH));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.HOUR_OF_DAY) == annuncioUno.getDataOra().get(GregorianCalendar.HOUR_OF_DAY));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.MINUTE) == annuncioUno.getDataOra().get(GregorianCalendar.MINUTE));
			assertTrue(messaggio.getDataOraAnnuncio().get(GregorianCalendar.SECOND) == annuncioUno.getDataOra().get(GregorianCalendar.SECOND));

			chat = chatDAO.doSearchByEmail(utenteDue.getEmail()).get(0);

			assertTrue(chat.getMittenteMessaggioNonLetto() > 0);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("metodo inviaMessaggio fallito");
		}


	}

	@Test
	public void notifica() {
		
		try {
		
			ChatBean chat = chatDAO.doSearchByEmail(utenteUno.getEmail()).get(0);	
			
			assertTrue(chat.getDestinatarioMessaggioNonLetto() == 1);
			
			assertTrue(manager.notifica(utenteUno.getEmail()));
			
			assertTrue(chat.getMittenteMessaggioNonLetto() == 0);

			assertTrue(manager.notifica(utenteDue.getEmail()) == false);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("metodo notifia fallito");
		}
	}

	@After
	public void stop() {

		try {
			utenteDao.removeByKey(utenteUno.getEmail());

			utenteDao.removeByKey(utenteDue.getEmail());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public ArrayList<MessaggioBean> recuperaMessaggi(String emailMittenteChat, String emailDestinatarioChat,
			String nomeAnnuncio, GregorianCalendar dataOraAnnuncio) {

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("SELECT * FROM progettois.MESSAGGIO WHERE email_mittente_chat = ? AND email_destinatario_chat = ? AND nome_annuncio = ? AND data_ora_annuncio = ? ORDER BY data_ora_invio_messaggio");

			ps.setString(1, emailMittenteChat);
			ps.setString(2, emailDestinatarioChat);
			ps.setString(3, nomeAnnuncio);

			int anno = dataOraAnnuncio.get(GregorianCalendar.YEAR);

			int mese = dataOraAnnuncio.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOraAnnuncio.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOraAnnuncio.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOraAnnuncio.get(GregorianCalendar.MINUTE);

			int secondi = dataOraAnnuncio.get(GregorianCalendar.SECOND);

			String data_ora_annuncio = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			ps.setString(4,data_ora_annuncio);

			ResultSet res = ps.executeQuery();

			ArrayList<MessaggioBean> listaMessaggi = new ArrayList<>();

			MessaggioBean messaggio;

			Date data;

			Time orario;

			GregorianCalendar data_ora;

			// Prendi il risultato
			while(res.next())
			{
				messaggio = new MessaggioBean();

				messaggio.setDataOraAnnuncio(dataOraAnnuncio);

				messaggio.setDescrizione((res.getString("descrizione")));

				messaggio.setEmailMittenteMessaggio(res.getString("email_mittente_messaggio"));

				messaggio.setEmailDestinatarioChat(res.getString("email_destinatario_chat"));

				messaggio.setEmailMittenteChat(res.getString("email_mittente_chat"));

				messaggio.setNomeAnnuncioChat(res.getString("nome_annuncio"));

				System.out.println("Metodo doSearchChatMessage: "+res.getString("data_ora_invio_messaggio"));

				data = res.getDate("data_ora_invio_messaggio");

				orario = res.getTime("data_ora_invio_messaggio");

				data_ora = new GregorianCalendar();

				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				messaggio.setDataOraInvioMessaggio(data_ora);

				listaMessaggi.add(messaggio);

			}
			return listaMessaggi;

		}catch(SQLException e1) {

			e1.printStackTrace();

			fail("metodo recuperaMessaggi è fallito");

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
}
