package br.com.caelum.leilao.service;

import br.com.caelum.leilao.dominio.Leilao;

public class TesteAvaliadorComSemLance {
	public static void main(String[] args) {
		Leilao leilao = new Leilao("Playstation 4 Slim");
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		System.out.println("Teste Sem Lance");
		System.out.println(leiloeiro.getMaiorDeTodos() == 0);
		System.out.println(leiloeiro.getMenorDeTodos() == 0);
	}
}
