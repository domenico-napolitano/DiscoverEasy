package model.beans;

import java.util.GregorianCalendar;

public class ChatBean {

    private String emailMittente;
    private String emailDestinatario;
    private String nomeAnnuncio;
     
    private GregorianCalendar dataOraAnnuncio;
  
       
    private String nomeVenditoreAnnuncio;
    private String nomeMittenteChat;
    private String immagine;
    
    private int mittenteMessaggioNonLetto;
    private int destinatarioMessaggioNonLetto;
      
    
    public void setEmailMittente (String emailMittente) {
    	
    	this.emailMittente = emailMittente;
    }
    
    public void setEmailDestinatario (String emailDestinatario) {
    	
    	this.emailDestinatario = emailDestinatario;
    }
    
    public void setNomeAnnuncio (String nomeAnnuncio) {
    	
    	this.nomeAnnuncio = nomeAnnuncio;
    }
    
    public void setDataOraAnnuncio (GregorianCalendar dataOraAnnuncio) {
    	
    	this.dataOraAnnuncio = dataOraAnnuncio;
    }
    
    public void setNomeVenditoreAnnuncio (String nomeVenditoreAnnuncio) {
    	
    	this.nomeVenditoreAnnuncio = nomeVenditoreAnnuncio;
    }
    
    public void setNomeMittenteChat (String nomeMittenteChat) {
    	
    	this.nomeMittenteChat = nomeMittenteChat;
    }
    
    public void setImmagine (String immagine) {
    	
    	this.immagine = immagine;
    }
    
   public void setMittenteMessaggioNonLetto (int mittenteMessaggioNonLetto) {
    	
    	this.mittenteMessaggioNonLetto = mittenteMessaggioNonLetto;
    }
    
    public void setDestinatarioMessaggioNonLetto (int destinatarioMessaggioNonLetto) {
    	
    	this.destinatarioMessaggioNonLetto = destinatarioMessaggioNonLetto;
    }
    
    public String getEmailMittente() {
    	
    	return emailMittente;
    }
    
    public String getEmailDestinatario() {
    	
    	return emailDestinatario;
    }
    
    public String getNomeAnnuncio() {
    	
    	return nomeAnnuncio;
    }
    
    public GregorianCalendar getDataOraAnnuncio() {
    	
    	return dataOraAnnuncio;
    }
    
    public String getNomeVenditoreAnnuncio () {
    	
    	return nomeVenditoreAnnuncio;
    }
    
    public String getNomeMittenteChat () {
    	
    	return nomeMittenteChat;
    }
    
    public String getImmagine () {
    	
    	return immagine;
    }
    
    public int getMittenteMessaggioNonLetto () {
    	
    	return mittenteMessaggioNonLetto;
    }
    
    public int getDestinatarioMessaggioNonLetto () {
    	
    	return destinatarioMessaggioNonLetto;
    }

	@Override
	public String toString() {
		return "ChatBean [emailMittente=" + emailMittente + ", emailDestinatario=" + emailDestinatario
				+ ", nomeAnnuncio=" + nomeAnnuncio + ", dataOraAnnuncio=" + dataOraAnnuncio + ", nomeVenditoreAnnuncio="
				+ nomeVenditoreAnnuncio + ", nomeMittenteChat=" + nomeMittenteChat + ", immagine=" + immagine
				+ ", mittenteMessaggioNonLetto=" + mittenteMessaggioNonLetto + ", destinatarioMessaggioNonLetto="
				+ destinatarioMessaggioNonLetto + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatBean other = (ChatBean) obj;
		if (dataOraAnnuncio == null) {
			if (other.dataOraAnnuncio != null)
				return false;
		} else if (!dataOraAnnuncio.equals(other.dataOraAnnuncio))
			return false;
		if (destinatarioMessaggioNonLetto != other.destinatarioMessaggioNonLetto)
			return false;
		if (emailDestinatario == null) {
			if (other.emailDestinatario != null)
				return false;
		} else if (!emailDestinatario.equals(other.emailDestinatario))
			return false;
		if (emailMittente == null) {
			if (other.emailMittente != null)
				return false;
		} else if (!emailMittente.equals(other.emailMittente))
			return false;
		if (immagine == null) {
			if (other.immagine != null)
				return false;
		} else if (!immagine.equals(other.immagine))
			return false;
		if (mittenteMessaggioNonLetto != other.mittenteMessaggioNonLetto)
			return false;
		if (nomeAnnuncio == null) {
			if (other.nomeAnnuncio != null)
				return false;
		} else if (!nomeAnnuncio.equals(other.nomeAnnuncio))
			return false;
		if (nomeMittenteChat == null) {
			if (other.nomeMittenteChat != null)
				return false;
		} else if (!nomeMittenteChat.equals(other.nomeMittenteChat))
			return false;
		if (nomeVenditoreAnnuncio == null) {
			if (other.nomeVenditoreAnnuncio != null)
				return false;
		} else if (!nomeVenditoreAnnuncio.equals(other.nomeVenditoreAnnuncio))
			return false;
		return true;
	}


}
















