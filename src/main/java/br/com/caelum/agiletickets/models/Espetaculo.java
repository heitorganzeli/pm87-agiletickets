package br.com.caelum.agiletickets.models;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@Entity
public class Espetaculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoDeEspetaculo tipo;

	@ManyToOne
	private Estabelecimento estabelecimento;

	@OneToMany(mappedBy = "espetaculo")
	private List<Sessao> sessoes = newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoDeEspetaculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeEspetaculo tipo) {
		this.tipo = tipo;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	/**
	 * Esse metodo eh responsavel por criar sessoes para o respectivo espetaculo,
	 * dado o intervalo de inicio e fim, mais a periodicidade.
	 * 
	 * O algoritmo funciona da seguinte forma: - Caso a data de inicio seja
	 * 01/01/2010, a data de fim seja 03/01/2010, e a periodicidade seja DIARIA, o
	 * algoritmo cria 3 sessoes, uma para cada dia: 01/01, 02/01 e 03/01.
	 * 
	 * - Caso a data de inicio seja 01/01/2010, a data fim seja 31/01/2010, e a
	 * periodicidade seja SEMANAL, o algoritmo cria 5 sessoes, uma a cada 7 dias:
	 * 01/01, 08/01, 15/01, 22/01 e 29/01.
	 * 
	 * Repare que a data da primeira sessao é sempre a data inicial.
	 */
	public List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim, LocalTime horario, Periodicidade periodicidade) {

		int periodo = calculaTotalDiasElegiveis(inicio, fim);

		int incremento = calculaPassoDeIncrementoDias(periodicidade);
		
		return criaSessoesPorPeriodoEIncremento(inicio, horario, periodo, incremento);
	}
			
	private int calculaTotalDiasElegiveis(LocalDate inicio, LocalDate fim) {
		if (inicio == null || fim == null)
			throw new NullPointerException("inicio ou fim nulos");		
		if (inicio.isAfter(fim))
			throw new IllegalArgumentException("inicio maior que fim");

		return Days.daysBetween(inicio, fim).getDays() + 1;
	}
	
	private int calculaPassoDeIncrementoDias(Periodicidade periodicidade) {
		return periodicidade.equals(Periodicidade.DIARIA) ? 1 : 7;
	}
	
	private List<Sessao> criaSessoesPorPeriodoEIncremento (LocalDate inicio, LocalTime horario, int periodo, int incremento) {
		if (inicio == null || horario == null)
			throw new NullPointerException("inicio ou horario nulos");		
		
		List<Sessao> sessoes = new ArrayList<Sessao>();
		
		for (int i = 0; i < periodo; i += incremento) {
			Sessao sessao = new Sessao();

			sessao.setInicio(inicio.plusDays(i).toDateTime(horario));
			sessoes.add(sessao);
			
			
		}
		
		return sessoes;
	}

	public boolean Vagas(int qtd, int min) {
		// ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
		int totDisp = 0;

		for (Sessao s : sessoes) {
			if (s.getIngressosDisponiveis() < min)
				return false;
			totDisp += s.getIngressosDisponiveis();
		}

		if (totDisp >= qtd)
			return true;
		else
			return false;
	}

	public boolean Vagas(int qtd) {
		// ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
		int totDisp = 0;

		for (Sessao s : sessoes) {
			totDisp += s.getIngressosDisponiveis();
		}

		if (totDisp >= qtd)
			return true;
		else
			return false;
	}

}
