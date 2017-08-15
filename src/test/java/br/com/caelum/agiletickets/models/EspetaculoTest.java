package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.lang.model.element.NestingKind;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void criarTresSessoesDiarias() {
		Espetaculo ivete = new Espetaculo();
		
		List<Sessao> sessoes = ivete.criaSessoes(new LocalDate(2010, 01, 01), new LocalDate(2010, 01, 03), new LocalTime(17, 0), Periodicidade.DIARIA);
		
		assertEquals(3 , sessoes.size());
	}
	
	@Test
	public void criarSessoesNosDiasEspecificados() {
		Espetaculo ivete = new Espetaculo();
		
		List<Sessao> sessoes = ivete.criaSessoes(new LocalDate(2010, 01, 01), new LocalDate(2010, 01, 03), new LocalTime(17, 0), Periodicidade.DIARIA);
		
		assertEquals("01/01/10", sessoes.get(0).getDia());
		assertEquals("02/01/10", sessoes.get(1).getDia());
		assertEquals("03/01/10", sessoes.get(2).getDia());
	}
	
	@Test
	public void criarSessoesNoHorarioCorreto() {
		Espetaculo ivete = new Espetaculo();
		
		List<Sessao> sessoes = ivete.criaSessoes(new LocalDate(2010, 01, 01), new LocalDate(2010, 01, 03), new LocalTime(17, 0), Periodicidade.DIARIA);
		
		assertEquals("17:00", sessoes.get(0).getHora());
		
	}
	
	@Test
	public void criar5SessoesSemanais() {
		Espetaculo ivete = new Espetaculo();
		
		List<Sessao> sessoes = ivete.criaSessoes(new LocalDate(2010, 01, 01), new LocalDate(2010, 01, 31), new LocalTime(17, 0), Periodicidade.SEMANAL);
		
		assertEquals(5 , sessoes.size());
	}
	
	@Test
	public void criarSessoesSemanaisNosDiasEspecificados() {
		Espetaculo ivete = new Espetaculo();
		
		List<Sessao> sessoes = ivete.criaSessoes(new LocalDate(2010, 01, 01), new LocalDate(2010, 01, 31), new LocalTime(17, 0), Periodicidade.SEMANAL);
		
		assertEquals("01/01/10", sessoes.get(0).getDia());
		assertEquals("08/01/10", sessoes.get(1).getDia());
		assertEquals("15/01/10", sessoes.get(2).getDia());
		assertEquals("22/01/10", sessoes.get(3).getDia());
		assertEquals("29/01/10", sessoes.get(4).getDia());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void erroInicioDepoisQueFim() {
		Espetaculo ivete = new Espetaculo();
		
		List<Sessao> sessoes = ivete.criaSessoes(new LocalDate(2010, 01, 31), new LocalDate(2010, 01, 01), new LocalTime(17, 0), Periodicidade.SEMANAL);
		
		fail();
	}
	
	@Test
	public void inicioEFimIguaisCriaUmaSessao() {
		Espetaculo ivete = new Espetaculo();
		
		List<Sessao> sessoes = ivete.criaSessoes(new LocalDate(2010, 01, 31), new LocalDate(2010, 01, 31), new LocalTime(17, 0), Periodicidade.SEMANAL);
		
		assertEquals(1 , sessoes.size());
	}
	
	
}
