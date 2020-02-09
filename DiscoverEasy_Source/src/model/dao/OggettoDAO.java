package model.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.beans.OggettoBean;

public class OggettoDAO {

	public synchronized OggettoBean doSave(OggettoBean c) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("INSERT INTO progettois.ANNUNCIO value(?,?,?,?,?,?,?,?,?,?)");

			ps.setString(1, c.getNome().trim());
			ps.setString(2, c.getImmagine());
			ps.setString(3, c.getDescrizione());
			ps.setString(4, c.getCategoria());
			ps.setString(5, c.getRegione());
			ps.setInt(6, c.getPrezzo());
			ps.setString(7, c.getEmail().trim());

			GregorianCalendar dataOra = new GregorianCalendar();

			c.setDataOra(dataOra);
			
			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;
			
			ps.setString(8, data_ora);
			
			ps.setBoolean(9, false);

			c.setEliminato(false);
			
			ps.setString(10, null);
			
			ps.execute();
			
		
		} finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		
		
		
		return c;
	}
	
	

	public synchronized ArrayList<OggettoBean> doSearch(String nome, String regione, String categoria){

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			ArrayList<OggettoBean> listaoggetti = new ArrayList<OggettoBean>();
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore=UTENTE.email and nome LIKE ? and categoria = ? and regione = ? and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");
			nome = nome.trim();
			while((nome.indexOf("  ")) != -1) /* ha messo due spazi e non uno per togliere gli spazi multipli perchè se stava uno spazio si univa la stringa*/
				nome=nome.replace("  ", " ");
			ps.setString(1, "%" + nome.substring(1) + "%");
			ps.setString(2, categoria);
			ps.setString(3, regione);
			ResultSet res = ps.executeQuery();

			Date data;

			Time orario;

			GregorianCalendar data_ora;

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

				data_ora = new GregorianCalendar();
				
				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				oggetto.setDataOra(data_ora);

				listaoggetti.add(oggetto);
			}

			return listaoggetti;


		} catch (SQLException e) {

			e.printStackTrace();

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

	public synchronized ArrayList<OggettoBean>doSearchNomeRegione(String nome, String regione){

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			ArrayList<OggettoBean> listaoggetti = new ArrayList<OggettoBean>();
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore=UTENTE.email and nome LIKE ? and regione = ?  and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");
			nome = nome.trim();
			while((nome.indexOf("  ")) != -1)
				nome=nome.replace("  ", " ");
			ps.setString(1, "%" + nome.substring(1) + "%");
			ps.setString(2, regione);
			ResultSet res = ps.executeQuery();

			Date data;

			Time orario;

			GregorianCalendar data_ora;
			
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

				data_ora = new GregorianCalendar();
				
				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				oggetto.setDataOra(data_ora);
				
				listaoggetti.add(oggetto);		
			}

			return listaoggetti;


		} catch (SQLException e) {

			e.printStackTrace();

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


	public synchronized ArrayList<OggettoBean> doSearchNomeCategoria(String nome, String categoria){

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			ArrayList<OggettoBean> listaoggetti = new ArrayList<OggettoBean>(); 
			
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore=UTENTE.email and nome LIKE ? and categoria = ? and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");

			nome = nome.trim();
			
			while((nome.indexOf("  ")) != -1)
				nome=nome.replace("  ", " ");
			ps.setString(1, "%" + nome.substring(1) + "%");
			ps.setString(2, categoria);
			ResultSet res = ps.executeQuery();

			Date data;

			Time orario;

			GregorianCalendar data_ora ;
			
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

				data_ora = new GregorianCalendar();
				
				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				oggetto.setDataOra(data_ora);
								
				listaoggetti.add(oggetto);
			}

			return listaoggetti;



		} catch (SQLException e) {

			e.printStackTrace();

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


	public synchronized ArrayList<OggettoBean> doSearchNome(String nome){

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			ArrayList<OggettoBean> listaoggetti = new ArrayList<OggettoBean>();
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore=UTENTE.email and nome LIKE ?  and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");
			
			nome = nome.trim();
			while((nome.indexOf("  ")) != -1)
				nome=nome.replace("  ", " ");
			ps.setString(1, "%" + nome.substring(1) + "%");
			ResultSet res = ps.executeQuery();

			Date data;

			Time orario;

			GregorianCalendar data_ora;

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
				System.out.println("ci sono\n"+oggetto);
				
				data = res.getDate("data_ora");

				orario = res.getTime("data_ora");

				data_ora = new GregorianCalendar();
				
				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());

				oggetto.setDataOra(data_ora);

				listaoggetti.add(oggetto);
				
			}
			return listaoggetti;


		} catch (SQLException e) {

			e.printStackTrace();

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

	public synchronized OggettoBean doSearchByKey(String nome, String email_venditore, GregorianCalendar dataOra) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore = UTENTE.email and nome = ? and email_venditore = ? and data_ora = ?  and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");
			
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

	public synchronized ArrayList<OggettoBean> doSearchByEmail(String email_venditore) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("select * from progettois.ANNUNCIO inner join progettois.UTENTE where ANNUNCIO.email_venditore=UTENTE.email and email_venditore = ?  and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");
			ps.setString(1, email_venditore);
			ResultSet res = ps.executeQuery();

			ArrayList<OggettoBean> listaOggetti = new ArrayList<>();

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
				oggetto.setNumero_proprietario(res.getString("telefono"));
				oggetto.setCategoria(res.getString("categoria"));

				data = res.getDate("data_ora");

				orario = res.getTime("data_ora");

				data_ora = new GregorianCalendar();
				
				data_ora.setTime(data);

				data_ora.set(GregorianCalendar.HOUR_OF_DAY, orario.toLocalTime().getHour());

				data_ora.set(GregorianCalendar.MINUTE, orario.toLocalTime().getMinute());

				data_ora.set(GregorianCalendar.SECOND, orario.toLocalTime().getSecond());
				
				oggetto.setDataOra(data_ora);
								
				listaOggetti.add(oggetto);
				
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


	public synchronized void banByKey(String email_gestore, String email_annuncio, String name, GregorianCalendar dataOra) throws SQLException {
		
		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("UPDATE progettois.ANNUNCIO SET email_ban = ? WHERE email_venditore = ? AND nome = ? AND data_ora = ? and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");
			ps.setString(1, email_gestore);
			ps.setString(2, email_annuncio);
			ps.setString(3, name);
						
			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;
			
			ps.setString(4,data_ora);
			
			ps.executeUpdate();

		}finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public synchronized void deleteByKey(String email, String name, GregorianCalendar dataOra) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("UPDATE progettois.ANNUNCIO SET eliminato = true WHERE email_venditore = ? AND nome = ? AND data_ora = ? and ANNUNCIO.eliminato = false and ANNUNCIO.email_ban is null");
			ps.setString(1, email);
			ps.setString(2, name);
			
			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;
			
			ps.setString(3,data_ora);
			
			ps.executeUpdate();

		}finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	
public synchronized void removeByKey(String email, String name, GregorianCalendar dataOra) throws SQLException{
		
		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("DELETE from progettois.ANNUNCIO where email_venditore = ? AND nome = ? AND data_ora = ?");

			ps.setString(1, email);
			ps.setString(2, name);
			int anno = dataOra.get(GregorianCalendar.YEAR);

			int mese = dataOra.get(GregorianCalendar.MONTH)+1;/*perchè gennaio parte da 0 invece che da 1*/

			int giorno = dataOra.get(GregorianCalendar.DAY_OF_MONTH);

			int ora = dataOra.get(GregorianCalendar.HOUR_OF_DAY);

			int minuti = dataOra.get(GregorianCalendar.MINUTE);

			int secondi = dataOra.get(GregorianCalendar.SECOND);

			String data_ora = anno+"-"+mese+"-"+giorno+" "+ora+":"+minuti+":"+secondi;
			
			ps.setString(3,data_ora);
			
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
