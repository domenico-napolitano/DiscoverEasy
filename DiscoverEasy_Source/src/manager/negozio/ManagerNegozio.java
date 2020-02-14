package manager.negozio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import model.beans.OggettoBean;
import model.dao.OggettoDAO;

public class ManagerNegozio {

	private static Logger log;

	public ManagerNegozio() {

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	public void eliminaAnnuncio(String nome, String email, GregorianCalendar data_ora) throws SQLException {

		nome = nome.trim();
		email = email.trim();

		System.out.println(nome +" "+ email);

		OggettoDAO oggettodao = new OggettoDAO();
		oggettodao.deleteByKey(email, nome, data_ora);

		System.out.println("oggettoEliminato");
	}

	public void salvaAnnuncio(String email, String nome, String categoria, String regione,
			String descrizione, String prezzo, String urlImmagine) throws SQLException {

		if(!checkDati(email, nome, categoria, regione, prezzo, urlImmagine, descrizione)) {

			throw new IllegalArgumentException("parametri di input in formato errato");

		}

		urlImmagine = costruisciPath(urlImmagine);

		//salva oggetto
		OggettoBean d = new OggettoBean();
		d.setEmail(email);
		d.setNome(nome);
		d.setCategoria(categoria);
		d.setRegione(regione);	
		d.setDescrizione(descrizione);
		d.setPrezzo(Integer.parseInt(prezzo));
		d.setImmagine(urlImmagine);
		log.logp(Level.INFO, "ManagerNegozio", "salvaAnnuncio", "ho controllati dato oggetto, ok");
		salvaOggetto(d); 
	}


	//funzione che testa i dati 
	private boolean checkDati(String email, String nome, String categoria, String regione, String prezzo,
			String immagine, String descrizione) {
		boolean b1 = checkEmail(email);
		boolean b2 = checkNome(nome);
		boolean b3 = checkCategoria(categoria);
		boolean b4 = checkRegione(regione);
		boolean b5 = checkPrezzo(prezzo);
		boolean b6 = checkImmagine(immagine);
		boolean b7 = checkDescrizione(descrizione);
		log.logp(Level.INFO, "ManagerNegozio", "checkDati", b1+"");
		log.logp(Level.INFO, "ManagerNegozio", "checkDati", b2+"");
		log.logp(Level.INFO, "ManagerNegozio", "checkDati", b3+"");
		log.logp(Level.INFO, "ManagerNegozio", "checkDati", b4+"");
		log.logp(Level.INFO, "ManagerNegozio", "checkDati", b5+"");
		log.logp(Level.INFO, "ManagerNegozio", "checkDati", b6+"");
		log.logp(Level.INFO, "ManagerNegozio", "checkDati", b7+"");
		return b1&&b2&&b3&&b4&&b5&&b6&&b7;
	} 

	private boolean checkImmagine(String immagine) {

		if(immagine.length() < 4)
			return false;

		if(!immagine.substring(immagine.length()-4).equalsIgnoreCase(".jpg"))
			return false;

		else 
			return true;
	}

	private boolean checkDescrizione(String descrizione) {

		if(descrizione.length() >= 2000)
			return false;

		else 
			return true;
	}

	private boolean checkPrezzo(String prezzo) {

		if(prezzo.equals(""))
			return false;

		int p= Integer.parseInt(prezzo);
		System.out.println("il prezzo è" + p);
		if(p<0 || p>100000)
			return false;
		else
			return true;
	}

	//controllo sulla correttezza del nome
	private boolean checkNome(String nome) {
		if(nome == null)
			return false;
		if(nome.length() < 1 || nome.length() > 30)
			return false;
		return true;
	}

	//validazione email path
	private boolean checkEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 

		Pattern pat = Pattern.compile(emailRegex); 

		if (email == null) 
			return false; 

		return pat.matcher(email).matches(); 

	}

	private boolean checkCategoria(String d) {
		if(d.equals("Veicoli") || d.equals("Informatica") || d.equals("Casalinghi") || d.equals("Altro"))
			return true;
		else
			return false;
	}

	private boolean checkRegione(String d) {
		if(d.equals("Campania") || d.equals("Abruzzo") || d.equals("Basilicata") || d.equals("Calabria") || d.equals("Emilia-Romagna") || d.equals("Friuli-Venezia-Giulia") ||d.equals("Lazio") || d.equals("Liguria") ||d.equals("Lombardia") || d.equals("Marche") || d.equals("Molise") || d.equals("Piemonte") || d.equals("Puglia") || d.equals("Sardegna") || d.equals("Sicilia") || d.equals("Toscana") || d.equals("Trentino-Alto-Adige") || d.equals("Umbria") || d.equals("Valle d'Aosta"))
			return true;
		else
			return false;
	}

	private String costruisciPath(String immagine) {

		int i = immagine.lastIndexOf("\\");
		int f = immagine.length();

		String iniziale = "Img\\Prodotti\\";
		String finale = iniziale + immagine.substring(i+1, f);
		System.out.println(finale);

		return finale;
	}

	private void salvaOggetto(OggettoBean d) throws SQLException {

		System.out.println(d);

		OggettoDAO c = new OggettoDAO();

		c.doSave(d);

		log.logp(Level.INFO, "ManagerNegozio", "salvaOggetto", "salva oggetto");

	} 
}
