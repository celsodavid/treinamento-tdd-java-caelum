package br.com.caelum.leilao.dominio;

public class TesteLeilaoSemProduto {

	public static void main(String[] args) {
		Leilao leilao = new Leilao(null);
		
		System.out.println(null == leilao.getDescricao());
	}

}
