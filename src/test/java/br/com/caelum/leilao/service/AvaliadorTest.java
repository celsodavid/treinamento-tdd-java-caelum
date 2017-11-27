package br.com.caelum.leilao.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	
	@Test
	public void testeAvaliadorEmOrdemAleatoria() {
		Usuario celso = new Usuario("Celso");
		Usuario jose = new Usuario("Jose");
		Usuario lais = new Usuario("Lais");
		Usuario edu = new Usuario("Edu");
		
		Leilao leilao = new Leilao("Playstation 4 Slim");
		
		leilao.propoe(new Lance(celso, 600));
		leilao.propoe(new Lance(lais, 300));
		leilao.propoe(new Lance(jose, 800));
		leilao.propoe(new Lance(edu, 400));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(800, leiloeiro.getMaiorDeTodos(), 0.000000001);
		assertEquals(300, leiloeiro.getMenorDeTodos(), 0.000000001);
		assertEquals(525, leiloeiro.getValorMedio(), 0.0001);
		
		List<Lance> lance = leiloeiro.getTresMaiores();
		assertEquals(3, lance.size());
		assertEquals(800, lance.get(0).getValor(), 0.000000001);
		assertEquals(600, lance.get(1).getValor(), 0.000000001);
		assertEquals(400, lance.get(2).getValor(), 0.000000001);
	}
	
	@Test
	public void testeAvaliadorOrdemCrescente() {
		Usuario celso = new Usuario("Celso");
		Usuario jose = new Usuario("Jose");
		Usuario lais = new Usuario("Lais");
		
		Leilao leilao = new Leilao("Playstation 4 Slim");
		
		leilao.propoe(new Lance(lais, 300));
		leilao.propoe(new Lance(celso, 600));
		leilao.propoe(new Lance(jose, 800));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(800, leiloeiro.getMaiorDeTodos(), 0.000000001);
		assertEquals(300, leiloeiro.getMenorDeTodos(), 0.000000001);
		assertEquals(566.6666, leiloeiro.getValorMedio(), 0.0001);
		
		List<Lance> lance = leiloeiro.getTresMaiores();
		assertEquals(3, lance.size());
		assertEquals(800, lance.get(0).getValor(), 0.000000001);
		assertEquals(600, lance.get(1).getValor(), 0.000000001);
		assertEquals(300, lance.get(2).getValor(), 0.000000001);
	}
	
	@Test
	public void testeAvaliadorOrdemDecrescente() {
		Usuario celso = new Usuario("Celso");
		Usuario jose = new Usuario("Jose");
		Usuario lais = new Usuario("Lais");
		
		Leilao leilao = new Leilao("Playstation 4 Slim");
		
		leilao.propoe(new Lance(jose, 800));
		leilao.propoe(new Lance(celso, 600));		
		leilao.propoe(new Lance(lais, 300));
		
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(800, leiloeiro.getMaiorDeTodos(), 0.000000001);
		assertEquals(300, leiloeiro.getMenorDeTodos(), 0.000000001);
		assertEquals(566.6666, leiloeiro.getValorMedio(), 0.0001);
	}
	
	@Test
	public void testeAvaliadorComSemLance() {
		Leilao leilao = new Leilao("Playstation 4 Slim");
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(0, leiloeiro.getMaiorDeTodos(), 0.000000001);
		assertEquals(0, leiloeiro.getMenorDeTodos(), 0.000000001);
		assertEquals(0, leiloeiro.getValorMedio(), 0.0001); /* obrigatorio */
		assertEquals(0, leiloeiro.getTresMaiores().size());
	}
	
	@Test
	public void testeAvaliadorComUmLance() {
		Usuario celso = new Usuario("Celso");
		
		Leilao leilao = new Leilao("Playstation 4 Slim");
		leilao.propoe(new Lance(celso, 400));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(400, leiloeiro.getMaiorDeTodos(), 0.000000001);
		assertEquals(400, leiloeiro.getMenorDeTodos(), 0.000000001);
		
		List<Lance> lance = leiloeiro.getTresMaiores();
		assertEquals(1, lance.size());
		assertEquals(400, lance.get(0).getValor(), 0.000000001);
	}
	
	
}
