
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiTest {

	private static final WebDriver chrome = new ChromeDriver();
	private static final WebDriverWait webdriver = new WebDriverWait(chrome, 1000);
	
	
	@Test
	public void _01_test_init() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", 
				"/home/henrique/workspace-spring-tool/test-petz/chromedriver");
		
		chrome.get("https://www.petz.com.br");
		
		
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.linkText("Ração"))).click();	
		
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("#a_-2_780"))).click();		
		
		Thread.sleep(2000);
		
		takeScreenshot();
	}
	
	@Test
	public void _02_shouldToValidValues() {
				
		webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("#gridProdutos li:nth-child(3) #produto-href"))).click();
		
		String productName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".prod-info h1"))).getText();
		String providerName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".blue"))).getText();
		String priceCurrent = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".price-current"))).getText();
		String priceSubscriber = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".price-subscriber"))).getText();
		
		takeScreenshot();
		
		assertEquals(productName, "Ração Biofresh para Cães Filhotes de Raças Pequenas e Mini");
		assertEquals(providerName, "Biofresh");		
		assertEquals(priceCurrent, "R$ 73,99");
		assertEquals(priceSubscriber, "R$ 66,59");			
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
		
		takeScreenshot();
		
		assertEquals(quantityItems, "1");
		assertEquals(productName, "Ração Biofresh para Cães Filhotes de Raças Pequenas e Mini - 1 Kg");
		assertEquals(priceCurrent, "R$ 73,99");
	}
	
	@Test
	public void _04_testsMass() throws FileNotFoundException, 
									   IOException, ParseException {
		
		chrome.navigate().back();
		chrome.navigate().back();
		
		int index = 2;
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("petz.json"));
		Iterator<JSONObject> iterator = jsonArray.iterator();
		while(iterator.hasNext()) {
			
			JSONObject obj = (JSONObject) iterator.next();
		
			webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector(
			"#gridProdutos li:nth-child("+(Long) obj.get("item")+") #produto-href")))
			 .click();
	
			String productName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector(".prod-info h1"))).getText();
			String providerName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector(".blue"))).getText();
			String priceCurrent = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector(".price-current"))).getText();
			String priceSubscriber = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector(".price-subscriber"))).getText();
			
			webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("#adicionarAoCarrinho"))).click();
			
			String cartName = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("tr:nth-child("+(index)+") .td-resumo a"))).getText();
			String cartPrice = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector("tr:nth-child("+(index)+") td.preco"))).getText();
			String quantity = webdriver.until(ExpectedConditions.visibilityOfElementLocated(
					By.cssSelector(".badge-cart"))).getText();
			
			index++;
			
			takeScreenshot();
			
			assertEquals(productName, (String) obj.get("productName"));
			assertEquals(providerName, (String) obj.get("providerName"));		
			assertEquals(priceCurrent, (String) obj.get("priceCurrent"));
			assertEquals(priceSubscriber, (String) obj.get("priceSubscriber"));
			
			assertEquals(cartName, (String) obj.get("carName"));
			assertEquals(cartPrice, (String) obj.get("priceCurrent"));
			assertEquals(Long.valueOf(quantity), (Long) obj.get("quantity"));
			
			chrome.navigate().back();
			chrome.navigate().back();
		}
	}
	
	public void takeScreenshot(){
        File scrFile = ((TakesScreenshot)chrome).getScreenshotAs(OutputType.FILE);
        Date data = new Date();
        try {
            FileUtils.copyFile(scrFile, new File(data.toString()),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
