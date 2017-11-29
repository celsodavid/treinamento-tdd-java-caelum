package br.com.caelum.leilao.infra.dao;

import br.com.caelum.leilao.dominio.Leilao;

public interface InterfaceEnviadorDeEmail {
	void envia(Leilao leilao);
}
