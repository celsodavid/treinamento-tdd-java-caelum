package br.com.caelum.pm73.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTest {

	private Session session;
	private UsuarioDao usuarioDao;
	private Usuario usuario;
	
	@Before
	public void setUp() {
		session = new CriadorDeSessao().getSession();
		usuarioDao = new UsuarioDao(session);
		usuario = new Usuario("fulano", "fulano@fulano.com.br");
		
		session.beginTransaction();
	}
	
	@After
	public void setOut() {
		session.getTransaction().rollback();
		session.close();
	}
	
	@Test
	public void testDeveEncontrarPeloNomeEEmail() {
		usuarioDao.salvar(usuario);
		
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("fulano", "fulano@fulano.com.br");
		
		assertThat(usuario, equalTo(usuarioDoBanco));
	}
	
	@Test
	public void testSeNaoExistirUsuarioDeveRetornarNulo() {
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("fulano", "fulano@fulano.com.br");
		
		assertNull(usuarioDoBanco);
	}
	
	@Test
	public void testDeveExcluirUmUsuario() {
		usuarioDao.salvar(usuario);
		
		Usuario usuarioDoBancoSalvo = usuarioDao.porNomeEEmail("fulano", "fulano@fulano.com.br");		
		assertThat(usuarioDoBancoSalvo, equalTo(usuario));
		
		usuarioDao.deletar(usuario);
		session.flush(); // metodo para ser utilizado na delecao e na atualizacao
		
		Usuario usuarioDoBancoDeletado = usuarioDao.porNomeEEmail("fulano", "fulano@fulano.com.br");
		assertThat(usuarioDoBancoDeletado, nullValue());
	}

	@Test
	public void testDeveAtualizarUmUsuario() {
		usuarioDao.salvar(usuario);
		
		Usuario usuarioDoBancoSalvo = usuarioDao.porNomeEEmail("fulano", "fulano@fulano.com.br");		
		assertThat(usuarioDoBancoSalvo, equalTo(usuario));
		
		usuario.setNome("Celso");
		usuario.setEmail("celso.lopes@fs.com.br");
		usuarioDao.atualizar(usuario);
		
		session.flush(); // metodo para ser utilizado na delecao e na atualizacao
		
		Usuario usuarioDoBancoAlterado = usuarioDao.porNomeEEmail("Celso", "celso.lopes@fs.com.br");
		assertThat(usuarioDoBancoAlterado, equalTo(usuario));
	}
}
