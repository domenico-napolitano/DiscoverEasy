package servlet.test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import interfaccia.account.Logout;
import interfaccia.gestore.*;
import manager.utente.ManagerUtente;
import model.beans.utenteBean;
import model.dao.utenteDAO;

public class LogoutTest {

	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static utenteDAO utenteDAO = new utenteDAO();
	private static Logout logout = new Logout();
	private static utenteBean utente = new utenteBean();
	
	
@Before
public void start() {
	utente.setEmail("test@gmail.com");
	utente.setPass("testtest");
	utente.setNome("test");
	utente.setAnno_nascita("1990");
	utente.setEliminato(false);
	utente.setRegione("Campania");
	utente.setSesso("Maschio");
	utente.setTelefono(new Long("3333333333"));
	utente.setEmail_ban(null);
	utente.setAmministratore(0);
	try {
		managerUtente.registra("test@gmail.com", "testtest","test", "Maschio", "Campania", "3333333333", "1990", "on");
	} catch (IllegalArgumentException | SQLException e) {
		e.printStackTrace();
		fail("Test fallito, impossibile caricare utente");
	}
}
	

@Test
public void logoutest() {
Mockito.when(request.getSession(false)).thenReturn(session);
Mockito.when(request.getHeader("referer")).thenReturn("");
Mockito.when(session.getAttribute("email")).thenReturn("test@gmail.com");
Mockito.when(session.getAttribute("accesso")).thenReturn(true);
try {
	logout.doPost(request, response);
	assertFalse(logout.isAccesso());
} catch (ServletException | IOException e) {
	e.printStackTrace();
	fail("Test fallito");
}

}	


@After 
public void clean() {
	try {
		utenteDAO.removeByKey("test@gmail.com");
	} catch (SQLException e) {
		e.printStackTrace();
	}
}	
	
	
	
}
