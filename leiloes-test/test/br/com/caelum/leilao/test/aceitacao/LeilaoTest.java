package br.com.caelum.leilao.test.aceitacao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.caelum.leilao.test.page.object.LeilaoPage;
import br.com.caelum.leilao.test.page.object.UsuarioPage;

public class LeilaoTest {

	private WebDriver navegador;
	private UsuarioPage usuarioPage;
	private LeilaoPage leilaoPage;

	@Before
	public void setUp() {
		navegador = new ChromeDriver();
		navegador.get("http://localhost:8080/apenas-teste/limpa");
		
		usuarioPage = new UsuarioPage(navegador);
		leilaoPage = new LeilaoPage(navegador);
		
	}

	@After
	public void tearDown() {
		navegador.close();
	}

	@Test
	public void testDeveCadastrarLeilaoComDadosValidos() {
		usuarioPage.abreListaDeUsuarios()
		   		   .varParaTelaDeCadastro()
		   		   .popularFormulario("Celso", "celso@lopes.com")
		   		   .enviaFormulario();
	
		leilaoPage.vaiParaListaDeLeiloes()
				  .vaiParaPaginaDeCadastro()
				  .preencherFormularioCom("TV", 10.0, "Celso", false)
				  .enviaFormulario()
		;
		
		boolean verificaSeLeilaoFoiCriado = leilaoPage.verificaSeLeilaoFoiCriado("TV", 10.0, "Celso", false);
		
		assertTrue(verificaSeLeilaoFoiCriado);
	}

	@Test
	public void testNaoDeveCadastrarLeilaoSemNome() {
		usuarioPage.abreListaDeUsuarios()
		   		   .varParaTelaDeCadastro()
		   		   .popularFormulario("Celso", "celso@lopes.com")
		   		   .enviaFormulario();
	
		leilaoPage.vaiParaListaDeLeiloes()
				  .vaiParaPaginaDeCadastro()
				  .preencherFormularioCom("", 10.0, "Celso", false)
				  .enviaFormulario()
		;
		
		boolean verificaSeLeilaoNaoFoiCriadoSemNome = leilaoPage.verificaSeLeilaoNaoFoiCriadoSem("Nome obrigatorio!");
		
		assertTrue(verificaSeLeilaoNaoFoiCriadoSemNome);
	}
	
	@Test
	public void testNaoDeveCadastrarLeilaoSemValor() {
		usuarioPage.abreListaDeUsuarios()
		   		   .varParaTelaDeCadastro()
		   		   .popularFormulario("Celso", "celso@lopes.com")
		   		   .enviaFormulario();
	
		leilaoPage.vaiParaListaDeLeiloes()
				  .vaiParaPaginaDeCadastro()
				  .preencherFormularioCom("TV", 0.0, "Celso", false)
				  .enviaFormulario()
		;
		
		boolean verificaSeLeilaoNaoFoiCriadoSemValor = leilaoPage.verificaSeLeilaoNaoFoiCriadoSem("Valor inicial deve ser maior que zero!");
		
		assertTrue(verificaSeLeilaoNaoFoiCriadoSemValor);
	}
	
	@Test
	public void testNaoDeveCadastrarLeilaoSemDadosValidos() {
		usuarioPage.abreListaDeUsuarios()
		   		   .varParaTelaDeCadastro()
		   		   .popularFormulario("Celso", "celso@lopes.com")
		   		   .enviaFormulario();
	
		leilaoPage.vaiParaListaDeLeiloes()
				  .vaiParaPaginaDeCadastro()
				  .preencherFormularioCom("", 0.0, "Celso", false)
				  .enviaFormulario()
		;
		
		boolean nomeObrigatorio = leilaoPage.verificaSeLeilaoNaoFoiCriadoSem("Nome obrigatorio!");
		boolean valorObrigatorio = leilaoPage.verificaSeLeilaoNaoFoiCriadoSem("Valor inicial deve ser maior que zero!");
		
		assertTrue(nomeObrigatorio);
		assertTrue(valorObrigatorio);
	}
}
