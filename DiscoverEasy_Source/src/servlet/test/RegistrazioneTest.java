package servlet.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;
import controller.ManagerUtente;
import controller.Registrazione;
import model.beans.utenteBean;
import model.dao.*;
public class RegistrazioneTest {
	
	private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
//	private static HttpSession session = Mockito.mock(HttpSession.class);
	private static ManagerUtente managerUtente = new ManagerUtente();
	private static utenteDAO utenteDAO = new utenteDAO();
	private Registrazione registrazione = new Registrazione();
	private PrintWriter print;
	private static utenteBean utente = new utenteBean();
	
	@Test
	public void Registrazione_Test(){
		try {
			print = new PrintWriter("tmp.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	Mockito.when(request.getParameter("emailRegistrazione")).thenReturn("test@gmail.com");
	Mockito.when(request.getParameter("passwordRegistrazione")).thenReturn("testtest");
	Mockito.when(request.getParameter("regioneRegistrazione")).thenReturn("Campania");
	Mockito.when(request.getParameter("anno_nascitaRegistrazione")).thenReturn("1990");
	Mockito.when(request.getParameter("checkboxRegistrazione")).thenReturn("on");
	Mockito.when(request.getParameter("sessoRegistrazione")).thenReturn("Maschio");
	Mockito.when(request.getParameter("nomeRegistrazione")).thenReturn("test");
	Mockito.when(request.getParameter("telefonoRegistrazione")).thenReturn("3333333333");
	
		try {
			Mockito.when(response.getWriter()).thenReturn(print);
			registrazione.doPost(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			fail("Test fallito, impossibile chiamare servlet Registrazione");
		}
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
	assertEquals(managerUtente.caricaMioProfilo("test@gmail.com"), utente);
		

	
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
