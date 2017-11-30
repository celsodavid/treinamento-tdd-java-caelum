package br.com.caelum.pm73.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.builder.LeilaoBuilder;
import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTest {

	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;
	private Calendar dia20Novembro;
	private Calendar dia30Novembro;
	private Usuario kurtCobain;
	private Usuario tonyStark;
	private double valorInicial;
	private double valorFinal;

	@Before
	public void setUp() {
		session = new CriadorDeSessao().getSession();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);

		dia20Novembro = new GregorianCalendar(2017, Calendar.NOVEMBER, 20);
		dia30Novembro = new GregorianCalendar(2017, Calendar.NOVEMBER, 30);
		kurtCobain = new Usuario("Kurt Cobain", "nirvana@nirvana.com");
		tonyStark = new Usuario("Tony Stark", "homem.de@ferro.com");

		valorInicial = 100.0;
		valorFinal = 1000.0;

		// inicia a transacao
		session.beginTransaction();
	}

	@After
	public void setOut() {
		// faz o rolback
		session.getTransaction().rollback();

		// encerra a conexcao
		session.close();
	}

	@Test
	public void testDeveRetornarZeroCasoExistirLeilaoEncerrado() {
		Leilao leilao1 = new LeilaoBuilder().comDono(kurtCobain).encerrado().constroi();
		Leilao leilao2 = new LeilaoBuilder().comDono(kurtCobain).encerrado().constroi();

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		long total = leilaoDao.total();

		assertThat(total, equalTo(0L));
	}

	@Test
	public void testDeveRetornarUmCasoExistirUmLeilaoEncerrado() {
		Leilao ativo = new LeilaoBuilder().comNome("Geladeira").comValor(1200.0).comDono(tonyStark).constroi();
		Leilao encerrado = new LeilaoBuilder().comNome("PS2").comValor(450.0).comDono(tonyStark).encerrado().constroi();

		usuarioDao.salvar(tonyStark);
		leilaoDao.salvar(ativo);
		leilaoDao.salvar(encerrado);

		long total = leilaoDao.total();

		assertThat(total, equalTo(1L));
	}

	@Test
	public void testDeveRetornarApenasUmLeilaoNaoUsado() {
		Leilao novo = new LeilaoBuilder().comNome("Tv Plasma 59 Polegadas").comValor(100.0).comDono(kurtCobain)
				.constroi();
		Leilao usado = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(800.0).comDono(tonyStark).usado()
				.constroi();

		usuarioDao.salvar(kurtCobain);
		usuarioDao.salvar(tonyStark);
		leilaoDao.salvar(novo);
		leilaoDao.salvar(usado);

		List<Leilao> leiloes = leilaoDao.novos();
		assertThat(leiloes.size(), equalTo(1));
	}

	@Test
	public void testDeveRetornarApenasLeilaosAntigos() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Tv Plasma 59 Polegadas").comValor(100.0).comDono(kurtCobain)
				.diasAtras(10).constroi();
		Leilao leilao2 = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(800.0).comDono(kurtCobain)
				.diasAtras(5).constroi();

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		List<Leilao> leiloes = leilaoDao.antigos();
		assertThat(leiloes.size(), equalTo(1));
	}

	@Test
	public void testDeveRetornarLeilaosAtivosDentroDoPeriodo() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Tv Plasma 59 Polegadas").comValor(100.0).comDono(kurtCobain)
				.diasAtras(5).constroi();

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);

		List<Leilao> leiloesNoPeriodo = leilaoDao.porPeriodo(dia20Novembro, dia30Novembro, false);

		assertThat(leiloesNoPeriodo.size(), equalTo(1));
		assertThat(leiloesNoPeriodo, hasItems(leilao1));
	}

	@Test
	public void testDeveRetornarLeiloesAtivosForaDoPeriodo() {
		// fazer
	}

	@Test
	public void testDeveRetornarLeiloesEncerradosDentroDoPeriodo() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Tv Plasma 59 Polegadas").comValor(100.0).comDono(kurtCobain)
				.diasAtras(7).encerrado().constroi();

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);

		List<Leilao> leiloesForaDoPeriodo = leilaoDao.porPeriodo(dia20Novembro, dia30Novembro, true);

		assertThat(leiloesForaDoPeriodo.size(), equalTo(1));
		assertThat(leiloesForaDoPeriodo, hasItems(leilao1));
	}

	@Test
	public void testDeveRetornarLeiloesEncerradosForaDoPeriodo() {
		// fazer
	}

	@Test
	public void testLeiloesAtivosEEncerradosDentroDoPeriodoEDeveRetornaApenasOsAtivos() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Tv Plasma 59 Polegadas").comValor(100.0).comDono(kurtCobain)
				.diasAtras(7).encerrado().constroi();
		Leilao leilao2 = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(800.0).comDono(kurtCobain)
				.diasAtras(5).constroi();

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		List<Leilao> leiloes = leilaoDao.porPeriodo(dia20Novembro, dia30Novembro, false);

		assertThat(leiloes.size(), equalTo(1));
		assertThat(leiloes, hasItems(leilao2));
	}

	@Test
	public void testDeveRetornarLeiloesAtivosDentroDoIntervaloDeValorEComNoMininoTresLances() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Tv Plasma 59 Polegadas").comValor(50.0).comDono(kurtCobain)
				.diasAtras(7).encerrado().constroi();
		Leilao leilao2 = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(800.0).comDono(kurtCobain)
				.diasAtras(5).constroi();

		Calendar data = new GregorianCalendar(2017, Calendar.NOVEMBER, 30);
		Lance lance1 = new Lance(data, kurtCobain, 300.0, leilao2);
		Lance lance2 = new Lance(data, kurtCobain, 300.0, leilao2);
		Lance lance3 = new Lance(data, kurtCobain, 300.0, leilao2);

		leilao2.adicionaLance(lance1);
		leilao2.adicionaLance(lance2);
		leilao2.adicionaLance(lance3);

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		List<Leilao> leiloes = leilaoDao.disputadosEntre(valorInicial, valorFinal);

		assertThat(leiloes.size(), equalTo(1));
	}

	@Test
	public void testNaoDeveRetornarLeiloesEncerradosDentroDoIntervaloDeValor() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Tv Plasma 59 Polegadas").comValor(150.0).comDono(kurtCobain)
				.diasAtras(7).encerrado().constroi();

		Calendar data = new GregorianCalendar(2017, Calendar.NOVEMBER, 30);
		Lance lance1 = new Lance(data, kurtCobain, 300.0, leilao1);
		Lance lance2 = new Lance(data, kurtCobain, 300.0, leilao1);
		Lance lance3 = new Lance(data, kurtCobain, 300.0, leilao1);

		leilao1.adicionaLance(lance1);
		leilao1.adicionaLance(lance2);
		leilao1.adicionaLance(lance3);

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);

		List<Leilao> leiloes = leilaoDao.disputadosEntre(valorInicial, valorFinal);

		assertThat(leiloes.size(), equalTo(0));
	}

	@Test
	public void testNaoDeveRetornarLeiloesAtivosForaDoIntervaloDeValor() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(1500.0).comDono(kurtCobain)
				.diasAtras(5).constroi();

		Calendar data = new GregorianCalendar(2017, Calendar.NOVEMBER, 30);
		Lance lance1 = new Lance(data, kurtCobain, 300.0, leilao1);
		Lance lance2 = new Lance(data, kurtCobain, 300.0, leilao1);
		Lance lance3 = new Lance(data, kurtCobain, 300.0, leilao1);

		leilao1.adicionaLance(lance1);
		leilao1.adicionaLance(lance2);
		leilao1.adicionaLance(lance3);

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);

		List<Leilao> leiloes = leilaoDao.disputadosEntre(valorInicial, valorFinal);

		assertThat(leiloes.size(), equalTo(0));
	}

	@Test
	public void testDeveRetornarListaDeLeilosPorUsuarioSemRepeticao() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(1500.0).comDono(kurtCobain)
				.diasAtras(5).constroi();
		Leilao leilao2 = new LeilaoBuilder().comNome("TV").comValor(800.0).comDono(kurtCobain).diasAtras(5).constroi();

		Calendar data = new GregorianCalendar(2017, Calendar.NOVEMBER, 30);
		Lance lance1 = new Lance(data, kurtCobain, 300.0, leilao1);
		Lance lance2 = new Lance(data, kurtCobain, 500.0, leilao1);
		Lance lance3 = new Lance(data, kurtCobain, 500.0, leilao2);

		leilao1.adicionaLance(lance1);
		leilao1.adicionaLance(lance2);
		leilao2.adicionaLance(lance3);

		usuarioDao.salvar(kurtCobain);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(kurtCobain);

		assertThat(leiloes.size(), equalTo(2));
	}

	@Test
	public void testDeveRetornarOValorInicialMedioDosLancesPorUsuario() {
		Leilao leilao1 = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(50.0).comDono(kurtCobain)
				.diasAtras(5).constroi();
		Leilao leilao2 = new LeilaoBuilder().comNome("TV").comValor(250.0).comDono(kurtCobain).diasAtras(5).constroi();

		Calendar data = new GregorianCalendar(2017, Calendar.NOVEMBER, 30);

		leilao1.adicionaLance(new Lance(data, tonyStark, 100.0, leilao1));
		leilao1.adicionaLance(new Lance(data, tonyStark, 200.0, leilao1));

		leilao2.adicionaLance(new Lance(data, tonyStark, 500.0, leilao2));

		usuarioDao.salvar(kurtCobain);
		usuarioDao.salvar(tonyStark);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);

		double valorInicialMedio = leilaoDao.getValorInicialMedioDoUsuario(tonyStark);
		assertThat(valorInicialMedio, equalTo(150.0));
	}

	@Test
	public void testDeveExcluirLeilaoEncerrado() {
		Leilao leilao = new LeilaoBuilder().comNome("Notebook Acer 15.5").comValor(50.0).comDono(kurtCobain).encerrado()
				.constroi();

		usuarioDao.salvar(kurtCobain);

		leilaoDao.salvar(leilao);
		leilaoDao.deletaEncerrados();

		session.flush();

		Leilao porId = leilaoDao.porId(leilao.getId());

		assertThat(porId, nullValue());
	}
}
