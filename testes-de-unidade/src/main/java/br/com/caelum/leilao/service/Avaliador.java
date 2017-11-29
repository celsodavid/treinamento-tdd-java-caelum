package br.com.caelum.leilao.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {
	private double valorMedio;
	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Lance> maiores = new ArrayList<>();
	
	public void avalia(Leilao leilao) {
		if (leilao.getLances().isEmpty()) {
			throw new IllegalArgumentException("Leilao deve possuir lances para ser avaliado");
		}
		
		for (Lance lance : leilao.getLances()) {
			if (lance.getValor() > maiorDeTodos) {
				this.maiorDeTodos = lance.getValor();
			}
			
			if(lance.getValor() < menorDeTodos) {
				this.menorDeTodos = lance.getValor();
			}
			
			valorMedio += lance.getValor();
		}
		
		valorMedio = valorMedio / leilao.getLances().size();
		
		pegaOsMaiores(leilao);			
	}
	
	public double getMaiorDeTodos() {
		return maiorDeTodos;
	}
	
	public double getMenorDeTodos() {
		return menorDeTodos;
	}
	
	public double getValorMedio() {
		return valorMedio;
	}
	
	public List<Lance> getTresMaiores() {
		return this.maiores;
	}
	
	private void pegaOsMaiores(Leilao leilao) {
		maiores = new ArrayList<>(leilao.getLances());
		Collections.sort(maiores, new Comparator<Lance>() {

			@Override
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() == o2.getValor()) {
					return 0;
				}
				
				if (o1.getValor() < o2.getValor()) {
					return 1;
				}
				
				return -1;
			}
		});
		
		maiores = maiores.subList(0, maiores.size() >= 3 ? 3 : maiores.size());
	}
}
