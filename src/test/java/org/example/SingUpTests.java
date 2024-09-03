package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@DisplayName("Testes automatizados da Funcionalidade de Sign Up")

public class SingUpTests {
    WebDriver navegador;
    @BeforeEach
    public void setup(){
        navegador = new ChromeDriver();
        navegador.get("https://www.saucedemo.com/inventory.html");
        navegador.findElement(By.id("user-name")).sendKeys("standard_user");
        navegador.findElement(By.id("password")).sendKeys("secret_sauce");
        navegador.findElement(By.id("login-button")).click();
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
