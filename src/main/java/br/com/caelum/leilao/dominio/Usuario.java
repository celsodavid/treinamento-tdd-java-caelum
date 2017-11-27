package br.com.caelum.leilao.dominio;

public class Usuario {
	private String nome;
	private int id;
	
	public Usuario(String nome, int id) {
		this.nome = nome;
		this.id = id;
	}
	
	public Usuario(String nome) {
		this(nome, 0);
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getId() {
		return id;
	}
}
