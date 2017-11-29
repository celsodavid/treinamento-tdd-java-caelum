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
	}
	
	@After
	public void setOut() {
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
}
