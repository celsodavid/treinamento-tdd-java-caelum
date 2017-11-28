package br.com.caelum.leilao.service;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

// teste data builder
public class CriadorDeLeilao {
	private Leilao leilao;
	
	public CriadorDeLeilao para(String produto) {
		this.leilao = new Leilao(produto);
		return this;
	}
	
	public CriadorDeLeilao lanceDo(Usuario usuario, double valor) {
		leilao.propoe(new Lance(usuario, valor));
		return this;
	}
	
	public Leilao constroi() {
		return leilao;
	}
}
