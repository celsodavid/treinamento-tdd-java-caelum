package br.com.caelum.leilao.test.aceitacao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.Assert;

public class FormularioTest {

	/* /apenas-teste/limpa */
	
	@Test
	public void testDeveCadastrarUsuario() {
		WebDriver navegador = new FirefoxDriver();
		navegador.navigate().to("http://localhost:8080/usuarios/new");
		
		WebElement campoNome = navegador.findElement(By.name("usuario.nome"));
		campoNome.sendKeys("Adriano Xavier");
		
		WebElement campoEmail = navegador.findElement(By.name("usuario.email"));
		campoEmail.sendKeys("adriano@empresa.com");
		
		campoEmail.submit();
		
		String html = navegador.getPageSource();
		
		navegador.close();
		
		boolean encontrouNome = html.contains("Adriano Xavier");
		boolean encontrouEmail = html.contains("adriano@empresa.com");
		
		assertTrue(encontrouNome);
		assertTrue(encontrouEmail);
	}

}
