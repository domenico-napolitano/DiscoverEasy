package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.beans.utenteBean;

public class utenteDAO {

	public synchronized void doSave(utenteBean c) throws SQLException{
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

		} finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
	}

	public synchronized utenteBean doRetrieveByKey(String email){

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

	public synchronized void doChangePassword(String email, String password) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			utenteBean utente = new utenteBean(); 
			conn = DriverManagerConnectionPool.getConnection();
			ps = conn.prepareStatement("update progettois.UTENTE set pass = ? where email = ?");

			ps.setString(1, password);

			ps.setString(2, email);

			ps.executeUpdate();

			System.out.println("password modificata");

		}finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(conn);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public synchronized void removeByKey(String email) throws SQLException{

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("DELETE from progettois.UTENTE where email = ?");

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

	public synchronized void deleteByKey(String email) throws SQLException{

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("UPDATE progettois.UTENTE SET eliminato = true WHERE email = ? and email_ban is null");

			ps.setString(1, email);
			ps.executeUpdate();

		} finally{

			try {

				ps.close();
				DriverManagerConnectionPool.releaseConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
	}

	public synchronized void banByKey(String emailGestore, String emailBan) throws SQLException{

		Connection con = null;
		PreparedStatement ps = null;/*prepari la stringa da dare al database*/

		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement("UPDATE progettois.UTENTE SET email_ban = ? WHERE email = ? and eliminato = false");

			ps.setString(1, emailGestore);
			ps.setString(2, emailBan);

			ps.executeUpdate();

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
