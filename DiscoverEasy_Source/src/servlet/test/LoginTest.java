package servlet.test;

	import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
	import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
	import javax.servlet.ServletException;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	import org.junit.After;
	import org.junit.Before;
	import org.junit.Test;
	import org.mockito.Mockito;

import interfaccia.account.Login;
import interfaccia.gestore.*;
import manager.utente.ManagerUtente;
import model.beans.utenteBean;
	import model.dao.utenteDAO;

	public class LoginTest {

		private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		private static HttpSession session = Mockito.mock(HttpSession.class);
		private static ManagerUtente managerUtente = new ManagerUtente();
		private static utenteDAO utenteDAO = new utenteDAO();
		private Login login = new Login();
		private static utenteBean utente = new utenteBean();
		private static PrintWriter print;
		
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
			print = new PrintWriter("tmp.txt");
		} catch (IllegalArgumentException | SQLException | FileNotFoundException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile caricare utente");
		}
	}
		

	@Test
	public void loginTestUtenteEsistente() {
	
	Mockito.when(request.getSession(true)).thenReturn(session);
	Mockito.when(request.getParameter("email")).thenReturn("test@gmail.com");
	Mockito.when(request.getParameter("password")).thenReturn("testtest");
	Mockito.when(request.getHeader("x-requested-with")).thenReturn("XMLHttpRequest");

	try {
		Mockito.when(response.getWriter()).thenReturn(print);
		login.doPost(request, response);

	} catch (ServletException | IOException e) {
		e.printStackTrace();
		fail("Test fallito");
	}
		assertTrue(login.isAccesso());
	}	
	
	@Test
	public void loginTestUtenteNonEsistente() {
	
	Mockito.when(request.getSession(true)).thenReturn(session);
	Mockito.when(request.getParameter("email")).thenReturn("test1@gmail.com");
	Mockito.when(request.getParameter("password")).thenReturn("testtest");
	Mockito.when(request.getHeader("x-requested-with")).thenReturn("XMLHttpRequest");

	try {
		Mockito.when(response.getWriter()).thenReturn(print);
		login.doPost(request, response);

	} catch (ServletException | IOException e) {
		e.printStackTrace();
		fail("Test fallito");
	}
		assertFalse(login.isAccesso());
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


