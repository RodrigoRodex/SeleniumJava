package org.users.test.standard;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.users.test.ConfigLoader;

@DisplayName("Testes automatizados da Funcionalidade de Sign Up")

public class defaultUserTest {
    private WebDriver navegador;

    @BeforeEach
    public void setup(){
        navegador = ConfigLoader.inicializador("https://www.saucedemo.com/inventory.html");
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("standard"));

        WebElement passwordPath = navegador.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordPath.sendKeys(config.getPassword());

        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();
    }

    @AfterEach
    public void end(){
        navegador.close();
    }

    @Test
    @DisplayName("Testar os itens no default user")
    public void carrinhoAll(){
        navegador.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        navegador.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        navegador.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        navegador.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        navegador.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        navegador.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();

        navegador.findElement(By.className("shopping_cart_link")).click();
        navegador.findElement(By.id("checkout")).click();
        navegador.findElement(By.id("first-name")).sendKeys("Adriana");
        navegador.findElement(By.id("last-name")).sendKeys("Veronica");
        navegador.findElement(By.id("postal-code")).sendKeys("124567890");
        navegador.findElement(By.id("continue")).click();

        navegador.findElement(By.xpath("//*[@id=\"finish\"]")).click();

        navegador.findElement(By.xpath("//*[@id=\"back-to-products\"]")).click();

        // é possivel finalizar o carrinho sem nada
    }

}
