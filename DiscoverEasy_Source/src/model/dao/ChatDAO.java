package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.beans.ChatBean;
import model.beans.MessaggioBean;

public class ChatDAO {

	public synchronized ArrayList<ChatBean> doSearchByEmail(String emailUtente) throws SQLException{

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

		}finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public synchronized ArrayList<MessaggioBean> doSearchChatMessage(String emailMittenteChat, String emailDestinatarioChat,
			String nomeAnnuncio, GregorianCalendar dataOraAnnuncio, int settaMessaggioNonLetto,
			boolean mittenteMessaggioNonLetto) throws SQLException{

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

			if(mittenteMessaggioNonLetto == true) {
				ps = conn.prepareStatement("UPDATE progettois.CHAT SET mittente_messaggio_non_letto = ? WHERE email_mittente = ? AND email_destinatario = ? AND nome_annuncio = ? AND data_ora_annuncio = ?");
			}
			else
			{
				ps = conn.prepareStatement("UPDATE progettois.CHAT SET destinatario_messaggio_non_letto = ? WHERE email_mittente = ? AND email_destinatario = ? AND nome_annuncio = ? AND data_ora_annuncio = ?");
			}
			ps.setInt(1, settaMessaggioNonLetto);

			ps.setString(2, emailMittenteChat);

			ps.setString(3, emailDestinatarioChat);

			ps.setString(4, nomeAnnuncio);

			ps.setString(5, data_ora_annuncio);

			ps.execute();

			return listaMessaggi;

		}finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public synchronized void iniziaNuovaChat(String emailMittenteChat, String emailDestinatarioChat,
			String nomeAnnuncio, GregorianCalendar dataOraAnnuncio) throws SQLException{

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.CHAT values (?, ?, ?, ?, ?, ?)");

			ps.setString(1, emailMittenteChat);

			System.out.println("Prova"+emailMittenteChat+"Prova");

			ps.setString(2, emailDestinatarioChat);

			System.out.println("Prova"+emailDestinatarioChat+"Prova");

			ps.setString(3, nomeAnnuncio);

			System.out.println("Prova"+nomeAnnuncio+"Prova");

			int annoAnnuncio = dataOraAnnuncio.get(GregorianCalendar.YEAR);

			int meseAnnuncio = dataOraAnnuncio.get(GregorianCalendar.MONTH);/*perchè gennaio parte da 0 invece che da 1*/

			int giornoAnnuncio = dataOraAnnuncio.get(GregorianCalendar.DAY_OF_MONTH);

			int oraAnnuncio = dataOraAnnuncio.get(GregorianCalendar.HOUR_OF_DAY);

			int minutiAnnuncio = dataOraAnnuncio.get(GregorianCalendar.MINUTE);

			int secondiAnnuncio = dataOraAnnuncio.get(GregorianCalendar.SECOND);

			String data_ora_annuncio = annoAnnuncio+"-"+meseAnnuncio+"-"+giornoAnnuncio+" "+oraAnnuncio+":"+minutiAnnuncio+":"+secondiAnnuncio;

			ps.setString(4, data_ora_annuncio);

			System.out.println("Prova"+data_ora_annuncio+"Prova");

			ps.setInt(5,0);

			ps.setInt(6,0);

			ps.execute();

		} finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
	}
	
	
	public synchronized void doSave(MessaggioBean messaggio, String emailMittenteChat, String emailDestinatarioChat,
			String nomeAnnuncio, GregorianCalendar dataOraAnnuncio, int mittenteMessaggioNonLetto,
			int destinatarioMessaggioNonLetto) throws SQLException{

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.MESSAGGIO values (?, ?, ?, ?, ?, ?, ?)");

			GregorianCalendar dataOraInvioMessaggio = new GregorianCalendar();

			int anno = dataOraInvioMessaggio.get(GregorianCalendar.YEAR);

			int mese = dataOraInvioMessaggio.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOraInvioMessaggio.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOraInvioMessaggio.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOraInvioMessaggio.get(GregorianCalendar.MINUTE);

			int secondi = dataOraInvioMessaggio.get(GregorianCalendar.SECOND);

			String data_ora_invio_messaggio = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			ps.setString(1, data_ora_invio_messaggio);

			System.out.println("Prova"+data_ora_invio_messaggio+"Prova");

			ps.setString(2, emailMittenteChat);

			System.out.println("Prova"+emailMittenteChat+"Prova");

			ps.setString(3, emailDestinatarioChat);

			System.out.println("Prova"+emailDestinatarioChat+"Prova");

			ps.setString(4, nomeAnnuncio);

			System.out.println("Prova"+nomeAnnuncio+"Prova");

			int annoAnnuncio = dataOraAnnuncio.get(GregorianCalendar.YEAR);

			int meseAnnuncio = dataOraAnnuncio.get(GregorianCalendar.MONTH);/*perchè gennaio parte da 0 invece che da 1*/

			int giornoAnnuncio = dataOraAnnuncio.get(GregorianCalendar.DAY_OF_MONTH);

			int oraAnnuncio = dataOraAnnuncio.get(GregorianCalendar.HOUR_OF_DAY);

			int minutiAnnuncio = dataOraAnnuncio.get(GregorianCalendar.MINUTE);

			int secondiAnnuncio = dataOraAnnuncio.get(GregorianCalendar.SECOND);

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

		} finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
	}
}
