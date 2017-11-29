package br.com.caelum.leilao.service;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class TesteAvaliadorOrdemDecrescente {
	public static void main(String[] args) {
		Usuario celso = new Usuario("Celso");
		Usuario jose = new Usuario("Jose");
		Usuario lais = new Usuario("Lais");
		
		Leilao leilao = new Leilao("Playstation 4 Slim");
		
		leilao.propoe(new Lance(jose, 800));
		leilao.propoe(new Lance(celso, 600));		
		leilao.propoe(new Lance(lais, 300));
		
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		System.out.println("Teste Ordem Decrescente");
		System.out.println(leiloeiro.getMaiorDeTodos() == 800);
		System.out.println(leiloeiro.getMenorDeTodos() == 300);
	}
}
