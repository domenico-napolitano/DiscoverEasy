package model.beans;

import java.util.GregorianCalendar;

public class OggettoBean {

	private String nome, immagine, descrizione, categoria, regione, email, nome_proprietario,
	numero_proprietario, email_ban, email_preferiti_utente;

	private int prezzo;

	private boolean eliminato;
	
	private GregorianCalendar data_ora;
	

	public OggettoBean(String nome, String immagine, String descrizione, String categoria, String regione, String email,
			String nome_proprietario, String numero_proprietario, String email_ban, int prezzo, boolean eliminato,
			GregorianCalendar data_ora) {
		super();
		this.nome = nome;
		this.immagine = immagine;
		this.descrizione = descrizione;
		this.categoria = categoria;
		this.regione = regione;
		this.email = email;
		this.nome_proprietario = nome_proprietario;
		this.numero_proprietario = numero_proprietario;
		this.email_ban = email_ban;
		this.prezzo = prezzo;
		this.eliminato = eliminato;
		this.data_ora = data_ora;
	}
	
	public OggettoBean() {

	}
	
	
	public String getEmailPreferitiUtente () {
		
		return email_preferiti_utente;
	}
	
	public void setEmailPreferitiUtente(String emailPreferitiUtente) {
		
		this.email_preferiti_utente = emailPreferitiUtente;
	}
	
	public String getEmail_ban() {
		
		return email_ban;
	}
	
	public void setEmail_ban(String email_ban) {
		
		this.email_ban = email_ban;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getImmagine() {
		return immagine;
	}
	public String getNome_proprietario() {
		return nome_proprietario;
	}
	public void setNome_proprietario(String nome_proprietario) {
		this.nome_proprietario = nome_proprietario;
	}
	public String getNumero_proprietario() {
		return numero_proprietario;
	}
	public void setNumero_proprietario(String numero_proprietario) {
		this.numero_proprietario = numero_proprietario;
	}
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	public boolean getEliminato() {
		return eliminato;
	}
	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}
	
	public GregorianCalendar getDataOra () {
		
		return data_ora;
	}
	
	public void setDataOra(GregorianCalendar dataOra) {
	
		this.data_ora = dataOra;
	}
	
	
	@Override
	public String toString() {
		return "OggettoBean [nome=" + nome + ", immagine=" + immagine + ", descrizione=" + descrizione + ", categoria="
				+ categoria + ", regione=" + regione + ", email=" + email + ", nome_proprietario=" + nome_proprietario
				+ ", numero_proprietario=" + numero_proprietario + ", prezzo=" + prezzo + "data e ora="+data_ora+"]";
	}

	@Override
	public boolean equals(Object oggetto) {
		
		if (oggetto == null)
			return false;
		
		if(!this.getClass().equals(oggetto.getClass()))
			return false;
		
		OggettoBean obj = (OggettoBean)oggetto;
		
		if(!this.getImmagine().equals(obj.getImmagine()))
			return false;
		
		if(!this.getDescrizione().equals(obj.getDescrizione()))
			return false;
		
		if(!this.getCategoria().equals(obj.getCategoria()))
			return false;
		
		if(!this.getRegione().equals(obj.getRegione()))
			return false;
		
		if(!this.getNome_proprietario().equals(obj.getNome_proprietario()))
			return false;
		
		if(!this.getNumero_proprietario().equals(obj.getNumero_proprietario()))
			return false;
		
		if(!(this.getPrezzo() == obj.getPrezzo()))
			return false;
		
		if(!this.getNome().equals(obj.getNome()))
			return false;
		
		if(!this.getEmail().equals(obj.getEmail()))
			return false;
		
		return true;
	}
	
}
