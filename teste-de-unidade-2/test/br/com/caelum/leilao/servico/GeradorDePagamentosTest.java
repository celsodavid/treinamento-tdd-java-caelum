package br.com.caelum.leilao.servico;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.RepositorioDePagamentos;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.dao.InterfaceLeilaoDao;

public class GeradorDePagamentosTest {

	private Usuario jose;
	private Usuario maria;

	@Before
	public void setUp() {
		jose = new Usuario("Jose");
		maria = new Usuario("Maria");
	}
	
	@Test
	public void testDeveGerarPagamentoParaUmLeilaoEncerrado() {
		InterfaceLeilaoDao dao = mock(InterfaceLeilaoDao.class);
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		Avaliador avaliador = mock(Avaliador.class);
		CalendarioDoSistema calendario = new CalendarioDoSistema();
		
		Leilao leilao = new CriadorDeLeilao()
			.para("Tv Plamas 49")
			.naData(Calendar.getInstance())
			.lance(jose, 600.0)
			.lance(maria, 200.00)
			.constroi()
		;
		
		when(dao.encerrados()).thenReturn(Arrays.asList(leilao));
		when(avaliador.getMaiorLance()).thenReturn(600.0);
		
		GeradorDePagamentos geradorDePagamentos = new GeradorDePagamentos(pagamentos, dao, avaliador, calendario);
		geradorDePagamentos.gera();
		
		ArgumentCaptor<Pagamento> pagamento = ArgumentCaptor.forClass(Pagamento.class);	
		verify(pagamentos).salva(pagamento.capture());
		
		Pagamento pagamentoGerado = pagamento.getValue();
		assertThat(pagamentoGerado.getValor(), equalTo(600.0));
	}

	@Test
	public void testDeveGerarPagamentoParaUmLeilaoEncerrado2() {
		InterfaceLeilaoDao dao = mock(InterfaceLeilaoDao.class);
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		Avaliador avaliador = new Avaliador();
		CalendarioDoSistema calendario = new CalendarioDoSistema();
		
		Leilao leilao = new CriadorDeLeilao()
			.para("Tv Plamas 49")
			.naData(Calendar.getInstance())
			.lance(jose, 600.0)
			.lance(maria, 200.00)
			.constroi()
		;
		
		when(dao.encerrados()).thenReturn(Arrays.asList(leilao));
		
		GeradorDePagamentos geradorDePagamentos = new GeradorDePagamentos(pagamentos, dao, avaliador, calendario);
		geradorDePagamentos.gera();
		
		ArgumentCaptor<Pagamento> pagamento = ArgumentCaptor.forClass(Pagamento.class);	
		verify(pagamentos).salva(pagamento.capture());
		
		Pagamento pagamentoGerado = pagamento.getValue();
		assertThat(pagamentoGerado.getValor(), equalTo(600.0));
	}
	
	@Test
	public void testDeveGerarPagamentoParaLeilaosEncerrados() {
		InterfaceLeilaoDao dao = mock(InterfaceLeilaoDao.class);
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		Avaliador avaliador = new Avaliador();
		CalendarioDoSistema calendario = new CalendarioDoSistema();
		
		Leilao leilao1 = new CriadorDeLeilao()
			.para("Tv Plamas 49")
			.naData(Calendar.getInstance())
			.lance(jose, 600.0)
			.lance(maria, 200.0)
			.constroi()
		;
		
		Leilao leilao2 = new CriadorDeLeilao()
			.para("Tv Plamas 49")
			.naData(Calendar.getInstance())
			.lance(jose, 40.0)
			.lance(maria, 90.0)
			.constroi()
		;		
		
		when(dao.encerrados()).thenReturn(Arrays.asList(leilao1));
		when(dao.encerrados()).thenReturn(Arrays.asList(leilao2));
		
		GeradorDePagamentos geradorDePagamentos = new GeradorDePagamentos(pagamentos, dao, avaliador, calendario);
		geradorDePagamentos.gera();
		
		ArgumentCaptor<Pagamento> pagamento = ArgumentCaptor.forClass(Pagamento.class);	
		verify(pagamentos).salva(pagamento.capture());
		
		Pagamento pagamentoGerado = pagamento.getValue();
		assertThat(pagamentoGerado.getValor(), equalTo(90.0));
		// faltou item aqui
	}
	
	@Test
	public void testGerarPagamentoNoMesmoDiaSeODiaForUtil() {
		Calendar hoje = new GregorianCalendar(2017, Calendar.NOVEMBER, 28);
		Leilao leilao = new CriadorDeLeilao().para("TV").naData(hoje).lance(maria, 100).constroi();
		
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		InterfaceLeilaoDao dao = mock(InterfaceLeilaoDao.class);
		Avaliador avaliador = mock(Avaliador.class);
		CalendarioDoSistema calendario = mock(CalendarioDoSistema.class);
		
		when(calendario.hoje()).thenReturn(hoje);
		
		when(dao.encerrados()).thenReturn(Arrays.asList(leilao));
		
		GeradorDePagamentos geradorDePagamentos = new GeradorDePagamentos(pagamentos, dao, avaliador, calendario);
		geradorDePagamentos.gera();
		
		ArgumentCaptor<Pagamento> pagamento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(pagamento.capture());
		
		Pagamento pagamentoGerado = pagamento.getValue();
		assertThat(pagamentoGerado.getData(), equalTo(hoje));
	}
	
	@Test
	public void testGerarPagamentoProximoDiaUtilCasoForDomingo() {
		Calendar domingo = new GregorianCalendar(20017, Calendar.NOVEMBER, 26);
		Calendar segunda = new GregorianCalendar(20017, Calendar.NOVEMBER, 27);
		
		Leilao leilao = new CriadorDeLeilao().para("TV").naData(domingo).lance(maria, 100).constroi();
		
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		InterfaceLeilaoDao dao = mock(InterfaceLeilaoDao.class);
		Avaliador avaliador = mock(Avaliador.class);
		CalendarioDoSistema calendario = mock(CalendarioDoSistema.class);
		
		when(calendario.hoje()).thenReturn(domingo);
		when(dao.encerrados()).thenReturn(Arrays.asList(leilao));
			
		GeradorDePagamentos geradorDePagamentos = new GeradorDePagamentos(pagamentos, dao, avaliador, calendario);
		geradorDePagamentos.gera();
		
		ArgumentCaptor<Pagamento> pagamento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(pagamento.capture());
		
		Pagamento pagamentoGerado = pagamento.getValue();
		assertThat(pagamentoGerado.getData(), equalTo(segunda));
	}
	
	@Test
	public void testGerarPagamentoProximoDiaUtilCasoForSabado() {
		Calendar sabado = new GregorianCalendar(20017, Calendar.NOVEMBER, 25);
		Calendar segunda = new GregorianCalendar(20017, Calendar.NOVEMBER, 27);
		
		Leilao leilao = new CriadorDeLeilao().para("TV").naData(sabado).lance(maria, 100).constroi();
		
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		InterfaceLeilaoDao dao = mock(InterfaceLeilaoDao.class);
		Avaliador avaliador = mock(Avaliador.class);
		CalendarioDoSistema calendario = mock(CalendarioDoSistema.class);
		
		when(calendario.hoje()).thenReturn(sabado);
		when(dao.encerrados()).thenReturn(Arrays.asList(leilao));
			
		GeradorDePagamentos geradorDePagamentos = new GeradorDePagamentos(pagamentos, dao, avaliador, calendario);
		geradorDePagamentos.gera();
		
		ArgumentCaptor<Pagamento> pagamento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(pagamento.capture());
		
		Pagamento pagamentoGerado = pagamento.getValue();
		assertThat(pagamentoGerado.getData(), equalTo(segunda));
	}
}
