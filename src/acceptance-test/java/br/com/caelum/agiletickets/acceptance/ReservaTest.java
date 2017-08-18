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
	private ReservaPage reserva;
	private EstabelecimentosPage estabelecimentos;

	@BeforeClass
	public static void abreBrowser() {
		System.setProperty("webdriver.gecko.driver", "./geckodriver");
		browser = new FirefoxDriver();
	}

	@Before
	public void setUp() throws Exception {
		PreencheBanco.main(new String[0]);
		reserva = new ReservaPage(browser);
		
	}

	@AfterClass
	public static void teardown() {
		browser.close();
	}

	@Test
	public void abrePaginaDeSessaoComEspacosDiponiveiseEReservaUmIngressoComValorAcrecidoDe10Porcento() throws Exception {
		reserva.abreSessaoData("30/08/17");
		reserva.reservaIngressos(95);
		reserva.abreSessaoData("30/08/17");
		reserva.reservaIngressos(1);
		reserva.verificaPreco(55);
		
	
	}
	
	
	
}
