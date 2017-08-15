package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		preco = calculaPrecoUnitario(sessao);
		
		 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPrecoUnitario(Sessao sessao) {
		BigDecimal preco;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			if(passouDoLimiteVendas(sessao, 0.05)) {
				
				preco = incrementaPreco(sessao.getPreco(), 0.10);
			} else {
				preco = sessao.getPreco();
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			if(passouDoLimiteVendas(sessao, 0.50)) {
				preco = incrementaPreco(sessao.getPreco(), 0.20);
			} else {
				preco = sessao.getPreco();
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = incrementaPreco(sessao.getPreco(), 0.10);
			}
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			if(passouDoLimiteVendas(sessao, 0.50)) { 
				preco = incrementaPreco(sessao.getPreco(), 0.20);
			} else {
				preco = sessao.getPreco();
			}

			if(sessao.getDuracaoEmMinutos() > 60){
				preco = incrementaPrecoAcumulado(preco, sessao.getPreco(), 0.10);
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		}
		
		return preco;
		
	}

	private static BigDecimal incrementaPreco(BigDecimal preco, double porcentagemDeAumento) {
		return preco.add(preco.multiply(BigDecimal.valueOf(porcentagemDeAumento)));
	}
	
	private static BigDecimal incrementaPrecoAcumulado(BigDecimal acumulado, BigDecimal precoOriginal, double porcentagem) {
		return acumulado.add(precoOriginal.multiply(BigDecimal.valueOf(porcentagem)));
	}

	private static boolean passouDoLimiteVendas(Sessao sessao, double limite) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= limite;
	}

}