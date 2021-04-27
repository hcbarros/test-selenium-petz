
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiTest {

	private static final WebDriver chrome = new ChromeDriver();
	private static final WebDriverWait webdriver = new WebDriverWait(chrome, 200);
	
	
	@Test
	public void _01_test_init() {
		
		System.setProperty("webdriver.chrome.driver", 
				"/home/henrique/workspace-spring-tool/test-petz/chromedriver");
		
		chrome.get("https://www.petz.com.br");
		
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.linkText("Ração"))).click();	
		
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("#gridProdutos li:nth-child(3) #produto-href"))).click();
	}
	
	@Test
	public void _02_shouldToValidValues() {
				
		String productName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".prod-info h1"))).getText();
		String providerName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".blue"))).getText();
		String priceCurrent = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".price-current"))).getText();
		String priceSubscriber = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".price-subscriber"))).getText();
		
		assertEquals(productName, "Golden Power Training Filhote para Cães Sabor Frango e Arroz - 15kg");
		assertEquals(providerName, "Premier Pet");		
		assertEquals(priceCurrent, "R$ 144,99");
		assertEquals(priceSubscriber, "R$ 130,49");
			
	}
	
	@Test
	public void _03_shouldToValidCart() {
		
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("#adicionarAoCarrinho"))).click();
		
		String quantityItems = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".badge-cart"))).getText();
		String productName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".td-resumo a"))).getText();
		String priceCurrent = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("td.preco"))).getText();
		
		assertEquals(productName, 
				"Golden Power Training Filhote para Cães Sabor Frango e Arroz - 15kg");
		assertEquals(quantityItems, "1");
		assertEquals(priceCurrent, "R$ 144,99");
		
		//webdriver.navigate().back();
	}

	
	
}
