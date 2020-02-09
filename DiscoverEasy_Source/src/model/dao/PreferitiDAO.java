package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;

import model.beans.OggettoBean;

public class PreferitiDAO {

	public synchronized void deleteAll(String email) throws SQLException{

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("UPDATE progettois.PREFERITI SET eliminato = true WHERE email_utente = ?");
			ps.setString(1, email);
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
	
	public synchronized void doSave(String emailUtente, String emailVenditore, String nomeAnnuncio,
			GregorianCalendar dataOra) throws  SQLException{
	
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
			
			ps.setBoolean(6, false);
			
			boolean res;
			
			System.out.println( res = ps.execute());
			
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public synchronized Hashtable<String, OggettoBean> doSearchByEmail(String email_utente) throws SQLException{

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
				
				oggetto.setEmailPreferitiUtente(res.getString("email_utente"));
				
				listaOggetti.put(oggetto.getNome()+oggetto.getEmail()+data_ora.get(GregorianCalendar.YEAR)+
						data_ora.get(GregorianCalendar.MONTH)+data_ora.get(GregorianCalendar.DAY_OF_MONTH)+
						data_ora.get(GregorianCalendar.HOUR_OF_DAY)+data_ora.get(GregorianCalendar.MINUTE)+
						data_ora.get(GregorianCalendar.SECOND),oggetto);				
			}

			return listaOggetti;

		}finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}
	
	public void deleteByKey(String emailUtente, String emailVenditore, String nomeAnnuncio, GregorianCalendar dataOra) throws SQLException {
	
		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("UPDATE progettois.PREFERITI SET eliminato = true WHERE email_utente = ? and email_venditore = ? and nome_annuncio = ? and data_ora_annuncio = ? and eliminato = false");
			ps.setString(1, emailUtente);
			ps.setString(2, emailVenditore);
			ps.setString(3, nomeAnnuncio);

			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;

			ps.setString(4, data_ora);
			
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
