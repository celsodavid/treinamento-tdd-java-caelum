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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Usuario other = (Usuario) obj;
		if (id != other.id) return false;
		
		if (nome == null) {
			if (other.nome != null) 
				return false;
		} else if(!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}
}
