package br.com.caelum.agiletickets.acceptance;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.caelum.agiletickets.PreencheBanco;
import br.com.caelum.agiletickets.acceptance.page.EstabelecimentosPage;
import br.com.caelum.agiletickets.acceptance.page.ReservaPage;

public class ReservaTest {

	public static String BASE_URL = "http://localhost:8080";
	private static WebDriver browser;

	private ReservaPage dado, quando, entao;
	

	@BeforeClass
	public static void abreBrowser() {
		System.setProperty("webdriver.gecko.driver", "./geckodriver");
		browser = new FirefoxDriver();
	}

	@Before
	public void setUp() throws Exception {
		PreencheBanco.main(new String[0]);
		reserva = new ReservaPage(browser);
		dado = new ReservaPage(browser);
		quando = dado;
		entao = dado;
		
	}

	@AfterClass
	public static void teardown() {
		browser.close();
	}

	@Test
	public void abrePaginaDeSessaoComEspacosDiponiveiseEReservaUmIngressoComValorAcrecidoDe10Porcento() throws Exception {
		
		dado.sessaoNaDataEIngressosReservados("30/08/17", 95);
		quando.sessaoNaDataEIngressosReservados("30/08/17", 1);
		entao.apresentaPreco(55);
		
	}
	
	
	
}
