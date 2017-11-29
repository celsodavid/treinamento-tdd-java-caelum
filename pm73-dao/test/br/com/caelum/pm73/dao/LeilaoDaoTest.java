package br.com.caelum.pm73.dao;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LeilaoDaoTest {

	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;
	
	@Before
	public void setUp() {
		session = new CriadorDeSessao().getSession();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);
	}
	
	@After
	public void setOut() {
		session.close();
	}
	
	@Test
	public void testDeveRetornarZeroCasoExistirLeilaoEncerrado() {
		
	}
	
	@Test
	public void testDeveRetornarUmCasoExistirUmLeilaoEncerrado() {
		
	}
}
