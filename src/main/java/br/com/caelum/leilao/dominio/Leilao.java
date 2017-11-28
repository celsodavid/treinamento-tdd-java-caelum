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
		if (lances.isEmpty() || (!ultimoLance().getUsuario().equals(lance.getUsuario()) && gtdLancesUsuario(lance.getUsuario()) < 5)) {
			lances.add(lance);
		}
	}
	
	protected Lance ultimoLance() {
		return lances.get(lances.size()-1);
	}
	
	protected int gtdLancesUsuario(Usuario usuario) {
		int aux = 0;
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				aux++;
		}
		
		return aux;
	}
	
	private boolean podeDarLance() {
		return true;
	}
}
