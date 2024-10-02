package org.users.test.performance;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.opentest4j.AssertionFailedError;
import org.users.test.ConfigLoader;

public class performanceUserTest {
    private WebDriver navegador;

    @BeforeEach
    public void setup(){
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");
        navegador = ConfigLoader.inicializador("https://www.saucedemo.com/inventory.html");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("performance"));

        WebElement passwordPath = navegador.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordPath.sendKeys(config.getPassword());


    }

    @AfterEach
    public void end(){
        navegador.close();
    }

    @Test
    @DisplayName("Teste de loading no login")
    public void loadLogin(){
        long stTime = System.currentTimeMillis();
        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();
        long enTime = System.currentTimeMillis();

        long loadTime = enTime - stTime;
        try{
            Assertions.assertTrue(loadTime < 2000, "Excedeu 2000ms");
        }catch (AssertionFailedError e){
            ConfigLoader.addError("Demorou " +loadTime+"s para carregar a página inicial");
        }
        ConfigLoader.reportErrors();
    }

    @Test
    @DisplayName("Teste de loading no filtro")
    public void loadFil(){
        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();

        long stTime = System.currentTimeMillis();
        WebElement filtro = navegador.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select"));
        Select select = new Select(filtro);
        select.selectByIndex(1);
        long enTime = System.currentTimeMillis();

        long loadTime = enTime - stTime;

        try {
            Assertions.assertTrue(loadTime < 2000, "Excedeu 2000ms");
        }catch (AssertionFailedError e){
            ConfigLoader.addError("Demorou "+loadTime+"s para carregar a mudança do filtro");
        }
        ConfigLoader.reportErrors();
    }

    @Test
    @DisplayName("Teste de loading checkout")
    public void loadCheck(){
        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();

        navegador.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
        navegador.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        navegador.findElement(By.xpath("//*[@id=\"checkout\"]")).click();

        navegador.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("Maria");
        navegador.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys("Serra");
        navegador.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys("124567890");
        navegador.findElement(By.xpath("//*[@id=\"continue\"]")).click();

        navegador.findElement(By.xpath("//*[@id=\"finish\"]")).click();

        long stTime = System.currentTimeMillis();
        WebElement end = navegador.findElement(By.xpath("//*[@id=\"back-to-products\"]"));
        end.click();
        long enTime = System.currentTimeMillis();

        long loadTime = enTime - stTime;

        try {
            Assertions.assertTrue(loadTime < 2000, "Excedeu 2000ms");
        }catch (AssertionFailedError e){
            ConfigLoader.addError("Demorou "+ loadTime +"s para finalizar a compra");
        }
        ConfigLoader.reportErrors();
    }
}
