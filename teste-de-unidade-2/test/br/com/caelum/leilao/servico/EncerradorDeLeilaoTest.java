package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.mockito.InOrder;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.dao.InterfaceEnviadorDeEmail;
import br.com.caelum.leilao.infra.dao.InterfaceLeilaoDao;

public class EncerradorDeLeilaoTest {
	
	private Usuario celso;
	private Usuario lopes;
	
	public void setUp() {
		this.celso = new Usuario("Celso");
		this.lopes = new Usuario("Lopes");
	}

	@Test
	public void testDeveEncerrarLeiloesQueEstaoMaisDeUmaSemana() {
		Leilao leilao = new CriadorDeLeilao()
				.para("TV 4K")
				.naData(new GregorianCalendar(2017, Calendar.NOVEMBER, 21))
				.lance(celso, 600)
				.constroi();
		
		InterfaceLeilaoDao daoFalso = mock(InterfaceLeilaoDao.class);
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao));
		
		InterfaceEnviadorDeEmail carteiroFalso = mock(InterfaceEnviadorDeEmail.class);
		
		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoFalso, carteiroFalso);
		encerradorDeLeilao.encerra();
		
		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(1));
		
		verify(daoFalso).atualiza(leilao);
		verify(carteiroFalso).envia(leilao);
		
		InOrder inOrder = inOrder(daoFalso, carteiroFalso);
		inOrder.verify(daoFalso, times(1)).atualiza(leilao);
		inOrder.verify(carteiroFalso, times(1)).envia(leilao);
	}

	@Test
	public void testQueNaoEncerraComMenosDeUmaSemana() {
		Leilao leilao = new CriadorDeLeilao()
				.para("TV 4K")
				.naData(new GregorianCalendar(2017, Calendar.NOVEMBER, 23))
				.lance(celso, 600)
				.constroi();
		
		InterfaceLeilaoDao daoFalso = mock(InterfaceLeilaoDao.class);
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao));
		
		InterfaceEnviadorDeEmail carteiroFalso = mock(InterfaceEnviadorDeEmail.class);
		
		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoFalso, carteiroFalso);
		encerradorDeLeilao.encerra();
		
		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(0));
		assertThat(leilao.isEncerrado(), equalTo(false));
		
		verify(daoFalso, never()).atualiza(leilao);
	}
	
	@Test
	public void testQueEncerraLeilaoComMaisDeUmaSemanaEMantemLeiloesComMenosDeUmaSemana() {
		Leilao leilao = new CriadorDeLeilao()
				.para("TV 4K")
				.naData(new GregorianCalendar(2017, Calendar.NOVEMBER, 20))
				.lance(celso, 600)
				.constroi();
		
		Leilao leilao2 = new CriadorDeLeilao()
				.para("TV 4K")
				.naData(new GregorianCalendar(2017, Calendar.NOVEMBER, 25))
				.lance(lopes, 300)
				.constroi();
		
		InterfaceLeilaoDao daoFalso = mock(InterfaceLeilaoDao.class);
		when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao, leilao2));
		
		InterfaceEnviadorDeEmail carteiroFalso = mock(InterfaceEnviadorDeEmail.class);
		
		EncerradorDeLeilao encerradorDeLeilao = new EncerradorDeLeilao(daoFalso, carteiroFalso);
		encerradorDeLeilao.encerra();
		
		assertThat(encerradorDeLeilao.getTotalEncerrados(), equalTo(1));
		assertThat(leilao.isEncerrado(), equalTo(true));
		assertThat(leilao2.isEncerrado(), equalTo(false));
		
		verify(daoFalso, times(1)).atualiza(leilao);
		verify(daoFalso, never()).atualiza(leilao2);
	}
}
