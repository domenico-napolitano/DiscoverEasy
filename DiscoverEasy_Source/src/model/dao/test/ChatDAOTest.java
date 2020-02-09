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
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.beans.ChatBean;
import model.beans.MessaggioBean;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.ChatDAO;
import model.dao.DriverManagerConnectionPool;

public class ChatDAOTest {

	private static utenteBean utenteUno;

	private static utenteBean utenteDue;

	private static OggettoBean annuncioUno;

	private static ChatBean chatUnoDue;

	private static ChatDAO chatDAO = new ChatDAO();


	@Before
	public void start() {

		utenteUno = new utenteBean();

		utenteDue = new utenteBean();

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

		salvaUtente(utenteDue);


		annuncioUno = new OggettoBean();

		annuncioUno.setEmail(utenteUno.getEmail());

		annuncioUno.setNome("annuncioUnoTest");

		GregorianCalendar dataOraAnnuncioUno = new GregorianCalendar(2020, 10, 10, 10, 10, 10);

		annuncioUno.setDataOra(dataOraAnnuncioUno);

		annuncioUno.setCategoria("Veicoli");

		annuncioUno.setRegione("Campania");

		annuncioUno.setPrezzo(33);

		annuncioUno.setEliminato(false);

		annuncioUno.setImmagine("immagine");

		annuncioUno.setDescrizione("descrizione");

		salvaAnnuncio(annuncioUno);

		chatUnoDue = new ChatBean();

		chatUnoDue.setEmailMittente(utenteDue.getEmail());

		chatUnoDue.setEmailDestinatario(utenteUno.getEmail());

		chatUnoDue.setNomeAnnuncio(annuncioUno.getNome());

		chatUnoDue.setDataOraAnnuncio(new GregorianCalendar(2020, 10, 10, 10, 10, 10));

		salvaChat(chatUnoDue);

	}

	@Test
	public void doSaveTest() {

		MessaggioBean messaggio = new MessaggioBean();

		chatUnoDue.getDataOraAnnuncio().set(GregorianCalendar.MONTH, 11);
		
		messaggio.setDescrizione("descrizione");

		messaggio.setEmailMittenteMessaggio(utenteUno.getEmail());

		messaggio.setEmailMittenteChat(chatUnoDue.getEmailMittente());

		messaggio.setEmailDestinatarioChat(chatUnoDue.getEmailDestinatario());

		messaggio.setNomeAnnuncioChat(chatUnoDue.getNomeAnnuncio());

		messaggio.setDataOraAnnuncio(chatUnoDue.getDataOraAnnuncio());

		System.out.println(chatUnoDue.getDataOraAnnuncio());

		try {
			
			chatDAO.doSave(messaggio, chatUnoDue.getEmailMittente(), chatUnoDue.getEmailDestinatario(), chatUnoDue.getNomeAnnuncio(),chatUnoDue.getDataOraAnnuncio(), 0, 1);

			System.out.println(chatUnoDue.getDataOraAnnuncio().get(GregorianCalendar.MONTH));
			
			assertEquals(messaggio, recuperaMessaggio(chatUnoDue.getEmailMittente(), chatUnoDue.getEmailDestinatario(), chatUnoDue.getNomeAnnuncio(), chatUnoDue.getDataOraAnnuncio()).get(0));

			ChatBean chatDatabase = caricaChat(utenteUno.getEmail()).get(0);

			assertEquals(chatDatabase.getDestinatarioMessaggioNonLetto(), 1);

			assertEquals(chatDatabase.getMittenteMessaggioNonLetto(), 0);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("chat non salvata");
		}

	}

	@Test
	public void doSearchByEmail() {

		try {

			String emailUtente = utenteUno.getEmail();

			ChatBean chat = chatDAO.doSearchByEmail(emailUtente).get(0);


			assertTrue(chat.getEmailDestinatario().equals(emailUtente) || chat.getEmailMittente().equals(emailUtente));


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("doSearchByEmail fallito");
		}

	}

	@Test
	public void doSearchChatMessage() {

		try {

			MessaggioBean messaggioUno = new MessaggioBean();

			messaggioUno.setDescrizione("descrizione");

			messaggioUno.setEmailMittenteMessaggio(utenteUno.getEmail());

			MessaggioBean messaggioDue = new MessaggioBean();

			messaggioDue.setDescrizione("descrizione");

			messaggioDue.setEmailMittenteMessaggio(utenteDue.getEmail());

			messaggioUno = salvaMessaggio(messaggioUno, chatUnoDue.getEmailMittente(), chatUnoDue.getEmailDestinatario(), 
					chatUnoDue.getNomeAnnuncio(), chatUnoDue.getDataOraAnnuncio(), 0, 1);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			messaggioDue = salvaMessaggio(messaggioDue, chatUnoDue.getEmailMittente(), chatUnoDue.getEmailDestinatario(), 
					chatUnoDue.getNomeAnnuncio(), chatUnoDue.getDataOraAnnuncio(), 1, 1);

			ArrayList<MessaggioBean> messaggi = chatDAO.doSearchChatMessage(chatUnoDue.getEmailMittente(), chatUnoDue.getEmailDestinatario(), chatUnoDue.getNomeAnnuncio(),
					chatUnoDue.getDataOraAnnuncio(),0,true);

			
			assertEquals(messaggioUno, messaggi.get(0));

			assertEquals(messaggioDue, messaggi.get(1));

			ChatBean chat = caricaChat(messaggioUno.getEmailMittenteMessaggio()).get(0);

			assertTrue(chat.getMittenteMessaggioNonLetto() == 0 && chat.getDestinatarioMessaggioNonLetto() == 1);

			chatDAO.doSearchChatMessage(chatUnoDue.getEmailMittente(), chatUnoDue.getEmailDestinatario(), chatUnoDue.getNomeAnnuncio(),
					chatUnoDue.getDataOraAnnuncio(),0,false);
			
			assertEquals(messaggioUno, messaggi.get(0));

			assertEquals(messaggioDue, messaggi.get(1));

			chat = caricaChat(messaggioUno.getEmailMittenteMessaggio()).get(0);

			assertTrue(chat.getDestinatarioMessaggioNonLetto() == 0 && chat.getMittenteMessaggioNonLetto()== 0);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail("non carica messaggi della chat");
		}
	}

	@Test
	public void iniziaNuovaChatTest() {
		
		OggettoBean annuncio = new OggettoBean();

		annuncio.setEmail(utenteDue.getEmail());

		annuncio.setNome("annuncioUnoTest");

		GregorianCalendar dataOraAnnuncioUno = new GregorianCalendar(2020, 10, 10, 10, 10, 10);

		annuncio.setDataOra(dataOraAnnuncioUno);

		annuncio.setCategoria("Veicoli");

		annuncio.setRegione("Campania");

		annuncio.setPrezzo(33);

		annuncio.setEliminato(false);

		annuncio.setImmagine("immagine");

		annuncio.setDescrizione("descrizione");

		salvaAnnuncio(annuncio);
		
		GregorianCalendar oraAnnuncio = (GregorianCalendar)dataOraAnnuncioUno.clone();
		
		oraAnnuncio.set(GregorianCalendar.MONTH, oraAnnuncio.get(GregorianCalendar.MONTH)+1);
		
		try {
			chatDAO.iniziaNuovaChat(utenteUno.getEmail(), utenteDue.getEmail(), annuncio.getNome(), oraAnnuncio);
		
			ChatBean chat = caricaChat(utenteDue.getEmail()).get(0);
			
			
			assertTrue(chat.getDataOraAnnuncio().equals(dataOraAnnuncioUno) && chat.getEmailMittente().equals(utenteUno.getEmail()) &&
					chat.getEmailDestinatario().equals(utenteDue.getEmail()) && chat.getNomeAnnuncio().equals(annuncio.getNome()));
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			fail("metodo inizia nuova chat test fallito");
		}
	}


	@After
	public void stop() {


		eliminaUtente(utenteUno);

		eliminaUtente(utenteDue);

		eliminaAnnuncio(annuncioUno);

		eliminaChat(chatUnoDue);
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

	public void salvaAnnuncio (OggettoBean annuncio) {

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

			ps.setBoolean(9, false);

			annuncio.setEliminato(false);

			ps.setString(10, null);

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


	public void eliminaAnnuncio (OggettoBean annuncio) {

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("DELETE from progettois.ANNUNCIO where email_venditore = ? AND nome = ? AND data_ora = ?");

			ps.setString(1, annuncio.getEmail());
			ps.setString(2, annuncio.getNome());
			int anno = annuncio.getDataOra().get(GregorianCalendar.YEAR);

			int mese = annuncio.getDataOra().get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = annuncio.getDataOra().get(GregorianCalendar.DAY_OF_MONTH);

			int ora = annuncio.getDataOra().get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = annuncio.getDataOra().get(GregorianCalendar.MINUTE);

			int secondi = annuncio.getDataOra().get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			ps.setString(3,data_ora);

			ps.execute();

		} catch(SQLException e) {

			e.printStackTrace();

			fail("elimina annuncio metodo");

		} finally{


			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

				fail("elimina annuncio metodo");

			}

		}
	}

	public void salvaChat (ChatBean chat) {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.CHAT values (?, ?, ?, ?, ?, ?)");

			ps.setString(1, chat.getEmailMittente());

			//System.out.println("Prova"+emailMittenteChat+"Prova");

			ps.setString(2, chat.getEmailDestinatario());

			//System.out.println("Prova"+emailDestinatarioChat+"Prova");

			ps.setString(3, chat.getNomeAnnuncio());

			//System.out.println("Prova"+nomeAnnuncio+"Prova");

			int annoAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.YEAR);

			int meseAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giornoAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.DAY_OF_MONTH);

			int oraAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.HOUR_OF_DAY);

			int minutiAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.MINUTE);

			int secondiAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.SECOND);

			String data_ora_annuncio = annoAnnuncio+"-"+meseAnnuncio+"-"+giornoAnnuncio+" "+oraAnnuncio+":"+minutiAnnuncio+":"+secondiAnnuncio;

			ps.setString(4, data_ora_annuncio);

			//	System.out.println("Prova"+data_ora_annuncio+"Prova");

			ps.setInt(5,0);

			ps.setInt(6,0);

			ps.execute();

		} catch(SQLException e) {

			e.printStackTrace();

			fail("salva chat metodo");

		}
		finally{


			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

				fail("salva chat metodo");

			}

		}
	}


	public void eliminaChat(ChatBean chat) {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("DELETE FROM progettois.CHAT WHERE email_mittente = ? AND email_destinatario = ? AND nome_annuncio = ? AND data_ora_annuncio = ?");

			ps.setString(1, chat.getEmailMittente());

			//System.out.println("Prova"+emailMittenteChat+"Prova");

			ps.setString(2, chat.getEmailDestinatario());

			//System.out.println("Prova"+emailDestinatarioChat+"Prova");

			ps.setString(3, chat.getNomeAnnuncio());

			//System.out.println("Prova"+nomeAnnuncio+"Prova");

			int annoAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.YEAR);

			int meseAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giornoAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.DAY_OF_MONTH);

			int oraAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.HOUR_OF_DAY);

			int minutiAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.MINUTE);

			int secondiAnnuncio = chat.getDataOraAnnuncio().get(GregorianCalendar.SECOND);

			String data_ora_annuncio = annoAnnuncio+"-"+meseAnnuncio+"-"+giornoAnnuncio+" "+oraAnnuncio+":"+minutiAnnuncio+":"+secondiAnnuncio;

			ps.setString(4, data_ora_annuncio);

			//	System.out.println("Prova"+data_ora_annuncio+"Prova");

			ps.execute();

		} catch(SQLException e) {

			e.printStackTrace();

			fail("elimina chat metodo");

		}
		finally{


			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

				fail("elimina chat metodo");
			}

		}
	}


	public ArrayList<MessaggioBean>  recuperaMessaggio(String emailMittenteChat, String emailDestinatarioChat,
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

			int mese = dataOraAnnuncio.get(GregorianCalendar.MONTH);/*perchè gennaio parte da 0 invece che da 1*/

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

				messaggio.setDescrizione((res.getString("descrizione")));

				messaggio.setEmailMittenteMessaggio(res.getString("email_mittente_messaggio"));

				messaggio.setEmailDestinatarioChat(res.getString("email_destinatario_chat"));

				messaggio.setEmailMittenteChat(res.getString("email_mittente_chat"));

				messaggio.setNomeAnnuncioChat(res.getString("nome_annuncio"));

				data = res.getDate("data_ora_annuncio");

				orario = res.getTime("data_ora_annuncio");

				data_ora = new GregorianCalendar();

				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				messaggio.setDataOraAnnuncio(data_ora);
				
				listaMessaggi.add(messaggio);

			}

			return listaMessaggi;

		} catch(SQLException e) {

			e.printStackTrace();

			fail("fallito recupera messaggio");
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


	public synchronized ArrayList<ChatBean> caricaChat (String emailUtente){

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select DISTINCT * from progettois.CHAT INNER JOIN progettois.UTENTE INNER JOIN progettois.datiDestinatario INNER JOIN progettois.ANNUNCIO WHERE CHAT.email_mittente = UTENTE.email AND CHAT.email_destinatario = datiDestinatario.email AND ANNUNCIO.nome = CHAT.nome_annuncio AND ANNUNCIO.email_venditore = CHAT.email_destinatario AND ANNUNCIO.data_ora = CHAT.data_ora_annuncio AND (email_mittente = ? OR email_destinatario = ?)");
			ps.setString(1, emailUtente);
			ps.setString(2, emailUtente);

			ResultSet res = ps.executeQuery();

			ArrayList<ChatBean> listaChat = new ArrayList<>();

			ChatBean chat;

			Date data;

			Time orario;

			GregorianCalendar data_ora;

			// Prendi il risultato
			while(res.next())
			{
				chat = new ChatBean();

				chat.setEmailMittente(res.getString("email_mittente"));

				chat.setEmailDestinatario(res.getString("email_destinatario"));

				chat.setNomeAnnuncio(res.getString("nome_annuncio"));

				chat.setNomeVenditoreAnnuncio(res.getString("nome_destinatario"));

				chat.setNomeMittenteChat(res.getString("nome_venditore"));

				chat.setImmagine(res.getString("immagine"));

				chat.setDestinatarioMessaggioNonLetto(res.getInt("destinatario_messaggio_non_letto"));

				chat.setMittenteMessaggioNonLetto(res.getInt("mittente_messaggio_non_letto"));

				data = res.getDate("data_ora");

				orario = res.getTime("data_ora");

				data_ora = new GregorianCalendar();

				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				chat.setDataOraAnnuncio(data_ora);

				listaChat.add(chat);

			}

			return listaChat;

		}catch(SQLException e1) {

			e1.printStackTrace();

			fail("fallito metodo carica chat");
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

	public MessaggioBean salvaMessaggio(MessaggioBean messaggio, String emailMittenteChat, String emailDestinatarioChat,
			String nomeAnnuncio, GregorianCalendar dataOraAnnuncio, int mittenteMessaggioNonLetto,
			int destinatarioMessaggioNonLetto) {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.MESSAGGIO values (?, ?, ?, ?, ?, ?, ?)");

			GregorianCalendar dataOraInvioMessaggio = new GregorianCalendar();

			messaggio.setDataOraInvioMessaggio(dataOraInvioMessaggio);

			int anno = dataOraInvioMessaggio.get(GregorianCalendar.YEAR);

			int mese = dataOraInvioMessaggio.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOraInvioMessaggio.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOraInvioMessaggio.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOraInvioMessaggio.get(GregorianCalendar.MINUTE);

			int secondi = dataOraInvioMessaggio.get(GregorianCalendar.SECOND);

			String data_ora_invio_messaggio = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			ps.setString(1, data_ora_invio_messaggio);

			System.out.println("Prova"+data_ora_invio_messaggio+"Prova");

			messaggio.setEmailMittenteChat(emailMittenteChat);

			ps.setString(2, emailMittenteChat);

			System.out.println("Prova"+emailMittenteChat+"Prova");

			messaggio.setEmailDestinatarioChat(emailDestinatarioChat);

			ps.setString(3, emailDestinatarioChat);

			System.out.println("Prova"+emailDestinatarioChat+"Prova");

			messaggio.setNomeAnnuncioChat(nomeAnnuncio);

			ps.setString(4, nomeAnnuncio);

			System.out.println("Prova"+nomeAnnuncio+"Prova");

			int annoAnnuncio = dataOraAnnuncio.get(GregorianCalendar.YEAR);

			int meseAnnuncio = dataOraAnnuncio.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giornoAnnuncio = dataOraAnnuncio.get(GregorianCalendar.DAY_OF_MONTH);

			int oraAnnuncio = dataOraAnnuncio.get(GregorianCalendar.HOUR_OF_DAY);

			int minutiAnnuncio = dataOraAnnuncio.get(GregorianCalendar.MINUTE);

			int secondiAnnuncio = dataOraAnnuncio.get(GregorianCalendar.SECOND);

			messaggio.setDataOraAnnuncio(dataOraAnnuncio);

			String data_ora_annuncio = annoAnnuncio+"-"+meseAnnuncio+"-"+giornoAnnuncio+" "+oraAnnuncio+":"+minutiAnnuncio+":"+secondiAnnuncio;

			ps.setString(5, data_ora_annuncio);

			System.out.println("Prova"+data_ora_annuncio+"Prova");

			ps.setString(6, messaggio.getDescrizione());

			System.out.println("Prova"+ messaggio.getDescrizione()+"Prova");

			ps.setString(7, messaggio.getEmailMittenteMessaggio().trim());

			System.out.println("Prova"+messaggio.getEmailMittenteMessaggio()+"Prova");

			ps.execute();

			ps = con.prepareStatement("UPDATE progettois.CHAT SET mittente_messaggio_non_letto = ?, destinatario_messaggio_non_letto = ? WHERE email_mittente = ? AND email_destinatario = ? AND nome_annuncio = ? AND data_ora_annuncio = ?");

			ps.setInt(1, mittenteMessaggioNonLetto);

			ps.setInt(2, destinatarioMessaggioNonLetto);

			ps.setString(3, emailMittenteChat);

			ps.setString(4, emailDestinatarioChat);

			ps.setString(5, nomeAnnuncio);

			ps.setString(6, data_ora_annuncio);

			ps.execute();

			return messaggio;

		} catch(SQLException e1) {

			e1.printStackTrace();

			fail("metodo salva messaggio fallito");

		}finally{


			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return null;
	}

}








