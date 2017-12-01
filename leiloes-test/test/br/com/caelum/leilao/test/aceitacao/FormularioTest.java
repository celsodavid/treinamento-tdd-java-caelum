package br.com.caelum.leilao.test.aceitacao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.caelum.leilao.test.page.object.UsuarioPage;

public class FormularioTest {
	
	private WebDriver navegador;
	private UsuarioPage usuarioPage;

	@Before
	public void setUp() {
		navegador = new ChromeDriver();
		navegador.get("http://localhost:8080/apenas-teste/limpa");
		usuarioPage = new UsuarioPage(navegador);
	}
	
	@After
	public void tearOut() {
		navegador.close();
	}
	
	@Test
	public void testDeveCadastrarUsuarioParaDadosValidos() {		
		boolean contemCampos = usuarioPage
				   .abreListaDeUsuarios()
				   .varParaTelaDeCadastro()
				   .popularFormulario("Adriano Xavier", "adriano@empresa.com")
				   .enviaFormulario()
				   .validaSePossui("Adriano Xavier", "adriano@empresa.com");
		
		assertTrue(contemCampos);
	}
	
	@Test
	public void testDeveExcluirOUsuarioCadastrado() {		
		boolean usuarioExcluido = usuarioPage
				   .abreListaDeUsuarios()
				   .varParaTelaDeCadastro()
				   .popularFormulario("Adriano Xavier", "adriano@empresa.com")
				   .enviaFormulario()
				   .clicaNoBotaoExcluir()
				   .confirmaExclusao()
				   .validaSeNaoPossui("Adriano Xavier", "adriano@empresa.com");
		
		assertTrue(usuarioExcluido);
	}
	
	@Test
	public void testDeveExibirMensagemDeErroAoCadastrarUmUsuarioSemDados() {			
		usuarioPage
		   .abreListaDeUsuarios()
		   .varParaTelaDeCadastro()
		   .popularFormulario("", "")
		   .enviaFormulario();
		
		boolean nomeObrigatorio = usuarioPage.validaMensagemDeErroCom("Nome obrigatorio");
		boolean emailObrigatorio = usuarioPage.validaMensagemDeErroCom("E-mail obrigatorio");
				
		assertTrue(nomeObrigatorio);
		assertTrue(emailObrigatorio);
	}
	
	@Test
	public void testDeveExibirMensagemDeErroAoCadastrarUmUsuarioSemUsuario() {			
		usuarioPage
		   .abreListaDeUsuarios()
		   .varParaTelaDeCadastro()
		   .popularFormulario("", "usuario@usuario.com")
		   .enviaFormulario();
		
		boolean nomeObrigatorio = usuarioPage.validaMensagemDeErroCom("Nome obrigatorio");
				
		assertTrue(nomeObrigatorio);
	}
	
	@Test
	public void testDeveExibirMensagemDeErroAoCadastrarUmUsuarioSemEmail() {			
		usuarioPage
		   .abreListaDeUsuarios()
		   .varParaTelaDeCadastro()
		   .popularFormulario("", "")
		   .enviaFormulario();
		
		boolean emailObrigatorio = usuarioPage.validaMensagemDeErroCom("E-mail obrigatorio");
		assertTrue(emailObrigatorio);
	}
}
