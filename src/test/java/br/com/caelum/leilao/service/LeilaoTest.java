package br.com.caelum.leilao.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {
	
	private Leilao leilao;
	private Usuario savage;
	private Usuario stark;
	
	@Before
	public void init() { // roda antes de cada teste
		this.leilao = new Leilao("Playstation 4 Slim");
		this.savage = new Usuario("Savage");
		this.stark = new Usuario("Stark");
	}
	
	/*@Test
	public void testeLeilaoSemProduto() {
		Leilao leilao = new Leilao(null);
		
		assertNull(leilao.getDescricao());	
	}*/
	
	@Test
	public void testeDeveReceberUmLance() {
		// Leilao leilao = new Leilao("Playstation 4 Slim");
		init();
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Savage"), 200));
		assertEquals(1, leilao.getLances().size());
		assertEquals(200, leilao.getLances().get(0).getValor(), 0.0001);
	}
	
	@Test
	public void testeDeveReceberVariosLances() {
		//Leilao leilao = new Leilao("Playstation 4 Slim");
		init();
		leilao.propoe(new Lance(new Usuario("Savage"), 200));
		leilao.propoe(new Lance(new Usuario("Tonny Stark"), 1000));
		
		assertEquals(2, leilao.getLances().size());
		assertEquals(200, leilao.getLances().get(0).getValor(), 0.0001);
		assertEquals(1000, leilao.getLances().get(1).getValor(), 0.0001);
	}
	
	@Test
	public void testeNaoDeveAceitarDoisLancesSeguidos() {
		//Leilao leilao = new Leilao("Playstation 4 Slim");
		//Usuario savage = new Usuario("Savage");
		init();
		
		leilao.propoe(new Lance(savage, 200));
		leilao.propoe(new Lance(savage, 1000));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(200, leilao.getLances().get(0).getValor(), 0.0001);
	}
	
	@Test
	public void testeNaoDeveAceitarMaisQue5LancesPorUsuario() {
		init();
		
		leilao.propoe(new Lance(savage, 200));
		leilao.propoe(new Lance(stark, 300));
		leilao.propoe(new Lance(savage, 400));
		leilao.propoe(new Lance(stark, 500));
		leilao.propoe(new Lance(savage, 600));
		leilao.propoe(new Lance(stark, 700));
		leilao.propoe(new Lance(savage, 800));
		leilao.propoe(new Lance(stark, 900));
		leilao.propoe(new Lance(savage, 1000));
		leilao.propoe(new Lance(stark, 2000));
		
		leilao.propoe(new Lance(savage, 3000));
		
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
