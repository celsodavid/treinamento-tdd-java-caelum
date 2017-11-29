package br.com.caelum.leilao.servico;

import java.util.Calendar;
import java.util.List;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.RepositorioDePagamentos;
import br.com.caelum.leilao.infra.dao.InterfaceLeilaoDao;

public class GeradorDePagamentos {
	private final Relogio calendario;
	private final RepositorioDePagamentos pagamentos;
	private final InterfaceLeilaoDao leilao;
	private final Avaliador avaliador;
	
	public GeradorDePagamentos(RepositorioDePagamentos pagamentos, InterfaceLeilaoDao leilao, Avaliador avaliador, Relogio calendario) {
		this.pagamentos = pagamentos;
		this.leilao = leilao;
		this.avaliador = avaliador;
		this.calendario = calendario;
	}
	
	public GeradorDePagamentos(RepositorioDePagamentos pagamentos, InterfaceLeilaoDao leilao, Avaliador avaliador) {
		this(pagamentos, leilao, avaliador, new CalendarioDoSistema());
	}
	
	public void gera() {
		List<Leilao> leiloesEncerrados = leilao.encerrados();
		
		for (Leilao leilao : leiloesEncerrados) {
			avaliador.avalia(leilao);
			
			Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), proximoDiaUtil());
			pagamentos.salva(novoPagamento);
		}
	}
	
	public Calendar proximoDiaUtil() {
		Calendar diaUtil = calendario.hoje();
		int diaDaSemana = diaUtil.get(Calendar.DAY_OF_WEEK);
		
		if (diaDaSemana == Calendar.SATURDAY) {
			diaUtil.add(Calendar.DAY_OF_MONTH, 2);
		} else if (diaDaSemana == Calendar.SUNDAY) {
			diaUtil.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return diaUtil;
	}
}
