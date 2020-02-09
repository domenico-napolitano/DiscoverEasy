package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.PreferitiDAO;
import model.dao.utenteDAO;

public class ManagerUtente {

	private static Logger log;

	public ManagerUtente(){

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);	
	}

	public void eliminaAccount(String email) throws SQLException{

		/*Questo metodo elimina tutti i preferiti, setta tutti gli annunci a eliminato e l'account a eliminato dell'utente,
		  con email che è uguale al parametro in input email*/

		ArrayList<OggettoBean> listaOggetti = new OggettoDAO().doSearchByEmail(email);

		new PreferitiDAO().deleteAll(email);

		OggettoDAO oggettoDAO = new OggettoDAO();

		OggettoBean annuncio;

		for(int i=0; i< listaOggetti.size(); i++) {

			annuncio = listaOggetti.get(i);
			
			System.out.println("data:  "+annuncio.getDataOra());

			System.out.println("nome:  "+annuncio.getNome());

			System.out.println("email:  "+annuncio.getEmail());

			oggettoDAO.deleteByKey(annuncio.getEmail(), annuncio.getNome(), annuncio.getDataOra());
		}

		new utenteDAO().deleteByKey(email);

	}

	public void modificaPassword(String email, String password) throws SQLException {

		System.out.println("email è: "+email);
		
		if(!checkPassword(password)) {

			log.logp(Level.INFO, "ManagerUtente", "modificaPassword", "throw IllegalArgumentException, formato password errato");

			throw new IllegalArgumentException("Formato password errata");
		}
		
		new utenteDAO().doChangePassword(email, password);
		
	}

	public utenteBean caricaMioProfilo(String email) {

		return new utenteDAO().doRetrieveByKey(email);
	}

	public utenteBean registra(String email, String password, String nome, String sesso, String regione,
			String telefono, String annoNascita, String condizioniUtente) throws IllegalArgumentException, SQLException{

		log.logp(Level.INFO, "ManagerUtente", "registra", "registrazione utente");

		log.logp(Level.INFO, "ManagerUtente", "registra", "dati inseriti :"+"\n email:"+email +"\n password:"+ password + "\n nome:"+nome
				+"\n sesso:"+sesso+ "\n annoNascita:"+annoNascita+ "\n telefono:"+telefono+ "\n regione:"+regione +
				"\n condizioniUtente:"+ condizioniUtente);

		if(!checkDatiAnagrafici(condizioniUtente, regione, annoNascita, nome, sesso, telefono)) {

			log.logp(Level.INFO, "ManagerUtente", "registra", "throw IllegalArgumentException, dati anagrafici errati");

			throw new IllegalArgumentException("Dati anagrafici errati");
		}

		if(!(checkPassword(password) && isValidEmailAddress(email))) {

			log.logp(Level.INFO, "ManagerUtente", "registra", "throw IllegalArgumentException, email o password errate");

			throw new IllegalArgumentException("Email o password errata");
		}

		//controllo esistenza email
		if(checkExistent(email)!= null) {

			log.logp(Level.INFO, "ManagerUtente", "registra", "throw IllegalArgumentException, email esistente");

			throw new IllegalArgumentException("Email già esistente");
		}


		//salva dati anagrafici
		utenteBean d = new utenteBean();
		d.setEmail(email);
		d.setAnno_nascita(annoNascita);
		d.setNome(nome);
		d.setSesso(sesso);
		d.setPass(password);
		d.setTelefono(Long.parseLong(telefono));
		d.setRegione(regione);
		salvaUtente(d); 

		return d;
	}

	public utenteBean autentica(String email, String password) {

		email = email.trim();

		if(!(checkPassword(password) && isValidEmailAddress(email))) {

			log.logp(Level.INFO, "Login", "ManagerUtente", "Formato pass o email errato");

			return null;
		}

		utenteBean utente = checkExistent(email);

		if(utente == null){

			log.logp(Level.INFO, "Login", "ManagerUtente", "Email non esistente");

			return null;
		}
		else
		{
			if(utente.getPass().equals(password)&& utente.getEliminato() == false && utente.getEmail_ban() == null) {

				return utente;
			}
			else
			{
				return null;
			}
		}		
	}

	public utenteBean checkExistent(String email) {

		utenteDAO cDao = new utenteDAO();
		utenteBean c = cDao.doRetrieveByKey(email);

		if(c == null) {
			return null;
		}else {
			return c;
		}
	}

	//controlla che la password sia minima di 8 e massima di 20 caratteri, senza simboli speciali
	private boolean checkPassword(String pass) {

		if(pass == null)
			return false;

		if(pass.length() > 40)
			return false;

		if(pass.length() < 8)
			return false;

		if(!pass.matches("^[a-zA-Z0-9]+$"))
			return false;

		return true;
	}

	//validazione email path
	private boolean isValidEmailAddress(String email) {

		if (email == null) 
			return false; 

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 


		Pattern pat = Pattern.compile(emailRegex); 

		return pat.matcher(email).matches(); 
	}


	//controllo sulla correttezza del nome
	private boolean checkNome(String nome) {
		if(nome == null)
			return false;
		if(nome.length() < 1 || nome.length() > 30)
			return false;

		if(!nome.matches("^[a-zA-Z]+$"))
			return false;

		return isAlpha(nome);
	}

	private boolean checkNumero(String numero) {


		if(numero == null)
			return false;



		if(numero.length() != 10 && numero.length() != 9)
			return false;



		if(!numero.matches("^[0-9]+$"))
			return false;

		return true;
	}

	//controlla se nel nome ci sono solo caratteri
	private boolean isAlpha(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}

		return true;
	}

	//controllo ceckbox sesso
	private boolean checkSesso(String sesso) {

		if(sesso == null)
			return false;

		if(( sesso.equalsIgnoreCase("maschio") || sesso.equalsIgnoreCase("femmina")))
			return true;
		else 
			return false;
	}

	//funzione che testa i dati anagrafici
	private boolean checkDatiAnagrafici(String checkbox, String regione, String anno_nascita, String nome, String sesso, String telefono) {
		boolean b1 = checkNome(nome);
		boolean b2 = checkAnno_nascita(anno_nascita);
		boolean b3 = checkSesso(sesso);
		boolean b4 = checkRegione(regione);
		boolean b5 = checkNumero(telefono);
		boolean b6 = condizioniUtente(checkbox);

		return b1&&b2&&b3&&b4&&b5&&b6;
	} 

	private boolean condizioniUtente(String checkbox) {

		if(checkbox == null)
			return false;

		if(checkbox.equals("on"))
			return true;
		else
			return false;
	}

	private boolean checkAnno_nascita(String d) {
		int i = Integer.parseInt(d);
		if(i>1918 && i<2003)
			return true;
		else
			return false;
	}

	private boolean checkRegione(String d) {
		if(d.equals("Campania") || d.equals("Abruzzo") || d.equals("Basilicata") || d.equals("Calabria") || d.equals("Emilia-Romagna") || d.equals("Friuli-Venezia-Giulia") ||d.equals("Lazio") || d.equals("Liguria") ||d.equals("Lombardia") || d.equals("Marchie") || d.equals("Molise") || d.equals("Piemonte") || d.equals("Puglia") || d.equals("Sardegna") || d.equals("Sicilia") || d.equals("Toscana") || d.equals("Trentino-Alto-Adige") || d.equals("Umbria") || d.equals("Valle d'Aosta"))
			return true;
		else
			return false;
	}

	private void salvaUtente(utenteBean d) throws SQLException{

		utenteDAO c = new utenteDAO();


		c.doSave(d);

		log.logp(Level.INFO, "ManagerUtente", "salvaUtente", "utente salvato");
	} 
}
