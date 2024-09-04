package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.time.Duration;

@DisplayName("Testes automatizados da Funcionalidade de Sign Up")

public class SingUpTests {
    private WebDriver navegador;

    @BeforeEach
    public void setup(){
        navegador = new ChromeDriver();
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");
        navegador.get("https://www.saucedemo.com/inventory.html");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername());

        WebElement passwordPath = navegador.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordPath.sendKeys(config.getPassword());

        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();
    }

    @Test
    @DisplayName("Testar os itens")
    public void Carrinho(){
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
        // a blusa vermelha não está com o nome correto
        // a mochila está com a descrição errada
        // é possivel finalizar o carrinho sem nada
        
    }

}
