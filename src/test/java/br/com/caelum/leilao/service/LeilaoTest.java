package br.com.caelum.leilao.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {
	
	private Usuario savage;
	private Usuario stark;
	
	@Before
	public void init() { /* roda antes de cada teste */
		this.savage = new Usuario("Savage");
		this.stark = new Usuario("Stark");
	}

	@Test
	public void testeDeveReceberUmLance() {
		// Leilao leilao = new Leilao("Playstation 4 Slim");
		Leilao leilao = new CriadorDeLeilao().para("TV").lanceDo(savage, 200).constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(200, leilao.getLances().get(0).getValor(), 0.0001);
	}
	
	@Test
	public void testeDeveReceberVariosLances() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Slim").lanceDo(savage, 200)
				.lanceDo(stark, 1000).constroi();
		
		assertEquals(2, leilao.getLances().size());
		assertEquals(200, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(1000, leilao.getLances().get(1).getValor(), 0.0001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLeilaoSemProdutoDeveGerarException() {
		Leilao leilao = new CriadorDeLeilao().para(null).constroi();
	}
	
	@Test
	public void testeNaoDeveAceitarDoisLancesSeguidos() {
		Leilao leilao = new CriadorDeLeilao().para("TV")
				.lanceDo(savage, 200)
				.lanceDo(savage, 1000)
				.constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(200, leilao.getLances().get(0).getValor(), 0.0001);
	}
	
	@Test
	public void testeNaoDeveAceitarMaisQue5LancesPorUsuario() {
		Leilao leilao = new CriadorDeLeilao().para("TV")
				.lanceDo(savage, 200)
				.lanceDo(stark, 300)
				.lanceDo(savage, 400)
				.lanceDo(stark, 500)
				.lanceDo(savage, 600)
				.lanceDo(stark, 700)
				.lanceDo(savage, 800)
				.lanceDo(stark, 900)
				.lanceDo(savage, 1000)
				.lanceDo(stark, 2000)
				.lanceDo(savage, 3000)
				.constroi();
		
		assertEquals(10, leilao.getLances().size());
		
		int ultimo = leilao.getLances().size() - 1;
		
		Lance ultimoLance = leilao.getLances().get(ultimo);
		assertEquals(2000, ultimoLance.getValor(), 0.0001);
	}

	/*@Test
	public void testeLanceNaoPodeSerMenorQueAnterior() {
		Leilao leilao = new Leilao("Playstation 4 Slim");
		
		Usuario celso = new Usuario("Celso");
		Usuario mirtes = new Usuario("Mirtes");
		
	}*/
}
