package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.InterfaceEnviadorDeEmail;

public class EnviadorDeEmailFalso implements InterfaceEnviadorDeEmail {

	public void envia(Leilao leilao) {
		
	}	
}