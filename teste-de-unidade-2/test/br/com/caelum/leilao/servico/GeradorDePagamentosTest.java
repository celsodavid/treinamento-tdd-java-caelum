package br.com.caelum.leilao.servico;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.RepositorioDePagamentos;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.dao.InterfaceLeilaoDao;

@RunWith(MockitoJUnitRunner.class)
public class GeradorDePagamentosTest {

	private Usuario jose;
	private Usuario maria;
	private Calendar domingo;
	private Calendar sabado;
	private Calendar segunda;
	private Leilao leilao;
	private Leilao leilao2;
	private Avaliador avaliador;
	private GeradorDePagamentos geradorDePagamentos;
	
	@Mock
	private InterfaceLeilaoDao leilaoDao;
	@Mock
	private RepositorioDePagamentos pagamentos;
	@Mock
	private CalendarioDoSistema calendario;
	@Captor
	private ArgumentCaptor<Pagamento> captor;

	@Before
	public void setUp() {
		jose = new Usuario("Jose");
		maria = new Usuario("Maria");
		
		avaliador = new Avaliador();
		
		domingo = new GregorianCalendar(20017, Calendar.NOVEMBER, 26);
		sabado = new GregorianCalendar(20017, Calendar.NOVEMBER, 25);
		segunda = new GregorianCalendar(20017, Calendar.NOVEMBER, 27);
		
		leilao = new CriadorDeLeilao().para("Tv Plamas 49").naData(segunda).lance(maria, 100).lance(jose, 600).constroi();
		leilao2 = new CriadorDeLeilao().para("Tv 4k").naData(segunda).lance(jose, 500).lance(maria, 200).constroi();
		
		geradorDePagamentos = new GeradorDePagamentos(pagamentos, leilaoDao, avaliador, calendario);
	}
	
	@Test
	public void testDeveGerarPagamentoParaUmLeilaoEncerrado() {
		
		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));
		when(calendario.hoje()).thenReturn(segunda);
		
		geradorDePagamentos.gera();
		
		verify(pagamentos).salva(captor.capture());
		
		Pagamento pagamentoGerado = captor.getValue();
		assertThat(pagamentoGerado.getValor(), equalTo(600.0));
	}

	@Test
	public void testDeveGerarPagamentoParaUmLeilaoEncerrado2() {
		
		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));
		when(calendario.hoje()).thenReturn(segunda);
		
		geradorDePagamentos.gera();
		
		verify(pagamentos).salva(captor.capture());
		
		Pagamento pagamentoGerado = captor.getValue();
		assertThat(pagamentoGerado.getValor(), equalTo(600.0));
	}
	
	@Test
	public void testDeveGerarPagamentoParaLeilaosEncerrados() {
		
		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));
		when(calendario.hoje()).thenReturn(segunda);
		
		geradorDePagamentos.gera();
			
		verify(pagamentos).salva(captor.capture());
		
		Pagamento pagamentoGerado = captor.getValue();
		assertThat(pagamentoGerado.getValor(), equalTo(600.0));
		// faltou item aqui
	}
	
	@Test
	public void testGerarPagamentoNoMesmoDiaSeODiaForUtil() {
		
		when(calendario.hoje()).thenReturn(segunda);		
		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));
		
		geradorDePagamentos.gera();
		
		verify(pagamentos).salva(captor.capture());
		
		Pagamento pagamentoGerado = captor.getValue();
		assertThat(pagamentoGerado.getData(), equalTo(segunda));
	}
	
	@Test
	public void testGerarPagamentoProximoDiaUtilCasoForDomingo() {
		
		when(calendario.hoje()).thenReturn(domingo);
		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));
			
		geradorDePagamentos.gera();
		
		verify(pagamentos).salva(captor.capture());
		
		Pagamento pagamentoGerado = captor.getValue();
		assertThat(pagamentoGerado.getData(), equalTo(segunda));
	}
	
	@Test
	public void testGerarPagamentoProximoDiaUtilCasoForSabado() {
		
		when(calendario.hoje()).thenReturn(sabado);
		when(leilaoDao.encerrados()).thenReturn(Arrays.asList(leilao));
			
		geradorDePagamentos.gera();
		
		verify(pagamentos).salva(captor.capture());
		
		Pagamento pagamentoGerado = captor.getValue();
		assertThat(pagamentoGerado.getData(), equalTo(segunda));
	}
}
