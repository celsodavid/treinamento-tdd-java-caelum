package br.com.caelum.leilao.service;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class TesteAvaliadorComUmLance {
	public static void main(String[] args) {
		Usuario celso = new Usuario("Celso");
		
		Leilao leilao = new Leilao("Playstation 4 Slim");
		leilao.propoe(new Lance(celso, 400));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		System.out.println("Teste Com Um Lance");
		System.out.println(leiloeiro.getMaiorDeTodos() == 400);
		System.out.println(leiloeiro.getMenorDeTodos() == 400);
	}
}
