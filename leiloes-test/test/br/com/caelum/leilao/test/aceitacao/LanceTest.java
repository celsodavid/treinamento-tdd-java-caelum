package br.com.caelum.leilao.test.aceitacao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.caelum.leilao.test.page.object.LeilaoPage;
import br.com.caelum.leilao.test.page.object.UsuarioPage;

public class LanceTest {

	private WebDriver navegador;

	@Before
	public void setUp() {
		navegador = new ChromeDriver();
		navegador.get("http://localhost:8080/apenas-teste/limpa");
	}

	@After
	public void tearDown() {
		navegador.close();
	}

	@Test
	public void testDeveCadastrarLanceNoLeilao() {
		UsuarioPage usuario = new UsuarioPage(navegador)
				.abreListaDeUsuarios()
				.varParaTelaDeCadastro().popularFormulario("Celso", "celso@lopes.com.br").enviaFormulario();
		
		LeilaoPage leilao = new LeilaoPage(navegador).vaiParaListaDeLeiloes().vaiParaPaginaDeCadastro()
				.preencherFormularioCom("Xbox", 1000.0, "Celso", false).enviaFormulario();
		
		boolean existeLance = leilao.vaiParaListaDeLeiloes().exibeLeilao()
				.propoeLance("Celso", 2000.0).validaQueExisteLance("Celso", 2000.0);
		
		assertTrue(existeLance);
	}

}
