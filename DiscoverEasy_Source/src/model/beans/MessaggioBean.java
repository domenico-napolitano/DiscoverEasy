package model.beans;

import java.util.GregorianCalendar;

public class MessaggioBean {

	private String descrizione;

	private String emailMittenteMessaggio;

	private GregorianCalendar dataOraInvioMessaggio;

	private GregorianCalendar dataOraAnnuncio;

	private String emailMittenteChat;

	private String emailDestinatarioChat;

	private String nomeAnnuncioChat;


	public GregorianCalendar getDataOraAnnuncio() {

		return dataOraAnnuncio;
	}

	public void setDataOraAnnuncio(GregorianCalendar dataOraAnnuncio) {

		this.dataOraAnnuncio = dataOraAnnuncio;
	}

	public String getEmailMittenteChat() {

		return emailMittenteChat;
	}

	public String getEmailDestinatarioChat() {

		return emailDestinatarioChat;
	}

	public String getNomeAnnuncioChat() {

		return nomeAnnuncioChat;
	}

	public void setEmailMittenteChat(String emailMittenteChat) {

		this.emailMittenteChat = emailMittenteChat;
	}

	public void setEmailDestinatarioChat(String emailDestinatarioChat) {

		this.emailDestinatarioChat = emailDestinatarioChat;
	}

	public void setNomeAnnuncioChat(String nomeAnnuncioChat) {

		this.nomeAnnuncioChat = nomeAnnuncioChat;
	}

	public void setDescrizione(String descrizione) {

		this.descrizione = descrizione;
	}

	public void setEmailMittenteMessaggio(String emailMittenteMessaggio) {

		this.emailMittenteMessaggio = emailMittenteMessaggio;
	}

	public void setDataOraInvioMessaggio(GregorianCalendar dataOraInvioMessaggio) {

		this.dataOraInvioMessaggio = dataOraInvioMessaggio;
	}

	public String getDescrizione() {

		return descrizione;
	}

	public String getEmailMittenteMessaggio() {

		return emailMittenteMessaggio;
	}

	public GregorianCalendar getDataOraInvioMessaggio() {

		return dataOraInvioMessaggio;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessaggioBean other = (MessaggioBean) obj;
		if (dataOraInvioMessaggio == null) {
			if (other.dataOraInvioMessaggio != null)
				return false;
		} 
		else {
			
			if(other.dataOraInvioMessaggio == null)
				return false;
			
			if (!(dataOraInvioMessaggio.get(GregorianCalendar.YEAR) == other.dataOraInvioMessaggio.get(GregorianCalendar.YEAR)))
				return false;

			if (!(dataOraInvioMessaggio.get(GregorianCalendar.MONTH) == other.dataOraInvioMessaggio.get(GregorianCalendar.MONTH)))
				return false;

			if (!(dataOraInvioMessaggio.get(GregorianCalendar.DAY_OF_MONTH) == other.dataOraInvioMessaggio.get(GregorianCalendar.DAY_OF_MONTH)))
				return false;

			if (!(dataOraInvioMessaggio.get(GregorianCalendar.HOUR_OF_DAY) == other.dataOraInvioMessaggio.get(GregorianCalendar.HOUR_OF_DAY)))
				return false;

			if (!(dataOraInvioMessaggio.get(GregorianCalendar.MINUTE) == other.dataOraInvioMessaggio.get(GregorianCalendar.MINUTE)))
				return false;

			if (!(dataOraInvioMessaggio.get(GregorianCalendar.SECOND) == other.dataOraInvioMessaggio.get(GregorianCalendar.SECOND)))
				return false;
		}
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (emailMittenteMessaggio == null) {
			if (other.emailMittenteMessaggio != null)
				return false;
		} else if (!emailMittenteMessaggio.equals(other.emailMittenteMessaggio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessaggioBean [descrizione=" + descrizione + ", emailMittenteMessaggio=" + emailMittenteMessaggio
				+ ", dataOraInvioMessaggio=" + dataOraInvioMessaggio + ", dataOraAnnuncio=" + dataOraAnnuncio
				+ ", emailMittenteChat=" + emailMittenteChat + ", emailDestinatarioChat=" + emailDestinatarioChat
				+ ", nomeAnnuncioChat=" + nomeAnnuncioChat + "]";
	}


}











