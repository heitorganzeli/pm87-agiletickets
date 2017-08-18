package br.com.caelum.agiletickets.acceptance.page;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReservaPage {

	private static final String BASE_URL = "http://localhost:8080";
	private final WebDriver driver;

	public ReservaPage(WebDriver driver) {
		this.driver = driver;
	}

	public void abreSessaoData(String data) {
		driver.get(BASE_URL + "/");
		
		WebElement linhaData = linhaData(data);
		linhaData.click();
		
		System.out.println("URL: " + driver.getCurrentUrl());
		
	}
	
	public void reservaIngressos(int quantidade) {
		WebElement form = form();
		form.findElement(By.name("quantidade")).sendKeys("" + quantidade);
		form.submit();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private WebElement form() {
		return driver.findElement(By.tagName("form"));
	}

	private WebElement linhaData(String data) {
		List<WebElement> linhas = driver.findElements(By.tagName("li"));
		
		return linhas.stream().filter(linha -> linha.getText().contains(data)).findFirst().get();
	}

	public void verificaPreco(int valor) {
		WebElement message = driver.findElement(By.id("message"));
		assertThat(message.getText(), containsString("" + valor));
		
	}

}
