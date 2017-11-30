package br.com.caelum.pm73.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TesteSelenium {

	@Test
	public void testDeveEncontrarCaelum() throws InterruptedException {
		WebDriver navegador = new FirefoxDriver();
		navegador.navigate().to("https://www.google.com.br/");

		WebElement campoDeBusca = navegador.findElement(By.name("q"));
		campoDeBusca.sendKeys("TDD");
		campoDeBusca.submit();

		Thread.sleep(20*1000);

		navegador.close();
		
		String html = navegador.getPageSource();

		System.out.println(html);
		boolean contains = html.contains("Caelum");
		assertTrue(contains);
	}
}
