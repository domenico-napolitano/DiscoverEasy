package model.beans;

public class utenteBean {
	private String email, pass, sesso, regione, nome, email_ban;
	private int anno_nascita, amministratore;
	private long telefono;
	private boolean eliminato;
	
	public long getTelefono() {
		return telefono;
	}
	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public int getAnno_nascita() {
		return anno_nascita;
	}
	public void setAnno_nascita(String anno) {
		this.anno_nascita = Integer.parseInt(anno);
	}
	public int getAmministratore() {
		return amministratore;
	}
	public void setAmministratore(int amministratore) {
		this.amministratore = amministratore;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean getEliminato() {
		
		return eliminato;
	}
	
	public void setEliminato(boolean eliminato) {
		this.eliminato = eliminato;
	}
	
	public String getEmail_ban() {
		
		return email_ban;
	}
	
	public void setEmail_ban(String email_ban) {
		
		this.email_ban = email_ban;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		utenteBean other = (utenteBean) obj;
		if (amministratore != other.amministratore)
			return false;
		if (anno_nascita != other.anno_nascita)
			return false;
		if (eliminato != other.eliminato)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (email_ban == null) {
			if (other.email_ban != null)
				return false;
		} else if (!email_ban.equals(other.email_ban))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (regione == null) {
			if (other.regione != null)
				return false;
		} else if (!regione.equals(other.regione))
			return false;
		if (sesso == null) {
			if (other.sesso != null)
				return false;
		} else if (!sesso.equals(other.sesso))
			return false;
		if (telefono != other.telefono)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "utenteBean [email=" + email + ", pass=" + pass + ", sesso=" + sesso + ", regione=" + regione + ", nome="
				+ nome + ", email_ban=" + email_ban + ", anno_nascita=" + anno_nascita + ", amministratore="
				+ amministratore + ", telefono=" + telefono + ", eliminato=" + eliminato + "]";
	}
	
	
	
	
}
