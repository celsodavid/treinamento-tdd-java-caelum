package br.com.caelum.leilao.service;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Leilao;

public class LeilaoTest {
	
	@Test
	public void testeLeilaoSemProduto() {
		Leilao leilao = new Leilao(null);
		
		assertNull(leilao.getDescricao());	
	}
}
