package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	private static final int UMA_HORA = 60;

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		preco = calculaPrecoUnitario(sessao);

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPrecoUnitario(Sessao sessao) {

		Calculadora calc = selecionaCalculadora(sessao.getEspetaculo().getTipo());
		return calc.calculaPreco(sessao);

	}

	private static Calculadora selecionaCalculadora(TipoDeEspetaculo tipo) {
		if (tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			return new CalculadoraCinemaShow();
		}
		if (tipo.equals(TipoDeEspetaculo.BALLET)) {
			return new CalculadoraBallet();
		}
		if (tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			return new CalculadoraOrquestra();
		}
		return new CalculadoraPadrao();
	}

	private static BigDecimal incrementaPreco(BigDecimal preco, double porcentagemDeAumento) {
		return preco.add(preco.multiply(BigDecimal.valueOf(porcentagemDeAumento)));
	}

	private static BigDecimal incrementaPrecoAcumulado(BigDecimal acumulado, BigDecimal precoOriginal,
			double porcentagem) {
		return acumulado.add(precoOriginal.multiply(BigDecimal.valueOf(porcentagem)));
	}

	private static boolean passouDoLimiteVendas(Sessao sessao, double limite) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue() <= limite;
	}

	private static interface Calculadora {

		public BigDecimal calculaPreco(Sessao sessao);

	}

	private static class CalculadoraCinemaShow implements Calculadora {

		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			if (passouDoLimiteVendas(sessao, 0.05)) {
				return incrementaPreco(sessao.getPreco(), 0.10);
			} else {
				return sessao.getPreco();
			}
		}
	}

	private static class CalculadoraOrquestra implements Calculadora {

		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco;
			if (passouDoLimiteVendas(sessao, 0.50)) {
				preco = incrementaPreco(sessao.getPreco(), 0.20);
			} else {
				preco = sessao.getPreco();
			}

			if (sessao.getDuracaoEmMinutos() > UMA_HORA) {
				preco = incrementaPrecoAcumulado(preco, sessao.getPreco(), 0.10);
			}
			return preco;
		}
	}

	private static class CalculadoraBallet implements Calculadora {

		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			BigDecimal preco;

			if (passouDoLimiteVendas(sessao, 0.50)) {
				preco = incrementaPreco(sessao.getPreco(), 0.20);
			} else {
				preco = sessao.getPreco();
			}

			if (sessao.getDuracaoEmMinutos() > UMA_HORA) {
				preco = incrementaPreco(sessao.getPreco(), 0.10);
			}
			return preco;
		}
	}

	private static class CalculadoraPadrao implements Calculadora {

		@Override
		public BigDecimal calculaPreco(Sessao sessao) {
			return sessao.getPreco();
		}
	}
}
