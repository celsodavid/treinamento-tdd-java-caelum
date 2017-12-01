package br.com.caelum.leilao.test.page.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LancePage {
	
	private WebDriver navegador;
	
	public LancePage(WebDriver navegador) {
		this.navegador = navegador;
	}

	public LancePage propoeLance(String nome, Double valor) {
		WebElement comboBoxUsuario = navegador.findElement(By.name("lance.usuario.id"));
		Select select = new Select(comboBoxUsuario);		
		select.selectByVisibleText(nome);
		
		
		WebElement campoValor = navegador.findElement(By.name("lance.valor"));
		campoValor.sendKeys(valor.toString());
		
		WebElement btnDarLance = navegador.findElement(By.id("btnDarLance"));
		btnDarLance.click();
		
		return this;
	}

	public boolean validaQueExisteLance(String nome, Double valor) {
		WebElement table = navegador.findElement(By.id("lancesDados"));
		
		WebDriverWait navegradorEsperando = new WebDriverWait(navegador, 5);
		navegradorEsperando.until(ExpectedConditions.textToBePresentInElement(table, nome));
		
		String htmlDaPagina = navegador.getPageSource();
		
		boolean contemNome = htmlDaPagina.contains(nome);
		boolean contemValor = htmlDaPagina.contains(valor.toString());
		
		return contemNome && contemValor;
	}
	
	
}
