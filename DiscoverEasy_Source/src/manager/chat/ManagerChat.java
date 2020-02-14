package manager.chat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.beans.ChatBean;
import model.beans.MessaggioBean;
import model.dao.ChatDAO;

public class ManagerChat {

	public ArrayList<ChatBean> caricaChat(String emailUtente) throws SQLException {

		emailUtente = emailUtente.trim();

		return new ChatDAO().doSearchByEmail(emailUtente);

	}
	
	public void iniziaNuovaChat(String emailMittenteChat, String emailDestinatarioChat,
			String nomeAnnuncio, GregorianCalendar dataOraAnnuncio) throws SQLException {
		
		emailMittenteChat = emailMittenteChat.trim();
		
		emailDestinatarioChat = emailDestinatarioChat.trim();
		
		nomeAnnuncio = nomeAnnuncio.trim();
		
		new ChatDAO().iniziaNuovaChat(emailMittenteChat, emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio);
	}

	public ArrayList<MessaggioBean> caricaMessaggi(String emailUtente, String emailMittenteChat, String emailDestinatarioChat,
			String nomeAnnuncio, GregorianCalendar dataOraAnnuncio) throws SQLException {

		emailMittenteChat = emailMittenteChat.trim();

		emailDestinatarioChat = emailDestinatarioChat.trim();

		nomeAnnuncio = nomeAnnuncio.trim();	
		
		emailUtente = emailUtente.trim();

		ArrayList<MessaggioBean> listaMessaggi;

		if(emailUtente.equals(emailMittenteChat)) {
		
			listaMessaggi = new ChatDAO().doSearchChatMessage(emailMittenteChat, emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio, 0, true);
		}
		else
		{
			listaMessaggi = new ChatDAO().doSearchChatMessage(emailMittenteChat, emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio, 0, false);
		}

		return listaMessaggi;
	}

	public void inviaMessaggio(String descrizione, String emailMittenteMessaggio,  String emailMittenteChat,
			String emailDestinatarioChat, String nomeAnnuncio, GregorianCalendar dataOraAnnuncio) throws SQLException {

		emailMittenteMessaggio = emailMittenteMessaggio.trim();
		
		emailMittenteChat = emailMittenteChat.trim();
		
		emailDestinatarioChat = emailDestinatarioChat.trim();
		
		nomeAnnuncio = nomeAnnuncio.trim();
		
		MessaggioBean messaggio = new MessaggioBean();

		messaggio.setDescrizione(descrizione);

		messaggio.setEmailMittenteMessaggio(emailMittenteMessaggio);

		/*Al destinatario arriva notifica messaggio ricevuto*/

		if(emailMittenteMessaggio.equals(emailMittenteChat)) {

			new ChatDAO().doSave(messaggio, emailMittenteChat, emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio, 0, 1);
		}
		else
		{
			new ChatDAO().doSave(messaggio, emailMittenteChat, emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio, 1, 0);
		}
	}
	
	public boolean notifica(String emailUtente) throws SQLException{
		
		ArrayList<ChatBean> listaChat = caricaChat(emailUtente);
		
		ChatBean chat;
		
		for(int i=0; i < listaChat.size(); i++)
		{
			chat = listaChat.get(i);
			
			if(chat.getEmailMittente().equals(emailUtente) && chat.getMittenteMessaggioNonLetto() == 1)
			{
				return true;
			}
			
			if(chat.getEmailDestinatario().equals(emailUtente) && chat.getDestinatarioMessaggioNonLetto() == 1)
			{
				return true;
			}	
		}
		
		return false;
	}
}



