package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {
	private String descricao;
	private List<Lance> lances = new ArrayList<>();
	
	public Leilao(String descricao) {
		if (null == descricao) {
			throw new IllegalArgumentException("Descricao Invalida!");
		}
		
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public List<Lance> getLances() {
		return Collections.unmodifiableList(this.lances);
	}
	
	public void propoe(Lance lance) {
		lances.add(lance);
	}
}
