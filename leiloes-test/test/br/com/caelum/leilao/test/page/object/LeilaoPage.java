package br.com.caelum.leilao.test.page.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LeilaoPage {
	
	private WebDriver navegador;
	
	public LeilaoPage(WebDriver navegador) {
		this.navegador = navegador;
	}

	public LeilaoPage vaiParaListaDeLeiloes() {
		navegador.navigate().to("http://localhost:8080/leiloes");
		return this;
	}
	
	public LeilaoPage vaiParaPaginaDeCadastro() {
		WebElement botaoDeNovo = navegador.findElement(By.linkText("Novo Leilão"));
		botaoDeNovo.click();
		
		return this;
	}
	
	public LeilaoPage preencherFormularioCom(String produto, double valor, String usuario, boolean usado) {
		WebElement nome = navegador.findElement(By.name("leilao.nome"));
		nome.sendKeys(produto);
		
		WebElement valorInicial = navegador.findElement(By.name("leilao.valorInicial"));
		valorInicial.sendKeys(String.valueOf(valor));
		
		WebElement comboBoxUsuario = navegador.findElement(By.name("leilao.usuario.id"));
		Select select = new Select(comboBoxUsuario);		
		select.selectByVisibleText(usuario);
		
		WebElement checkBoxUsado = navegador.findElement(By.name("leilao.usado"));
		
		if (usado)
			checkBoxUsado.click();
		
		return this;
	}
	
	public LeilaoPage enviaFormulario() {
		WebElement btnSalvar = navegador.findElement(By.tagName("button"));
		btnSalvar.click();
		
		return this;

	}
	
	public boolean verificaSeLeilaoFoiCriado(String produto, double valor, String usuario, boolean usado) {		
		String htmlDaPagina = navegador.getPageSource();
		
		boolean temProduto = htmlDaPagina.contains(produto);
		boolean temValor = htmlDaPagina.contains(String.valueOf(valor));
		boolean temUsuario = htmlDaPagina.contains(usuario);
		boolean temUsado = htmlDaPagina.contains(usado ? "Sim" : "Não");
		
		return temProduto && temValor && temUsuario && temUsado && temUsado;
	}	
	
	public boolean verificaSeLeilaoNaoFoiCriadoSem(String string) {		
		String htmlDaPagina = navegador.getPageSource();
		
		boolean campoObrigatorio = htmlDaPagina.contains(string);
		
		return campoObrigatorio;
	}

	public LancePage exibeLeilao() {
		LancePage lancePage = new LancePage(navegador);
		
		WebElement btnExibeLeilao = navegador.findElement(By.linkText("exibir"));
		btnExibeLeilao.click();
				
		return lancePage;
	}
}
