package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import model.beans.OggettoBean;
import model.dao.OggettoDAO;
import model.dao.PreferitiDAO;

public class ManagerRicerca {

	public OggettoBean ricercaAnnuncioPerChiave (String nomeAnnuncio, String emailVenditore,
			GregorianCalendar dataOraAnnuncio) throws SQLException {
		
		return  new OggettoDAO().doSearchByKey(nomeAnnuncio, emailVenditore, dataOraAnnuncio);
	}
	
	public ArrayList<OggettoBean> ricercaAnnunci(String regione, String categoria, String barraRicerca) {
		
		OggettoDAO oggettodao = new OggettoDAO();

		
		ArrayList<OggettoBean> risultatiRicerca;

		barraRicerca = barraRicerca.trim();
		if(barraRicerca.equals("") || barraRicerca == null)
			barraRicerca = "a";

		
		if(regione.equalsIgnoreCase("Tutta Italia") && categoria.equalsIgnoreCase("Tutte le categorie"))
		{
			System.out.println("tutti e due");

			risultatiRicerca = oggettodao.doSearchNome(barraRicerca);
		}
		else {

			if(regione.equalsIgnoreCase("Tutta Italia"))/*Ricerca solo per categoria*/
			{
				System.out.println("Tutta Italia");
				risultatiRicerca = oggettodao.doSearchNomeCategoria(barraRicerca, categoria);
				
			}
			else /*Ricerca solo per Regione*/ 
			{
				if(categoria.equalsIgnoreCase("Tutte le categorie"))
				{     
					System.out.println("Tutte le categorie");
					risultatiRicerca = oggettodao.doSearchNomeRegione(barraRicerca, regione);
				}
				else
				{
					System.out.println("tutti e 3");
					risultatiRicerca = oggettodao.doSearch(barraRicerca, regione, categoria);
				}
			}
		}
	
	
		return risultatiRicerca;
	}
	
	public ArrayList<OggettoBean> caricaAnnunci(String email) throws SQLException{

		ArrayList<OggettoBean> listaOggetti = new OggettoDAO().doSearchByEmail(email);
		
		return listaOggetti;
	}
	
	
	public Hashtable<String, OggettoBean> mergePreferitiSessioneDatabase (Hashtable<String, OggettoBean> insiemeSessione, String email_utente ) throws SQLException{
		
		/*Questo metodo unisce i preferiti della sessione dell'utente con quelli salvati nel database, una volta effettuato
		  il login */
		
		PreferitiDAO preferitiDAO = new PreferitiDAO();
		
		Hashtable<String, OggettoBean> insiemeDB = preferitiDAO.doSearchByEmail(email_utente);
		
	    Set<String> keys = insiemeSessione.keySet();

	    Iterator<String> scorriInsiemeSessione = keys.iterator();
		
		OggettoBean annuncio;
		
		while(scorriInsiemeSessione.hasNext()) {
			
			annuncio = insiemeSessione.get(scorriInsiemeSessione.next());
			
			System.out.println(insiemeDB);
			
			if(insiemeDB.put(annuncio.getNome()+annuncio.getEmail()+annuncio.getDataOra().get(GregorianCalendar.YEAR)+
					annuncio.getDataOra().get(GregorianCalendar.MONTH)+annuncio.getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
					annuncio.getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+annuncio.getDataOra().get(GregorianCalendar.MINUTE)+
					annuncio.getDataOra().get(GregorianCalendar.SECOND),annuncio) == null) { 
				/*Se il metodo add ritorna true allora ha inserito l'annuncio nell'insiemeDB perchè nel database non era presente
				  allora occorre aggiungerlo anche nel database altrimenti se ritorna false, il metodo non ha aggiunto l'annuncio
				  nell'insiemeDB perchè già era presente nel dataBase*/
				
				preferitiDAO.doSave(email_utente, annuncio.getEmail(), annuncio.getNome(), annuncio.getDataOra());
				
			}
		}
		return insiemeDB;
	}

	public void salvaAnnuncioAiPreferiti(String emailUtente, OggettoBean annuncio) throws SQLException {
		
		new PreferitiDAO().doSave(emailUtente, annuncio.getEmail(), annuncio.getNome(), annuncio.getDataOra());
	}
	
	public void eliminaAnnuncio(String emailUtente, String emailVenditore, String nomeAnnuncio, GregorianCalendar dataOra) throws SQLException{
		
		new PreferitiDAO().deleteByKey(emailUtente, emailVenditore, nomeAnnuncio, dataOra);
	}
	
	public void cestinaAnnunci(String emailUtente) throws SQLException {
	
		new PreferitiDAO().deleteAll(emailUtente);
	}
	
}














