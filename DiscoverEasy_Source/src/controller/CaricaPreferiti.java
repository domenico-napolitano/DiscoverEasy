package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.OggettoBean;


/**
 * Servlet implementation class Sessione
 */
@WebServlet("/CaricaPreferiti")
public class CaricaPreferiti extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	public CaricaPreferiti() {
		super();
		// TODO Auto-generated constructor stub

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	private Hashtable<String, OggettoBean> listaOggettiPreferiti = null;
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/* se getSession riceve come input true ritorna la sessione esistente o, se non esiste, ne crea 
		  una nuova altrimenti se prende false ritorna, se possibile, la sessione esistente, altrimenti 
		  ritorna null */

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();


		HttpSession sessione = request.getSession(true);

		/*Carico preferiti dell'utente*/
		synchronized(sessione) {

			listaOggettiPreferiti = (Hashtable<String, OggettoBean>) sessione.getAttribute("listaOggettiPreferiti");

			/*Serve una volta per inizializzare un arrylist contenente gli oggetti preferiti
			 e quindi se la sessione non ha un attributo listaOggettiPreferiti, ritorna null il metodo
			  getattribute e inserisco l'arrayList nella sessione altrimenti se ce l'ha non ritorna null ma 
			  l'oggetto arrayList quindi l'if fallisce nell == null e non li inizializzo altre volte l'arrayList*/

			if(listaOggettiPreferiti == null)
			{
				listaOggettiPreferiti = new Hashtable<>();

				sessione.setAttribute("listaOggettiPreferiti", listaOggettiPreferiti);
			}

			/*recupero l'attributo accesso dalla sessione. Se esite nella sessione allora significa che l'utente ha
			 effettuato il login, quindi è stata avviata la servlet login che ha messo nella sessione un attributo
			 accesso con valore true altrimenti, se l'utente non ha effettuato il login oppure ha effettuato il logout
			 oppure è scaduta la sessione quindi una volta acceduto NUOVAMENTE alla pagina si aprira una nuova sessione
			 che non conterrà questo attributo accesso con valore true perchè la servlet login non è stata avviata
			 perchè l'utente non ha effettuato il login. Quindi se esiste attributo nella sessione sarà eseguito l'if 
			 se non esiste il metodo getAttribute restituirà null e quindi non sarà eseguito l'if */

			Object loginEffettuato = sessione.getAttribute("accesso");

			/*L'if effettua se l'utente è loggato al sistema il merge tra i preferiti della sessione e del database e il risultato, lo
			 * mantiene nella sessione*/

			if(loginEffettuato != null && (boolean )loginEffettuato != false)
			{

				try {
					listaOggettiPreferiti = new ManagerRicerca().mergePreferitiSessioneDatabase(listaOggettiPreferiti,(String) sessione.getAttribute("email"));

					/*Aggiorna la sessione con la nuova lista preferiti*/

					sessione.setAttribute("listaOggettiPreferiti", listaOggettiPreferiti); 

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					log.logp(Level.INFO, "CaricaPreferiti", "doGet","sorta eccezzione per il metodo mergePreferitiSessioneDatabase,"
							+ "quindi saranno caricati solo preferiti sessione e non anche quelli del database");
				}
			}



			int lunghezzaListaOggettiPreferiti = listaOggettiPreferiti.size();

			log.logp(Level.INFO, "CaricaPreferiti", "doGet","lunghezza lista preferiti:"+lunghezzaListaOggettiPreferiti);

			log.logp(Level.INFO, "CaricaPreferiti", "doGet","Contenuto lista preferiti sessione: "+listaOggettiPreferiti);


			/*Se la listaOggettiPreferiti non è vuota allora significa che l'utente ha salvato degli oggetti
			 in preferiti e quindi viene eseguito l'if questi oggetti vengono caricati in un array Json
			 che contiente oggettiJson, un oggetto Json rappresenta un singolo oggetto preferito , quindi
			 l'array Json è un insieme di oggetti preferiti .L'ArrayJson vieni mandato ad uno
			 script Javascript che carica i preferiti nella lsita preferiti. Se la listaOggettiPreferiti
			 è vuota quindi la sua dimensione è minore di 1 allora l'utente non ha inserito oggetti nella
			 lista preferiti*/



			/*è un array Json perchè viene usato per metterci dentro oggetti preferiti che servono per 
			  caricare preferiti nel modal preferiti */

			String arrayJson = "[";

			if(lunghezzaListaOggettiPreferiti > 0)
			{
				String immagine;

				OggettoBean annuncio;

				Set<String> keys = listaOggettiPreferiti.keySet();

				Iterator<String> scorriListaPreferiti = keys.iterator();

				GregorianCalendar dataOra;

				while(scorriListaPreferiti.hasNext())
				{
					annuncio = listaOggettiPreferiti.get(scorriListaPreferiti.next());

					dataOra = annuncio.getDataOra();

					arrayJson +="{"+

							"\"nome\":"+ "\"" + annuncio.getNome() +"\"," +

							"\"descrizione\":"+ "\"" + annuncio.getDescrizione() +"\","+

							"\"regione\":"+ "\"" + annuncio.getRegione() +"\","+

							"\"telefono\":"+ "\"" + annuncio.getNumero_proprietario().trim() +"\","+

							"\"prezzo\":"+ annuncio.getPrezzo() +","+

							"\"email_venditore\":"+ "\"" + annuncio.getEmail() +"\","+

							"\"anno\":"+ "\"" + dataOra.get(GregorianCalendar.YEAR) +"\","+

							"\"mese\":"+ "\"" + (dataOra.get(GregorianCalendar.MONTH)+1) +"\","+		

							"\"giorno\":"+ "\"" + dataOra.get(GregorianCalendar.DAY_OF_MONTH) +"\","+

							"\"ora\":"+ "\"" + dataOra.get(GregorianCalendar.HOUR_OF_DAY) +"\","+

							"\"minuti\":"+ "\"" + dataOra.get(GregorianCalendar.MINUTE) +"\","+

							"\"secondi\":"+ "\"" + dataOra.get(GregorianCalendar.SECOND) +"\",";


					immagine = annuncio.getImmagine();


					/*sostituisco il carattere \ con il caratte \\ perchè l'url dell'immagine essendo che è gerarchico
							 ha degli slash \ nella stringa per indicare le sottocartelle, questo \ slash da problemi al parse
							 JSON perchè in javascript lo \ è un carattere di escape , quindi sostituisco lo \ nell url con 2 \
							 slash per dire che è un carattere normale e non di escape. Anche nel metodo sotto replaceALL metto doppio
							 slash per dire chè è un caratte \ normale e non uno di escape, quindi questo vale anche per JAVA */

					immagine = immagine.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement("\\\\"));

					arrayJson += "\"immagine\":"+ "\"" + immagine +"\"";


					arrayJson += "},";

				}

				arrayJson = arrayJson.substring(0, arrayJson.length()-1); // per togliere la virgola
				arrayJson += "]";

				log.logp(Level.INFO, "CaricaPreferiti", "doGet","Contenuto lista preferiti JSON: "+arrayJson);


				out.println(arrayJson);

			}//if
			else
			{
				/*lista preferiti vuota, quindi è un array vuoto*/

				arrayJson += "]";

				out.println(arrayJson);
			}
		}//synchronized

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public Hashtable<String, OggettoBean> getListaOggettiPreferiti() {
		// TODO Auto-generated method stub
		return listaOggettiPreferiti;
	}

}
