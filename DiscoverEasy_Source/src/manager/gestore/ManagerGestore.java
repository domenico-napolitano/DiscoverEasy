package manager.gestore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.beans.OggettoBean;
import model.dao.OggettoDAO;
import model.dao.PreferitiDAO;
import model.dao.utenteDAO;

public class ManagerGestore {

	public void banAnnuncio(String email_gestore,String nome, String email_annuncio, GregorianCalendar data_ora) throws SQLException {
		
		nome = nome.trim();
		email_annuncio = email_annuncio.trim();

		OggettoDAO oggettodao = new OggettoDAO();

		oggettodao.banByKey(email_gestore, email_annuncio, nome, data_ora);

		System.out.println("oggettoEliminato");
		
	}
	
	public void banAccount(String emailGestore, String emailBan) throws SQLException {
	
		/*Questo metodo elimina tutti i preferiti, setta tutti gli annunci a ban e l'account a ban dell'utente,
		  con email che è uguale al parametro in input email*/

		ArrayList<OggettoBean> listaOggetti = new OggettoDAO().doSearchByEmail(emailBan);

		new PreferitiDAO().deleteAll(emailBan);

		OggettoDAO oggettoDAO = new OggettoDAO();

		OggettoBean annuncio;

		for(int i=0; i< listaOggetti.size(); i++) {

			annuncio = listaOggetti.get(i);
			
			System.out.println("data:  "+annuncio.getDataOra());

			System.out.println("nome:  "+annuncio.getNome());

			System.out.println("email:  "+annuncio.getEmail());

			oggettoDAO.banByKey(emailGestore, annuncio.getEmail(), annuncio.getNome(), annuncio.getDataOra());
		}

		new utenteDAO().banByKey(emailGestore, emailBan);
	}
}
