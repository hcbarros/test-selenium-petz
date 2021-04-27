import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiMassTest {

	private static final WebDriver chrome = new ChromeDriver();
	private static final WebDriverWait webdriver = new WebDriverWait(chrome, 200);
	private JSONArray jsonArray;
	
	
	@Test
	public void _01_test_init() throws FileNotFoundException, IOException, ParseException {
		
		System.setProperty("webdriver.chrome.driver", 
				"/home/henrique/workspace-spring-tool/test-petz/chromedriver");
		
		chrome.get("https://www.petz.com.br");
		
		JSONParser jsonParser = new JSONParser();
		jsonArray = (JSONArray) jsonParser.parse(new FileReader("petz.json"));
		
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.linkText("Ração"))).click();	
		
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("#gridProdutos li:nth-child(3) #produto-href"))).click();
	}
//	
//	public void _02_shouldToValidValues() {
//		
//		webdriver.findElement(By.cssSelector("#gridProdutos li:nth-child(3) #produto-href"))
//		 .click();
//
//		String productName = webdriver.findElement(By.cssSelector(".prod-info h1")).getText();
//		String providerName = webdriver.findElement(By.cssSelector(".blue")).getText();
//		String priceCurrent = webdriver.findElement(By.cssSelector(".price-current")).getText();
//		String priceSubscriber = webdriver.findElement(By.cssSelector(".price-subscriber")).getText();
//		
//		assertEquals(productName, "Golden Power Training Filhote para Cães Sabor Frango e Arroz - 15kg");
//		assertEquals(providerName, "Premier Pet");		
//		assertEquals(priceCurrent, "R$ 144,90");
//		assertEquals(priceSubscriber, "R$ 130,41");
//			
//		webdriver.findElement(By.cssSelector("#adicionarAoCarrinho")).click();
//	}
//	
//	public void _03_shouldToValidCart() {
//		
//		String quantityItems = webdriver.findElement(By.cssSelector(".badge-cart")).getText();
//		assertEquals(quantityItems, "1");
//
//		String productName = webdriver.findElement(By.cssSelector(".td-resumo a")).getText();
//		assertEquals(productName, 
//				"Golden Power Training Filhote para Cães Sabor Frango e Arroz - 15kg");
//	
//		String priceCurrent = webdriver.findElement(By.cssSelector(".total")).getText();
//		assertEquals(priceCurrent, "R$ 144,90");
//		
//		//webdriver.navigate().back();
//	}

	
	
}
