package org.users.test.error;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.opentest4j.AssertionFailedError;
import org.users.test.ConfigLoader;


public class errorUserTest {
    private WebDriver navegador;
    private final String[] Card = {"-sauce-labs-backpack\"]",
            "-sauce-labs-bike-light\"]",
            "-sauce-labs-bolt-t-shirt\"]",
            "-sauce-labs-fleece-jacket\"]",
            "-sauce-labs-onesie\"]",
            "-test.allthethings()-t-shirt-(red)\"]"};

    @BeforeEach
    public void setup(){
        ConfigLoader config = new ConfigLoader("src/test/resources/credentials");
        navegador = ConfigLoader.inicializador("https://www.saucedemo.com/inventory.html");

        WebElement userNamePath = navegador.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNamePath.sendKeys(config.getUsername("error"));

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
    @DisplayName("Sobrenome no cadastro")
    public void lastNameTest(){
        // não da para colocar sobrenome no cadastro de checkout

        navegador.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        navegador.findElement(By.xpath("//*[@id=\"checkout\"]")).click();

        WebElement fName = navegador.findElement(By.xpath("//*[@id=\"first-name\"]"));
        fName.sendKeys("Maria");
        Assertions.assertEquals("Maria",fName.getAttribute("value"));

        WebElement lName = navegador.findElement(By.xpath("//*[@id=\"last-name\"]"));
        lName.sendKeys("Serra");

        try{
            Assertions.assertEquals("Serra", lName.getAttribute("value"));
        }catch (AssertionFailedError e){
            ConfigLoader.addError("Elemento Last Name não funciona como deveria");
        }
        WebElement ZCode = navegador.findElement(By.xpath("//*[@id=\"postal-code\"]"));
        ZCode.sendKeys("124567890");

        // não é possivel apertar o botão de finalizar
        navegador.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        navegador.findElement(By.xpath("//*[@id=\"finish\"]")).click();

        try{
            navegador.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2"));
        }catch (NoSuchElementException e){
            ConfigLoader.addError("Pagina de chekout completo não foi mostrada");
        }

        ConfigLoader.reportErrors();
    }

    @Test
    @DisplayName("Testando quais cards estão com problema")
    public void cardTest(){
        // não é possivel adicionar três
        for (String s : Card) {
            String cardAddBtnXPath = String.format("%s", "//*[@id=\"add-to-cart" + s);
            String cardRemBtnXPath = String.format("%s", "//*[@id=\"remove" + s);

            try{
                WebElement cardAddButton = navegador.findElement(By.xpath(cardAddBtnXPath));
                cardAddButton.click();
                navegador.findElement(By.xpath(cardRemBtnXPath));
            }catch (NoSuchElementException e){
                ConfigLoader.addError("Botão do card " + s + " não foi alterado ao clicar e nem contabilizou");
            }
        }
        ConfigLoader.reportErrors();
    }
    // filtro também não funciona

}
