package br.com.caelum.leilao.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
// import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario celso;
	private Usuario jose;
	private Usuario lais;
	private Usuario edu;

	@Rule
	public ExpectedException thrower = ExpectedException.none();

	@Before
	public void setUp() {
		System.out.println("Inicia");

		leiloeiro = new Avaliador();

		this.celso = new Usuario("Celso");
		this.jose = new Usuario("Jose");
		this.lais = new Usuario("Lais");
		this.edu = new Usuario("Edu");
	}

	@After
	public void finaliza() {
		System.out.println("Fim do Teste");
	}

	@BeforeClass
	public static void iniciaClasse() {
		System.out.println("Iniciando classe de teste.");
	}

	@AfterClass
	public static void finalizaClasse() {
		System.out.println("Finalizando classe de teste.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void qlqrTest() {
		setUp();

		Leilao leilao = new Leilao("Playstation 4 Slim");
		leiloeiro.avalia(leilao);
	}

	@Test
	public void testeAvaliadorEmOrdemAleatoria() {
		/*
		 * Como criar os testes antes Usuario celso = new Usuario("Celso");
		 * Usuario jose = new Usuario("Jose"); Usuario lais = new
		 * Usuario("Lais"); Usuario edu = new Usuario("Edu");
		 * 
		 * Leilao leilao = new Leilao("Playstation 4 Slim");
		 * 
		 * leilao.propoe(new Lance(celso, 600)); leilao.propoe(new Lance(lais,
		 * 300)); leilao.propoe(new Lance(jose, 800)); leilao.propoe(new
		 * Lance(edu, 400));
		 * 
		 * Avaliador leiloeiro = new Avaliador(); 
		 * leiloeiro.avalia(leilao);
		 * 
		 * assertEquals(800, leiloeiro.getMaiorDeTodos(), 0.000000001);
		 * assertEquals(300, leiloeiro.getMenorDeTodos(), 0.000000001); 
		 * assertEquals(525, leiloeiro.getValorMedio(), 0.0001);
		 * 
		 * List<Lance> lance = leiloeiro.getTresMaiores();
		 * assertEquals(3, lance.size());
		 * assertEquals(800, lance.get(0).getValor(), 0.000000001);
		 * assertEquals(600, lance.get(1).getValor(), 0.000000001);
		 * assertEquals(400, lance.get(2).getValor(), 0.000000001);
		 */

		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Slim").lanceDo(celso, 600.0).lanceDo(jose, 300.0)
				.lanceDo(lais, 800.0).lanceDo(edu, 400.0).constroi();

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorDeTodos(), equalTo(800.0));
		assertThat(leiloeiro.getMenorDeTodos(), equalTo(300.0));
		assertThat(leiloeiro.getValorMedio(), equalTo(525.0));

		List<Lance> lance = leiloeiro.getTresMaiores();		
		assertThat(lance.size(), equalTo(3));
		assertThat(lance, hasItems(new Lance(celso, 600.0), new Lance(lais, 800.0), new Lance(edu, 400.0)));		
	}

	@Test
	public void testeAvaliadorOrdemCrescente() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Slim").lanceDo(celso, 300).lanceDo(jose, 600)
				.lanceDo(lais, 800).constroi();

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorDeTodos(), equalTo(800.0));
		assertThat(leiloeiro.getMenorDeTodos(), equalTo(300.0));
		assertThat(leiloeiro.getValorMedio(), equalTo(566.6666666666666));
		
		List<Lance> lance = leiloeiro.getTresMaiores();
		assertThat(lance.size(), equalTo(3));
		assertThat(lance, hasItems(new Lance(celso, 300.0), new Lance(jose, 600.0), new Lance(lais, 800.0)));
	}

	@Test
	public void testeAvaliadorOrdemDecrescente() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Slim").lanceDo(celso, 600).lanceDo(jose, 300)
				.lanceDo(lais, 800).constroi();

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorDeTodos(), equalTo(800.0));
		assertThat(leiloeiro.getMenorDeTodos(), equalTo(300.0));
		assertThat(leiloeiro.getValorMedio(), equalTo(566.6666666666666));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testeAvaliadorComSemLance() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Slim").constroi();
		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorDeTodos(), equalTo(0.0));
		assertThat(leiloeiro.getMenorDeTodos(), equalTo(0.0));
		assertThat(leiloeiro.getValorMedio(), equalTo(0.0));
		assertThat(leiloeiro.getTresMaiores().size(), equalTo(0));
	}

	@Test
	public void testeAvaliadorComUmLance() {
		Usuario celso = new Usuario("Celso");

		Leilao leilao = new Leilao("Playstation 4 Slim");
		leilao.propoe(new Lance(celso, 400.0));

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorDeTodos(), equalTo(400.0));
		assertThat(leiloeiro.getMenorDeTodos(), equalTo(400.0));
		
		List<Lance> lance = leiloeiro.getTresMaiores();
		assertThat(lance.size(), equalTo(1));
		assertThat(lance, hasItem(new Lance(celso, 400.0)));
	}

	@Test
	public void testGaranteQueLeilaoSemLancesDeveGerarException() {
		// excecoes
		thrower.expect(IllegalArgumentException.class);
		thrower.expectMessage("Leilao deve possuir lances para ser avaliado");

		Leilao leilao = new CriadorDeLeilao().para("TV").constroi();
		leiloeiro.avalia(leilao);
	}
}
