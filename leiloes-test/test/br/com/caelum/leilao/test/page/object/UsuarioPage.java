package br.com.caelum.leilao.test.page.object;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsuarioPage {
	
	private WebDriver navegador;
	
	public UsuarioPage(WebDriver navegador) {
		this.navegador = navegador;
	}
	
	public UsuarioPage abreListaDeUsuarios() {
		navegador.navigate().to("http://localhost:8080/usuarios");
		
		return this;
	}
	
	public UsuarioPage varParaTelaDeCadastro() {
		WebElement botaoDeNovo = navegador.findElement(By.linkText("Novo Usuário"));
		botaoDeNovo.click();
		
		return this;
	}
	
	public UsuarioPage popularFormulario(String nome, String email) {
		WebElement campoNome = navegador.findElement(By.name("usuario.nome"));
		WebElement campoEmail = navegador.findElement(By.name("usuario.email"));
		
		campoNome.sendKeys(nome);
		campoEmail.sendKeys(email);
		
		return this;
	}
	
	public UsuarioPage enviaFormulario() {
		WebElement botaoDeEnviar = navegador.findElement(By.id("btnSalvar"));
		botaoDeEnviar.submit();
		
		return this;
	}
	
	public boolean validaSePossui(String nome, String email) {

		String htmlDaPagina = navegador.getPageSource();
		
		boolean temNome = htmlDaPagina.contains(nome);
		boolean temEmail = htmlDaPagina.contains(email);
		
		return temNome && temEmail;
	}

	public UsuarioPage clicaNoBotaoExcluir() {
		WebElement botaoDeExcluir = navegador.findElement(By.tagName("button"));
		botaoDeExcluir.click();
		
		return this;
	}

	public UsuarioPage confirmaExclusao() {
		Alert alert = navegador.switchTo().alert();
		alert.accept();
		
		return this;
	}
	
	public boolean validaSeNaoPossui(String nome, String email) {
		String htmlDaPagina = navegador.getPageSource();
		
		boolean temNome = htmlDaPagina.contains(nome);
		boolean temEmail = htmlDaPagina.contains(email);
		
		return !temNome && !temEmail;
	}

	public boolean validaMensagemDeErroCom(String campo) {
		String htmlDaPagina = navegador.getPageSource();
		
		boolean obrigatorio = htmlDaPagina.contains(campo);
		
		return obrigatorio;
	}
}
