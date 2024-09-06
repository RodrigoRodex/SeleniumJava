package org.users.test.standard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.users.test.ConfigLoader;

public class cards {
    private WebDriver navegador;
    private final String[] nomesEspe = {
            "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Onesie", "Sauce Labs Red T-Shirt",
            "Sauce Labs Backpack"
    };

    @BeforeEach
    public void setup(){
        navegador = new ChromeDriver();
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");
        navegador.get("https://www.saucedemo.com/inventory.html");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("standard"));

        WebElement passwordPath = navegador.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordPath.sendKeys(config.getPassword());

        WebElement loginBtn = navegador.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginBtn.click();
    }

    @Test
    @DisplayName("Testa se a mochila está com nome e texto corretos")
    public void cardName(){
        for (int i= 0; i<=4;i++){
            String cardXPath = String.format("//*[@id=\"item_%d_title_link\"]/div",i);
            WebElement card = navegador.findElement(By.xpath(cardXPath));

            String nomeEspe = nomesEspe[i];
            String nome = card.getText();
            Assertions.assertEquals(nome, nomeEspe);
            // verifica se todas os nomes estão corretos, se um estiver errado, da erro
        }
    }
}
